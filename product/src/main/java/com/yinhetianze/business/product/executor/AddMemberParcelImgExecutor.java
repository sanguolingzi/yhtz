package com.yinhetianze.business.product.executor;


import com.yinhetianze.business.product.model.MemberParcelImgModel;
import com.yinhetianze.business.product.service.MemberParcelImgBusiService;
import com.yinhetianze.business.product.service.MemberParcelImgInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.pojo.product.MemberParcelImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddMemberParcelImgExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MemberParcelImgBusiService memberParcelImgBusiServiceImpl;

    @Autowired
    private MemberParcelImgInfoService memberParcelImgInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String categoryImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            MemberParcelImgModel memberParcelImgModel=(MemberParcelImgModel)model;
            MemberParcelImgPojo memberParcelImgPojoParam=new MemberParcelImgPojo();
            memberParcelImgPojoParam.setMemberParcelId(memberParcelImgModel.getMemberParcelId());
            memberParcelImgPojoParam.setDelFlag((short)0);
            //查询当前礼包是否已有轮播图
            List<MemberParcelImgPojo> memberParcelImgPojoList=memberParcelImgInfoServiceImpl.selectMemberParcelImgList(memberParcelImgPojoParam);
            //前台已有图片数组一
            String [] arr1=memberParcelImgModel.getMemberParcelName();
            if(CommonUtil.isNotNull(memberParcelImgPojoList) && memberParcelImgPojoList.size()>0 ){
                List<MemberParcelImgPojo>  deleteImg =memberParcelImgPojoList.stream().filter(
                        memberParcelImg ->{
                            return Arrays.stream(arr1).noneMatch(
                                    p ->{
                                        return (memberParcelImg.getParcelImg().split("/")[memberParcelImg.getParcelImg().split("/").length-1].equals(p));
                                    }
                            );
                        }
                ).collect(Collectors.toList());
                //后台剩余的数组进行删除操作
                if(CommonUtil.isNotEmpty(deleteImg)){
                    deleteImg.stream().forEach(memberParcelImgPojo->{
                        memberParcelImgPojo.setDelFlag((short)1);
                        memberParcelImgBusiServiceImpl.updateMemberParcelImgList(memberParcelImgPojo);
                    });
                }
            }
            //前台传递数组判断
            List<String> frontImgList = Arrays.stream(arr1).filter(p2->{
                        return memberParcelImgPojoList.stream().noneMatch(memberParcelImg->{
                            return (p2.equals(memberParcelImg.getParcelImg().split("/")[memberParcelImg.getParcelImg().split("/").length-1]));
                        });
                    }
            ).collect(Collectors.toList());

            String ossPath = categoryImagePath;
            String storeFilePath = fileUploadPath + categoryImagePath;
            //获取地址名称数组
            if(frontImgList.size()>0){
                for (int i=0; i<frontImgList.size();i++){
                    MemberParcelImgPojo  memberParcelImgPojo =new MemberParcelImgPojo();
                    memberParcelImgPojo.setMemberParcelId(memberParcelImgModel.getMemberParcelId());
                    //读取本地文件
                    File file = FileUtil.loadFile(storeFilePath,frontImgList.get(i));
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                    //删除本地文件
                    file.delete();
                    memberParcelImgPojo.setParcelImg(key);
                    memberParcelImgPojo.setSort((short)i);
                    int result=memberParcelImgBusiServiceImpl.insertSelective(memberParcelImgPojo);
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
