package com.userfront.dao;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.userfront.domain.User;
import com.userfront.domain.VerificationToken;

public interface VerificationTokenDao extends CrudRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);
	
	VerificationToken findByUser(User user);
	
	Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);
	void deleteByExpiryDateLessThan(Date now);
	
	@Modifying
	@Query("delete from VerificationToken t where t.expiryDate <= ?1")
	void deleteAllExpiredSince(Date now);

}
