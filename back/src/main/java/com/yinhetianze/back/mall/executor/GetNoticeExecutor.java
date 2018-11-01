package com.yinhetianze.back.mall.executor;

import com.yinhetianze.business.message.service.busi.MessageBusiService;
import com.yinhetianze.business.message.service.info.MessageInfoService;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import com.yinhetianze.security.custom.userdetails.UserDetails;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.mall.model.NoticeModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.List;

/**
 * 查询商城公告
 */

@Component
public class GetNoticeExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CacheData<List<Map>> noticeCacheData;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private MessageInfoService messageInfoServiceImpl;

    @Autowired
    private MessageBusiService messageBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        NoticeModel noticeModel = (NoticeModel)model;

        List<Map> list = noticeCacheData.getCacheData();

        UserDetails userDetails = redisUserDetails.getUserDetails(noticeModel.getToken());
        if(userDetails != null){
            TokenUser tokenUser = (TokenUser)userDetails;

            BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
            busiMessagePojo.setId(tokenUser.getId());
            busiMessagePojo = messageInfoServiceImpl.selectOne(busiMessagePojo);

            if(busiMessagePojo != null){
                busiMessagePojo.setNoticeTime(new java.util.Date());
                messageBusiServiceImpl.updateInfo(busiMessagePojo);
            }
        }

        int currentPage = noticeModel.getCurrentPage();
        currentPage=currentPage<0?0:currentPage;
        int pageSize = noticeModel.getPageSize();


        int listSize = (list!=null)?list.size():0;
        PageData pageData = new PageData();
        if(listSize == 0){
            return pageData;
        }

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
