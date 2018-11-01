package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.OneAreaModel;
import com.yinhetianze.business.product.service.OneAreaBusiService;
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
import com.yinhetianze.pojo.product.OneAreaPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class AddOneAreaExecutor extends AbstractRestBusiExecutor {


    @Autowired
    private OneAreaBusiService oneAreaBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OneAreaModel oneAreaModel=(OneAreaModel)model;
        OneAreaPojo oneAreaPojo=new OneAreaPojo();
        BeanUtils.copyProperties(oneAreaModel,oneAreaPojo);
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            String ossPath = goodsImagePath;
            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(UpdateOneAreaExecutor.class, "OneArea add read local file "+storeFilePath+ File.separatorChar+oneAreaModel.getProductImg());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,oneAreaModel.getProductImg());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            oneAreaPojo.setProductImg(key);
        }catch (Exception e){
            LoggerUtil.error(OneAreaBusiService.class,e.getMessage());
        }
        String oneSpeci = sysPropertiesUtils.getStringValue("oneSpeci");
        oneAreaPojo.setProdSpeci(oneSpeci);
        int shopId = Integer.parseInt(sysPropertiesUtils.getStringValue("shopId"));
        oneAreaPojo.setShopId(shopId);
        int result=oneAreaBusiServiceImpl.addOneArea(oneAreaPojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success" ;
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OneAreaModel oneAreaModel=(OneAreaModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(oneAreaModel.getProdName())){
            errorMessage.rejectNull("prodName","null","商品名称");
        }
        if(CommonUtil.isEmpty(oneAreaModel.getpTitle())){
            errorMessage.rejectNull("pTitle","null","商品标题");
        }
        if(CommonUtil.isEmpty(oneAreaModel.getProductImg())){
            errorMessage.rejectNull("productImg","null","商品主图");
        }
        if(CommonUtil.isEmpty(oneAreaModel.getSort())){
            errorMessage.rejectNull("sort","null","排序");
        }
        if(CommonUtil.isEmpty(oneAreaModel.getIsShow())){
            errorMessage.rejectNull("isShow","null","是否显示");
        }
        if(CommonUtil.isEmpty(oneAreaModel.getMarketPrice())){
            errorMessage.rejectNull("marketPrice","null","展示价");
        }
        if(CommonUtil.isEmpty(oneAreaModel.getSellPrice())){
            errorMessage.rejectNull("sellPrice","null","销售价");
        }
        if(CommonUtil.isEmpty(oneAreaModel.getFreight())){
            errorMessage.rejectNull("freight","null","邮费");
        }
        return errorMessage;
    }
}
