package com.rama.model;

public class Contact {
	private String id;
	private String name;
	private String email;
	private String phone;
	private String companyName;
	private String jobTitle;
	private String state;

	public Contact(String name) {
		this.name = name;
	}

	public Contact(String name, String email, String phone, String companyName,
			String jobTitle, String state) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.companyName = companyName;
		this.jobTitle = jobTitle;
		this.state = state;
	}

	public Contact(String id, String name, String email, String phone,
			String companyName, String jobTitle, String state) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.companyName = companyName;
		this.jobTitle = jobTitle;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", email=" + email
				+ ", phone=" + phone + ", companyName=" + companyName
				+ ", jobTitle=" + jobTitle + ", state=" + state + "]";
	}

}
