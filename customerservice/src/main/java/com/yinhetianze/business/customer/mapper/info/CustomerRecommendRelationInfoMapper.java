package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.business.customer.model.BusiCustomerRecommendRelationModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

import java.util.List;

public interface CustomerRecommendRelationInfoMapper extends InfoMapper<BusiCustomerRecommendRelationPojo> {

    List<BusiCustomerRecommendRelationModel> selectRecommendRelationList(@Param("recommendGameId") Integer recommendGameId);

    int selectRecommendRelationCount(Map<String,Object> paraMap);

    int selectCount(Map<String,Object> paraMap);

}