package com.example.Note.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class NoteRequest implements Serializable {
	
	private String title;
	
	private String contents;
}
