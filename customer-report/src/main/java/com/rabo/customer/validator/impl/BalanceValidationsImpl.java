package com.rabo.customer.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.validator.inf.BalanceValidations;

	
	public class BalanceValidationsImpl implements ConstraintValidator<BalanceValidations,  CustomerStatement>{

		@Override
		public boolean isValid(CustomerStatement value, ConstraintValidatorContext context) {
			return value.getStartBalance().add(value.getMutation()).equals(value.getEndBalance());
		}

}
