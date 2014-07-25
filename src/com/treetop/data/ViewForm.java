package com.treetop.data;

import java.util.*;
import java.math.*;
import java.sql.*;
import java.io.*;
/**
 * Form system view.
 * Creation date: (8/13/2003 4:16:42 PM)
 * @author: 
 */
public class ViewForm {
	

	// Data base fields.
	
	private String title;
	private String extraOptions; 
	private String javascriptHead;
	private String javascriptFoot;
	private String section;
	private int    image;
 
/**
 * Form view constructor.
 */
public ViewForm() {
	
	super();
}
/**
 * Form view for extra options.
 * Creation date: (8/13/2003 2:28:15 PM)
 */
public String getExtraOptions() {
	
	return this.extraOptions;
}
/**
 * Form view for image count.
 * Creation date: (8/13/2003 2:28:15 PM)
 */
public int getImage() {
	
	return this.image;
}
/**
 * Form view for javascript (footer).
 * Creation date: (8/13/2003 2:28:15 PM)
 */
public String getJavascriptFoot() {
	
	return this.javascriptFoot;
}
/**
 * Form view for javascript (header).
 * Creation date: (8/13/2003 2:28:15 PM)
 */
public String getJavascriptHead() {
	
	return this.javascriptHead;
}
/**
 * Form view for expanding sections.
 * Creation date: (8/13/2003 2:28:15 PM)
 */
public String getSection() {
	
	return this.section;
}
/**
 * Form view for page titles.
 * Creation date: (8/13/2003 2:28:15 PM)
 */
public String getTitle() {
	
	return this.title;
}
/**
 * Update form view for extra options.
 * Creation date: (8/13/2003 9:09:11 AM)
 */
public void setExtraOptions(String inExtraOptions) 
{
	this.extraOptions = inExtraOptions;
}
/**
 * Update form view for image count.
 * Creation date: (8/13/2003 9:09:11 AM)
 */
public void setImage(int inImage) 
{
	this.image = inImage;
}
/**
 * Update form view for javascript footer.
 * Creation date: (8/13/2003 9:09:11 AM)
 */
public void setJavascriptFoot(String inJavascriptFoot) 
{
	this.javascriptFoot = inJavascriptFoot;
}
/**
 * Update form view for javascript header.
 * Creation date: (8/13/2003 9:09:11 AM)
 */
public void setJavascriptHead(String inJavascriptHead) 
{
	this.javascriptHead = inJavascriptHead;
}
/**
 * Update form view for expanding section.
 * Creation date: (8/13/2003 9:09:11 AM)
 */
public void setSection(String inSection) 
{
	this.section = inSection;
}
/**
 * Update form view for page titles.
 * Creation date: (8/13/2003 9:09:11 AM)
 */
public void setTitle(String inTitle) 
{
	this.title = inTitle;
}
}
