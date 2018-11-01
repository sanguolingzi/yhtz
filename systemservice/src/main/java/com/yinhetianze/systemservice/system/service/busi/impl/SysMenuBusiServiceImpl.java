package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.menu.service.busi.SysPerMenuBusiService;
import com.yinhetianze.systemservice.menu.service.info.SysPerMenuInfoService;
import com.yinhetianze.systemservice.system.mapper.busi.SysMenuBusiMapper;
import com.yinhetianze.systemservice.system.service.busi.SysMenuBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysPerMenuPojo;
import com.yinhetianze.pojo.back.SysMenuPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysMenuBusiServiceImpl implements SysMenuBusiService
{
    @Autowired
    private SysMenuBusiMapper mapper;

    @Autowired
    private SysPerMenuBusiService sysPerMenuBusiServiceImpl;

    @Autowired
    private SysPerMenuInfoService sysPerMenuInfoServiceImpl;

    @Override
    public int insertSelective(SysMenuPojo sysMenuPojo){
        return mapper.insertSelective(sysMenuPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(SysMenuPojo sysMenuPojo){
        sysMenuPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(sysMenuPojo);
    }

    @Override
    public int deleteBatch(String ids) throws BusinessException{
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0)
            return 0;
        for(String id:idsArr){
            SysMenuPojo sysMenuPojo = new SysMenuPojo();
            sysMenuPojo.setId(Integer.parseInt(id));
            sysMenuPojo.setDelFlag((short)1);
            int result = updateByPrimaryKeySelective(sysMenuPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);


            //删除菜单关联的权限关系
            BusiSysPerMenuPojo busiSysPerMenuPojo = new BusiSysPerMenuPojo();
            busiSysPerMenuPojo.setMenuId(sysMenuPojo.getId());
            busiSysPerMenuPojo.setDelFlag((short)0);

            List<BusiSysPerMenuPojo> perMenuPojoList =  sysPerMenuInfoServiceImpl.selectList(busiSysPerMenuPojo);
            if(CommonUtil.isNotEmpty(perMenuPojoList)){
                for(BusiSysPerMenuPojo perMenu : perMenuPojoList){
                    sysPerMenuBusiServiceImpl.deleteInfo(perMenu.getId());
                }
            }
        }
        return 1;
    }
}