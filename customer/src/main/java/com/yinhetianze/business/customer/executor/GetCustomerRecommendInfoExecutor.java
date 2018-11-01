package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerRecommendRelationModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取推荐人 推荐信息
 */

@Component
public class GetCustomerRecommendInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerRecommendRelationModel busiCustomerRecommendRelationModel = (BusiCustomerRecommendRelationModel)model;
        if(busiCustomerRecommendRelationModel.getIsAll() == null){
            PageHelper.startPage(busiCustomerRecommendRelationModel.getCurrentPage(),busiCustomerRecommendRelationModel.getPageSize());
        }
        PageInfo pageInfo = new PageInfo(
                customerRecommendRelationInfoServiceImpl.
                        selectRecommendRelationList( busiCustomerRecommendRelationModel.getRecomGameId()));
        return pageInfo;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerRecommendRelationModel busiCustomerRecommendRelationModel = (BusiCustomerRecommendRelationModel)model;
        if(CommonUtil.isEmpty(busiCustomerRecommendRelationModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerRecommendRelationModel.getToken());
        busiCustomerRecommendRelationModel.setRecomCustomerId(tokenUser.getId());
        busiCustomerRecommendRelationModel.setRecomGameId(tokenUser.getGameId());

        return errorMessage;
    }

}
