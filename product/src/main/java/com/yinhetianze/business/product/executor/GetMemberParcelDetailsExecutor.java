package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.MemberParcelModel;
import com.yinhetianze.business.product.service.MemberParcelImgInfoService;
import com.yinhetianze.business.product.service.MemberParcelInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.MemberParcelImgPojo;
import com.yinhetianze.pojo.product.MemberParcelPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetMemberParcelDetailsExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MemberParcelImgInfoService memberParcelImgInfoServiceImpl;

    @Autowired
    private MemberParcelInfoService memberParcelInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> memberParcelMap=new HashMap<String, Object>();
        MemberParcelModel memberParcelModel=(MemberParcelModel)model;
        MemberParcelPojo memberParcelPojo=new MemberParcelPojo();
        memberParcelPojo.setId(memberParcelModel.getId());
        List<Map> memberParcelList=memberParcelInfoServiceImpl.selectMemberParcelDetails(memberParcelPojo);
        memberParcelMap.put("memberParcel",memberParcelList);
        if(memberParcelList.size()>0){
            MemberParcelImgPojo memberParcelImgPojo=new MemberParcelImgPojo();
            memberParcelImgPojo.setMemberParcelId(memberParcelModel.getId());
            memberParcelImgPojo.setDelFlag((short)0);
            List<MemberParcelImgPojo> memberParcelImg=memberParcelImgInfoServiceImpl.selectMemberParcelImgList(memberParcelImgPojo);
            memberParcelMap.put("memberParcelImg",memberParcelImg);
        }else{
            throw new BusinessException("该会员礼包不存在");
        }
        return memberParcelMap;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        MemberParcelModel memberParcelModel=(MemberParcelModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(memberParcelModel.getId())){
            errorMessage.rejectNull("id","null","会员礼包ID");
        }
        return errorMessage;
    }
}
