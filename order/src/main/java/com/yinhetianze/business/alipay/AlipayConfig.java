package com.yinhetianze.business.alipay;

public class AlipayConfig {
    // 商户appid
    public static String APPID = "2018073060771733";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDQnsU8ej3h2+Jt" +
            "OWPvpi1OMQm4wt2JHt9S07tC93jYQYZsJGi3UezC6ZqB0kJlp1JAnZqLQ2vb9P6j" +
            "86RG1++8ZKUthbnNYCKwZ7R2GWcVOyg6W1JZQxouiOCRnGVd3GC/JgN6TglAx9sM" +
            "K3sf63XufBXX2WO1Bk9f5SPW3uruqP6Ol6zYn5nFxh+02y3wBZtxeQTSuNA6+6W/" +
            "LM2XS5nanKKqKUW8Ru2Layaomsblqc6xfzqY1yktkbNXgIUM51f+QUuL/dYeXz9V" +
            "EzNBD1F7nKHWKTc+q+rEKiLT3pAMKbSWulK39WKqia4mcTGuxmgS5kccD8jUD+B3" +
            "Uh9pgcSxAgMBAAECggEBALXf5RX/gFmaqAEPCogQOOk1KUORZy+24X1FJ7mPJCRs" +
            "o7M93qkLXDzlCZVqaAZVZAj/HY+RvMOrCCCJNUupz9BPcg2Iyt44lKV0E8juBodk" +
            "5oJRwS4eqQGuxRFM5rZ4fUPH8To8NCbs1ZP7iEDgtxStyyE93YYhNSvmCWO3PdvH" +
            "liGQrtdnldbM7GwLSu8fYm+smQLyNUDeOs1Z1NNohRQo8B6PCn9VGLYbsFQ87k/7" +
            "7NjatDC8tGG73jh739EUSYjyBRB4EAdgaD8aJT9K0JdOmPLc6LWeFrTi3dylo4WW" +
            "8z2dXCquQyxmlqrfg6Zj721L+J2850ILK//qEtD94M0CgYEA/reeDs0HUNnSoYjb" +
            "LwreBMwrxcmgorX8wtKmbP92ExHwbbYBZPlGAHv//1Ckgpqm22TOMd7qjYMnKJFu" +
            "zXDGM20nWcK2BJAef93oOPSC3wD77EXUDMjm659vh9vTjDlgWVJ5/W/cWZRKx1JT" +
            "XhdYDEKfnpmO+a54ebhT/WL34TMCgYEA0au5ebvmlSBTfTMhQoc2biQDbxi+h1wp" +
            "YKktrHwVhoMSGpCuT0UdErNymTgCcU9loLRzgGfxYR/djiMXQiaWyHwP91J1YTYS" +
            "i6UXqipsiWkG7cn7HclXxpSNtxZDNcbZG0x+MYj78YnNRwVW+XUn/vFb7ZkC7kYf" +
            "jJWgmMZniosCgYAa9CU2nb2TcalXTJsdDEJYMZ7ELETiUu2vVAkmaZX+CJzvjx3w" +
            "qGwe6wA47TirVWAowS8MmfEqDmHbaiBWfwOKN5e9aNxH+VQw5bOIK1+Z54nBA9af" +
            "j7bX4iQ0NS/am8u1SAP+rJKdrBKJqw2FXGUFzxsC9m7pvuuivb2th4aBfwKBgQCl" +
            "zjkEbXUw5LlhPrfEloySmOQmTNkOzfDiwaaeyhlzn98HJvbRNQSCr2mmhMZZuMHT" +
            "TiAwoQIfHkXkixdvdniy3nj5yjmMwGf2Cn4mURo3p9iU3q84pOpG3V8cM/y+YBt8" +
            "HQeGQJ+MKL8utKhM6TrRvyhFQkUxDD8Su5ied/eVdQKBgQC8AeBqQLglCOvczw6Q" +
            "++810dKELAl11izJUywqi3H0/F7Lti4iBXTZpFFBDgI1WvJASqLbqNLHQY1iBGGR" +
            "gKjgTScOaqRZn2oNlw1j1Ta2biXPNC4NE76i8TVlktjhLIAS+KRXgfiR+kfUWM4x" +
            "3AM04vRtCnkbxL0E2unbZK77OQ==";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://guanghaun3.iask.in:45451/order/aliPay";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://guanghaun3.iask.in:45451/mobile/#/paySuccess";
    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0J7FPHo94dvibTlj76Yt" +
            "TjEJuMLdiR7fUtO7Qvd42EGGbCRot1HswumagdJCZadSQJ2ai0Nr2/T+o/OkRtfv" +
            "vGSlLYW5zWAisGe0dhlnFTsoOltSWUMaLojgkZxlXdxgvyYDek4JQMfbDCt7H+t1" +
            "7nwV19ljtQZPX+Uj1t7q7qj+jpes2J+ZxcYftNst8AWbcXkE0rjQOvulvyzNl0uZ" +
            "2pyiqilFvEbti2smqJrG5anOsX86mNcpLZGzV4CFDOdX/kFLi/3WHl8/VRMzQQ9R" +
            "e5yh1ik3PqvqxCoi096QDCm0lrpSt/ViqomuJnExrsZoEuZHHA/I1A/gd1IfaYHE" +
            "sQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
