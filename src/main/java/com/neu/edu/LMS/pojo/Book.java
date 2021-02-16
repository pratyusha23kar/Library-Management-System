package com.neu.edu.LMS.pojo;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "BOOK")
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "BOOK_ID", unique = true, nullable = false)
    private Integer bookId;

    @Column(name = "ISBN", nullable = false, unique = true)
    private String isbn;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CALLNUMBER")
    private String callnumber;

    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;

    @Column(name = "YEAR_OF_PUBLICATION")
    private String yearofpublication;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "NUM_OF_COPIES")
    private int num_of_copies;

    @Column(name = "CURRENT_STATUS")
    private String current_status;

    @Column(name = "KEYWORDS")
    private String keywords;

    @Column(name = "LAST_AVAILABLE_DATE")
    private String last_available_date;

    @Column(name = "WAITLISTED_USER")
    private Integer wtUId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @OneToMany(mappedBy = "book", cascade = {CascadeType.REMOVE})
    List<UserBook> currentUsers = new ArrayList<UserBook>();

    @OneToMany(mappedBy = "book", cascade = {CascadeType.REMOVE})
    List<UserBookTempCart> TempCartUsers = new ArrayList<UserBookTempCart>();
    
    @OneToMany(mappedBy = "book", cascade = {CascadeType.REMOVE})
    List<UserBookWishList> wishlistUsers = new ArrayList<UserBookWishList>();
    
    public Book() {
    }

    public Book(String isbn, String author, String title, String callnumber, String publisher, String year_of_publication, String location, int num_of_copies, String current_status, String keywords) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.callnumber = callnumber;
        this.publisher = publisher;
        this.yearofpublication = year_of_publication;
        this.location = location;
        this.num_of_copies = num_of_copies;
        this.current_status = current_status;
        this.keywords = keywords;
        this.wtUId = -1;
        this.last_available_date = null;

    }

    public String getIsbn() {
        return isbn;
    }


    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getCallnumber() {
        return callnumber;
    }


    public void setCallnumber(String callnumber) {
        this.callnumber = callnumber;
    }


    public String getPublisher() {
        return publisher;
    }


    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public int getNum_of_copies() {
        return num_of_copies;
    }


    public void setNum_of_copies(int num_of_copies) {
        this.num_of_copies = num_of_copies;
    }


    public String getCurrent_status() {
        return current_status;
    }


    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }


    public String getKeywords() {
        return keywords;
    }


    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

  
    public String getYear_of_publication() {
        return yearofpublication;
    }


    public void setYear_of_publication(String year_of_publication) {
        this.yearofpublication = year_of_publication;
    }


    public Integer getBookId() {
        return bookId;
    }


    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }


    public String getLast_available_date() {
        return last_available_date;
    }


    public void setLast_available_date(String last_available_date) {
        this.last_available_date = last_available_date;
    }


    public Integer getWtUId() {
        return wtUId;
    }


    public void setWtUId(Integer wtUId) {
        this.wtUId = wtUId;
    }


	public List<UserBook> getCurrentUsers() {
		return currentUsers;
	}


	public void setCurrentUsers(List<UserBook> currentUsers) {
		this.currentUsers = currentUsers;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<UserBookTempCart> getTempCartUsers() {
		return TempCartUsers;
	}


	public void setTempCartUsers(List<UserBookTempCart> tempCartUsers) {
		TempCartUsers = tempCartUsers;
	}


	public List<UserBookWishList> getWishlistUsers() {
		return wishlistUsers;
	}


	public void setWishlistUsers(List<UserBookWishList> wishlistUsers) {
		this.wishlistUsers = wishlistUsers;
	}
    
    
}
