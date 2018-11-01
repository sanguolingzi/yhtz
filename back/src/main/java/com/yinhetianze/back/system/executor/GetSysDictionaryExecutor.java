package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.system.model.SysDictionaryModel;
import com.yinhetianze.systemservice.system.service.info.SysDictionaryInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.SysDictionaryPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 查询错误码
 */

@Component
public class GetSysDictionaryExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysDictionaryInfoService sysDictionaryInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysDictionaryModel sysDictionaryModel = (SysDictionaryModel)model;
        if(CommonUtil.isNotEmpty(sysDictionaryModel.getDicName())){
            try {
                String dicName= URLDecoder.decode(sysDictionaryModel.getDicName(),"UTF-8");
                sysDictionaryModel.setDicName(dicName);
            }catch (Exception e){
                LoggerUtil.error(GetSysDictionaryExecutor.class, e);
            }
        }
        PageHelper.startPage(sysDictionaryModel.getCurrentPage(),sysDictionaryModel.getPageSize());
        SysDictionaryPojo sysDictionaryPojo = new SysDictionaryPojo();
        sysDictionaryModel.setDelFlag((short)0);
        BeanUtils.copyProperties(sysDictionaryModel,sysDictionaryPojo);
        PageInfo pageInfo = new PageInfo(sysDictionaryInfoServiceImpl.selectList(sysDictionaryPojo));
        return pageInfo;


    }
}
