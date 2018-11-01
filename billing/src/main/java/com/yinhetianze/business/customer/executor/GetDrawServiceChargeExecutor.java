package com.yinhetianze.business.customer.executor;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 获取提现手续费数值
 */

@Component
public class GetDrawServiceChargeExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> paraMap = new HashMap<>();
        BigDecimal serviceCharge  =sysPropertiesUtils.getDecimalValue("serviceCharge",new BigDecimal("3"));
        paraMap.put("serviceCharge",serviceCharge.toString());
        return paraMap;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        return null;
    }

}
