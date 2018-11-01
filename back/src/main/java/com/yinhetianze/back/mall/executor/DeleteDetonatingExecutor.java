package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.DetonatingPojo;
import com.yinhetianze.systemservice.mall.model.DetonatingModel;
import com.yinhetianze.systemservice.mall.service.busi.DetonatingBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteDetonatingExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private DetonatingBusiService detonatingBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DetonatingModel detonatingModel=(DetonatingModel)model;
        DetonatingPojo detonatingPojo=new DetonatingPojo();
        detonatingPojo.setId(detonatingModel.getId());
        detonatingPojo.setDelFlag((short)1);
        int result = detonatingBusiServiceImpl.updateByPrimaryKeySelective(detonatingPojo);
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        DetonatingModel detonatingModel=(DetonatingModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(detonatingModel.getId())){
            errorMessage.rejectNull("id","null","爆品专区ID");
        }
        return errorMessage;
    }
}
