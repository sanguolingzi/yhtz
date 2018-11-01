package com.yinhetianze.business.task.service.impl;

import com.yinhetianze.business.task.service.TaskHisBusiService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.task.mapper.TaskHisBusiMapper;

@Service
public class TaskHisBusiServiceImpl implements TaskHisBusiService
{
    @Autowired
    private TaskHisBusiMapper mapper;
}