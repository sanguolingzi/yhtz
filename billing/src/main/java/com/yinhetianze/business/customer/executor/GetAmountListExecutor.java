package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiAmountFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的市场 获取用户订单相关余额返还明细
 */

@Component
public class GetAmountListExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = (BusiCustomerBankrollFlowModel)model;

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerBankrollFlowModel.getToken());

        if(tokenUser == null){
            return null;
        }

        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(tokenUser.getId());

        if(busiCustomerBankrollFlowModel.getIsAll() == null){
            PageHelper.startPage(busiCustomerBankrollFlowModel.getCurrentPage(),busiCustomerBankrollFlowModel.getPageSize());
        }


        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("bankrollId",busiCustomerBankrollPojo.getId());

        List<Short> flowDescriptionList = new ArrayList();

        flowDescriptionList.add(BankrollInfoEnum.MEMBERGIVE.getValue());
        flowDescriptionList.add(BankrollInfoEnum.SPREADADD.getValue());
        flowDescriptionList.add(BankrollInfoEnum.SECONDSPREADADD.getValue());
        flowDescriptionList.add(BankrollInfoEnum.PARTNERGIVE.getValue());
        flowDescriptionList.add(BankrollInfoEnum.RECOMMENDAMOUNT.getValue());
        flowDescriptionList.add(BankrollInfoEnum.SECONDMEMBERGIVE.getValue());

        paraMap.put("flowDescriptionList",flowDescriptionList);

        List<BusiAmountFlowModel> list =   customerBankrollFlowInfoServiceImpl.selectPersonal(paraMap);
        PageInfo pageInfo = new PageInfo(list);
        PageData pageData = new PageData<>(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){



        return null;
    }

}
