package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.model.CateParamRelaModel;
import com.yinhetianze.business.category.service.CateParamRelaBusiService;
import com.yinhetianze.business.category.service.ParameterBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.category.CateParamRelaPojo;
import com.yinhetianze.pojo.category.ParameterPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class UpdateCateParamRelaExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private CateParamRelaBusiService cateParamRelaBusiServiceImpl;

    @Autowired
    private ParameterBusiService parameterBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        CateParamRelaModel cateParamRelaModel=(CateParamRelaModel)model;
        CateParamRelaPojo cateParamRelaPojo =new CateParamRelaPojo();
        BeanUtils.copyProperties(cateParamRelaModel,cateParamRelaPojo);
        ParameterPojo parameterPojo=new ParameterPojo();
        BeanUtils.copyProperties(cateParamRelaPojo.getParameterPojo(),parameterPojo);
        if(parameterBusiServiceImpl.updateParameter(parameterPojo)<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        if(cateParamRelaBusiServiceImpl.updateByPrimaryKeySelective(cateParamRelaPojo)<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        CateParamRelaModel cateParamRelaModel=(CateParamRelaModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(cateParamRelaModel.getCateId())){
            errorMessage.rejectNull("cateId",null,"关联的商品分类ID");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(cateParamRelaModel.getParamId())){
            errorMessage.rejectNull("paramId",null,"关联的商品参数ID");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(cateParamRelaModel.getParameterPojo().getId())){
            errorMessage.rejectNull("id",null,"商品参数ID");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(cateParamRelaModel.getParameterPojo().getParamName())){
            errorMessage.rejectNull("paramName",null,"商品参数");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(cateParamRelaModel.getParameterPojo().getSort())){
            errorMessage.rejectNull("sort",null,"排序编号");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(cateParamRelaModel.getParameterPojo().getFirstWord())){
            errorMessage.rejectNull("firstWord",null,"排序首字母");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(cateParamRelaModel.getParameterPojo().getIsInputable())){
            errorMessage.rejectNull("isInputable",null,"用户输入的参数");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(cateParamRelaModel.getParameterPojo().getIsWholeLine())){
            errorMessage.rejectNull("isWholeLine",null,"是否独占一行显示");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(cateParamRelaModel.getParameterPojo().getStatus())){
            errorMessage.rejectNull("status",null,"是否有效");
            return errorMessage;
        }
        return errorMessage;
    }
}
