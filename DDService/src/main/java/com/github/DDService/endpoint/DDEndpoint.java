package com.github.DDService.endpoint;

import java.util.HashMap;
import java.util.Map;


import com.github.DDService.tools.DDHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dd")
@CrossOrigin
public class DDEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DDEndpoint.class);
    
    @Autowired
    private DDHelper ddHelper;

    @RequestMapping(value="/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, Object> args){
    	String code = "";
    	if(args !=null &&args.containsKey("code")) {
    		code = args.getOrDefault("code", "").toString();
    	}
    	Map<String, Object> result=new HashMap<>();

		result.put("status", -1);

    	if("".equals(code)) {
    		return result;
    	}
    	
    	try {
    		String ddid = ddHelper.getDDUserId(code);
    		if(ddid !=null && ddid != "") {
    			JSONObject user = ddHelper.getDDUserInfoById(ddid);
    			result.put("data", user.toString());
    			result.put("status", 0);
    		}
		} catch (Exception e) {
			System.out.println( e.toString());
		}
		return result;
    }
}