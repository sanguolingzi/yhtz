package com.yinhetianze.business.task.service.impl;

import com.yinhetianze.business.task.service.TaskInfoService;
import com.yinhetianze.pojo.task.TaskPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.task.mapper.TaskInfoMapper;

import java.util.List;

@Service
public class TaskInfoServiceImpl implements TaskInfoService
{
    @Autowired
    private TaskInfoMapper mapper;

    @Override
    public List<TaskPojo> getTaskPojoList(TaskPojo pojo)
    {
        return mapper.select(pojo);
    }

    @Override
    public TaskPojo findTaskPojo(TaskPojo pojo)
    {
        return mapper.selectOne(pojo);
    }
}