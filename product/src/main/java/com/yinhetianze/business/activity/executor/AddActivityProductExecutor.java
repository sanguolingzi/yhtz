package com.yinhetianze.business.activity.executor;

import com.yinhetianze.business.activity.model.ActivityProductModel;
import com.yinhetianze.business.activity.service.busi.ActivityProductBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.SysDictionaryPojo;
import com.yinhetianze.pojo.product.ActivityProductPojo;
import com.yinhetianze.systemservice.system.service.info.SysDictionaryInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class AddActivityProductExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private ActivityProductBusiService activityProductBusiServiceImpl;

    @Autowired
    private SysDictionaryInfoService sysDictionaryInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ActivityProductModel activityProductModel=(ActivityProductModel)model;
        ActivityProductPojo activityProductPojo=new ActivityProductPojo();
        BeanUtils.copyProperties(activityProductModel,activityProductPojo);
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            String ossPath = goodsImagePath;
            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(AddActivityProductExecutor.class, "activityProduct add read local file "+storeFilePath+ File.separatorChar+activityProductModel.getProdImg());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,activityProductModel.getProdImg());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            activityProductPojo.setProdImg(key);
        }catch (Exception e){
            LoggerUtil.error(AddActivityProductExecutor.class,e.getMessage());
        }
        SysDictionaryPojo sysDictionaryPojo=new SysDictionaryPojo();
        sysDictionaryPojo.setDicValue(activityProductModel.getActId());
        sysDictionaryPojo.setDicType("ACTIVITY_TYPE");
        sysDictionaryPojo=sysDictionaryInfoServiceImpl.selectSysDictionary(sysDictionaryPojo);
        if(CommonUtil.isEmpty(sysDictionaryPojo)){
            throw new BusinessException("该活动数据字典不存在");
        }
        activityProductPojo.setActName(sysDictionaryPojo.getDicName());
        String prodSpeci = sysPropertiesUtils.getStringValue("discountSpeci");
        activityProductPojo.setProdSpeci(prodSpeci);
        int shopId = Integer.parseInt(sysPropertiesUtils.getStringValue("shopId"));
        activityProductPojo.setShopId(shopId);
        int result=activityProductBusiServiceImpl.addActivityProduct(activityProductPojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }
}
