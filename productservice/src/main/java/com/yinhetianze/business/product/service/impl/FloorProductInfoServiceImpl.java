package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.FloorProductInfoMapper;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.FloorProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FloorProductInfoServiceImpl implements FloorProductInfoService
{
    @Autowired
    private FloorProductInfoMapper mapper;

    @Override
    public List<Map> selectProductFromFloor(Integer floorId)
    {
        return mapper.selectProductFromFloor(floorId);
    }
    @Override
    public List<Map> getGuessProductList(){
        return  mapper.getGuessProductList();
    }

    @Override
    public List<Map> selectFloorImg(Integer floorId) {
        return mapper.selectFloorImg(floorId);
    }
}