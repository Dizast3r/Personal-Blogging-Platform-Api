/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Exception.Custom;

/**
 *
 * @author User
 */
public class BlogAlreadyExistsException extends RuntimeException{

    public BlogAlreadyExistsException(String titulo) {
        super(String.format("Blog with title: %s Already exists", titulo));
    }
    
}
