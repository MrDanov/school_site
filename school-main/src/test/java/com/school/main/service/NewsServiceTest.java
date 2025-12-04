package com.school.main.service;

import com.school.main.client.NotificationClient;
import com.school.main.entity.News;
import com.school.main.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private NewsService newsService;

    @Test
    void createNews_ShouldSaveAndNotify() {
        News news = News.builder()
                .title("Test News")
                .content("Content")
                .imageUrl("url")
                .build();

        when(newsRepository.save(any(News.class))).thenReturn(news);

        News created = newsService.createNews(news);

        assertNotNull(created);
        assertEquals("Test News", created.getTitle());
        verify(newsRepository, times(1)).save(any(News.class));
        verify(notificationClient, times(1)).createNotification(any());
    }

    @Test
    void getNewsById_ShouldReturnNews() {
        UUID id = UUID.randomUUID();
        News news = News.builder().id(id).title("Test").build();

        when(newsRepository.findById(id)).thenReturn(Optional.of(news));

        News found = newsService.getNewsById(id);

        assertEquals(id, found.getId());
    }
}
