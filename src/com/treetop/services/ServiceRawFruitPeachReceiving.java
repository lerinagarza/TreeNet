package com.treetop.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.treetop.businessobjects.*;
import com.treetop.controller.sublot.*;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.html.DropDownSingle;


/**
 * @author deisen.
 *
 * Service class to obtain and return data 
 * for user business objects for Raw Fruit Peach Receiving.
 * 
 */

public class ServiceRawFruitPeachReceiving {
	
	private static class BuildSQL {
		
		/**
		 * @author deisen.
		 * SQL to insert peach receiving tickets (header lot/item).
		 */
			
		private static String insertPeachTicketHeader(UpdSubLotHeader requestBean) 
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();			
		
			try {		
		
				if ((!requestBean.getEnvironment().trim().equals("")) &&
					(!requestBean.getCompany().trim().equals(""))) 		
				{					
					String TTlibrary = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());	
					
					if ((!requestBean.getSupplierNumber().trim().equals("")) &&	
						(!requestBean.getLoadNumber().trim().equals("")) &&
						(!requestBean.getLotNumber().trim().equals("")) &&
						(!requestBean.getItemNumber().trim().equals(""))) 
					{				
						sqlString.append("INSERT INTO " + TTlibrary + ".RFPAPRECH ");
						sqlString.append("VALUES(");
						sqlString.append("'" + requestBean.getSupplierNumber().trim() + "',");							
						sqlString.append("'" + requestBean.getLoadNumber().trim() + "',");
						sqlString.append("'" + requestBean.getLotNumber().trim() + "',");
						sqlString.append("'" + requestBean.getItemNumber().trim() + "',");
						sqlString.append("'" + requestBean.getReceivingUser().trim() + "',");
						sqlString.append("'" + requestBean.getReceivingUser().trim() + "',");
						sqlString.append(requestBean.getReceivingDate().trim() + ",");
						sqlString.append(requestBean.getReceivingTime().trim() + ",");
						sqlString.append("'" + requestBean.getReceivingUser().trim() + "',");
						sqlString.append(requestBean.getReceivingDate().trim() + ",");
						sqlString.append(requestBean.getReceivingTime().trim());		
						sqlString.append(")");					
									
					}
				}
					
			} catch (Exception e) 
			{		
				throwError.append("Build SQL statement failed. ");
				throwError.append("insertPeachTicketHeader. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
				throwError.append("BuildSQL.insertPeachTicketHeader(UpdSubLotHeader)");
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // insertPeachTicketHeader

		/**
		 * @author deisen.
		 * SQL to insert peach receiving tickets (detail tags).
		 */
			
		private static String insertPeachTicketDetail(UpdSubLotHeader requestBean) 
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();			
			
			try {		
		
				if ((!requestBean.getEnvironment().trim().equals("")) &&
					(!requestBean.getCompany().trim().equals(""))) 		
				{					
					String TTlibrary = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());	
					
					if ((!requestBean.getSupplierNumber().trim().equals("")) &&	
						(!requestBean.getLoadNumber().trim().equals("")) &&
						(!requestBean.getLotNumber().trim().equals("")) &&
						(!requestBean.getItemNumber().trim().equals(""))) 
					{						
						UpdSubLotDetail thisTag = (UpdSubLotDetail) requestBean.getListLotUnits().elementAt(0);
						
						sqlString.append("INSERT INTO " + TTlibrary + ".RFPBPRECD ");
						sqlString.append("VALUES(");							
						sqlString.append("'" + thisTag.getLotNumber().trim() + "',");
						sqlString.append("'" + thisTag.getItemNumber().trim() + "',");
						sqlString.append(thisTag.getUnitNumber().trim() + ",");
						sqlString.append("'" + thisTag.getGrowerCode().trim() + "'");
						sqlString.append(")");									
					}
				}
					
			} catch (Exception e) 
			{		
				throwError.append("Build SQL statement failed. ");
				throwError.append("insertPeachTicketDetail. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
				throwError.append("BuildSQL.insertPeachTicketDetail(UpdSubLotHeader)");
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // insertPeachTicketDetail

		/**
		 * @author deisen.
		 * SQL to delete peach receiving tickets (header lot/item).
		 */
			
		private static String deletePeachTicketHeader(UpdSubLotHeader requestBean) 
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();			
		
			try {		
		
				if ((!requestBean.getEnvironment().trim().equals("")) &&
					(!requestBean.getCompany().trim().equals(""))) 		
				{					
					String TTlibrary = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());	
					
					if ((!requestBean.getLotNumber().trim().equals("")) &&						
						(!requestBean.getItemNumber().trim().equals(""))) 
					{				
						sqlString.append("DELETE FROM " + TTlibrary + ".RFPAPRECH ");
						sqlString.append("WHERE");						
						sqlString.append(" RFALOT = '" + requestBean.getLotNumber().trim() + "'");
						sqlString.append(" AND");
						sqlString.append(" RFAITEM = '" + requestBean.getItemNumber().trim() + "'");				
									
					}
				}
					
			} catch (Exception e) 
			{		
				throwError.append("Build SQL statement failed. ");
				throwError.append("deletePeachTicketHeader. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
				throwError.append("BuildSQL.deletePeachTicketHeader(UpdSubLotHeader)");
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // deletePeachTicketHeader

		/**
		 * @author deisen.
		 * SQL to delete peach receiving tickets (detail tags).
		 */
			
		private static String deletePeachTicketDetail(UpdSubLotHeader requestBean) 
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();			
		
			try {		
		
				if ((!requestBean.getEnvironment().trim().equals("")) &&
					(!requestBean.getCompany().trim().equals(""))) 		
				{					
					String TTlibrary = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());	
					
					if ((!requestBean.getLotNumber().trim().equals("")) &&						
						(!requestBean.getItemNumber().trim().equals(""))) 
					{				
						sqlString.append("DELETE FROM " + TTlibrary + ".RFPBPRECD ");
						sqlString.append("WHERE");						
						sqlString.append(" RFBLOT = '" + requestBean.getLotNumber().trim() + "'");
						sqlString.append(" AND");
						sqlString.append(" RFBITEM = '" + requestBean.getItemNumber().trim() + "'");				
									
					}
				}
					
			} catch (Exception e) 
			{		
				throwError.append("Build SQL statement failed. ");
				throwError.append("deletePeachTicketDetail. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
				throwError.append("BuildSQL.deletePeachTicketDetail(UpdSubLotHeader)");
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // deletePeachTicketDetail

		/**
		 * @author deisen.
		 * SQL to retrieve the ticket header.
		 */
			
		private static String findPeachTicketHeader(DtlSubLot requestBean) 
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {		
		
				if ((!requestBean.getEnvironment().trim().equals("")) &&
					(!requestBean.getCompany().trim().equals(""))) 		
				{
					String TTlibrary = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());
					String library = GeneralUtility.getLibrary(requestBean.getEnvironment().trim());	
					
					if ((!requestBean.getLotNumber().trim().equals("")) &&						
						(!requestBean.getItemNumber().trim().equals(""))) 
					{				
						sqlString.append(" SELECT RFASUNO, RFALOAD, RFALOT, RFAITEM, RFADEC,");
						sqlString.append(" RFACUSER, RFACDATE, RFACTIME, RFAUUSER, RFAUDATE, RFAUTIME,");						
						sqlString.append(" IFNULL(IDSUNM,' ') as IDNAME, IFNULL(MMITDS,' ') as MMDESC");	
						
						sqlString.append(" FROM " + TTlibrary + ".RFPAPRECH");
						
						sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS ON");
						sqlString.append(" IDCONO = '" + requestBean.getCompany().trim() + "' AND");
						sqlString.append(" IDSUNO = RFASUNO");
						sqlString.append(" LEFT OUTER JOIN " + library + ".MITMAS ON");
						sqlString.append(" MMCONO = '" + requestBean.getCompany().trim() + "' AND");
						sqlString.append(" MMITNO = RFAITEM");
					
						sqlString.append(" WHERE");					
						sqlString.append(" RFALOT = '" + requestBean.getLotNumber().trim() + "'");
						sqlString.append(" AND");
						sqlString.append(" RFAITEM = '" + requestBean.getItemNumber().trim() + "'");
					}
					
				}
				
			} catch (Exception e) 
			{		
				throwError.append("Build SQL statement failed. ");
				throwError.append("findPeachTicketHeader. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
				throwError.append("BuildSQL.findPeachTicketHeader(DtlSubLot)");
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
			
		} // findPeachTicketHeader
		
		
		/**
		 * @author deisen.
		 * SQL to retrieve the ticket detail.
		 */
			
		private static String findPeachTicketDetail(DtlSubLot requestBean) 
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {		
		
				if ((!requestBean.getEnvironment().trim().equals("")) &&
					(!requestBean.getCompany().trim().equals(""))) 		
				{
					String TTlibrary = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());	
					
					if ((!requestBean.getLotNumber().trim().equals("")) &&						
						(!requestBean.getItemNumber().trim().equals(""))) 
					{				
						sqlString.append(" SELECT RFBLOT, RFBITEM, RFBTAG, RFBGRWR");
						
						sqlString.append(" FROM " + TTlibrary + ".RFPBPRECD");
					
						sqlString.append(" WHERE");					
						sqlString.append(" RFBLOT = '" + requestBean.getLotNumber().trim() + "'");
						sqlString.append(" AND");
						sqlString.append(" RFBITEM = '" + requestBean.getItemNumber().trim() + "'");
						
						sqlString.append(" ORDER BY");
						sqlString.append(" RFBLOT, RFBITEM, RFBTAG");
					}
					
				}
				
			} catch (Exception e) 
			{		
				throwError.append("Build SQL statement failed. ");
				throwError.append("findPeachTicketDetail. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
				throwError.append("BuildSQL.findPeachTicketDetail(DtlSubLot)");
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
			
		} // findPeachTicketDetail

		/**
		 * @author deisen.
		 * SQL to retrieve a list of peach tickets.
		 */
			
		private static String listPeachTicket(InqSubLot requestBean) 
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {		
		
				if ((!requestBean.getEnvironment().trim().equals("")) &&
					(!requestBean.getCompany().trim().equals(""))) 		
				{
					String TTlibrary = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());
					String library = GeneralUtility.getLibrary(requestBean.getEnvironment().trim());
					
					sqlString.append(" SELECT RFASUNO, RFALOAD, RFALOT, RFAITEM, RFADEC,");
					sqlString.append(" RFACUSER, RFACDATE, RFACTIME, RFAUUSER, RFAUDATE, RFAUTIME,");
					sqlString.append(" IFNULL(IDSUNM,' ') as IDNAME, IFNULL(MMITDS,' ') as MMDESC");
					
					if (requestBean.getGrowerName().equals(""))					
						sqlString.append(" FROM " + TTlibrary + ".RFPAPRECH");
										
					else {
					
						sqlString.append(" FROM (");
						sqlString.append(" SELECT RFBGRWR, RFBLOT, RFBITEM");
						sqlString.append(" FROM " + TTlibrary + ".RFPBPRECD");
						sqlString.append(" WHERE RFBGRWR = '" + requestBean.getGrowerName().trim() + "'");
						sqlString.append(" GROUP BY RFBGRWR, RFBLOT, RFBITEM) AS dt");
						
						sqlString.append(" JOIN " + TTlibrary + ".RFPAPRECH ON");
						sqlString.append(" RFALOT = RFBLOT AND RFAITEM = RFBITEM");
					}
					
					sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS ON");
					sqlString.append(" IDCONO = '" + requestBean.getCompany().trim() + "' AND");
					sqlString.append(" IDSUNO = RFASUNO");
					sqlString.append(" LEFT OUTER JOIN " + library + ".MITMAS ON");
					sqlString.append(" MMCONO = '" + requestBean.getCompany().trim() + "' AND");
					sqlString.append(" MMITNO = RFAITEM");
					
					String function = new String();
					function = " WHERE";
					
					if (!requestBean.getLotNumber().equals(""))
					{
						sqlString.append(function);
						sqlString.append(" RFALOT = '" + requestBean.getLotNumber().trim() + "'");
						function = " AND";
					}
					if (!requestBean.getItemNumber().equals(""))
					{
						sqlString.append(function);
						sqlString.append(" RFAITEM = '" + requestBean.getItemNumber().trim() + "'");
						function = " AND";
					}
					if ((!requestBean.getFromDate().equals("")) &&
						(!requestBean.getToDate().equals("")))
					{
						sqlString.append(function);
						sqlString.append(" RFACDATE BETWEEN " + requestBean.getFromDate().trim());
						sqlString.append(" AND " + requestBean.getToDate().trim());
						function = " AND";
					}					
					if (!requestBean.getSupplierNumber().equals(""))
					{
						sqlString.append(function);
						sqlString.append(" RFASUNO = '" + requestBean.getSupplierNumber().trim() + "'");
						function = " AND";
					}
					if (!requestBean.getLoadNumber().equals(""))
					{
						sqlString.append(function);
						sqlString.append(" RFALOAD = '" + requestBean.getLoadNumber().trim() + "'");						
					}
					
					sqlString.append(" ORDER BY RFALOT, RFAITEM");
					
				}				
				
			} catch (Exception e) 
			{		
				throwError.append("Build SQL statement failed. ");
				throwError.append("listPeachTicket. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
				throwError.append("BuildSQL.listPeachTicket(InqSubLot)");
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
			
		} // listPeachTicket

		private static String dropDownSinglePeachItems(String env) throws Exception {
			StringBuffer throwError = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			
			try {
			
				String library = GeneralUtility.getLibrary(env);
				
				//Method Stub
				sql.append("SELECT MPITNO, MMITDS ");
				sql.append("FROM " + library + ".MITPOP ");
				sql.append("INNER JOIN " + library + ".MITMAS ");
				sql.append("ON MPCONO = MMCONO AND MPITNO = MMITNO ");
				sql.append("WHERE MPCONO = 100 ");
				sql.append("AND MPALWT = 3 " );
				sql.append("AND MPALWQ = 'PEAC' ");
				sql.append("ORDER BY MPITNO ");
			
			} catch (Exception e) {
				throwError.append(e);
			} finally {
				if (!throwError.toString().equals("")) {
					throwError.append("dropDownSinglePeachItems(" + env + ")");
					throw new Exception (throwError.toString());
				}
			}
			
			return sql.toString();
		}
		
	}

	/**
	 * @author deisen.
	 * Load fields from SQL results.
	 */
	private static class LoadFields {
		
	/**
	 * @author deisen.
	 * Load fields for one peach ticket (detail).
	 */
			
	private static TicketDetail peachDetail(ResultSet rs) 
	throws Exception
	{		
		StringBuffer throwError = new StringBuffer();
		TicketDetail ticketTag  = new TicketDetail();

		try {	
			
			ticketTag.setLotNumber(rs.getString("RFBLOT").trim());
			ticketTag.setItemNumber(rs.getString("RFBITEM").trim());
			ticketTag.setTagNumber(rs.getString("RFBTAG").trim());
			ticketTag.setGrowerName(rs.getString("RFBGRWR").trim());
			
		} catch (Exception e) 
		{		
			throwError.append("Load fields failed. ");
			throwError.append("peachDetail. " + e);
		}
			
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
			throwError.append("LoadFields.peachDetail(ResultSet)");
			
			throw new Exception(throwError.toString());
		}
			
		return ticketTag;
		
	} // peachDetail

	/**
	 * @author deisen.
	 * Load fields for one peach ticket (header).
	 */
			
	private static PeachTicket peachHeader(ResultSet rs) 
	throws Exception
	{		
		StringBuffer throwError = new StringBuffer();
		PeachTicket peachTicket = new PeachTicket();
	
		try {
	
			peachTicket.setSupplierNumber(rs.getString("RFASUNO").trim());
			peachTicket.setSupplierName(rs.getString("IDNAME").trim());
			peachTicket.setLoadNumber(rs.getString("RFALOAD").trim());
			peachTicket.setLotNumber(rs.getString("RFALOT").trim());
			peachTicket.setItemNumber(rs.getString("RFAITEM").trim());
			peachTicket.setItemDescription(rs.getString("MMDESC").trim());
			peachTicket.setReceivingUser(rs.getString("RFADEC").trim());
			peachTicket.setCreateUser(rs.getString("RFACUSER").trim());
			peachTicket.setCreateDate(rs.getString("RFACDATE").trim());
			peachTicket.setCreateTime(rs.getString("RFACTIME").trim());
			peachTicket.setUpdateUser(rs.getString("RFAUUSER").trim());
			peachTicket.setUpdateDate(rs.getString("RFAUDATE").trim());
			peachTicket.setUpdateTime(rs.getString("RFAUTIME").trim());
			
			String shortName = peachTicket.getSupplierName().substring(25);
			peachTicket.setSupplierName(shortName);
			
		} catch (Exception e) 
		{		
			throwError.append("Load fields failed. ");
			throwError.append("peachHeader. " + e);
		}
			
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append("Error at com.treetop.services.ServiceRawFruitPeachReceiving.");
			throwError.append("LoadFields.peachHeader(ResultSet)");
			
			throw new Exception(throwError.toString());
		}
			
		return peachTicket;
		
	} // peachHeader
	
	}

	/**
	 * @author deisen.
	 * main, used for debuging.
	 */
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		int	 activeTest  = 1;
		
		try {
		
			// testing insert of peach ticket.
			
			if (activeTest == 0)  
			{
				UpdSubLotHeader bean = new UpdSubLotHeader();
				bean.setEnvironment("PRD");
				bean.setSupplierNumber(" S and P ");
				bean.setLoadNumber(" 787 ");
				bean.setLotNumber(" 4588 ");
				bean.setItemNumber(" 750000601 ");
				bean.setReceivingUser(" PeachPit ");
				bean.setReceivingDate(" 20130823 ");
				bean.setReceivingTime(" 154217 ");
		
				Vector<UpdSubLotDetail> tagList = new Vector<UpdSubLotDetail>();
				UpdSubLotDetail beanTag = new UpdSubLotDetail();
				beanTag.setEnvironment(bean.getEnvironment());
				beanTag.setLotNumber(bean.getLotNumber());
				beanTag.setItemNumber(bean.getItemNumber());
				beanTag.setUnitNumber(" 72 ");
				beanTag.setGrowerCode(" Farmer Peach ");
				tagList.addElement(beanTag);
				bean.setListLotUnits(tagList);

				insertPeachTicket(bean);
				String breakPoint = "stopHere";
			}
			
			// testing delete of peach ticket.
			
			if (activeTest == 0)  
			{
				UpdSubLotHeader bean = new UpdSubLotHeader();
				bean.setEnvironment("PRD");				
				bean.setLotNumber(" peaches  ");
				bean.setItemNumber(" 750000553  ");
				
				deletePeachTicket(bean);
				String breakPoint = "stopHere";
			}
			
			// testing detail page processing.
			
			if (activeTest == 0)  
			{
				DtlSubLot bean = new DtlSubLot();
				bean.setEnvironment("PRD");				
				bean.setLotNumber(" peaches  ");
				bean.setItemNumber(" 75000061  ");
				
				findPeachTicket(bean);
				String breakPoint = "stopHere";
			}
			
			// testing inquiry list page.
			
			if (activeTest == 1)  
			{
				InqSubLot bean = new InqSubLot();
				bean.setEnvironment("PRD");	
//				bean.setLotNumber(" abc123 ");
				bean.setItemNumber(" 75000553 ");
//				bean.setGrowerName(" Death Valley  ");	
//				bean.setLoadNumber(" 666  ");
//				bean.setSupplierNumber(" 10065  ");
//				bean.setFromDate(" 20130810  ");
//				bean.setToDate(" 20130823  ");
				
				listPeachTicket(bean);
				String breakPoint = "stopHere";
			}

		} catch (Exception e) 
		{
				System.out.println(e);	
		}
		
	}

	/**
	 * @author deisen.
	 * List the peach tickets (header) for the search page.
	 */
	
	public static void listPeachTicket(InqSubLot requestBean)
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   conn       = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15(); 
			listPeachTicket(requestBean, conn);
			
		} catch (Exception e)
		{
			throwError.append("List peach ticket setup process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (conn != null)						
			        ServiceConnection.returnConnectionStack15(conn);			
				
			} catch(Exception e)
			{
				throwError.append("Connection failed return. " + e);
			}
		}	
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruitPeachReceiving.");
			throwError.append("listPeachTicket");
			throwError.append("(InqSubLot). ");		
			
			throw new Exception(throwError.toString());
		}
		
		return;
	}

	/**
	 * @author deisen.
	 * Insert the peach ticket (header and detail)
	 */
	
	private static void insertPeachTicket(UpdSubLotHeader requestBean, Connection conn)             
	throws Exception
	{
		StringBuffer throwError  = new StringBuffer();
		String       sql         = new String();
		Statement    insertIt    = null;	
		
		try {
			
	 		try { 			

				sql = BuildSQL.insertPeachTicketHeader(requestBean);	
	 			insertIt = conn.createStatement();
	 			insertIt.executeUpdate(sql);  			
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement header. " + e);
	 		 }
	 		 
	 		try {

	 			Vector<UpdSubLotDetail> peachTags = new Vector<UpdSubLotDetail>();
	 			peachTags = requestBean.getListLotUnits();
				
				for (int y = 0; peachTags.size() > y; y++)
				{
					UpdSubLotHeader ticket = new UpdSubLotHeader();
					ticket = requestBean;
					Vector<UpdSubLotDetail> tagList = new Vector<UpdSubLotDetail>();
					UpdSubLotDetail thisTag = (UpdSubLotDetail) peachTags.elementAt(y);
					tagList.addElement(thisTag);
					ticket.setListLotUnits(tagList);
					
					sql = BuildSQL.insertPeachTicketDetail(ticket);	
					insertIt = conn.createStatement();
					insertIt.executeUpdate(sql);
				}
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement detail. " + e);
	 		 }
	 		 
		
		} catch (Exception e)
		{
			throwError.append("Insert peach ticket processing failed. " + e);	
		}
		
		finally {
			
			try {
		  
				if (insertIt != null)		  
					insertIt.close();
				
			} catch(Exception e)
			{
				throwError.append("Insert connnection close failed. " + e);
			}
			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruitPeachReceiving.");
			throwError.append("insertPeachTicket");
			throwError.append("(UpdSubLotHeader, conn). ");		
			
			throw new Exception(throwError.toString());
		}
	
		return;
	}

	/**
	 * @author deisen.
	 * Insert the peach ticket (header and detail)
	 */
	
	public static void insertPeachTicket(UpdSubLotHeader requestBean)
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   conn       = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15(); 
			insertPeachTicket(requestBean, conn);
			
		} catch (Exception e)
		{
			throwError.append("Insert peach ticket setup process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (conn != null)						
			        ServiceConnection.returnConnectionStack15(conn);			
				
			} catch(Exception e)
			{
				throwError.append("Connection failed return. " + e);
			}
		}	
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruitPeachReceiving.");
			throwError.append("insertPeachTicket");
			throwError.append("(UpdSubLotHeader). ");		
			
			throw new Exception(throwError.toString());
		}
		
		return;
	}

	/**
	 * @author deisen.
	 * Delete the peach ticket (header and detail)
	 */
	
	private static void deletePeachTicket(UpdSubLotHeader requestBean, Connection conn)             
	throws Exception
	{
		StringBuffer throwError  = new StringBuffer();
		String       sql         = new String();
		Statement    deleteIt    = null;	
		
		try {
			
	 		try { 			
	
				sql = BuildSQL.deletePeachTicketDetail(requestBean);	
	 			deleteIt = conn.createStatement();
	 			deleteIt.executeUpdate(sql);  			
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement detail. " + e);
	 		 }
	 		 
	 		try { 			
	
				sql = BuildSQL.deletePeachTicketHeader(requestBean);	
	 			deleteIt = conn.createStatement();
	 			deleteIt.executeUpdate(sql);  			
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement header. " + e);
	 		 }
	 		 
		
		} catch (Exception e)
		{
			throwError.append("Delete peach ticket processing failed. " + e);	
		}
		
		finally {
			
			try {
		  
				if (deleteIt != null)		  
					deleteIt.close();
				
			} catch(Exception e)
			{
				throwError.append("Delete connnection close failed. " + e);
			}
			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruitPeachReceiving.");
			throwError.append("deletePeachTicket");
			throwError.append("(UpdSubLotHeader, conn). ");		
			
			throw new Exception(throwError.toString());
		}
	
		return;
	}

	/**
	 * @author deisen.
	 * Delete the peach ticket (header and detail)
	 */
	
	public static void deletePeachTicket(UpdSubLotHeader requestBean)
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   conn       = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15(); 
			deletePeachTicket(requestBean, conn);
			
		} catch (Exception e)
		{
			throwError.append("Delete peach ticket setup process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (conn != null)						
			        ServiceConnection.returnConnectionStack15(conn);			
				
			} catch(Exception e)
			{
				throwError.append("Connection failed return. " + e);
			}
		}	
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruitPeachReceiving.");
			throwError.append("deletePeachTicket");
			throwError.append("(UpdSubLotHeader). ");		
			
			throw new Exception(throwError.toString());
		}
		
		return;
	}

	/**
	 * @author deisen.
	 * Find the peach ticket (header and detail) for detail page.
	 */
	
	private static void findPeachTicket(DtlSubLot requestBean, Connection conn)             
	throws Exception
	{
		StringBuffer throwError  = new StringBuffer();
		String       sql         = new String();
		ResultSet    rs          = null;
		Statement    listThem    = null;
		
		Vector<TicketDetail> tagList = new Vector<TicketDetail>();
		
		try {
			
	 		try { 			
	
				sql = BuildSQL.findPeachTicketHeader(requestBean);	
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);				
				
					 try {
						 
						 if (rs.next())
						 {							
							 PeachTicket ticket = LoadFields.peachHeader(rs);
							 requestBean.getBeanPeach().setTicket(ticket);					 		
						 }
						
			     	 } catch(Exception e)
					 {
			     		throwError.append("Error occured while processing a result set header. " + e);
					 }					 
			
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement header. " + e);
	 		 }
	 		 
	 		 if (throwError.toString().trim().equals(""))
	 		 {
	 		 
	 			 try { 			
	
	 				 sql = BuildSQL.findPeachTicketDetail(requestBean);	
	 				 listThem = conn.createStatement();
	 				 rs = listThem.executeQuery(sql);
				
	 				 try {
					 
	 					 while (rs.next())
	 					 {							
	 						 TicketDetail ticketTag = LoadFields.peachDetail(rs);
	 						 tagList.addElement(ticketTag);
	 						 					 		
	 					 }
	 					 
	 					requestBean.getBeanPeach().getTicket().setTagDetail(tagList);
					
	 				 } catch(Exception e)
	 				 {
	 					 throwError.append("Error occured while processing a result set detail. " + e);
	 				 }	 			 	 		 
	 		
	 		 	} catch(Exception e)
	 		 	{
	 		 		throwError.append("Error occured retrieving or executing a sql statement detail. " + e);
	 		 	}
			
	 		 }
		
		} catch (Exception e)
		{
			throwError.append("Find peach ticket processing failed. " + e);	
		}
		
		finally {
			
			try {
				
				if (rs != null)	   
				    rs.close();
				 
			} catch(Exception e)
			{
				throwError.append("Result set close failed. " + e);
			}				
		
			
			try {
		  
				if (listThem != null)		  
					listThem.close();
				
			} catch(Exception e)
			{
				throwError.append("SQL statement close failed. " + e);
			}
			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruitPeachReceiving.");
			throwError.append("findPeachTicket");
			throwError.append("(DtlSubLot, conn). ");		
			
			throw new Exception(throwError.toString());
		}
	
		return;
	}

	/**
	 * @author deisen.
	 * Find the peach ticket (header and detail) for detail page.
	 */
	
	public static void findPeachTicket(DtlSubLot requestBean)
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   conn       = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15(); 
			findPeachTicket(requestBean, conn);
			
		} catch (Exception e)
		{
			throwError.append("Find peach ticket setup process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (conn != null)						
			        ServiceConnection.returnConnectionStack15(conn);			
				
			} catch(Exception e)
			{
				throwError.append("Connection failed return. " + e);
			}
		}	
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruitPeachReceiving.");
			throwError.append("findPeachTicket");
			throwError.append("(DtlSubLot). ");		
			
			throw new Exception(throwError.toString());
		}
		
		return;
	}

	/**
	 * @author deisen.
	 * List the peach tickets (header) for the search page.
	 */
	
	private static void listPeachTicket(InqSubLot requestBean, Connection conn)             
	throws Exception
	{
		StringBuffer throwError  = new StringBuffer();
		String       sql         = new String();
		ResultSet    rs          = null;
		Statement    listThem    = null;
		
		Vector<PeachTicket> ticketList = new Vector<PeachTicket>();
		
		try {
			
	 		try { 			
	
				sql = BuildSQL.listPeachTicket(requestBean);	
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);				
				
					 try {
						 
						 while (rs.next())
						 {							
							 PeachTicket ticket = LoadFields.peachHeader(rs);
							 ticketList.addElement(ticket);
							 requestBean.getBeanPeach().setTicketList(ticketList);					 		
						 }
						
			     	 } catch(Exception e)
					 {
			     		throwError.append("Error occured while processing a result set header. " + e);
					 }					 
			
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement header. " + e);
	 		 }
	 		 
		} catch (Exception e)
		{
			throwError.append("List peach ticket processing failed. " + e);	
		}
		
		finally {
			
			try {
				
				if (rs != null)	   
				    rs.close();
				 
			} catch(Exception e)
			{
				throwError.append("Result set close failed. " + e);
			}				
		
			
			try {
		  
				if (listThem != null)		  
					listThem.close();
				
			} catch(Exception e)
			{
				throwError.append("SQL statement close failed. " + e);
			}
			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruitPeachReceiving.");
			throwError.append("listPeachTicket");
			throwError.append("(InqSubLot, conn). ");		
			
			throw new Exception(throwError.toString());
		}
	
		return;
	}

	public static Vector<DropDownSingle> getDropDownSinglePeachItems(String env) throws Exception {
		
		Vector<DropDownSingle> items = new Vector<DropDownSingle>();
		
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15();
			stmt = conn.createStatement();
			
			String sql = BuildSQL.dropDownSinglePeachItems(env);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
	
				DropDownSingle dd = new DropDownSingle();
				
				dd.setDescription(rs.getString("MMITDS").trim());
				dd.setValue(rs.getString("MPITNO").trim());
				
				items.add(dd);
	
			}
			
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
			}
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack15(conn);
				} catch (Exception e) {}
			}
		}
		
		if (!throwError.toString().trim().equals("")) {
			throw new Exception(throwError.toString());
		}
		
		return items;
	}

}
