package com.rabo.customer.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.rabo.customer.model.wrapper.CustomerStatementWrapper;
/**
 *  The customer statement service will navigate the process
 * @author diman
 *
 */
@Service
public class CustomerStatementService {
	
	Logger logger = Logger.getLogger(getClass().getName());
	/**
	 * This method will process the List of Json customer statement
	 * validation part already done in controller itself.
	 * @param wrapper
	 * @return CustomerStatementWrapper
	 */

	public CustomerStatementWrapper processCustomerStatement(CustomerStatementWrapper wrapper)
	
	{
		logger.info("Customer statement started processing");
		return wrapper;
	}

}
