package com.vh.athena;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ContactData{

	private String name;
    
	private String email;
    
	private String country;
    
	private String phone;
    
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}