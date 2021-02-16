package com.neu.edu.LMS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.neu.edu.LMS.dao.BookDao;
import com.neu.edu.LMS.dao.UserDao;
import com.neu.edu.LMS.pojo.Address;
import com.neu.edu.LMS.pojo.Book;
import com.neu.edu.LMS.pojo.Role;
import com.neu.edu.LMS.pojo.User;
import com.neu.edu.LMS.validator.*;

@Component
@EnableAspectJAutoProxy
@Controller
@RequestMapping("/Admin")
public class  AdminController {

	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;
	
    @Autowired
    @Qualifier("UserDaoImpl")
    private UserDao userdao;

    @Autowired
    @Qualifier("BookDaoImpl")
    private BookDao bookdao;
    
  //  @Autowired
  //  private JavaMailSender mailSender;
    
    @ModelAttribute("allRoles")
    public List<Role> populateRoles()
    {
        //ArrayList<Role> roles = new ArrayList<Role>();
        return userdao.GetRoles();
    }
    
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, String error, String logout) {
        return "Admin/AdminHomePage";
    }
	
	@RequestMapping(value = "/UserManagement", method = RequestMethod.GET)
    public String userManagement(Model model, String error, String logout) {
        return "Admin/AdminUserManagement";
    }
	
	@RequestMapping(value = "/BookManagement", method = RequestMethod.GET)
    public String bookManagement(Model model, String error, String logout) {
        return "Admin/AdminBookManagement";
    }
	
	@RequestMapping(value = "/ReportManagement", method = RequestMethod.GET)
    public String reportManagement(Model model, String error, String logout) {
        return "Admin/AdminReportManagement";
    }
	
	@RequestMapping(value = "/AddNewUser", method = RequestMethod.GET)
    public String newUser(Model model, String error, String logout) {
		//List<Role> roles= userdao.GetRoles();
		model.addAttribute("User", new User());
		//model.addAttribute("Role",new Role());
		
		model.addAttribute("Message", "");
		//model.addAttribute("allRoles", roles);
		return "Admin/AddNewUser";
    }
	
	@RequestMapping(value = "/AddNewUser", method = RequestMethod.POST)
    public String newUserResult(@ModelAttribute("User") User userForm, 
    							//@RequestParam(value="select_role", required=false) User roleselected,
    							BindingResult bindingResult, Model model) {
		
		validator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			//model.addAttribute("User", new User());
			return "Admin/AddNewUser";
		}
		//String locaLpath="C:\\Users\\praty\\OneDrive\\Documents\\WedToolsDev_Yusuf_2019\\FinalProject_WebTools_LMS";
		
		File file = new File(userForm.getImageName());
		byte[] imageData = new byte[(int) file.length()];
		
		try {
		    FileInputStream fileInputStream = new FileInputStream(file);
		    fileInputStream.read(imageData);
		    fileInputStream.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		//userForm.setImageName("test.jpeg");
		userForm.setImageData(imageData);
		
		int unique_id= (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); 
		String LibNumber = "LIB" + String.valueOf(unique_id);
		Address address = new Address(userForm.getAddress().getAddress1(),userForm.getAddress().getAddress2(),userForm.getAddress().getCity(),userForm.getAddress().getState(),userForm.getAddress().getZipcode());
		User u = new User(userForm.getEmail(),userForm.getName(),userForm.getLastName(),true,LibNumber,userForm.getGender(),userForm.getAge(),userForm.getSelect_role(),address , userForm.getPhonenumber(), userForm.getImageName(), userForm.getImageData());
		
		userdao.createUser(u);
		try {
		String body = "Please register by using Lib Number :"+ LibNumber;
		String Sub = "LMS ( Library Management System ) Registration Process";
		SendEmail(userForm.getEmail(),Sub,body);
		model.addAttribute("Message", "Succesfully Added");
		}catch ( Exception e){
			model.addAttribute("Message", "Succesfully Added but issue with email service");
		}
		model.addAttribute("User", new User());
		
		return "Admin/AddNewUser";
    }
	
	@RequestMapping(value = "/AddNewBook", method = RequestMethod.GET)
    public String newBook(Model model, String error, String logout) {
		model.addAttribute("Book", new Book());
		model.addAttribute("Message", "");
		return "Admin/AddNewBook";
    }
	
	@RequestMapping(value = "/AddNewBook", method = RequestMethod.POST)
    public String newBookResult(@ModelAttribute("Book") Book bookForm, BindingResult bindingResult, Model model) {
		
		//validator.validate(bookForm, bindingResult);
		model.addAttribute("Book", new Book());
		if (bindingResult.hasErrors()) {
			return "Admin/AddNewBook";
		}
		
		Book b = new Book(bookForm.getIsbn(),bookForm.getAuthor(),bookForm.getTitle(),bookForm.getCallnumber(),bookForm.getPublisher(),bookForm.getYear_of_publication(),bookForm.getLocation(),bookForm.getNum_of_copies(),bookForm.getCurrent_status(),bookForm.getKeywords());
		
		bookdao.createBook(b);
		
		model.addAttribute("Message", "Succesfully Added");
		return "Admin/AddNewBook";
    }
	
	public void SendEmail(String recipientAddress,String subject,String message) {
        // creates a simple e-mail object
       // SimpleMailMessage email = new SimpleMailMessage();
       // email.setTo(recipientAddress);
       // email.setSubject(subject);
       // email.setText(message);
         
        // sends the e-mail
       // mailSender.send(email);
    }
}
