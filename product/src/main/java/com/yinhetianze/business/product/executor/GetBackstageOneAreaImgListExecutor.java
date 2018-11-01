package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.OneAreaImgModel;
import com.yinhetianze.business.product.service.OneAreaImgInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.product.OneAreaImgPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class GetBackstageOneAreaImgListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OneAreaImgInfoService oneAreaImgInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OneAreaImgModel oneAreaImgModel=(OneAreaImgModel)model;
        OneAreaImgPojo oneAreaImgPojo=new OneAreaImgPojo();
        BeanUtils.copyProperties(oneAreaImgModel,oneAreaImgPojo);
        oneAreaImgPojo.setDelFlag((short)0);
        List<OneAreaImgPojo> oneAreaImgPojoList=oneAreaImgInfoServiceImpl.selectOneAreaImgList(oneAreaImgPojo);
        return oneAreaImgPojoList;
    }
}
