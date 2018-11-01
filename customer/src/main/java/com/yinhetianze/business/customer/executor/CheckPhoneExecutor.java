package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 校验号码是否已注册  1 未注册  0 已注册
 */

@Component
public class CheckPhoneExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel)model;
        Map<String,Object> map = new HashMap();
        if(customerInfoServiceImpl.selectByPhone(busiRegeisterModel.getPhone()) == null){
            map.put("state","1");
            return map;
        }
        map.put("state","0");
        return map;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel)model;

        if(CommonUtil.isEmpty(busiRegeisterModel.getPhone())){
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.rejectNull("phone",null,"phone");
            return errorMessage;
        }
        return null;
    }
}
