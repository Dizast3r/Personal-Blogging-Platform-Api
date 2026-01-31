/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services.Implementation;

import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Blog.BlogDTOMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Blog.BlogMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogCreateDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogModifyDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogSearchDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Tag.TagDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Response.Blog.BlogResponseDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import com.Dizast3r.blogging_api.Blog.Exception.Custom.BlogAlreadyExistsException;
import com.Dizast3r.blogging_api.Blog.Exception.Custom.BlogNotFoundException;
import com.Dizast3r.blogging_api.Blog.Services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Dizast3r.blogging_api.Blog.Repositories.BlogRepository;
import com.Dizast3r.blogging_api.Blog.Services.TagService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;

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
        try {
            Set<Tag> blogTags = new HashSet();
            for (TagDTO tagDTO : blogDTO.getBlogTags()) {
                blogTags.add(tagService.createTag(tagDTO));
            }

            Blog blogGuardadoDB = mapperEntity.toEntityCreate(blogDTO);
            blogGuardadoDB.setBlogTags(blogTags);
            blogRepository.save(blogGuardadoDB);

            return mapperDTO.toResponse(blogGuardadoDB);

        } catch (DataIntegrityViolationException e) {
            throw new BlogAlreadyExistsException(blogDTO.getTitulo());
        }
    }

    @Override
    public BlogResponseDTO getBlogById(UUID id) {
        Blog blogDevolver = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException(id));
        return mapperDTO.toResponse(blogDevolver);
    }

    @Override
    public List<BlogResponseDTO> searchBlog(BlogSearchDTO blogSearchDTO) {

        if (blogSearchDTO.isEmpty()) {
            return blogRepository.findAll().stream().map(mapperDTO::toResponse).collect(Collectors.toList());
        }
        
        List<BlogResponseDTO> blogsEncontrados = blogRepository.searchAll(blogSearchDTO.getTitulo(),
                blogSearchDTO.getFechaMinima(),
                blogSearchDTO.getFechaMaxima(),
                blogSearchDTO.getTagNames()).stream().map(mapperDTO::toResponse)
                .collect(Collectors.toList());
        
        if(blogsEncontrados.isEmpty()) {
            throw new BlogNotFoundException("No se encontro ningun blog con los parametros solicitados");
        }
        return blogsEncontrados;
    }

    @Override
    public BlogResponseDTO modifyBlog(BlogModifyDTO blogDTO, UUID id) {
        Blog nuevoBlogInfo = mapperEntity.toEntityModify(blogDTO);
        Blog blogActualizar = blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException(id));
        blogActualizar.setTitulo(nuevoBlogInfo.getTitulo());
        blogActualizar.setContenido(nuevoBlogInfo.getContenido());
        Set<Tag> nuevosBlogTags = new HashSet<>();
        for (TagDTO tagDTO : blogDTO.getBlogTags()) {
            nuevosBlogTags.add(tagService.createTag(tagDTO));
        }
        blogActualizar.setBlogTags(nuevosBlogTags);
        return mapperDTO.toResponse(blogRepository.save(blogActualizar));
    }

    @Override
    public void deleteBlog(UUID id) {
        if(blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        } else {
            throw new BlogNotFoundException(id);
        }
        
    }

}
