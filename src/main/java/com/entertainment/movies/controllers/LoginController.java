package com.entertainment.movies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entertainment.movies.model.dtos.GeneralResponse;
import com.entertainment.movies.model.dtos.LoginResponseDTO;
import com.entertainment.movies.model.dtos.UserRequestDTO;
import com.entertainment.movies.services.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("security")
@Api(value = "LoginController", description = "REST APIs related to User Entity!!!!")
public class LoginController {

	@Autowired
	private IUserService userService;

	@PostMapping(value = "sign-up")
	@ApiOperation(value = "insert an user into the aplication.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "bad request!!!"),
			@ApiResponse(code = 201, message = "created!!!"),
			@ApiResponse(code = 302, message = "found!!!"),
			@ApiResponse(code = 404, message = "not found!!!"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<GeneralResponse> signUp(@RequestBody() UserRequestDTO user) {
		GeneralResponse response = userService.signUp(user);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping(value = "log-in")
	@ApiOperation(value = "Authenticate the user on the application.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "bad request!!!"),
			@ApiResponse(code = 401, message = "not authorized!!!"), @ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<LoginResponseDTO> login(
			@RequestParam(value = "email", defaultValue = "admin@test.com", required = true) String email,
			@RequestParam(value = "password", defaultValue = "***", required = true) String password) {
		LoginResponseDTO loginResponse = userService.logIn(email, password);
		return new ResponseEntity<>(loginResponse, loginResponse.getResponse().getStatus());
	}
}
