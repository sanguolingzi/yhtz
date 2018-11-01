package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.service.busi.QuestionBusiService;
import com.yinhetianze.pojo.back.QuestionPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.busi.QuestionBusiMapper;

@Service
public class QuestionBusiServiceImpl implements QuestionBusiService
{
    @Autowired
    private QuestionBusiMapper mapper;

    @Override
    public int addQuestion(QuestionPojo questionPojo) {
        return mapper.insertSelective(questionPojo);
    }

    @Override
    public int updateQuestion(QuestionPojo questionPojo) {
        return mapper.updateByPrimaryKeySelective(questionPojo);
    }

    @Override
    public int deleteQuestion(QuestionPojo questionPojo) {
        return mapper.deleteByPrimaryKey(questionPojo);
    }
}