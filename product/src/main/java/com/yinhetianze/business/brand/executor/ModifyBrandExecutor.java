package com.yinhetianze.business.brand.executor;

import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.brand.service.BrandBusiService;
import com.yinhetianze.business.brand.service.BrandInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.brand.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 获取分类列表的executor
 */
@Service
public class ModifyBrandExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private BrandInfoService brandInfoServiceImpl;

    @Autowired
    private BrandBusiService brandBusiServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    /*@Value("${brandImagePath}")
    private String brandImagePath;

    @Value("${fileUploadPath}")
    private String fileUploadPath;

    @Value("${ossRootPath}")
    private String ossRootPath;*/

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        BrandModel brandModel = (BrandModel) model;
        BrandPojo pojoParam = new BrandPojo();

        // 品牌ID
        if (CommonUtil.isEmpty(brandModel.getBrandId()))
        {
            throw new BusinessException("品牌ID不能为空");
        }
        else
        {
            pojoParam.setId(brandModel.getBrandId());
        }

        // 进行修改过的标志
        boolean isNotUpdated = true;
        // 获取当前品牌信息，不存在则抛出异常
        pojoParam = brandInfoServiceImpl.findBrand(pojoParam);
        if (CommonUtil.isEmpty(pojoParam))
        {
            throw new BusinessException("当前品牌信息不存在");
        }

        // 是否显示
        if (CommonUtil.isNotEmpty(brandModel.getIsShow()))
        {
            pojoParam.setIsShow(brandModel.getIsShow());
            isNotUpdated = false;
        }

        // 品牌标题
        if (CommonUtil.isNotEmpty(brandModel.getTitle()))
        {
            pojoParam.setTitle(brandModel.getTitle());
            isNotUpdated = false;
        }

        // 品牌名称
        if (CommonUtil.isNotEmpty(brandModel.getBrandName()))
        {
            pojoParam.setBrandName(brandModel.getBrandName());
            isNotUpdated = false;
        }
        // 品牌简介
        if (CommonUtil.isNotEmpty(brandModel.getSynopsis()))
        {
            pojoParam.setSynopsis(brandModel.getSynopsis());
            isNotUpdated = false;
        }

        // 品牌排序
        if (CommonUtil.isNotEmpty(brandModel.getSort()))
        {
            pojoParam.setSort(brandModel.getSort());
            isNotUpdated = false;
        }

        // 品牌描述详情
        if (CommonUtil.isNotEmpty(brandModel.getDescription()))
        {
            pojoParam.setDescription(brandModel.getDescription());
            isNotUpdated = false;
        }

        // 品牌首字母
        if (CommonUtil.isNotEmpty(brandModel.getFirstWord()))
        {
            pojoParam.setFirstWord(brandModel.getFirstWord());
            isNotUpdated = false;
        }

        //品牌描述
        if (CommonUtil.isNotEmpty(brandModel.getDescription()))
        {
            pojoParam.setDescription(brandModel.getDescription());
            isNotUpdated = false;
        }

        if(CommonUtil.isNotEmpty(brandModel.getBigImg())){
            isNotUpdated = false;
        }

        if(CommonUtil.isNotEmpty(brandModel.getSmallImg())){
            isNotUpdated = false;
        }

        // 如果没有进行修改则不做执行
        if (isNotUpdated)
        {
            throw new BusinessException("没有进行任何修改");
        }

        try
        {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String ossRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
            String brandImagePath = sysPropertiesUtils.getStringValue("brandImagePath");
            String ossPath = ossRootPath+brandImagePath;
            String storeFilePath = fileUploadPath + brandImagePath;
            if(CommonUtil.isEmpty(pojoParam.getBrandBigImg()) && CommonUtil.isNotEmpty(brandModel.getBigImg())  || CommonUtil.isNotEmpty(brandModel.getBigImg()) &&
                    !pojoParam.getBrandBigImg().equals((brandModel.getBigImg()))){

                LoggerUtil.info(AddBrandExecutor.class, "brand update read local file "+storeFilePath+File.separatorChar+brandModel.getBigImg());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,brandModel.getBigImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    throw new BusinessException("修改品牌未成功");
                //删除本地文件
                file.delete();
                pojoParam.setBrandBigImg(key);
            }

            if(CommonUtil.isEmpty(pojoParam.getBrandSmallImg()) && CommonUtil.isNotEmpty(brandModel.getSmallImg())  || CommonUtil.isNotEmpty(brandModel.getSmallImg()) &&
                    !pojoParam.getBrandSmallImg().equals((brandModel.getSmallImg()))){

                LoggerUtil.info(AddBrandExecutor.class, "brand update read local file "+storeFilePath+File.separatorChar+brandModel.getSmallImg());
                //读取本地文件
                File smallFile = FileUtil.loadFile(storeFilePath,brandModel.getSmallImg());
                //上传oss
                String smallKey = ossFileUpload.fileUpload(smallFile, ossPath);
                if(smallKey == null)
                    throw new BusinessException("修改品牌未成功");
                //删除本地文件
                smallFile.delete();
                pojoParam.setBrandSmallImg(smallKey);
            }

            int result = brandBusiServiceImpl.modifyBrand(pojoParam);
            if (result <= 0)
            {
                throw new BusinessException("修改品牌没有成功");
            }
        }
        catch (Exception e)
        {
           LoggerUtil.error(ModifyBrandExecutor.class, e);
            throw new BusinessException(e);
        }

        return "success";
    }

    @Override
    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        BrandModel brandModel = (BrandModel) model;

        // 修改的品牌id不能为空
        if (CommonUtil.isEmpty(brandModel.getBrandId()))
        {
            errors.rejectError("brandId", "BC0006", "品牌id");
        }

        /*// 品牌名称
        if (CommonUtil.isEmpty(brandModel.getBrandName()))
        {
            errors.rejectError("brandName", "9999", new Object[]{});
        }

        // 品牌标题
        if (CommonUtil.isEmpty(brandModel.getTitle()))
        {
            errors.rejectError("title", "9999", new Object[]{});
        }*/

        return errors;
    }
}
