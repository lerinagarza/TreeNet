/*
 * Created on December 29, 2011 
 */
package com.treetop.businessobjects;

import java.util.*;

import javax.mail.internet.InternetAddress;

import com.treetop.utilities.html.*;

public class CommunicationMessage {
	
	// from File 	dbprd/xxxxxxxx
	//              dbprd/xxxxxxxx
	
	private	String					number					= "";	//xxxxxx
	
	private	Vector<CommunicationContact>	to				= new Vector<CommunicationContact>();
	private String					listTo					= "";
	private String					owner					= "";
	private	CommunicationContact	from					= new CommunicationContact();	//
	private String          		subject          		= "";   //
	
	private	String					body					= "";	//
	
	private	String					headerImage				= "";	//
	private String					headerText				= "";   //
	private	String					footerAndBorderColor	= "";	//
	
	private String					backgroundImage			= "";  //
	private String					backgroundColor			= "";  //
	
	private Vector<HtmlInput>		footerLinks				= new Vector<HtmlInput>();
	
	private	String					lastSavedDate			= "";	//
	private	String					lastSavedTime			= "";	//
	private	String					lastSavedUser			= "";	//
	
	
	/**
	 * Semi-colon separated list of email display names
	 */
	private void updateListTo() {
		StringBuffer listTo = new StringBuffer();
		Vector<CommunicationContact> to = this.getTo(); 
		
		//convert the vector of contact objects into an array of InternetAddress objects
		InternetAddress[] emails = new InternetAddress[to.size()];
		for (int i=0; i<to.size(); i++) {
			emails[i] = to.elementAt(i);
		}
		//use static method from InternetAddress to build list of email addresses with display names
		this.setListTo(InternetAddress.toString(emails));
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Vector<CommunicationContact> getTo() {
		return to;
	}
	public void setTo(Vector<CommunicationContact> to) {
		this.to = to;
		updateListTo();
	}
	public String getListTo() {
		return listTo;
	}
	public void setListTo(String listTo) {
		this.listTo = listTo;
	}
	public CommunicationContact getFrom() {
		return from;
	}
	public void setFrom(CommunicationContact from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getHeaderImage() {
		return headerImage;
	}
	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}
	public String getFooterAndBorderColor() {
		return footerAndBorderColor;
	}
	public void setFooterAndBorderColor(String footerAndBorderColor) {
		this.footerAndBorderColor = footerAndBorderColor;
	}
	public Vector<HtmlInput> getFooterLinks() {
		return footerLinks;
	}
	public void setFooterLinks(Vector<HtmlInput> footerLinks) {
		this.footerLinks = footerLinks;
	}
	public String getLastSavedDate() {
		return lastSavedDate;
	}
	public void setLastSavedDate(String lastSavedDate) {
		this.lastSavedDate = lastSavedDate;
	}
	public String getLastSavedTime() {
		return lastSavedTime;
	}
	public void setLastSavedTime(String lastSavedTime) {
		this.lastSavedTime = lastSavedTime;
	}
	public String getLastSavedUser() {
		return lastSavedUser;
	}
	public void setLastSavedUser(String lastSavedUser) {
		this.lastSavedUser = lastSavedUser;
	}
	public String getHeaderText() {
		return headerText;
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	
	
	
}
