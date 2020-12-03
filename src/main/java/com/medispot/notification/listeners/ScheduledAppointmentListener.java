package com.medispot.notification.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medispot.notification.models.ScheduledAppointmentDetail;
import com.medispot.notification.services.EmailService;
import com.medispot.notification.services.SmsService;

@Service
public class ScheduledAppointmentListener {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	EmailService emailService;
	
    static final String QUEUE_NAME = "medispotconnector-scheduled-appointment";

    @SqsListener(QUEUE_NAME)
    public void receive(String message)  {
        System.out.println(message);
        ScheduledAppointmentDetail scheduleAppointmentDetail;
		try {
			scheduleAppointmentDetail = objectMapper.readValue(message,ScheduledAppointmentDetail.class);
			smsService.sendTextMessage(scheduleAppointmentDetail);
	        emailService.sendMail(scheduleAppointmentDetail);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
    }
}
