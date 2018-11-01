package com.yinhetianze.business.parameter.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetProductParameterListExecutor extends AbstractRestBusiExecutor<PageData<ProductParameterPojo>>
{
    @Autowired
    private ProductParameterInfoService productParameterInfoServiceImpl;

    @Override
    protected PageData<ProductParameterPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductParameterModel paramModel = (ProductParameterModel) model;
//        ProductParameterPojo pojo = new ProductParameterPojo();
        Map<String, Object> pojo = new HashMap<>();

        if (CommonUtil.isNotEmpty(paramModel.getParamName()))
        {
//            pojo.setParamName(paramModel.getParamName());
            pojo.put("paramName", paramModel.getParamName());
        }
        if (CommonUtil.isNotEmpty(paramModel.getFirstWord()))
        {
//            pojo.setFirstWord(paramModel.getFirstWord());
            pojo.put("firstWord", paramModel.getFirstWord());
        }
        if (CommonUtil.isNotEmpty(paramModel.getIsWholeLine()))
        {
//            pojo.setIsWholeLine(paramModel.getIsWholeLine());
            pojo.put("isWholeLine", paramModel.getIsWholeLine());
        }

        // 最终返回的分页数据
        PageData<ProductParameterPojo> pageData = new PageData<>();
        // 分页参数
        PageHelper.startPage(paramModel.getCurrentPage(), paramModel.getPageSize());
        // 执行查询
        List<ProductParameterPojo> paramList = productParameterInfoServiceImpl.getProductParameterList(pojo);
        if (CommonUtil.isNotEmpty(paramList))
        {
            // 分页数据结果设置
            PageInfo<ProductParameterPojo> pageInfo = new PageInfo<>(paramList);
            pageData.setListData(pageInfo.getList());
            pageData.setTotalRecord(pageInfo.getTotal());
        }

        return pageData;
    }
}
