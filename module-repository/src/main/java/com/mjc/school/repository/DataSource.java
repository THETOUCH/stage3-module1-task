package com.mjc.school.repository;

import com.mjc.school.repository.entity.Author;
import com.mjc.school.repository.entity.News;

import java.util.List;

public interface DataSource {
    List<Author> readAllAuthors();

    List<News> readAllNews();

    News readById(Long id);

    News createNews(News news);

    News updateNews(News news);

    Boolean deleteNewsById(Long id);
}
