package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.MemberParcelImgModel;
import com.yinhetianze.business.product.service.MemberParcelImgInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.product.MemberParcelImgPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class GetBackstageMemberParcelImgListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MemberParcelImgInfoService memberParcelImgInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MemberParcelImgModel memberParcelImgModel=(MemberParcelImgModel)model;
        MemberParcelImgPojo memberParcelImgPojo=new MemberParcelImgPojo();
        BeanUtils.copyProperties(memberParcelImgModel,memberParcelImgPojo);
        memberParcelImgPojo.setDelFlag((short)0);
        List<MemberParcelImgPojo> memberParcelImgPojoList=memberParcelImgInfoServiceImpl.selectMemberParcelImgList(memberParcelImgPojo);
        return memberParcelImgPojoList;
    }
}
