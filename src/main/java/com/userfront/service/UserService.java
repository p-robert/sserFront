package com.userfront.service;

import java.util.Set;

import com.userfront.domain.User;
import com.userfront.domain.VerificationToken;
import com.userfront.domain.security.UserRole;
import com.userfront.domain.TokenState;

public interface UserService {

	
	 void saveUser(User user);
	 User findByUsername(String username);
	 User findByEmail(String email);
	 boolean checkUserExists(String username, String email) ;
	 boolean checkUsernameExists(String username);
	 VerificationToken getVerificationToken(final String verificationToken);
	 boolean checkEmailExists(String email);
	 User createUser(User user, Set<UserRole> userRoles);
	 void createVerificationTokenForUser(User user, String token);
	 VerificationToken generateNewVerificationToken(final String existingVerificationToken);
	 TokenState validateVerificationToken(String token);
	 User getUser(final String token);
}
