package com.neu.edu.LMS.dao;


import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neu.edu.LMS.pojo.Book;
import com.neu.edu.LMS.pojo.User;
import com.neu.edu.LMS.pojo.UserBook;
import com.neu.edu.LMS.pojo.UserBookTempCart;
import com.neu.edu.LMS.pojo.UserBookWishList;

@Transactional
@Repository
public class UserBookDaoImpl extends DAO implements UserBookDao {
		
	public String AddUserBookCart(int UserId, int BookId) {
		try {
			begin();
			LocalDateTime today = LocalDateTime.now();
			User u = getSession().get(User.class, UserId);
			Book b = getSession().get(Book.class, BookId);
			UserBookTempCart cart = new UserBookTempCart(b,u,today);
			
			getSession().save(cart);
			commit();
			close();
			return "";
		}catch (HibernateException e) {
            rollback();
            close();
            return "error";
        }
	}
	
	public String AddUserBookWishCart(int UserId, int BookId) {
		try {
			begin();
			LocalDateTime today = LocalDateTime.now();
			User u = getSession().get(User.class, UserId);
			Book b = getSession().get(Book.class, BookId);
			UserBookWishList wishlist = new UserBookWishList(b,u,today);
			
			getSession().save(wishlist);
			commit();
			close();
			UpdateBookUserIdRef_wishlist(UserId,BookId);
			return "";
		}catch (HibernateException e) {
            rollback();
            close();
            return "error";
        }
	}
	
	public String RemoveUserBookWishCart(int UserId, int BookId) {
		try {
			//RemoveBookUserIdRef_wishlist(BookId);
			begin();
			Criteria crit = getSession().createCriteria(UserBookWishList.class);
			crit.add(Restrictions.eq("user.id",UserId));
			crit.add(Restrictions.eq("book.id",BookId));
			List<UserBookWishList> userbookwishlist = crit.list();
			
			for(UserBookWishList ubwl : userbookwishlist ) {
				getSession().delete(ubwl);
			}
			commit();
			close();
			return "";
		}catch (HibernateException e) {
            rollback();
            close();
            return "error";
        }
	}
	
	public int NumberofBooksCart(int UserId) {
		try {
				begin();
				Criteria crit = getSession().createCriteria(UserBookTempCart.class);
				crit.add(Restrictions.eq("user.id",UserId));
				crit.setProjection(Projections.rowCount());
				List userbook = crit.list();
				if (userbook!=null) {
					Integer rowCount = ((Long) userbook.get(0)).intValue();
	                return rowCount;
	            }
				commit();
				close();
			}catch (HibernateException e) {
	            rollback();
	            close();
	            //throw new CategoryException("Could not obtain the named category " + title + " " + e.getMessage());
	            return 0;
	        }
		 return 0;
	}
	
	public List<Book> BooksFromCart(int UserId) {
		List<Book> books = new ArrayList<Book>();
		try {
			begin();
			Criteria crit = getSession().createCriteria(UserBookTempCart.class);
			crit.add(Restrictions.eq("user.id",UserId));
			
			List<UserBookTempCart> userbookcart = crit.list();
			
			for(UserBookTempCart ub : userbookcart ) {
				books.add(ub.getBook());
			}
			commit();
			close();
			return books;
		}catch (HibernateException e) {
            rollback();
            close();
            return null;
        }
		
	}
	
	public String CheckOutUserBook(int UserId , int BookId) {
		try {
			if(IsBookIsTaken(BookId) == false & IsBookIsWishList(BookId) == false ) {
				begin();
				LocalDateTime today = LocalDateTime.now();
				User u = getSession().get(User.class, UserId);
				Book b = getSession().get(Book.class, BookId);
				b.setUser(u);
				getSession().save(b);
				UserBook cart = new UserBook(b,u,today,0);
				getSession().save(cart);
				try {
					cart.setDue_date(cart.getDueDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				commit();
				close();
				RemoveFromTempCart(UserId,BookId);
				UpdateBookUserIdRef(UserId,BookId);
				return "Successfully Check out";
			} else {
				begin();
				commit();
				close();
				return "Book has been taken by another User";
			}
		}catch (HibernateException e) {
            rollback();
            close();
        }
		return "";
	}
	
	// Check if the Book is Already takend by some one else
	public boolean IsBookIsTaken(int BookId) {
		try {
			begin();
			Criteria crit = getSession().createCriteria(UserBook.class);
			crit.add(Restrictions.eq("book.id",BookId));
			List<UserBook> userbookcart = crit.list();
			
			for(UserBook ub : userbookcart ) {
				if(!(ub.getUser() == null)) {

					commit();
					close();
					return true;
				}
			}
			commit();
			close();
			return false;
		}catch(HibernateException e) {
			rollback();
			return false;
		}
	}
	
	// Check if the Book is Already takend by some one else
	public boolean IsBookIsWishList(int BookId) {
		try {
			begin();
			Criteria crit = getSession().createCriteria(UserBookWishList.class);
			crit.add(Restrictions.eq("book.id",BookId));
			List<UserBookWishList> userbookwl = crit.list();
			
			for(UserBookWishList ub : userbookwl ) {
				if(!(ub.getUser() == null)) {

					commit();
					close();
					return true;
				}
			}
			commit();
			close();
			return false;
		}catch(HibernateException e) {
			rollback();
			close();
			return false;
		}
	}
	public void RemoveFromTempCart(int UserId , int BookId) {
		try {
			begin();
			Criteria crit = getSession().createCriteria(UserBookTempCart.class);
			crit.add(Restrictions.eq("user.id",UserId));
			crit.add(Restrictions.eq("book.id",BookId));
			//crit.setProjection(Projections.rowCount());
			List<UserBookTempCart> userbookcart = crit.list();
			
			for(UserBookTempCart ub : userbookcart ) {
				getSession().delete(ub);
			}
			commit();
			close();
		}catch (HibernateException e) {
            rollback();
            close();
        }
	}
	
	public void UpdateBookUserIdRef(int UserId , int BookId) {
		try {
			begin();
			Book b = getSession().get(Book.class, BookId);
			User u = getSession().get(User.class, UserId);
			b.setUser(u);
			b.setCurrent_status("Notavailable");
			commit();
			close();
		}catch (HibernateException e) {
            rollback();
            close();
        }
	}
	
	public void UpdateBookUserIdRef_wishlist(int UserId , int BookId) {
		try {
			begin();
			Book b = getSession().get(Book.class, BookId);
			User u = getSession().get(User.class, UserId);

			b.setCurrent_status("Notavailable/Hold");
			b.setWtUId(u.getId());
			commit();
			close();
		}catch (HibernateException e) {
            rollback();
            close();
        }
	}
	
	public boolean RemoveBookUserIdRef_wishlist(int BookId) {
		try {
			begin();
			Book b = getSession().get(Book.class, BookId);
			if(b.getCurrent_status().equals("Hold")) {
				b.setCurrent_status("available");
			} else if (b.getCurrent_status().equals("Notavailable/Hold")) {
				b.setCurrent_status("Notavailable");
			}
			
			b.setWtUId(-1);
			commit();
			close();
			return true;
		}catch (HibernateException e) {
            rollback();
            close();
            return false;
        }
	}
	
	public List<UserBook> BooksUserOwe(int UserId){
		List<UserBook> userbooks = new ArrayList<UserBook>();
		try {
			begin();
			Date date = new Date();
			Criteria crit = getSession().createCriteria(UserBook.class);
			crit.add(Restrictions.eq("user.id",UserId));
			
			userbooks = crit.list();
			for(UserBook ub : userbooks ) {
				try {
					ub.setCalculateFine(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			commit();
			close();
			return userbooks;
		}catch (HibernateException e) {
            rollback();
            close();
            return null;
        }
	}
	
	public List<Book> wishlistbooks(int UserId){
		List<Book> books = new ArrayList<Book>();
		try {
			begin();
			Criteria crit = getSession().createCriteria(UserBookWishList.class);
			crit.add(Restrictions.eq("user.id",UserId));
			List<UserBookWishList> userbookwishlist = crit.list();
			
			for(UserBookWishList ubwl : userbookwishlist ) {
				books.add(ubwl.getBook());
			}
			commit();
			close();
			return books;
		}catch (HibernateException e) {
            rollback();
            close();
            return null;
        }
	}
	
	public boolean ReturnUserOweBook(int UserId, int BookId) {
		try {
			begin();
			// remove the user and Book reference and status 
			Book b = getSession().get(Book.class, BookId);
			if(b.getCurrent_status().equals("Notavailable/Hold")) {
				b.setCurrent_status("Hold");
			} else if (b.getCurrent_status().equals("Notavailable")) {
				b.setCurrent_status("available");
			}
			b.setUser(null);
			
			// remove the row from UserBook Table
			Criteria crit = getSession().createCriteria(UserBook.class);
			crit.add(Restrictions.eq("user.id",UserId));
			crit.add(Restrictions.eq("book.id",BookId));
			//crit.setProjection(Projections.rowCount());
			List<UserBook> userbook = crit.list();
			
			for(UserBook ubwl : userbook ) {
				getSession().delete(ubwl);
			}
			commit();
			close();
			return true;
		}catch (HibernateException e) {
            rollback();
            close();
            return false;
        }
	}
	
	public boolean RenewUserOweBook(int UserId,int BookId) {
		try {
			begin();
			LocalDateTime today = LocalDateTime.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			Criteria crit = getSession().createCriteria(UserBook.class);
			crit.add(Restrictions.eq("user.id",UserId));
			crit.add(Restrictions.eq("book.id",BookId));
			List<UserBook> userbook = crit.list();
			
			for(UserBook ubwl : userbook ) {
				ubwl.setCheckout_date(dtf.format(today));
				try {
					ubwl.setDue_date(ubwl.getDueDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ubwl.setRenew_flag(1);
			}
			commit();
			close();
			return true;
		}catch (HibernateException e) {
            rollback();
            close();
            return false;
        }
	}
}
