package com.neu.edu.LMS.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.edu.LMS.pojo.User;

public class PasswordValidator implements Validator {
	@Override
	public boolean supports(Class clazz) {
		//just validate the Customer instances
		return User.class.isAssignableFrom(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
			"required.password", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword", "Field name is required.");
		
		User u = (User)target;
		
		if(!(u.getPassword().equals(u.getConfirmPassword()))){
			errors.rejectValue("password", "notmatch.password");
		}
		
	}
	
}