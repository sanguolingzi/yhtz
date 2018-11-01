package com.yinhetianze.business.settlement.executor;

import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.business.settlement.service.SettlementBusiService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.SettlementPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AddSettlementExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private SettlementBusiService settlementBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SettlementModel settlementModel=(SettlementModel)model;
        SettlementPojo settlementPojo=new SettlementPojo();
        BeanUtils.copyProperties(model, settlementPojo);
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //校验商家信息
        BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(CommonUtil.isEmpty(shopPojo)){
            throw new BusinessException("该用户不是商家");
        }
        settlementPojo.setCustomerId(tokenUser.getId());
        settlementPojo.setShopId(shopPojo.getId());
        settlementPojo.setShopName(shopPojo.getShopName());
        settlementPojo.setSettlementNo(CommonUtil.getSerialnumber());
        int i=settlementBusiServiceImpl.addSettlement(settlementPojo,settlementModel.getAll());
        if(i<1){
            throw new BusinessException("申请结算失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        SettlementModel settlementModel=(SettlementModel)model;
        if(CommonUtil.isEmpty(settlementModel.getAll())){
            error.rejectNull("all",null,"结算方式");
        }
        if(settlementModel.getAll()==0&&CommonUtil.isEmpty(settlementModel.getOrdersIds())){
            error.rejectNull("ordersIds",null,"订单id");
        }
        if(CommonUtil.isEmpty(settlementModel.getBankCardName())){
            error.rejectNull("bankCardName",null,"银行卡名称");
        }
        if(CommonUtil.isEmpty(settlementModel.getBankCardNo())){
            error.rejectNull("bankCardNo",null,"银行卡号");
        }
        if(CommonUtil.isEmpty(settlementModel.getBankUserName())){
            error.rejectNull("bankUserName",null,"持卡人名字");
        }
        return error;
    }
}
