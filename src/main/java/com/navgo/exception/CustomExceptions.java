/**
 * 
 */
package com.navgo.exception;

/**
 * @author Akash Bais
 *
 */
public class CustomExceptions {

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidPassKeyException extends RuntimeException {
        public InvalidPassKeyException(String message) {
            super(message);
        }
    }
    
    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }
    

    public static class InvalidOTPException extends RuntimeException {
        public InvalidOTPException(String message) {
            super(message);
        }
    }
    
    public static class InvalidInputException extends RuntimeException {
        public InvalidInputException(String message) {
            super(message);
        }
    }
}
