package com.yinhetianze.business.product.mapper;

import com.yinhetianze.business.product.model.SysProdauditModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.SysProdauditPojo;

import java.util.List;
import java.util.Map;

public interface SysProdauditInfoMapper extends InfoMapper<SysProdauditPojo> {
    List<Map> getShopProdaudit(SysProdauditModel sysProdauditModel);
}