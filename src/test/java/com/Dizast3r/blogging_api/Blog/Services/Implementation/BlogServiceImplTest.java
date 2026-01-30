package com.Dizast3r.blogging_api.Blog.Services.Implementation;

import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Blog.BlogDTOMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Mappers.Blog.BlogMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Blog.BlogCreateDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Request.Tag.TagDTO;
import com.Dizast3r.blogging_api.Blog.DTO.Response.Blog.BlogResponseDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import com.Dizast3r.blogging_api.Blog.Repositories.BlogRepository;
import com.Dizast3r.blogging_api.Blog.Services.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceImplTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private TagService tagService;

    @Mock
    private BlogDTOMapper blogDTOMapper;

    @Mock
    private BlogMapper blogMapper;

    @InjectMocks
    private BlogServiceImpl blogService;

    @BeforeEach
    void setUp() {
        // Mock BlogMapper to convert DTO to Entity
        lenient().when(blogMapper.toEntityCreate(any(BlogCreateDTO.class))).thenAnswer(invocation -> {
            BlogCreateDTO dto = invocation.getArgument(0);
            Blog blog = new Blog();
            blog.setTitulo(dto.getTitulo());
            blog.setContenido(dto.getContenido());
            return blog;
        });

        // Mock BlogDTOMapper to convert Entity to Response DTO
        lenient().when(blogDTOMapper.toResponse(any(Blog.class))).thenAnswer(invocation -> {
            Blog blog = invocation.getArgument(0);
            List<String> tagNames = new ArrayList<>();
            if (blog.getBlogTags() != null) {
                tagNames = blog.getBlogTags().stream()
                        .map(Tag::getNombre)
                        .toList();
            }
            return new BlogResponseDTO(
                    blog.getBlogId(),
                    blog.getTitulo(),
                    blog.getContenido(),
                    blog.getFechaDeCreacion(),
                    blog.getFechaDeModificacion(),
                    tagNames);
        });
    }

    // --- Happy Paths ---

    @Test
    @DisplayName("Should create blog w/ new tags (tags stored with normalized name)")
    public void createBlog_WithNewTags_ShouldSaveAndAssignTags() {
        // Arrange
        TagDTO tagDTO = new TagDTO();
        tagDTO.setNombre(" JavA  ");

        List<TagDTO> tagDTOs = new ArrayList<>();
        tagDTOs.add(tagDTO);

        BlogCreateDTO blogCreateDTO = new BlogCreateDTO(
                "My First Blog",
                "Content here",
                tagDTOs);

        // Mock: TagService creates and returns normalized tag
        Tag savedTag = new Tag(UUID.randomUUID(), "java", new HashSet<>());
        when(tagService.createTag(any(TagDTO.class))).thenReturn(savedTag);

        // Mock: Blog save
        when(blogRepository.save(any(Blog.class))).thenAnswer(i -> {
            Blog blog = i.getArgument(0);
            blog.setBlogId(UUID.randomUUID());
            return blog;
        });

        // Act
        BlogResponseDTO result = blogService.createBlog(blogCreateDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getBlogId());
        assertEquals(1, result.getBlogTags().size());
        assertEquals("java", result.getBlogTags().get(0));

        verify(tagService).createTag(any(TagDTO.class));
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    @DisplayName("Should create blog w/ existing tags (deduplication)")
    public void createBlog_WithExistingTags_ShouldReuseTagFromDB() {
        // Arrange
        TagDTO tagDTO = new TagDTO();
        tagDTO.setNombre("java");

        List<TagDTO> tagDTOs = new ArrayList<>();
        tagDTOs.add(tagDTO);

        BlogCreateDTO blogCreateDTO = new BlogCreateDTO(
                "Java Blog",
                "Content about Java",
                tagDTOs);

        // Mock: TagService returns existing tag (deduplication happens in TagService)
        Tag existingTag = new Tag(UUID.randomUUID(), "java", new HashSet<>());
        when(tagService.createTag(any(TagDTO.class))).thenReturn(existingTag);

        // Mock: Blog save
        when(blogRepository.save(any(Blog.class))).thenAnswer(i -> {
            Blog blog = i.getArgument(0);
            blog.setBlogId(UUID.randomUUID());
            return blog;
        });

        // Act
        BlogResponseDTO result = blogService.createBlog(blogCreateDTO);

        // Assert
        assertEquals(1, result.getBlogTags().size());
        assertEquals("java", result.getBlogTags().get(0));

        verify(tagService).createTag(any(TagDTO.class));
        verify(blogRepository).save(any(Blog.class));
    }

    // --- Edge Cases ---

    @Test
    @DisplayName("Should handle Mixed Tags (One new, One existing)")
    public void createBlog_WithMixedTags_ShouldHandleBothcorrectly() {
        // Arrange
        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setNombre("python");

        TagDTO tagDTO2 = new TagDTO();
        tagDTO2.setNombre("java");

        List<TagDTO> tagDTOs = new ArrayList<>();
        tagDTOs.add(tagDTO1);
        tagDTOs.add(tagDTO2);

        BlogCreateDTO blogCreateDTO = new BlogCreateDTO(
                "Polyglot",
                "Content about multiple languages",
                tagDTOs);

        // Mock: TagService handles both new and existing tags
        Tag pythonTag = new Tag(UUID.randomUUID(), "python", new HashSet<>());
        Tag javaTag = new Tag(UUID.randomUUID(), "java", new HashSet<>());

        // Use thenAnswer to inspect the actual argument and return appropriate tag
        when(tagService.createTag(any(TagDTO.class))).thenAnswer(invocation -> {
            TagDTO dto = invocation.getArgument(0);
            if (dto.getNombre().equals("python")) {
                return pythonTag;
            } else if (dto.getNombre().equals("java")) {
                return javaTag;
            }
            return null;
        });

        // Mock: Blog save
        when(blogRepository.save(any(Blog.class))).thenAnswer(i -> {
            Blog blog = i.getArgument(0);
            blog.setBlogId(UUID.randomUUID());
            return blog;
        });

        // Act
        BlogResponseDTO result = blogService.createBlog(blogCreateDTO);

        // Assert
        assertEquals(2, result.getBlogTags().size());
        assertTrue(result.getBlogTags().contains("java"));
        assertTrue(result.getBlogTags().contains("python"));

        verify(tagService, times(2)).createTag(any(TagDTO.class));
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    @DisplayName("Should handle empty tags list")
    public void createBlog_WithNoTags_ShouldSaveBlog() {
        // Arrange
        BlogCreateDTO blogCreateDTO = new BlogCreateDTO(
                "No Tags Blog",
                "Content without tags",
                new ArrayList<>());

        // Mock: Blog save
        when(blogRepository.save(any(Blog.class))).thenAnswer(i -> {
            Blog blog = i.getArgument(0);
            blog.setBlogId(UUID.randomUUID());
            return blog;
        });

        // Act
        BlogResponseDTO result = blogService.createBlog(blogCreateDTO);

        // Assert
        assertTrue(result.getBlogTags().isEmpty());
        verify(tagService, never()).createTag(any(TagDTO.class));
        verify(blogRepository).save(any(Blog.class));
    }
}
