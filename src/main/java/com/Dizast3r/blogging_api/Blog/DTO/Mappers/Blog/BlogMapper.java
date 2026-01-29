package com.Dizast3r.blogging_api.Blog.DTO.Mappers.Blog;

import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Tag.TagMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogCreateDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogModifyDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {TagMapper.class})
public interface BlogMapper {
    
    
    Blog toEntityCreate(BlogCreateDTO blogDTO);
    
    Blog toEntityModify(BlogModifyDTO blogDTO);
    
    
}
