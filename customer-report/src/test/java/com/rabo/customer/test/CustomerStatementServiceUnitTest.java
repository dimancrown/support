package com.rabo.customer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rabo.customer.model.wrapper.CustomerStatementWrapper;
import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.service.CustomerStatementAdaptor;
import com.rabo.customer.service.CustomerStatementService;

@TestInstance(Lifecycle.PER_CLASS)
public class CustomerStatementServiceUnitTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	CustomerStatementService customerStatement;

	@Mock
	CustomerStatementAdaptor customerAdaptor;

	@BeforeAll
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	private CustomerStatementWrapper getPreLoadedCustomerStatement() {
		CustomerStatementWrapper wrapper = new CustomerStatementWrapper();
		List<CustomerStatement> customerStatementList = new ArrayList<>();

		CustomerStatement custRecordOne = new CustomerStatement();
		custRecordOne.setAcountNumber("NL91RABO0315273637");
		custRecordOne.setTxnReference(BigDecimal.valueOf(194261));
		custRecordOne.setDescription("Clothes from Jan Bakker");
		custRecordOne.setStartBalance(BigDecimal.valueOf(21.6));
		custRecordOne.setMutation(BigDecimal.valueOf(-41.83));
		custRecordOne.setEndBalance(BigDecimal.valueOf(-20.23));

		CustomerStatement custRecordTwo = new CustomerStatement();
		custRecordTwo.setAcountNumber("NL91RABO0315273637");
		custRecordTwo.setTxnReference(BigDecimal.valueOf(194262));
		custRecordTwo.setDescription("Clothes from Jan Bakker");
		custRecordTwo.setStartBalance(BigDecimal.valueOf(21.6));
		custRecordTwo.setMutation(BigDecimal.valueOf(-41.83));
		custRecordTwo.setEndBalance(BigDecimal.valueOf(-20.23));

		customerStatementList.add(custRecordOne);
		customerStatementList.add(custRecordTwo);
		wrapper.setCustomerStatement(customerStatementList);
		return wrapper;
	}

	
	
	@Test
	public void process_customer_statement_throw_exceptions() {
		expectedException.expect(Exception.class);
		customerStatement.processCustomerStatement(null);
	}

	@Test
	public void process_customer_statement() {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		when(customerAdaptor.processCustomerStatement(wrapper.getCustomerStatement())).thenReturn(wrapper);
		CustomerStatementWrapper response =  customerStatement.processCustomerStatement(wrapper);
		//assertThat(response.getCustomerStatement(), hasSize(2));
	}
	
}
