package com.neu.edu.LMS.pojo;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Transient
    private int confirmPassword;
    
    @Column(name = "First_name")
    @NotEmpty(message = "*Please provide your name")
    private String name;
    
    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;
    
    @Column(name = "age")
    private String age;
    
    @Column(name = "active")
    private boolean active;
    
    @Column(name = "lib_reg_num")
    private String LibRegNum;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "phone_number")
    private String phonenumber;
    
    @OneToOne(fetch = FetchType.EAGER,
            cascade =  CascadeType.ALL)
    @JoinColumn(name= "role_id")
    private Role role;
    
    @Column(name= "UploadimageName")
    private String imageName;
    
    @Column(name= "UploadimageData")
    private byte[] imageData;
    
    @OneToMany
    @JoinTable(name="USER_BOOK_CURRENT",joinColumns = @JoinColumn(name = "user_id"),
    			inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> book = new ArrayList<Book>();
    
    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    List<UserBook> currentBooks = new ArrayList<UserBook>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    List<UserBookTempCart> TempCartBooks = new ArrayList<UserBookTempCart>();
   
    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    List<UserBookWishList> WishListBooks = new ArrayList<UserBookWishList>();
    
    @Transient
    private int select_role;
    
    @Embedded
    private Address address;
    
    public int getSelect_role() {
		return select_role;
	}

	public void setSelect_role(int select_role) {
		this.select_role = select_role;
	}

	public User() {
    	
    }
    
	public User(String email, String name, String lastName, boolean active, String libRegNum, String gender, String age, int selectrole, Address address, String phonenumber, String imageName,byte[] imageData) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.active = active;
		this.LibRegNum = libRegNum;
		this.gender = gender;
		this.age = age;
		this.select_role = selectrole;
		this.address = address;
		this.phonenumber = phonenumber;
		this.imageData = imageData;
		this.imageName = imageName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLibRegNum() {
		return LibRegNum;
	}

	public void setLibRegNum(String libRegNum) {
		LibRegNum = libRegNum;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}

	public List<UserBook> getCurrentBooks() {
		return currentBooks;
	}

	public void setCurrentBooks(List<UserBook> currentBooks) {
		this.currentBooks = currentBooks;
	}

	
	public List<UserBookTempCart> getTempCartBooks() {
		return TempCartBooks;
	}

	public void setTempCartBooks(List<UserBookTempCart> tempCartBooks) {
		TempCartBooks = tempCartBooks;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public List<UserBookWishList> getWishListBooks() {
		return WishListBooks;
	}

	public void setWishListBooks(List<UserBookWishList> wishListBooks) {
		WishListBooks = wishListBooks;
	}

	public int getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(int confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	
	
}