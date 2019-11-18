package com.example.demo.model;

import java.util.Date;

public class Tweet {
	private String id;

//  @NotBlank
//  @Size(max = 140)
	private String text;

//  @NotNull
	private Date createdAt = new Date();

	public Tweet() {

	}

	public Tweet(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
