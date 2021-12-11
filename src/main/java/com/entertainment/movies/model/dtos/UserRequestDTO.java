package com.entertainment.movies.model.dtos;

import com.entertainment.movies.model.enums.ERol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString(exclude = { "password" })
@EqualsAndHashCode(of = { "email" })
@NoArgsConstructor
public class UserRequestDTO {
	private String email;
	private String password;
	private ERol rol;
}
