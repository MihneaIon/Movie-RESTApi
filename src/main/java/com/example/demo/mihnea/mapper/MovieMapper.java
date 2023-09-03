package com.example.demo.mihnea.mapper;

import com.example.demo.mihnea.model.Movie;
import com.example.demo.mihnea.modelDto.MovieDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    MovieDto movietoDto(Movie movie);
}
