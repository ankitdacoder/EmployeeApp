package com.crudspringboot.employeeApp.exception;

public class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		
		
		super(message);
		System.out.println("called -----------------");
	}

}
