package com.github.DDService.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ddparameter")
public class DDParameter {
	private String token;
	private Long tokenExpires;
	private String appkey;
	private String appSecret;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getTokenExpires() {
		return tokenExpires;
	}

	public void setTokenExpires(Long tokenExpires) {
		this.tokenExpires = tokenExpires;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
