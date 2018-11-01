package com.yinhetianze.back.system.executor;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.systemservice.system.model.ClassifyModel;
import com.yinhetianze.systemservice.system.service.busi.ClassifyBusiService;
import com.yinhetianze.systemservice.system.service.info.ClassifyInfoService;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ClassifyPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Service
public class UpdateClassifyExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private ClassifyBusiService classifyBusiServiceImpl;

    @Autowired
    private ClassifyInfoService classifyInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ClassifyPojo classifyPojo=new ClassifyPojo();
        ClassifyPojo classifyPojoDto=new ClassifyPojo();
        BeanUtils.copyProperties(model,classifyPojo);
        try {
            classifyPojoDto.setId(classifyPojo.getId());
            classifyPojoDto=classifyInfoServiceImpl.selectOne(classifyPojoDto);
            int result=0;
            if(!classifyPojoDto.getClassifyImg().equals(classifyPojo.getClassifyImg())){
                //删除原图片
                //ossFileUpload.deleteFile(classifyPojoDto.getClassifyImg());


                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String helpImagePath = sysPropertiesUtils.getStringValue("helpImagePath");

                String ossPath = backOssRootPath+helpImagePath;
                String storeFilePath = fileUploadPath + ossPath;
                LoggerUtil.info(UpdateClassifyExecutor.class, "help update read local file "+storeFilePath+ File.separatorChar+classifyPojo.getClassifyImg());
                File file = FileUtil.loadFile(storeFilePath,classifyPojo.getClassifyImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                //删除本地文件
                if(key == null){
                    throw new BusinessException("修改分类图片失败");
                }
                classifyPojo.setClassifyImg(key);
                result=classifyBusiServiceImpl.updateByPrimaryKeySelective(classifyPojo);
            }else{
                result=classifyBusiServiceImpl.updateByPrimaryKeySelective(classifyPojo);
            }
            if(result!=1){
                throw new BusinessException("修改分类失败");
            }
            return "success";
        }catch (Exception e){
            LoggerUtil.error(UpdateClassifyExecutor.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        ClassifyModel classifyModel=(ClassifyModel)model;
        if(CommonUtil.isEmpty(classifyModel.getId())){
            errorMessage.rejectNull("id",null,"分类ID");
        }
        if(CommonUtil.isEmpty(classifyModel.getClassifyName())){
            errorMessage.rejectNull("classifyName",null,"分类名称");
        }
        if(CommonUtil.isEmpty(classifyModel.getClassifyImg())){
            errorMessage.rejectNull("classifyImg",null,"分类图片");
        }
        if(CommonUtil.isEmpty(classifyModel.getSort())){
            errorMessage.rejectNull("sort",null,"排序");
        }
        if(CommonUtil.isEmpty(classifyModel.getIsShow())){
            errorMessage.rejectNull("isShow",null,"是否显示");
        }
        return errorMessage;
    }
}
