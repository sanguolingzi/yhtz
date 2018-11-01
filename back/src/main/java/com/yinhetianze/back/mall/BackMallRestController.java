package com.yinhetianze.back.mall;

import com.yinhetianze.back.mall.executor.GetMallActivityForManageExecutor;
import com.yinhetianze.back.mall.executor.GetNoticeForManageExecutor;
import com.yinhetianze.back.mall.executor.GetNoticeInfoExecutor;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.systemservice.mall.model.*;
import com.yinhetianze.systemservice.system.model.DeleteModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * on 2018/04/19.
 */
@RestController
@RequestMapping("back")
public class BackMallRestController extends RestfulController
{
    /**
     * 添加商城活动
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertMallActivity")
    public Object addMallActivityExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody MallActivityModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addMallActivityExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改商城活动
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateMallActivity")
    public Object updateMallActivityExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody MallActivityModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateMallActivityExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除商城活动
     * @param request
     * @param response
     * @return
     */
    @PostMapping (value = "/deleteMallActivity")
    public Object deleteMallActivityExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteMallActivityExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response,model);
    }

    /**
     * 刷新商城活动缓存
     * @return
     */
    @GetMapping(value = "/refreshMallActivity")
    public Object refreshMallActivityCacheExecutor(HttpServletRequest request, HttpServletResponse response
            ,String refresh)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("refreshMallActivityCacheExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,refresh);
    }

    /**
     * 查询商城活动
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/mallActivity")
    public Object getMallActivityExecutor(HttpServletRequest request, HttpServletResponse response, MallActivityModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getMallActivityExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 后台管理-查询商城活动
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/mallActivity/manage")
    public Object getMallActivityForManage(HttpServletRequest request, HttpServletResponse response, MallActivityModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getMallActivityForManageExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 个人中心查询未读商城活动
     * @param request
     * @param response
     * @return
     */
    @GetMapping (value = "/unReadActivity")
    public Object getUnReadActivityExecutor(HttpServletRequest request, HttpServletResponse response, MallActivityModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getUnReadActivityExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 添加商城公告
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertNotice")
    public Object addNoticeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody NoticeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addNoticeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改商城公告
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateNotice")
    public Object updateNoticeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody NoticeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateNoticeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除商城公告
     * @param request
     * @param response
     * @return
     */
    @PostMapping (value = "/deleteNotice")
    public Object deleteNoticeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteNoticeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 刷新商城公告缓存
     * @return
     */
    @GetMapping(value = "/refreshNoticeCache")
    public Object refreshNoticeCacheExecutor(HttpServletRequest request, HttpServletResponse response
            ,String refresh)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("refreshNoticeCacheExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,refresh);
    }

    /**
     * 查询商城公告
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/notice")
    public Object getNoticeExecutor(HttpServletRequest request, HttpServletResponse response, NoticeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getNoticeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 后台管理-查询商城公告
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/notice/manage")
    public Object getNoticeForManageExecutor(HttpServletRequest request, HttpServletResponse response, NoticeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getNoticeForManageExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询商城公告
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/noticeInfo")
    public Object getNoticeInfoExecutor(HttpServletRequest request, HttpServletResponse response, NoticeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getNoticeInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }





    /**
     * 个人中心查询未读商城活动
     * @param request
     * @param response
     * @return
     */
    @GetMapping (value = "/unReadNotice")
    public Object getUnReadNoticeExecutor(HttpServletRequest request, HttpServletResponse response, NoticeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getUnReadNoticeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 添加楼层
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertSysFloor")
    public Object addSysFloorExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysFloorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysFloorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改楼层
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateSysFloor")
    public Object updateSysFloorExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysFloorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysFloorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除楼层
     * @param request
     * @param response
     * @return
     */
    @PostMapping (value = "/deleteSysFloor")
    public Object deleteSysFloorExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysFloorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询楼层
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/sysFloor")
    public Object getSysFloorExecutor(HttpServletRequest request, HttpServletResponse response, SysFloorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysFloorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * wap查询楼层列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/floorList")
    public Object getFloorListExecutor(HttpServletRequest request, HttpServletResponse response, SysFloorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getFloorListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    /**
     * 添加楼层
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertSysFloorDetail")
    public Object addSysFloorDetailExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysFloorDetailModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysFloorDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改楼层详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateSysFloorDetail")
    public Object updateSysFloorDetailExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysFloorDetailModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysFloorDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 删除楼层详情
     * @param request
     * @param response
     * @return
     */
    @PostMapping (value = "/deleteSysFloorDetail")
    public Object deleteSysFloorDetailExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysFloorDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询楼层详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/sysFloorDetail")
    public Object getSysFloorDetailExecutor(HttpServletRequest request, HttpServletResponse response, SysFloorDetailModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysFloorDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 添加频道
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertChannel")
    public Object addChannelExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ChannelModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addChannelExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改频道
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateChannel")
    public Object updateChannelExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ChannelModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateChannelExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除频道
     * @param request
     * @param response
     * @return
     */
    @PostMapping (value = "/deleteChannel")
    public Object deleteChannelExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteChannelExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询频道
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/channel")
    public Object getChannelExecutor(HttpServletRequest request, HttpServletResponse response, ChannelModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getChannelExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询商城快报
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/mallFlashreport")
    public  Object getBusiMallFlashreportExecutor(HttpServletRequest request, HttpServletResponse response , MallFlashreportModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getMallFlashreportExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 增加商城快报
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/insertMallFlashreport")
    public  Object addBusiMallFlashreportExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody MallFlashreportModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("addMallFlashreportExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 修改商品快报
     * @param request
     * @param response
     * @param model
     * @return
     */

    @PostMapping(value = "/updateMallFlashreport")
    public  Object updateBusiMallFlashreportExecutor(HttpServletRequest request, HttpServletResponse response , @RequestBody MallFlashreportModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("updateMallFlashreportExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 删除商品快报
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/deleteMallFlashreport")
    public  Object deleteBusiMallFlashreportExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody DeleteModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteMallFlashreportExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 查询楼层商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/pcFloorProductList")
    public  Object getPcFloorProductListExecutor(HttpServletRequest request, HttpServletResponse response , FloorDetailProductPageModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getPcFloorProductListExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台查询手机楼层列表(新专区)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/backstageMobileFloorList")
    public  Object getBackstageMobileFloorListExecutor(HttpServletRequest request, HttpServletResponse response , MobileFloorModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageMobileFloorListExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }
    /**
     * 后台添加手机楼层(新专区)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/addBackstageMobileFloor")
    public  Object addBackstageMobileFloorExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody MobileFloorModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("addBackstageMobileFloorExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }
    /**
     * 后台修改手机楼层(新专区)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateBackstageMobileFloor")
    public  Object updateBackstageMobileFloorExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody MobileFloorModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("updateBackstageMobileFloorExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }
    /**
     * 后台删除手机楼层(新专区)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/deleteBackstageMobileFloor")
    public  Object deleteBackstageMobileFloorExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody MobileFloorModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteBackstageMobileFloorExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台查询手机楼层列表明细(新专区)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/backstageMobileFloorDetailList")
    public  Object getBackstageMobileFloorDetailListExecutor(HttpServletRequest request, HttpServletResponse response , MobileFloorDetailModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageMobileFloorDetailListExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }
    /**
     * 后台添加手机楼层明细(新专区)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/addBackstageMobileFloorDetail")
    public  Object addBackstageMobileFloorDetailExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody MobileFloorDetailModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("addBackstageMobileFloorDetailExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }
    /**
     * 后台修改手机楼层明细(新专区)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateBackstageMobileFloorDetail")
    public  Object updateBackstageMobileFloorDetailExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody MobileFloorDetailModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("updateBackstageMobileFloorDetailExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }
    /**
     * 后台删除手机楼层明细(新专区)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/deleteBackstageMobileFloorDetail")
    public  Object deleteBackstageMobileFloorDetailExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody MobileFloorDetailModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteBackstageMobileFloorDetailExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台查询爆品专区商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/backstageDetonatingList")
    public  Object getBackstageDetonatingListExecutor(HttpServletRequest request, HttpServletResponse response , DetonatingModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageDetonatingListExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台增加爆品专区商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/addDetonating")
    public  Object addDetonatingExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody DetonatingModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("addDetonatingExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台删除爆品专区商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/deleteDetonating")
    public  Object deleteDetonatingExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody DetonatingModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteDetonatingExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台修改爆品专区商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateDetonating")
    public  Object updateDetonatingExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody DetonatingModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("updateDetonatingExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }


    /**
     * 前端查询爆品专区商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/detonatingList")
    public  Object getDetonatingListExecutor(HttpServletRequest request, HttpServletResponse response , DetonatingModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getDetonatingListExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台查询广告列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/backstageAdvertisementList")
    public  Object getBackstageAdvertisementListExecutor(HttpServletRequest request, HttpServletResponse response , AdvertisementModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageAdvertisementListExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台增加广告列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/addAdvertisement")
    public  Object addAdvertisementExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody AdvertisementModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("addAdvertisementExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台删除广告列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/deleteAdvertisement")
    public  Object deleteAdvertisementExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody AdvertisementModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteAdvertisementExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台修改广告列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateAdvertisement")
    public  Object updateAdvertisementExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody AdvertisementModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("updateAdvertisementExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台查询广告详情列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/backstageAdvertisementDetailList")
    public  Object getBackstageAdvertisementDetailListExecutor(HttpServletRequest request, HttpServletResponse response , AdvertisementDetailModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageAdvertisementDetailListExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台增加广告详情列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/addAdvertisementDetail")
    public  Object addAdvertisementDetailExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody AdvertisementDetailModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("addAdvertisementDetailExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台删除广告详情列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/deleteAdvertisementDetail")
    public  Object deleteAdvertisementDetailExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody AdvertisementDetailModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteAdvertisementDetailExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 后台修改广告详情列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateAdvertisementDetail")
    public  Object updateAdvertisementDetailExecutor(HttpServletRequest request, HttpServletResponse response ,@RequestBody AdvertisementDetailModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("updateAdvertisementDetailExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 前端查询广告列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/advertisementList")
    public  Object getAdvertisementListExecutor(HttpServletRequest request, HttpServletResponse response , AdvertisementModel model){
        //执行业务
        BusinessExecutor<String> executor=(BusinessExecutor<String>)ApplicationContextFactory.getBean("getAdvertisementListExecutor");
        return  CommonUtil.isNull(executor) ? null : executor.execute(request,response,model);
    }

    /**
     * 刷新爆品专区缓存
     * @return
     */
    @GetMapping(value = "/refreshDetonating")
    public Object refreshDetonatingCacheExecutor(HttpServletRequest request, HttpServletResponse response ,String refresh)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("refreshDetonatingCacheExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,refresh);
    }
}
