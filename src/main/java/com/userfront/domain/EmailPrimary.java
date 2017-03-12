package com.userfront.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class EmailPrimary extends Email{

	
	@OneToOne
    @JoinColumn(name = "primary_transaction_id")
	private PrimaryTransaction primaryTransaction;

	public PrimaryTransaction getPrimaryTransaction() {
		return primaryTransaction;
	}

	public void setPrimaryTransaction(PrimaryTransaction primaryTransaction) {
		this.primaryTransaction = primaryTransaction;
	}
	
	
}
