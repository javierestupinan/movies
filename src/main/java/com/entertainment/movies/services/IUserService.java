package com.entertainment.movies.services;

import com.entertainment.movies.model.dtos.GeneralResponse;
import com.entertainment.movies.model.dtos.LoginResponseDTO;
import com.entertainment.movies.model.dtos.UserRequestDTO;

public interface IUserService {
	public LoginResponseDTO logIn(String user, String password);
	
	public GeneralResponse signUp(UserRequestDTO userData);
}
