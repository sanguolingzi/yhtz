package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.business.category.model.ClassifyImgModel;
import com.yinhetianze.business.category.service.CategoryBusiService;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.business.category.service.ClassifyImgBusiService;
import com.yinhetianze.business.category.service.ClassifyImgInfoService;
import com.yinhetianze.business.category.service.impl.CategoryBusiServiceImpl;
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
import com.yinhetianze.pojo.category.ClassifyImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取分类列表的executor
 */
@Service
public class AddCategoryImageExecutor extends AbstractRestBusiExecutor<String>{
    @Autowired
    private ClassifyImgBusiService classifyImgBusiServiceImpl;


    @Autowired
    private ClassifyImgInfoService classifyImgInfoServiceImpl;

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
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
            try {
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String ossRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
                String categoryImagePath = sysPropertiesUtils.getStringValue("categoryImagePath");
                ClassifyImgModel classifyImgModel = (ClassifyImgModel)model;
                ClassifyImgPojo  classifyImgPojParam = new ClassifyImgPojo();
                classifyImgPojParam.setClassifyId(classifyImgModel.getClassifyId());
                classifyImgPojParam.setDelFlag((short)0);
                //查询当前商品是否已有轮播图
                List<ClassifyImgPojo> classifyImgPojoList=classifyImgInfoServiceImpl.selectProductImgList(classifyImgPojParam);
                //前台已有图片数组一
                String [] arr1=classifyImgModel.getProductName();
                if(CommonUtil.isNotNull(classifyImgPojoList) && classifyImgPojoList.size()>0 ){
                    List<ClassifyImgPojo>  deleteImg =classifyImgPojoList.stream().filter(
                            productImg ->{
                                return Arrays.stream(arr1).noneMatch(
                                        p ->{
                                            return (productImg.getImgUrl().split("/")[productImg.getImgUrl().split("/").length-1].equals(p));
                                        }
                                );
                            }
                    ).collect(Collectors.toList());
                    //后台剩余的数组进行删除操作
                    if(CommonUtil.isNotEmpty(deleteImg)){
                        deleteImg.stream().forEach(classifyImgPojo->{
                            classifyImgPojo.setDelFlag((short)1);
                            classifyImgBusiServiceImpl.updateProductImgList(classifyImgPojo);
                        });
                    }
                }
                //前台传递数组判断
                List<String> frontImgList = Arrays.stream(arr1).filter(p2->{
                            return classifyImgPojoList.stream().noneMatch(productImg->{
                                return (p2.equals(productImg.getImgUrl().split("/")[productImg.getImgUrl().split("/").length-1]));
                            });
                        }
                ).collect(Collectors.toList());

                String ossPath = ossRootPath+categoryImagePath;
                String storeFilePath = fileUploadPath + categoryImagePath;
                //获取地址名称数组
                if(frontImgList.size()>0){
                    for (int i=0; i<frontImgList.size();i++){
                        ClassifyImgPojo  classifyImgPojo =new ClassifyImgPojo();
                        classifyImgPojo.setClassifyId(classifyImgModel.getClassifyId());
                        //读取本地文件
                        File file = FileUtil.loadFile(storeFilePath,frontImgList.get(i));
                        //上传oss
                        String key = ossFileUpload.fileUpload(file, ossPath);
                        if(key == null)
                            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                        //删除本地文件
                        //file.delete();
                        classifyImgPojo.setImgUrl(key);
                        classifyImgPojo.setSort((short) i);
                        int result=classifyImgBusiServiceImpl.insertSelective(classifyImgPojo);
                        if(result <= 0)
                            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                    }
                }
                return "success";
            } catch (Exception e) {
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage = new ErrorMessage();
        ClassifyImgModel classifyImgModel = (ClassifyImgModel) model;

        // 分类名称不能为空
        if (CommonUtil.isEmpty(classifyImgModel.getClassifyId())) {
            errorMessage.rejectNull("classifyId", null, "分类ID");
        }
        // 分类轮播图数组不能为空
        if (CommonUtil.isEmpty(classifyImgModel.getProductName())) {
            errorMessage.rejectNull("productName", null, "分类轮播图数组");
        }
        return errorMessage;
    }
}
