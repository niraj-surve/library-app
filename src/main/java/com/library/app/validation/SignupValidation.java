/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.app.validation;

import java.util.regex.Pattern;

/**
 *
 * @author Niraj Surve
 */
public class SignupValidation {
    String usernameRegex = "^[a-zA-Z]+$";
    Pattern usernamePattern = Pattern.compile(usernameRegex);

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern emailPattern = Pattern.compile(emailRegex);
    
    String phoneRegex = "^[0-9]{10}$";
    Pattern phonePattern = Pattern.compile(phoneRegex);
    
    public String validateUsername(String username) {
        if (username.isEmpty()) 
            return "Required";
        else if(!usernamePattern.matcher(username).matches())
            return "Invalid";

        return "";
    }
    
    public String validateEmail(String email) {
        if (email.isEmpty()) 
            return "Required";
        else if(!emailPattern.matcher(email).matches())
            return "Invalid";

        return "";
    }
    
    public String validatePhone(String phone) {
        if (phone.isEmpty()) 
            return "Required";
        else if(!phonePattern.matcher(phone).matches())
            return "Invalid";

        return "";
    }
    
    public String validatePassword(String password) {
        if (password.isEmpty()) 
            return "Required";
        else if(password.length() < 8)
            return "Greater";
        else if(password.length() > 16)
            return "Lesser";

        return "";
    }
}
