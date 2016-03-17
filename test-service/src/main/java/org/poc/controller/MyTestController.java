package org.poc.controller;

import java.util.ArrayList;
import java.util.List;

import org.poc.service.PushNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyTestController {
	
	private Logger log = LoggerFactory.getLogger(MyTestController.class);

	@SuppressWarnings("finally")
	@RequestMapping("/myservice")
	public boolean myService(@RequestParam(value="deviceId", defaultValue="c26d20b4a9cb800bf0dee8d5e08c62aadca595055326e074b63abb53b0ed95cb") String deviceId,
			@RequestParam(value="alert", defaultValue="Notification message") String alertMessage) {
		boolean flag = true;
		PushNotificationService pushMgr = new PushNotificationService();
		List<String> ids = new ArrayList<String>();
		ids.add(deviceId);
		try {
			log.info("before calling push ");
			pushMgr.pushMessage(ids,"Title", alertMessage);
			log.info("After calling push ");
		} catch (Exception e){
			e.printStackTrace();
			flag=false;
			throw e;
		}
		finally{
			return flag;
		}
	}
}
