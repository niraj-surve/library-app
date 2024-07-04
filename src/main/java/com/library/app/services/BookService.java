/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.app.services;

import com.library.app.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Niraj Surve
 */
public class BookService {
    public boolean getBookByID(int id) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("Select * from books where book_id = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
    public boolean getBookByName(String bookName) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("Select * from books where book_name = ?");
            pst.setString(1, bookName);
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
    public boolean getBookByAuthor(String authorName) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("Select * from books where author = ?");
            pst.setString(1, authorName);
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
    public boolean getBookByIDNameAuthor(String bookId, String bookName, String authorName) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("Select * from books where book_id = ? and book_name = ? and author = ?");
            pst.setInt(1, Integer.parseInt(bookId));
            pst.setString(2, bookName);
            pst.setString(3, authorName);
            
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
}
