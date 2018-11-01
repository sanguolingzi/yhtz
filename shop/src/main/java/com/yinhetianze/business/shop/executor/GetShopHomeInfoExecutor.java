package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.service.info.CustomerCollectorInfoService;
import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.business.exchange.service.ExchangeInfoService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.shop.model.BusiShopHomeModel;
import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家中心-查询店铺首页数据
 */

@Component
public class GetShopHomeInfoExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private ExchangeInfoService exchangeInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerCollectorInfoService customerCollectorInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());

        BusiShopPojo busiShopPojo =  shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());

        BusiShopHomeModel busiShopHomeModel = new BusiShopHomeModel();
        if(busiShopPojo != null){
            busiShopHomeModel.setShopLogo(busiShopPojo.getShopLogo());
            busiShopHomeModel.setVisitorCount(busiShopPojo.getVisitorCount());
            busiShopHomeModel.setShopQrcode(busiShopPojo.getShopQrcode());
            busiShopHomeModel.setAuditStatus(busiShopPojo.getAuditStatus());
            busiShopHomeModel.setShopPhone(busiShopPojo.getShopPhone());
            busiShopHomeModel.setId(busiShopPojo.getId());
            busiShopHomeModel.setShopName(busiShopPojo.getShopName());

            BusiCustomerCollectorModel busiCustomerCollectorModel = new BusiCustomerCollectorModel();
            busiCustomerCollectorModel.setcType((short)1);
            busiCustomerCollectorModel.setRelationId(busiShopPojo.getId());
            Integer shopCollectCount = customerCollectorInfoServiceImpl.selectCount(busiCustomerCollectorModel);
            busiShopHomeModel.setShopCollectCount(shopCollectCount);

            Map<String,Object> param = new HashMap<String,Object>();
            param.put("shopId",busiShopPojo.getId());
            //查询已付款订单
            param.put("orderStatus","2");
            List list = orderInfoServiceImpl.findOrder(param);
            int newOrders = 0;
            if(list!=null)
                newOrders=list.size();

            busiShopHomeModel.setNewOrders(newOrders);

            ExchangeModel exchangeModel = new ExchangeModel();
            exchangeModel.setShopId(busiShopPojo.getId());
            list = exchangeInfoServiceImpl.findExchange(exchangeModel);
            int cancelOrder = 0;
            if(list!=null)
                cancelOrder=list.size();

            busiShopHomeModel.setCancelOrders(cancelOrder);
        }

        return busiShopHomeModel;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiShopModel busiShopModel = (BusiShopModel)model;
        if(CommonUtil.isEmpty(busiShopModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        return errorMessage;
    }
}
