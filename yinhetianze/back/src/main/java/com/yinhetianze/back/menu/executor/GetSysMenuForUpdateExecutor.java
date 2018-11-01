package com.yinhetianze.back.menu.executor;

import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.SysMenuPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限菜单修改列表 构造成如下格式
 *
 */
@Component
public class GetSysMenuForUpdateExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysMenuInfoService sysMenuInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysMenuPojo sysMenuPojo = new SysMenuPojo();
        sysMenuPojo.setDelFlag((short)0);
        List<SysMenuPojo> list = sysMenuInfoServiceImpl.selectList(sysMenuPojo);
        return sysMenuInfoServiceImpl.GetSysMenuForUpdateList(list);

    }



}
