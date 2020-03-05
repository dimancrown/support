package com.rabo.customer.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.validator.inf.BalanceValidations;
/**
 *  Custom validator for Balance Validation.
 * @author diman
 *
 */
	
	public class BalanceValidationsImpl implements ConstraintValidator<BalanceValidations,  CustomerStatement>{
		
		/**
		 * The starting balance after mutation needs equals to end balance.
		 * purposefully added equals method instead of compareTo method.
		 * Since compareTo provides validation upto 2 decimals.
		 */
		@Override
		public boolean isValid(CustomerStatement value, ConstraintValidatorContext context) {
			return value.getStartBalance().add(value.getMutation()).equals(value.getEndBalance());
		}

}
