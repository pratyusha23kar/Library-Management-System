package com.neu.edu.LMS.dao;

import org.springframework.transaction.annotation.Transactional;

import com.neu.edu.LMS.pojo.User;

@Transactional
public interface AuthenticationDao {
	User GetloginUser(String Username, String Password);
}
