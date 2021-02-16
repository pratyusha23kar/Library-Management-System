package com.neu.edu.LMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.edu.LMS.dao.UserDao;
import com.neu.edu.LMS.pojo.*;

@Component
@Controller
@EnableAspectJAutoProxy
@RequestMapping("/GetUser")
public class GetUserDetails {

    @Autowired
    @Qualifier("UserDaoImpl")
    private UserDao userdao;
    
    @RequestMapping(value = "/ByEmail?{email_id}", method = RequestMethod.GET)
    @Transactional
    public
    @ResponseBody
    User searchUserByEmailId(@ModelAttribute("User") User user, @PathVariable("email_id") String email) {
       User u = new User(); //= userdao.FindUserFilter(email,"SearchByEmail");
        return u;
    }
    
    @RequestMapping(value="/ByCriteria", method=RequestMethod.GET)
	public String SearchUserBYCriteria(@RequestParam(value="error_msg", required=false) String error, 
			@RequestParam(value="SearchBy", required=false)String searchby, 
			@RequestParam(value="Searchtext", required=false)String searchtext,
			Model model){
		
    	List<User> u = userdao.FindUserFilter(searchtext,searchby);
    	model.addAttribute("SearchUser", u);
		//return "login";	
		return "Admin/AdminUserManagement";
	}
}
