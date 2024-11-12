package com.mjc.school.service;

import com.mjc.school.repository.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    NewsDTO newsToNewsDTO(News news);
    News newsDTOToNews(NewsDTO newsDTO);
}