package com.example.Note.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="user_tbl")
@Data
public class User {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username")
	private String name;
	
	@Column(name = "password")
	private String pass;
	
	
	public String noteListQuery(User user) {

		String query = "SELECT title, contents FROM notes_tbl WHERE userid = " + user.id;
		
		return query;
	}
	
	public String createNoteQuery(String title, String contents, User user) {
		
		String query = "INSERT INTO notes_tbl (title, contents, userid) VALUES ('" + title + "', '" + contents + "', " + user.id + ")";
		
		return query;
	}
}