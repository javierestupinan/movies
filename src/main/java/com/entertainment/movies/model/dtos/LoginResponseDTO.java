package com.entertainment.movies.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
	private GeneralResponse response;
	private String token;
}
