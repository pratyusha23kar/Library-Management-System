package com.neu.edu.LMS.pojo;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
		public String Address1;
		public String Address2;
		public String city;
		public String state;
		public String zipcode;
		
		public Address() {
		}
		
		public Address(String Address1, String Address2, String city, String state, String zipcode) {
			this.Address1 = Address1;
			this.Address2 = Address2;
			this.city = city;
			this.state = state;
			this.zipcode = zipcode;
		}

		

		public String getAddress1() {
			return Address1;
		}

		public void setAddress1(String address1) {
			Address1 = address1;
		}
		
		

		public String getAddress2() {
			return Address2;
		}

		public void setAddress2(String address2) {
			Address2 = address2;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		
}
