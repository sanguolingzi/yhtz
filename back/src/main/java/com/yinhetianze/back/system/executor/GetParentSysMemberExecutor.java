package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.systemservice.system.model.SearchBoxModel;
import com.yinhetianze.systemservice.system.model.SysMemberInfoModel;
import com.yinhetianze.systemservice.system.service.info.SearchBoxInfoService;
import com.yinhetianze.systemservice.system.service.info.SysMemberInfoService;
import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询父级权益
 */

@Component
public class GetParentSysMemberExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysMemberInfoService sysMemberInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysMemberInfoModel sysMemberInfoModel = (SysMemberInfoModel)model;
        PageHelper.startPage(sysMemberInfoModel.getCurrentPage(),sysMemberInfoModel.getPageSize());
        List<SysMemberInfoModel> list = sysMemberInfoServiceImpl.selectParentSystemMember(sysMemberInfoModel);
        PageInfo pageInfo = new PageInfo(list);

        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
