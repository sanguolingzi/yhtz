package com.yinhetianze.back.user.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.systemservice.user.model.BusiSysOptorPageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class GetSceneIdExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private RedisManager redisManager;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysOptorPageModel busiSysOptorPageModel=(BusiSysOptorPageModel)model;
        String sceneId=busiSysOptorPageModel.getAccount();
        if(CommonUtil.isNotEmpty(sceneId)){
            if(sceneId.equals(redisManager.getSerializeObject(sceneId,null))){
                redisManager.deleteValue(sceneId);
                return "success";
            }else{
                return "fail";
            }
        }else{
            return "fail";
        }

    }
}
