package com.yinhetianze.business.customer.util;

public class AccountingCustomerConstant {

    public static String changeTitleTemplate="%s变动通知";

    public static String changeContentTemplate="您的账户%s变更如下:</br>" +
            "%s:%s</br> " +
            "%s剩余:%s</br> " +
            "变更原因:%s";

    public static String drawTitleTemplate="提现审核结果";


    public static String drawPassTemplate="您的提现申请:%s 提现金额:%s 已通过!";

    public static String drawFailTemplate="您的提现申请:%s 提现金额:%s 未通过! 原因:%s";

    /**
     *
     * @param arg1  账户类型 积分/余额/消费券
     * @param arg2  当前操作描述 增加/减少
     * @param arg3  增加/减少 账户数值
     * @param arg4  账户类型 +“余额” 余额类型除外
     * @param arg5  对应账户余额数值
     * @param arg6  变更原因
     * @return
     */
    public static String  getContentTemplate(String arg1,String arg2,String arg3,String arg4,String arg5,String arg6){
        return String.format(AccountingCustomerConstant.changeContentTemplate,arg1,arg2,arg3,arg4,arg5,arg6);
    }

    /**
     *
     * @param arg1 账户类型 积分/余额/消费券
     * @return
     */
    public static String  getTitleTemplate(String arg1){
        return String.format(AccountingCustomerConstant.changeTitleTemplate,arg1);
    }


    public static void main(String[] args){



        String s = "";
        System.out.println(String.format(AccountingCustomerConstant.changeTitleTemplate,"积分"));

        System.out.println(String.format(AccountingCustomerConstant.changeContentTemplate,"积分","减少积分","2","积分余额","23","购物"));
    }


}
