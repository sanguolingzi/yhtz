package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.pojo.back.QuestionPojo;

public interface QuestionBusiService
{
    int addQuestion(QuestionPojo questionPojo);

    int updateQuestion(QuestionPojo questionPojo);

    int deleteQuestion(QuestionPojo questionPojo);
}