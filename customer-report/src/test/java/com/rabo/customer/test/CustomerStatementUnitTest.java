package com.rabo.customer.test;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.rabo.customer.model.wrapper.CustomerStatementWrapper;
import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.response.CustomerResult;
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerStatementUnitTest {

	private Validator validator;

	@BeforeAll
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
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
	public void give_customer_statement_success() {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		Set<ConstraintViolation<CustomerStatementWrapper>> violations = validator.validate(wrapper);
		List<String> violationMsg = violations.stream().map(s -> s.getMessage()).collect(Collectors.toList());
		assertThat(violationMsg.isEmpty(), is(true));
	}

	@Test
	public void give_customer_statement_duplicate() {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		wrapper.getCustomerStatement().get(1).setTxnReference(new BigDecimal(194261));
		Set<ConstraintViolation<CustomerStatementWrapper>> violations = validator.validate(wrapper);
		List<String> violationMsg = violations.stream().map(s -> s.getMessage()).collect(Collectors.toList());
		assertThat(violationMsg, hasItems(CustomerResult.DUPLICATE_REFERENCE.name()));
	}

	@Test
	public void give_customer_statement_invalidBalance() {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		wrapper.getCustomerStatement().get(0).setEndBalance((new BigDecimal(11)));
		Set<ConstraintViolation<CustomerStatementWrapper>> violations = validator.validate(wrapper);
		List<String> violationMsg = violations.stream().map(s -> s.getMessage()).collect(Collectors.toList());
		assertThat(violationMsg, hasItems(CustomerResult.INCORRECT_END_BALANCE.name()));
	}

	@Test
	public void give_customer_statement_invalidBalance_duplicate_reference() {
		CustomerStatementWrapper wrapper = getPreLoadedCustomerStatement();
		wrapper.getCustomerStatement().get(1).setTxnReference(new BigDecimal(194261));
		wrapper.getCustomerStatement().get(1).setEndBalance((new BigDecimal(11)));
		Set<ConstraintViolation<CustomerStatementWrapper>> violations = validator.validate(wrapper);
		List<String> violationMsg = violations.stream().map(s -> s.getMessage()).collect(Collectors.toList());
		assertThat(violationMsg, hasItems(CustomerResult.DUPLICATE_REFERENCE.name()));
		assertThat(violationMsg, hasItems(CustomerResult.INCORRECT_END_BALANCE.name()));
	}

}
