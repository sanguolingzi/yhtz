package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerRecommendRelationBusiMapper;
import com.yinhetianze.business.customer.service.busi.CustomerRecommendRelationBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CustomerRecommendRelationBusiServiceImpl implements CustomerRecommendRelationBusiService
{
    @Autowired
    private CustomerRecommendRelationBusiMapper customerRecommendRelationBusiMapper;

    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    public int insertSelective(BusiCustomerRecommendRelationPojo record) {
        return customerRecommendRelationBusiMapper.insertSelective(record);
    }

    @Override
    public int bindRelation(BusiCustomerRecommendRelationPojo record) throws BusinessException {
        try{
            if(record.getRecomGameId()!=null && record.getRecomGameId().intValue() == 0){
                return 1;
            }

            //查找被推荐人身份 若 被推荐人身份是 合伙人 那么 商城不记录此条推荐关系
            BusiCustomerPojo customerPojo = customerInfoServiceImpl.selectByGameId(record.getRecomedGameId());
            if(customerPojo != null && customerPojo.getIsPartner() == 0){
                return 1;
            }


            //推荐人
            BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo = new BusiCustomerRecommendRelationPojo();
            //被推荐人 也就是刚刚新增的customer
            busiCustomerRecommendRelationPojo.setRecomGameId(record.getRecomGameId());
            busiCustomerRecommendRelationPojo.setRecomedGameId(record.getRecomedGameId());

            //维护推荐人关系 A推荐B 则 B的code 为A的 customerIdA+'#';
            //B推荐C 则 C的code B的code+B 的 customerId+'#';
            //最后C的code 为customerIdA+'#'+customerIdB+'#'
            //若C继续推荐别人  code 依次类推

            BusiCustomerRecommendRelationPojo recommend =  customerRecommendRelationInfoServiceImpl.selectRecommendUser(busiCustomerRecommendRelationPojo.getRecomGameId());
            String relationCode = "";
            Integer grandRecomGameId = null;
            if(recommend==null){
                relationCode=busiCustomerRecommendRelationPojo.getRecomGameId()+"#";
            }else{
                relationCode=recommend.getRelationCode()+busiCustomerRecommendRelationPojo.getRecomGameId()+"#";
                //上上级推荐人
                grandRecomGameId=recommend.getRecomGameId();
            }

            busiCustomerRecommendRelationPojo.setRelationCode(relationCode);
            busiCustomerRecommendRelationPojo.setGrandRecomGameId(grandRecomGameId);
            BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByGameId(record.getRecomGameId());

            if(busiCustomerPojo != null && busiCustomerPojo.getIsPartner() == 0){
                //推荐人为 则添加该字段
                busiCustomerRecommendRelationPojo.setPartnerId(busiCustomerPojo.getGameId());
            }else{
                //或者 查看推荐人是否 有合伙人字段 有 则设置给被推荐人
                if(recommend != null && recommend.getPartnerId()!=null){
                    busiCustomerRecommendRelationPojo.setPartnerId(recommend.getPartnerId());
                }
            }
            if(CommonUtil.isNotEmpty(record.getShowId())){
                busiCustomerRecommendRelationPojo.setShowId(record.getShowId());
            }
            if(CommonUtil.isNotEmpty(record.getpShowId())){
                busiCustomerRecommendRelationPojo.setpShowId(record.getpShowId());
            }
            customerRecommendRelationBusiMapper.insertSelective(busiCustomerRecommendRelationPojo);
        }catch(Exception e){ //若是主键重复异常 就忽略掉
            if(!(e instanceof DuplicateKeyException)){
                LoggerUtil.error(CustomerRecommendRelationBusiServiceImpl.class,e);
                throw new BusinessException();
            }
        }

        return 1;
    }

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerRecommendRelationPojo record) {
        return customerRecommendRelationBusiMapper.updateByPrimaryKeySelective(record);
    }
}