package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiCustomerOrderModel;
import com.yinhetianze.business.customer.model.BusiCustomerPageModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;

import java.util.List;

import java.util.Map;

public interface CustomerInfoService
{
     BusiCustomerPojo selectOne(BusiCustomerPojo record);

     BusiCustomerPojo selectByPhone(String phone);

     BusiCustomerPojo selectByGameId(Integer gameId);

     BusiCustomerPojo selectByRecommendCode(String recommendCode);

     BusiCustomerPojo selectByQrCodeSecret(String qrCodeSecret);

     public BusiCustomerPojo selectById(int id);

     List<BusiCustomerOrderModel>  selectList(BusiCustomerPageModel busiCustomerPageModel);

     Map<String,Object> login(BusiCustomerModel busiCustomerModel) throws BusinessException;

     /**
      * 第三方查询用户信息接口
      */
     Map getOneCustomer(BusiCustomerPojo record);

     List<BusiCustomerPojo> select(BusiCustomerPojo busiCustomerPojo);
}