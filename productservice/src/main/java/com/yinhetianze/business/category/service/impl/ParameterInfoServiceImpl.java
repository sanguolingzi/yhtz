package com.yinhetianze.business.category.service.impl;

import com.yinhetianze.business.category.model.ParameterModel;
import com.yinhetianze.business.category.service.ParameterInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.category.mapper.ParameterInfoMapper;

@Service
public class ParameterInfoServiceImpl implements ParameterInfoService
{
    @Autowired
    private ParameterInfoMapper mapper;

}