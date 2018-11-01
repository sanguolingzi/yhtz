package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.info.SysFloorDetailInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 查询楼层商品列表
 */

@Component
public class GetSysFloorDetailExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysFloorDetailInfoService sysFloorDetailInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysFloorDetailModel sysFloorDetailModel = (SysFloorDetailModel)model;
        if(CommonUtil.isNotEmpty(sysFloorDetailModel.getDescription())){
            try{
                String description = URLDecoder.decode(sysFloorDetailModel.getDescription(),"UTF-8");
                sysFloorDetailModel.setDescription(description);
            }catch (Exception e){
                LoggerUtil.error(GetSysFloorDetailExecutor.class, e);
            }
        }
        PageHelper.startPage(sysFloorDetailModel.getCurrentPage(), sysFloorDetailModel.getPageSize());
        PageInfo pageInfo = new PageInfo(sysFloorDetailInfoServiceImpl.selectSysFloorProductList(sysFloorDetailModel));
        return pageInfo;
    }
}
