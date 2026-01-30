/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services;

import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogCreateDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogModifyDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogSearchDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Response.Blog.BlogResponseDTO;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author User
 */
public interface BlogService {

    public BlogResponseDTO createBlog(BlogCreateDTO blog);

    public BlogResponseDTO getBlogById(UUID id);

    public List<BlogResponseDTO> searchBlog(BlogSearchDTO blogSearchDTO);

    public void modifyBlog(BlogModifyDTO blog, UUID id);

    public void deleteBlog(UUID id);

}
