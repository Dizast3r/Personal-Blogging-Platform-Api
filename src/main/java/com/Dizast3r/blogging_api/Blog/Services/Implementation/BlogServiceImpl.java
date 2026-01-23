/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services.Implementation;

import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import com.Dizast3r.blogging_api.Blog.Services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Dizast3r.blogging_api.Blog.Repositories.BlogRepository;
import com.Dizast3r.blogging_api.Blog.Repositories.TagRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Blog createBlog(Blog blog) {
        
        Set<Tag>  blogTags = new HashSet();
        for (Tag tag : blog.getBlogTags()) {
            
            List<Tag> tagsEnLaDB = tagRepository.findByNombre(tag.getNombre().toLowerCase().trim());
            
            if(tagsEnLaDB.isEmpty()) {
                Tag tagGuardar = tag;
                tagGuardar.setNombre(tagGuardar.getNombre().toLowerCase().trim());
                blogTags.add(tagRepository.save(tagGuardar));
            } else {
                blogTags.add(tagsEnLaDB.get(0));
            }
        }
        
        Blog blogGuardadoDB = blog;
        blogGuardadoDB.setBlogTags(blogTags);
        blogRepository.save(blogGuardadoDB);
        
        return blogGuardadoDB;
    }
        
}
