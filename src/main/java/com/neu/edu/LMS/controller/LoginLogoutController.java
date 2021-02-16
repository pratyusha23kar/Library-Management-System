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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.neu.edu.LMS.dao.AuthenticationDao;
import com.neu.edu.LMS.dao.UserDao;
import com.neu.edu.LMS.pojo.User;
import com.neu.edu.LMS.validator.PasswordValidator;


@Component
@EnableAspectJAutoProxy
@Controller
@RequestMapping("/auth")
public class LoginLogoutController {
	
    @Autowired
    @Qualifier("UserDaoImpl")
    private UserDao userdao;
    
	@Autowired
	@Qualifier("authenticationDaoImpl")	
	private AuthenticationDao authenticationdao;
	
	@Autowired
	@Qualifier("passwordValidator")
	PasswordValidator validator;
	
    public String Msg;
    
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, @RequestParam(value="error", required=false)String error, String logout) {
		
        if (error != "" & !(error == null))
            model.addAttribute("error", error);

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        
        return "login";
    }
	
	@RequestMapping(value="/validate", method=RequestMethod.POST)
	public String showvalidationPage(HttpServletRequest request,@RequestParam(value="error_msg", required=false) String error, 
			@RequestParam(value="username", required=false)String name, 
			@RequestParam(value="password", required=false)String password,
			@RequestParam(value="option", required=false)String option,
			Model model){
		
		HttpSession session = (HttpSession) request.getSession();
		User u = authenticationdao.GetloginUser(name, password);
		
		if (u == null) {
			System.out.println("Bad Credentials. No user found with this UserId/password combination.");
			model.addAttribute("error", "Bad Credentials. No user found with this UserId/password combination.");
		} else {
			//request.getSession().setAttribute("user", u);
			session.setAttribute("user", u);
			model.addAttribute("error", "");
			if (u.getRole().getRole().equals("Librarian") || u.getRole().getRole().equals("ADMIN")) {
                return "redirect:/Admin/dashboard";
            } else {
                return "redirect:/User/dashboard";
            }
		}
		if(!(option == null) && option.equals("Register")) {
			return "register";
		}
		return "redirect:/auth/login";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registration(@RequestParam(value="regnum", required=false) String regnum, Model model) {
        Msg = "";
        
        if(!(regnum == null) && !(regnum.equals(""))) {
        	model.addAttribute("regnum", regnum);
        	User result = userdao.GetUserByLibRegNumber(regnum);
        	if(!(result == null)) {
        		model.addAttribute("userForm", result);
        		return "registration";
        	} else {
        		Msg = "Library Registration number is not correct"; 
        	}
        }
        model.addAttribute("message",Msg ); 
        return "searchregnumber";
    }
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registration(@RequestParam(value="regnum", required=false) String regnum, 
			@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userForm.setActive(true);
        boolean Isupdate = userdao.updateUser(userForm);
        
        if(Isupdate == true) {
        	return "redirect:/auth/login";
        }
        return "redirect:/register";
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
 	public String logout(HttpServletRequest request, HttpServletResponse response) {
		/*Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}*/
		HttpSession session = (HttpSession) request.getSession();
		session.removeAttribute("user");
		session.invalidate();
		return "redirect:/auth/login";
	}

}
