package com.yinhetianze.business.shop.executor;


import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;
import com.yinhetianze.business.shop.model.ShopProxyModel;
import com.yinhetianze.business.shop.service.busi.ShopProxyBusiService;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.systemservice.user.service.info.SysOptorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AddShopProxyExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopProxyBusiService shopProxyBusiServiceImpl;

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Autowired
    private SysOptorInfoService sysOptorInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result=shopProxyBusiServiceImpl.addInfo((ShopProxyModel)model);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
       ShopProxyModel shopProxyModel=(ShopProxyModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
         if(CommonUtil.isEmpty(shopProxyModel.getShopAccount())){
            errorMessage.rejectNull("shopAccount","null","商家账号");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getAccountName())){
            errorMessage.rejectNull("accountName","null","商家姓名");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getShopName())){
            errorMessage.rejectNull("shopName","null","商家名称");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getContactPhone())){
            errorMessage.rejectNull("contactPhone","null","联系电话");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getShopAddress())){
            errorMessage.rejectNull("shopAddress","null","详细地址");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getShopPhone())){
            errorMessage.rejectNull("shopPhone","null","商家电话");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getBank())){
            errorMessage.rejectNull("bank","null","开户银行");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getBankCardNumber())){
            errorMessage.rejectNull("bankCardNumber","null","银行卡号");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getSettlementMethod())){
            errorMessage.rejectNull("settlementMethod","null","结算方式");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getShopProvince())){
            errorMessage.rejectNull("shopProvince","null","商家地省级ID");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getShopCity())){
            errorMessage.rejectNull("shopCity","null","商家地市级ID");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getShopArea())){
            errorMessage.rejectNull("shopArea","null","商家地区级ID");
        }
        if(CommonUtil.isEmpty(shopProxyModel.getShopLogo())){
            errorMessage.rejectNull("shopLogo","null","店铺LOGO");
        }
        BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
        busiSysOptorPojo.setAccount(shopProxyModel.getShopAccount());
        busiSysOptorPojo=sysOptorInfoServiceImpl.select(busiSysOptorPojo);
        if(busiSysOptorPojo != null){
            errorMessage.rejectError("account","BC0055","账号","账号");
            return errorMessage;
        }
        ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
        shopProxyPojo.setShopAccount(shopProxyModel.getShopAccount());
        shopProxyPojo.setDelFlag((short)0);
        shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
        if(shopProxyPojo!=null){
            errorMessage.rejectError("account","BC0055","店铺账号","店铺账号");
            return errorMessage;
        }
        return errorMessage;
    }
}
