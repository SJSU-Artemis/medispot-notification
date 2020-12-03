package com.medispot.notification.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.medispot.notification.models.PatientDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsServiceForReports {
	@Value("${twilio.account.id}")
	private String accountSid;
	@Value("${twilio.auth.token}")
	private String authToken;
	@Value("${trial_number}")
	private String trialNumber;

	@PostConstruct
	public void initialize() {
		Twilio.init(accountSid, authToken);
	}

	public void sendTextMessage(PatientDTO patientDto) {
		try {
			String phone = patientDto.getPhoneNumber();
			String message = getMessageContent(patientDto);
			Message.creator(new PhoneNumber(phone), new PhoneNumber(trialNumber), getMessageContent(patientDto))
					.create();
		} catch (Exception e) {
			log.info("Unable to send message, errorMessage={}", e.getMessage());
		}
	}

	private String getMessageContent(PatientDTO patientDto) {
		String message = " ";
		message = "Hello, " + patientDto.getFirstName() + " " + patientDto.getLastName()
				+ " Your Test Report is Published please see your account for details.";
		return message;
	}
}
