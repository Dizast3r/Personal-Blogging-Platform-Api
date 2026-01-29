package com.Dizast3r.blogging_api.Blog.DTO.Mappers;

import com.Dizast3r.blogging_api.Blog.DTO.Response.BlogResponseDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BlogDTOMapper {
    
    @Mapping(source = "blogTags", target = "blogTags", qualifiedByName = "tagsToStringList")
    BlogResponseDTO toResponse (Blog blog);
    
    @Named("tagsToStringList")
    default List<String> tagsToStringList(Set<Tag> tags) {
        if(tags == null) {
            return null;
        }
        return tags.stream().map(Tag::getNombre).collect(Collectors.toList());
    }
}
