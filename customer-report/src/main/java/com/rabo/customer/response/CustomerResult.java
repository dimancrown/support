package com.rabo.customer.response;
/**
 * 
 * @author diman
 * This enum will group the customer statement response result
 */
public enum CustomerResult {
	SUCCESSFUL,DUPLICATE_REFERENCE,INCORRECT_END_BALANCE, 
	DUPLICATE_REFERENCE_INCORRECT_END_BALANCE,BAD_REQUEST,INTERNAL_SERVER_ERROR
}
