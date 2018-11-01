package com.yinhetianze.business.util;

import javax.servlet.http.HttpServletRequest;

public class GetIp {

    public static String getIp(HttpServletRequest request) {
        String spbill_create_ip = request.getHeader("x-forwarded-for");
        if (spbill_create_ip == null || spbill_create_ip.length() == 0 || "unknown".equalsIgnoreCase(spbill_create_ip)) {
            spbill_create_ip = request.getHeader("Proxy-Client-IP");
        }
        if (spbill_create_ip == null || spbill_create_ip.length() == 0 || "unknown".equalsIgnoreCase(spbill_create_ip)) {
            spbill_create_ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (spbill_create_ip == null || spbill_create_ip.length() == 0 || "unknown".equalsIgnoreCase(spbill_create_ip)) {
            spbill_create_ip = request.getRemoteAddr();
        }
        if (spbill_create_ip.indexOf(",") != -1) {
            String[] ips = spbill_create_ip.split(",");
            if (ips.length > 1) {
                spbill_create_ip = ips[0];
            }
        }
        return spbill_create_ip;
    }
}
