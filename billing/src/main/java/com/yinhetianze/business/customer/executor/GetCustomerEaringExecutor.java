package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.info.CustomerEarningsInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取我的市场 汇总收益
 */

@Component
public class GetCustomerEaringExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerEarningsInfoService customerEarningsInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectById(busiCustomerModel.getId());
        Map<String,Object> paraMap = new HashMap<>();
        if(busiCustomerPojo == null){
            return paraMap;
        }

        //一级市场
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("customerId",busiCustomerPojo.getId());
        dataMap.put("gameId",busiCustomerPojo.getGameId());
        dataMap.put("type",1);

        BigDecimal firstMarketEaring = customerEarningsInfoServiceImpl.selectTotalEaring(dataMap);



        //二级市场
        dataMap.put("type",2);
        BigDecimal secondMarketEaring = customerEarningsInfoServiceImpl.selectTotalEaring(dataMap);


        //若是合伙人 查询拓展市场
        BigDecimal otherMarketEaring = new BigDecimal("0");
        if(busiCustomerPojo.getIsPartner() == 0){
            dataMap.put("type",3);
            otherMarketEaring = customerEarningsInfoServiceImpl.selectTotalEaring(dataMap);
        }

        //总收益
        BigDecimal total = firstMarketEaring.add(secondMarketEaring).add(otherMarketEaring);

        //一级市场收益占比
        BigDecimal firstMarketPercent = new BigDecimal("0");
        //二级市场收益占比
        BigDecimal secondMarketPercent = new BigDecimal("0");
        //拓展市场收益占比
        BigDecimal otherMarketPercent = new BigDecimal("0");

        if(total.compareTo(new BigDecimal("0"))>0){
            firstMarketPercent = firstMarketEaring.divide(total,2,BigDecimal.ROUND_HALF_UP).movePointRight(2);
            secondMarketPercent = secondMarketEaring.divide(total,2,BigDecimal.ROUND_HALF_UP).movePointRight(2);
            otherMarketPercent = otherMarketEaring.divide(total,2,BigDecimal.ROUND_HALF_UP).movePointRight(2);
        }

        paraMap.put("total",total.toString());
        paraMap.put("isPartner",busiCustomerPojo.getIsPartner());
        paraMap.put("firstMarketEaring",firstMarketEaring.toString());
        paraMap.put("secondMarketEaring",secondMarketEaring.toString());
        paraMap.put("otherMarketEaring",otherMarketEaring.toString());
        paraMap.put("firstMarketPercent",firstMarketPercent.toString());
        paraMap.put("secondMarketPercent",secondMarketPercent.toString());
        paraMap.put("otherMarketPercent",otherMarketPercent.toString());

        return paraMap;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;
        if(CommonUtil.isEmpty(busiCustomerModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerModel.getToken());
        busiCustomerModel.setId(tokenUser.getId());
        return errorMessage;
    }

}
