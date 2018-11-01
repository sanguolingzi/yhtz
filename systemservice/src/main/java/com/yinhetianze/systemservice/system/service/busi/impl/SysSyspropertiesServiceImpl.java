package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.mapper.busi.SysSyspropertiesMapper;
import com.yinhetianze.systemservice.system.service.busi.SysSyspropertiesService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysSyspropertiesServiceImpl implements SysSyspropertiesService
{
    @Autowired
    private SysSyspropertiesMapper mapper;

    @Override
    public int insertSelective(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo){
        return mapper.insertSelective(busiSysSyspropertiesPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo){
        busiSysSyspropertiesPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(busiSysSyspropertiesPojo);
    }

    @Override
    public int deleteInfo(String ids) throws BusinessException {
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            BusiSysSyspropertiesPojo busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
            busiSysSyspropertiesPojo.setId(Integer.parseInt(id));
            busiSysSyspropertiesPojo.setDelFlag((short)1);
            int result = updateByPrimaryKeySelective(busiSysSyspropertiesPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }
}