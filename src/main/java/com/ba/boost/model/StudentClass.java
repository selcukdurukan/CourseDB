package com.ba.boost.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString


public class StudentClass {
	
	private Long oid;
	private Long nationalId;
	private String firstName;
	private String lastName;
	private String city;
	private String street;
	private String country;
	private int countryCode;
	@Setter
	private List<Telephone> numbers;
	private int courseId;
	private String courseName;
	private int attandenceYear;
	private double grade;

}
