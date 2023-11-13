package com.chudzik.mappers;

import com.chudzik.dtos.MovieDto;
import com.chudzik.models.Movie;

public class MovieMapper {

    MovieMapper() {

    }

    public static MovieDto convertToDto(Movie movie) {
        MovieDto out = new MovieDto();
        out.setId(movie.getId());
        out.setDescription(movie.getDescription());
        out.setDuration(movie.getDuration());
        out.setTitle(movie.getTitle());
        return out;
    }

}
