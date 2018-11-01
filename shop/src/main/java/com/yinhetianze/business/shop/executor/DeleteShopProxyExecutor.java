package com.yinhetianze.business.shop.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.business.shop.model.ShopProxyModel;
import com.yinhetianze.business.shop.service.busi.ShopProxyBusiService;
import com.yinhetianze.systemservice.user.service.busi.SysOptorBusiService;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import com.yinhetianze.systemservice.user.service.info.SysOptorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteShopProxyExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopProxyBusiService shopProxyBusiServiceImpl;

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Autowired
    private SysOptorInfoService sysOptorInfoServiceImpl;


    @Autowired
    private SysOptorBusiService sysOptorBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopProxyModel shopProxyModel=(ShopProxyModel)model;
        ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
        shopProxyPojo.setId(shopProxyModel.getId());
        shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
        BusiSysOptorPojo busiSysOptorPojo=new BusiSysOptorPojo();
        busiSysOptorPojo.setAccount(shopProxyPojo.getShopAccount());
        busiSysOptorPojo.setDelFlag((short)0);
        busiSysOptorPojo=sysOptorInfoServiceImpl.select(busiSysOptorPojo);
        if(CommonUtil.isNotEmpty(busiSysOptorPojo)){
            busiSysOptorPojo.setDelFlag((short)1);
            int i=sysOptorBusiServiceImpl.updateByPrimaryKeySelective(busiSysOptorPojo);
            if(i<=0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        shopProxyPojo.setDelFlag((short)1);
        int result=shopProxyBusiServiceImpl.updateByPrimaryKeySelective(shopProxyPojo);
        if(result<=0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ShopProxyModel shopProxyModel=(ShopProxyModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(shopProxyModel.getId())){
            errorMessage.rejectNull("id","null","自营店铺ID");
        }
        return errorMessage;
    }
}
