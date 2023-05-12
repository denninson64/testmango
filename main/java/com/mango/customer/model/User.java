package com.mango.customer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false, unique = true)
	private String email;

	public User() {
	}

	public User(Long id, String name, String lastName, String address, String city, String email) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(address, user.address) && Objects.equals(city, user.city) && Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, lastName, address, city, email);
	}

	@Override
	public String toString() {
		return "User{" +
			"name='" + name + '\'' +
			", lastName='" + lastName + '\'' +
			", address='" + address + '\'' +
			", city='" + city + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
