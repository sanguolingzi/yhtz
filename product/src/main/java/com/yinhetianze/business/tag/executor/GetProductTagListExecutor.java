package com.yinhetianze.business.tag.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.tag.model.ProductTagModel;
import com.yinhetianze.business.tag.service.ProductTagInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.tag.ProductTagPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetProductTagListExecutor extends AbstractRestBusiExecutor<PageData<ProductTagPojo>>
{
    @Autowired
    private ProductTagInfoService productTagInfoServiceImpl;

    @Override
    protected PageData<ProductTagPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductTagModel tagModel = (ProductTagModel) model;
        Map<String, Object> tagParam = new HashMap<>();

        if (CommonUtil.isNotEmpty(tagModel.getTagName()))
        {
            tagParam.put("tagName", tagModel.getTagName());
        }
        if (CommonUtil.isNotEmpty(tagModel.getIsShow()))
        {
            tagParam.put("isShow", tagModel.getIsShow());
        }
        if (CommonUtil.isNotEmpty(tagModel.getDelFlag()))
        {
            tagParam.put("delFlag", tagModel.getDelFlag());
        }
        if (CommonUtil.isNotEmpty(tagModel.getTagId()))
        {
            tagParam.put("tagId", tagModel.getTagId());
        }

        // 返回的分页数据
        PageData<ProductTagPojo> pageData = new PageData<>();

        // 分页插件
        PageHelper.startPage(tagModel.getCurrentPage(), tagModel.getPageSize());
        // 执行查询
        List<ProductTagPojo> tagList = productTagInfoServiceImpl.getProductTagList(tagParam);
        if (CommonUtil.isNotEmpty(tagList))
        {
            // 不为空处理返回数据
            PageInfo<ProductTagPojo> pageInfo = new PageInfo<>(tagList);
            pageData.setListData(pageInfo.getList()); // 返回数据
            pageData.setTotalRecord(pageInfo.getTotal()); // 返回数据条数
        }

        return pageData;
    }
}
