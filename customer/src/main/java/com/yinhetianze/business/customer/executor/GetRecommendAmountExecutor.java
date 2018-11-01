package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiRecommendAmountInfoModel;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 个人中心-奖励金
 */

@Component
public class GetRecommendAmountExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiRecommendAmountInfoModel busiRecommendAmountInfoModel = (BusiRecommendAmountInfoModel)model;


        /**
         * 获取用户推荐信息
         */
        /*
        BusiCustomerRecommendRelationModel busiCustomerRecommendRelationModel =
                new BusiCustomerRecommendRelationModel();
        busiCustomerRecommendRelationModel.setPageSize(10);
        busiCustomerRecommendRelationModel.setCurrentPage(1);
        PageHelper.startPage(busiCustomerRecommendRelationModel.getCurrentPage(),busiCustomerRecommendRelationModel.getPageSize());
        PageInfo<BusiCustomerRecommendRelationModel> pageInfo = new PageInfo(
                customerRecommendRelationInfoServiceImpl.
                        selectRecommendRelationList( busiRecommendAmountInfoModel.getCustomerId()));
        busiRecommendAmountInfoModel.setBusiCustomerRecommendRelationModel(pageInfo);


        /**
         * 获取用户奖励金
        ResponseData responseData = null;
        try{
            Map doGetMap = new HashMap();
            doGetMap.put("customerId",busiRecommendAmountInfoModel.getToken());

            String result = HttpClientUtil.doGet(accountingService+"/customer/bankroll",doGetMap,null);
            responseData = CommonUtil.readFromString(result, ResponseData.class);
            if(!responseData.getResultInfo().getCode().equals(ResponseConstant.RESULT_CODE_SUCCESS)){
                throw new Exception(accountingService+"/bankroll 业务调用失败!");
            }
            BusiCustomerBankrollPojo busiCustomerBankrollPojo = CommonUtil.refelctBean((Map)responseData.getData(), BusiCustomerBankrollPojo.class);
            busiRecommendAmountInfoModel.setAmount(busiCustomerBankrollPojo.getAmount().movePointLeft(2));
        }catch (Exception e){
            StringBuilder errorMessage = new StringBuilder(e.toString());
            errorMessage.append(" : ");
            errorMessage.append((responseData!=null&&responseData.getResultInfo()!=null)?responseData.getResultInfo().getMessage():"");
            LoggerUtil.error(GetCustomerCenterInfoExecutor.class,errorMessage.toString());
            throw new BusinessException();
        }
         */
        return busiRecommendAmountInfoModel;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiRecommendAmountInfoModel busiRecommendAmountInfoModel = (BusiRecommendAmountInfoModel)model;

        if(CommonUtil.isEmpty(busiRecommendAmountInfoModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiRecommendAmountInfoModel.getToken());
        busiRecommendAmountInfoModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }

}
