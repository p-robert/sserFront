package com.userfront.domain;

public enum TokenState {
	TOKEN_INVALID("invalidToken"), TOKEN_EXPIRED("expired"), TOKEN_VALID("valid");

	private String state;

	private TokenState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

}