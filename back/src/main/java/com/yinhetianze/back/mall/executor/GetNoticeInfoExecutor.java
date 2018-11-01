package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.mall.model.NoticeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 查询商城公告详情
 */

@Component
public class GetNoticeInfoExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CacheData<List<Map>> noticeCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        NoticeModel noticeModel = (NoticeModel)model;
        List<Map> list = noticeCacheData.getCacheData();
        if(list==null || list.isEmpty())
           return null;

        for(Map m:list){
           String cacheId = m.getOrDefault("id","").toString();
           if(cacheId.equals(noticeModel.getId().toString())){

               return m;
           }
        }
        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        NoticeModel noticeModel = (NoticeModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(noticeModel.getId())){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }
        return errorMessage;
    }
}
