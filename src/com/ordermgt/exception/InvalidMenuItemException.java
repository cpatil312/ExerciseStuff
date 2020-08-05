package com.ordermgt.exception;

public class InvalidMenuItemException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidMenuItemException(String msg) {
		super(msg);
	}
}
