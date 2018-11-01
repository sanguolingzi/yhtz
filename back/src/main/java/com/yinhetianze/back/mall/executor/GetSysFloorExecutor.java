package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;
import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 查询PC楼层
 */

@Component
public class GetSysFloorExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysFloorInfoService sysFloorInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysFloorModel sysFloorModel = (SysFloorModel)model;
        if(CommonUtil.isNotEmpty(sysFloorModel.getFloorName())){
            try{
                String floorName= URLDecoder.decode(sysFloorModel.getFloorName(),"UTF-8");
                sysFloorModel.setFloorName(floorName);
            }catch (Exception e){
                LoggerUtil.error(GetSysFloorExecutor.class, e);
            }
        }
        PageHelper.startPage(sysFloorModel.getCurrentPage(),sysFloorModel.getPageSize());
        PageInfo pageInfo = new PageInfo(sysFloorInfoServiceImpl.selectSysFloorList(sysFloorModel));
        return pageInfo;


    }
}
