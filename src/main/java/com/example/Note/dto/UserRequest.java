package com.example.Note.dto;

import java.io.Serializable;

import lombok.Data;


@Data
public class UserRequest implements Serializable{
	
	private String username;

	private String password;

}