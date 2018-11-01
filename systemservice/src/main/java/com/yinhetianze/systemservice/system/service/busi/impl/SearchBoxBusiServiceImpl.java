package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.mapper.busi.SearchBoxBusiMapper;
import com.yinhetianze.systemservice.system.service.busi.SearchBoxBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.SearchBoxPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SearchBoxBusiServiceImpl implements SearchBoxBusiService
{
    @Autowired
    private SearchBoxBusiMapper mapper;

    @Override
    public int insertSelective(SearchBoxPojo searchBoxPojo){
        return mapper.insertSelective(searchBoxPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(SearchBoxPojo searchBoxPojo){
        searchBoxPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(searchBoxPojo);
    }

    @Override
    public int batchDelete(String ids) throws BusinessException {
        String[] idsArr = ids.split(",");
        if(idsArr==null || idsArr.length == 0){
            return 0;
        }
        for(String id:idsArr){
            SearchBoxPojo searchBoxPojo = new SearchBoxPojo();
            searchBoxPojo.setDelFlag((short)1);
            searchBoxPojo.setId(Integer.parseInt(id));
            int result = updateByPrimaryKeySelective(searchBoxPojo);
            if(result == 0){
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }
        return 1;
    }
}