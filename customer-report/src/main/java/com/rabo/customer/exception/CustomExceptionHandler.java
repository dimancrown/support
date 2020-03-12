package com.rabo.customer.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rabo.customer.models.CustomerStatement;
import com.rabo.customer.response.CustomerResult;
import com.rabo.customer.response.CustomerStatementResponse;
import com.rabo.customer.response.ErrorRecord;

/**
 * Custome Exception class which will capture all the exception
 * @author diman
 *
 */
@RestControllerAdvice
public class CustomExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * This custom exception handler for catch the validation failures.
	 * DUPLICATE_REFERENCE,INCORRECT_END_BALANCE,DUPLICATE_REFERENCE_INCORRECT_END_BALANCE
	 * @param ex
	 * @return CustomerStatementResponse
	 */
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomerStatementResponse> handleException(MethodArgumentNotValidException ex) {
		logger.error(ex.getMessage(), ex.getCause());
		CustomerStatementResponse respone = new CustomerStatementResponse();
		StringBuilder builder = new StringBuilder();
		List<ErrorRecord> errors = new ArrayList<>();
		/**
		 * Forming error response for validation..
		 * Based on the requirement custom response message formed.
		 */
		ex.getBindingResult().getAllErrors().forEach(error -> {
			if(((FieldError) error).getRejectedValue() instanceof CustomerStatement)
			{
				CustomerStatement statement = (CustomerStatement) ((FieldError) error).getRejectedValue();
				errors.add(bindErrorMessages(statement));
				builder.append(error.getDefaultMessage());
			}
			else if(((FieldError) error).getRejectedValue() instanceof List<?>)
			{
				List<CustomerStatement> errorInputs = (List<CustomerStatement>) ((FieldError) error).getRejectedValue();
				List<CustomerStatement> duplicateRecords = errorInputs.stream().collect(Collectors.groupingBy(CustomerStatement::getTxnReference)).entrySet().stream().filter(e -> e.getValue().size()>1)
						.flatMap(c->c.getValue().stream()).collect(Collectors.toList());
				errors.addAll(duplicateRecords.stream().map(new CustomExceptionHandler()::bindErrorMessages).collect(Collectors.toList()));
				builder.append(error.getDefaultMessage());
			}
			respone.setErrorRecords(errors);
		});
		
		if(builder.toString().contains(CustomerResult.DUPLICATE_REFERENCE.name())&& builder.toString().contains(CustomerResult.INCORRECT_END_BALANCE.name()))
		{
			respone.setResult(CustomerResult.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.name());
		}
		else
		{
			respone.setResult(builder.toString());
		}
		
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	
	/**
	 * The list of error records formed.
	 * @param custStatement
	 * @return ErrorRecord
	 */
	private ErrorRecord bindErrorMessages(CustomerStatement custStatement)
	{
		ErrorRecord errorRecord = new ErrorRecord();
		errorRecord.setReference(custStatement.getTxnReference().toString());
		errorRecord.setAccountNumber(custStatement.getAcountNumber());
		return errorRecord;
	}
	
	/**
	 * In case of exception in process customer statement
	 * this exception handler will be initiated
	 * @param ex
	 * @return CustomerStatementResponse
	 */
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomerStatementResponse> handleException(Exception ex) {
		logger.error(ex.getMessage(), ex.getCause());
		CustomerStatementResponse response = new CustomerStatementResponse();
		response.setResult(CustomerResult.INTERNAL_SERVER_ERROR.name());
		response.setErrorRecords(new ArrayList<ErrorRecord>());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * if  bad requet passed from upstream then this handler will be initiated.
     * @param ex
     * @return CustomerStatementResponse
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomerStatementResponse> handleException(HttpMessageNotReadableException  ex) {
		logger.error(ex.getMessage(), ex.getCause());
		CustomerStatementResponse response = new CustomerStatementResponse();
		response.setResult(CustomerResult.BAD_REQUEST.name());
		response.setErrorRecords(new ArrayList<ErrorRecord>());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
