package com.yinhetianze.business.exchange.executor;

import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.business.exchange.service.ExchangeBusiService;
import com.yinhetianze.business.exchange.service.ExchangeInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.ExchangePojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class DeleteExchangeExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private ExchangeBusiService exchangeBusiServiceImpl;

    @Autowired
    private ExchangeInfoService exchangeInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ExchangePojo exchangePojo=new ExchangePojo();
        BeanUtils.copyProperties(model, exchangePojo);
        //查询售后记录
        ExchangePojo exchangePojo1=new ExchangePojo();
        exchangePojo1.setId(exchangePojo.getId());
        exchangePojo1=exchangeInfoServiceImpl.findById(exchangePojo1);
        if(CommonUtil.isEmpty(exchangePojo1)){
            throw new BusinessException("传入的ID有误");
        }
        exchangePojo.setDelFlag((short)1);
        int i=exchangeBusiServiceImpl.updateExchange(exchangePojo);
        if(i!=1){
            throw new BusinessException("删除售后单失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        ExchangeModel exchangeModel=(ExchangeModel) model;
        if(CommonUtil.isEmpty(exchangeModel.getId())){
            errorMessage.rejectNull("id",null,"退款记录ID");
        }
        return errorMessage;
    }
}
