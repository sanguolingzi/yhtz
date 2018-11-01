package com.yinhetianze.business.shoppingcart.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shoppingcart.model.ShopcartModel;
import com.yinhetianze.business.shoppingcart.service.ShopcartBusiService;
import com.yinhetianze.business.shoppingcart.service.ShopcartInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddShopcartExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopcartBusiService shopcartBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private ShopcartInfoService shopcartInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopcartModel shopcartModel=(ShopcartModel)model;
        Map<String,Object> map=new HashMap<String,Object>();
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        Map<String,Object> shoppingcart=new HashMap<>();

        //用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }

        //BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        //查询商品信息
        ProductPojo prodPojo = new ProductPojo();
        prodPojo.setId(shopcartModel.getProductId());
        prodPojo = productInfoServiceImpl.findProduct(prodPojo);
        if (CommonUtil.isEmpty(prodPojo)){
            throw new BusinessException("商品信息不存在");
        }
        //校验 商家不能买自己店铺的商品
        /*if(CommonUtil.isNotEmpty(shopPojo)){
             if(shopPojo.getId()==prodPojo.getShopId()){
                 throw new BusinessException("商家不能购买买自己店铺的商品");
             }
        }*/

        Map<String, Object> detailParam = new HashMap<>();
        detailParam.put("prodId",shopcartModel.getProductId());
        detailParam.put("skuCode",shopcartModel.getSkuCode());
        detailParam.put("delFlag", 0); // 没有删除的标记
        detailParam.put("status", 0); // 有效状态1
        // 商品详情信息单品列表
        List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
        if(CommonUtil.isEmpty(detailList)){
            throw new BusinessException("商品规格信息不存在");
        }

        //校验购物车数量
        /*int countProd=0;
        if(CommonUtil.isNotEmpty(shopcartInfoServiceImpl.countNumberByCustomerId(tokenUser.getId()))){
            countProd=shopcartInfoServiceImpl.countNumberByCustomerId(tokenUser.getId());
        }
        if(CommonUtil.isEmpty(shopcartModel.getNumber())){
           if(countProd>99){
               throw new BusinessException("购物车商品数量不能大于100");
           }
        }else{
            if(countProd+shopcartModel.getNumber()>100){
                throw new BusinessException("购物车商品数量不能大于100");
            }
        }*/
        //购买数量不能大于库存量
        if(shopcartModel.getNumber()>Integer.valueOf(detailList.get(0).get("storage")+"")){
            throw new BusinessException("商品库存不足");
        }
        try {
            //根据商品ID和用户ID查询购物车信息
            //存在记录表示已经添加过，不存在表示没添加
            int i=0;
            shoppingcart=shopcartInfoServiceImpl.findShopcart(tokenUser.getId(),shopcartModel.getProductId(),shopcartModel.getSkuCode());
            if(CommonUtil.isEmpty(shoppingcart)){
                if(CommonUtil.isEmpty(shopcartModel.getNumber())){
                    map.put("number",1);
                }else{
                    map.put("number",shopcartModel.getNumber());
                }
                map.put("shopId",prodPojo.getShopId());
                map.put("prodId",shopcartModel.getProductId());
                map.put("prodSku",shopcartModel.getSkuCode());
                map.put("customerId",tokenUser.getId());
                if(prodPojo.getDropShipping()==1){
                    map.put("proxyShopId",prodPojo.getDropShippingId());
                }
                i=shopcartBusiServiceImpl.addShopcart(map);
            }else{
                if(CommonUtil.isEmpty(shopcartModel.getNumber())){
                    map.put("number",Integer.valueOf(shoppingcart.get("number")+"")+1);
                }else{
                    map.put("number",shopcartModel.getNumber()+Integer.valueOf(shoppingcart.get("number")+""));
                }
                map.put("id",shoppingcart.get("id"));
                i=shopcartBusiServiceImpl.updateById(map);
            }
            if(i!=1){
                throw new BusinessException("添加购物车异常");
            }
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                shoppingcart=shopcartInfoServiceImpl.findShopcart(tokenUser.getId(),shopcartModel.getProductId(),shopcartModel.getSkuCode());
                map.put("number",Integer.valueOf(shoppingcart.get("number")+"")+1);
                map.put("id",shoppingcart.get("id"));
                shopcartBusiServiceImpl.updateById(map);
            }else{
                LoggerUtil.error(AddShopcartExecutor.class,e.getMessage());
                throw new BusinessException("添加到购物车失败");
            }
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ShopcartModel shopcartModel=(ShopcartModel)model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(shopcartModel.getProductId())){
            error.rejectNull("prodId",null,"商品ID");
        }
        if(CommonUtil.isEmpty(shopcartModel.getSkuCode())){
            error.rejectNull("skuCode",null,"商品sku");
        }
        return error;
    }
}
