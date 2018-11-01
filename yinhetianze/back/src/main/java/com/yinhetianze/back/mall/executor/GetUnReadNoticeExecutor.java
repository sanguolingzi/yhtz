package com.yinhetianze.back.mall.executor;

import com.yinhetianze.business.message.service.info.MessageInfoService;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.mall.model.BusiMessageCenterModel;
import com.yinhetianze.systemservice.mall.model.NoticeModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 查询商城活动 前台用户个人中心查看商城公告情况
 */

@Component
public class GetUnReadNoticeExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CacheData<List<Map>> noticeCacheData;

    @Autowired
    private MessageInfoService messageInfoServiceImpl;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        NoticeModel noticeModel = (NoticeModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(noticeModel.getToken());
        if(tokenUser != null){
            BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
            busiMessagePojo.setId(tokenUser.getId());
            busiMessagePojo = messageInfoServiceImpl.selectOne(busiMessagePojo);

            List<Map> list = noticeCacheData.getCacheData();
            BusiMessageCenterModel busiMessageCenterModel = new BusiMessageCenterModel();
            if(list!=null&&!list.isEmpty()){
                //记录第一条未读消息标题 和 时间
                String tempTitle="";
                Date tempDate = null;
                int unReadCount = 0;

                for(Map m:list){
                    Long l = (Long)m.get("createTime");
                    //比较时间 确认是否是未读消息
                    Date d = new Date();
                    d.setTime(l);

                    if(busiMessagePojo.getNoticeTime().before(d)){
                        if(unReadCount == 0){
                            tempTitle = m.getOrDefault("title","").toString();
                            tempDate  = new Date();
                            tempDate.setTime(l);
                        }
                        unReadCount++;
                    }
                }

                if(unReadCount > 0){
                    busiMessageCenterModel.setMessageCount(unReadCount);
                    busiMessageCenterModel.setmTitle(tempTitle);
                    busiMessageCenterModel.setCreateTime(DateUtils.formatDate(tempDate,"yyyy-MM-dd HH:mm:ss"));
                }
            }
            return busiMessageCenterModel;
        }else{
            BusiMessageCenterModel busiMessageCenterModel = new BusiMessageCenterModel();
            busiMessageCenterModel.setMessageCount(0);
            return busiMessageCenterModel;
        }
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        return null;
    }
}
