package com.neu.edu.LMS.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.neu.edu.LMS.pojo.Book;

@Transactional
public interface BookDao {
	boolean createBook(Book book);
	List<Book> FindUserFilter(String filtervalue,String filterby);
	Book getBookDetails(Integer id);
	boolean saveBookDetails(Book book);
	String DeleteBookDetails(Book book);
}
