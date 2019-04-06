package org.litespring.beans.exception;

public class BeanCreationException extends BeanException {
	public BeanCreationException(String msg) {
		super(msg);
	}
	
	public BeanCreationException(String msg,Throwable cause) {
		super(msg,cause);
	}
}
