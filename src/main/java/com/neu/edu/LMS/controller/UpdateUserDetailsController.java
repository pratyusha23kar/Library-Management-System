package com.neu.edu.LMS.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.neu.edu.LMS.dao.UserDao;
import com.neu.edu.LMS.pojo.User;

@Component
@EnableAspectJAutoProxy
@Controller
@RequestMapping("/UserDetails")
public class UpdateUserDetailsController {

	   @Autowired
	   @Qualifier("UserDaoImpl")
	   private UserDao userdao;
	   // By Admin
	   @RequestMapping(value="/Update/{userId}", method=RequestMethod.GET)
		public String UpdateUser_get(@PathVariable Integer userId, 
				Model model){
		    User u = userdao.getUser(userId);
	    	model.addAttribute("userForm", u);
	    	model.addAttribute("Message", "");
	    	model.addAttribute("Error", "");
			return "Admin/AdminUpdateUserDetails";	
	   }
	   
	   @RequestMapping(value="/Update/{userId}", method=RequestMethod.POST)
		public String UpdateUser_post(@PathVariable Integer userId,
				@ModelAttribute("userForm") User userForm, 
				Model model){
		    boolean status = userdao.SaveUserDetails(userForm);
		    if(status == true) {
		    	User u = userdao.getUser(userForm.getId());
		    	model.addAttribute("Message", "User Details Succesfully Updated");
		    	model.addAttribute("Error", "");
		    	model.addAttribute("userForm", u);
		    } else {
		    	model.addAttribute("userForm", userForm);
		    	model.addAttribute("Message", "");
		    	model.addAttribute("Error", "There is issue with user Details Update");
		    }
	    	
			return "Admin/AdminUpdateUserDetails";	
	   }
	   
	   // By User
	   
	   @RequestMapping(value="/Update", method=RequestMethod.GET)
		public String UpdateUser_ByUser_Get( Model model,HttpServletRequest request,HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
		    User u = userdao.getUser(userId);
	    	model.addAttribute("userForm", u);
	    	model.addAttribute("Message", "");
	    	model.addAttribute("Error", "");
			return "User/UserUpdateDetails";	
	   }
	   
	   @RequestMapping(value="/Update", method=RequestMethod.POST)
		public String UpdateUser_ByUser_Post(
				@ModelAttribute("userForm") User userForm, 
				Model model){
		    boolean status = userdao.SaveUserDetails(userForm);
		    if(status == true) {
		    	User u = userdao.getUser(userForm.getId());
		    	model.addAttribute("Message", "User Details Succesfully Updated");
		    	model.addAttribute("Error", "");
		    	model.addAttribute("userForm", u);
		    } else {
		    	model.addAttribute("userForm", userForm);
		    	model.addAttribute("Message", "");
		    	model.addAttribute("Error", "There is some issue with user Details Update");
		    }
	    	
			return "User/UserUpdateDetails";	
	   }
}
