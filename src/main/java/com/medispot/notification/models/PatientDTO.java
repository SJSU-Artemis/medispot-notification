package com.medispot.notification.models;

import lombok.Data;

@Data
public class PatientDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String address1;
	private String address2;
	private String zip;
	private String city;
	private String state;
	private String phoneNumber;
}