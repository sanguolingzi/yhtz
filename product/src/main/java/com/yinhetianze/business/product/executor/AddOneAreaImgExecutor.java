package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.MemberParcelImgModel;
import com.yinhetianze.business.product.model.OneAreaImgModel;
import com.yinhetianze.business.product.model.OneAreaModel;
import com.yinhetianze.business.product.service.OneAreaImgBusiService;
import com.yinhetianze.business.product.service.OneAreaImgInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.pojo.product.MemberParcelImgPojo;
import com.yinhetianze.pojo.product.OneAreaImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddOneAreaImgExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OneAreaImgInfoService oneAreaImgInfoServiceImpl;

    @Autowired
    private OneAreaImgBusiService oneAreaImgBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String categoryImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            OneAreaImgModel oneAreaImgModel=(OneAreaImgModel)model;
            OneAreaImgPojo oneAreaImgPojoParam=new OneAreaImgPojo();
            oneAreaImgPojoParam.setOneAreaId(oneAreaImgModel.getOneAreaId());
            oneAreaImgPojoParam.setDelFlag((short)0);
            //查询当前商品是否已有轮播图
            List<OneAreaImgPojo> oneAreaImgPojoList=oneAreaImgInfoServiceImpl.selectOneAreaImgList(oneAreaImgPojoParam);
            //前台已有图片数组一
            String [] arr1=oneAreaImgModel.getProductName();
            if(CommonUtil.isNotNull(oneAreaImgPojoList) && oneAreaImgPojoList.size()>0 ){
                List<OneAreaImgPojo>  deleteImg =oneAreaImgPojoList.stream().filter(
                        oneAreaImg ->{
                            return Arrays.stream(arr1).noneMatch(
                                    p ->{
                                        return (oneAreaImg.getProductImg().split("/")[oneAreaImg.getProductImg().split("/").length-1].equals(p));
                                    }
                            );
                        }
                ).collect(Collectors.toList());
                //后台剩余的数组进行删除操作
                if(CommonUtil.isNotEmpty(deleteImg)){
                    deleteImg.stream().forEach(oneAreaImgPojo->{
                        oneAreaImgPojo.setDelFlag((short)1);
                        oneAreaImgBusiServiceImpl.updateOneAreaImgPojoList(oneAreaImgPojo);
                    });
                }
            }
            //前台传递数组判断
            List<String> frontImgList = Arrays.stream(arr1).filter(p2->{
                        return oneAreaImgPojoList.stream().noneMatch(oneAreaImg->{
                            return (p2.equals(oneAreaImg.getProductImg().split("/")[oneAreaImg.getProductImg().split("/").length-1]));
                        });
                    }
            ).collect(Collectors.toList());

            String ossPath = categoryImagePath;
            String storeFilePath = fileUploadPath + categoryImagePath;
            //获取地址名称数组
            if(frontImgList.size()>0){
                for (int i=0; i<frontImgList.size();i++){
                    OneAreaImgPojo oneAreaImgPojo=new OneAreaImgPojo();
                    oneAreaImgPojo.setOneAreaId(oneAreaImgModel.getOneAreaId());
                    //读取本地文件
                    File file = FileUtil.loadFile(storeFilePath,frontImgList.get(i));
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                    //删除本地文件
                    file.delete();
                    oneAreaImgPojo.setProductImg(key);
                    oneAreaImgPojo.setSort((short)i);
                    int result=oneAreaImgBusiServiceImpl.insertSelective(oneAreaImgPojo);
                    if(result <= 0)
                        throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                }
            }
            return "success";
        } catch (Exception e) {
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }
}
