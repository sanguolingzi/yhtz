package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.pojo.back.BusiSysMemberInfoPojo;
import com.yinhetianze.systemservice.system.model.SysMemberInfoModel;

import java.util.List;

import java.util.Map;

public interface SysMemberInfoService
{
    List<SysMemberInfoModel> selectParentSystemMember(SysMemberInfoModel sysMemberInfoModel);

    List<SysMemberInfoModel> selectChildSystemMember(SysMemberInfoModel sysMemberInfoModel);

    BusiSysMemberInfoPojo selectOne(BusiSysMemberInfoPojo busiSysMemberInfoPojo);

    /**
     * 查询所有的权益信息 及其子级信息
     * [{
     *     "title":"",
     *     "img":"",
     *     "banner":"",
     *     "child":[
     *      {
     *          "title":"","content":"","img":""
     *      }
     *     ]
     * }]
     * @return
     */
    List<Map<String,Object>> selectMemberInfo();
}