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
 * 修改错误码
 */

@Component
public class UpdateSearchBoxExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SearchBoxBusiService searchBoxBusiServiceImpl;

    @Autowired
    private SearchBoxInfoService searchBoxInfoServiceImpl;
    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SearchBoxPojo searchBoxPojo = new SearchBoxPojo();
        BeanUtils.copyProperties(model,searchBoxPojo);
        int result = searchBoxBusiServiceImpl.updateByPrimaryKeySelective(searchBoxPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SearchBoxModel searchBoxModel = (SearchBoxModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        SearchBoxPojo searchBoxPojo=new SearchBoxPojo();
        searchBoxPojo.setSearchName(searchBoxModel.getSearchName());
        searchBoxPojo.setDelFlag((short)0);
        searchBoxPojo=searchBoxInfoServiceImpl.selectSearchBox(searchBoxPojo);

       if(searchBoxPojo != null
               && searchBoxPojo.getId() != searchBoxModel.getId()){
           errorMessage.rejectError("searchName","BC0055","搜索名称", "搜索名称");
           return errorMessage;
       }

       if(searchBoxPojo.getId()!=searchBoxModel.getId() && searchBoxPojo.getSearchName()==searchBoxModel.getSearchName()){

       }
       if(searchBoxModel.getId() == null){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
       }
       return errorMessage;
    }
}
