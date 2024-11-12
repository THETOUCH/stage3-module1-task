import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.entity.News;
import com.mjc.school.service.NewsDTO;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.NewsServiceImpl;
import com.mjc.school.service.exceptions.NewsNotFoundException;
import com.mjc.school.service.exceptions.TitleLException;
import com.mjc.school.service.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class testService {
    @Mock
    private DataSource mockDataSource;

    @InjectMocks
    private NewsServiceImpl newsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        newsService = new NewsServiceImpl(mockDataSource, Validator.getInstance());
    }

    @Test
    public void testGetAllNews() {
        List<News> newsList = List.of(
                new News(1L, "title1", "content1",
                        LocalDateTime.parse("2024-11-12T20:08:48.871142"),
                        LocalDateTime.parse("2024-11-12T20:08:48.871142"), 1L),
                new News(2L, "title2", "content2",
                        LocalDateTime.parse("2024-11-12T20:08:48.871142"),
                        LocalDateTime.parse("2024-11-12T20:08:48.871142"), 2L)
        );

        when(mockDataSource.readAllNews()).thenReturn(newsList);

        List<NewsDTO> result = newsService.getAllNews();

        assertEquals(2, result.size());
        assertEquals("title1", result.get(0).getTitle());
        assertEquals("title2", result.get(1).getTitle());
    }

    @Test
    public void testCreateNews_Success() {
        NewsDTO newNews = new NewsDTO(null, "New Title", "New Content", LocalDateTime.now(), LocalDateTime.now(), 1L);
        News createdNews = new News(1L, "New Title", "New Content", LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(mockDataSource.createNews(any(News.class))).thenReturn(createdNews);

        NewsDTO createdNewsDTO = newsService.createNews(newNews);

        assertNotNull(createdNewsDTO);
        assertEquals("New Title", createdNewsDTO.getTitle());
        assertEquals("New Content", createdNewsDTO.getContent());
        assertEquals(1L, createdNewsDTO.getAuthorId());
    }

    /*@Test
    public void testCreateNews_ValidationError() {
        NewsDTO invalidNews = new NewsDTO(null, "T", "Content", LocalDateTime.now(), LocalDateTime.now(), 1L);
        assertThrows(TitleLException.class, () -> newsService.createNews(invalidNews));
    }*/

    @Test
    public void testUpdateNews_Success() {
        Long existingId = 1L;
        NewsDTO updatedNews = new NewsDTO(null, "Updated Title", "Updated Content", LocalDateTime.now(), LocalDateTime.now(), 1L);
        News existingNews = new News(existingId, "Old Title", "Old Content", LocalDateTime.now(), LocalDateTime.now(), 1L);
        News updatedNewsEntity = new News(existingId, "Updated Title", "Updated Content", LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(mockDataSource.readById(existingId)).thenReturn(existingNews);
        when(mockDataSource.updateNews(any(News.class))).thenReturn(updatedNewsEntity);

        NewsDTO result = newsService.updateNews(existingId, updatedNews);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());
        assertEquals(existingId, result.getId());
    }

    @Test
    public void testUpdateNews_NewsNotFound() {
        Long nonExistentId = 999L;
        NewsDTO updatedNews = new NewsDTO(null, "Updated Title", "Updated Content", LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(mockDataSource.readById(nonExistentId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> newsService.updateNews(nonExistentId, updatedNews));
    }

    @Test
    public void testDeleteNews_Success() {
        Long existingId = 1L;
        when(mockDataSource.deleteNewsById(existingId)).thenReturn(true);

        boolean isDeleted = newsService.deleteNews(existingId);

        assertTrue(isDeleted);
    }

    @Test
    public void testDeleteNews_NewsNotFound() {
        Long nonExistentId = 999L;
        when(mockDataSource.deleteNewsById(nonExistentId)).thenReturn(false);

        boolean isDeleted = newsService.deleteNews(nonExistentId);

        assertFalse(isDeleted);
    }
}
