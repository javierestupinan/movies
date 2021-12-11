package com.entertainment.movies.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.entertainment.movies.repository.entities.User;

public interface IUserRepository extends CrudRepository<User, Integer> {
	
	List<User> findByEmail(String email);

}
