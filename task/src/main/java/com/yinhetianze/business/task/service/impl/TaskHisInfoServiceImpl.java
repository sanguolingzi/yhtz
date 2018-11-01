package com.yinhetianze.business.task.service.impl;

import com.yinhetianze.business.task.service.TaskHisInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.task.mapper.TaskHisInfoMapper;

@Service
public class TaskHisInfoServiceImpl implements TaskHisInfoService
{
    @Autowired
    private TaskHisInfoMapper mapper;
}