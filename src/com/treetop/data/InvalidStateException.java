package com.treetop.data;

/**
 * Insert the type's description here.
 * Creation date: (7/24/2002 3:43:27 PM)
 * @author: 
 */
public class InvalidStateException extends Exception {
/**
 * InvalidStateException constructor comment.
 */
public InvalidStateException() {
	super();
}
/**
 * InvalidStateException constructor comment.
 */
public InvalidStateException(String stateCode) {
	super("Invalid state code: " + stateCode);
}
}
