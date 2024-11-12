package com.mjc.school.repository;

import com.mjc.school.repository.datasource.AuthorDataSource;
import com.mjc.school.repository.datasource.NewsDataSource;
import com.mjc.school.repository.entity.Author;
import com.mjc.school.repository.entity.News;

import java.util.List;
import java.util.Optional;

public class DataSourceImpl implements DataSource {
    private final NewsDataSource newsDataSource;
    private final AuthorDataSource authorDataSource;

    public DataSourceImpl(AuthorDataSource authorDataSource, NewsDataSource newsDataSource) {
        this.authorDataSource = authorDataSource;
        this.newsDataSource = newsDataSource;
    }

    @Override
    public List<Author> readAllAuthors() {
        return authorDataSource.getAuthors();
    }

    @Override
    public List<News> readAllNews() {
        return newsDataSource.getNews();
    }

    @Override
    public News readById(Long id) {
        return newsDataSource.getNews().stream()
                .filter(news -> news.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public News createNews(News news) {
        news.setId(generateNewIdForNews());
        newsDataSource.getNews().add(news);
        return news;
    }

    private long generateNewIdForNews() {
        return newsDataSource.getNews().stream()
                .mapToLong(News::getId)
                .max()
                .orElse(0L) + 1;
    }

    @Override
    public News updateNews(News news) {
        Optional<News> existingNews = newsDataSource.getNews().stream()
                .filter(news1 -> news1.getId().equals(news.getId()))
                .findFirst();

        existingNews.ifPresent(news1 -> {
            news1.setTitle(news.getTitle());
            news1.setContent(news.getContent());
            news1.setLastUpdateDate(news.getLastUpdateDate());
            news1.setAuthorId(news.getAuthorId());
        });
        return existingNews.orElse(null);
    }

    @Override
    public Boolean deleteNewsById(Long id) {
        return newsDataSource.getNews().removeIf(news -> news.getId().equals(id));
    }
}
