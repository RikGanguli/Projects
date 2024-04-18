package com.libapp.model;

import java.sql.Date;

public class Librarian {
	private Integer lid;
	private String lastname;
	private String firstname;
	private String address;
	private Integer contact;
	private Date doj;
	private Date dob;
	private String email;
	
	public Librarian() {
		
	}
	
	public Librarian(String libData) {
		String[] lib = libData.split("\\|+");
				
		this.lid = Integer.parseInt(lib[0]);
		this.lastname = lib[1];
		this.firstname = lib[2];
		this.address = lib[3];
		this.contact = Integer.parseInt(lib[4]);
		this.email = lib[7];

		this.dob = Date.valueOf(lib[6]);
		this.doj = Date.valueOf(lib[5]);
	}
	
	@Override
	public String toString() {
		return lid + "|" + lastname + "|" + firstname + "|" + address + "|" + contact + "|" + doj + "|" + dob + "|" + email;
	}
	
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getContact() {
		return contact;
	}
	public void setContact(Integer contact) {
		this.contact = contact;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
