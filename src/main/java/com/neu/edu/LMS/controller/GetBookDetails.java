package com.neu.edu.LMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.edu.LMS.dao.BookDao;
import com.neu.edu.LMS.pojo.Book;

@Component
@Controller
@EnableAspectJAutoProxy
@RequestMapping("/GetBook")
public class GetBookDetails {
	
	    @Autowired
	    @Qualifier("BookDaoImpl")
	    private BookDao bookdao;
    
	 @RequestMapping(value="/ByCriteria", method=RequestMethod.GET)
		public String SearchBookBYCriteria(@RequestParam(value="error_msg", required=false) String error, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				Model model){
			
	    	List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    	model.addAttribute("Searchbook", b);
			//return "login";	
			return "Admin/AdminBookManagement";
		}
	 
	 	@RequestMapping(value="/SearchCriteria",method = RequestMethod.GET,produces = {"application/json"})
	 	@Transactional
	 	@ResponseBody
	 	public String SearchBookBYCriteriaJason( 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext){
	    	List<Book> b = bookdao.FindUserFilter(searchtext,searchby);	
			return b.toString();
		}
}
