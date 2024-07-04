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
public class BookValidation {
    String idRegex = "^[0-9]+$";
    Pattern idPattern = Pattern.compile(idRegex);
    
        String nameRegex = "^[a-zA-Z .,?!@#$%^&*()_+=-]+(?:\\\\s[a-zA-Z .,?!@#$%^&*()_+=-]+)*$";
    Pattern namePattern = Pattern.compile(nameRegex);
    
    public String validateName(String username) {
        if (username.isEmpty()) 
            return "Required";
        else if(!namePattern.matcher(username).matches())
            return "Invalid";

        return "";
    }
    
    public String validateId(String id) {
        if (id.isEmpty()) 
            return "Required";
        else if(!idPattern.matcher(id).matches())
            return "Invalid";

        return "";
    }
}
