package com.example.Note.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Note.dbUtil.dbConnection;
import com.example.Note.entity.Note;
import com.example.Note.entity.User;

@Service
public class MainModel {

	
	Connection conn = null;
	
	public MainModel() {
		this.conn = dbConnection.getConnection();
		
		if(this.conn == null) {
			System.exit(1);
		}
	}
	
	public boolean isDatabaseConnected() {
		return this.conn != null;
	}
	
	public List<Note> getNoteList(User userInf) {
		
		PreparedStatement statement = null;
		ResultSet result = null;

		User user = new User();
		
		String query = user.noteListQuery(userInf);
		
		try {
			statement = conn.prepareStatement(query);
			
			List<Note> notes = new ArrayList<>();
			
			result = statement.executeQuery();
			
			
			while(result.next()) {
				
				Note note = new Note();
				
				note.setTitle(result.getString("title"));
				note.setContents(result.getString("contents"));
				
				notes.add(note);
				
			}
			
			return notes;
		} catch(SQLException e) {
			
			e.printStackTrace();
			
			return null;
			
		} finally {
			
			try {
				statement.close();
				if(result != null) {
					result.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//	 method to create a note
	public void createNote(User userInf, String title, String contents) {
		
		PreparedStatement statement = null;
		
		User user = new User();
		String query = user.createNoteQuery(title, contents, userInf);
		
		try {
			statement = conn.prepareStatement(query);
			
			statement.executeQuery();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void deleteNote(String title) {
		PreparedStatement statement = null;
		
		String query = "DELETE FROM notes_tbl WHERE title = ?";
		
		try {
			statement = conn.prepareStatement(query);
			
			statement.setString(1, title);
			
			statement.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateNote(String title, String contents) {
		PreparedStatement statement = null;
		
		String query = "UPDATE notes_tbl SET title = ?, contents = ? WHERE title = ?";
		
		try {
			statement = this.conn.prepareStatement(query);
			
			statement.setString(1, title);
			statement.setString(2, contents);
			statement.setString(3, title);
			
			statement.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
