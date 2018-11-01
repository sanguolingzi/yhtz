package com.yinhetianze.back.mall.executor;

import com.yinhetianze.business.message.service.info.MessageInfoService;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.mall.model.BusiMessageCenterModel;
import com.yinhetianze.systemservice.mall.model.MallActivityModel;
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

/**
 * 查询商城活动 前台用户个人中心查看商城活动情况
 */

@Component
public class GetUnReadActivityExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private MessageInfoService messageInfoServiceImpl;

    @Autowired
    private CacheData<List<MallActivityModel>> mallActivityCacheData;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MallActivityModel mallActivityModel = (MallActivityModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(mallActivityModel.getToken());
        if(tokenUser != null){
            BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
            busiMessagePojo.setId(tokenUser.getId());
            busiMessagePojo = messageInfoServiceImpl.selectOne(busiMessagePojo);

            List<MallActivityModel> list = mallActivityCacheData.getCacheData();
            BusiMessageCenterModel busiMessageCenterModel = new BusiMessageCenterModel();
            if(list!=null&&!list.isEmpty() && mallActivityModel.getType() !=null ){
                //记录第一条未读消息标题 和 时间
                String tempTitle="";
                Date tempDate = null;
                int unReadCount = 0;

                for(MallActivityModel m:list){
                    if(busiMessagePojo.getActiveTime().before(m.getCreateTime())
                                &&    (m.getType().intValue() == mallActivityModel.getType().intValue())
                            ){
                        if(unReadCount == 0){
                            tempTitle = m.getActivityname();
                            tempDate  = m.getCreateTime();
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
