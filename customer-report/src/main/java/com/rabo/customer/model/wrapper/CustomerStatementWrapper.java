package com.rabo.customer.model.wrapper;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.validator.inf.DuplicateFinder;
public class CustomerStatementWrapper {

	@Valid
	@NotNull
	@DuplicateFinder
	private List<CustomerStatement> customerStatement;

	public List<CustomerStatement> getCustomerStatement() {
		return customerStatement;
	}

	public void setCustomerStatement(List<CustomerStatement> customerStatement) {
		this.customerStatement = customerStatement;
	}

		
}
