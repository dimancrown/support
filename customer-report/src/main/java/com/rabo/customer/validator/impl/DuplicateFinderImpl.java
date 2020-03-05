package com.rabo.customer.validator.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.validator.inf.DuplicateFinder;

public class DuplicateFinderImpl implements ConstraintValidator<DuplicateFinder,  List<CustomerStatement>>{

	@Override
	public boolean isValid(List<CustomerStatement> value, ConstraintValidatorContext context) {
		return !(value.stream().collect(Collectors.groupingBy(CustomerStatement::getTxnReference)).entrySet().stream().anyMatch(e -> e.getValue().size()>1));                                    
	}

	

}
