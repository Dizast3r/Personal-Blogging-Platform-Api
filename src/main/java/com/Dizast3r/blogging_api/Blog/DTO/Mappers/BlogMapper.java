package com.Dizast3r.blogging_api.Blog.DTO.Mappers;

import com.Dizast3r.blogging_api.Blog.DTO.Request.BlogCreateDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.BlogModifyDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    
    @Mapping(source = "blogTags", target = "blogTags", qualifiedByName = "stringToTagList")
    Blog toEntityCreate(BlogCreateDTO blogDTO);
    
    @Mapping(source = "blogTags", target = "blogTags", qualifiedByName = "stringToTagList")
    Blog toEntityModify(BlogModifyDTO blogDTO);
    
    @Named("stringToTagList")
    default Set<Tag> StringToTagList(List<String> tags){
        if(tags == null) {
            return null;
        }
        return tags.stream().map(tag -> new Tag(null, tag, null)).collect(Collectors.toSet());
    }
}
