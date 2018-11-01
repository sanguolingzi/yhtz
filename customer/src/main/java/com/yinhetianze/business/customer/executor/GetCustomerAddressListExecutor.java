package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerReceiveaddressModel;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消费者/会员 获取收获地址列表
 */

@Component
public class GetCustomerAddressListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerReceiveaddressModel busiCustomerReceiveaddressModel = (BusiCustomerReceiveaddressModel)model;

        if(busiCustomerReceiveaddressModel.getIsAll() == null){
            PageHelper.startPage(busiCustomerReceiveaddressModel.getCurrentPage(),busiCustomerReceiveaddressModel.getPageSize());
        }
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerReceiveaddressModel.getToken());
        List<BusiCustomerReceiveaddressPojo> list = customerReceiveaddressInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        PageInfo<BusiCustomerReceiveaddressPojo> pageInfo = new PageInfo(list);
        PageData<BusiCustomerReceiveaddressPojo> pageData =  new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerReceiveaddressModel busiCustomerReceiveaddressModel = (BusiCustomerReceiveaddressModel)model;
        if(CommonUtil.isEmpty(busiCustomerReceiveaddressModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return  errorMessage;
        }
        return errorMessage;
    }

}
