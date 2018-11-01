package com.yinhetianze.business.expressCode.service.impl;

import com.yinhetianze.business.expressCode.service.ExpressCodeInfoService;
import com.yinhetianze.pojo.order.ExpressCodePojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.expressCode.mapper.ExpressCodeInfoMapper;

import java.util.List;

@Service
public class ExpressCodeInfoServiceImpl implements ExpressCodeInfoService
{
    @Autowired
    private ExpressCodeInfoMapper mapper;

    @Override
    public List<ExpressCodePojo> findAll() {
        return mapper.findAll();
    }

    @Override
    public ExpressCodePojo selectOne(ExpressCodePojo expressCodePojo) {
        return mapper.selectOne(expressCodePojo);
    }
}