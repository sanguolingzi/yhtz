package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.shop.model.ShopCategoryModel;
import com.yinhetianze.business.shop.service.info.ShopCategoryInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.system.service.info.SysSyspropertiesInfoService;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏token获取
 */

@Component
public class GetCheckGameTokenExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        GameRecordModel gameRecordModel = (GameRecordModel)model;
        //获取系统参数配置的divisionId
        String divisionId= sysPropertiesUtils.getStringValue("divisionId");
        if(CommonUtil.isEmpty(divisionId)){
            throw new BusinessException("获取游戏专区失败");
        }
        Map gameMap=redisUserDetails.getGameToken(gameRecordModel.getGameToken());
        if(CommonUtil.isNotEmpty(gameMap)){
            gameMap.remove("gameId");
            gameMap.put("divisionId",divisionId);
            //通过pGameId 获取推荐人的推荐码
            if(CommonUtil.isNotEmpty(gameMap.get("pGameId"))){
                //查询该gameId在平台是否绑定
                BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
                busiCustomerPojo.setGameId(Integer.parseInt(gameMap.get("pGameId").toString()));
               /* BusiCustomerPojo customerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
                //通过pGameId查找用户是否是会员是否有推荐码
                if(CommonUtil.isNotEmpty(customerPojo)){
                    if(customerPojo.getIsMember()==0 && CommonUtil.isNotEmpty(customerPojo.getRecommendCode())){
                        gameMap.put("recommendCode",customerPojo.getRecommendCode());
                    }
                }*/
            }
            return gameMap;
        }else{
            return divisionId;
        }
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();

        GameRecordModel gameRecordModel = (GameRecordModel)model;

        if(CommonUtil.isEmpty(gameRecordModel.getGameToken())){
            errorMessage.rejectNull("gameToken",null,"gameToken");
            return errorMessage;
        }
        return errorMessage;
    }
}
