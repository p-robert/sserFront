package com.userfront.service;

import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.userfront.dao.RoleDao;
import com.userfront.dao.UserDao;
import com.userfront.dao.VerificationTokenDao;
import com.userfront.domain.TokenState;
import com.userfront.domain.User;
import com.userfront.domain.VerificationToken;
import com.userfront.domain.security.UserRole;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private VerificationTokenDao tokenDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AccountService accountService;

	public void saveUser(User user) {
		userDao.save(user);
	}

	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userDao.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("User with username {} already exists. Nothing to be done. ", user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);

			for (UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);

			user.setPrimaryAccount(accountService.createPrimaryAccount());
			user.getPrimaryAccount().setUser(user);
			user.setSavingsAccount(accountService.createSavingsAccount());
			user.getSavingsAccount().setUser(user);
			localUser = userDao.save(user);
		}
		return localUser;
	}

	@Override
	public VerificationToken getVerificationToken(final String verificationToken) {
		return tokenDao.findByToken(verificationToken);
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public boolean checkUserExists(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(email)) {
			return true;
		}

		return false;
	}

	public boolean checkUsernameExists(String username) {
		if (null != findByUsername(username)) {
			return true;
		}
		return false;
	}

	public boolean checkEmailExists(String email) {
		if (null != findByEmail(email)) {
			return true;
		}
		return false;
	}

	@Override
	public void createVerificationTokenForUser(final User user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		tokenDao.save(myToken);
	}

	 @Override
	    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
	        VerificationToken vToken = tokenDao.findByToken(existingVerificationToken);
	        vToken.updateToken(UUID.randomUUID().toString());
	        vToken = tokenDao.save(vToken);
	        return vToken;
	    }
	 
	 @Override
	    public TokenState validateVerificationToken(String token) {
	        final VerificationToken verificationToken = tokenDao.findByToken(token);
	        if (verificationToken == null) {
	            return TokenState.TOKEN_INVALID;
	        }

	        final User user = verificationToken.getUser();
	        final Calendar cal = Calendar.getInstance();
	        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        	tokenDao.delete(verificationToken);
	            return TokenState.TOKEN_EXPIRED;
	        }

	        user.setEnabled(true);
	      //  tokenDao.delete(verificationToken);
	        userDao.save(user);
	        return TokenState.TOKEN_VALID;
	    }
	 
	 public User getUser(final String token){
		 VerificationToken toVerificationToken = tokenDao.findByToken(token);
		 if(toVerificationToken != null){
			 return toVerificationToken.getUser();
		 }
		 return null;
		 
	 }
	 
}
