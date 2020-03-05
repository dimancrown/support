package com.rabo.customer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.rabo.customer.CustomerStatementApplication;
import com.rabo.customer.model.wrapper.CustomerStatementWrapper;
import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.response.CustomerResult;
import com.rabo.customer.response.CustomerStatementResponse;

@SpringBootTest(classes = CustomerStatementApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerStatementIntegrationTest {
	
	@LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
    
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
    public void customer_statement_success() {
    	CustomerStatementWrapper customerWrapper = getPreLoadedCustomerStatement();
        ResponseEntity<CustomerStatementResponse> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/rabo/customer-report/process", customerWrapper, CustomerStatementResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(CustomerResult.SUCCESSFUL.name(), responseEntity.getBody().getResult());
    }
    
    @Test
    public void customer_statement_duplicate() {
    	CustomerStatementWrapper customerWrapper = getPreLoadedCustomerStatement();
    	customerWrapper.getCustomerStatement().get(1).setTxnReference(new BigDecimal(194261));
        ResponseEntity<CustomerStatementResponse> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/rabo/customer-report/process", customerWrapper, CustomerStatementResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(CustomerResult.DUPLICATE_REFERENCE.name(), responseEntity.getBody().getResult());
    }
    
    @Test
    public void customer_statement_invalid_balance() {
    	CustomerStatementWrapper customerWrapper = getPreLoadedCustomerStatement();
    	customerWrapper.getCustomerStatement().get(0).setEndBalance((new BigDecimal(11)));
        ResponseEntity<CustomerStatementResponse> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/rabo/customer-report/process", customerWrapper, CustomerStatementResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(CustomerResult.INCORRECT_END_BALANCE.name(),responseEntity.getBody().getResult());
    }
    
    @Test
    public void customer_statement_invalid_balance_and_duplicate() {
    	CustomerStatementWrapper customerWrapper = getPreLoadedCustomerStatement();
    	customerWrapper.getCustomerStatement().get(0).setEndBalance((new BigDecimal(11)));
    	customerWrapper.getCustomerStatement().get(1).setTxnReference(new BigDecimal(194261));
        ResponseEntity<CustomerStatementResponse> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/rabo/customer-report/process", customerWrapper, CustomerStatementResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(CustomerResult.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.name(), responseEntity.getBody().getResult());
    }
    
    @Test
    public void customer_statement_internal_error_exception()
    {
    	
    }

}
