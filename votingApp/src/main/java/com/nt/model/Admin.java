package com.nt.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String name;
private String password;

@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
@JoinTable(
		name = "admins_roles",
		joinColumns = {@JoinColumn(name = "aid")},
		inverseJoinColumns = {@JoinColumn(name = "rid")}
		)
List<Role> roles = new ArrayList<>();

public Admin() {
	
}

public Admin(int id, String name, String password, List<Role> roles) {
	super();
	this.id = id;
	this.name = name;
	this.password = password;
	this.roles = roles;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public List<Role> getRoles() {
	return roles;
}

public void setRoles(List<Role> roles) {
	this.roles = roles;
}

@Override
public String toString() {
	return "Admin [id=" + id + ", name=" + name + ", password=" + password + ", roles=" + roles + "]";
}


	

}
