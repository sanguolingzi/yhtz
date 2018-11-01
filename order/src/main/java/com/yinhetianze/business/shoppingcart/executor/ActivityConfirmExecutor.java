package com.yinhetianze.business.shoppingcart.executor;

import com.yinhetianze.business.activity.service.info.ActivityProductInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shoppingcart.model.ShopcartModel;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.product.ActivityProductPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActivityConfirmExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private ActivityProductInfoService activityProductInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> resultInfo=new HashMap<String,Object>();
        ShopcartModel shopcartModel=(ShopcartModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }
        //账户信息
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectBankrollInfo(tokenUser.getId());
        if (CommonUtil.isEmpty(busiCustomerBankrollPojo.getStarCoin())) {
            throw new BusinessException("没有获取到账户信息");
        }

        ActivityProductPojo activityProductPojo=new ActivityProductPojo();
        activityProductPojo.setId(shopcartModel.getId());
        activityProductPojo=activityProductInfoServiceImpl.selectOne(activityProductPojo);

        BusiShopPojo busiShopPojo = new BusiShopPojo();
        busiShopPojo.setId(activityProductPojo.getShopId());
        busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
        if(CommonUtil.isEmpty(busiShopPojo)){
            throw new BusinessException("没有相关店铺信息");
        }

        BigDecimal freight=activityProductPojo.getFreight();
        BigDecimal amount=activityProductPojo.getSellPrice().multiply(new BigDecimal(shopcartModel.getNumber()));
        BigDecimal totalAmount=amount.add(freight);

        resultInfo.put("starCoin",busiCustomerBankrollPojo.getStarCoin());
        resultInfo.put("rewardAmount",busiCustomerBankrollPojo.getRewardAmount());
        resultInfo.put("shopName",busiShopPojo.getShopName());
        resultInfo.put("shopLogo",busiShopPojo.getShopLogo());
        resultInfo.put("shopId",activityProductPojo.getShopId());
        resultInfo.put("prodId",activityProductPojo.getId());
        resultInfo.put("prodName",activityProductPojo.getProdName());
        resultInfo.put("prodImg",activityProductPojo.getProdImg());
        resultInfo.put("prodPic",activityProductPojo.getSellPrice());
        resultInfo.put("prodSpeci",activityProductPojo.getProdSpeci());
        resultInfo.put("uPrice",activityProductPojo.getuPrice());
        resultInfo.put("marketPrice",activityProductPojo.getMarketPrice());
        resultInfo.put("freight",freight);
        resultInfo.put("number",shopcartModel.getNumber());
        resultInfo.put("totalAmount",totalAmount);
        resultInfo.put("uRatio",sysPropertiesUtils.getDecimalValue("uRatio"));

        return resultInfo;
    }
}
