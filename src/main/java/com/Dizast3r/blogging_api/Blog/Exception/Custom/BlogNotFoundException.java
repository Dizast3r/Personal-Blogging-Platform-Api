/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Exception.Custom;

import java.util.UUID;

/**
 *
 * @author User
 */
public class BlogNotFoundException extends RuntimeException{

    public BlogNotFoundException(UUID id) {
        super(String.format("Blog with id = %s not found", id));
    }
    
    public BlogNotFoundException(String message) {
        super(message);
    }
    
}
