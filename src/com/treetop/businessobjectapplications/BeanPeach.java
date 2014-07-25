/*
 * Created on May 9, 2013
 *
 */
package com.treetop.businessobjectapplications;

import java.util.Vector;

import com.treetop.businessobjects.PeachTicket;

/**
 * @author deisen
 *
 * Bean to combine processing results for sending to the servlet. (Peach Day)
 */

public class BeanPeach {
	
	private	String				environment    = new String();
	private	PeachTicket 		ticket         = new PeachTicket();
	private Vector<PeachTicket> ticketList	   = new Vector<PeachTicket>();	
	
	

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public PeachTicket getTicket() {
		return ticket;
	}

	public void setTicket(PeachTicket ticket) {
		this.ticket = ticket;
	}

	public Vector<PeachTicket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(Vector<PeachTicket> ticketList) {
		this.ticketList = ticketList;
	}	

}
