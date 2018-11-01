package com.yinhetianze.business.shop.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.business.shop.model.ShopProxyModel;

public interface ShopProxyBusiService
{
    int  insertSelective(ShopProxyPojo shopProxyPojo);
    int updateByPrimaryKeySelective(ShopProxyPojo shopProxyPojo);

    int addMessage(ShopProxyModel shopProxyModel) throws BusinessException;

    int addInfo(ShopProxyModel shopProxyModel)throws BusinessException;

    int updateInfo(ShopProxyModel shopProxyModel)throws BusinessException;
}