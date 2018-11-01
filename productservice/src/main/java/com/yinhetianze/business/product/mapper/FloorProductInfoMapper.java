package com.yinhetianze.business.product.mapper;

import com.yinhetianze.business.product.model.ProductModel;

import java.util.Map;
import java.util.List;

public interface FloorProductInfoMapper {

    List<Map> selectProductFromFloor(Integer floorId);
    List<Map> getGuessProductList();
    List<Map>selectFloorImg(Integer floorId);

}