package com.xworkz.vaccination.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class EmailValidator {
	
	private static final  Logger log = Logger.getLogger(EmailValidator.class) ;
	
	
	public static boolean emailValidate(String email) {
		log.info("Invoked emailValidate");
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
		

	}
}
