package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.business.shop.model.ShopProxyModel;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * 查询代发用户
 */
@Component
public class GetShopProxyExecutor extends AbstractRestBusiExecutor<PageInfo> {

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Override
    protected PageInfo executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopProxyModel shopProxyModel = (ShopProxyModel)model;
        if(CommonUtil.isNotEmpty(shopProxyModel.getShopName())){
            try {
                shopProxyModel.setShopName(URLDecoder.decode(shopProxyModel.getShopName(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                LoggerUtil.error(GetShopProxyExecutor.class, e);
            }
        }
        ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
        PageHelper.startPage(shopProxyModel.getCurrentPage(),shopProxyModel.getPageSize());
        BeanUtils.copyProperties(model, shopProxyPojo);
        List<Map> list = shopProxyInfoServiceImpl.getProductShopProxyList(shopProxyPojo);
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
        return pageInfo;
    }
}
