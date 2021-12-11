package com.entertainment.movies.repository;

import org.springframework.data.repository.CrudRepository;

import com.entertainment.movies.repository.entities.Movie;

public interface IMovieRepository extends CrudRepository<Movie, Integer>{

}
