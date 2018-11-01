package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.system.model.BusiSysSyspropertiesModel;
import com.yinhetianze.systemservice.system.service.info.SysSyspropertiesInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 查询系统参数
 */

@Component
public class GetSysSyspropertiesExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysSyspropertiesInfoService sysSyspropertiesInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysSyspropertiesModel busiSysSyspropertiesModel = (BusiSysSyspropertiesModel)model;
        if(CommonUtil.isNotEmpty(busiSysSyspropertiesModel.getpName())){
            try {
                String pName = URLDecoder.decode(busiSysSyspropertiesModel.getpName(),"UTF-8");
                busiSysSyspropertiesModel.setpName(pName);
            }catch (Exception e){
                LoggerUtil.error(GetSysSyspropertiesExecutor.class, e);
            }
        }
        if(CommonUtil.isNotEmpty(busiSysSyspropertiesModel.getpDescription())){
            try {
                String pDescription = URLDecoder.decode(busiSysSyspropertiesModel.getpDescription(),"UTF-8");
                busiSysSyspropertiesModel.setpDescription(pDescription);
            }catch (Exception e){
                LoggerUtil.error(GetSysSyspropertiesExecutor.class, e);
            }
        }
        PageHelper.startPage(busiSysSyspropertiesModel.getCurrentPage(),busiSysSyspropertiesModel.getPageSize());
        PageInfo pageInfo = new PageInfo(sysSyspropertiesInfoServiceImpl.selectPropertiesList(busiSysSyspropertiesModel));
        return pageInfo;
    }
}
