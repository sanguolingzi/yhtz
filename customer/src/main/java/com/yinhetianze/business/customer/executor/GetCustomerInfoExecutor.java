package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 获取基础信息
 */

@Component
public class GetCustomerInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;


    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;
        busiCustomerPojo.setId(busiCustomerModel.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(busiCustomerPojo != null){
            BeanUtils.copyProperties(busiCustomerPojo,busiCustomerModel);

            //TODO 需要兼容 app/ios 端
            String photoUrl = busiCustomerPojo.getPhotoUrl();
            if(CommonUtil.isEmpty(photoUrl)){
                BusiCustomerWechatPojo busiCustomerWechatPojo = customerWechatInfoServiceImpl.selectByCustomerId(busiCustomerModel.getId(),(short)1);
                if(busiCustomerWechatPojo!=null && busiCustomerWechatPojo.getHeadImgUrl() !=null){
                    photoUrl = busiCustomerWechatPojo.getHeadImgUrl();
                }
            }
            busiCustomerModel.setPhotoUrl(photoUrl);
            return busiCustomerModel;
        }
        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        if(CommonUtil.isEmpty(busiCustomerModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerModel.getToken());
        busiCustomerModel.setId(tokenUser.getId());
        return errorMessage;
    }

}
