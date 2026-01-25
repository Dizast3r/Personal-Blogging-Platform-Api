/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Dizast3r.blogging_api.Blog.Services;

import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author User
 */
public interface TagService {
    
    public List<Tag> getAllTags();
    public Tag getTagById(UUID id);
    public List<Tag> getTagByName();
    public void deleteTag(UUID id);
    public void modifyTag(Tag tag);
}
