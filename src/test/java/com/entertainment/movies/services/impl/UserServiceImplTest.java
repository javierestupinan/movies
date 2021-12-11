package com.entertainment.movies.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.entertainment.movies.model.dtos.GeneralResponse;
import com.entertainment.movies.model.dtos.LoginResponseDTO;
import com.entertainment.movies.model.dtos.UserRequestDTO;
import com.entertainment.movies.model.enums.ERol;
import com.entertainment.movies.repository.IUserRepository;
import com.entertainment.movies.repository.entities.Rol;
import com.entertainment.movies.repository.entities.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserServiceImplTest {
	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private IUserRepository userDB;
	
	private static List<User> userFound = new ArrayList<>();
	private static Rol rol = null;
	private static UserRequestDTO userDTO = null;

	@BeforeAll
	static void setup() {
		rol = new Rol(2, "BASIC_USER");
		userFound.add(new User(2, rol, null, "validEmail@test.com", "$2a$10$pV7d8uaTuaWgnL66Sayph.vIYsew1RRHjTiNFEXleDhQ5kpxgmkXe"));
		// log.info("@BeforeAll - executes once before all test methods in this class");
	}

	@BeforeEach
	void setUpBefore(TestInfo info) {
		// log.info("@BeforeEach - executed before each test method");
		log.info(info.getDisplayName() + "   -------------------------");
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() {
		//log.info("@AfterEach - executed after each test method.");
	}

	@AfterAll
	static void done() {
		//log.info("@AfterAll - executed after all test methods.");
	}
	
	@Test
	@DisplayName("logIn() missingParameterEmail")
	void logIn_missingParameterEmail_returnBadRequest() {
		LoginResponseDTO response = userService.logIn("", "password");
		assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("logIn() missingParameterPassword")
	void logIn_missingParameterPassword_returnBadRequest() {
		LoginResponseDTO response = userService.logIn("novalidEmail@", "");
		assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("logIn() emailNotValid")
	void logIn_emailWithError_returnBadRequest() {
		LoginResponseDTO response = userService.logIn("novalidEmail@", "password");
		assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("logIn() passwordLengthLessThan10")
	void logIn_passwordLengthLessThan10_returnBadRequest() {
		LoginResponseDTO response = userService.logIn("validEmail@test.com", "password");
		assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("logIn() passwordWithOutSpecialCaracter")
	void logIn_passwordWithOutSpecialCaracter_returnBadRequest() {
		LoginResponseDTO response = userService.logIn("validEmail@test.com", "Password55");
		assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("logIn() passwordWithOutUpperCase")
	void logIn_passwordWithOutUpperCase_returnBadRequest() {
		LoginResponseDTO response = userService.logIn("validEmail@test.com", "password55!");
		assertEquals(response.getResponse().getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("logIn() emailDonotExist")
	void logIn_emailDonotExist_returnNotFound() {
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenReturn(new ArrayList<User>());
		userService.setSessionMinutes(10);
		LoginResponseDTO response = userService.logIn("noExist@test.com", "Password55!");
		assertEquals(response.getResponse().getStatus(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("logIn() passwordDotNotMatch")
	void logIn_passwordDotNotMatch_returnUnAuthorized() {
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenReturn(userFound);
		userService.setSessionMinutes(10);
		LoginResponseDTO response = userService.logIn("validEmail@test.com", "Password55!3");
		assertEquals(response.getResponse().getStatus(), HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	@DisplayName("logIn() gotAnExceptionQueringDB")
	void logIn_gotAnExceptionQueringDB_returnInternalServerError() {
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenThrow(new RuntimeException());
		userService.setSessionMinutes(10);
		LoginResponseDTO response = userService.logIn("validEmail@test.com", "Password55!");
		assertEquals(response.getResponse().getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@DisplayName("logIn() everythingOK")
	void logIn_everythingOK_returnOK() {
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenReturn(userFound);
		userService.setSessionMinutes(10);
		LoginResponseDTO response = userService.logIn("validEmail@test.com", "Password55!");
		assertEquals(response.getResponse().getStatus(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("signUp() emailNotValid")
	void signUp_emailNotValid_returnBadRequest() {
		userDTO = new UserRequestDTO("validEmail@","Password55!",ERol.BASIC_USER);
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenReturn(userFound);
		GeneralResponse response = userService.signUp(userDTO);
		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("signUp() passwordLengthLessThan10")
	void signUp_passwordLengthLessThan10_returnBadRequest() {
		userDTO = new UserRequestDTO("validEmail@test.com","Pard55!",ERol.BASIC_USER);
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenReturn(userFound);
		GeneralResponse response = userService.signUp(userDTO);
		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("signUp() userAlreadyExist")
	void signUp_userAlreadyExist_returnFound() {
		userDTO = new UserRequestDTO("validEmail@test.com","Password55!",ERol.BASIC_USER);
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenReturn(userFound);
		GeneralResponse response = userService.signUp(userDTO);
		assertEquals(response.getStatus(), HttpStatus.FOUND);
	}
	
	@Test
	@DisplayName("signUp() problemException")
	void signUp_problemException_returnInternalServerError() {
		userDTO = new UserRequestDTO("validEmail@test.com","Password55!",ERol.BASIC_USER);
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenThrow(new RuntimeException());
		GeneralResponse response = userService.signUp(userDTO);
		assertEquals(response.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@DisplayName("signUp() everythingOK")
	void signUp_everythingOK_returnCreated() {
		userDTO = new UserRequestDTO("newEmail@test.com","Password55!",ERol.BASIC_USER);
		Mockito.when(userDB.findByEmail(Mockito.anyString())).thenReturn(new ArrayList<User>());
		GeneralResponse response = userService.signUp(userDTO);
		assertEquals(response.getStatus(), HttpStatus.CREATED);
	}
	
	
}
