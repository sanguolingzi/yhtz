package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 消费者/会员 获取账户流水信息详情
 */

@Component
public class GetCustomerBankrollFlowDetailExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = (BusiCustomerBankrollFlowModel)model;
        BusiCustomerBankrollFlowPojo busiCustomerBankrollFlowPojo = new BusiCustomerBankrollFlowPojo();
        busiCustomerBankrollFlowPojo.setId(busiCustomerBankrollFlowModel.getId());



        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerBankrollFlowModel.getToken());
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(tokenUser.getId());

        if(CommonUtil.isEmpty(busiCustomerBankrollPojo)){
            throw new BusinessException("流水相关账户信息不存在!");
        }
        busiCustomerBankrollFlowPojo.setBankrollId(busiCustomerBankrollPojo.getId());
        busiCustomerBankrollFlowPojo=customerBankrollFlowInfoServiceImpl.selectOne(busiCustomerBankrollFlowPojo);
        if(busiCustomerBankrollFlowPojo == null)
            return null;
        busiCustomerBankrollFlowPojo.setAmount(busiCustomerBankrollFlowPojo.getAmount().movePointLeft(2));

        BeanUtils.copyProperties(busiCustomerBankrollFlowPojo,busiCustomerBankrollFlowModel);
        busiCustomerBankrollFlowModel.setStartTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(busiCustomerBankrollFlowPojo.getCreateTime()));
        return busiCustomerBankrollFlowModel;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();

        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = (BusiCustomerBankrollFlowModel)model;
        if(CommonUtil.isEmpty(busiCustomerBankrollFlowModel.getId())){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }


        if(CommonUtil.isEmpty(busiCustomerBankrollFlowModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        return errorMessage;
    }

}
