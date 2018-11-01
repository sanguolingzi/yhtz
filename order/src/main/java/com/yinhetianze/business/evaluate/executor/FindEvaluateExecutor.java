package com.yinhetianze.business.evaluate.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.evaluate.service.EvaluateInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.util.Util;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.order.EvaluatePojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindEvaluateExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private EvaluateInfoService evaluateInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        EvaluateModel evaluateModel=(EvaluateModel)model;
        Map<String, Object> result=new HashMap<String, Object>();
        List<Map<String,Object>> evaluateLists=new ArrayList<>();
        //查询评论
        if(evaluateModel.getCurrentPage()==0){
            evaluateModel.setCurrentPage(1);
            evaluateModel.setPageSize(10);
        }
        Map<String,Object> parameter=new HashMap<>();

        try {
            if(CommonUtil.isNotEmpty(evaluateModel.getIsShop())){
                TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
                //校验商家信息
                BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
                if(CommonUtil.isEmpty(shopPojo)){
                    throw new BusinessException("该用户不是商家");
                }
                //pc端商户查询评论
                parameter.put("shopId",shopPojo.getId());
                if(CommonUtil.isNotEmpty(evaluateModel.getProductId())){
                    parameter.put("productId",evaluateModel.getProductId());
                }
                if(CommonUtil.isNotEmpty(evaluateModel.getProductStar())){
                    parameter.put("productStar",evaluateModel.getProductStar());
                }
                if(CommonUtil.isNotEmpty(evaluateModel.getCreateTimeSort())){
                    parameter.put("createTimeSort",evaluateModel.getCreateTimeSort());
                }
                if(CommonUtil.isNotEmpty(evaluateModel.getOrderNo())){
                    parameter.put("orderNo",evaluateModel.getOrderNo());
                }
            }else if(CommonUtil.isEmpty(evaluateModel.getShopId())&&CommonUtil.isNotEmpty(evaluateModel.getProductId())){
                //手机端商品查评论
                parameter.put("productId",evaluateModel.getProductId());
            }else{
                //pc端查询我的评论
                TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
                if(CommonUtil.isEmpty(tokenUser)){
                    throw new BusinessException("该用户没有登录");
                }
                parameter.put("evaluateUser",tokenUser.getId());
                parameter.put("orderNo",evaluateModel.getOrderNo());
            }
            parameter.put("addEvaluate",0);
            parameter.put("isShow",1);
            PageHelper.startPage(evaluateModel.getCurrentPage(),evaluateModel.getPageSize());
            List<EvaluateModel> list=evaluateInfoServiceImpl.findEvaluate(parameter);
            PageInfo<EvaluateModel> pageList = new PageInfo<>(list);

            //查询每条评论对应的追评
            list=new ArrayList<>();
            if(CommonUtil.isNotEmpty(pageList.getList())){
                parameter.put("addEvaluate",1);
                for(EvaluateModel evaluatePojo:pageList.getList()){
                    parameter.put("orderId",evaluatePojo.getOrderId());
                    parameter.put("productId",evaluatePojo.getProductId());
                    parameter.put("productSuk",evaluatePojo.getProductSuk());
                    List<EvaluateModel> evaluateList=evaluateInfoServiceImpl.findEvaluate(parameter);
                    Map<String,Object> map= Util.transBean2Map(evaluatePojo);
                    if(CommonUtil.isNotEmpty(evaluateList)){
                        map.put("addEvaluate",evaluateList.get(0));
                    }else{
                        map.put("addEvaluate",null);
                    }
                    //用户信息
                    BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
                    busiCustomerPojo.setId(evaluatePojo.getEvaluateUser());
                    busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
                    if (CommonUtil.isEmpty(busiCustomerPojo)) {
                        throw  new BusinessException("评价人信息异常");
                    }
                    map.put("userName",busiCustomerPojo.getPhone());
                    map.put("photoUrl",busiCustomerPojo.getPhotoUrl());

                    //店铺信息
                    BusiShopPojo busiShopPojo = new BusiShopPojo();
                    busiShopPojo.setId(evaluatePojo.getShopId());
                    busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
                    if(CommonUtil.isEmpty(busiShopPojo)){
                        throw new BusinessException("没有相关店铺信息");
                    }
                    map.put("shopName",busiShopPojo.getShopName());
                    evaluateLists.add(map);
                }
            }
            result.put("total",pageList.getTotal());
            result.put("evaluateList",evaluateLists);
            return result;
        }catch (Exception e){
            LoggerUtil.error(FindEvaluateExecutor.class,e);
            throw new BusinessException("查询评论失败");
        }
    }

}
