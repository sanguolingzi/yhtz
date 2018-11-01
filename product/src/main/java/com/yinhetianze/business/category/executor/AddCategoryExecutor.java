package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.business.category.service.CategoryBusiService;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.business.product.utils.ProductUtil;
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
import org.springframework.beans.factory.annotation.Value;
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
public class AddCategoryExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private CategoryBusiService categoryBusiServiceImpl;

    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

//    @Value("${fileUploadPath}")
//    private String fileUploadPath;
//
//    @Value("${ossRootPath}")
//    private String ossRootPath;
//
//    @Value("${categoryImagePath}")
//    private String categoryImagePath;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
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
        if(CommonUtil.isNotNull(cateModel.getCateImg())){
            try {
                String ossPath = ossRootPath + categoryImagePath;
                String storeFilePath = fileUploadPath + categoryImagePath;
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath, cateModel.getCateImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if (key == null)
                    throw new BusinessException("上传不成功oss地址"+ossPath+"本地文件地址"+storeFilePath+"文件对象"+file+key);
                   // LoggerUtil.error(AddCategoryExecutor.class,"上传不成功");
                cateModel.setCateImg(key);
            }catch (Exception e) {
                if(e instanceof BusinessException){
                    LoggerUtil.error (AddCategoryExecutor.class,((BusinessException)e).toString());
                }
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }
        // PC端分类名称
        if (CommonUtil.isNotEmpty(cateModel.getCateName()))
        {
            pojoParam.setCateName(cateModel.getCateName());
        }
        else
        {
            throw new BusinessException("PC端分类名称不能为空");
        }
        //手机端分类名称
        if(CommonUtil.isNotEmpty(cateModel.getCateMobileName())){
            pojoParam.setCateMobileName(cateModel.getCateMobileName());
        }else{
            throw new BusinessException("手机端分类名称不能为空");
        }

        if(CommonUtil.isNotEmpty(cateModel.getShoppingGuide())){
            pojoParam.setShoppingGuide(cateModel.getShoppingGuide());
        }

        // 上级分类id，父分类id不为空并且不为负数则查询父分类
        if (CommonUtil.isNotEmpty(cateModel.getParentId()) && cateModel.getParentId() > 0)
        {
            // 父级分类查询对象
            CategoryPojo parentCate = new CategoryPojo();
            parentCate.setId(cateModel.getParentId()); // 父级分类id
            parentCate = categoryInfoServiceImpl.findCategory(parentCate); // 查询父级分类

            if (CommonUtil.isNull(parentCate))
            {
                // 没有查询到对应的父级分类
                throw new BusinessException("父级分类不存在");
            }
            // 如果父级分类不是通用分类0，则判断当前子类是否与父类的使用终端是一致的，否则抛出异常
            else if (parentCate.getCateType() != 0 && cateModel.getCateType() != parentCate.getCateType())
            {
                throw new BusinessException("当前分类与父级分类的使用端不一致");
            }
            else
            {
                // 全局固定3级分类，父类超过3级分类则抛出异常
                if (parentCate.getCateLevel() >= 3)
                {
                    throw new BusinessException("父级分类信息错误");
                }

                // 设置父级分类信息
                pojoParam.setParentId(parentCate.getId());
                pojoParam.setIsLeaf((short)1); // 是子级分类
                if (parentCate.getCateLevel() == 2)
                {
                    pojoParam.setCateLevel((short)3);
                }
                else if (parentCate.getCateLevel() == 1)
                {
                    pojoParam.setCateLevel((short)2);
                }
                else
                {
                    throw new BusinessException("商品分类归属有误，请检查分类层级关系");
                }
            }
        }
        else
        {
            // 没有上级分类默认表示当前项为顶级分类
            pojoParam.setCateLevel((short)1);
            pojoParam.setIsLeaf((short)0); // 设置不是子分类
        }
        //商品分类
        if(CommonUtil.isNotEmpty(cateModel.getCateBelong())){
            pojoParam.setCateBelong(cateModel.getCateBelong());
        }else{
            throw new BusinessException("分类名称不能为空");
        }

        // 商品分类应用端设置，为空默认设置0，表示全部端都可以使用
        if (CommonUtil.isNotEmpty(cateModel.getCateType()))
        {
            pojoParam.setCateType(cateModel.getCateType());
        }
        else
        {
            pojoParam.setCateType((short)0);
        }

        // 默认设置显示状态为1
        if (CommonUtil.isNotEmpty(cateModel.getIsShow()))
        {
            pojoParam.setIsShow(cateModel.getIsShow());
        }
        else
        {
            pojoParam.setIsShow((short)1);
        }

        // 设置分类图标
        if (CommonUtil.isNotEmpty(cateModel.getCateImg()))
        {
            pojoParam.setCateImg(cateModel.getCateImg());
        }

        try
        {
            // 执行添加分类信息
            int result = categoryBusiServiceImpl.addCategory(pojoParam);
            if (result <= 0)
            {
                LoggerUtil.info(AddCategoryExecutor.class, "没有成功添加分类");
                throw new BusinessException("添加分类未成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(AddCategoryExecutor.class, e);
            throw new BusinessException(e);
        }

        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        CategoryModel cateModel = (CategoryModel) model;

        if(categoryInfoServiceImpl.selectCategoryid(cateModel)>0){
            errorMessage.rejectError("searchName","BC0055","分类名称", "分类名称");
            return errorMessage;
        }

        // 分类名称不能为空
        if (CommonUtil.isEmpty(cateModel.getCateName()))
        {
            errorMessage.rejectNull("cateName", null, "分类名称");
        }

        return errorMessage;
    }
}
