package com.example.Note.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.example.Note.dbUtil.dbConnection;
import com.example.Note.entity.User;


@Service
public class LoginModel {

	Connection conn = null;
	
	public LoginModel() {
		this.conn = dbConnection.getConnection();
		
		if(this.conn == null) {
			System.exit(1);
		}
	}
	
	public boolean isDatabaseConnected() {
		return this.conn != null;
	}
	
//	method to Login
	public User isLogin(String username, String password) {
		PreparedStatement statement = null;
		ResultSet result = null;
		
		String query = "SELECT id, username, password FROM user_tbl WHERE username = ? AND password = ?";
		
		try {
			statement = this.conn.prepareStatement(query);
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			result = statement.executeQuery();
			
			if(result.next()) {
				User user = new User();
				
				user.setId(result.getInt(1));
				user.setName(result.getString(2));
				user.setPass(result.getString(3));
				
				return user;
			}
			
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				statement.close();
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//	method to Sign Up
	public void signUp(String username, String password) {
		String query = "INSERT INTO user_tbl (username, password) VALUES (?,?)";
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement(query);
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//	method to check if already exists the same username
	public boolean isExist(String username) {
		String query = "SELECT * FROM user_tbl WHERE username = ?";
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			statement = conn.prepareStatement(query);
			
			statement.setString(1, username);
			
			result = statement.executeQuery();
			
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}