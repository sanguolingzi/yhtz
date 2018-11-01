package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.MemberParcelModel;
import com.yinhetianze.business.product.service.MemberParcelBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.MemberParcelPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeteleMemberParcelExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MemberParcelBusiService memberParcelBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MemberParcelModel memberParcelModel=(MemberParcelModel)model;
        MemberParcelPojo memberParcelPojo=new MemberParcelPojo();
        memberParcelPojo.setId(memberParcelModel.getId());
        memberParcelPojo.setDelFlag((short)1);
        int result=memberParcelBusiServiceImpl.updateByPrimaryKeySelective(memberParcelPojo);
        if (result<=0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        MemberParcelModel memberParcelModel=(MemberParcelModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(memberParcelModel.getId())){
            errorMessage.rejectNull("id","null","id");
        }
        return errorMessage;
    }
}
