package com;

@SuppressWarnings("serial")
public class MutexNotFound extends Exception {
	public MutexNotFound() { 
		super("MutexNotFound");
	}
	
	public MutexNotFound(Throwable cause) {
		super(cause);
	}
}

