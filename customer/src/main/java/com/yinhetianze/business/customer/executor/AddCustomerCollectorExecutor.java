package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.service.busi.CustomerCollectorBusiService;
import com.yinhetianze.business.customer.service.info.CustomerCollectorInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerCollectorPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 新增收藏
 */

@Component
public class AddCustomerCollectorExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerCollectorBusiService customerCollectorBusiServiceImpl;

    @Autowired
    private CustomerCollectorInfoService customerCollectorInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerCollectorPojo busiCustomerCollectorPojo = new BusiCustomerCollectorPojo();
        BeanUtils.copyProperties(model,busiCustomerCollectorPojo);
        int result = customerCollectorBusiServiceImpl.addInfo(busiCustomerCollectorPojo);
        if(result <= 0){
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        Map<String,Object> m = new HashMap<String,Object>();
        m.put("id",busiCustomerCollectorPojo.getId());
        return m;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;
        ErrorMessage errorMessage = new ErrorMessage();


        if(CommonUtil.isEmpty(busiCustomerCollectorModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }


        if(CommonUtil.isEmpty(busiCustomerCollectorModel.getcType())){
            errorMessage.rejectNull("cType",null,"收藏类型");
            return errorMessage;
        }


        if(CommonUtil.isEmpty(busiCustomerCollectorModel.getRelationId())){
            errorMessage.rejectNull("relationId",null,"收藏信息id");
            return errorMessage;
        }


        BusiCustomerCollectorPojo busiCustomerCollectorPojo = new BusiCustomerCollectorPojo();

        busiCustomerCollectorPojo.setRelationId(busiCustomerCollectorModel.getRelationId());
        busiCustomerCollectorPojo.setcType(busiCustomerCollectorModel.getcType());
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        busiCustomerCollectorPojo.setCustomerId(tokenUser.getId());

        busiCustomerCollectorPojo = customerCollectorInfoServiceImpl.selectOne(busiCustomerCollectorPojo);
        if(busiCustomerCollectorPojo != null){
            errorMessage.rejectErrorMessage("info2","收藏信息已存在","收藏信息已存在");
            return errorMessage;
        }
        busiCustomerCollectorModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }
}
