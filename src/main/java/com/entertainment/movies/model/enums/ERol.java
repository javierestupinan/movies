package com.entertainment.movies.model.enums;

import java.util.List;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERol {
	USER(Arrays.asList("2"), "USER"),
	ADMIN(Arrays.asList("1"), "ADMIN");
	
	private List<String> idRol;
	private String rol;

	public static String getRolName(String codOsiris){
	    for(ERol valor : values()){
	        if( valor.getIdRol().contains(codOsiris)){
	            return valor.getRol();
	        }
	    }
	    return ERol.USER.getRol();
	}

}
