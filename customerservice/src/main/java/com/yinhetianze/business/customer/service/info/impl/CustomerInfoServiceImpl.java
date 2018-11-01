package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerInfoMapper;
import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiCustomerOrderModel;
import com.yinhetianze.business.customer.model.BusiCustomerPageModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerUtil;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.core.utils.SecurityCode;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService
{
    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Override
    public BusiCustomerPojo selectOne(BusiCustomerPojo record) {
        record.setDelFlag((short)0);
        return customerInfoMapper.selectOne(record);
    }


    @Override
    public BusiCustomerPojo selectById(int id) {
        BusiCustomerPojo record = new BusiCustomerPojo();
        record.setId(id);
        return selectOne(record);
    }

    @Override
    public BusiCustomerPojo selectByPhone(String phone) {
        BusiCustomerPojo record = new BusiCustomerPojo();
        record.setPhone(phone);
        return selectOne(record);
    }

    @Override
    public BusiCustomerPojo selectByGameId(Integer gameId) {
        BusiCustomerPojo record = new BusiCustomerPojo();
        record.setGameId(gameId);
        return selectOne(record);
    }

    @Override
    public List<BusiCustomerOrderModel> selectList(BusiCustomerPageModel busiCustomerPageModel) {
        return customerInfoMapper.selectList(busiCustomerPageModel);
    }

    @Override
    public Map<String, Object> login(BusiCustomerModel busiCustomerModel) throws BusinessException{
        Map<String,Object> paraMap = new HashMap<String,Object>();
        BusiCustomerPojo busiCustomerPojo = null;

        if(CommonUtil.isNotEmpty(busiCustomerModel.getId())){
            busiCustomerPojo  = selectById(busiCustomerModel.getId());
        }else if(CommonUtil.isNotEmpty(busiCustomerModel.getPhone())){
            busiCustomerPojo  = selectByPhone(busiCustomerModel.getPhone());
        }


        if(busiCustomerPojo == null){
            throw new BusinessException("BC0007", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        if(busiCustomerPojo.getAccountStatus() == 1){
            throw new BusinessException("BC0035", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        //有登录密码才校验
        //微信扫码登录  用户是没有登录密码
        if(busiCustomerModel.getCheckPassword()){
            if(CommonUtil.isNotEmpty(busiCustomerPojo.getLoginPassword())){
                if(!CustomerUtil.checkPassword(busiCustomerPojo.getLoginPassword(),busiCustomerModel.getLoginPassword())){
                    throw new BusinessException("BC0007", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                }
            }else{
                throw new BusinessException("BC0007", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }
        //long token = System.nanoTime();
        String token = MD5CoderUtil.encode("yhtz"+String.valueOf(System.nanoTime())+ SecurityCode.getSecurityCode());
        //默认设置 角色为 USER 拥有访问所有链接的权限
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("USER");
        Map<String,Object> info=null;
        try{
            info = CommonUtil.readFromString(CommonUtil.objectToJsonString(busiCustomerPojo),Map.class);
        }catch (Exception e){
            LoggerUtil.error(CustomerInfoServiceImpl.class,e.getMessage());
        }
        TokenUser tokenUser = new TokenUser(
                busiCustomerPojo.getId(),
                busiCustomerPojo.getGameId(),
                busiCustomerPojo.getPhone(),
                busiCustomerPojo.getLoginPassword(),
                token,
                roleSet,
                info
                );
        redisUserDetails.saveUserDetails(tokenUser);

        BusiShopCompanyPojo busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectByCustomerId(busiCustomerPojo.getId());
        List<String> roleForFront = new ArrayList<>();
        roleForFront.add("1");//普通登录
        if(busiShopCompanyPojo != null){
            if(busiShopCompanyPojo.getAuditStatus() == 0){
                roleForFront.add("2"); //商家入驻已审核通过 允许进入商家中心
            }
        }

        //过滤一些信息 返回给前端存储
        paraMap.put("token",token);
        paraMap.put("hasGameId",(CommonUtil.isEmpty(busiCustomerPojo.getGameId())?"1":"0")); // 1未设置 0已设置
        paraMap.put("role",roleForFront);
        paraMap.put("phone",busiCustomerPojo.getPhone());
        paraMap.put("isMember",busiCustomerPojo.getIsMember());
        //TODO  RefreshUserTokenExecutor 中有同样的返回用户信息的代码 注意和login中保持一致 刷新token的同时会返回用户信息

        return paraMap;
    }

    @Override
    public Map getOneCustomer(BusiCustomerPojo busiCustomerPojo){
        return customerInfoMapper.getOneCustomer(busiCustomerPojo);
    }

    @Override
    public BusiCustomerPojo selectByRecommendCode(String recommendCode) {
        if("0".equalsIgnoreCase(recommendCode))
            return null;
        BusiCustomerPojo record = new BusiCustomerPojo();
        record.setDelFlag((short)0);
        record.setRecommendCode(recommendCode);
        return customerInfoMapper.selectOne(record);
    }

    @Override
    public BusiCustomerPojo selectByQrCodeSecret(String qrCodeSecret) {
        if(CommonUtil.isEmpty(qrCodeSecret))
            return null;
        BusiCustomerPojo record = new BusiCustomerPojo();
        record.setDelFlag((short)0);
        record.setQrcodeSecret(qrCodeSecret);
        return customerInfoMapper.selectOne(record);
    }

    @Override
    public List<BusiCustomerPojo> select(BusiCustomerPojo busiCustomerPojo) {
        return customerInfoMapper.select(busiCustomerPojo);
    }
}