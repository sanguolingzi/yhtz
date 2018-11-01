package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.mapper.info.CustomerDrawrecordInfoMapper;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerDrawrecordInfoService;
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
import java.util.List;

/**
 * 获取用户提现申请列表
 */

@Component
public class GetCustomerDrawRecordListExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerDrawrecordInfoService customerDrawrecordInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerDrawrecordModel busiCustomerDrawrecordModel = (BusiCustomerDrawrecordModel)model;

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerDrawrecordModel.getToken());
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(tokenUser.getId());

        if(busiCustomerDrawrecordModel.getIsAll() == null){
            PageHelper.startPage(busiCustomerDrawrecordModel.getCurrentPage(),busiCustomerDrawrecordModel.getPageSize());
        }


        busiCustomerDrawrecordModel.setBankrollId(busiCustomerBankrollPojo.getId());
        List<BusiCustomerDrawrecordModel> list = customerDrawrecordInfoServiceImpl.selectListByCustomer(busiCustomerDrawrecordModel);
        PageInfo pageInfo = new PageInfo(list);
        PageData pageData = new PageData<>(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        return null;
    }

}
