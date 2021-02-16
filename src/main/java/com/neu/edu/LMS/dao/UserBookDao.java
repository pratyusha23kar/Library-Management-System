package com.neu.edu.LMS.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.neu.edu.LMS.pojo.Book;
import com.neu.edu.LMS.pojo.UserBook;

@Transactional
public interface UserBookDao {

	String AddUserBookCart(int UserId,int BookId);
	int NumberofBooksCart(int UserId);
	List<Book> BooksFromCart(int UserId);
	String CheckOutUserBook(int UserId , int BookId);
	void RemoveFromTempCart(int UserId , int BookId);
	void UpdateBookUserIdRef(int UserId , int BookId);
	List<UserBook> BooksUserOwe(int UserId);
	String AddUserBookWishCart(int UserId, int BookId);
	List<Book> wishlistbooks(int userId);
	String RemoveUserBookWishCart(int UserId, int BookId);
	boolean ReturnUserOweBook(int UserId , int BookId);
	boolean RenewUserOweBook(int UserId,int BookId);
	boolean RemoveBookUserIdRef_wishlist(int BookId);
}
