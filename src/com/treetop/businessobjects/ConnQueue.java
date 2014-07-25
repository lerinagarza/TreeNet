package com.treetop.businessobjects;

import java.util.LinkedList;

import com.treetop.Security;
import com.treetop.services.*;

public class ConnQueue extends LinkedList {
	
	protected int 					initialSize;
	protected int 					maxSize;
	protected int 					totalCreated; 
	protected String				user;
	protected String				password;
	
	public ConnQueue(String authorization, int initConnections, int maxConnections){
		this.setUser(Security.getProfile(authorization));
		this.setPassword(Security.getPassword(authorization));
		this.setInitialSize(initConnections);
		this.setMaxSize(maxConnections);
		
		this.setTotalCreated(0);
		
	}
	
	public ConnQueue(String user, String password, int initConnections, int maxConnections){
		this.setUser(user);
		this.setPassword(password);
		this.setInitialSize(initConnections);
		this.setMaxSize(maxConnections);
		
		this.setTotalCreated(0);
		
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public int getTotalCreated() {
		return totalCreated;
	}
	public void setTotalCreated(int connectionCount) {
		this.totalCreated = connectionCount;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxConnections) {
		this.maxSize = maxConnections;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initConnections) {
		this.initialSize = initConnections;
	}

}
