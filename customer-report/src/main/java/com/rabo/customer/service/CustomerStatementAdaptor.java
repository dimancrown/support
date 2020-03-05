package com.rabo.customer.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.rabo.customer.model.wrapper.CustomerStatementWrapper;
import com.rabo.customer.models.CustomerStatement;

@Service
public class CustomerStatementAdaptor {
	
	Logger logger = Logger.getLogger(getClass().getName());

	public CustomerStatementWrapper processCustomerStatement(List<CustomerStatement> customerStatement) {
		CustomerStatementWrapper wrapper = null;
		logger.info("Customer statement processed successfully");
		return wrapper;
	}

}
