package com.entertainment.movies.utils;

public final class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class");
	}
	
	public static final class GeneralStrings {
		private GeneralStrings() {}
		public static final String EMPTY = "";
	}
	
	public static final class JwtVariables {
		private JwtVariables() {}
		public static final String BEARER = "Bearer ";
		public static final String ID = "MoviesAPP";
		public static final String ROL_ID ="rol_id";
		public static final String KEY = "MoviesApp2021*";
		public static final String AUTHORITIES = "authorities";
	}

}
