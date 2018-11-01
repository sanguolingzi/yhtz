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

/**
 * 添加商品计量单位业务执行器
 */
@Service
public class AddUnitExecutor extends AbstractRestBusiExecutor<List>
{
    @Autowired
    private UnitBusiService unitBusiServiceImpl;

    @Autowired
    private UnitInfoService unitInfoServiceImpl;

    @Override
    protected List executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        UnitModel unitModel = (UnitModel) model;
        UnitPojo pojo = new UnitPojo();

        if (CommonUtil.isEmpty(unitModel.getUnitName()))
        {
            throw new BusinessException("单位不能为空");
        }
        else
        {
            // 校验计量单位是否存在
            Map<String, Object> param = new HashMap<>();
            param.put("unitName", unitModel.getUnitName());
            Object obj = unitInfoServiceImpl.getUnitList(param);
            if (CommonUtil.isNotEmpty(obj))
            {
                throw new BusinessException("当前商品计量单位已经存在");
            }
        }

        // 是否显示，默认设置为显示
        if (CommonUtil.isEmpty(unitModel.getIsShow()))
        {
            pojo.setIsShow((short)1);
        }
        else
        {
            pojo.setIsShow(unitModel.getIsShow());
        }

        // 排序，默认设置为1
        if (CommonUtil.isEmpty(unitModel.getSort()))
        {
            pojo.setSort((short)1);
        }
        else
        {
            pojo.setSort(unitModel.getSort());
        }

        // 计量单位名称与创建时间
        pojo.setUnitName(unitModel.getUnitName());
        pojo.setCreateTime(new Date());

        try
        {
            // 执行插入操作
            int result = unitBusiServiceImpl.addUnit(pojo);
            if (result <= 0)
            {
                throw new BusinessException("添加商品计量单位未成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(AddUnitExecutor.class, e);
            throw new BusinessException("添加商品计量单位失败");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        UnitModel unitModel = (UnitModel) model;

        // 单位名称不能为空
        if (CommonUtil.isEmpty(unitModel.getUnitName()))
        {
            errors.rejectNull("unitName", null, "单位名称");
        }

        return errors;
    }
}
