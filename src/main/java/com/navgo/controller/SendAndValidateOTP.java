//package com.navgo.controller;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.navgo.model.AppUser;
//import com.navgo.service.AuthService;
//import com.navgo.service.EmailService;
//import com.navgo.util.OTPService;
//import com.navgo.util.ValidateEmailAddress;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@RestController
//public class SendAndValidateOTP {
//
//    private static final Logger logger = LoggerFactory.getLogger(SendAndValidateOTP.class);
//    
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private AppUserController appUserController;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private OTPService otpService;
//
//    private static final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();
//    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//    
//    @PostMapping("/send-otp")
//    public ResponseEntity<String> sendOtp(@RequestParam String email, @RequestParam String firstName) {
//        String providedEmail = email.trim();
//        logger.info("Received OTP request for email: {}", providedEmail);
//
//        try {
//        	if(!ValidateEmailAddress.validate(providedEmail)) {
//        		logger.warn("Provide valid email : {}", providedEmail);	
//        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format");
//        	}
//        	if (authService.authenticateEmail(providedEmail)) {
//                logger.warn("Attempt to send OTP to an existing account email: {}", providedEmail);
//                return ResponseEntity.status(HttpStatus.CONFLICT).body("Account exists with given email");
//            }
//            String otp = otpService.generateOTP();
//            otpStorage.put(providedEmail, otp.trim());
//            scheduler.schedule(() -> otpStorage.remove(providedEmail), 3, TimeUnit.MINUTES);
//
//            String emailContent = getFormattedEmailContent(otp, firstName);
//            emailService.sendEmail(providedEmail, "Check your email to sign up in Navgo", emailContent);
//
//            logger.info("OTP sent to email: {}", providedEmail);
//            return ResponseEntity.ok("OTP sent to your email");
//        } catch (Exception e) {
//            logger.error("Error sending OTP to email {}: {}", providedEmail, e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An unexpected error occurred while sending OTP. Please try again later.");
//        }
//    }
//
//    @PostMapping("/verify-otp-and-signup")
//    public ResponseEntity<String> verifyOtpAndSignup(@RequestParam String email, @RequestParam String otp, @RequestBody AppUser appUser) {
//        String providedEmail = email.trim();
//        logger.info("Received OTP verification request for email: {}", providedEmail);
//
//        String storedOtp = otpStorage.get(providedEmail);
//        if (storedOtp == null || !otp.equals(storedOtp)) {
//            logger.error("Invalid OTP for email: {}", providedEmail);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
//        }
//
//        logger.info("OTP Verified for email: {}", providedEmail);
//        otpStorage.remove(providedEmail);
//
//        try {
//            ResponseEntity<AppUser> resp = appUserController.signup(appUser);
//            if (resp.getStatusCode().is2xxSuccessful()) {
//                return ResponseEntity.status(HttpStatus.CREATED).body("Signup successful");
//            } else {
//                return ResponseEntity.status(resp.getStatusCode()).body("Signup failed");
//            }
//        } catch (Exception e) {
//            logger.error("Error during signup for email {}: {}", providedEmail, e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup error occurred");
//        }
//    }
//
//
//    private String getFormattedEmailContent(String otp, String name) {
//        return "<div style='font-family: Arial, sans-serif; font-size: 14px;'>" + 
//               "<h2>Your OTP Code</h2>" + 
//               "<p>Dear " + name + " ,</p>" + 
//               "<p>Thank you for signing up. Your OTP code is:</p>" + 
//               "<h3 style='color: blue;'>" + otp + "</h3>" + 
//               "<p>Please use this code to complete your signup process.</p>" + 
//               "<p>If you did not request this code, please ignore this email.</p>" + 
//               "<p>Best regards,</p>" + 
//               "<p>Navgo By Abhyudaya Coding club</p>" + 
//               "</div>";
//    }
//}
