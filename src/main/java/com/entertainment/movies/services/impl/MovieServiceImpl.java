package com.entertainment.movies.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.entertainment.movies.configuration.JWTAuthorizationFilter;
import com.entertainment.movies.model.dtos.BasicMovieDTO;
import com.entertainment.movies.model.dtos.GeneralResponse;
import com.entertainment.movies.model.dtos.MovieDto;
import com.entertainment.movies.model.dtos.Response;
import com.entertainment.movies.repository.IMovieRepository;
import com.entertainment.movies.repository.entities.Movie;
import com.entertainment.movies.repository.entities.User;
import com.entertainment.movies.services.IMovieService;

@Service
class MovieServiceImpl implements IMovieService {

	@Autowired
	private IMovieRepository movieRepository;

	@Override
	public Page<MovieDto> lookupAll(Integer pageNumber, Integer rowsNumber) {
		Pageable sortedByPriceDesc = PageRequest.of(pageNumber < 0 ? 1 : pageNumber, rowsNumber <= 0 ? 1 : rowsNumber,
				Sort.by("movieId").descending());
		List<MovieDto> movies = movieRepository.findAll(sortedByPriceDesc).stream()
				.map(movie -> new MovieDto(movie.getMovieId(), movie.getName(), movie.getGender(), movie.getRate()))
				.collect(Collectors.toList());
		return new PageImpl<MovieDto>(movies);
	}

	@Override
	public GeneralResponse updateMovie(MovieDto movie, String logedUser) {
		Optional<Movie> savedMovie = movieRepository.findById(movie.getId());
		try {
			if (savedMovie.isPresent()) {
				Movie modifiedMovie = savedMovie.get();
				String idUser = "0";
				if (logedUser.length() > 10) {
					idUser = JWTAuthorizationFilter.getUser(logedUser);
				}
				if (modifiedMovie.getUser().getUserId().toString().equals(idUser)) {
					modifiedMovie.setName(movie.getName().trim().toUpperCase());
					modifiedMovie.setGender(movie.getGender().trim().toUpperCase());
					modifiedMovie.setRate(movie.getRate());
					movieRepository.save(modifiedMovie);
					return Response.ok("Movie Updated", movie);
				} else {
					return Response.unauthorized("You don't have permission to madificate this record.", movie);
				}
			} else {
				return Response.notFound("The movie was not found.", movie);
			}
		} catch (Exception e) {
			return Response.internalServerError("Upss something went wrong, please contac the administrator." + e.getMessage(), movie);
		}
	}

	@Override
	public GeneralResponse createMovie(BasicMovieDTO movie, String logedUser) {
		try {
			List<Movie> savedMovie = movieRepository.findByName(movie.getName().trim().toUpperCase());
			if (savedMovie.isEmpty()) {
				String idUser = "0";
				if (logedUser.length() > 10) {
					idUser = JWTAuthorizationFilter.getUser(logedUser);
				} else {
					return Response.badRequest("the user was not found and you don't have permisions to modify the record", movie);
				}
				User user = new User();
				user.setUserId(Integer.valueOf(idUser));
				Movie modifiedMovie = new Movie();
				modifiedMovie.setUser(user);
				modifiedMovie.setName(movie.getName().trim().toUpperCase());
				modifiedMovie.setGender(movie.getGender().trim().toUpperCase());
				modifiedMovie.setRate(movie.getRate());
				movieRepository.save(modifiedMovie);
				return Response.created("Movie created", movie);
			} else {
				return Response.found("The movie was found and it can not be created again.", movie);
			}
		} catch (Exception e) {
			return Response.internalServerError("Upss something went wrong, please contac the administrator." + e.getMessage(), movie);
		}
	}

}
