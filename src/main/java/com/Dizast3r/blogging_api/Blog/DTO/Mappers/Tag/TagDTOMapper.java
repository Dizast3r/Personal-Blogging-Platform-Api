
package com.Dizast3r.blogging_api.Blog.DTO.Mappers.Tag;

import com.Dizast3r.blogging_api.Blog.DTO.Response.Tag.TagResponseDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagDTOMapper {
    
    @Mapping(source = "blogs", target = "associatedBlogs", qualifiedByName = "BlogToTitleList")
    TagResponseDTO toResponse(Tag tag);
    
    @Named("BlogToTitleList")
    default List<String> blogToTitleList (Set<Blog> blogs) {
        return blogs.stream().map(Blog::getTitulo).collect(Collectors.toList());
    }
}
