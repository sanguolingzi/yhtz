package com.yinhetianze.business.activity.executor;

import com.yinhetianze.business.activity.model.ActivityProductModel;
import com.yinhetianze.business.activity.service.busi.ActivityProductBusiService;
import com.yinhetianze.business.activity.service.info.ActivityProductInfoService;
import com.yinhetianze.business.product.executor.UpdateOneAreaExecutor;
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
import com.yinhetianze.pojo.product.ActivityProductPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class UpdateActivityProductExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ActivityProductBusiService activityProductBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private ActivityProductInfoService activityProductInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ActivityProductModel activityProductModel=(ActivityProductModel)model;
        ActivityProductPojo activityProductPojo=new ActivityProductPojo();
        BeanUtils.copyProperties(activityProductModel,activityProductPojo);
        ActivityProductPojo dbPojo=new ActivityProductPojo();
        dbPojo.setId(activityProductModel.getId());
        dbPojo.setDelFlag((short)0);
        dbPojo=activityProductInfoServiceImpl.selectOne(dbPojo);
        try {
            if(CommonUtil.isNotEmpty(activityProductPojo.getProdImg()) && !dbPojo.getProdImg().equals(activityProductPojo.getProdImg())){
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
                String ossPath = goodsImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(UpdateActivityProductExecutor.class, "ActivityProduct update read local file "+storeFilePath+ File.separatorChar+activityProductModel.getProdImg());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,activityProductModel.getProdImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                activityProductPojo.setProdImg(key);
            }
        }catch (Exception e){
            LoggerUtil.error(UpdateActivityProductExecutor.class,e.getMessage());
        }
        String prodSpeci = sysPropertiesUtils.getStringValue("discountSpeci");
        activityProductPojo.setProdSpeci(prodSpeci);
        int shopId = Integer.parseInt(sysPropertiesUtils.getStringValue("shopId"));
        activityProductPojo.setShopId(shopId);
        int result =activityProductBusiServiceImpl.updateByPrimaryKeySelective(activityProductPojo);
        if(result<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ActivityProductModel activityProductModel=(ActivityProductModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(activityProductModel.getId())){
            errorMessage.rejectNull("id","null","商品ID");
        }
        return errorMessage;
    }
}
