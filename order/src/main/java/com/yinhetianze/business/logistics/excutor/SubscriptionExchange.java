package com.yinhetianze.business.logistics.excutor;

import com.yinhetianze.business.logistics.service.busi.LogisticsInformationBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.order.LogisticsInformationPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 快递鸟推送信息接收接口

 */
@Service
public class SubscriptionExchange extends AbstractRestBusiExecutor{

    @Autowired
    private LogisticsInformationBusiService logisticsInformationBusiServiceImpl;

    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        try {
            LogisticsInformationPojo logisticsInformationPojo = new LogisticsInformationPojo();
            BeanUtils.copyProperties(model,logisticsInformationPojo);
            int result = logisticsInformationBusiServiceImpl.logisticsInformation(logisticsInformationPojo);
            if(result <= 0)
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
