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
public class StudentService {
    public boolean getStudentByID(int id) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("Select * from students where student_id = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
    public boolean getStudentByName(String studentName) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("Select * from students where name = ?");
            pst.setString(1, studentName);
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
    public boolean getStudentByIDNameClassBranch(String studentId, String studentName, String studentClass, String studentBranch) {
        boolean isExists = false;
        
        try {
            Connection conn = DBConnection.getConnection();
            
            PreparedStatement pst = conn.prepareStatement("select * from students where student_id = ? and name = ? and class = ? and branch = ?");
            pst.setInt(1, Integer.parseInt(studentId));
            pst.setString(2, studentName);
            pst.setString(3, studentClass);
            pst.setString(4, studentBranch);
            
            ResultSet rs = pst.executeQuery();
            
            isExists = rs.next();
        } catch (SQLException e) {
        }
        
        return isExists;
    }
    
}
