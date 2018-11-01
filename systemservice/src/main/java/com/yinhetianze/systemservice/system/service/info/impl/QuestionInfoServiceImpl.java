package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.model.QuestionModel;
import com.yinhetianze.systemservice.system.service.info.QuestionInfoService;
import com.yinhetianze.pojo.back.QuestionPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.info.QuestionInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class QuestionInfoServiceImpl implements QuestionInfoService
{
    @Autowired
    private QuestionInfoMapper mapper;

    @Override
    public QuestionPojo findQuestion(QuestionPojo questionPojo) {
        return mapper.selectOne(questionPojo);
    }

    @Override
    public List<QuestionPojo> selectQuestion(QuestionPojo questionPojo) {
        return mapper.select(questionPojo);
    }

    @Override
    public List<QuestionModel> getQuestion(QuestionModel questionModel) {
        return mapper.getQuestion(questionModel);
    }
}