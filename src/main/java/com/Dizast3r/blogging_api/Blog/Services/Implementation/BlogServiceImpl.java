/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services.Implementation;

import com.Dizast3r.blogging_api.Blog.DTO.Mappers.BlogDTOMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Mappers.BlogMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Response.BlogResponseDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import com.Dizast3r.blogging_api.Blog.Services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Dizast3r.blogging_api.Blog.Repositories.BlogRepository;
import com.Dizast3r.blogging_api.Blog.Repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private BlogDTOMapper mapperDTO;
    
    @Autowired
    private BlogMapper mapperEntity;

    @Override
    public BlogResponseDTO createBlog(Blog blog) {

        Set<Tag> blogTags = new HashSet();
        for (Tag tag : blog.getBlogTags()) {

            List<Tag> tagsEnLaDB = tagRepository.findByNombre(tag.getNombre().toLowerCase().trim());

            if (tagsEnLaDB.isEmpty()) {
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

        return mapperDTO.toResponse(blogGuardadoDB);
    }

    @Override
    public BlogResponseDTO getBlogById(UUID id) {
        Blog blogDevolver = blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontro blog con el id dado"));
        return mapperDTO.toResponse(blogDevolver);
    }

    @Override
    public List<BlogResponseDTO> searchBlog(String titulo, LocalDateTime fechaMinima, LocalDateTime fechaMaxima, Set<String> tagNames) {
        return blogRepository.searchAll(titulo, fechaMinima, fechaMaxima, tagNames).stream().map(mapperDTO::toResponse).collect(Collectors.toList());
    }

    @Override
    public void modifyBlog(Blog blog) {
        
    }

    @Override
    public void deleteBlog(UUID id) {
        blogRepository.deleteById(id);
    }
    
    
    
    

    
    
    
}

