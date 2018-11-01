package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.business.customer.model.BusiCustomerRecommendRelationModel;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;

import java.util.List;

public interface CustomerRecommendRelationInfoService
{
    /**
     * 以推荐人为维度 查询推荐信息
     * @param gameId  推荐人gameId
     * @return
     */
    List<BusiCustomerRecommendRelationModel> selectRecommendRelationList(Integer gameId);


    /**
     * 查询推荐人
     * @param gameId  被推荐人gameid
     * @return
     */
    BusiCustomerRecommendRelationPojo selectRecommendUser(Integer gameId);


    /**
     * 判断是否满足推荐奖励条件  x 时间内 推荐 y 个用户
     * @param gameId
     * @return
     */
    boolean doRecommendReward(Integer gameId);


    List<BusiCustomerRecommendRelationPojo> selectList(BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo);


    int selectCount(Integer customerId);

}