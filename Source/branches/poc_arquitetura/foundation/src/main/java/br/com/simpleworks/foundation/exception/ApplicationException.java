package br.com.simpleworks.foundation.exception;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ApplicationException(String bundleKey) {
		super(bundleKey);
	}

}
