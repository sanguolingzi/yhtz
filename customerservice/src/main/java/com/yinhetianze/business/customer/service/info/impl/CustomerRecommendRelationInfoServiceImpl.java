package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerRecommendRelationInfoMapper;
import com.yinhetianze.business.customer.model.BusiCustomerRecommendRelationModel;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerRecommendRelationInfoServiceImpl implements CustomerRecommendRelationInfoService
{
    @Autowired
    private CustomerRecommendRelationInfoMapper customerRecommendRelationInfoMapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;


    @Override
    public List<BusiCustomerRecommendRelationModel> selectRecommendRelationList(Integer gameId) {
        return customerRecommendRelationInfoMapper.selectRecommendRelationList(gameId);
    }

    @Override
    public BusiCustomerRecommendRelationPojo selectRecommendUser(Integer gameId) {
        BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo = new BusiCustomerRecommendRelationPojo();
        busiCustomerRecommendRelationPojo.setRecomedGameId(gameId);
        busiCustomerRecommendRelationPojo.setDelFlag((short)0);
        return customerRecommendRelationInfoMapper.selectOne(busiCustomerRecommendRelationPojo);
    }

    @Override
    public boolean doRecommendReward(Integer gameId) {

        Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("gameId",gameId);
        paraMap.put("recommendLimitDay",sysPropertiesUtils.getStringValue("recommendLimitDay"));
        int limit = sysPropertiesUtils.getIntValue("recommendLimitUser");
        int count = customerRecommendRelationInfoMapper.selectRecommendRelationCount(paraMap);

        if(count >= limit){
            return true;
        }
        return false;
    }


    @Override
    public List<BusiCustomerRecommendRelationPojo> selectList(BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo) {
        busiCustomerRecommendRelationPojo.setDelFlag((short)0);
        return customerRecommendRelationInfoMapper.select(busiCustomerRecommendRelationPojo);
    }

    @Override
    public int selectCount(Integer customerId) {
        Map<String,Object> map = new HashMap<>();
        map.put("customerId",customerId);
        int count = customerRecommendRelationInfoMapper.selectCount(map);
        return count;
    }
}