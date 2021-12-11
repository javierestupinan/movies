package com.entertainment.movies.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERol {
	BASIC_USER(2, "BASIC_USER"),
	ADMIN(1, "ADMINISTRATOR");
	
	private Integer idRol;
	private String rol;

	public static String getRolName(Integer codRol){
	    for(ERol valor : values()){
	        if( valor.getIdRol().equals(codRol)){
	            return valor.getRol();
	        }
	    }
	    return ERol.BASIC_USER.getRol();
	}

}
