package com.libapp.model;

import java.sql.Date;

public class Member {
	private Integer mid;
	private String lastname;
	private String firstname;
	private String address;
	private Integer contact;
	private Date dob;
	private String email;
	private Date dueDate;
	private Date returnDate;
	private Integer did;
	private Boolean onTime;
	private Date doj;
	private Integer borrowCount;
	private Integer latefees;
	
	public Member() {
		
	}
	
	public Member(String memData) {
		String[] mem = memData.split("\\|+");
//		System.out.println(mem);
//		System.out.println(mem.length);
//		System.out.println(mem[5]);
		
		this.mid = Integer.parseInt(mem[0]);
		this.lastname = mem[1];
		this.firstname = mem[2];
		this.address = mem[3];
		this.contact = Integer.parseInt(mem[4]);
		this.dob = !mem[5].equals("null") ? Date.valueOf(mem[5]) : null;
		this.email = mem[6];
		this.dueDate = !mem[7].equals("null") ? Date.valueOf(mem[7]) : null;
		this.returnDate = !mem[8].equals("null") ? Date.valueOf(mem[8]) : null;
		this.did = !mem[9].equals("null") ? Integer.parseInt(mem[9]) : null;
		this.onTime = !mem[10].equals("null") ? Boolean.parseBoolean(mem[10]) : null;
		this.doj = !mem[11].equals("null") ? Date.valueOf(mem[11]) : null;
		this.borrowCount = Integer.parseInt(mem[12]);
		this.latefees = Integer.parseInt(mem[13]);	
	}

	@Override
	public String toString() {
		return mid + "|" + lastname + "|" + firstname + "|" + address + "|" + contact + "|" + dob + "|" + email + "|" + dueDate + "|" + returnDate + "|" + did + "|" + onTime + "|" + doj + "|" + borrowCount + "|" + latefees;
	}
	
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
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
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public boolean isOnTime() {
		return onTime;
	}
	public void setOnTime(boolean onTime) {
		this.onTime = onTime;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public Integer getBorrowCount() {
		return borrowCount;
	}
	public void setBorrowCount(Integer borrowCount) {
		this.borrowCount = borrowCount;
	}
	public Integer getLatefees() {
		return latefees;
	}
	public void setLatefees(Integer latefees) {
		this.latefees = latefees;
	}
}
