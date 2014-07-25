package com.treetop.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.treetop.businessobjects.*;
import com.treetop.controller.blending.*;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;

/**
 * @author twalto.
 *
 * Service class to obtain and return data 
 * for user business objects for Blending of Products to reach a specific attribute level
 * 
 */

public class ServiceBlending {
	
	private static class BuildSQL {
		
		/**
		 * @author twalto.
		 * SQL to retrieve a list of production plan.
		 */
			
		private static String listProductionPlan(InqBlending requestBean) 
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {		
		
				if ((!requestBean.getEnvironment().trim().equals("")) &&
					(!requestBean.getCompany().trim().equals(""))) 		
				{
					String library = GeneralUtility.getLibrary(requestBean.getEnvironment().trim());
					String ttLibrary = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());
					
					sqlString.append(" SELECT MOWHLO, MWWHNM, MOITNO, MMITDS, MOPLDT, MOTRQT, ");
					sqlString.append(" MMUNMS, (MOTRQT * (ifNull(fs.MUCOFA,0))) as fruitSolids, ");
					sqlString.append(" CFFACI, CFFACN, MWFACI, MWWHLO, ");
					sqlString.append(" ifNull(DCPN01,0) as batchSize ");
					sqlString.append(", (MOTRQT * (ifnull (gl.MUCOFA, 0))) as gallons ");
					
					sqlString.append(" FROM " + library + ".MITPLO");
					
					sqlString.append(" INNER JOIN " + library + ".MITMAS ON MOCONO = MMCONO ");
					sqlString.append("   AND MOITNO = MMITNO AND MMITTY = '100' ");
					sqlString.append("   AND MMITCL <> '130' AND MMITCL <> '300' ");
					
					sqlString.append(" LEFT OUTER JOIN " + library + ".MITAUN fs ON MOCONO = fs.MUCONO ");
					sqlString.append("   AND MOITNO = fs.MUITNO AND fs.MUAUTP = 1 AND fs.MUALUN = 'FS' ");
					
					sqlString.append(" LEFT OUTER JOIN " + library + ".MITAUN gl ON MOCONO = gl.MUCONO ");
					sqlString.append("   AND MOITNO = gl.MUITNO AND gl.MUAUTP = 1 AND gl.MUALUN = 'GL' ");

					sqlString.append(" LEFT OUTER JOIN " + library + ".MITWHL ON MWCONO = MOCONO ");
					sqlString.append("   AND MWWHLO = MOWHLO ");
					
					sqlString.append(" LEFT OUTER JOIN " + library + ".CFACIL on MWCONO = CFCONO ");
					sqlString.append("    AND MWFACI = CFFACI ");
					
					sqlString.append(" LEFT OUTER JOIN " + ttLibrary + ".DCPALL ");
					sqlString.append("    ON DCPK00 = 'BLENDINGBATCHSIZE' AND MWFACI = DCPK01 ");
					
					sqlString.append(" INNER JOIN " + library + ".CSYPER ");
					sqlString.append(" ON MOCONO = CPCONO AND CPDIVI = '' AND CPPETP = 2 ");
					sqlString.append(" AND MOPLDT >= CPFDAT AND MOPLDT <= CPTDAT ");
					
					sqlString.append(" WHERE MOCONO = 100 AND MOORCA = '100' ");
					sqlString.append("  AND MOPLDT >= " + requestBean.getFromDate());
					sqlString.append("  AND MOPLDT <= " + requestBean.getToDate());
					
					//sqlString.append(" ORDER BY MOPLDT, MWFACI, MOWHLO, MOITNO "); 2013/07/18 wth
					sqlString.append(" ORDER BY CPYEA4, CPPERI, MWFACI, MOITNO ");
					
				}				
				
			} catch (Exception e) 
			{		
				throwError.append("Build SQL statement failed. ");
				throwError.append("listProductionPlan. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceBlending.");
				throwError.append("BuildSQL.listProductionPlan(InqBlending)");
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
			
		} // listProductionPlan
		
	}

	/**
	 * @author twalto.
	 * Load fields from SQL results.
	 */
	private static class LoadFields {

		/**
		 * @author deisen.
		 * Load fields for one Manufacturing Order (header).
		 */
				
		private static ManufacturingOrder moHeader(ResultSet rs) 
		throws Exception
		{		
			StringBuffer throwError = new StringBuffer();
			ManufacturingOrder moHead = new ManufacturingOrder();
		
			try {
				Warehouse whse = new Warehouse();
				moHead.setWarehouse(ServiceWarehouse.loadFieldsWarehouse("basicWarehouse", rs));
				Item itm = new Item();
				itm.setItemNumber(rs.getString("MOITNO").trim());
				itm.setItemDescription(rs.getString("MMITDS").trim());
				itm.setBasicUnitOfMeasure(rs.getString("MMUNMS").trim());
				moHead.setItem(itm);
				DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(rs.getString("MOPLDT"));
				moHead.setActualStartDate(UtilityDateTime.addDaysToDate(dt, 0));
				moHead.setOrderQuantity(rs.getBigDecimal("MOTRQT"));
				moHead.setProductionFsAtStd(rs.getBigDecimal("fruitSolids"));
				moHead.setProductionGlAtStd(rs.getBigDecimal("gallons"));
				
				moHead.setStandardBatchSize(rs.getBigDecimal("batchSize"));
				if (moHead.getStandardBatchSize().compareTo(new BigDecimal("0")) != 0){
//					moHead.setNumberOfBatches(moHead.getOrderQuantity().divide(moHead.getStandardBatchSize(), 2));      //2013/07/18 wth
					moHead.setNumberOfBatches(moHead.getProductionGlAtStd().divide(moHead.getStandardBatchSize(), 2));  //2013/07/18 wth
				}
					
				
			} catch (Exception e) 
			{		
				throwError.append("Load fields failed. ");
				throwError.append("moHeader. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceBlending.");
				throwError.append("LoadFields.moHeader(ResultSet)");
				
				throw new Exception(throwError.toString());
			}
				
			return moHead;
			
		} // peachHeader
	
	}

	/**
	 * @author twalto.
	 * main, used for debugging.
	 */
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		int	 activeTest  = 1;
		
		try {
		    // test for returning a list of Planned production
			if (activeTest == 1)  
			{
				InqBlending bean = new InqBlending();
				bean.setCompany("100");
				bean.setEnvironment("PRD");
				bean.setFromDate("20130701");
				bean.setToDate("201307022");
				
				listProductionPlan(bean);
				String breakPoint = "stopHere";
			}
			
		} catch (Exception e) 
		{
				System.out.println(e);	
		}
		
	}

	/**
	 * @author twalto.
	 * List the Production Plan for a certain time frame.
	 */
	
	public static void listProductionPlan(InqBlending requestBean)
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   conn       = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15(); 
			listProductionPlan(requestBean, conn);
			
		} catch (Exception e)
		{
			throwError.append("List production plan process failed. " + e);		
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
			throwError.append("ServiceBlending.");
			throwError.append("listProductionPlan");
			throwError.append("(InqBlending). ");		
			
			throw new Exception(throwError.toString());
		}
		
		return;
	}

	/**
	 * @author twalto.
	 * List the production plan for the search page.
	 */
	
	private static void listProductionPlan(InqBlending requestBean, Connection conn)             
	throws Exception
	{
		StringBuffer throwError  = new StringBuffer();
		String       sql         = new String();
		ResultSet    rs          = null;
		Statement    listThem    = null;
		
		Vector<ManufacturingOrder> moList = new Vector<ManufacturingOrder>();
		
		try {
			
	 		try { 			
	
				sql = BuildSQL.listProductionPlan(requestBean);	
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);				
				
					 try {
						 
						 while (rs.next())
						 {							
							 moList.addElement(LoadFields.moHeader(rs));
							 		
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
			throwError.append("List production plan processing failed. " + e);	
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
		 requestBean.getBean().setListProduction(moList);	
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceBlending.");
			throwError.append("listProductionPlan");
			throwError.append("(InqBlending, conn). ");		
			
			throw new Exception(throwError.toString());
		}
	
		return;
	}

}
