package com.yinhetianze.business;

import com.yinhetianze.business.customer.executor.DoEaringScheduleExecutor;
import com.yinhetianze.business.customer.model.*;
import com.yinhetianze.business.shop.model.BusiShopBankrollModel;
import com.yinhetianze.business.shop.model.BusiShopDrawrecordModel;
import com.yinhetianze.configurations.columnfilter.JSON;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator
 * on 2018/1/28.
 */
@RestController
@RequestMapping("accounting")
public class AccountingRestController extends RestfulController
{
    //---------------------------------shop---------------------------------------

    /**--------------------------------POST start--------------------------------------**/

    /**
     * 新增 店铺 账户信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/shop/addBankroll")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object addShopBankrollInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiShopBankrollModel busiShopBankrollModelu)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("addShopBankrollInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopBankrollModelu);
    }

    /**
     * 新增店铺货款提现申请
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/shop/drawrecord")
    public Object addShopDrawrecordInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopDrawrecordModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addShopDrawrecordInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**--------------------------------POST  end--------------------------------------**/








    /**--------------------------------GET start--------------------------------------**/


    /**--------------------------------GET end--------------------------------------**/

    //---------------------------------shop---------------------------------------


    //---------------------------------customer---------------------------------------

    /**--------------------------------POST start--------------------------------------**/
    /**
     * 修改消费者/会员 账户信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/customer/updateBankroll")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object updateCustomerBankrollExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiUpdateBankrollModel busiUpdateBankrollModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("updateCustomerBankrollExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiUpdateBankrollModel);
    }
    /**
     * 账户充值
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/customer/updateCustomerRecharge")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object updateCustomerRechargeExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiUpdateBankrollModel busiUpdateBankrollModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("updateCustomerRechargeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiUpdateBankrollModel);
    }



    /**
     * 新增消费者/会员 账户信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/customer/addBankroll")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object addCustomerBankrollExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiCustomerBankrollModel busiCustomerBankrollModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("addCustomerBankrollExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerBankrollModel);
    }

    /**
     * 新增消费者/会员 账户信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/customer/deleteBankroll")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object deleteCustomerBankrollExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiCustomerBankrollModel busiCustomerBankrollModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("deleteCustomerBankrollExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerBankrollModel);
    }



    /**
     * 新增消费者/会员 提现申请 (银行卡)
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="/customer/drawrecord/add")
    public Object addCustomerDrawrecordInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerDrawrecordModel busiCustomerDrawrecordModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addCustomerDrawrecordInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerDrawrecordModel);
    }

    /**
     * 新增消费者/会员 提现申请 (微信)
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="/customer/drawrecord/wxPay")
    public Object addCustomerDrawrecordWeChatExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerDrawrecordModel busiCustomerDrawrecordModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addCustomerDrawrecordWeChatExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerDrawrecordModel);
    }




    /**
     * 新增消费者/会员摘星记录
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="/customer/starCoin")
    public Object addCustomerStarCoinExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerStarCoinInfoModel busiCustomerStarCoinInfoModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addCustomerStarCoinExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerStarCoinInfoModel);
    }

    /**
     * 后台管理-审核提现记录
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="/customer/drawRecord/manage")
    public Object updateCustomerDrawRecordExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody  BusiCustomerDrawrecordModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCustomerDrawRecordExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**--------------------------------POST  end--------------------------------------**/











    /**--------------------------------GET start--------------------------------------**/

    /**
     * 查询账户信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/bankroll")
    @JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime,id,customerId")
    public Object getCustomerBankrollInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerBankrollModel busiCustomerBankrollModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerBankrollInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerBankrollModel);
    }


    /**
     * 查询账户流水
     * @param request
     * @param response
     * @return pageSize=10&currentPage=1&customerId=&flowCategory=&flowType=&flow_number=
     */
    @GetMapping(value = "/customer/bankrollFlow/page",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type= BusiCustomerBankrollFlowModel.class,include = "id,flowCategory,flowType,amount,flowNumber,flowDescription,startTime")
    public Object getCustomerBankrollFlowInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerBankrollFlowInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerBankrollFlowModel);
    }

    /**
     * 查询账户流水详情
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/bankrollFlow/detail",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type = BusiCustomerBankrollFlowModel.class,include = "id,flowCategory,flowType,startTime,amount,flowNumber,flowDescription")
    public Object getCustomerBankrollFlowDetailExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerBankrollFlowDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerBankrollFlowModel);
    }

    /**
     * 我的-近日摘星页面数据
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/starCoin")
    public Object getCustomerStarCoinInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerStarCoinInfoModel busiCustomerStarCoinInfoModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerStarCoinInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerStarCoinInfoModel);
    }


    /**
     * 查询账户消费记录
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/consumption",produces = MediaType.APPLICATION_JSON_VALUE)
    @JSON(type=BusiCustomerConsumptionModel.class,include = "flowNumber,content,createTime")
    public Object getCustomerConsumptionListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerConsumptionModel busiCustomerConsumptionModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerConsumptionListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerConsumptionModel);
    }


    /**
     * 查询用户提现记录列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/drawRecord/list",produces = MediaType.APPLICATION_JSON_VALUE)
    @JSON(type=BusiCustomerDrawrecordModel.class,include = "id,drawAmount,bankNumber,applyTime,auditStatus,receiveUser,drawNumber,reason,payType")
    public Object getCustomerDrawRecordListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerDrawrecordModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerDrawRecordListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询用户提现记录列表(后台)
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/drawRecord/list/manage")
    public Object getCustomerDrawRecordManageExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerDrawrecordModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerDrawRecordManageExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询提现详情
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/drawRecord/detail",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type = BusiCustomerDrawrecordModel.class,include = "id,drawType,drawAmount,serviceCharge,finalAmount,bankNumber,applyTime,receiveUser,drawNumber,reason,payType,auditStatus")
    public Object getCustomerDrawRecordDetailExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerDrawrecordModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerDrawRecordDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    /**
     * 查询提现手续费
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/drawRecord/serviceCharge")
    public Object getDrawServiceChargeExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerBankrollModel busiCustomerBankrollModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getDrawServiceChargeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null);
    }

    /**
     * 我的市场 查询 余额 流水 明细
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/amount/personal",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type =BusiAmountFlowModel.class,include = "id,flowCategory,flowType,startTime,amount,flowDescription,relationAmount,isGameOrder,photoUrl")
    public Object getAmountListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getAmountListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerBankrollFlowModel);
    }

    /**
     * 我的市场 查询汇总收益数据
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/amount/earing")
    public Object getCustomerEaringExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerModel busiCustomerModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerEaringExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerModel);
    }


    /**
     * 我的市场 查询一、二、拓展市场列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/amount/earingList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type =BusiCustomerEaringModel.class,include = "id,amount,phone,photoUrl,isMember,showId,gameId")
    public Object getCustomerEaringListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerEaringModel busiCustomerEaringModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerEaringListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerEaringModel);
    }


    /**
     * 我的市场 查询指定用户贡献
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/amount/detail")
    public Object getCustomerEaringDetailExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerEaringModel busiCustomerEaringModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerEaringDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerEaringModel);
    }


    /**
     * 我的市场 查询指定用户贡献列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/amount/detailList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type =BusiAmountFlowModel.class,include = "startTime,amount,relationAmount,isGameOrder")
    public Object getCustomerEaringDetailListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerEaringDetailListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerBankrollFlowModel);
    }


    /**
     * 我的市场 手动触发余额统计定时任务
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/customer/amount/doEaringSchedule")
    public Object doEaringScheduleExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel,String time)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("doEaringScheduleExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,time);
    }

    /**--------------------------------GET end--------------------------------------**/
    //---------------------------------customer---------------------------------------

}
