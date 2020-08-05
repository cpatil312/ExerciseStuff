package com.ordermgt.exception;

public class InvalidExclusionItems extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidExclusionItems(String msg) {
		super(msg);
	}
}
