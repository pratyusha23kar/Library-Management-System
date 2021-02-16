
package com.neu.edu.LMS.pojo;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "USER_BOOK_CURRENT")
public class UserBook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_book_id")
	private int id;
	    
	 
    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @Column(name = "CHECKOUT_DATE")
    private String checkout_date;
    
    @Column(name = "DUE_DATE")
    private String Due_date;

    @Column(name = "RENEW_FLAG")
    private Integer renew_flag;
    
    @Column(name = "FINE")
    private Integer fine;


    public UserBook() {

    }

    public UserBook(Book b, User u, LocalDateTime f, Integer renewFlag) {
        this.book = b;
        this.user = u;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.checkout_date = dtf.format(f);
        this.renew_flag = renewFlag;
        this.fine = 0;

    }

    
	public String getDue_date() {
		return Due_date;
	}

	public void setDue_date(String due_date) {
		Due_date = due_date;
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

    public String getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public Integer getRenew_flag() {
        return renew_flag;
    }

    public void setRenew_flag(Integer renew_flag) {
        this.renew_flag = renew_flag;
    }

    public Integer getFine() {
        return fine;
    }

    public void setFine(Integer fine) {
        this.fine = fine;
    }

    public void UserBookPersist(Book b, User u) {
        u.getCurrentBooks().add(this);
        b.getCurrentUsers().add(this);

    }

    public String getDueDate() throws ParseException {

        DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date duedate = dtf.parse(this.checkout_date);

        Calendar cal = new GregorianCalendar();
        cal.setTime(duedate);
        cal.add(Calendar.DATE, 1);

        String dueDate = dtf.format(cal.getTime());

        return dueDate;
    }

    public void setCalculateFine(Date currDate) throws ParseException {
        DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date checkDate = dtf.parse(this.getDueDate());
        long hours = (currDate.getTime() - checkDate.getTime()) / (60 * 60 * 1000);
        if (hours <= 0) {
            this.fine = 0;
            return;
        }
        Integer intHours = (int) (long) hours;
        this.fine = ((intHours / 24) + 1);
    }

}
