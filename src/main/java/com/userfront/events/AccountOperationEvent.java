package com.userfront.events;

import org.springframework.context.ApplicationEvent;

import com.userfront.domain.Email;

public class AccountOperationEvent extends ApplicationEvent {

	private Email email;
	private static final long serialVersionUID = 8315108061942834346L;

	public AccountOperationEvent(Email email) {
		super(email);
		this.email = email;
	}

	public Email getEmail() {
		return email;
	}

	
	
}
