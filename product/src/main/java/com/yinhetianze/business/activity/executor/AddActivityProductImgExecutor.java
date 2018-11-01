package com.yinhetianze.business.activity.executor;

import com.yinhetianze.business.activity.model.ActivityProductImgModel;
import com.yinhetianze.business.activity.service.busi.ActivityProductImgBusiService;
import com.yinhetianze.business.activity.service.info.ActivityProductImgInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.pojo.product.ActivityProductImgPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddActivityProductImgExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ActivityProductImgInfoService activityProductImgInfoServiceImpl;

    @Autowired
    private ActivityProductImgBusiService activityProductImgBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String categoryImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            ActivityProductImgModel activityProductImgModel=(ActivityProductImgModel)model;
            ActivityProductImgPojo activityProductImgPojoParam=new ActivityProductImgPojo();
            activityProductImgPojoParam.setActProdId(activityProductImgModel.getActProdId());
            activityProductImgPojoParam.setDelFlag((short)0);
            //查询当前商品是否已有轮播图
            List<ActivityProductImgPojo> activityProductImgPojoList=activityProductImgInfoServiceImpl.selectOneAreaImgList(activityProductImgPojoParam);
            //前台已有图片数组一
            String [] arr1=activityProductImgModel.getProductName();
            if(CommonUtil.isNotNull(activityProductImgPojoList) && activityProductImgPojoList.size()>0 ){
                List<ActivityProductImgPojo>  deleteImg =activityProductImgPojoList.stream().filter(
                        activityProductImg ->{
                            return Arrays.stream(arr1).noneMatch(
                                    p ->{
                                        return (activityProductImg.getProdImg().split("/")[activityProductImg.getProdImg().split("/").length-1].equals(p));
                                    }
                            );
                        }
                ).collect(Collectors.toList());
                //后台剩余的数组进行删除操作
                if(CommonUtil.isNotEmpty(deleteImg)){
                    deleteImg.stream().forEach(activityProductImgPojo->{
                        activityProductImgPojo.setDelFlag((short)1);
                        activityProductImgBusiServiceImpl.updateOneAreaImgPojoList(activityProductImgPojo);
                    });
                }
            }
            //前台传递数组判断
            List<String> frontImgList = Arrays.stream(arr1).filter(p2->{
                        return activityProductImgPojoList.stream().noneMatch(activityProductImg->{
                            return (p2.equals(activityProductImg.getProdImg().split("/")[activityProductImg.getProdImg().split("/").length-1]));
                        });
                    }
            ).collect(Collectors.toList());

            String ossPath = categoryImagePath;
            String storeFilePath = fileUploadPath + categoryImagePath;
            //获取地址名称数组
            if(frontImgList.size()>0){
                for (int i=0; i<frontImgList.size();i++){
                    ActivityProductImgPojo activityProductImgPojo=new ActivityProductImgPojo();
                    BeanUtils.copyProperties(activityProductImgModel,activityProductImgPojo);
                    //读取本地文件
                    File file = FileUtil.loadFile(storeFilePath,frontImgList.get(i));
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                    //删除本地文件
                    file.delete();
                    activityProductImgPojo.setProdImg(key);
                    activityProductImgPojo.setSort((short)i);
                    int result=activityProductImgBusiServiceImpl.insertSelective(activityProductImgPojo);
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
