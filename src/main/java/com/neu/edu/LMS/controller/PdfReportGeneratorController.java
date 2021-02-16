package com.neu.edu.LMS.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import com.neu.edu.LMS.View.PdfOweBookDetails;
import com.neu.edu.LMS.dao.UserBookDao;
import com.neu.edu.LMS.dao.UserDao;
import com.neu.edu.LMS.pojo.User;
import com.neu.edu.LMS.pojo.UserBook;

@Component
@Controller
@EnableAspectJAutoProxy
@RequestMapping("/pdfgenerator")
public class PdfReportGeneratorController {

    @Autowired
    @Qualifier("UserDaoImpl")
    private UserDao userdao;

    @Autowired
    @Qualifier("UserBookDaoImpl")
    private UserBookDao userbookdao;
    
	@RequestMapping(value= "{userId}/BookDetails.pdf", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response, @PathVariable Integer userId) throws Exception {
		User u = userdao.getUser(userId);
    	List<UserBook> userbookOwe = userbookdao.BooksUserOwe(userId);
    	
    	View view = new PdfOweBookDetails();
    	ModelAndView model = new ModelAndView();
    	model.addObject("User", u);
    	model.addObject("userbook", userbookOwe);
    	model.setView(view);
		
    	return model;
			
		}
		
	}
	