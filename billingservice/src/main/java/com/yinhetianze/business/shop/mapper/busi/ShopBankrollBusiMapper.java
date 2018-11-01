package com.yinhetianze.business.shop.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface ShopBankrollBusiMapper extends BusiMapper<BusiShopBankrollPojo> {
    int updateGoodsAmount(@Param("id") Integer id, @Param("goodsAmount") BigDecimal goodsAmount,
                          @Param("oldGoodsAmount") BigDecimal oldGoodsAmout);
}