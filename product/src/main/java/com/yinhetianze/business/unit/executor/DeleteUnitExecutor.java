package com.yinhetianze.business.unit.executor;

import com.yinhetianze.business.unit.model.UnitModel;
import com.yinhetianze.business.unit.service.UnitBusiService;
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
import java.util.List;

/**
 * 删除商品计量单位执行器
 */
@Service
public class DeleteUnitExecutor extends AbstractRestBusiExecutor<List>
{
    @Autowired
    private UnitBusiService unitBusiServiceImpl;

    @Override
    protected List executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        UnitModel unitModel = (UnitModel) model;

        /**
         * 商品单位ID
         */
        if (CommonUtil.isEmpty(unitModel.getUnitId()))
        {
            throw new BusinessException("商品计量单位ID不能为空");
        }

        // 实体类
        UnitPojo pojo = new UnitPojo();
        pojo.setId(unitModel.getUnitId());
        try
        {
            // 执行删除操作
            int result = unitBusiServiceImpl.deleteUnit(pojo);
            if (result <= 0)
            {
                throw new BusinessException("删除商品计量单位未成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(DeleteUnitExecutor.class, e);
            throw new BusinessException("删除商品计量单位失败");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        UnitModel unitModel = (UnitModel) model;

        if (CommonUtil.isEmpty(unitModel.getUnitId()))
        {
            errors.rejectNull("unitId", null, "计量单位Id");
        }

        return super.validate(request, model, params);
    }
}
