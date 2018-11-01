package com.yinhetianze.business.customer.util;

public class CustomerConstant {

    public static String regeisterSufixKey="_regeister";
    public static String updPasswordSufixKey="_password";
    public static String updPhoneSufixKey="_phone";
    public static String updPayPasswordSufixKey="_payPassword";
    public static String forgetPassSufixKey="_forgetPass";
    public static String wechatBindSufixKey="_wechatbind";
    public static String userLoginSufixKey="_login";
    public static String userBindGameSufixKey="_bindgame";
    public static String currentUserOperator="_operator";
    public static String accountNotification="_account";

    //认为是系统生成用户   明文是 sys_yhtz_recommend
    public static String systemRecommend = "28c836cdd85c88a80d0ddfcd97447005";

    public static String tokenSufixKey="_token";

    public static String commonSmsCode="888888";

    public static String salt = "yhtz";

    /**
     * string.matche 为false  代表手机号格式不对
     */
    public static String phoneRegex = "^[1][3,4,5,6,7,8][0-9]{9}$";
    /**
     * 数字、英文字母、汉字
     */
    public static String nameRegex= "^[\u4e00-\u9fa5_a-zA-Z0-9]+$";
}
