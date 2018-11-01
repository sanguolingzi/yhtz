package com.yinhetianze.business.settlement.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.business.settlement.service.SettlementInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.SettlementPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
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
public class FindSettlementByShopIdExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private SettlementInfoService settlementInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //校验商家信息
        BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(CommonUtil.isEmpty(shopPojo)){
            throw new BusinessException("该用户不是商家");
        }
        SettlementModel settlementModel=(SettlementModel)model;
        settlementModel.setShopId(shopPojo.getId());
        PageHelper.startPage(settlementModel.getCurrentPage(),settlementModel.getPageSize());
        List<SettlementPojo> settlementList=settlementInfoServiceImpl.findSettlements(settlementModel);
        PageInfo<SettlementPojo> pageList = new PageInfo<>(settlementList);
        Map<String,Object> result=new HashMap<>();
        result.put("total",pageList.getTotal());
        result.put("settlementList",pageList.getList());
        return result;
    }

}
