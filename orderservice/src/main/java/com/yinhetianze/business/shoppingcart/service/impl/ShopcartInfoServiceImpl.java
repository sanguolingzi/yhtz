package com.yinhetianze.business.shoppingcart.service.impl;

import com.yinhetianze.business.shoppingcart.mapper.ShopcartInfoMapper;
import com.yinhetianze.business.shoppingcart.service.ShopcartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShopcartInfoServiceImpl implements ShopcartInfoService {

    @Autowired
    private ShopcartInfoMapper shopcartInfoMapper;

    @Override
    public Map<String, Object> findShopcartById(Integer id) {

        return shopcartInfoMapper.findShopcartById(id);
    }

    @Override
    public List<Map<String, Object>> findShopcartByCustomerId(Integer customerId) {
        return shopcartInfoMapper.findShopcartByCustomerId(customerId);
    }

    @Override
    public Map<String, Object> findShopcart(Integer customerId, Integer prodId,String prodSku) {
        return shopcartInfoMapper.findShopcart(customerId,prodId,prodSku);
    }

    @Override
    public Integer countNumberByCustomerId(Integer customerId) {
        return shopcartInfoMapper.countNumberByCustomerId(customerId);
    }

    @Override
    public Integer countNumber(Integer customerId, List<Map<String, Object>> list) {
        return shopcartInfoMapper.countNumber(customerId,list);
    }

    @Override
    public List<Map<String, Object>> findShopcartByIds(int[] ids) {
        return shopcartInfoMapper.findShopcartByIds(ids);
    }
}
