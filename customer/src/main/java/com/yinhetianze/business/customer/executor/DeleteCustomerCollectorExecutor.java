package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.service.busi.CustomerCollectorBusiService;
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
 * 删除 消费者/会员 收藏记录
 */

@Component
public class DeleteCustomerCollectorExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerCollectorBusiService customerCollectorBusiServiceImpl;

    @Autowired
    private CustomerCollectorInfoService customerCollectorInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;BusiCustomerReceiveaddressPojo  busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        BusiCustomerCollectorPojo busiCustomerCollectorPojo = new BusiCustomerCollectorPojo();
        busiCustomerCollectorPojo.setId(busiCustomerCollectorModel.getId());
        busiCustomerCollectorPojo.setCustomerId(busiCustomerCollectorModel.getCustomerId());
        int result = customerCollectorBusiServiceImpl.deleteInfo(busiCustomerCollectorPojo);
        //if(result <= 0)
        //    throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiCustomerCollectorModel.getId())){
            errorMessage.rejectNull("id",null,"收藏记录id");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerCollectorModel.getToken());
        BusiCustomerCollectorPojo busiCustomerCollectorPojo = new BusiCustomerCollectorPojo();
        busiCustomerCollectorPojo.setId(busiCustomerCollectorModel.getId());
        busiCustomerCollectorPojo = customerCollectorInfoServiceImpl.selectOne(busiCustomerCollectorPojo);
        if(busiCustomerCollectorPojo == null
                || busiCustomerCollectorPojo.getCustomerId().intValue() != tokenUser.getId().intValue()){
            errorMessage.rejectErrorMessage("info","收藏记录信息异常","收藏记录信息异常");
            return errorMessage;
        }
        busiCustomerCollectorModel.setCustomerId(busiCustomerCollectorPojo.getCustomerId());
        return errorMessage;
    }
}
