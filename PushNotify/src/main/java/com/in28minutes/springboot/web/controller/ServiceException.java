package com.in28minutes.springboot.web.controller;

public class ServiceException extends Exception {
	String s;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ServiceException(String s, String string, String string2){
		super(s,null,false,false);
	}
}
