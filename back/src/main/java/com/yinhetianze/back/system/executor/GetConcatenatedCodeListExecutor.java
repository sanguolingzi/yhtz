package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.systemservice.system.service.info.ConcatenatedCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class GetConcatenatedCodeListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ConcatenatedCodeInfoService concatenatedCodeInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        List<Map>list=concatenatedCodeInfoServiceImpl.selectConcatenatedCodeNameList();
        PageInfo pageInfo=new PageInfo(list);
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
