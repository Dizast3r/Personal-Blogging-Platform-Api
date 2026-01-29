package com.Dizast3r.blogging_api.Blog.Services.Implementation;

import com.Dizast3r.blogging_api.Blog.DTO.Mappers.BlogDTOMapper;
import com.Dizast3r.blogging_api.Blog.DTO.Response.BlogResponseDTO;
import com.Dizast3r.blogging_api.Blog.Entities.Blog;
import com.Dizast3r.blogging_api.Blog.Entities.Tag;
import com.Dizast3r.blogging_api.Blog.Repositories.BlogRepository;
import com.Dizast3r.blogging_api.Blog.Repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceImplTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private BlogDTOMapper blogDTOMapper;

    @InjectMocks
    private BlogServiceImpl blogService;

    @BeforeEach
    void setUp() {
        lenient().when(blogDTOMapper.toResponse(any(Blog.class))).thenAnswer(invocation -> {
            Blog blog = invocation.getArgument(0);
            List<String> tags = new ArrayList<>();
            if (blog.getBlogTags() != null) {
                tags = blog.getBlogTags().stream().map(Tag::getNombre).collect(Collectors.toList());
            }
            return new BlogResponseDTO(blog.getTitulo(), blog.getContenido(), blog.getFechaDeCreacion(), tags);
        });
    }

    // --- Happy Paths ---

    @Test
    @DisplayName("Should create blog w/ new tags (tags stored with normalized name)")
    public void createBlog_WithNewTags_ShouldSaveAndAssignTags() {
        // Arrange
        // Input tag has spaces and mixed case " JavA "
        Tag inputTag = new Tag(null, " JavA  ", new HashSet<>());
        Set<Tag> inputTags = new HashSet<>();
        inputTags.add(inputTag);

        Blog newBlog = new Blog();
        newBlog.setTitulo("My First Blog");
        newBlog.setBlogTags(inputTags);

        // Mock: Tag doesn't exist initially, but after save it should be found
        Tag savedTag = new Tag(UUID.randomUUID(), "java", new HashSet<>());

        when(tagRepository.findByNombre("java"))
                .thenReturn(Collections.emptyList()) // First call: doesn't exist
                .thenReturn(List.of(savedTag)); // Subsequent calls: return saved tag

        // Mock: Saving the tag (repo returns the saved entity)
        when(tagRepository.save(any(Tag.class))).thenReturn(savedTag);

        // Mock: Saving the blog
        when(blogRepository.save(any(Blog.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        BlogResponseDTO result = blogService.createBlog(newBlog);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getBlogTags().size());
        String resultTag = result.getBlogTags().iterator().next();

        // Check normalization
        assertEquals("java", resultTag);
        assertNotNull(tagRepository.findByNombre(resultTag).get(0).getTagId()); // Should be the saved one

        verify(tagRepository).save(any(Tag.class)); // Verifies save was called
        verify(blogRepository).save(newBlog);
    }

    @Test
    @DisplayName("Should create blog w/ existing tags (deduplication)")
    public void createBlog_WithExistingTags_ShouldReuseTagFromDB() {
        // Arrange
        // Input: "java"
        Tag inputTag = new Tag(null, "java", new HashSet<>());
        Set<Tag> inputTags = new HashSet<>();
        inputTags.add(inputTag);

        Blog newBlog = new Blog();
        newBlog.setTitulo("Java Blog");
        newBlog.setBlogTags(inputTags);

        // Mock: Tag ALREADY exists in DB
        Tag existingDbTag = new Tag(UUID.randomUUID(), "java", new HashSet<>());
        when(tagRepository.findByNombre("java")).thenReturn(List.of(existingDbTag));

        // Mock: Blog save
        when(blogRepository.save(any(Blog.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        BlogResponseDTO result = blogService.createBlog(newBlog);

        // Assert
        assertEquals(1, result.getBlogTags().size());
        String resultTag = result.getBlogTags().iterator().next();

        // Should contain the ID from the existing tag
        assertEquals(existingDbTag.getTagId(), tagRepository.findByNombre(resultTag).get(0).getTagId());

        // Verify Tag SAVE was NEVER called (deduplication success)
        verify(tagRepository, never()).save(any(Tag.class));
        verify(blogRepository).save(newBlog);
    }

    // --- Edge Cases ---

    @Test
    @DisplayName("Should handle Mixed Tags (One new, One existing)")
    public void createBlog_WithMixedTags_ShouldHandleBothcorrectly() {
        // Arrange
        Tag tagNew = new Tag(null, "python", new HashSet<>());
        Tag tagExisting = new Tag(null, "java", new HashSet<>());

        Set<Tag> tags = new HashSet<>();
        tags.add(tagNew);
        tags.add(tagExisting);

        Blog blog = new Blog();
        blog.setTitulo("Polyglot");
        blog.setBlogTags(tags);

        // Mocking
        Tag existingJava = new Tag(UUID.randomUUID(), "java", new HashSet<>());
        Tag savedPython = new Tag(UUID.randomUUID(), "python", new HashSet<>());

        // "python" -> New (Empty list initially, then return saved)
        when(tagRepository.findByNombre("python"))
                .thenReturn(Collections.emptyList()) // First call: doesn't exist
                .thenReturn(List.of(savedPython)); // Subsequent calls: return saved tag

        // "java" -> Exists (always return existing)
        when(tagRepository.findByNombre("java")).thenReturn(List.of(existingJava));

        // Mock Save for new tag
        when(tagRepository.save(argThat(t -> t.getNombre().equals("python")))).thenReturn(savedPython);

        when(blogRepository.save(any(Blog.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        BlogResponseDTO result = blogService.createBlog(blog);

        // Assert
        assertEquals(2, result.getBlogTags().size());

        // Check IDs
        boolean foundJava = result.getBlogTags().stream()
                .anyMatch(t -> tagRepository.findByNombre(t).get(0).getTagId().equals(existingJava.getTagId()));
        boolean foundPython = result.getBlogTags().stream()
                .anyMatch(t -> tagRepository.findByNombre(t).get(0).getTagId().equals(savedPython.getTagId()));

        assertTrue(foundJava, "Should have reused existing Java tag");
        assertTrue(foundPython, "Should have used new saved Python tag");

        // Verify only 1 save called for tags
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    @DisplayName("Should handle empty tags list")
    public void createBlog_WithNoTags_ShouldSaveBlog() {
        // Arrange
        Blog blog = new Blog();
        blog.setTitulo("No Tags Blog");
        blog.setBlogTags(new HashSet<>()); // Empty

        when(blogRepository.save(any(Blog.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        BlogResponseDTO result = blogService.createBlog(blog);

        // Assert
        assertTrue(result.getBlogTags().isEmpty());
        verify(tagRepository, never()).findByNombre(anyString());
        verify(tagRepository, never()).save(any(Tag.class));
        verify(blogRepository).save(blog);
    }
}
