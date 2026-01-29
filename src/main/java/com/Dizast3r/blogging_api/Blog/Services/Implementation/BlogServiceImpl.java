/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services.Implementation;

import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Blog.BlogDTOMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Blog.BlogMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogCreateDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogModifyDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Tag.TagDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Response.Blog.BlogResponseDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import com.Dizast3r.blogging_api.Blog.Services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Dizast3r.blogging_api.Blog.Repositories.BlogRepository;
import com.Dizast3r.blogging_api.Blog.Services.TagService;
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
    private TagService tagService;

    @Autowired
    private BlogDTOMapper mapperDTO;

    @Autowired
    private BlogMapper mapperEntity;

    @Override
    public BlogResponseDTO createBlog(BlogCreateDTO blogDTO) {
        Set<Tag> blogTags = new HashSet();
        for (TagDTO tagDTO : blogDTO.getBlogTags()) {
            blogTags.add(tagService.createTag(tagDTO));
        }

        Blog blogGuardadoDB = mapperEntity.toEntityCreate(blogDTO);
        blogGuardadoDB.setBlogTags(blogTags);
        blogRepository.save(blogGuardadoDB);

        return mapperDTO.toResponse(blogGuardadoDB);
    }

    @Override
    public BlogResponseDTO getBlogById(UUID id) {
        Blog blogDevolver = blogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro blog con el id dado"));
        return mapperDTO.toResponse(blogDevolver);
    }

    @Override
    public List<BlogResponseDTO> searchBlog(String titulo, LocalDateTime fechaMinima, LocalDateTime fechaMaxima,
            Set<String> tagNames) {
        return blogRepository.searchAll(titulo, fechaMinima, fechaMaxima, tagNames).stream().map(mapperDTO::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void modifyBlog(BlogModifyDTO blog) {

    }

    @Override
    public void deleteBlog(UUID id) {
        blogRepository.deleteById(id);
    }

}
