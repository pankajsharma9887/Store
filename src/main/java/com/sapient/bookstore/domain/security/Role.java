package com.sapient.bookstore.domain.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int roleId;
	private String name;
	
	@OneToMany(mappedBy="role", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<UserRole> userRoles = new HashSet<>();

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	

}
