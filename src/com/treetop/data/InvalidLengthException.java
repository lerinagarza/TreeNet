package com.treetop.data;

/**
 * Insert the type's description here.
 * Creation date: (7/24/2002 4:32:38 PM)
 * @author: 
 */
public class InvalidLengthException extends Exception {
/**
 * InvalidLengthException constructor comment.
 */
public InvalidLengthException() {
	super();
}
/**
 * InvalidLengthException constructor comment.
 * @param s java.lang.String
 */
public InvalidLengthException(String s) {
	super(s);
}
	public InvalidLengthException(String fieldName,
		                          int given,
		                          int allowed) {
			                          
       super("Entry length for " + fieldName + " of " +
	          given +
	         " is greater than the maximum allowed of " +
	          allowed + ".");
       
	}
}
