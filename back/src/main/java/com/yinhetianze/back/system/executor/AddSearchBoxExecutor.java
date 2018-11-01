package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.SearchBoxModel;
import com.yinhetianze.systemservice.system.service.busi.SearchBoxBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.SearchBoxPojo;
import com.yinhetianze.systemservice.system.service.info.SearchBoxInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增轮播图
 */
@Component
public class AddSearchBoxExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SearchBoxBusiService searchBoxBusiServiceImpl;
    @Autowired
    private SearchBoxInfoService searchBoxInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SearchBoxPojo searchBoxPojo = new SearchBoxPojo();
        BeanUtils.copyProperties(model,searchBoxPojo);
        int result = searchBoxBusiServiceImpl.insertSelective(searchBoxPojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SearchBoxModel searchBoxModel = (SearchBoxModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(searchBoxInfoServiceImpl.selectSearchBoxId(searchBoxModel)>0){
            errorMessage.rejectError("searchName","BC0055","搜索名称", "搜索名称");
            return errorMessage;
        }
        if(searchBoxModel.getSort() == null){
            errorMessage.rejectError("sort",null,"排序号");
        }

        if(searchBoxModel.getSearchName() == null){
            errorMessage.rejectNull("searchName",null,"搜索名称");
            return errorMessage;
        }

        return errorMessage;
    }
}
