package org.poc.service;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;

@Service
public class PushNotificationService {
	private Logger log = LoggerFactory.getLogger(PushNotificationService.class);
	
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	/*
	@Value("${certFile}")
	private String certFile;*/
	
	public void pushMessage(List<String> ids, String title, String message) {
		  log.info("Push Message####" );
        ApnsService service = null;

        
        try {
        	InputStream certStream = this.getClass().getClassLoader().getResourceAsStream("JLR_APNS_DEV.p12");
//        	InputStream certStream = resourceLoader.getResource("classpath:JLR_APNS_DEV.p12").getInputStream();
        	 log.info("After certStream ####" );
			service = APNS.newService().withCert(certStream, "Tcs@1234").withSandboxDestination().build();
            log.info("service initialization ##### " );
            // or
            // service = APNS.newService().withCert(certStream,
            // "your_cert_password").withProductionDestination().build();
            service.start();
            log.info("service start #####" );
            // You have to delete the devices from you list that no longer 
            //have the app installed, see method below
//            deleteInactiveDevices(service);
            // read your user list
            	  log.info("Identify device ###### " );
            	PayloadBuilder payloadBuilder = APNS.newPayload();
            	payloadBuilder.alertTitle(title);
            	payloadBuilder.alertBody(message);
            	
            	 String payload = payloadBuilder.build();
//                 String token = device.getiDeviceId();
            	 String token = "c26d20b4a9cb800bf0dee8d5e08c62aadca595055326e074b63abb53b0ed95cb";
//                 service.push(token, payload);
                 service.push(ids, payload);
            log.info("after Push Message #####" );
        } catch (Exception ex) {
            // more logging
        	log.error("Exception is "+ex.getStackTrace());
        	ex.printStackTrace();
        	throw ex;
        } finally {
            // check if the service was successfull initialized and stop it here, if it was
            if (service != null) {
                service.stop();
            }
 
        }
 
    }

}
