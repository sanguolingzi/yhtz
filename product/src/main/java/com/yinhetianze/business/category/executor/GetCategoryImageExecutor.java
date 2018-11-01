package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.model.ClassifyImgModel;
import com.yinhetianze.business.category.service.ClassifyImgInfoService;
import com.yinhetianze.business.product.model.ProductImgModel;
import com.yinhetianze.business.product.service.ProductImgInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.category.ClassifyImgPojo;
import com.yinhetianze.pojo.product.ProductImgPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询轮播图
 */

@Component
public class GetCategoryImageExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private ClassifyImgInfoService classifyImgInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ClassifyImgModel classifyImgModel = (ClassifyImgModel)model;
        ClassifyImgPojo classifyImgPojo = new ClassifyImgPojo();
        BeanUtils.copyProperties(classifyImgModel,classifyImgPojo);
        classifyImgPojo.setDelFlag((short) 0);
        List<ClassifyImgPojo> classifyImgPojoList = classifyImgInfoServiceImpl.selectProductImgList(classifyImgPojo);
        return classifyImgPojoList;
    }
}
