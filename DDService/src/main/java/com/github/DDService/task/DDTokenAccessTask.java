package com.github.DDService.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.DDService.tools.DDHelper;

@Component
@Service
public class DDTokenAccessTask {
	public final static long INTERVAL_TIME =  7000*1000;
	public final static long SLEEP_TIME =  60*1000;
	
	@Autowired
    private DDHelper ddHelper;
	
	@Scheduled(fixedDelay=INTERVAL_TIME)
	public void fixedDelayJob(){
	    System.out.println((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())) + " >>获取钉钉token");
	    String token = ddHelper.getDDAccessToken();
	    while(token==null) {
	    	//未获取token时，等待一定时间重新执行
	    	try {
				Thread.currentThread().sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	System.out.println("token>>token获取失败");
	    	token = ddHelper.getDDAccessToken();
	    }
	    System.out.println("token>>"+token);
	}
}
