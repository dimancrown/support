package com.rabo.customer.models;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rabo.customer.validator.inf.BalanceValidations;
@BalanceValidations
public class CustomerStatement {
	@NotNull
	private BigDecimal txnReference;
	@NotBlank
	private String acountNumber;
	@NotNull
	private BigDecimal startBalance;
	@NotNull
	private BigDecimal mutation;
	
	private String description;
	@NotNull
	private BigDecimal endBalance;
	
	public BigDecimal getTxnReference() {
		return txnReference;
	}
	public void setTxnReference(BigDecimal txnReference) {
		this.txnReference = txnReference;
	}
	public String getAcountNumber() {
		return acountNumber;
	}
	public void setAcountNumber(String acountNumber) {
		this.acountNumber = acountNumber;
	}
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}
	public BigDecimal getMutation() {
		return mutation;
	}
	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}
	
	
	

}
