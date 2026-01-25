/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services;

import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author User
 */
public interface BlogService {

    public Blog createBlog(Blog blog);

    public Blog getBlogById(UUID id);

    public List<Blog> searchBlog(String titulo, LocalDateTime fechaMinima, LocalDateTime fechaMaxima, Set<String> tagNames);

    public void modifyBlog(Blog blog);

    public void deleteBlog(UUID id);

}
