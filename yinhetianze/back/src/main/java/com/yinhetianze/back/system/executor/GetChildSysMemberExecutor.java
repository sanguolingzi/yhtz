package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.system.model.SysMemberInfoModel;
import com.yinhetianze.systemservice.system.service.info.SysMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询子级权益
 */

@Component
public class GetChildSysMemberExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysMemberInfoService sysMemberInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysMemberInfoModel sysMemberInfoModel = (SysMemberInfoModel)model;
        PageHelper.startPage(sysMemberInfoModel.getCurrentPage(),sysMemberInfoModel.getPageSize());
        List<SysMemberInfoModel> list = sysMemberInfoServiceImpl.selectChildSystemMember(sysMemberInfoModel);
        PageInfo pageInfo = new PageInfo(list);

        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        SysMemberInfoModel sysMemberInfoModel = (SysMemberInfoModel)model;
        if(CommonUtil.isEmpty(sysMemberInfoModel.getParentId())){
            errorMessage.rejectNull("parentId",null,"parentId");
        }
        return errorMessage;
    }
}
