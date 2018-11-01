package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.product.model.ProductFresherRewardModel;
import com.yinhetianze.business.product.service.ProductFresherRewardInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@Component
public class GetProductFresherRewardExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ProductFresherRewardInfoService productFresherRewardInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductFresherRewardModel productFresherRewardModel=(ProductFresherRewardModel)model;
        Map map=new HashMap();
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(productFresherRewardModel.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户信息不存在");
        }
        BusiCustomerPojo busiCustomerPojo=new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isEmpty(busiCustomerPojo)){
            throw new BusinessException("获取账户信息失败");
        }else{
            if(CommonUtil.isEmpty(busiCustomerPojo.getGameId())){
                throw new BusinessException("账号无兑换资格");
            }else{
                ProductFresherRewardPojo productFresherRewardPojo=new ProductFresherRewardPojo();
                productFresherRewardPojo.setGameId(busiCustomerPojo.getGameId());
                productFresherRewardPojo=productFresherRewardInfoServiceImpl.selectOne(productFresherRewardPojo);
                if(CommonUtil.isEmpty(productFresherRewardPojo)){
                    throw new BusinessException("账号无兑换资格");
                }else{
                    if(productFresherRewardPojo.getHandleStatus()==1){
                        throw new BusinessException("账号无兑换资格");
                    }else{
                        if(productFresherRewardPojo.getStatus()==1){
                            throw new BusinessException("账号已兑换过");
                        }
                    }
                }
            }
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductFresherRewardModel productFresherRewardModel=(ProductFresherRewardModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(productFresherRewardModel.getToken())){
            errorMessage.rejectNull("token","null","token");
        }
        return errorMessage;
    }
}
