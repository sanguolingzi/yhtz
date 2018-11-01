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
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CancelExchangeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ExchangeBusiService exchangeBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ExchangeInfoService exchangeInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户未登录");
        }
        ExchangePojo exchangePojo=new ExchangePojo();
        BeanUtils.copyProperties(model, exchangePojo);
        exchangePojo.setExchangeStatus((short)1);
        //查询售后记录
        ExchangePojo exchangePojo1=exchangeInfoServiceImpl.findById(exchangePojo);
        if(CommonUtil.isEmpty(exchangePojo1)){
            throw new BusinessException("待审核的售后单才可以取消");
        }
        exchangePojo.setOrderNo(exchangePojo1.getOrderNo());
        if(exchangePojo1.getExchangeType()==1){
            exchangePojo.setOrderDetailId(exchangePojo1.getOrderDetailId());
        }
        exchangePojo.setExchangeStatus((short)0);
        int i=exchangeBusiServiceImpl.cancelExchange(exchangePojo,tokenUser.getId());
        if(i!=1){
            throw new BusinessException("取消失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        ExchangeModel exchangeModel=(ExchangeModel) model;
        if(CommonUtil.isEmpty(exchangeModel.getId())){
            errorMessage.rejectNull("id",null,"售后记录ID");
        }
        return errorMessage;
    }
}
