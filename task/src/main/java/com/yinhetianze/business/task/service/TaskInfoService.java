package com.yinhetianze.business.task.service;

import com.yinhetianze.pojo.task.TaskPojo;

import java.util.List;

public interface TaskInfoService
{
    List<TaskPojo> getTaskPojoList(TaskPojo pojo);

    TaskPojo findTaskPojo(TaskPojo pojo);
}