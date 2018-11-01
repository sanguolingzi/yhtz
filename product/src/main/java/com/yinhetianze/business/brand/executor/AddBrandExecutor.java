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
import java.util.Date;

/**
 * 获取分类列表的executor
 */
@Service
public class AddBrandExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private BrandBusiService brandBusiServiceImpl;

    @Autowired
    private BrandInfoService brandInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

//    @Value("${brandImagePath}")
//    private String brandImagePath;
//
//    @Value("${fileUploadPath}")
//    private String fileUploadPath;
//
//    @Value("${ossRootPath}")
//    private String ossRootPath;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {

        BrandModel brandModel = (BrandModel) model;
        BrandPojo pojoParam = new BrandPojo();

        // 品牌名称
        if (CommonUtil.isNotEmpty(brandModel.getBrandName()))
        {
            pojoParam.setBrandName(brandModel.getBrandName());
        }
        else
        {
            throw new BusinessException("品牌名称不能为空");
        }

        // 品牌名称唯一校验
        Object obj = brandInfoServiceImpl.findBrand(pojoParam);
        if (CommonUtil.isNotEmpty(obj))
        {
            throw new BusinessException("当前品牌名称已经存在");
        }

        // 品牌首字母，默认设置为0
        if (CommonUtil.isNotEmpty(brandModel.getFirstWord()))
        {
            pojoParam.setFirstWord(brandModel.getFirstWord());
        }
        else
        {
            pojoParam.setFirstWord("0");
        }

        // 品牌标题不能为空
        if (CommonUtil.isNotEmpty(brandModel.getTitle()))
        {
            pojoParam.setTitle(brandModel.getTitle());
        }
        else
        {
            throw new BusinessException("品牌标题不能为空");
        }

        // 排序编号，默认为20
        if (CommonUtil.isNotEmpty(brandModel.getSort()))
        {
            pojoParam.setSort(brandModel.getSort());
        }
        else
        {
            pojoParam.setSort(20);
        }

        // 默认设置显示状态为1
        if (CommonUtil.isNotEmpty(brandModel.getIsShow()))
        {
            pojoParam.setIsShow(brandModel.getIsShow());
        }
        else
        {
            pojoParam.setIsShow((short)1);
        }

        // 可以为空的参数
        //pojoParam.setBrandBigImg(brandModel.getBigImg());
        pojoParam.setBrandSmallImg(brandModel.getSmallImg());
        pojoParam.setSynopsis(brandModel.getSynopsis());
        pojoParam.setCreateTime(new Date()); // 创建时间
        pojoParam.setDescription(brandModel.getDescription());

        try{
            String brandImagePath = sysPropertiesUtils.getStringValue("brandImagePath");
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String productOssRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");

            String ossPath = productOssRootPath+brandImagePath;

            String storeFilePath = fileUploadPath + brandImagePath;
            LoggerUtil.info(AddBrandExecutor.class, "brand add read local file "+storeFilePath+File.separatorChar+brandModel.getBigImg());

            if(CommonUtil.isNotEmpty(brandModel.getBigImg())){
                pojoParam.setBrandBigImg(brandModel.getBigImg());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,brandModel.getBigImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    throw new BusinessException("添加品牌未成功");
                //删除本地文件
                file.delete();
                pojoParam.setBrandBigImg(key);
                LoggerUtil.info(AddBrandExecutor.class, "brand add read local file "+storeFilePath+File.separatorChar+brandModel.getSmallImg());
            }
            if(CommonUtil.isNotEmpty(brandModel.getSmallImg())){
                //读取本地文件
                File smallFile = FileUtil.loadFile(storeFilePath,brandModel.getSmallImg());
                //上传oss
                String smallKey = ossFileUpload.fileUpload(smallFile, ossPath);
                if(smallKey == null)
                    throw new BusinessException("添加品牌未成功");
                //删除本地文件
                smallFile.delete();
                pojoParam.setBrandSmallImg(smallKey);


            }



            // 执行添加分类信息
            int result = brandBusiServiceImpl.addBrand(pojoParam);
            if (result <= 0)
            {
                LoggerUtil.info(AddBrandExecutor.class, "没有成功添加品牌信息");
                throw new BusinessException("添加品牌未成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(AddBrandExecutor.class, e);
            throw new BusinessException(e);
        }

        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        BrandModel brandModel = (BrandModel) model;

        // 品牌名称不能为空
        if (CommonUtil.isEmpty(brandModel.getBrandName()))
        {
            errorMessage.rejectError("brandName", "BC0006","品牌名称");
            return errorMessage;
        }

        // 品牌标题不能为空
        if (CommonUtil.isEmpty(brandModel.getTitle()))
        {
            errorMessage.rejectError("title", "BC0006","品牌标题");
            return errorMessage;
        }

        return errorMessage;
    }
}
