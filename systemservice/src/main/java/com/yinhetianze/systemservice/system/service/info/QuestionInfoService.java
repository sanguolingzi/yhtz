package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.pojo.back.QuestionPojo;
import com.yinhetianze.systemservice.system.model.QuestionModel;

import java.util.List;
import java.util.Map;

public interface QuestionInfoService
{
    QuestionPojo findQuestion(QuestionPojo questionPojo);

    List<QuestionPojo> selectQuestion(QuestionPojo QuestionPojo);

    List<QuestionModel> getQuestion(QuestionModel questionModel);

}