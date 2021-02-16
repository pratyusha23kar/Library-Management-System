
package com.neu.edu.LMS.pojo;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "USER_BOOK_CARTTEMP")
public class UserBookTempCart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_book_Cart_id")
	private int id;
	
    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @Column(name = "ADDED_DATE")
    private String ADDED_DATE;


    public UserBookTempCart() {

    }

    public UserBookTempCart(Book b, User u, LocalDateTime f) {
        this.book = b;
        this.user = u;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.ADDED_DATE = dtf.format(f);
 
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
		return ADDED_DATE;
	}

	public void setADDED_DATE(String aDDED_DATE) {
		ADDED_DATE = aDDED_DATE;
	}

	public void UserBookPersist(Book b, User u) {
        u.getTempCartBooks().add(this);
        b.getTempCartUsers().add(this);

    }

}
