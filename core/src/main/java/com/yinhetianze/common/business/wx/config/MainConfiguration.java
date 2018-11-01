package com.yinhetianze.common.business.wx.config;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceJoddHttpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信公众号主配置
 * <p>
 * Created by bjliumingbo on 2017/5/12.
 *
 * @author FirenzesEagle
 * @author BinaryWang
 */
@Configuration
public class MainConfiguration {
	/*@Value("${wx.appId}")
	private String appId;

	@Value("${wx.appSecret}")
	private String appSecret;

	@Value("${wx.token}")
	private String token;

	@Value("${wx.aesKey}")
	private String aesKey;*/

	@Autowired
	private SysPropertiesUtils sysPropertiesUtils;

	@Bean(name="wxMpConfigStorage")
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(sysPropertiesUtils.getStringValue("appId"));
		configStorage.setSecret(sysPropertiesUtils.getStringValue("appSecret"));
		configStorage.setToken(sysPropertiesUtils.getStringValue("token"));
		configStorage.setAesKey(sysPropertiesUtils.getStringValue("aesKey"));
		return configStorage;
	}

	@Bean(name="appWxMpConfigStorage")
	public WxMpConfigStorage appWxMpConfigStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(sysPropertiesUtils.getStringValue("openAppId"));
		configStorage.setSecret(sysPropertiesUtils.getStringValue("openAppSecret"));
		configStorage.setToken(sysPropertiesUtils.getStringValue("token"));
		configStorage.setAesKey(sysPropertiesUtils.getStringValue("aesKey"));
		return configStorage;
	}


	@Bean(name="wxMpService")
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceJoddHttpImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

	@Bean(name="appWxMpService")
	public WxMpService appWxMpService() {
		WxMpService wxMpService = new WxMpServiceJoddHttpImpl();
		wxMpService.setWxMpConfigStorage(appWxMpConfigStorage());
		return wxMpService;
	}

}
