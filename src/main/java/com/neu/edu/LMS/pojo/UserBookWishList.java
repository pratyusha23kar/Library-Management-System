
package com.neu.edu.LMS.pojo;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "USER_BOOK_WISHLIST")
public class UserBookWishList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_book_wishlist_id")
	private int id;
	
    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @Column(name = "WISHLIST_DATE")
    private String WISHLIST_DATE;


    public UserBookWishList() {

    }

    public UserBookWishList(Book b, User u, LocalDateTime f) {
        this.book = b;
        this.user = u;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.WISHLIST_DATE = dtf.format(f);
    }

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    public String getADDED_DATE() {
		return WISHLIST_DATE;
	}

	public void setADDED_DATE(String aDDED_DATE) {
		WISHLIST_DATE = aDDED_DATE;
	}

	public void UserBookPersist(Book b, User u) {
        u.getWishListBooks().add(this);
        b.getWishlistUsers().add(this);

    }

}
