package com.neu.edu.LMS.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neu.edu.LMS.pojo.Book;
import com.neu.edu.LMS.pojo.UserBookTempCart;

@Transactional
@Repository
public class BookDaoImpl extends DAO implements BookDao {
	
	@Override
    public boolean createBook(Book bookdetail) {
		try{
			begin();
			getSession().save(bookdetail);
			commit();
			close();
		} catch (HibernateException e) {
	        rollback();
	        close();
	        return false;
	    }
    	return true;
    }
	
	@Override
    public List<Book> FindUserFilter(String filtervalue,String filterby) {
		try {
			begin();
			List<Book> booklist = new ArrayList<Book>();
			Criteria crit = getSession().createCriteria(Book.class);
			if(filterby.equals("SearchByISBN")) {
				crit.add(Restrictions.like("isbn","%"+ filtervalue + "%"));
				booklist = crit.list();
			} else if(filterby.equals("SearchByAuthor")) {
				crit.add(Restrictions.like("author","%"+ filtervalue + "%"));
				booklist = crit.list();
			} else if(filterby.equals("SearchByTitle")){
				crit.add(Restrictions.like("title","%"+ filtervalue + "%"));
				booklist = crit.list();
			}
			
			commit();
			close();
			return booklist;
		}catch (HibernateException e) {
           
			rollback();
			close();
            return null;
        }	
	}
	//@Override
    public List<Book> FindUserFilter_temp(String filtervalue,String filterby) {
    	try {
			begin();
			org.hibernate.Query u;
			List<Book> ulist;
			if(filterby.equals("SearchByISBN")) {
				u = getSession().createQuery("from Book where isbn like :isbnno")
						.setParameter("isbnno","%"+ filtervalue + "%");
				ulist = u.list();
				commit();
				close();
				return ulist;
	    	} else if(filterby.equals("SearchByAuthor")) {
	    		u = getSession().createQuery("from Book where author like :Author")
						.setParameter("Author", "%"+ filtervalue + "%");
	    		ulist = u.list();

				commit();
				close();
				return ulist;
	    	} else if(filterby.equals("SearchByTitle")){
	    		u = getSession().createQuery("from Book where title like :Title")
						.setParameter("Title", "%"+ filtervalue + "%");
	    		ulist = u.list();

				commit();
				close();
				return ulist;
	    	}
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			close();
			return null;
		}
    	return null;
    }
	
	 @Override
	 public Book getBookDetails(Integer id) {
	    	Book bk;
	    	try{
	    		begin();
	    		bk = getSession().get(Book.class, id);
	    		
	    		commit();
	    		close();
	    		return bk;
	    	} catch (HibernateException e) {
	    		
	            rollback();
	            close();
	            return null;
	        }
	    }
	 
	 @Override
	 public boolean saveBookDetails(Book book) {
		 try{
	    		begin();
	    		Book bk = getSession().get(Book.class, book.getBookId());
	    		bk.setCallnumber(book.getCallnumber());
	    		bk.setCurrent_status(book.getCurrent_status());
	    		bk.setKeywords(book.getKeywords());
	    		bk.setLocation(book.getLocation());
	    		bk.setNum_of_copies(book.getNum_of_copies());
	    		bk.setPublisher(book.getPublisher());
	    		bk.setTitle(book.getTitle());
	    		
	    		commit();
	    		close();
	    	} catch (HibernateException e) {
	    		
	            rollback();
	            close();
	            return false;
	        }
	    	return true;
	 }
	 
	 @Override
	 public String DeleteBookDetails(Book book) {
		 try{
	    		begin();
	    		getSession().delete(book);
	    		
	    		commit();
	    		close();
	    		return "successfully Delete";
	    	} catch (HibernateException e) {
	    		
	            rollback();
	            close();
	            return e.getMessage();
	        }
	 }
}
