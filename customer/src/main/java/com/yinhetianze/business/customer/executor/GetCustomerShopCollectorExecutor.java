package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.service.info.CustomerCollectorInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 PC获取店铺收藏信息
 */

@Component
public class GetCustomerShopCollectorExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerCollectorInfoService customerCollectorInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;BusiCustomerReceiveaddressPojo  busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        Integer count = customerCollectorInfoServiceImpl.selectCount(busiCustomerCollectorModel);
        Map<String,Object> m = new HashMap();
        m.put("count",count);
        return m;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;BusiCustomerReceiveaddressPojo  busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();

        if(CommonUtil.isEmpty(busiCustomerCollectorModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return  errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerCollectorModel.getToken());
        //这个接口会不做token校验 所以token可能为空 这里做一下处理
        busiCustomerCollectorModel.setCustomerId(tokenUser==null?-1:tokenUser.getId());
        return errorMessage;
    }

}
