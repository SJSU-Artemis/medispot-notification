package com.medispot.notification.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medispot.notification.models.ScheduledAppointmentDetail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
	private SendGrid sendGrid;
	private final String FROM_ADDRESS = "anisha.agarwal1993@gmail.com";

	@Autowired
	public EmailService(SendGrid sendGrid) {
		this.sendGrid = sendGrid;
	}

	public void sendMail(ScheduledAppointmentDetail scheduleAppointmentDetail) {
		Response response = sendEmail(scheduleAppointmentDetail);
	}

	private Response sendEmail(ScheduledAppointmentDetail scheduleAppointmentDetail) {
		Mail mail = getMailData(scheduleAppointmentDetail);
		Request request = new Request();
		Response response = null;
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			response = this.sendGrid.api(request);
		} catch (IOException ex) {
			log.info("Exception happened while sending mail, error={}", ex.getMessage());
		}
		return response;

	}

	private Mail getMailData(ScheduledAppointmentDetail scheduleAppointmentDetail) {
		// Mail mail = new Mail();
		Email from = new Email(FROM_ADDRESS);
		Email to = new Email(scheduleAppointmentDetail.getEmail());
		String subject = "Appointment Schedule";
		Content content = new Content("text/plain",
				"Hello " + scheduleAppointmentDetail.getName()
				+ ",\n\nThis is an Email Notification regarding your test appointment with "
				+ scheduleAppointmentDetail.getTestCenterName() + ".\nYour appointment is scheduled on Date: "
				+ scheduleAppointmentDetail.getAppointmentDate() + "\nTime: "
				+ scheduleAppointmentDetail.getAppointmentTime() + "\nIn Test center: "
				+ scheduleAppointmentDetail.getTestCenterAddress()
				+ "\n\nThankyou for booking an appointment with us!");

		Mail mail = new Mail(from, subject, to, content);
		return mail;
	}
}
