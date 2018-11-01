package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.MemberParcelModel;
import com.yinhetianze.business.product.service.MemberParcelBusiService;
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
import com.yinhetianze.pojo.product.MemberParcelPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class AddMemberParcelExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private MemberParcelBusiService memberParcelBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MemberParcelModel memberParcelModel=(MemberParcelModel)model;
        MemberParcelPojo memberParcelPojo=new MemberParcelPojo();
        BeanUtils.copyProperties(memberParcelModel,memberParcelPojo);
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            String ossPath = goodsImagePath;
            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(AddMemberParcelExecutor.class, "MemberParcel add read local file "+storeFilePath+ File.separatorChar+memberParcelModel.getParcelImg());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,memberParcelModel.getParcelImg());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            memberParcelPojo.setParcelImg(key);
        }catch (Exception e){
            LoggerUtil.error(AddMemberParcelExecutor.class,e.getMessage());
        }
        String memberSpeci = sysPropertiesUtils.getStringValue("memberSpeci");
        memberParcelPojo.setParcelSpeci(memberSpeci);
        int shopId = Integer.parseInt(sysPropertiesUtils.getStringValue("shopId"));
        memberParcelPojo.setShopId(shopId);
        int result=memberParcelBusiServiceImpl.addMemberParcel(memberParcelPojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success" ;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        MemberParcelModel memberParcelModel=(MemberParcelModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(memberParcelModel.getParcelName())){
            errorMessage.rejectNull("parcelName","null","礼包名称");
        }
        if(CommonUtil.isEmpty(memberParcelModel.getParcelTitle())){
            errorMessage.rejectNull("parcelTitle","null","礼包标题");
        }
        if(CommonUtil.isEmpty(memberParcelModel.getParcelImg())){
            errorMessage.rejectNull("parcelImg","null","礼包主图");
        }
        if(CommonUtil.isEmpty(memberParcelModel.getSort())){
            errorMessage.rejectNull("sort","null","排序");
        }
        if(CommonUtil.isEmpty(memberParcelModel.getIsShow())){
            errorMessage.rejectNull("isShow","null","是否显示");
        }
        if(CommonUtil.isEmpty(memberParcelModel.getParcelPrice())){
            errorMessage.rejectNull("parcelPrice","null","商品销售价");
        }
        if(CommonUtil.isEmpty(memberParcelModel.getMarketPrice())){
            errorMessage.rejectNull("marketPrice","null","商品市场价");
        }
        if(CommonUtil.isEmpty(memberParcelModel.getNumber())){
            errorMessage.rejectNull("number","null","房卡数量");
        }
        return errorMessage;
    }
}
