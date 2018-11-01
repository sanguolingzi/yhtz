package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理 查询企业/店铺综合审核信息结果 用户判断页面跳转情况
 */

@Component
public class GetShopAuditResultExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        String result = "";
        Map<String,Object> m = new HashMap<>();
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(model.getToken() == null || tokenUser == null){
            result="3";
            m.put("result",result);
            return m;
        }
        BusiShopCompanyPojo busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectByCustomerId(tokenUser.getId());

        if(busiShopCompanyPojo == null){//需要添加入驻企业
            result="0";
        //审核未通过   直接跳转至结果查看页面
        }else if(busiShopCompanyPojo.getAuditStatus() == 1){
            result="1";
        //待审核   直接跳转至结果查看页面
        }else if(busiShopCompanyPojo.getAuditStatus() == 2){
            result="4";
        }else{//直接跳转商家中心
            result="2";
        }
        m.put("result",result);
        return m;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        return null;
    }
}
