package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.service.CateParamRelaBusiService;
import com.yinhetianze.business.category.service.CateParamRelaInfoService;
import com.yinhetianze.business.category.service.ParameterBusiService;
import com.yinhetianze.business.product.model.DeleteModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.category.CateParamRelaPojo;
import com.yinhetianze.pojo.category.ParameterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class DeleteCateParamRelaExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private CateParamRelaBusiService cateParamRelaBusiServiceImpl;

    @Autowired
    private CateParamRelaInfoService cateParamRelaInfoServiceImpl;

    @Autowired
    private ParameterBusiService parameterBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel deleteModel=(DeleteModel)model;
        String[] idsArr = deleteModel.getIds().split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for (String id:idsArr){
            CateParamRelaPojo cateParamRelaPojo=new CateParamRelaPojo();
            cateParamRelaPojo.setId(Integer.parseInt(id));
            cateParamRelaPojo.setDelFlag((short)1);
            ParameterPojo parameterPojo=new ParameterPojo();
            int paramid=cateParamRelaInfoServiceImpl.getParameterId(cateParamRelaPojo);
            if(paramid<=0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            parameterPojo.setId(paramid);
            parameterPojo.setDelFlag((short)1);
            if(parameterBusiServiceImpl.updateParameter(parameterPojo)<=0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            if(cateParamRelaBusiServiceImpl.updateByPrimaryKeySelective(cateParamRelaPojo)<=0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        DeleteModel deleteModel=(DeleteModel)model;
        if(CommonUtil.isEmpty(deleteModel.getIds())){
            errorMessage.rejectNull("ids",null,"ids");
            return errorMessage;
        }
        return errorMessage;
    }
}
