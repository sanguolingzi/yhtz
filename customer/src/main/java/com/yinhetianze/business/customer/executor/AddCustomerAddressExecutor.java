package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerReceiveaddressModel;
import com.yinhetianze.business.customer.service.busi.CustomerReceiveaddressBusiService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 新增收获地址信息
 */

@Component
public class AddCustomerAddressExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerReceiveaddressBusiService customerReceiveaddressBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerReceiveaddressPojo  busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        BeanUtils.copyProperties(model,busiCustomerReceiveaddressPojo);
        int result = customerReceiveaddressBusiServiceImpl.addCustomerAddress(busiCustomerReceiveaddressPojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiCustomerReceiveaddressModel busiCustomerReceiveaddressModel = (BusiCustomerReceiveaddressModel)model;
        ErrorMessage errorMessage = new ErrorMessage();


        if(CommonUtil.isEmpty(busiCustomerReceiveaddressModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        /*
        if(CommonUtil.isEmpty(busiCustomerReceiveaddressModel.getCustomerId())){
            errorMessage.rejectNull("customerId",null,"用户id");
            return errorMessage;
        }



        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(tokenUser.getId().intValue() != busiCustomerReceiveaddressModel.getCustomerId().intValue()){
            LoggerUtil.error(UpdateCustomerInfoExecutor.class,"tokenId:"+tokenUser.getId()+".....param CustomerId:"+busiCustomerReceiveaddressModel.getCustomerId());
            errorMessage.rejectNull("info",null,"提交参数异常");
            return errorMessage;
        }
        */

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        busiCustomerReceiveaddressModel.setCustomerId(tokenUser.getId());


        if(CommonUtil.isEmpty(busiCustomerReceiveaddressModel.getPhone())){
            errorMessage.rejectNull("phone",null,"联系电话");
            return errorMessage;
        }


        if(!busiCustomerReceiveaddressModel.getPhone().matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("phone","BC0017","联系电话");
            return errorMessage;
        }

        errorMessage.rejectNull("address",busiCustomerReceiveaddressModel.getAddress(),"详细地址");
        errorMessage.rejectNull("city",busiCustomerReceiveaddressModel.getCity(),"所属城市");
        errorMessage.rejectNull("areaCounty",busiCustomerReceiveaddressModel.getAreaCounty(),"所属乡镇");
        errorMessage.rejectNull("regionLocation",busiCustomerReceiveaddressModel.getRegionLocation(),"所属州省");
        errorMessage.rejectNull("phone",busiCustomerReceiveaddressModel.getPhone(),"联系电话");

        return errorMessage;
    }
}
