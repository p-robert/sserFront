package com.userfront.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class EmailSavings extends Email{

	@OneToOne
	@JoinColumn(name = "savings_transaction_id")
	private SavingsTransaction savingsTransaction;

	public SavingsTransaction getSavingsTransaction() {
		return savingsTransaction;
	}

	public void setSavingsTransaction(SavingsTransaction savingsTransaction) {
		this.savingsTransaction = savingsTransaction;
	}
	
	
}
