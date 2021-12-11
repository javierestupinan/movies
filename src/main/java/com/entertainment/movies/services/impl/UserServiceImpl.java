package com.entertainment.movies.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.entertainment.movies.model.dtos.GeneralResponse;
import com.entertainment.movies.model.dtos.LoginResponseDTO;
import com.entertainment.movies.model.dtos.Response;
import com.entertainment.movies.model.dtos.UserRequestDTO;
import com.entertainment.movies.repository.IUserRepository;
import com.entertainment.movies.repository.entities.Rol;
import com.entertainment.movies.repository.entities.User;
import com.entertainment.movies.services.IUserService;
import com.entertainment.movies.utils.Constants.GeneralStrings;
import com.entertainment.movies.utils.UtilValidation;

import lombok.AccessLevel;
import lombok.Setter;

@Service
class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userDB;

	private User userFound = null;
	
	@Setter(value = AccessLevel.PROTECTED)
	@Value("${minutes.token.session:10}")
	private Integer sessionMinutes;

	@Override
	public LoginResponseDTO logIn(String user, String password) {
		final LoginResponseDTO response = new LoginResponseDTO();
		final String localUser = user != null ? user.trim().toLowerCase() : GeneralStrings.EMPTY;
		final String localPassword = password != null ? password.trim() : GeneralStrings.EMPTY;
		if (!StringUtils.hasText(localUser) || !StringUtils.hasText(localPassword)) {
			response.setResponse(Response.badRequest("Some of the parameters don't have information.", localUser));
			return response;
		}

		final GeneralResponse emailValidation = UtilValidation.validateEmail(localUser);
		if (!emailValidation.getStatus().equals(HttpStatus.OK)) {
			response.setResponse(emailValidation);
			return response;
		}

		final GeneralResponse passwordValidation = UtilValidation.validatePassword(localPassword, localUser);
		if (!passwordValidation.getStatus().equals(HttpStatus.OK)) {
			response.setResponse(passwordValidation);
			return response;
		}

		final GeneralResponse loginValidation = validateUserSignIn(localUser, localPassword);
		if (loginValidation.getStatus().equals(HttpStatus.OK) && userFound != null) {
			response.setToken(UtilValidation.generateJWT(user, userFound, sessionMinutes));
		}
		response.setResponse(loginValidation);
		return response;
	}
	
	/**
	 * Logic to sign-in the user on the database.
	 * @param user data
	 * @param password String password.
	 * @return response GeneralResponse
	 */
	private GeneralResponse validateUserSignIn(String user, String password) {
		try {
			List<User> userList = userDB.findByEmail(user);
			if (userList != null && !userList.isEmpty()) {
				userFound = userList.get(0);
			} else {
				return Response.notFound("The user was not found.", user);
			}
			if (userFound != null) {
				boolean comparePasswords = UtilValidation.matchPassword(password, userFound.getPassword());
				if (!comparePasswords) {
					return Response.unauthorized("The user or password are not correct.", user);
				}
			}
		} catch (Exception e) {
			String error = "There were some problems in the system, please contact the administrator. \n "
					+ e.getMessage();
			return Response.internalServerError(error, user);
		}
		return Response.ok("User Logged in.", user);
	}

	@Override
	public GeneralResponse signUp(UserRequestDTO userData) {
		final String localUser = userData.getEmail() != null ? userData.getEmail().trim().toLowerCase() : GeneralStrings.EMPTY;
		final String localPassword = userData.getPassword() != null ? userData.getPassword().trim() : GeneralStrings.EMPTY;
		
		GeneralResponse response = Response.created("User created.", localUser);
		try {
			final GeneralResponse emailValidation = UtilValidation.validateEmail(localUser);
			if (!emailValidation.getStatus().equals(HttpStatus.OK)) {
				return emailValidation;
			}
			
			final GeneralResponse passwordValidation = UtilValidation.validatePassword(localPassword, localUser);
			if (!passwordValidation.getStatus().equals(HttpStatus.OK)) {
				return passwordValidation;
			}
			
			//validate the user
			List<User> userList = userDB.findByEmail(localUser);
			if (!userList.isEmpty()) {
				return Response.found("The user already exist.", localUser);
			}
			
			//Insert the user
			User newUser = new User();
			newUser.setEmail(localUser);
			newUser.setPassword(UtilValidation.obtainBcrypPassword(localPassword));
			Rol rol = new Rol();
			rol.setRolId(userData.getRol().getIdRol());
			rol.setName(userData.getRol().getRol());
			newUser.setRol(rol);
			userDB.save(newUser);
			
		} catch (Exception e) {
			String error = "There were some problems in the system, please contact the administrator. \n "
					+ e.getMessage();
			return Response.internalServerError(error, userData);
		}
		return response;
	}

}
