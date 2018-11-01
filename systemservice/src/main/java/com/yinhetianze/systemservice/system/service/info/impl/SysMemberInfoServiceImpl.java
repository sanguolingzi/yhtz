package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.pojo.back.BusiSysMemberInfoPojo;
import com.yinhetianze.systemservice.system.mapper.info.SysMemberInfoMapper;
import com.yinhetianze.systemservice.system.model.SysMemberInfoModel;
import com.yinhetianze.systemservice.system.service.info.SysMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMemberInfoServiceImpl implements SysMemberInfoService
{
    @Autowired
    private SysMemberInfoMapper mapper;

    @Override
    public List<SysMemberInfoModel> selectParentSystemMember(SysMemberInfoModel sysMemberInfoModel) {
        return mapper.selectParentSystemMember(sysMemberInfoModel);
    }

    @Override
    public List<SysMemberInfoModel> selectChildSystemMember(SysMemberInfoModel sysMemberInfoModel) {

        return mapper.selectChildSystemMember(sysMemberInfoModel);
    }


    @Override
    public BusiSysMemberInfoPojo selectOne(BusiSysMemberInfoPojo busiSysMemberInfoPojo) {
        busiSysMemberInfoPojo.setDelFlag((short)0);
        return  mapper.selectOne(busiSysMemberInfoPojo);
    }

    @Override
    public List<Map<String, Object>> selectMemberInfo() {

        List<Map<String, Object>> returnList=  new ArrayList<>();
        SysMemberInfoModel sysMemberInfoModel = new SysMemberInfoModel();
        List<SysMemberInfoModel> parentList = mapper.selectParentSystemMember(sysMemberInfoModel);
        if(parentList!=null && !parentList.isEmpty()){

            for(SysMemberInfoModel parent : parentList){

                if(parent.getIsShow() == 1){
                    continue;
                }

                Map<String,Object> parentMap  = new HashMap<>();

                parentMap.put("title",parent.getMemberTitle());
                parentMap.put("content",parent.getMemberContent());

                sysMemberInfoModel.setParentId(parent.getId());
                List<SysMemberInfoModel> childList = mapper.selectChildSystemMember(sysMemberInfoModel);
                List<Map<String, Object>> returnChildList=  new ArrayList<>();
                if(childList!=null && !childList.isEmpty()){
                    for(SysMemberInfoModel child:childList){

                        Map<String,Object> childMap  = new HashMap<>();

                        childMap.put("title",child.getMemberTitle());
                        childMap.put("content",child.getMemberContent());
                        childMap.put("img",child.getMemberImg());
                        returnChildList.add(childMap);
                    }
                }
                parentMap.put("child",returnChildList);
                returnList.add(parentMap);
            }
        }
        return returnList;
    }
}