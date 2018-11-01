package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.service.info.CustomerCollectorInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerCollectorPojo;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 获取收藏记录
 */

@Component
public class GetCustomerCollectorExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerCollectorInfoService customerCollectorInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;BusiCustomerReceiveaddressPojo  busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        BusiCustomerCollectorPojo busiCustomerCollectorPojo = new BusiCustomerCollectorPojo();
        busiCustomerCollectorPojo.setCustomerId(busiCustomerCollectorModel.getCustomerId());
        busiCustomerCollectorPojo.setRelationId(busiCustomerCollectorModel.getRelationId());
        busiCustomerCollectorPojo = customerCollectorInfoServiceImpl.selectOne(busiCustomerCollectorPojo);

        if(busiCustomerCollectorPojo!=null){
            busiCustomerCollectorModel.setId(busiCustomerCollectorPojo.getId());
            busiCustomerCollectorModel.setCustomerId(null);
            return busiCustomerCollectorModel;
        }
        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;BusiCustomerReceiveaddressPojo  busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();

        if(CommonUtil.isEmpty(busiCustomerCollectorModel.getRelationId())){
            errorMessage.rejectNull("relationId",null,"关联数据id");
            return  errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerCollectorModel.getToken());
        //这个接口会不做token校验 所以token可能为空 这里做一下处理
        busiCustomerCollectorModel.setCustomerId(tokenUser==null?-1:tokenUser.getId());
        return errorMessage;
    }

}
