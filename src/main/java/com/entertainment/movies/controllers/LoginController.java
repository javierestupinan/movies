package com.entertainment.movies.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("security")
@Api(value = "LoginController", description = "REST APIs related to User Entity!!!!")
public class LoginController {
	
	@GetMapping(value = "sign-in")
	@ApiOperation(value = "Authenticate the user on the application.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!!!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public String login(@RequestParam(value = "email", defaultValue = "admin@test.com", required = true) String email,
			@RequestParam(value = "password", defaultValue = "***", required = true) String password) {
		return String.format("Configuring your login with the email: ", email);
	}
}
