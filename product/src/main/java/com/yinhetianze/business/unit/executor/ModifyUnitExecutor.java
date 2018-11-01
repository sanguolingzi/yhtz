package com.yinhetianze.business.unit.executor;

import com.yinhetianze.business.unit.model.UnitModel;
import com.yinhetianze.business.unit.service.UnitBusiService;
import com.yinhetianze.business.unit.service.UnitInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.unit.UnitPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModifyUnitExecutor extends AbstractRestBusiExecutor<List>
{
    @Autowired
    private UnitInfoService unitInfoServiceImpl;

    @Autowired
    private UnitBusiService unitBusiServiceImpl;

    @Override
    protected List executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        UnitModel unitModel = (UnitModel) model;
        UnitPojo pojo = new UnitPojo();

        // 商品计量单位ID
        if (CommonUtil.isEmpty(unitModel.getUnitId()))
        {
            throw new BusinessException("商品计量单位ID不能为空");
        }
        else
        {
            // 根据unitid查询是否存在
            pojo.setId(unitModel.getUnitId());
            pojo = unitInfoServiceImpl.findUnit(pojo);
            if (CommonUtil.isEmpty(pojo))
            {
                throw new BusinessException("商品计量单位不存在");
            }
        }

        // 商品计量单位名称
        if (CommonUtil.isEmpty(unitModel.getUnitName()))
        {
            throw new BusinessException("计量单位名称不能为空");
        }
        else
        {
            Map<String, Object> param = new HashMap<>();
            param.put("unitName", unitModel.getUnitName());
            Object obj = unitInfoServiceImpl.getUnitList(param);
            if (CommonUtil.isNotEmpty(obj))
            {
                throw new BusinessException("计量单位已经存在");
            }
        }
        pojo.setUnitName(unitModel.getUnitName());

        // 是否显示
        if (CommonUtil.isNotEmpty(unitModel.getIsShow()))
        {
            pojo.setIsShow(unitModel.getIsShow());
        }

        // 排序编号
        if (CommonUtil.isNotEmpty(unitModel.getSort()))
        {
            pojo.setSort(unitModel.getSort());
        }

        try
        {
            // 执行操作
            int result = unitBusiServiceImpl.modifyUnit(pojo);
            if (result <= 0)
            {
                throw new BusinessException("添加商品计量单位未成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(ModifyUnitExecutor.class, e);
            throw new BusinessException("添加商品计量单位失败");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        UnitModel unitModel = (UnitModel) model;

        if (CommonUtil.isEmpty(unitModel.getUnitName()))
        {
            errors.rejectNull("unitName", null,"计量单位名称");
        }

        if (CommonUtil.isEmpty(unitModel.getUnitId()))
        {
            errors.rejectNull("unitId", null,"计量单位Id");
        }

        return errors;
    }
}
