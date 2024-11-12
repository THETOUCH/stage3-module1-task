package com.mjc.school.service;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.DataSourceImpl;
import com.mjc.school.repository.entity.News;
import com.mjc.school.service.exceptions.ContentLException;
import com.mjc.school.service.exceptions.DataFormatException;
import com.mjc.school.service.exceptions.NewsNotFoundException;
import com.mjc.school.service.exceptions.TitleLException;
import com.mjc.school.service.validators.Validator;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NewsServiceImpl implements NewsService {
    private final DataSource dataSource;
    private final Validator validator;

    public NewsServiceImpl(DataSource dataSource, Validator validator) {
        this.dataSource = dataSource;
        this.validator = validator;
    }

    @Override
    public List<NewsDTO> getAllNews() {
        return dataSource.readAllNews()
                .stream()
                .map(NewsMapper.INSTANCE::newsToNewsDTO)
                .toList();
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        News news = dataSource.readById(id);

        if (news == null) {
            throw new NullPointerException("News with id " + id + " not found");
        }
        return NewsMapper.INSTANCE.newsToNewsDTO(news);
    }

    @Override
    public NewsDTO createNews(NewsDTO news) {
        LocalDateTime date=LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        try {
            validator.validate(news);
            return NewsMapper.INSTANCE.newsToNewsDTO(dataSource.createNews(
                    new News(
                    getId(dataSource),
                    news.getTitle(),
                    news.getContent(),
                    date,
                    date,
                    news.getAuthorId()
            )));
        } catch(TitleLException | ContentLException | DataFormatException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public NewsDTO updateNews(Long id, NewsDTO newsDto) {
        LocalDateTime date=LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        try {
            validator.validate(newsDto);
            return NewsMapper.INSTANCE.newsToNewsDTO(dataSource.updateNews(
                    new News(
                            id,
                            newsDto.getTitle(),
                            newsDto.getContent(),
                            dataSource.readById(id).getCreateDate(),
                            date,
                            newsDto.getAuthorId()
                    )
            ));
        } catch(TitleLException | ContentLException | DataFormatException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean deleteNews(Long id) {
        return dataSource.deleteNewsById(id); //check
    }
    private Long getId(DataSource dataSource) {
        return (long) (dataSource.readAllNews().size() + 1);
    }
}
