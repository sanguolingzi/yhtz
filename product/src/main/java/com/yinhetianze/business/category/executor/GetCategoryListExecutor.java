package com.yinhetianze.business.category.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.category.CategoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取分类列表的executor
 */
@Service
public class GetCategoryListExecutor extends AbstractRestBusiExecutor<PageData<CategoryPojo>>
{
    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Override
    protected PageData<CategoryPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        CategoryModel cateModel = (CategoryModel) model;
        Map<String, Object> cateParam = new HashMap<>();

        // 设置商品分类名称条件
        if (CommonUtil.isNotEmpty(cateModel.getCateName()))
        {
            try {
                String cateName= URLDecoder.decode(cateModel.getCateName(),"UTF-8");
                cateParam.put("cateName", cateName);
            }catch (Exception e){
                LoggerUtil.error(GetCategoryListExecutor.class,e);
            }
        }else{
            // 设置商品分类父级分类ID条件
            if (CommonUtil.isNotEmpty(cateModel.getParentId()))
            {
                cateParam.put("parentId", cateModel.getParentId());
            }
        }
        // 设置是否显示条件
        if (CommonUtil.isNotEmpty(cateModel.getIsShow()))
        {
            cateParam.put("isShow", cateModel.getIsShow());
        }
        // 设置分类所属终端条件
        if (CommonUtil.isNotEmpty(cateModel.getCateType()))
        {
            cateParam.put("cateType", cateModel.getCateType());
        }
        // 分类级别
        if (CommonUtil.isNotEmpty(cateModel.getCateLevel()))
        {
            cateParam.put("cateLevel", cateModel.getCateLevel());
        }
        if(CommonUtil.isNotEmpty(cateModel.getCateBelong())){
            cateParam.put("cateBelong",cateModel.getCateBelong());
        }

        List<CategoryPojo> pojoList = null;
        try
        {
            // 分页
            PageHelper.startPage(cateModel.getCurrentPage(), cateModel.getPageSize());

            // 执行查询
            pojoList = categoryInfoServiceImpl.getCategoryList(cateParam);
            PageInfo<CategoryPojo> pageInfo = new PageInfo<>(pojoList);

            // 分页结果
            PageData<CategoryPojo> pageData = new PageData<CategoryPojo>(pageInfo.getList(), pageInfo.getTotal());
            return pageData;
        }
        catch (Exception e)
        {
            LoggerUtil.error(GetCategoryListExecutor.class, e);
            throw new BusinessException(e);
        }

    }
}
