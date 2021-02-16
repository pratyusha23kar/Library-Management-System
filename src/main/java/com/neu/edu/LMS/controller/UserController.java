package com.neu.edu.LMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.edu.LMS.dao.BookDao;
import com.neu.edu.LMS.dao.UserDao;
import com.neu.edu.LMS.validator.UserValidator;

@Component
@EnableAspectJAutoProxy
@Controller
@RequestMapping("/User")
public class UserController {

	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;
	
    @Autowired
    @Qualifier("UserDaoImpl")
    private UserDao userdao;

    @Autowired
    @Qualifier("BookDaoImpl")
    private BookDao bookdao;
    
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, String error, String logout) {
        return "User/UserHomePage";
    }
}
