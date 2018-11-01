package com.yinhetianze.business.customer.executor;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerStarCoinInfoModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 消费者/会员 今日摘星 页面
 */

@Component
public class GetCustomerStarCoinInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerStarCoinInfoModel busiCustomerStarCoinInfoModel = (BusiCustomerStarCoinInfoModel)model;
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.
                selectByCustomerId(busiCustomerStarCoinInfoModel.getCustomerId());

        //获取 总积分
        busiCustomerStarCoinInfoModel.setIntegral(busiCustomerBankrollPojo.getIntegral().movePointLeft(2)
                .setScale(2, BigDecimal.ROUND_HALF_UP));
        //获取 星币总数
        busiCustomerStarCoinInfoModel.setStarCoin(busiCustomerBankrollPojo.getStarCoin().movePointLeft(2)
                .setScale(2, BigDecimal.ROUND_HALF_UP));


        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
        busiCustomerBankrollFlowModel.setBankrollId(busiCustomerBankrollPojo.getId());
        busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.INTEGRALTOSTARCOIN.getValue());
        busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.INTEGRAL.getValue());

        //获取当前所在星期 的 周一  和 周日  做数据查询
        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        int curr = dayOfWeek.getValue();

        LocalDateTime firstDayOfWeek=localDateTime.minus((curr-1),ChronoUnit.DAYS);

        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        busiCustomerBankrollFlowModel.setStartTime(firstDayOfWeek.format(dateTimeFormatter));
        busiCustomerBankrollFlowModel.setEndTime(localDateTime.plus(1,ChronoUnit.DAYS).format(dateTimeFormatter));
        //获取 当前所在星期的摘星情况
        List<BusiCustomerBankrollFlowModel> busiCustomerBankrollFlowModelList = customerBankrollFlowInfoServiceImpl
                .selectList(busiCustomerBankrollFlowModel);

        JSONObject jsonObject = new JSONObject();
        //查询结果根据时间倒序排 这里要倒序遍历
        for(int i=busiCustomerBankrollFlowModelList.size()-1;i>=0;i--){
            //换算日期 将日期 换算成 对应星期
            BusiCustomerBankrollFlowModel temp = busiCustomerBankrollFlowModelList.get(i);
            int week = LocalDateTime.parse(temp.getStartTime(),dateTimeFormatter).getDayOfWeek().getValue();

            JSONObject info = new JSONObject();
            info.put("value",temp.getAmount());
            info.put("curr",(curr==week?0:1));
            jsonObject.put(String.valueOf(week),info);
        }
        busiCustomerStarCoinInfoModel.setJsonObject(jsonObject.toJSONString());
        return busiCustomerStarCoinInfoModel;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        BusiCustomerStarCoinInfoModel busiCustomerStarCoinInfoModel = (BusiCustomerStarCoinInfoModel)model;
        ErrorMessage errorMessage  = new ErrorMessage();

        if(CommonUtil.isEmpty(busiCustomerStarCoinInfoModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerStarCoinInfoModel.getToken());
        busiCustomerStarCoinInfoModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }
}
