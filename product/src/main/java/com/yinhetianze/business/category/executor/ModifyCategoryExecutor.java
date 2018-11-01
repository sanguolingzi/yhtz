package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.business.category.service.CategoryBusiService;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.category.CategoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 获取分类列表的executor
 */
@Service
public class ModifyCategoryExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Autowired
    private CategoryBusiService categoryBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
        String ossRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
        String categoryImagePath = sysPropertiesUtils.getStringValue("categoryImagePath");
        CategoryModel cateModel = (CategoryModel) model;
        CategoryPojo pojoParam = new CategoryPojo();
        pojoParam.setCreateTime(new Date()); // 创建时间
        pojoParam.setCreateUser(cateModel.getCreateUser()); // 操作员id
        //处理分类图标
        //上传商品主图至oss
        if(CommonUtil.isNotEmpty(cateModel.getCateImg())){
            try {
                String ossPath = ossRootPath + categoryImagePath;
                String storeFilePath = fileUploadPath + categoryImagePath;
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath, cateModel.getCateImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                cateModel.setCateImg(key);
                if (key == null)
                    throw new BusinessException("上传不成功oss地址"+ossPath+"本地文件地址"+storeFilePath+"文件对象"+file+key);
                // LoggerUtil.error(AddCategoryExecutor.class,"上传不成功");
            }catch (Exception e) {
                if(e instanceof BusinessException){
                    LoggerUtil.error (AddCategoryExecutor.class,((BusinessException)e).toString());
                }
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }

        pojoParam.setUpdateTime(new Date());

        // 修改的分类ID
        if (CommonUtil.isNotEmpty(cateModel.getId()))
        {
            pojoParam.setId(cateModel.getId());
        }
        else
        {
            throw new BusinessException("分类ID不能为空");
        }

        // 修改的PC端分类名称
        if (CommonUtil.isNotEmpty(cateModel.getCateName()))
        {
            pojoParam.setCateName(cateModel.getCateName());
        }
        else
        {
            throw new BusinessException("PC端分类名称不能为空");
        }
        // 修改的手机端分类名称
        if(CommonUtil.isNotEmpty(cateModel.getCateMobileName())){
            pojoParam.setCateMobileName(cateModel.getCateMobileName());
        }else{
            throw new BusinessException("手机端分类名称不能为空");
        }

        // 修改的分类父级ID
        if (CommonUtil.isNotEmpty(cateModel.getParentId()) && cateModel.getParentId() > 0)
        {
            // 获取当前选择的父级分类信息
            CategoryPojo parentPojo = new CategoryPojo();
            parentPojo.setId(cateModel.getParentId());
            parentPojo = categoryInfoServiceImpl.findCategory(parentPojo);
            if (CommonUtil.isNull(parentPojo))
            {
                throw new BusinessException("父级分类不存在");
            }
            // 如果父级分类不是通用分类0，则判断当前子类是否与父类的使用终端是一致的，否则抛出异常
            else if (parentPojo.getCateType() != 0 && cateModel.getCateType() != parentPojo.getCateType())
            {
                throw new BusinessException("当前分类与父级分类的使用端不一致");
            }

            // 全局固定3级分类，父类超过3级分类则抛出异常
            if (parentPojo.getCateLevel() >= 3)
            {
                throw new BusinessException("父级分类信息错误");
            }

            pojoParam.setParentId(cateModel.getParentId()); // 上级ID
            pojoParam.setIsLeaf((short)1); // 必须是子分类
            // 根据父级分类等级设置当前子类等级
            if (parentPojo.getCateLevel() == 1)
            {
                pojoParam.setCateLevel((short)2);
            }
            else if (parentPojo.getCateLevel() == 2)
            {
                pojoParam.setCateLevel((short)3);
            }
            else
            {
                throw new BusinessException("商品分类归属有误，请检查分类层级关系");
            }
        }
        else
        {
            pojoParam.setCateLevel((short)1); // 设置为1级分类
            pojoParam.setIsLeaf((short)0); // 设置为不是子分类
        }
        //分类名称
        if(CommonUtil.isNotEmpty(cateModel.getCateBelong())){
            pojoParam.setCateBelong(cateModel.getCateBelong());
        }else{
            throw new BusinessException("分类名称不能为空");
        }

        // 是否显示
        if (CommonUtil.isNotEmpty(cateModel.getIsShow()))
        {
            pojoParam.setIsShow(cateModel.getIsShow());
        }
        else
        {
            pojoParam.setIsShow((short)0); // 默认设置为显示
        }


        // 所属终端
        if (CommonUtil.isNotEmpty(cateModel.getCateType()))
        {
            pojoParam.setCateType(cateModel.getCateType());
        }
        else
        {
            pojoParam.setCateType((short)0); // 默认设置为所有端使用
        }

        // 执行修改的操作员
        if (CommonUtil.isNotEmpty(cateModel.getUpdateUser()))
        {
            pojoParam.setUpdateUser(cateModel.getUpdateUser());
        }
        // 设置分类图标
        if (CommonUtil.isNotEmpty(cateModel.getCateImg()))
        {
            pojoParam.setCateImg(cateModel.getCateImg());
        }
        pojoParam.setShoppingGuide(cateModel.getShoppingGuide());
        try
        {
            int result = categoryBusiServiceImpl.modifyCategory(pojoParam);
            if (result <= 0)
            {
                throw new BusinessException("修改分类没有成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(ModifyCategoryExecutor.class, e);
            throw new BusinessException(e);
        }

        return null;
    }

    @Override
    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        CategoryModel cateModel = (CategoryModel) model;
        CategoryPojo categoryPojo=new CategoryPojo();
        categoryPojo.setCateName(cateModel.getCateName());
        categoryPojo.setDelFlag((short)0);
        categoryPojo=categoryInfoServiceImpl.selectCategory(categoryPojo);
        if(categoryPojo != null
                && categoryPojo.getId().intValue() != cateModel.getId().intValue()){
            errors.rejectError("searchName","BC0055","分类名称", "分类名称");
            return errors;
        }

        // 修改的分类id不能为空
        if (CommonUtil.isEmpty(cateModel.getId()))
        {
            errors.rejectNull("id", null, "分类Id");
        }

        // 分类名称不能为空
        if (CommonUtil.isEmpty(cateModel.getCateName()))
        {
            errors.rejectNull("cateName", null, "分类名称");
        }

        return errors;
    }
}
