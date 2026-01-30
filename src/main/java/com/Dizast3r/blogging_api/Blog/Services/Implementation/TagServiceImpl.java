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
import jakarta.persistence.EntityNotFoundException;
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
        List<Tag> tagsEnLaDB = tagRepository.findByNombre(normalizarTexto(tagGuardar.getNombre()));

        if (tagsEnLaDB.isEmpty()) {
            tagGuardar.setNombre(normalizarTexto(tagGuardar.getNombre()));
            return tagRepository.save(tagGuardar);
        }

        return tagsEnLaDB.get(0);
    }

    @Override
    public Tag getTagById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro blog con el id dado"));
    }

    @Override
    public List<Tag> getTagByName(TagDTO tagDTO) {
        return tagRepository.findByNombreLike(tagDTO.getNombre());
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void deleteTag(UUID id) {
        tagRepository.deleteById(id);
    }

    @Override
    public void modifyTag(TagDTO tag, UUID id) {
        Tag tagNuevo = tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontro tag con el id dado"));
        tagNuevo.setNombre(tag.getNombre());
        tagRepository.save(tagNuevo);
    }

}
