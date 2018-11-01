package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.QuestionPojo;
import com.yinhetianze.systemservice.system.model.QuestionModel;

import java.util.List;

public interface QuestionInfoMapper extends InfoMapper<QuestionPojo> {

    List<QuestionModel> getQuestion(QuestionModel questionModel);
}