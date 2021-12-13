package com.entertainment.movies.model.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MovieDto extends BasicMovieDTO {
	@NonNull
	private Integer id;
	
	public MovieDto(Integer id, String name, String gender, Integer rate){
		super(name,gender,rate);
		this.id = id;
	}
}
