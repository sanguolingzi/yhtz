package com.yinhetianze.back.system.executor;

import com.yinhetianze.back.system.cachedata.MemberInfoCacheData;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Map;

import java.util.List;

/**
 * 查询用户会员权益
 */
@Service
public class FindMemberInfoExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private MemberInfoCacheData memberInfoCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        String name = null;
        if(params!=null&&params.length>0){
            name = params[0].toString();
        }

        if(name == null)
            return null;

        try {
            name= URLDecoder.decode(name,"UTF-8");
        }catch (Exception e){
            LoggerUtil.error(FindMemberInfoExecutor.class, e);
            return null;
        }

        List<Map<String,Object>> list = ( List<Map<String,Object>>)memberInfoCacheData.getCacheData();
        if(list!=null && !list.isEmpty()){
            for(Map<String,Object> map : list){
                   if(map.getOrDefault("title","").toString().equalsIgnoreCase(name)){
                       return map;
                   }
            }
        }
        return null;
    }


}
