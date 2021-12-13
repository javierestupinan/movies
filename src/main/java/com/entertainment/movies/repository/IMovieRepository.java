package com.entertainment.movies.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.entertainment.movies.repository.entities.Movie;

public interface IMovieRepository extends CrudRepository<Movie, Integer>{
	
	Page<Movie> findAll(Pageable pageable);
	
	List<Movie> findByName(String name);

}
