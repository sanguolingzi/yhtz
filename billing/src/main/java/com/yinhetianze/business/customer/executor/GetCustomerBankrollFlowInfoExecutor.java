package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 获取账户流水信息
 */

@Component
public class GetCustomerBankrollFlowInfoExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = (BusiCustomerBankrollFlowModel)model;
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl
                .selectByCustomerId(busiCustomerBankrollFlowModel.getCustomerId());
        busiCustomerBankrollFlowModel.setBankrollId(busiCustomerBankrollPojo.getId());
        if(busiCustomerBankrollFlowModel.getIsAll() == null) {
            PageHelper.startPage(busiCustomerBankrollFlowModel.getCurrentPage(),busiCustomerBankrollFlowModel.getPageSize());
        }
        PageInfo pageInfo = new PageInfo(customerBankrollFlowInfoServiceImpl.selectList(busiCustomerBankrollFlowModel));
        PageData pageData = new PageData<>(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();

        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = (BusiCustomerBankrollFlowModel)model;
        if(CommonUtil.isEmpty(busiCustomerBankrollFlowModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerBankrollFlowModel.getToken());
        busiCustomerBankrollFlowModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }

}
