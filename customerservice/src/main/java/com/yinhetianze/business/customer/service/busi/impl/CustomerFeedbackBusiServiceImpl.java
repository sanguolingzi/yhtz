package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerFeedbackBusiMapper;
import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.business.customer.service.busi.CustomerFeedbackImgBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerFeedbackBusiService;
import com.yinhetianze.business.customer.service.info.CustomerFeedbackImgInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerFeedbackImgPojo;
import com.yinhetianze.pojo.customer.BusiCustomerFeedbackPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CustomerFeedbackBusiServiceImpl implements CustomerFeedbackBusiService
{
    @Autowired
    private CustomerFeedbackBusiMapper customerFeedbackBusiMapper;
    @Autowired
    private CustomerFeedbackImgBusiService customerFeedbackImgBusiServiceImpl;
    @Autowired
    private CustomerFeedbackImgInfoService customerFeedbackImgInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    public int addInfo(BusiCustomerFeedbackModel busiCustomerFeedbackModel) throws BusinessException {
        BusiCustomerFeedbackPojo busiCustomerFeedbackPojo = new BusiCustomerFeedbackPojo();
        BeanUtils.copyProperties(busiCustomerFeedbackModel,busiCustomerFeedbackPojo);
        customerFeedbackBusiMapper.insertSelective(busiCustomerFeedbackPojo);

        if(CommonUtil.isNotEmpty(busiCustomerFeedbackModel.getImgUrls())){
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String customerOssRootPath = sysPropertiesUtils.getStringValue("customerOssRootPath");
            String userFeedBackImagePath = sysPropertiesUtils.getStringValue("userFeedBackImagePath");

            String ossPath = customerOssRootPath+userFeedBackImagePath;

            String storeFilePath = fileUploadPath + ossPath;
            String[] imgUrlsArr = busiCustomerFeedbackModel.getImgUrls().split(",");

            int sortNo = 0;
            for(String url:imgUrlsArr){
                if(sortNo > 3)
                    break;

                File file = FileUtil.loadFile(storeFilePath,url);
                try{
                    String key = ossFileUpload.fileUpload(file,ossPath);
                    if(key == null)
                        throw new Exception("feedback img upload failed  id:"+busiCustomerFeedbackPojo.getId()+".....path:"+storeFilePath+File.separator+url);

                    BusiCustomerFeedbackImgPojo busiCustomerFeedbackImgPojo = new BusiCustomerFeedbackImgPojo();
                    busiCustomerFeedbackImgPojo.setImgUrl(key);
                    busiCustomerFeedbackImgPojo.setFeedbackId(busiCustomerFeedbackPojo.getId());
                    busiCustomerFeedbackImgPojo.setSortNo(Short.parseShort(String.valueOf(sortNo++)));
                    customerFeedbackImgBusiServiceImpl.insertSelective(busiCustomerFeedbackImgPojo);
                }catch(Exception e){
                    LoggerUtil.error(CustomerFeedbackBusiServiceImpl.class,e.toString());
                }
            }
        }
        return 1;
    }

    @Override
    public int deleteInfoBatch(String ids) throws BusinessException {

        String[] idsArr = ids.split(",");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        for(String id:idsArr){
            BusiCustomerFeedbackModel busiCustomerFeedbackModel = new BusiCustomerFeedbackModel();
            busiCustomerFeedbackModel.setId(Integer.parseInt(id));
            deleteInfo(busiCustomerFeedbackModel);
        }
        return 1;
    }

    @Override
    public int deleteInfo(BusiCustomerFeedbackModel busiCustomerFeedbackModel) throws BusinessException {
        BusiCustomerFeedbackPojo busiCustomerFeedbackPojo = new BusiCustomerFeedbackPojo();
        busiCustomerFeedbackPojo.setId(busiCustomerFeedbackModel.getId());
        busiCustomerFeedbackPojo.setDelFlag((short)1);
        int result = customerFeedbackBusiMapper.updateByPrimaryKeySelective(busiCustomerFeedbackPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);

        List<BusiCustomerFeedbackImgPojo> list = customerFeedbackImgInfoServiceImpl.selectByFeedbackId(busiCustomerFeedbackPojo.getId());
        for(BusiCustomerFeedbackImgPojo busiCustomerFeedbackImgPojo:list){
            busiCustomerFeedbackImgPojo.setDelFlag((short)1);
            customerFeedbackImgBusiServiceImpl.updateByPrimaryKeySelective(busiCustomerFeedbackImgPojo);
        }
        return 1;
    }
}