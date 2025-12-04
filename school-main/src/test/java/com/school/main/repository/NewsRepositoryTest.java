package com.school.main.repository;

import com.school.main.entity.News;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    void findAllByOrderByDateDesc_ShouldReturnSortedNews() {
        News n1 = News.builder().title("Old").content("C").imageUrl("url").date(LocalDate.now().minusDays(2)).build();
        News n2 = News.builder().title("New").content("C").imageUrl("url").date(LocalDate.now()).build();

        newsRepository.save(n1);
        newsRepository.save(n2);

        List<News> list = newsRepository.findAllByOrderByDateDesc();

        assertEquals(2, list.size());
        assertEquals("New", list.get(0).getTitle());
    }
}
