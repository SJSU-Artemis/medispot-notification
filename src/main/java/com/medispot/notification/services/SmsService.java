package com.medispot.notification.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.medispot.notification.models.ScheduledAppointmentDetail;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsService {

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

	public void sendTextMessage(ScheduledAppointmentDetail scheduleAppointmentDetail) {
		try {
			String phone = scheduleAppointmentDetail.getPhoneNumber();
			String message = getMessageContent(scheduleAppointmentDetail);
			Message.creator(
					new PhoneNumber(phone), new PhoneNumber(trialNumber), getMessageContent(scheduleAppointmentDetail))
					.create();
		} catch (Exception e) {
			log.info("Unable to send message, errorMessage={}", e.getMessage());
		}
	}

	private String getMessageContent(ScheduledAppointmentDetail scheduleAppointmentDetail) {
		String message = " ";
		message = "Hello," + scheduleAppointmentDetail.getName() + "Notifying you from:"
				+ scheduleAppointmentDetail.getTestCenterName() + "Your appointment is scheduled on"
				+ scheduleAppointmentDetail.getAppointmentDate() + scheduleAppointmentDetail.getAppointmentTime() + "at"
				+ scheduleAppointmentDetail.getTestCenterAddress();
		return message;
	}
}
