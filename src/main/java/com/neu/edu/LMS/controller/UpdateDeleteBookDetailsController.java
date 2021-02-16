package com.neu.edu.LMS.controller;

import java.util.List;

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
import com.neu.edu.LMS.dao.BookDao;
import com.neu.edu.LMS.pojo.Book;

@Component
@EnableAspectJAutoProxy
@Controller
@RequestMapping("/BookDetails")
public class UpdateDeleteBookDetailsController {

	   @Autowired
	   @Qualifier("BookDaoImpl")
	   private BookDao bookdao;
	   
	   @RequestMapping(value="/Update/{Id}", method=RequestMethod.GET)
		public String UpdateBook_get(@PathVariable Integer Id, 
				Model model){
		    Book bk = bookdao.getBookDetails(Id);
	    	model.addAttribute("BookForm", bk);
	    	model.addAttribute("Message", "");
	    	model.addAttribute("Error", "");
			return "Admin/AdminUpdateBookDetails";	
	   }
	   
	   @RequestMapping(value="/Update/{Id}", method=RequestMethod.POST)
		public String UpdateBook_post(@PathVariable Integer Id,
				@ModelAttribute("BookForm") Book bookForm, 
				Model model){
		    boolean status = bookdao.saveBookDetails(bookForm);
		    if(status == true) {
		    	Book bk = bookdao.getBookDetails(bookForm.getBookId());
		    	model.addAttribute("Message", "Succesfully Updated");
		    	model.addAttribute("Error", "");
		    	model.addAttribute("BookForm", bk);
		    } else {
		    	model.addAttribute("BookForm", bookForm);
		    	model.addAttribute("Message", "");
		    	model.addAttribute("Error", "There is issue with Book Details Update");
		    }
	    	
			return "Admin/AdminUpdateBookDetails";	
	   }
	   
	   @RequestMapping(value="/Delete/{Id}", method=RequestMethod.GET)
		public String DeleteBook_get(@PathVariable Integer Id, 
				Model model){
		    Book bk = bookdao.getBookDetails(Id);
	    	model.addAttribute("BookForm", bk);
	    	model.addAttribute("Message", "");
	    	model.addAttribute("Error", "");
			return "Admin/AdminDeleteBookDetails";	
	   }
	   
	   @RequestMapping(value="/Delete/{Id}", method=RequestMethod.POST)
		public String DeleteBook_post(@PathVariable Integer Id,
				@ModelAttribute("BookForm") Book bookForm, 
				Model model){
		    String msg = bookdao.DeleteBookDetails(bookForm);
		    if(msg.equals("successfully Delete")) {
		    	Book bk = bookdao.getBookDetails(bookForm.getBookId());
		    	model.addAttribute("Message", msg);
		    	model.addAttribute("Error", "");
		    	model.addAttribute("BookForm", new Book());
		    } else {
		    	model.addAttribute("BookForm", bookForm);
		    	model.addAttribute("Message", "");
		    	model.addAttribute("Error", msg);
		    }
	    	
			return "Admin/AdminDeleteBookDetails";	
	   }
}
