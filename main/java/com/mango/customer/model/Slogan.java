package com.mango.customer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "slogans")
public class Slogan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	public Slogan() {
	}

	public Slogan(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	public Slogan(Long id, String content, User user) {
		this.id = id;
		this.content = content;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Slogan slogan = (Slogan) o;
		return id.equals(slogan.id) && content.equals(slogan.content) && user.equals(slogan.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, content, user);
	}

	@Override
	public String toString() {
		return "Slogan{" +
			"id=" + id +
			", content='" + content + '\'' +
			", user=" + user +
			'}';
	}
}
