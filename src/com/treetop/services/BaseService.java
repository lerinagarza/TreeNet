/*
 * Created on Aug 05, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class BaseService {
	
	protected static DataSource mDataSource;
	
	public BaseService(){
		
	}
	
	protected static Connection getDBConnection() throws SQLException {
		
		try {
            if (mDataSource == null) {
                  Context ctx = new InitialContext();
                  mDataSource = (DataSource) ctx.lookup("jdbc/treetopds");
            }

      } catch (Exception e) {
            System.out.println("Exception occurred initializing datasource: " + e);
      }
		
		return mDataSource.getConnection();
		
	}
	
	

}
