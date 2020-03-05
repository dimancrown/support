package com.rabo.customer.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabo.customer.model.wrapper.CustomerStatementWrapper;

@Service
public class CustomerStatementService {
	
	@Autowired
	CustomerStatementAdaptor customerAdaptor;
	
	Logger logger = Logger.getLogger(getClass().getName());

	public CustomerStatementWrapper processCustomerStatement(CustomerStatementWrapper wrapper)
	
	{
		/**
		 * To do  customer statement process
		 */

		logger.info("Customer statement started processing");
		return customerAdaptor.processCustomerStatement(wrapper.getCustomerStatement());
	}

}
