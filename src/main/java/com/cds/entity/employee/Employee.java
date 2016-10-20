package com.cds.entity.employee;

public class Employee {
	private Integer idEmployee;
	private String fullname;
	private Integer gender; // 1.Male, 2.Female
	private String email;
	private String phonenumber;
	private String address;
	private String username;
	private String password;
	private Integer active;
	
	public Employee() {
		super();
	}
	public Employee(String fullname, Integer gender, String email, String phonenumber, String address,
			String username, String password, Integer active) {
		super();
		this.fullname = fullname;
		this.gender = gender;
		this.email = email;
		this.phonenumber = phonenumber;
		this.address = address;
		this.username = username;
		this.password = password;
		this.active = active;
	}
	public Employee(Integer idEmployee, String fullname, Integer gender, String email, String phonenumber, String address,
			String username, String password, Integer active) {
		super();
		this.idEmployee = idEmployee;
		this.fullname = fullname;
		this.gender = gender;
		this.email = email;
		this.phonenumber = phonenumber;
		this.address = address;
		this.username = username;
		this.password = password;
		this.active = active;
	}
	public Integer getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	
}
