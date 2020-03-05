package com.rabo.customer.response;

import java.util.List;

public class CustomerStatementResponse {
	
	private String result;
	private List<ErrorRecord> errorRecords;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<ErrorRecord> getErrorRecords() {
		return errorRecords;
	}
	public void setErrorRecords(List<ErrorRecord> errorRecords) {
		this.errorRecords = errorRecords;
	}
	public CustomerStatementResponse(String result, List<ErrorRecord> errorRecords) {
		super();
		this.result = result;
		this.errorRecords = errorRecords;
	}
	public CustomerStatementResponse() {
		super();
	}
	
	
	
	

}
