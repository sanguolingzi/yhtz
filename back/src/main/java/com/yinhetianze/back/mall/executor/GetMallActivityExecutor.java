package com.yinhetianze.back.mall.executor;

import com.yinhetianze.business.message.service.busi.MessageBusiService;
import com.yinhetianze.business.message.service.info.MessageInfoService;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import com.yinhetianze.security.custom.userdetails.UserDetails;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询商城活动
 */

@Component
public class GetMallActivityExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private MessageInfoService messageInfoServiceImpl;

    @Autowired
    private MessageBusiService messageBusiServiceImpl;

    @Autowired
    private CacheData<List<MallActivityModel>> mallActivityCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MallActivityModel mallActivityModel = (MallActivityModel)model;
        UserDetails userDetails = redisUserDetails.getUserDetails(mallActivityModel.getToken());
        if(userDetails != null){
            TokenUser tokenUser = (TokenUser)userDetails;

            BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
            busiMessagePojo.setId(tokenUser.getId());
            busiMessagePojo = messageInfoServiceImpl.selectOne(busiMessagePojo);

            if(busiMessagePojo != null){
                busiMessagePojo.setActiveTime(new java.util.Date());
                messageBusiServiceImpl.updateInfo(busiMessagePojo);
            }
        }

        //过滤符合条件的活动数据
        List<MallActivityModel> list = mallActivityCacheData.getCacheData();
        if(mallActivityModel.getType() != null){
            List<MallActivityModel> tempList = new ArrayList<>();
            if(list!=null&&!list.isEmpty()){
                for(MallActivityModel cacheData:list){
                    if(cacheData.getType()!=null
                            && cacheData.getType().intValue() == mallActivityModel.getType().intValue()){
                        tempList.add(cacheData);
                    }
                }
            }
            list = tempList;
        }

        int listSize = (list!=null)?list.size():0;
        PageData pageData = new PageData();
        if(listSize == 0){
            return pageData;
        }


        int currentPage = mallActivityModel.getCurrentPage();
        currentPage=currentPage<0?0:currentPage;

        int pageSize = mallActivityModel.getPageSize();

        int startIndex = (currentPage-1)*pageSize;
        if(startIndex > listSize){
            return pageData;
        }

        int endIndex = currentPage*pageSize;

        if(listSize>endIndex){
            list=list.subList(startIndex,endIndex);
        }else{
            list=list.subList(startIndex,listSize);
        }
        pageData.setListData(list);
        pageData.setTotalRecord(Long.parseLong((list!=null?list.size():0)+""));
        return pageData;
    }
}
