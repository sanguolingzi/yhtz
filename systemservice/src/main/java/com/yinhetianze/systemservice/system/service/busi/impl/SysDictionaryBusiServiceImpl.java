package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.mapper.busi.SysDictionaryBusiMapper;
import com.yinhetianze.systemservice.system.service.busi.SysDictionaryBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.SysDictionaryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysDictionaryBusiServiceImpl implements SysDictionaryBusiService
{
    @Autowired
    private SysDictionaryBusiMapper mapper;

    @Override
    public int insertSelective(SysDictionaryPojo sysDictionaryPojo){
        return mapper.insertSelective(sysDictionaryPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDictionaryPojo sysDictionaryPojo){
        return mapper.updateByPrimaryKeySelective(sysDictionaryPojo);
    }

    @Override
    public int deleteInfo(String ids) throws BusinessException {
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            SysDictionaryPojo sysDictionaryPojo = new SysDictionaryPojo();
            sysDictionaryPojo.setId(Integer.parseInt(id));
            sysDictionaryPojo.setDelFlag((short)1);
            int result = updateByPrimaryKeySelective(sysDictionaryPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }
}