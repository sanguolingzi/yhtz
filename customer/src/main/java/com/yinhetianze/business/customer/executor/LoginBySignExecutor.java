package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 自动登录 查询用户qrcodesecret
 */

@Component
public class LoginBySignExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel)model;

        Map<String,Object> paraMap = new HashMap<>();
        /*
        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByQrCodeSecret(busiRegeisterModel.getSecret());
        if(busiCustomerPojo == null) {
            paraMap.put("code","1");
            return paraMap;
        }
        BusiCustomerModel busiCustomerModel  = new BusiCustomerModel();
        busiCustomerModel.setCheckPassword(false);
        busiCustomerModel.setId(busiCustomerPojo.getId());
        Map<String,Object> userInfo = customerInfoServiceImpl.login(busiCustomerModel);

        paraMap.put("code","2");
        paraMap.put("data",userInfo);
        */
        return paraMap;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        return null;
    }
}
