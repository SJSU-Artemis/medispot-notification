package com.medispot.notification.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;

@Configuration
public class SendGridConfig {

	@Value("${sendgrid.api.key}")
	private String appKey;

	@Bean
	public SendGrid getSendGrid() {
		return new SendGrid(appKey);
	}
}