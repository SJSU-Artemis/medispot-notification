package com.medispot.notification.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medispot.notification.models.PatientDTO;
import com.medispot.notification.services.EmailServiceForReports;
import com.medispot.notification.services.SmsServiceForReports;

@Service
public class ReportNotificationListener {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	SmsServiceForReports smsServiceForReports;

	@Autowired
	EmailServiceForReports emailServiceForReports;

	static final String QUEUE_NAME = "medispotconnector-reports-queue";

	@SqsListener(QUEUE_NAME)
	public void receive(String message) {
		System.out.println(message);
		PatientDTO patientDto;
		try {
			patientDto = objectMapper.readValue(message, PatientDTO.class);
			smsServiceForReports.sendTextMessage(patientDto);
			emailServiceForReports.sendMail(patientDto);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
}
