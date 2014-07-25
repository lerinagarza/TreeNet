package com.treetop;

/**
 * Insert the type's description here.
 * Creation date: (12/17/2003 1:07:26 PM)
 * @author: 
 */
public class Labels 
{
	private String    labelInformation;
	private Integer   templateNumber;
	private Integer   labelNumber;
/**
 * Labels constructor comment.
 */
public Labels() 
{

}
/**
 * Label Information -  A String which may be a table in HTML
 *                   will print onto the label.
 * Creation date: (12/17/2003 1:15:04 PM)
 */
public String getLabelInformation() 
{
	return labelInformation;	
}
/**
 * Label Information -  A String which may be a table in HTML
 *                   will print onto the label.
 * Creation date: (12/17/2003 1:15:04 PM)
 */
public Integer getLabelNumber() 
{
	return labelNumber;	
}
/**
 * Template Number (Which Template to Display -- 
 *                  Templates are based on label stock)
 * Creation date: (12/17/2003 1:15:04 PM)
 */
public Integer getTemplateNumber() 
{
	return templateNumber;	
}
/**
 * Label Information -  A String which may be a table in HTML
 *                   will print onto the label.
 * Creation date: (12/17/2003 1:15:04 PM)
 */
public void setLabelInformation(String inLabelInformation) 
{
	this.labelInformation = inLabelInformation;
}
/**
 * Label Number (Which Label to Start With)
 *
 * Creation date: (12/17/2003 1:15:04 PM)
 */
public void setLabelNumber(Integer inLabelNumber) 
{
	this.labelNumber = inLabelNumber;
}
/**
 * Template Number (Which Template to Display -- 
 *                  Templates are based on label stock)
 * Creation date: (12/17/2003 1:15:04 PM)
 */
public void setTemplateNumber(Integer inTemplateNumber) 
{
	this.templateNumber = inTemplateNumber;
}
}
