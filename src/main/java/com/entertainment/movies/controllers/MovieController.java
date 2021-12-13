package com.entertainment.movies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entertainment.movies.model.dtos.BasicMovieDTO;
import com.entertainment.movies.model.dtos.GeneralResponse;
import com.entertainment.movies.model.dtos.MovieDto;
import com.entertainment.movies.services.IMovieService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("movie")
@Api(value = "MovieController", description = "REST APIs related to movie Entity!!!!")
public class MovieController {
	
	@Autowired
	private IMovieService movieService;
	
	@GetMapping(value = "")
	@ApiOperation(value = "getAll the informatioN about movies.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "bad request!!!"),
			@ApiResponse(code = 201, message = "created!!!"),
			@ApiResponse(code = 302, message = "found!!!"),
			@ApiResponse(code = 404, message = "not found!!!"),
			@ApiResponse(code = 500, message = "Internal server error") })
    public ResponseEntity<Page<MovieDto>> getAll(@RequestHeader("Authorization") String token,
    		@RequestParam(value = "Page number", defaultValue = "0", required = true) Integer pageNumber,
    		@RequestParam(value = "Rows per page", defaultValue = "1", required = true) Integer rowsNumber) {
        return  new ResponseEntity<>(movieService.lookupAll(pageNumber, rowsNumber),HttpStatus.OK);
    }
	
	@PostMapping(value = "update")
	@ApiOperation(value = "update information for one movie.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "bad request!!!"),
			@ApiResponse(code = 404, message = "not found!!!"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<GeneralResponse> updateMovie(@RequestHeader("Authorization") String token,
			@RequestBody(required = true) MovieDto movie){
		GeneralResponse response = movieService.updateMovie(movie, token);
		return new ResponseEntity<>(response,response.getStatus());
	}
	
	@PostMapping(value = "create")
	@ApiOperation(value = "Service to add more movies to the app.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "bad request!!!"),
			@ApiResponse(code = 201, message = "created!!!"),
			@ApiResponse(code = 302, message = "found!!!"),
			@ApiResponse(code = 500, message = "Internal server error") })
    public ResponseEntity<GeneralResponse> createMovie(@RequestHeader("Authorization") String token,
			@RequestBody(required = true) BasicMovieDTO movie){
		GeneralResponse response = movieService.createMovie(movie, token);
		return new ResponseEntity<>(response,response.getStatus());
    }
	
}
