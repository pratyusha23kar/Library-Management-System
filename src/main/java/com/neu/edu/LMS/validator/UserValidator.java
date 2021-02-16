package com.neu.edu.LMS.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.edu.LMS.pojo.User;
import com.neu.edu.LMS.pojo.UserBook;

public class UserValidator implements Validator {

	public boolean supports(Class aClass) {
		return aClass.equals(UserBook.class);
	}

	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "Enter correct email address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required", "First Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastname.required", "Last Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "gender.required", "Gender Required");
		
		// check if user exists
		
	}
}
