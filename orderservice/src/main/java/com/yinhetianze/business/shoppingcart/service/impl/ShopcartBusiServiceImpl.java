package com.yinhetianze.business.shoppingcart.service.impl;

import com.yinhetianze.business.shoppingcart.mapper.ShopcartBusiMapper;
import com.yinhetianze.business.shoppingcart.service.ShopcartBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShopcartBusiServiceImpl implements ShopcartBusiService {

    @Autowired
    private ShopcartBusiMapper shopcartBusiMapper;

    @Override
    public int addShopcart(Map<String, Object> map) {
        return shopcartBusiMapper.addShopcart(map);
    }

    @Override
    public void deleteShopcart(Integer[] array) {
        shopcartBusiMapper.deleteShopcart(array);
    }

    @Override
    public void modifyShopcart(List<Map<String, Object>> list) {
        shopcartBusiMapper.modifyShopcart(list);
    }

    @Override
    public int updateById(Map<String, Object> map) {
        return shopcartBusiMapper.updateById(map);
    }
}
