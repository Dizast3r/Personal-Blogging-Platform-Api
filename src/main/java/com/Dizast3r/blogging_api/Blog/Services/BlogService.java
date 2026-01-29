/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services;

import com.Dizast3r.blogging_api.Blog.DTO.Request.BlogCreateDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.BlogModifyDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Response.BlogResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author User
 */
public interface BlogService {

    public BlogResponseDTO createBlog(BlogCreateDTO blog);

    public BlogResponseDTO getBlogById(UUID id);

    public List<BlogResponseDTO> searchBlog(String titulo, LocalDateTime fechaMinima, LocalDateTime fechaMaxima, Set<String> tagNames);

    public void modifyBlog(BlogModifyDTO blog);

    public void deleteBlog(UUID id);

}
