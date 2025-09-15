/**
 * 
 */
package com.navgo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * @author Akash Bais
 *
 */

@Entity
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String googleId;
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	private String password;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
	private Role role;
	
	
	public AppUser() {
		super();
	}


	public AppUser(Long id, String googleId, String name, String email, String password, Role role) {
		super();
		this.id = id;
		this.googleId = googleId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}


	public Long getUserId() {
		return id;
	}


	public void setUserId(Long userId) {
		this.id = userId;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getGoogleId() {
		return googleId;
	}


	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}


	@Override
	public String toString() {
		return "AppUser [userId=" + id + ", googleId=" + googleId + ", name=" + name + ", email=" + email
				+ ", password=" + password + ", role=" + role + "]";
	}
	
	
}
