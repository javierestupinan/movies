package com.entertainment.movies.services;

import org.springframework.data.domain.Page;

import com.entertainment.movies.model.dtos.BasicMovieDTO;
import com.entertainment.movies.model.dtos.GeneralResponse;
import com.entertainment.movies.model.dtos.MovieDto;

public interface IMovieService {
	public Page<MovieDto> lookupAll(Integer pageNumber, Integer rowsNumber);

	public GeneralResponse updateMovie(MovieDto movie, String logedUser);
	
	public GeneralResponse createMovie(BasicMovieDTO movie, String logedUser);
}
