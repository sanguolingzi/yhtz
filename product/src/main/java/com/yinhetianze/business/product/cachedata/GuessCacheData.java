package com.yinhetianze.business.product.cachedata;

import com.yinhetianze.business.product.service.FloorProductInfoService;
import com.yinhetianze.core.cachedata.RedisCacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GuessCacheData extends RedisCacheData {

    @Autowired
    private FloorProductInfoService floorProductInfoServiceImpl;
    @Override
    public List cacheData() throws Exception {
        List<Map> list=floorProductInfoServiceImpl.getGuessProductList();
        return  list;
    }
}
