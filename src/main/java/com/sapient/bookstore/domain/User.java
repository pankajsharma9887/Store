package com.sapient.bookstore.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sapient.bookstore.domain.security.Authority;
import com.sapient.bookstore.domain.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Model
 * @author Pankaj Sharma
 * @since 1.0
 */
@Entity
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	@Column(name="email", nullable = false, updatable = false)
	private String email;
	private String phone;
	@ApiModelProperty(hidden = true)
	private boolean enabled=true;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="user")
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private ShoppingCart shoppingCart;
	
	@OneToMany(mappedBy = "user")
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private List<Order> orderList;

	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Set<UserRole> userRoles = new HashSet<>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
		return authorities;
	}
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
