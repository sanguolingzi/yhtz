package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiWechatModel;
import com.yinhetianze.business.customer.service.busi.CustomerWechatBusiService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 微信绑定登陆用户
 */

@Component
public class WechatCancleBindExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerWechatBusiService customerWechatBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiWechatModel busiWechatModel =(BusiWechatModel)model;
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("result","failed");

        //拿到token 获取当前登录用户信息
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiWechatModel.getToken());
        BusiCustomerWechatPojo busiCustomerWechatPojo =  customerWechatInfoServiceImpl.selectByCustomerId(tokenUser.getId(),busiWechatModel.getIdType());
        if(busiCustomerWechatPojo == null){
            param.put("code","1");
            return param;
        }

        BusiCustomerWechatPojo temp = new BusiCustomerWechatPojo();
        temp.setId(busiCustomerWechatPojo.getId());
        temp.setOpenIdHis(busiCustomerWechatPojo.getOpenId());
        temp.setIdTypeHis(busiCustomerWechatPojo.getIdType());
        int result = customerWechatBusiServiceImpl.cancleBind(temp);
        param.put("code",(result>0?"0":"1"));
        param.put("result","success");
        return param;

    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiWechatModel busiWechatModel =(BusiWechatModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(busiWechatModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiWechatModel.getIdType())){
            errorMessage.rejectNull("idType",null,"idType");
            return errorMessage;
        }else if(busiWechatModel.getIdType() != 1 && busiWechatModel.getIdType() != 2){
            errorMessage.rejectErrorMessage("idType","idType数据异常","idType数据异常");
            return errorMessage;
        }

        return null;
    }

}
