package com.yinhetianze.business.shoppingcart.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
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
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModifyShopcartExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopcartBusiService shopcartBusiServiceImpl;

    @Autowired
    private ShopcartInfoService shopcartInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
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
        int number=0;
        //校验购物车ID
        for(int i=0;i<shopcartModel.getShopcartList().size();i++){
            Map<String,Object> m=shopcartInfoServiceImpl.findShopcartById(Integer.valueOf(shopcartModel.getShopcartList().get(i).get("id")+""));
            if(CommonUtil.isEmpty(m)){
                throw new BusinessException("购物车id为"+shopcartModel.getShopcartList().get(i).get("id")+"的购物车信息不存在");
            }
            //校验库存
            Map<String, Object> detailParam = new HashMap<>();
            detailParam.put("prodId",m.get("prodId"));
            detailParam.put("skuCode",m.get("prodSku"));
            detailParam.put("delFlag", 0); // 没有删除的标记
            detailParam.put("status", 0); // 有效状态1
            // 商品详情信息单品列表
            List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
            if(CommonUtil.isEmpty(detailList)){
                throw new BusinessException("商品规格信息不存在");
            }
            int count=Integer.valueOf(shopcartModel.getShopcartList().get(i).get("number")+"");
            if(count>Integer.valueOf(detailList.get(0).get("storage")+"")){
                throw new BusinessException("库存不足");
            }
            //number=number+Integer.valueOf(shopcartModel.getShopcartList().get(i).get("number")+"");
        }

        //校验购物车商品数量
           /* if(CommonUtil.isNotEmpty(shopcartInfoServiceImpl.countNumber(tokenUser.getId(),shopcartModel.getShopcartList()))){
                int countProd=shopcartInfoServiceImpl.countNumber(tokenUser.getId(),shopcartModel.getShopcartList());
                if(countProd+number>100){
                    throw new BusinessException("购物车商品数量不能大于100");
                }
            }*/
        //修改购物车信息
        try {
            shopcartBusiServiceImpl.modifyShopcart(shopcartModel.getShopcartList());
        }catch (Exception e){
            LoggerUtil.error(ModifyShopcartExecutor.class,e.getMessage());
            throw new BusinessException("修改购物车失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ShopcartModel shopcartModel=(ShopcartModel)model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(shopcartModel.getShopcartList())){
            error.rejectNull("shopcartList",null,"修改的商品信息");
        }else{
            for(int i=0;i<shopcartModel.getShopcartList().size();i++){
                if(CommonUtil.isEmpty(shopcartModel.getShopcartList().get(i).get("id"))){
                    error.rejectNull("shopcartList",null,"购物车ID");
                }
                if(CommonUtil.isEmpty(shopcartModel.getShopcartList().get(i).get("number"))){
                    error.rejectNull("number",null,"商品数量");
                }else{
                    if(Integer.valueOf(shopcartModel.getShopcartList().get(i).get("number")+"")<1){
                        error.rejectError("number","BC0031","","商品");
                    }
                }
            }
        }
        return error;
    }
}
