package com.rabo.customer.test;


import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rabo.customer.service.CustomerStatementService;

@TestInstance(Lifecycle.PER_CLASS)
public class CustomerStatementServiceUnitTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	CustomerStatementService customerStatement;

	@BeforeAll
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void process_customer_statement_throw_exceptions() {
		expectedException.expect(Exception.class);
		customerStatement.processCustomerStatement(null);
	}

}
