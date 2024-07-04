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
public class UserService {
    public boolean getUserByEmail(String email) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("Select * from users where email = ?");
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
    public boolean getUserByPhone(String phone) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("Select * from users where phone = ?");
            pst.setString(1, phone);
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
}
