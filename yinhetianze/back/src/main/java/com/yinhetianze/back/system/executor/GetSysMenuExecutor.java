package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.system.model.SysMenuModel;
import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 查询系统菜单
 */

@Component
public class GetSysMenuExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysMenuInfoService sysMenuInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysMenuModel sysMenuModel = (SysMenuModel)model;
        if(CommonUtil.isNotEmpty(sysMenuModel.getMenuName())){
            try {
                String menuName= URLDecoder.decode(sysMenuModel.getMenuName(),"UTF-8");
                sysMenuModel.setMenuName(menuName);
            }catch (Exception e){
                LoggerUtil.error(GetSysMenuExecutor.class, e);
            }
        }
        PageHelper.startPage(sysMenuModel.getCurrentPage(),sysMenuModel.getPageSize());
        PageInfo pageInfo = new PageInfo(sysMenuInfoServiceImpl.selectMenuList(sysMenuModel));
        return pageInfo;
    }
}
