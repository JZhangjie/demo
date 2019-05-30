package com.github.DDService.tools;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.DDService.model.DDParameter;

@Component
public class DDHelper {
	@Autowired
	private  DDParameter ddParameter;
	
	static Logger log = Logger.getLogger(DDHelper.class);
	
	public  String getDDAccessToken()
    {
		String appKey = ddParameter.getAppkey();
		String appSecret = ddParameter.getAppSecret();
		if(appKey==null || appKey =="" || appSecret ==null || appSecret=="") {
			throw new RuntimeException(">>应用key或secret不存在<<");
		}
		String result = HTTPHelper.sendGet("https://oapi.dingtalk.com/gettoken", "appkey="+appKey+"&appsecret="+appSecret);
		
		try {
			JSONObject j = new JSONObject(result);
			ddParameter.setToken(j.getString("access_token"));
			ddParameter.setTokenExpires(j.getLong("expires_in"));
		} catch (JSONException e) {
			e.printStackTrace();
			ddParameter.setToken("");
			ddParameter.setTokenExpires(0L);
			return null;
		}
		return ddParameter.getToken();
    }
	
	public  String getDDUserId(String code) {
		String accesstoken = ddParameter.getToken();
		if(accesstoken==null || accesstoken =="") {
			throw new RuntimeException(">>钉钉的token不存在<<");
		}
		String result = HTTPHelper.sendGet("https://oapi.dingtalk.com/user/getuserinfo", "access_token="+accesstoken+"&code="+code);
		log.info("获取钉钉用户id:"+result);
		System.out.println("获取钉钉用户id:"+result);
		JSONObject jo;
		try {
			jo = new JSONObject(result);
			if(jo!=null&&jo.has("errcode")) {
				int errcode=jo.getInt("errcode");
				if(errcode==0) {
					return jo.getString("userid");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject getDDUserInfoById(String ddid) {
		String accesstoken = ddParameter.getToken();
		if(accesstoken==null || accesstoken =="") {
			throw new RuntimeException(">>钉钉的token不存在<<");
		}
		String result = HTTPHelper.sendGet("https://oapi.dingtalk.com/user/get", "access_token="+accesstoken+"&userid="+ddid);
		log.info("获取钉钉用户信息:"+result);
		System.out.println("获取钉钉用户信息:"+result);
		JSONObject jo;
		try {
			jo = new JSONObject(result);
			if(jo!=null && jo.has("mobile")) {
				return jo;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
