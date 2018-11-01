package com.yinhetianze.back.role.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.systemservice.role.model.BusiSysRolePageModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.systemservice.role.service.info.SysRoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分页查询系统角色
 */
@Component
public class GetSysRoleListExecutor extends AbstractRestBusiExecutor<PageInfo> {

    @Autowired
    private SysRoleInfoService sysRoleInfoServiceImpl;

    @Override
    protected PageInfo executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysRolePageModel busiSysRolePageModel = (BusiSysRolePageModel)model;
        if(busiSysRolePageModel.getIsAll() == null){
            PageHelper.startPage(busiSysRolePageModel.getCurrentPage(),busiSysRolePageModel.getPageSize());
        }
        List<BusiSysRoleModel> list = sysRoleInfoServiceImpl.selectSysRoleList(busiSysRolePageModel);
        PageInfo<BusiSysRoleModel> pageInfo = new PageInfo<BusiSysRoleModel>(list);
        return pageInfo;
    }
}
