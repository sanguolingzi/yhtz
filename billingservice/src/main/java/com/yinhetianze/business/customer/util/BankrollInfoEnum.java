package com.yinhetianze.business.customer.util;


import java.util.List;

public enum BankrollInfoEnum {


    //账户类型
    AMOUNT("余额",(short)1),
    INTEGRAL("积分",(short)2),
    STARCOIN("友旗币",(short)3),
    WECHAT("微信",(short)4),
    ALI("支付宝",(short)5),
    REWARDAMOUNT("U币",(short)6),


    //流水类型
    DRAW("提现",(short)1),
    RECOMMEND("推荐奖励余额",(short)2),//会员完成指定任务 x时间内 推荐 y会员 得到z奖励
    AMOUNTPAY("余额支付",(short)3),//
    ADDSTARCOIN("摘星获得星币",(short)4),
    STARCOINPAY("友旗币支付",(short)5),
    ADDIN("赠送积分",(short)6),
    INTEGRALTOSTARCOIN("摘星消耗积分",(short)7),
    STARCOINPAYBACK("订单友旗币退回",(short)8),
    AMOUNTPAYBACK("订单余额退回",(short)9),//
    WECHATPAY("微信支付",(short)10),
    ALIPAY("支付宝支付",(short)11),
    WECHATBACK("微信退回",(short)12),
    ALIBACK("支付宝退回",(short)13),
    GAMEADD("赠送友旗币",(short)14),
    SPREADADD("推广赚",(short)15),//普通商品推广赚
    DRAWBACK("提现退回",(short)16),
    PARTNERGIVE("合伙人返的余额",(short)17),//
    MEMBERGIVE("会员返的余额",(short)18),//游戏商品推广赚
    GAMEAMONUT("游戏任务奖励U币",(short)19),
    PAYGAMEAMOUNT("订单支付U币",(short)20),
    GAMEAMONUTBACK("订单退回U币",(short)21),
    RECOMMENDAMOUNT("推荐会员返余额",(short)22),//会员礼包返还
    REGEISTER("注册赠送U币",(short)23),
    RECOMMENDREWARD("推荐注册赠送U币",(short)24),
    SECONDMEMBERGIVE("上级会员返的余额",(short)25),//游戏商品推广赚 的 50%
    SECONDSPREADADD("上级会员返推广赚",(short)26),//普通商品推广赚50%
    // 人工充值流水记录
    MANUAL_RECHARGE_U("人工充值U币", ((short)27)),
    // 人工充值流水记录
    MANUAL_RECHARGE_YOUQI("人工充值友旗币", ((short)28)),
    // 人工充值流水记录
    MANUAL_RECHARGE_BALANCE("人工充值余额", ((short)29)),



    //收入支出
    INCOME("收入",(short)0),
    EXPENSE("支出",(short)1);

    private short value;//枚举值

    private String desc;//描述


    /**
     * 私有构造,防止被外部调用
     * @param value
     */
    private BankrollInfoEnum(String desc,short value){
        this.value=value;
        this.desc=desc;
    }

    public short getValue(){
        return value;
    }
    public String getDesc(){
        return desc;
    }


    public static String getDesc(Short value){
        if(value == null )
            return "";
        //只保留 流水类型
        List<String> exuldeList = java.util.Arrays.asList(new String[]{AMOUNT.getDesc(),INTEGRAL.getDesc(),STARCOIN.getDesc(),WECHAT.getDesc(),
                ALI.getDesc(),REWARDAMOUNT.getDesc(),INCOME.getDesc(),EXPENSE.getDesc()});

        BankrollInfoEnum[] arr = BankrollInfoEnum.values();
        for(int i=0;i<arr.length;i++){
            if(value == arr[i].getValue()
                    && !exuldeList.contains(arr[i].getDesc()))
                return arr[i].getDesc();
        }
        return "";
    }
}