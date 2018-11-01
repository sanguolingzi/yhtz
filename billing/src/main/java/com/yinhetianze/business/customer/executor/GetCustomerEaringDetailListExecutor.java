package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiAmountFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
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
 * 获取我的市场 查询指定用户产生的收益列表
 */

@Component
public class GetCustomerEaringDetailListExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = (BusiCustomerBankrollFlowModel)model;
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerBankrollFlowModel.getToken());
        if(tokenUser == null)
            return null;

        BusiCustomerBankrollPojo busiCustomerBankrollPojo =
                customerBankrollInfoServiceImpl.selectByCustomerId(tokenUser.getId());

        PageHelper.startPage(busiCustomerBankrollFlowModel.getCurrentPage(),busiCustomerBankrollFlowModel.getPageSize());

        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByGameId(busiCustomerBankrollFlowModel.getGameId());
        List<BusiAmountFlowModel> list = new ArrayList<>();
        if(busiCustomerPojo != null){
            Map<String,Object> paraMap = new HashMap<>();
            paraMap.put("bankrollId",busiCustomerBankrollPojo.getId());
            paraMap.put("createId",busiCustomerPojo.getId());
            list =   customerBankrollFlowInfoServiceImpl.selectPersonal(paraMap);
            PageInfo<BusiAmountFlowModel> pageInfo = new PageInfo<>(list);
            PageData<BusiAmountFlowModel> pageData = new PageData<BusiAmountFlowModel>(pageInfo.getList(),pageInfo.getTotal());
            return pageData;
        }
        return new PageData<BusiAmountFlowModel>(list,0L);
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = (BusiCustomerBankrollFlowModel)model;
        if(CommonUtil.isEmpty(busiCustomerBankrollFlowModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerBankrollFlowModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"gameId");
            return errorMessage;
        }
        return errorMessage;
    }

}
