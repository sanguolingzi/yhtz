package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductFresherModel;
import com.yinhetianze.business.product.service.ProductFresherBusiService;
import com.yinhetianze.business.product.service.ProductFresherInfoService;
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
import com.yinhetianze.pojo.product.ProductFresherPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class UpdateProductFresherExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductFresherBusiService productFresherBusiServiceImpl;

    @Autowired
    private ProductFresherInfoService productFresherInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductFresherModel productFresherModel=(ProductFresherModel)model;
        ProductFresherPojo productFresherPojo=new ProductFresherPojo();
        BeanUtils.copyProperties(productFresherModel,productFresherPojo);
        ProductFresherPojo dbPojo=new ProductFresherPojo();
        dbPojo.setId(productFresherModel.getId());
        dbPojo.setDelFlag((short)0);
        dbPojo=productFresherInfoServiceImpl.selectOne(dbPojo);
        try {
            if(CommonUtil.isNotEmpty(productFresherPojo.getProdImg()) && !dbPojo.getProdImg().equals(productFresherPojo.getProdImg())){
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
                String ossPath = goodsImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(UpdateProductFresherExecutor.class, "RewardAmount update read local file "+storeFilePath+ File.separatorChar+productFresherModel.getProdImg());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,productFresherModel.getProdImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                productFresherPojo.setProdImg(key);
            }
        }catch (Exception e){
            LoggerUtil.error(UpdateProductFresherExecutor.class,e.getMessage());
        }
        String productFresher = sysPropertiesUtils.getStringValue("productFresherSpeci");
        productFresherPojo.setProdSpeci(productFresher);
        int shopId = Integer.parseInt(sysPropertiesUtils.getStringValue("shopId"));
        productFresherPojo.setShopId(shopId);
        int result =productFresherBusiServiceImpl.updateByPrimaryKeySelective(productFresherPojo);
        if(result<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductFresherModel productFresherModel=(ProductFresherModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(productFresherModel.getId())){
            errorMessage.rejectNull("id","null","id");
        }
        if(CommonUtil.isEmpty(productFresherModel.getProdName())){
            errorMessage.rejectNull("prodName","null","商品名称");
        }
        if(CommonUtil.isEmpty(productFresherModel.getProdTitle())){
            errorMessage.rejectNull("pTitle","null","商品标题");
        }
        if(CommonUtil.isEmpty(productFresherModel.getProdImg())){
            errorMessage.rejectNull("productImg","null","商品主图");
        }
        if(CommonUtil.isEmpty(productFresherModel.getProdStorage())){
            errorMessage.rejectNull("prodStorage","null","商品库存");
        }
        if(CommonUtil.isEmpty(productFresherModel.getSort())){
            errorMessage.rejectNull("sort","null","排序");
        }
        if(CommonUtil.isEmpty(productFresherModel.getIsShow())){
            errorMessage.rejectNull("isShow","null","是否显示");
        }
        if(CommonUtil.isEmpty(productFresherModel.getuPrice())){
            errorMessage.rejectNull("uPrice","null","u币抵扣");
        }
        /*
        if(CommonUtil.isEmpty(productFresherModel.getSellPrice())){
            errorMessage.rejectNull("sellPrice","null","销售价");
        }
        */
        if(CommonUtil.isEmpty(productFresherModel.getFreight())){
            errorMessage.rejectNull("freight","null","邮费");
        }
        return errorMessage;
    }
}
