package com.yinhetianze.business.unit.executor;

import com.yinhetianze.business.unit.model.UnitModel;
import com.yinhetianze.business.unit.service.UnitInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.unit.UnitPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据ID查询商品计量单位
 */
@Service
public class GetUnitExecutor extends AbstractRestBusiExecutor<UnitPojo>
{
    @Autowired
    private UnitInfoService unitInfoServiceImpl;

    @Override
    protected UnitPojo executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        UnitModel unitModel = (UnitModel) model;

        if (CommonUtil.isEmpty(unitModel.getUnitId()))
        {
            throw new BusinessException("商品计量单位ID不能为空");
        }

        try
        {
            UnitPojo pojo = new UnitPojo();
            pojo.setId(unitModel.getUnitId());
            pojo = unitInfoServiceImpl.findUnit(pojo);
            if (CommonUtil.isEmpty(pojo))
            {
                throw new BusinessException("没有匹配的计量单位");
            }

            return pojo;
        }
        catch (Exception e)
        {
            LoggerUtil.error(GetUnitExecutor.class, e);
            throw new BusinessException("查询商品单位信息失败");
        }

    }

}
