package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.system.model.SysErrorCodeModel;
import com.yinhetianze.systemservice.system.service.info.SysErrorCodeInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.SysErrorCodePojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询错误码
 */

@Component
public class GetSysErrorCodeExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysErrorCodeInfoService sysErrorCodeInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysErrorCodeModel sysErrorCodeModel = (SysErrorCodeModel)model;
        PageHelper.startPage(sysErrorCodeModel.getCurrentPage(),sysErrorCodeModel.getPageSize());
        SysErrorCodePojo sysErrorCodePojo = new SysErrorCodePojo();
        BeanUtils.copyProperties(sysErrorCodeModel,sysErrorCodePojo);
        PageInfo pageInfo = new PageInfo(sysErrorCodeInfoServiceImpl.selectList(sysErrorCodePojo));
        return pageInfo;
    }
}
