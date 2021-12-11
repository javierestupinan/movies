package com.entertainment.movies.utils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.entertainment.movies.model.dtos.GeneralResponse;
import com.entertainment.movies.model.dtos.Response;
import com.entertainment.movies.repository.entities.User;
import com.entertainment.movies.utils.Constants.JwtVariables;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UtilValidation {
	
	public static GeneralResponse validateEmail(String email) {
		final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		boolean emailValidation = Pattern.compile(regexPattern)
	      .matcher(email)
	      .matches();
		return emailValidation ? Response.ok(null, email) : Response.badRequest("The email is malformed.", email);
	}
	
	public static GeneralResponse validatePassword(String password, String user) {
		if(password.length() < 10) {
			return Response.badRequest("The password isn't long enough, It must have 10 characters at least.", user);
		}
		
		final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{10,20}$";

	    final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	    Matcher matcher = pattern.matcher(password);
        if (matcher.matches()) {
        	return Response.ok(null, user);
        }
        return Response.badRequest("The password does not have the format required.", user);
	}
	
	public static String obtainBcrypPassword(final String password) {
		int strength = 10;
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength);
		return bCryptPasswordEncoder.encode(password);
	}
	
	public static boolean matchPassword(final String password, final String seed) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		return b.matches(password, seed);
	}

	public static String generateJWT(String user, User userFound, Integer sessionMinutes) {
		String token = "";
		if(userFound != null) {
			String secretKey = JwtVariables.KEY;
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(userFound.getRol().getName());
			
			Claims subject = Jwts.claims().setId(JwtVariables.ID).setSubject(user);
			subject.put(JwtVariables.AUTHORITIES, grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
			subject.put(JwtVariables.ROL_ID, userFound.getRol().getRolId());
			
			token = Jwts.builder().setId(JwtVariables.ID).setSubject(user)
						.addClaims(subject)
						.setExpiration(new Date(System.currentTimeMillis() + 60000 * sessionMinutes))
						.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
		}

		return JwtVariables.BEARER + token;
	}
	
}
