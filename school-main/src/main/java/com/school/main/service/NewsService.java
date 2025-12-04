package com.school.main.service;

import com.school.main.client.NotificationClient;
import com.school.main.client.NotificationDTO;
import com.school.main.entity.News;
import com.school.main.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;
    private final NotificationClient notificationClient;

    @Cacheable("newsList")
    public List<News> getAllNews() {
        return newsRepository.findAllByOrderByDateDesc();
    }

    public List<News> getTop6News() {
        return newsRepository.findTop6ByOrderByDateDesc();
    }

    public News getNewsById(UUID id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
    }

    @Transactional
    @CacheEvict(value = "newsList", allEntries = true)
    public News createNews(News news) {
        log.info("Creating news: {}", news.getTitle());
        if (news.getDate() == null) {
            news.setDate(LocalDate.now());
        }
        News savedNews = newsRepository.save(news);

        // Notify if important (logic can be refined, for now assume all admin created
        // news are important)
        try {
            String username = "System";
            try {
                username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                        .getAuthentication().getName();
            } catch (Exception e) {
                log.warn("Could not get username for notification", e);
            }

            notificationClient.createNotification(NotificationDTO.builder()
                    .title("New News Published")
                    .message("Check out: " + savedNews.getTitle())
                    .type("NEWS_CREATED")
                    .username(username)
                    .build());
        } catch (Exception e) {
            log.error("Failed to send notification", e);
        }

        return savedNews;
    }

    @Transactional
    @CacheEvict(value = "newsList", allEntries = true)
    public News updateNews(News news) {
        log.info("Updating news: {}", news.getId());
        return newsRepository.save(news);
    }

    @Transactional
    @CacheEvict(value = "newsList", allEntries = true)
    public void deleteNews(UUID id) {
        log.info("Deleting news: {}", id);
        newsRepository.deleteById(id);
    }
}
