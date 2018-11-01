package com.yinhetianze.business.shoppingcart.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.shoppingcart.model.ShopcartModel;
import com.yinhetianze.business.shoppingcart.service.ShopcartBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class RemoveShopcartExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopcartBusiService shopcartBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
            ShopcartModel shopcart = (ShopcartModel) model;
            TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
            if(CommonUtil.isEmpty(tokenUser)){
                throw new BusinessException("用户没有登录");
            }
            try {
                //用户信息
                BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
                busiCustomerPojo.setId(tokenUser.getId());
                busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
                if (CommonUtil.isEmpty(busiCustomerPojo)) {
                    throw new BusinessException("没有获取到用户信息");
                }
                //根据主键批量删除购物车
                shopcartBusiServiceImpl.deleteShopcart(shopcart.getShopcartIds());
            } catch (Exception e) {
                LoggerUtil.error(RemoveShopcartExecutor.class,e.getMessage());
                throw new BusinessException("删除购物车失败");
            }
            return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ShopcartModel shopcartModel=(ShopcartModel)model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(shopcartModel.getShopcartIds())){
            error.rejectNull("shopcartId",null,"被删除的购物车主键数组");
        }
        return error;
    }
}
