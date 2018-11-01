package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.MemberParcelModel;
import com.yinhetianze.business.product.service.MemberParcelInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.MemberParcelPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetMemberParcelListExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private MemberParcelInfoService memberParcelInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MemberParcelModel memberParcelModel=(MemberParcelModel)model;
        MemberParcelPojo memberParcelPojo=new MemberParcelPojo();
        if(CommonUtil.isNotEmpty(memberParcelModel.getParcelName())){
            try {
                String parcelName = URLDecoder.decode(memberParcelModel.getParcelName(), "UTF-8");
                memberParcelPojo.setParcelName(parcelName);
            }catch (Exception e){
                LoggerUtil.error(GetMemberParcelListExecutor.class, e);
            }
        }
        PageHelper.startPage(memberParcelModel.getCurrentPage(), memberParcelModel.getPageSize());
        PageInfo pageInfo=new PageInfo(memberParcelInfoServiceImpl.selectMemberParcelList(memberParcelPojo));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return  pageData;
    }
}
