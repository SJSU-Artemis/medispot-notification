package com.medispot.notification.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medispot.notification.models.PatientDTO;
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
public class EmailServiceForReports {
	private SendGrid sendGrid;
	private final String FROM_ADDRESS = "anisha.agarwal1993@gmail.com";

	@Autowired
	public EmailServiceForReports(SendGrid sendGrid) {
		this.sendGrid = sendGrid;
	}

	public void sendMail(PatientDTO patientDto) {
		Response response = sendEmail(patientDto);
	}

	private Response sendEmail(PatientDTO patientDto) {
		Mail mail = getMailData(patientDto);
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

	private Mail getMailData(PatientDTO patientDto) {
		Email from = new Email(FROM_ADDRESS);
		Email to = new Email(patientDto.getEmail());
		String subject = "Appointment Schedule";
		Content content = new Content("text/plain", "Hello, " + patientDto.getFirstName() + " "
				+ patientDto.getLastName()
				+ "\nThis is an Email Notification regarding your Test Report Published. \nPlease see your account for details. ");

		Mail mail = new Mail(from, subject, to, content);
		return mail;
	}
}
