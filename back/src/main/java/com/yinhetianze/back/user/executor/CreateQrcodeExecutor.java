package com.yinhetianze.back.user.executor;

import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.user.model.BusiSysOptorPageModel;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateQrcodeExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private WxService wxMpService;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户信息不存在");
        }
        ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
        shopProxyPojo.setOptorId(tokenUser.getId());
        shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
        if(CommonUtil.isEmpty(shopProxyPojo)){
            throw new BusinessException("商家没有没有绑定店铺");
        }
        Map<String,Object> map=new HashMap<>();
        if(CommonUtil.isNotEmpty(shopProxyPojo.getOpenId())){
            map.put("haveOpenId",1);
            return map;
        }else{
            try {
                String sceneId=shopProxyPojo.getShopPhone();
                WxMpQrCodeTicket wxMpQrCodeTicket= wxMpService.getqrCodeCreateTmpTicket(sceneId,300);
                map.put("haveOpenId",0);
                map.put("sceneId",sceneId);
                map.put("url",wxMpQrCodeTicket.getUrl());
                return map;
            }catch (Exception e){

                throw new BusinessException("生成二维码失败");
            }
        }

    }

}
