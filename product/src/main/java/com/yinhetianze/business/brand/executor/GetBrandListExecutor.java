package com.yinhetianze.business.brand.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.brand.service.BrandInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.brand.BrandPojo;
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
public class GetBrandListExecutor extends AbstractRestBusiExecutor<PageData<BrandPojo>>
{
    @Autowired
    private BrandInfoService brandInfoServiceImpl;

    @Override
    protected PageData<BrandPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        BrandModel brandModel = (BrandModel) model;
        Map<String, Object> brandParam = new HashMap<>();

        // 设置商品分类名称条件
        if (CommonUtil.isNotEmpty(brandModel.getBrandName()))
        {
            try {
                String brandName= URLDecoder.decode(brandModel.getBrandName(),"UTF-8");
                brandParam.put("brandName", brandName);
            }catch (Exception e){
                LoggerUtil.error(GetBrandListExecutor.class,e);
            }

        }

        // 设置商品品牌是否可见
        if (CommonUtil.isNotEmpty(brandModel.getIsShow()))
        {
            brandParam.put("isShow", brandModel.getIsShow());
        }

        // 设置排序编号
        if (CommonUtil.isNotEmpty(brandModel.getSort()))
        {
            brandParam.put("sort", brandModel.getSort());
        }

        // 商品品牌标题
        if (CommonUtil.isNotEmpty(brandModel.getTitle()))
        {
            brandParam.put("title", brandModel.getTitle());
        }

        // 品牌ID
        if (CommonUtil.isNotEmpty(brandModel.getBrandId()))
        {
            brandParam.put("brandId", brandModel.getBrandId());
        }

        // 品牌首字母
        if (CommonUtil.isNotEmpty(brandModel.getFirstWord()))
        {
            brandParam.put("firstWord", brandModel.getFirstWord());
        }

        List<BrandPojo> pojoList = null;
        try
        {
            // 分页
            if(brandModel.getIsAll() == null){
                PageHelper.startPage(brandModel.getCurrentPage(), brandModel.getPageSize());
            }

            // 执行查询
            pojoList = brandInfoServiceImpl.getBrandList(brandParam);
            PageInfo<BrandPojo> pageInfo = new PageInfo<>(pojoList);

            // 分页结果
            PageData<BrandPojo> pageData = new PageData<BrandPojo>(pageInfo.getList(), pageInfo.getTotal());
            return pageData;
        }
        catch (Exception e)
        {
            LoggerUtil.error(GetBrandListExecutor.class, e);
            throw new BusinessException(e);
        }

    }
}
