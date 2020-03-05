package com.rabo.customer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.customer.model.wrapper.CustomerStatementWrapper;
import com.rabo.customer.response.CustomerResult;
import com.rabo.customer.response.CustomerStatementResponse;
import com.rabo.customer.response.ErrorRecord;
import com.rabo.customer.service.CustomerStatementService;

@RestController
@RequestMapping("/rabo/customer-report/")
public class CustomerStatementController {

	@Autowired
	CustomerStatementService customerService;

	@PostMapping("/process")
	public CustomerStatementResponse proessCustomerReport(@Valid @RequestBody CustomerStatementWrapper customerWrapper) {
		CustomerStatementResponse response = null;
		List<ErrorRecord> errorRecords = new ArrayList<>();
		customerService.processCustomerStatement(customerWrapper);
		response = new CustomerStatementResponse(CustomerResult.SUCCESSFUL.name(), errorRecords);
		return response;
	}

}
