package com.yinhetianze.back.user.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.systemservice.user.model.BusiSysOptorPageModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.systemservice.user.service.info.SysOptorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;

/**
 * 分页查询系统用户
 */
@Component
public class GetSysOptorListExecutor extends AbstractRestBusiExecutor<PageInfo> {

    @Autowired
    private SysOptorInfoService sysOptorInfoServiceImpl;

    @Override
    protected PageInfo executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysOptorPageModel busiSysOptorPageModel = (BusiSysOptorPageModel)model;
        if(CommonUtil.isNotEmpty(busiSysOptorPageModel.getOptorName())){
            try {
                String optorName= URLDecoder.decode(busiSysOptorPageModel.getOptorName(),"UTF-8");
                busiSysOptorPageModel.setOptorName(optorName);
            }catch (Exception e){
                LoggerUtil.error(GetSysOptorListExecutor.class, e);
            }
        }
        PageHelper.startPage(busiSysOptorPageModel.getCurrentPage(),busiSysOptorPageModel.getPageSize());
        List<BusiSysOptorModel> list = sysOptorInfoServiceImpl.selectSysOptorList(busiSysOptorPageModel);
        PageInfo<BusiSysOptorModel> pageInfo = new PageInfo<BusiSysOptorModel>(list);
        return pageInfo;
    }
}
