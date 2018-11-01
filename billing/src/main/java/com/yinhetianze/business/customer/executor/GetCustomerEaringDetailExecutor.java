package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerEaringModel;
import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.ParaCustomerModel;
import com.yinhetianze.business.customer.service.info.CustomerEarningsInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取我的市场 指定用户收益详情
 *
 */

@Component
public class GetCustomerEaringDetailExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerEarningsInfoService customerEarningsInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerEaringModel busiCustomerEaringModel = (BusiCustomerEaringModel)model;
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerEaringModel.getToken());
        if(tokenUser == null)
            return null;
        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectById(tokenUser.getId());
        ParaCustomerModel paraModel = new ParaCustomerModel();
        //Map<String,Object> paraMap = new HashMap<>();
        if(busiCustomerPojo == null){
            return paraModel;
        }


        Integer gameId = busiCustomerEaringModel.getGameId();
        BusiCustomerPojo createUser = customerInfoServiceImpl.selectByGameId(gameId);

        BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo =
                customerRecommendRelationInfoServiceImpl.selectRecommendUser(gameId);


        paraModel.setRegisterTime(busiCustomerRecommendRelationPojo!=null? DateFormatUtils.format(busiCustomerRecommendRelationPojo.getCreateTime(),"yyyy-MM-dd HH:mm:ss")
                :"");
        paraModel.setShowId(busiCustomerRecommendRelationPojo!=null?busiCustomerRecommendRelationPojo.getShowId():null);

        //查看的用户 是否是 当前登录人的 一级推荐人
        //也就是在createUser的推荐关系中 RecomCustomerId = busiCustomerPojo.getId
        if(busiCustomerRecommendRelationPojo != null){
            if(busiCustomerRecommendRelationPojo.getRecomGameId().intValue() ==busiCustomerPojo.getGameId().intValue()){
                int recommendCount = customerRecommendRelationInfoServiceImpl.selectCount(gameId);
                //推荐人数
                paraModel.setRecommendCount(recommendCount);
                paraModel.setMarketLevel(1);
                /*paraMap.put("recommendCount",recommendCount);
                paraMap.put("marketLevel","1");//一级市场*/
            }else if(busiCustomerRecommendRelationPojo.getGrandRecomGameId()!=null
                    && busiCustomerRecommendRelationPojo.getGrandRecomGameId().intValue() == busiCustomerPojo.getGameId().intValue()){
                //paraMap.put("marketLevel","2");//二级市场
                paraModel.setMarketLevel(2);
            }else{
                paraModel.setMarketLevel(3);
                //paraMap.put("marketLevel","3");//拓展市场
            }
        }
        if(createUser == null){
            return paraModel;
        }


        paraModel.setPhone(createUser.getPhone());
        paraModel.setPhotoUrl(createUser.getPhotoUrl());
        /*paraMap.put("phone",createUser.getPhone());
        paraMap.put("photoUrl",createUser.getPhotoUrl());
        paraMap.put("registerTime", DateFormatUtils.format(createUser.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));*/
        //paraMap.put("recommendCode",createUser.getRecommendCode());


        Integer createId = createUser.getId();
        //总贡献值

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("customerId",busiCustomerPojo.getId());
        dataMap.put("createId",createId);
        BigDecimal totalEaring = customerEarningsInfoServiceImpl.selectEaringByCondition(dataMap);
        //paraMap.put("totalEaring",totalEaring);
        paraModel.setTotalEaring(totalEaring);

        //上月贡献值
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        String beginTime = lastMonth.with(TemporalAdjusters.firstDayOfMonth()).toString();
        String endTime = lastMonth.with(TemporalAdjusters.lastDayOfMonth()).toString();
        dataMap.put("beginTime",beginTime);
        dataMap.put("endTime",endTime);

        BigDecimal lastMonthEaring = customerEarningsInfoServiceImpl.selectEaringByCondition(dataMap);
        //paraMap.put("lastMonthEaring",lastMonthEaring);
        paraModel.setLastMonthEaring(lastMonthEaring);
        return paraModel;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerEaringModel busiCustomerEaringModel = (BusiCustomerEaringModel)model;
        if(CommonUtil.isEmpty(busiCustomerEaringModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerEaringModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"gameId");
            return errorMessage;
        }
        return errorMessage;
    }
}
