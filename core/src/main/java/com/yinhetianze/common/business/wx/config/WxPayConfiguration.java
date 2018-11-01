package com.yinhetianze.common.business.wx.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付相关配置
 * <p>
 * Created by bjliumingbo on 2017/5/12.
 */
@Configuration
public class WxPayConfiguration {
	/*@Value("${wx.appId}")
	private String appId;

	@Value("${wx.mchId}")
	private String mchId;

	@Value("${wx.mchKey}")
	private String mchKey;

	@Value("${wx.subAppId}")
	private String subAppId;

	@Value("${wx.subMchId}")
	private String subMchId;

	@Value("${wx.keyPath}")
	private String keyPath;*/

	@Autowired
	private SysPropertiesUtils sysPropertiesUtils;

	@Bean(name="payConfig")
	public WxPayConfig payConfig() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(sysPropertiesUtils.getStringValue("appId"));
		payConfig.setMchId(sysPropertiesUtils.getStringValue("mchId"));
		payConfig.setMchKey(sysPropertiesUtils.getStringValue("mchKey"));
		/*payConfig.setSubAppId(this.subAppId);
		payConfig.setSubMchId(this.subMchId);*/
		payConfig.setKeyPath(sysPropertiesUtils.getStringValue("keyPath"));

		return payConfig;
	}

	@Bean(name="appPayConfig")
	public WxPayConfig appPayConfig() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(sysPropertiesUtils.getStringValue("openAppId"));
		payConfig.setMchId(sysPropertiesUtils.getStringValue("appMchId"));
		payConfig.setMchKey(sysPropertiesUtils.getStringValue("appMchKey"));
		/*payConfig.setSubAppId(this.subAppId);
		payConfig.setSubMchId(this.subMchId);*/
		payConfig.setKeyPath(sysPropertiesUtils.getStringValue("keyPath"));

		return payConfig;
	}

	@Bean(name="payService")
	public WxPayService payService() {
		WxPayService payService = new WxPayServiceImpl();
		payService.setConfig(payConfig());
		return payService;
	}

	@Bean(name="appPayService")
	public WxPayService appPayService() {
		WxPayService payService = new WxPayServiceImpl();
		payService.setConfig(appPayConfig());
		return payService;
	}

}
