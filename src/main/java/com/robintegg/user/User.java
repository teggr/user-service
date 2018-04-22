package com.robintegg.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(max = 50)
	private String username;

	@NotNull
	@Size(max = 50)
	private String firstname;

	@NotNull
	@Size(max = 50)
	private String lastname;

	@NotNull
	@Min(value = 0)
	private Integer age;

	@JsonCreator
	public User(@JsonProperty("username") String username, @JsonProperty("firstname") String firstname,
			@JsonProperty("lastname") String lastname, @JsonProperty("age") Integer age) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}

	@SuppressWarnings("unused")
	private User() {
		// persistence support
	}

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Integer getAge() {
		return age;
	}

}
