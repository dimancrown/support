package com.rabo.customer.validator.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.validator.inf.DuplicateFinder;
/**
 * Custom validator for finding Duplicate record exist
 * @author diman
 *
 */
public class DuplicateFinderImpl implements ConstraintValidator<DuplicateFinder,  List<CustomerStatement>>{

	/**
	 * Finding duplicates in list.
	 * 1. Grouping the list by txn Reference.
	 * 2.Iterate the records which is grouped.
	 * 3. which ever the grouped records having entry greater than 1 then there is a duplicate
	 * 4. return false.
	 */
	@Override
	public boolean isValid(List<CustomerStatement> value, ConstraintValidatorContext context) {
		return !(value.stream().collect(Collectors.groupingBy(CustomerStatement::getTxnReference)).entrySet().stream().anyMatch(e -> e.getValue().size()>1));                                    
	}

	

}
