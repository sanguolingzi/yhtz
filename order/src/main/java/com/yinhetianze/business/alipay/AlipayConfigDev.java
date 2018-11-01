package com.yinhetianze.business.alipay;

public class AlipayConfigDev {
    // 商户appid
    public static String APPID = "2016091300502253";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCvv/uOEhLewc/8uo67CcxH+bQy5k1UhCdkt+AH2QBWyfvqJtBj8BzQ41MKHnWgIMdz/8A+jjmYX2tRliabM1NMYbew1BBgYDYRsnNLGEaAgLTm1D5XwkPCp1h0pA5iYpzWT79i8O/uKQp6h/AHQ/7iB5Wkobbo6E3JLRxurA3idAadj6QRaZmEGLpfZjVToDgcwu7nWkAtF8N8CSAinvG06YeIhoXtmYcs0X1ii3SXd3SapEqqhHCEhXpX0J7f+hzI2Qf94bGm2E032rajuVoSippUCvLi37HHKJTQmrb5/j9bKJrOWDPT59OJ2ejB/f22epVw01unHZ7mOELICfvbAgMBAAECggEAEMmRybxIOSbiKw3W3F7POIDNiZ8d+9izupcluxorQh8BBRi4BxPEbN+J6wmFysnWw2nGfzMupTNYGO7yuRhp+H3NAgL/nXUTNRWKlb233kZtAS981P0lY86AUIPrK9KnkIEFkvnYsTMSYSni7467JfP7g0I/KngmFfVNJfUhTmQF/ibsHKQ8QAZ/5/omLC6XcSJPvRcbj7tvt3TSRW388KCSdz/X9grpWIyY7TCiKmdINnU6YVmJIZoGBIoc+DKmiFBka8mbiR6Yp4QZrOX8iNlPAxVJeIf4jUtW//YQp+my/1As/pD8Ofg3lpCeNSDW8ZA8IFBPCer2jK79qJTEwQKBgQDu/p+W6t08R6UHKV/R+4w2Zw/PXjcRftEDSAYWNRwqFM8PT8D0c/P970Lf/plxjuI0dNAh/IC5iHxrLm2zPXNvleWABHkxhke/YJSDOniXzKMdLVdD10B+xZr+y0tdSSQxDLz5OgCnNvhT+LQmm2EwVHgUdxlW94yb9H7n7SZIswKBgQC8QVVgl+TS1pgEaHyRNtcrwrbZjHTIAEUjNptsT1Hvwjq7S9DjL+s+1Zr8hRaEItwi9a2FKN1/5NeCtOnPkYeL3xRhXMfk8YfP1xRsn2lEh2x705eGkTTUPhTabM+766TGIH0fGG6pxc/dLv36L8/V9EhIWt/U1HQJ/hfOr5sEOQKBgEimWjj0OEBkvwPITfp07xByj9nHmBHXbQ4ETOVfS6p3HOJYqh0lSlw86SVSLa7ZcmXjxIkwSqkblka2nF5KJdcbx+H8eiLV4OWKJFsn4RSz/jmWWNqkilR/GTiu77/xxfCrIJ7aROyPxzkvdwPtY8PfSYlfEvLe2X1m+1ThQCwFAoGAW6Q2SppGkvhR8jtnAE3A+xWDa227X6z5+wVXLaeDTIKxkoF6w02NZ/4Vv32OE27g2Mfd22ekQ+EDpCYrd5bxc/55blf7GxEUuvGJiMMlPG3sOm8rYR+IpxU9k4LKsUEp6iTgo+WB4XVhFdwNWOAzfYS4Nzi9o150ynR95GiJvskCgYEAu/yrUVc6ptMZ0grnzCdmNr0gYfNbpxfW0VWYP5sTsjXZN1xjmP7Ro46ClXWPhOB0Q4o5oi/59AngLwG3/KLmyu5m1eCYzNNBLF0eUFficmSbOH6XYzmRWBEj/Hq0b6Fjr74ab8D6NrkEmEmMPgxPyUxhKvd20ZnigZa4cxICzV0=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://guanghaun3.iask.in:45451/order/aliPay";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://guanghaun3.iask.in:45451/mobile/#/paySuccess";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA11618Y/c01lG/h7+KGM1kuQYCA2jlR7NRD/1Dw/2cm2UvEedaH/Tv0xO0KjhlkNBAB+lIJJ3mP5oD0Inx8Pa6IG5pDMTuu+RAFgeLV8iVofh2oxfqGcVGKk3HfYOO0xCN4ybr3v2SzK3UKn1GPtpZlYwwAsMkvjHauC/pLFUkNe/yTGBd1UnZrrZBOQ0CNlA5PH9krE633cWtk0WrLGpILkSbaB2oBiSc3tv/pYcHgtkFf9hTfOC6Hkf48HsQLznsQE/y1NcnucvWdFmcO8/225JDvK6cGzA+0b8SUJ5uIPmA0Iu20smfc7AvY+BkHE056yz0AgLoLd1PWJqQ+lqEwIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
