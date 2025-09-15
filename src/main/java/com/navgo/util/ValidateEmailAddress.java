/**
 * 
 */
package com.navgo.util;

/**
 * @author Akash Bais
 *
 */
import java.util.regex.*;

import org.springframework.stereotype.Component;

@Component
public class ValidateEmailAddress {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		public static boolean validate(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		        return matcher.matches();
		}
}
