package com.school.main.repository;

import com.school.main.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {
    List<News> findAllByOrderByDateDesc();

    List<News> findTop6ByOrderByDateDesc();
}
