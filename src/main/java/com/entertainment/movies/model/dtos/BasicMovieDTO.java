package com.entertainment.movies.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class BasicMovieDTO {
	@NonNull
	private String name;
	@NonNull
	private String gender;
	@NonNull
	private Integer rate;
}
