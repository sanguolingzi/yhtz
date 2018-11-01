package com.yinhetianze.back.report.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.report.model.OrderReportModel;
import com.yinhetianze.systemservice.report.reportutil.ReportDateUtil;
import com.yinhetianze.systemservice.report.reportutil.ReportHandler;
import com.yinhetianze.systemservice.report.reportutil.SWorkSheetModel;
import com.yinhetianze.systemservice.report.service.info.ReportInfoService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetTotalOrderDetailReportExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private ReportInfoService reportInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map timeMap =new HashMap();
        List<OrderReportModel> list= reportInfoServiceImpl.getTotalOrderDetail(timeMap);
        if(list.size()>0){
            //处理数据
            list.forEach(
                    orderReportModel ->{
                        //商品类型处理
                        if("0".equals(orderReportModel.getIs_game_order())){
                            orderReportModel.setIs_game_order("普通商品");
                        }else if("1".equals(orderReportModel.getIs_game_order())){
                            orderReportModel.setIs_game_order("游戏商品");
                        }else if("2".equals(orderReportModel.getIs_game_order())){
                            orderReportModel.setIs_game_order("会员礼包");
                        }else if("4".equals(orderReportModel.getIs_game_order())){
                            orderReportModel.setIs_game_order("U币商品");
                        }
                        //支付时间处理
                        if(CommonUtil.isNotEmpty(orderReportModel.getPay_time())){
                               orderReportModel.setPayTime(ReportDateUtil.format(orderReportModel.getPay_time()));
                        }
                        //支付方式处理 1 支付宝 2 微信 3纯星币 4余额 5 快捷
                        if("2".equals(orderReportModel.getPay_type())){
                            orderReportModel.setPay_type("微信支付");
                        }else if("3".equals(orderReportModel.getPay_type())){
                            orderReportModel.setPay_type("友旗币");
                        }else if("4".equals(orderReportModel.getPay_type())){
                            orderReportModel.setPay_type("余额");
                        }

                    }
            );
            // 导出报表
            // 1.实例化报表处理器
            try {
                ReportHandler handler = new ReportHandler();
                // 2.创建表头内容
                List<String[]> headersList = new ArrayList<String[]>();
                // --------------------用“{}”括起的表头会加上筛选功能
                String[] headers1 = {"{订单总金额","订单号","支付金额","支付时间","支付类型","结算金额","抵扣友旗金","U币抵扣","用户id","订单类型}"};
                headersList.add(headers1);
                //报表筛选的参数
                Map<String, String>	map = new HashMap<String, String>();
                // 3.实例化报表内容（参数位置不要搞错了）
                handler.addSWorkSheet(new SWorkSheetModel("总订单统计报表", "总订单统计报表", null, headersList, list, null, "5"));
                // 4.生成报表
                Workbook wb = handler.doProduce();
                // 5.下载报表
                handler.downloadXlsFile(response, wb, "总订单统计报表.xlsx");
                // 6.释放内存
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
