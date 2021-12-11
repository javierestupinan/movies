package com.entertainment.movies.model.dtos;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public final class GeneralResponse {
	private String message;
	private HttpStatus status;
	
	 GeneralResponse() {
		this.message = "";
		this.status = HttpStatus.OK;
	}
	
	GeneralResponse(String message) {
		this();
		this.message = message;
	}
	
	GeneralResponse(String message, HttpStatus status) {
		this();
		this.message = message;
		this.status = status;
	}

	@Override
	public String toString() {
		return "response [status=" + status + ", message=" + message + " ]";
	}
	
	
	
}
