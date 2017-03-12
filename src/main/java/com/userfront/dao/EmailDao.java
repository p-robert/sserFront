package com.userfront.dao;

import org.springframework.data.repository.CrudRepository;

import com.userfront.domain.Email;
import com.userfront.domain.User;

public interface EmailDao extends CrudRepository<Email,Long> {

	
}
