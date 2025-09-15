//package com.navgo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity.BodyBuilder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.navgo.model.AppUser;
//import com.navgo.service.AppUserService;
//import com.navgo.service.AuthService;
//import com.navgo.util.PasswordEncryptorDecryptor;
//import com.navgo.exception.CustomExceptions.EmailAlreadyExistsException;
//import com.navgo.exception.CustomExceptions.InvalidPassKeyException;
//
///**
// * Author: Akash Bais
// */
//@RestController
//@RequestMapping("/users")
//public class AppUserController {
//
//	private final String adminPasskey = "hb14kf54ul49";
//
//	@Autowired
//	private AuthService authService;
//
//	@Autowired
//	private AppUserService appUserService;
//
//	@Autowired
//	private PasswordEncryptorDecryptor encryptorDecryptor;
//
//	@PostMapping("/signup")
//	public ResponseEntity signup(@RequestBody AppUser appUser) {
//		try {
//			if ("STUDENT".equals(appUser.getRole())) {
//
//				String encryptedPassword = encryptorDecryptor.encryptPassword(appUser.getPassword());
//				appUser.setPassword(encryptedPassword);
//
//				ResponseEntity<String> result = appUserService.addUser(appUser);
//
//				if ("User created successfully".equals(result.getBody())) {
//					return new ResponseEntity(HttpStatus.CREATED);
//
//				} else {
//					return new ResponseEntity(HttpStatus.BAD_REQUEST);
//				}
//			} else {
//				String providedPassKey = appUser.getPassKey().trim();
//				if (!adminPasskey.equals(providedPassKey)) {
//					throw new InvalidPassKeyException("Invalid passkey provided.");
//				}
//
//				String encryptedPassword = encryptorDecryptor.encryptPassword(appUser.getPassword());
//				appUser.setPassword(encryptedPassword);
//
//				ResponseEntity<String> result = appUserService.addUser(appUser);
//
//				if ("User created successfully".equals(result.getBody())) {
//					return new ResponseEntity(HttpStatus.CREATED);
//				} else {
//					return new ResponseEntity(HttpStatus.BAD_REQUEST);
//				}
//			}
//		} catch (EmailAlreadyExistsException e) {
//			// This should be handled by the global exception handler
//			throw e;
//		} catch (InvalidPassKeyException e) {
//			// This should be handled by the global exception handler
//			throw e;
//		} catch (Exception e) {
//			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//
//		}
//	}
//
//	@PostMapping("/validatePassKey")
//	public ResponseEntity<Boolean> validateAdminPassKey(@RequestParam String passKey) {
//		boolean isValid = adminPasskey.equals(passKey);
//		return ResponseEntity.ok(isValid);
//	}
//}