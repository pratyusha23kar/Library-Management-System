package com.neu.edu.LMS.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neu.edu.LMS.dao.BookDao;
import com.neu.edu.LMS.dao.UserBookDao;
import com.neu.edu.LMS.dao.UserDao;
import com.neu.edu.LMS.pojo.Book;
import com.neu.edu.LMS.pojo.User;
import com.neu.edu.LMS.pojo.UserBook;

@Component
@EnableAspectJAutoProxy
@Controller
@RequestMapping("/User")
public class UserBookTransactionController {

	    @Autowired
	    @Qualifier("UserDaoImpl")
	    private UserDao userdao;

	    @Autowired
	    @Qualifier("UserBookDaoImpl")
	    private UserBookDao userbookdao;
	    
	    @Autowired
	    @Qualifier("BookDaoImpl")
	    private BookDao bookdao;
    
	    @RequestMapping(value="/BookDetails/Checkin/{userId}", method=RequestMethod.GET)
		public String SearchUserBYCriteria(@PathVariable Integer userId, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	count = userbookdao.NumberofBooksCart(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("ItemsinCart", count); 
	    	model.addAttribute("Message", ""); 
			return "Admin/AdminUserBookCheckin";
		}
	    
	    @RequestMapping(value="/BookDetails/wishlist/{userId}", method=RequestMethod.GET)
		public String SearchUserBYCriteriaWishList(@PathVariable Integer userId, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	count = userbookdao.NumberofBooksCart(userId);
	    	List<Book> wishlistbooks = userbookdao.wishlistbooks(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("WishlistBooks", wishlistbooks); 
	    	model.addAttribute("Message", ""); 
			return "Admin/AdminUserBookWishList";
		}
	    
	    // Show Books owe by a User
	    @RequestMapping(value="/BookDetails/OweBooks/{userId}", method=RequestMethod.GET)
		public String OweBookByUser(@PathVariable Integer userId, 
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	User u = userdao.getUser(userId);
	    	List<UserBook> userbookOwe = userbookdao.BooksUserOwe(userId);
	    	
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("OweBooks", userbookOwe); 
	    	model.addAttribute("Message", ""); 
	    	model.addAttribute("Error", "");
			return "Admin/AdminUserOweBook";
		}
	    // Return Book
	    @RequestMapping(value="/BookDetails/Return/user/{userId}/book/{bookId}", method=RequestMethod.GET)
		public String ReturnUserBookByAdmin(@PathVariable Integer userId, 
				@PathVariable Integer bookId,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	User u = userdao.getUser(userId);
	    	
	    	boolean status = userbookdao.ReturnUserOweBook(userId,bookId);
	    	List<UserBook> userbookOwe = userbookdao.BooksUserOwe(userId);
	    	
	    	if(status == true) {
	    		model.addAttribute("Message", "Book has been Returned Succesfully"); 
	    		model.addAttribute("Error", "");
	    	} else {
	    		model.addAttribute("Message", ""); 
	    		model.addAttribute("Error", "Error during Book return");
	    	}
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("OweBooks", userbookOwe); 
	    	
			return "Admin/AdminUserOweBook";
		}
	    // Renew Book
	    @RequestMapping(value="/BookDetails/Renew/user/{userId}/book/{bookId}", method=RequestMethod.GET)
		public String RenewUserBookByAdmin(@PathVariable Integer userId, 
				@PathVariable Integer bookId,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	User u = userdao.getUser(userId);
	    	
	    	boolean status = userbookdao.RenewUserOweBook(userId,bookId);
	    	List<UserBook> userbookOwe = userbookdao.BooksUserOwe(userId);
	    	
	    	if(status == true) {
	    		model.addAttribute("Message", "Book has been Renewed Succesfully"); 
	    		model.addAttribute("Error", "");
	    	} else {
	    		model.addAttribute("Message", ""); 
	    		model.addAttribute("Error", "Error during Book Renew");
	    	}
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("OweBooks", userbookOwe); 
	    	
			return "Admin/AdminUserOweBook";
		}
	    
	    @RequestMapping(value="/BookDetails/Checkin/user/{userId}/Book/{BookId}", method=RequestMethod.GET)
		public String TemporaryAddtoCart(@PathVariable Integer userId, 
				@PathVariable Integer BookId, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	String Msg = "";
	    	Msg = userbookdao.AddUserBookCart(userId,BookId);
	    	if(!(Msg.equals(""))) {
	    		Msg = "Books are already added to cart.";
	    	}
	    	int count = userbookdao.NumberofBooksCart(userId);
	    	
	    	User u = userdao.getUser(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("ItemsinCart", count); 
	    	model.addAttribute("Message", Msg); 
			return "Admin/AdminUserBookCheckin";
		}
	    
	    @RequestMapping(value="/BookDetails/WishList/user/{userId}/Book/{BookId}", method=RequestMethod.GET)
		public String AddToWishList(@PathVariable Integer userId, 
				@PathVariable Integer BookId, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	String Msg = "";
	    	Msg = userbookdao.AddUserBookWishCart(userId,BookId);
	    	if(!(Msg.equals(""))) {
	    		Msg = "Books are already added to cart.";
	    	}
	    	//int count = userbookdao.NumberofBooksCart(userId);
	    	List<Book> wishlistbooks = userbookdao.wishlistbooks(userId);
	    	User u = userdao.getUser(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("WishlistBooks", wishlistbooks); 
	    	model.addAttribute("Message", Msg); 
			return "Admin/AdminUserBookWishList";
		}
	    
	    @RequestMapping(value="/BookDetails/WishList/user/{userId}/RemoveBook/{BookId}", method=RequestMethod.GET)
		public String RemoveFromWishList(@PathVariable Integer userId, 
				@PathVariable Integer BookId, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	String Msg = "";
	    	//Msg = userbookdao.AddUserBookWishCart(userId,BookId);
	    	boolean updatestat = userbookdao.RemoveBookUserIdRef_wishlist(BookId);
	    	if(updatestat == true) {
	    		Msg = userbookdao.RemoveUserBookWishCart(userId,BookId);
		    	if(!(Msg.equals(""))) {
		    		Msg = "Books are already added to cart.";
		    	}
	    	} else {
	    		Msg = "Issue with update of reference tables";
	    	}
	    	
	    	List<Book> wishlistbooks = userbookdao.wishlistbooks(userId);
	    	User u = userdao.getUser(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("WishlistBooks", wishlistbooks); 
	    	model.addAttribute("Message", Msg); 
			return "Admin/AdminUserBookWishList";
		}
	    
	    @RequestMapping(value="/BookDetails/Cart/{userId}", method=RequestMethod.GET)
		public String UserBookCart(@PathVariable Integer userId,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	count = userbookdao.NumberofBooksCart(userId);
	    	//Already added book details.
	    	List<Book> books = userbookdao.BooksFromCart(userId);
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("BooksinCart", books); 
	    	model.addAttribute("Message", ""); 
			return "Admin/AdminUserBookCart";
		}
	    
	    @RequestMapping(value="/BookDetails/Cart/CheckOut/{userId}", method=RequestMethod.GET)
		public String UserBookCheckOut(@PathVariable Integer userId,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	
	    	//Already added book details.
	    	List<Book> books = userbookdao.BooksFromCart(userId);
	    	String msg = "";
	    	String Error = "";
	    	for(Book b : books) {
	    		String msg_t = userbookdao.CheckOutUserBook(userId,b.getBookId());
	    		if(msg_t.equals("Successfully Check out")){
	    			msg = msg + msg_t + " for Book (ISBN) : "+b.getIsbn()+" , ";
	    		} else  {
	    			Error = Error + msg_t + " for Book : "+b.getIsbn()+" , ";
	    		}
	    				
	    	}
	    	count = userbookdao.NumberofBooksCart(userId);
	    	List<Book> books_aftercheckout = userbookdao.BooksFromCart(userId);
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("ItemsinCart", count); 
	    	model.addAttribute("Message", msg); 
	    	model.addAttribute("Error",Error);
	    	model.addAttribute("BooksinCart", books_aftercheckout); 
			//return "Admin/AdminUserBookCheckin";
	    	return "Admin/AdminUserBookCart";
		}
	    
	    @RequestMapping(value="/BookDetails/Cart/CheckOut/Delete/User/{userId}/Book/{bookId}", method=RequestMethod.GET)
		public String UserBookCheckOutDelete(@PathVariable Integer userId,
				@PathVariable Integer bookId,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	
	    	// delete the book from DeleteBooksFromCart(userId, bookId)cart 
	    	userbookdao.RemoveFromTempCart(userId,bookId);
	    	//Already added book details.
	    	List<Book> books = userbookdao.BooksFromCart(userId);
	    	count = userbookdao.NumberofBooksCart(userId);
	    	//Already added book details.
	    	//List<Book> books = userbookdao.BooksFromCart(userId);
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("BooksinCart", books); 
	    	model.addAttribute("Message", ""); 
			return "Admin/AdminUserBookCart";
		}
	    
	    // User Management ------------------
	    
	    @RequestMapping(value="/OweBooks", method=RequestMethod.GET)
		public String UserOweBooks(ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	User u = userdao.getUser(sessionuser.getId());
	    	List<UserBook> userbookOwe = userbookdao.BooksUserOwe(sessionuser.getId());
	    	
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("OweBooks", userbookOwe); 
	    	model.addAttribute("Message", ""); 
			return "User/UserOweBook";
		}
	    
	    @RequestMapping(value="/WishlistBooks", method=RequestMethod.GET)
		public String UserWishList(
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	count = userbookdao.NumberofBooksCart(userId);
	    	List<Book> wishlistbooks = userbookdao.wishlistbooks(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("WishlistBooks", wishlistbooks); 
	    	model.addAttribute("Message", ""); 
			return "User/UserBookWishList";
		}
	    
	    @RequestMapping(value="/BookDetails/userwishlist", method=RequestMethod.GET)
		public String SearchUserBookWishList( 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}

	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	count = userbookdao.NumberofBooksCart(userId);
	    	List<Book> wishlistbooks = userbookdao.wishlistbooks(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("WishlistBooks", wishlistbooks); 
	    	model.addAttribute("Message", ""); 
			return "User/UserBookWishList";
		}
	    
	    @RequestMapping(value="/BookDetails/WishList/userRemoveBook/{BookId}", method=RequestMethod.GET)
		public String RemoveFromUserWishList( 
				@PathVariable Integer BookId, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	String Msg = "";
	    	//Msg = userbookdao.AddUserBookWishCart(userId,BookId);
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	
	    	boolean updatestat = userbookdao.RemoveBookUserIdRef_wishlist(BookId);
	    	if(updatestat == true) {
	    		Msg = userbookdao.RemoveUserBookWishCart(userId,BookId);
		    	if(!(Msg.equals(""))) {
		    		Msg = "Books are already added to cart.";
		    	}
	    	} else {
	    		Msg = "Issue with update of reference tables";
	    	}
	    	
	    	List<Book> wishlistbooks = userbookdao.wishlistbooks(userId);
	    	User u = userdao.getUser(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("WishlistBooks", wishlistbooks); 
	    	model.addAttribute("Message", Msg); 
			return "User/UserBookWishList";
		}
	    
	    @RequestMapping(value="/BookDetails/WishList/AddBook/{BookId}", method=RequestMethod.GET)
		public String UserAddBookToWishList(
				@PathVariable Integer BookId, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	String Msg = "";
	    	Msg = userbookdao.AddUserBookWishCart(userId,BookId);
	    	if(!(Msg.equals(""))) {
	    		Msg = "Books are already in Wishlist.";
	    	}
	    	//int count = userbookdao.NumberofBooksCart(userId);
	    	List<Book> wishlistbooks = userbookdao.wishlistbooks(userId);
	    	User u = userdao.getUser(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("WishlistBooks", wishlistbooks); 
	    	model.addAttribute("Message", Msg); 
			return "User/UserBookWishList";
		}
	    
	    @RequestMapping(value="/CheckinBooks", method=RequestMethod.GET)
		public String UserBookCheckinPage(
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	count = userbookdao.NumberofBooksCart(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("ItemsinCart", count); 
	    	model.addAttribute("Message", ""); 
			return "User/UserBookCheckin";
		}
	    
	    @RequestMapping(value="/Checkin/SearchBooks", method=RequestMethod.GET)
		public String UserCheckinSearchBook( 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	count = userbookdao.NumberofBooksCart(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("ItemsinCart", count); 
	    	model.addAttribute("Message", ""); 
			return "User/UserBookCheckin";
		}
	    
	    @RequestMapping(value="/Checkin/AddBookToCart/{BookId}", method=RequestMethod.GET)
		public String UserCheckinAddBookToCart( 
				@PathVariable Integer BookId, 
				@RequestParam(value="SearchBy", required=false)String searchby, 
				@RequestParam(value="Searchtext", required=false)String searchtext,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	if(!(searchby == null)) {
	    		List<Book> b = bookdao.FindUserFilter(searchtext,searchby);
	    		model.addAttribute("Searchbook", b);
	    	}
	    	String Msg = "";
	    	Msg = userbookdao.AddUserBookCart(userId,BookId);
	    	if(!(Msg.equals(""))) {
	    		Msg = "Books are already added to cart.";
	    	}
	    	int count = userbookdao.NumberofBooksCart(userId);
	    	
	    	User u = userdao.getUser(userId);
	    	//Already added book details.
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("ItemsinCart", count); 
	    	model.addAttribute("Message", Msg); 
			return "User/UserBookCheckin";
		}
	    
	    @RequestMapping(value="/BookCart", method=RequestMethod.GET)
		public String UserBookCartDetails(
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	count = userbookdao.NumberofBooksCart(userId);
	    	//Already added book details.
	    	List<Book> books = userbookdao.BooksFromCart(userId);
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("BooksinCart", books); 
	    	model.addAttribute("Message", ""); 
			return "User/UserBookCart";
		}
	    
	    @RequestMapping(value="/BookCart/DeleteBook/{bookId}", method=RequestMethod.GET)
		public String UserBookCheckOutDelete(
				@PathVariable Integer bookId,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	
	    	// delete the book from DeleteBooksFromCart(userId, bookId)cart 
	    	userbookdao.RemoveFromTempCart(userId,bookId);
	    	//Already added book details.
	    	List<Book> books = userbookdao.BooksFromCart(userId);
	    	count = userbookdao.NumberofBooksCart(userId);
	    	//Already added book details.
	    	//List<Book> books = userbookdao.BooksFromCart(userId);
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("BooksinCart", books); 
	    	model.addAttribute("Message", ""); 
			return "User/UserBookCart";
		}
	    
	    @RequestMapping(value="/BookCart/CheckOut", method=RequestMethod.GET)
		public String UserBookCheckOut(
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	User u = userdao.getUser(userId);
	    	int count = 0 ;
	    	
	    	//Already added book details.
	    	List<Book> books = userbookdao.BooksFromCart(userId);
	    	String msg = "";
	    	String Error = "";
	    	for(Book b : books) {
	    		String msg_t = userbookdao.CheckOutUserBook(userId,b.getBookId());
	    		if(msg_t.equals("Successfully Check out")){
	    			msg = msg + msg_t + " for Book (ISBN) : "+b.getIsbn()+" , ";
	    		} else  {
	    			Error = Error + msg_t + " for Book (ISBN) : "+b.getIsbn()+" , ";
	    		}
	    				
	    	}
	    	count = userbookdao.NumberofBooksCart(userId);
	    	List<Book> books_aftercheckout = userbookdao.BooksFromCart(userId);
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("ItemsinCart", count); 
	    	model.addAttribute("Message", msg); 
	    	model.addAttribute("Error",Error);
	    	model.addAttribute("BooksinCart", books_aftercheckout); 
			//return "Admin/AdminUserBookCheckin";
	    	return "User/UserBookCart";
		}
	    
	    @RequestMapping(value="/RenewBooks", method=RequestMethod.GET)
		public String UserRenewBooksPage(ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	User u = userdao.getUser(sessionuser.getId());
	    	List<UserBook> userbookOwe = userbookdao.BooksUserOwe(sessionuser.getId());
	    	
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("OweBooks", userbookOwe); 
	    	model.addAttribute("Message", ""); 
	    	model.addAttribute("Error", "");
			return "User/UserRenewBook";
		}
	    
	    @RequestMapping(value="/Renewbook/{bookId}", method=RequestMethod.GET)
		public String RenewBookByUser(
				@PathVariable Integer bookId,
				ModelMap model,
                HttpServletRequest request,
                HttpServletResponse response){
	    	HttpSession session = (HttpSession) request.getSession();
	    	User sessionuser = (User) session.getAttribute("user");
	    	int userId = sessionuser.getId();
	    	User u = userdao.getUser(userId);
	    	
	    	boolean status = userbookdao.RenewUserOweBook(userId,bookId);
	    	List<UserBook> userbookOwe = userbookdao.BooksUserOwe(userId);
	    	
	    	if(status == true) {
	    		model.addAttribute("Message", "Book has been Renewed Succesfully"); 
	    		model.addAttribute("Error", "");
	    	} else {
	    		model.addAttribute("Message", ""); 
	    		model.addAttribute("Error", "Error during Book Renew");
	    	}
	    	model.addAttribute("userdetails", u);
	    	model.addAttribute("OweBooks", userbookOwe); 
	    	
			return "User/UserRenewBook";
		}
}
