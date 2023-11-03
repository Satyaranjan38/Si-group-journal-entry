package com.SiGroup.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "entry")
public class Entry {

	@Id
	@Column(name = "company_code")
	private String comapany_code ; 
	@Column(name = "name")
	private String name ;
	@Column(name ="email")
	private String email ; 
	@Column(name = "mob_no")
	private String mob_no ;
	public String getComapany_code() {
		return comapany_code;
	}
	public void setComapany_code(String comapany_code) {
		this.comapany_code = comapany_code;
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
	public String getMob_no() {
		return mob_no;
	}
	public void setMob_no(String mob_no) {
		this.mob_no = mob_no;
	}
	@Override
	public String toString() {
		return "Entry [comapany_code=" + comapany_code + ", name=" + name + ", email=" + email + ", mob_no=" + mob_no
				+ "]";
	} 
}
