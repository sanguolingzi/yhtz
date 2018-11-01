package com.yinhetianze.business.message.util;


public enum MessageDetailInfoEnum {


    LOGISTIC("物流通知",(short)0),
    INFO("通知消息",(short)1),
    ONLINESERVICE("在线客服",(short)2),
    ACTIVE("商城活动",(short)3),
    NOTICE("商城公告",(short)4);

    private short value;//枚举值
    private String desc;//描述


    /**
     * 私有构造,防止被外部调用
     * @param value
     */
    private MessageDetailInfoEnum(String desc, short value){
        this.value=value;
        this.desc=desc;
    }

    public short getValue(){
        return value;
    }
    public String getDesc(){
        return desc;
    }
}