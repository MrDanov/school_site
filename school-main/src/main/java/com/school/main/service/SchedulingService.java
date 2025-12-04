package com.school.main.service;

import com.school.main.entity.News;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {

    private final NewsService newsService;

    // Auto-post holiday news every year on Dec 25th (Example)
    @Scheduled(cron = "0 0 8 25 12 ?")
    public void postChristmasMessage() {
        log.info("Posting scheduled Christmas message");
        News news = News.builder()
                .title("Merry Christmas!")
                .content("We wish all students and staff a Merry Christmas and a Happy New Year!")
                .date(LocalDate.now())
                .build();
        newsService.createNews(news);
    }

    // Auto-archive/cleanup old news (Example: every month)
    @Scheduled(fixedRate = 2592000000L) // 30 days
    public void cleanupOldData() {
        log.info("Running scheduled cleanup task (placeholder)");
        // Logic to archive or delete old events/news
    }
}
