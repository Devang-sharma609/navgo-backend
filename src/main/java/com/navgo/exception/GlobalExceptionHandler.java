/**
 * 
 */
package com.navgo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.navgo.exception.CustomExceptions.InvalidOTPException;

/**
 * @author Akash Bais
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomExceptions.EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(CustomExceptions.EmailAlreadyExistsException ex) {
        return new ResponseEntity<>("An account with this email already exists.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomExceptions.InvalidPassKeyException.class)
    public ResponseEntity<String> handleInvalidPassKeyException(CustomExceptions.InvalidPassKeyException ex) {
        return new ResponseEntity<>("Invalid passkey provided.", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(CustomExceptions.InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(CustomExceptions.InvalidPasswordException ex) {
        return new ResponseEntity<>("Invalid password provided.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptions.InvalidOTPException.class)
    public ResponseEntity<String> handleInvalidOTPException(CustomExceptions.InvalidOTPException ex) {
        return new ResponseEntity<>("Invalid OTP provided.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}