package com.rabo.customer.test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.customer.CustomerStatementApplication;
import com.rabo.customer.controller.CustomerStatementController;
import com.rabo.customer.model.wrapper.CustomerStatementWrapper;
import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.response.CustomerResult;
import com.rabo.customer.service.CustomerStatementService;

@SpringBootTest(classes={ CustomerStatementApplication.class })
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerStatementControllerUnitTest {

	private MockMvc mockMvc;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Mock
	CustomerStatementService customerStatement;

	@InjectMocks
	CustomerStatementController customerStatementController;

	@BeforeAll
	public void init() {
	    this.mockMvc = webAppContextSetup(webApplicationContext).build();

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
	public void process_customer_statement_throw_bad_request_exception() throws Exception {
		expectedException.expect(Exception.class);
		this.mockMvc
				.perform(post("/rabo/customer-report/process").header("Origin", "*"))
				.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void process_customer_statement() throws Exception {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		ObjectMapper mapper = new ObjectMapper();
		this.mockMvc
				.perform(post("/rabo/customer-report/process").content(mapper.writeValueAsString(wrapper))
						.header("Origin", "*")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				 .andExpect(jsonPath("$.result", is(CustomerResult.SUCCESSFUL.name()))).andReturn();
	}
	
	@Test
	public void process_customer_statement_duplicate() throws Exception {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		wrapper.getCustomerStatement().get(1).setTxnReference(new BigDecimal(194261));
		ObjectMapper mapper = new ObjectMapper();
		this.mockMvc
				.perform(post("/rabo/customer-report/process").content(mapper.writeValueAsString(wrapper))
						.header("Origin", "*")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				 .andExpect(jsonPath("$.result", is(CustomerResult.DUPLICATE_REFERENCE.name()))).andReturn();
	}
	
	@Test
	public void process_customer_statement_incorrect_balance() throws Exception {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		wrapper.getCustomerStatement().get(0).setEndBalance((new BigDecimal(11)));
		ObjectMapper mapper = new ObjectMapper();
		this.mockMvc
				.perform(post("/rabo/customer-report/process").content(mapper.writeValueAsString(wrapper))
						.header("Origin", "*")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				 .andExpect(jsonPath("$.result", is(CustomerResult.INCORRECT_END_BALANCE.name()))).andReturn();
	}
	
	@Test
	public void process_customer_statement_incorrect_balance_and_duplicate() throws Exception {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		wrapper.getCustomerStatement().get(0).setEndBalance((new BigDecimal(11)));
		wrapper.getCustomerStatement().get(1).setTxnReference(new BigDecimal(194261));
		ObjectMapper mapper = new ObjectMapper();
		this.mockMvc
				.perform(post("/rabo/customer-report/process").content(mapper.writeValueAsString(wrapper))
						.header("Origin", "*")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				 .andExpect(jsonPath("$.result", is(CustomerResult.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.name()))).andReturn();
	}
}
