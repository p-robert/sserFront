package com.userfront.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.userfront.domain.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent{

	
	private final String appUrl;
	private final Locale locale;
	private final User user;
	
	public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl) {
		super(user);
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	private static final long serialVersionUID = 1490472249664298618L;

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public User getUser() {
		return user;
	}

	
}
