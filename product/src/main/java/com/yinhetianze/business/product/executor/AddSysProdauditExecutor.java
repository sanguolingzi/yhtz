package com.yinhetianze.business.product.executor;

import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.yinhetianze.business.product.model.SysProdauditModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.service.SysProdauditBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.product.SysProdauditPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;

@Component
public class AddSysProdauditExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private SysProdauditBusiService sysProdauditBusiServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysProdauditModel sysProdauditModel=(SysProdauditModel)model;
        SysProdauditPojo sysProdauditPojo=new SysProdauditPojo();
        ProductPojo productPojo=new ProductPojo();

        BeanUtils.copyProperties(sysProdauditModel,sysProdauditPojo);
        productPojo.setId(sysProdauditModel.getProductId());
        productPojo.setAuditState(sysProdauditModel.getAuditStatus());
        if(sysProdauditPojo.getAuditStatus()==1){
            ProductPojo prodPojo = new ProductPojo();
            prodPojo.setId(sysProdauditModel.getProductId());
            prodPojo.setDelFlag((short)0);
            prodPojo=productInfoServiceImpl.findProduct(prodPojo);
            if(CommonUtil.isEmpty(prodPojo.getQrcode())){
                try{
                    String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                    //String fileUploadPath = "d:\\testFile";
                    String productOssRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
                    String qrcodeImagePath = sysPropertiesUtils.getStringValue("qrcodeImagePath");
                    String fileName = CommonUtil.getSerialnumber()+".png";

                    String dir = fileUploadPath+productOssRootPath+qrcodeImagePath;
                    String finalPath = dir+ File.separator+fileName;
                    String wapUrl = sysPropertiesUtils.getStringValue("wapUrl");
                    byte[] b = QrcodeUtils.createQrcode(wapUrl+"goodsDetails?id="+sysProdauditModel.getProductId(),null);
                    java.io.File fileDir = new  java.io.File(dir);
                    if(!fileDir.exists()){
                        fileDir.mkdirs();
                    }
                    java.io.File qrcodeFile = new  java.io.File(finalPath);
                    FileOutputStream fos = new FileOutputStream(qrcodeFile);
                    fos.write(b);

                    String key = ossFileUpload.fileUpload(qrcodeFile,productOssRootPath+qrcodeImagePath);
                    if(key == null){
                        throw new Exception(" oss 商品二维码文件上传失败!"+finalPath);
                    }else{
                        productPojo.setQrcode(key);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    LoggerUtil.error(AddSysProdauditExecutor.class," 商品二维码失败!  "+e.getMessage());
                }
            }

        }
        int result=sysProdauditBusiServiceImpl.addsysProdaudit(sysProdauditPojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        int results=productBusiServiceImpl.modifyProduct(productPojo);
        if(results<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);

        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysProdauditModel sysProdauditModel=(SysProdauditModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(sysProdauditModel.getProductId())){
            errorMessage.rejectNull("productId",null,"商品ID不能为空");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(sysProdauditModel.getAuditStatus())){
            errorMessage.rejectNull("auditStatus",null,"审核状态不能为空");
            return errorMessage;
        }
        return  errorMessage;
    }
}
