/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services.Implementation;

import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Tag.TagDTOMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Tag.TagMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Tag.TagDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import com.Dizast3r.blogging_api.Blog.Repositories.TagRepository;
import com.Dizast3r.blogging_api.Blog.Services.TagService;
import java.text.Normalizer;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagDTOMapper mapperDTO;

    @Autowired
    TagMapper mapperEntity;
    
    private String normalizarTexto(String texto) {
    return Normalizer.normalize(texto, Normalizer.Form.NFD)
                     .replaceAll("\\p{M}", "")
                     .toLowerCase()
                     .trim();
}

    @Override
    public Tag createTag(TagDTO tagDTO) {
        Tag tagGuardar = mapperEntity.toEntity(tagDTO);
        List<Tag> tagsEnLaDB = tagRepository.findByNombre(tagGuardar.getNombre().toLowerCase().trim());

        if (tagsEnLaDB.isEmpty()) {
            tagGuardar.setNombre(normalizarTexto(tagGuardar.getNombre()));
            return tagRepository.save(tagGuardar);
        }

        return tagsEnLaDB.get(0);
    }

    @Override
    public Tag getTagById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Tag> getTagByName(TagDTO tagDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Tag> getAllTags() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteTag(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void modifyTag(TagDTO tag, UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
