package com.yinhetianze.business.task.service.impl;

import com.yinhetianze.business.task.service.TaskBusiService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.task.mapper.TaskBusiMapper;

@Service
public class TaskBusiServiceImpl implements TaskBusiService
{
    @Autowired
    private TaskBusiMapper mapper;
}