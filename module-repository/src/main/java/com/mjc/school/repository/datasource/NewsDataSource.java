package com.mjc.school.repository.datasource;

import com.mjc.school.repository.entity.Author;
import com.mjc.school.repository.entity.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NewsDataSource {
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private List<String> newsList;
    private List<String> contentList;
    private List<News> news;
    private AuthorDataSource authorDataSource;

    public NewsDataSource(AuthorDataSource authorDataSource) {
        newsList = new ArrayList<String>();
        contentList = new ArrayList<String>();
        news = new ArrayList<>();
        this.authorDataSource = authorDataSource;
        loadNews();
        loadContent();
        newsCreateList();
    }
    private Long getAuthorsId(int i) {
        List<Author> authors = authorDataSource.getAuthors();
        if (i < 0 || i >= authors.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return authors.get(authors.size() - 1 - i).getId();
    }
    public List<News> getNews() {
        return news;
    }
    private void newsCreateList() {
        for (int i = 0; i < newsList.size(); i++) {
            news.add(new News(
                    (long) i,
                    newsList.get(i),
                    contentList.get(i),
                    getCurrentDateTimeInISOFormat(),
                    getCurrentDateTimeInISOFormat(),
                    getAuthorsId(i)
            ));
        }
    }

    public List<String> getNewsList() {
        return newsList;
    }
    public List<String> getContentList() {
        return contentList;
    }

    private void loadNews() {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("news.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                newsList.add(line);
            }
        } catch (IOException e) {
            System.err.println("NewsDataSource (news) error" + e.getMessage());
        }
    }
    private void loadContent() {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("content.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentList.add(line);
            }
        } catch (IOException e) {
            System.err.println("NewsDataSource (content) error" + e.getMessage());
        }
    }
    private LocalDateTime getCurrentDateTimeInISOFormat() {
        return LocalDateTime.now();
    }
}