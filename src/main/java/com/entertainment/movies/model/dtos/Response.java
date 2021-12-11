package com.entertainment.movies.model.dtos;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Response {
	
	private static boolean logActivated = true;

	public static GeneralResponse ok(String message, Object importantData) {
		if (logActivated && StringUtils.hasText(message)) {
			log.info("OK: \"{}\" | object: \"{}\"", message, importantData.toString());
		}
		return new GeneralResponse(message);
	}

	public static GeneralResponse badRequest(String message, Object importantData) {
		if (logActivated) {
			log.info("BAD_REQUEST: \"{}\" | object: \"{}\"", message, importantData.toString());
		}
		return new GeneralResponse(message, HttpStatus.BAD_REQUEST);
	}

	public static GeneralResponse unauthorized(String message, Object importantData) {
		if (logActivated) {
			log.info("UNAUTHORIZED: \"{}\" | object: \"{}\"", message, importantData.toString());
		}
		return new GeneralResponse(message, HttpStatus.UNAUTHORIZED);
	}

	public static GeneralResponse notFound(String message, Object importantData) {
		if (logActivated) {
			log.info("NOT_FOUND: \"{}\" | object: \"{}\"", message, importantData.toString());
		}
		return new GeneralResponse(message, HttpStatus.NOT_FOUND);
	}

	public static GeneralResponse internalServerError(String message, Object importantData) {
		if (logActivated) {
			log.error("INTERNAL_SERVER_ERROR: \"{}\" | object: \"{}\"", message, importantData.toString());
		}
		return new GeneralResponse(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static GeneralResponse created(String message, Object importantData) {
		if (logActivated) {
			log.error("CREATED: \"{}\" | object: \"{}\"", message, importantData.toString());
		}
		return new GeneralResponse(message, HttpStatus.CREATED);
	}
	
	public static GeneralResponse found(String message, Object importantData) {
		if (logActivated) {
			log.error("FOUND: \"{}\" | object: \"{}\"", message, importantData.toString());
		}
		return new GeneralResponse(message, HttpStatus.FOUND);
	}

	public static void setLogActivated(boolean logActivated) {
		Response.logActivated = logActivated;
	}

}
