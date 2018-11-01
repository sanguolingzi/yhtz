package com.yinhetianze.business.product.service;

import com.yinhetianze.business.product.model.ProductModel;

import java.util.List;
import java.util.Map;

public interface FloorProductInfoService
{
    List<Map> selectProductFromFloor(Integer floorId);

    List<Map> getGuessProductList();

    List<Map>selectFloorImg(Integer floorId);
}