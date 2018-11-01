package com.yinhetianze.business.order.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettlementOrderExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto) model;
        Map<String,Object> map=new HashMap<>();
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //校验商家信息
        BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if (CommonUtil.isEmpty(shopPojo)) {
            throw new BusinessException("该用户不是商家");
        }
        map.put("shopId",shopPojo.getId());
        map.put("beginTime",orderDto.getBeginTime());
        map.put("endTime",orderDto.getEndTime());
        map.put("completeTimeSort",orderDto.getCompleteTimeSort());
        map.put("totalAmountSort",orderDto.getTotalAmountSort());
        List<Map<String,Object>> list=new ArrayList<>();
        PageHelper.startPage(orderDto.getCurrentPage(),orderDto.getPageSize());
        if(orderDto.getIsSettlement()==1){
            list=orderInfoServiceImpl.settlementOrder(map);
        }else{
            list=orderInfoServiceImpl.noSettlementOrder(map);
        }
        PageInfo<Map<String,Object>> pageList = new PageInfo<Map<String,Object>>(list);
        Map<String,Object> result=new HashMap<>();
        result.put("settlementOrder",pageList.getList());
        result.put("total",pageList.getTotal());
        return result;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        OrderDto orderDto=(OrderDto) model;
        if(CommonUtil.isEmpty(orderDto.getIsSettlement())){
            errorMessage.rejectNull("isSettlement",null,"是否为可结算");
        }
        return errorMessage;
    }
}
