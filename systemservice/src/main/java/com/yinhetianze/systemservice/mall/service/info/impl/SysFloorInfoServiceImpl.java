package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.systemservice.mall.mapper.info.SysFloorInfoMapper;
import com.yinhetianze.systemservice.mall.model.FloorMobileIndexModel;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;
import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import com.yinhetianze.pojo.back.SysFloorPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysFloorInfoServiceImpl implements SysFloorInfoService
{
    @Autowired
    private SysFloorInfoMapper mapper;

    @Override
    public List selectList(SysFloorPojo sysFloorPojo){
        return mapper.select(sysFloorPojo);
    }

    @Override
    public List<SysFloorModel> selectSysFloorList(SysFloorModel sysFloorModel) {
        return mapper.selectSysFloorList(sysFloorModel);
    }

    @Override
    public List selectForMobile(SysFloorPojo sysFloorPojo) {
       List<SysFloorPojo> list =  selectList(sysFloorPojo);
       List<Map> returnList = new ArrayList<>();
       for(SysFloorPojo pojo : list){
           Map m = new HashMap();
           m.put("id",pojo.getId());
           m.put("name",pojo.getFloorName());
           returnList.add(m);

       }
       return returnList;
    }

    @Override
    public SysFloorPojo selectOne(SysFloorPojo sysFloorPojo) {
        sysFloorPojo.setDelFlag((short)0);
        return mapper.selectOne(sysFloorPojo);
    }

    @Override
    public FloorMobileIndexModel selectFloorOne(SysFloorPojo sysFloorPojo) {
        return mapper.selectFloorOne(sysFloorPojo);
    }

    @Override
    public List<SysFloorPojo> selectFloorList() {
        return mapper.selectFloorList();
    }

    @Override
    public List<Map> getFloorList() {
        return mapper.getFloorList();
    }
}