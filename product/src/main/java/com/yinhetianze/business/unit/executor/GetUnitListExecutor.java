package com.yinhetianze.business.unit.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.unit.model.UnitModel;
import com.yinhetianze.business.unit.service.UnitInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.unit.UnitPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据条件查询商品计量单位列表
 */
@Service
public class GetUnitListExecutor extends AbstractRestBusiExecutor<PageData<UnitPojo>>
{
    @Autowired
    private UnitInfoService unitInfoServiceImpl;

    @Override
    protected PageData<UnitPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        UnitModel unitModel = (UnitModel) model;
        Map<String, Object> param = new HashMap<>();

        // 单位名称条件
        if (CommonUtil.isNotEmpty(unitModel))
        {
            param.put("unitName", unitModel.getUnitName());
        }

        // 单位ID条件
        if (CommonUtil.isNotEmpty(unitModel.getUnitId()))
        {
            param.put("unitId", unitModel.getUnitId());
        }

        // 是否显示条件
        if (CommonUtil.isNotEmpty(unitModel.getIsShow()))
        {
            param.put("isShow", unitModel.getIsShow());
        }

        List<UnitPojo> unitList = null;
        try
        {
            // 是否查询全部，否则进行分页
            if (!CommonUtil.oneZeroJudge(unitModel.getIsAll()))
            {
                PageHelper.startPage(unitModel.getCurrentPage(), unitModel.getPageSize());
            }

            // 执行查询操作
            unitList = unitInfoServiceImpl.getUnitList(param);

            // 当分页时进行分页结果处理，不分页时返回所有
            if (!CommonUtil.oneZeroJudge(unitModel.getIsAll()))
            {
                PageInfo<UnitPojo> pageInfo = new PageInfo(unitList);
                return new PageData<UnitPojo>(pageInfo.getList(), pageInfo.getTotal());
            }
            else
            {
                return new PageData<>(unitList);
            }
        }
        catch (Exception e)
        {
            // 查询发生异常，记录日志并返回异常
            LoggerUtil.error(GetUnitListExecutor.class, e);
            throw new BusinessException("查询商品计量单位列表失败");
        }
    }
}
