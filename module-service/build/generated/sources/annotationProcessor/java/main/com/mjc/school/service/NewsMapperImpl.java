package com.mjc.school.service;

import com.mjc.school.repository.entity.News;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-13T02:18:24+0600",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.2.jar, environment: Java 17.0.10 (Amazon.com Inc.)"
)
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsDTO newsToNewsDTO(News news) {
        if ( news == null ) {
            return null;
        }

        NewsDTO newsDTO = new NewsDTO();

        newsDTO.setId( news.getId() );
        newsDTO.setTitle( news.getTitle() );
        newsDTO.setContent( news.getContent() );
        newsDTO.setCreateDate( news.getCreateDate() );
        newsDTO.setLastUpdateDate( news.getLastUpdateDate() );
        newsDTO.setAuthorId( news.getAuthorId() );

        return newsDTO;
    }

    @Override
    public News newsDTOToNews(NewsDTO newsDTO) {
        if ( newsDTO == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String content = null;
        LocalDateTime createDate = null;
        Long authorId = null;
        LocalDateTime lastUpdateDate = null;

        id = newsDTO.getId();
        title = newsDTO.getTitle();
        content = newsDTO.getContent();
        createDate = newsDTO.getCreateDate();
        authorId = newsDTO.getAuthorId();
        lastUpdateDate = newsDTO.getLastUpdateDate();

        News news = new News( id, title, content, createDate, lastUpdateDate, authorId );

        return news;
    }
}
