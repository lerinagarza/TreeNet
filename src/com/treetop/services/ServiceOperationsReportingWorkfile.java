package com.treetop.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.treetop.businessobjectapplications.BeanOperationsReporting;
import com.treetop.businessobjects.ManufacturingOrderDetail;
import com.treetop.businessobjects.Warehouse;
import com.treetop.controller.operations.InqOperations;
import com.treetop.services.ServiceOperationsReporting.ReportingType;

@RunWith(Suite.class)
public class ServiceOperationsReportingWorkfile {

	private static String[] warehouses = null;
	private static Hashtable<Integer, Integer[]> periods = null;

	static {
		periods = new Hashtable<Integer, Integer[]>();

		int week = 1;
		for (int i = 1; i <= 12; i++) {
			int weekCount = i % 3 == 0 ? 5 : 4;

			Integer[] weeks = new Integer[weekCount];
			for (int p = 0; p < weekCount; p++) {
				weeks[p] = week;
				week++;
			}
			periods.put(i, weeks);
		}

		//warehouses = new String[] { "209", "230", "240", "280", "290", "469" };
		warehouses = new String[] { "240" };

	}

	private static class BuildSQL {

		private static String clearWorkfile(InqOperations io) {
			StringBuffer sql = new StringBuffer();

			sql.append(" DELETE FROM DBTST.OPPVOLSUM ");
			sql.append(" WHERE ");
			sql.append(" OPYEA4 = " + io.getFiscalYear() + "");
			sql.append(" AND OPPERI = " + io.getFiscalPeriodStart() + " ");

			return sql.toString();
		}

		private static String insertRow(InqOperations io, ManufacturingOrderDetail m, String key, String level1) throws Exception {

			StringBuffer sql = new StringBuffer();

			String level2 = "";
			String level3 = "";

			String[] keyParts = key.split(":");
			if (keyParts.length >= 0) {
				level2 = keyParts[0];
			}

			if (keyParts.length > 1) {
				level3 = keyParts[1];
			}

			sql.append(" INSERT INTO  ");
			sql.append(" DBTST.OPPVOLSUM VALUES ( ");
			sql.append(" 100,  ");
			sql.append(" '" + io.getBean().getWarehouse().getFacility() + "', ");
			sql.append(" '" + io.getWarehouse() + "', ");
			sql.append(" " + io.getFiscalYear() + ", ");
			sql.append(" " + io.getFiscalPeriodStart() + ", ");
			sql.append(" " + io.getFiscalWeekStart() + ", ");
			sql.append(" '" + level1 + "', ");
			sql.append(" '" + level2 + "', ");
			sql.append(" '" + level3 + "', ");
			
			if (level1.equals("RAWFRUIT")) {
				sql.append(" " + m.getUsageRawFruitForecast() + ", ");
				sql.append(" " + m.getUsageRawFruitPlanned() + ", ");
				sql.append(" " + m.getUsageRawFruitActual() + ", ");
				sql.append(" 'TON' ");
			}
			if (level1.equals("FINISHEDGOODSCS")) {
				sql.append(" " + m.getProductionForecast() + ", ");
				sql.append(" " + m.getProductionPlanned() + ", ");
				sql.append(" " + m.getProduction() + ", ");
				sql.append(" 'CS' ");
			}
			if (level1.equals("FINISHEDGOODSLB")) {
				sql.append(" " + m.getProductionForecast() + ", ");
				sql.append(" " + m.getProductionPlanned() + ", ");
				sql.append(" " + m.getProductionLbAtStd() + ", ");
				sql.append(" 'LB' ");
			}
			

			sql.append(" ) ");

			return sql.toString();

		}

	}

	public static void clearWorkfile(InqOperations io) throws Exception {
		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (io == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (io.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (io.getFiscalYear().equals("")) {
				throwError.append("Fiscal year cannot be empty");
			}
			if (io.getFiscalPeriodStart().equals("")) {
				throwError.append("Fiscal period start cannot be empty");
			}
		}

		Connection conn = null;

		if (throwError.toString().trim().equals("")) {

			try {
				conn = ServiceConnection.getConnectionStack11();

				clearWorkfile(io, conn);

			} catch (Exception e) {
				throwError.append(e);
			} finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}

		}

		if (!throwError.toString().trim().equals("")) {
			throw new Exception(throwError.toString());
		}

	}

	private static void clearWorkfile(InqOperations io, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Statement stmt = null;

		try {

			String sql = BuildSQL.clearWorkfile(io);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throwError.append(e);
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingWorkfile.");
			throwError.append("clearWorkfile() ");
			throw new Exception(throwError.toString());
		}
	}

	public static void buildWorkfile(InqOperations io) throws Exception {
		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (io == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (io.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (io.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
			}
			if (io.getFiscalYear().equals("")) {
				throwError.append("Fiscal year cannot be empty");
			}
			if (io.getFiscalPeriodStart().equals("")) {
				throwError.append("Fiscal period start cannot be empty");
			}
		}

		Connection conn = null;

		if (throwError.toString().trim().equals("")) {

			try {
				conn = ServiceConnection.getConnectionStack11();
				
				if (io.getWarehouse().equals("209")) {
					buildWorkfileRawFruit(io, conn);
					//buildWorkfilePackaging(io, conn);
					
				}
				if (io.getWarehouse().equals("230")) {
					buildWorkfileRawFruit(io, conn);
					//buildWorkfileProcessingProduction(io, conn);
				}
				if (io.getWarehouse().equals("240")) {
					buildWorkfileRawFruit(io, conn);
					//buildWorkfileProcessingProduction(io, conn);

				}
				if (io.getWarehouse().equals("251")) {
					buildWorkfileRawFruit(io, conn);
					//buildWorkfileProcessingProduction(io, conn);

				}
				if (io.getWarehouse().equals("280")) {
					buildWorkfileRawFruit(io, conn);
					//buildWorkfileProcessingProduction(io, conn);

				}
				if (io.getWarehouse().equals("290")) {
					buildWorkfileRawFruit(io, conn);
					//buildWorkfileProcessingProduction(io, conn);

				}
				if (io.getWarehouse().equals("469")) {
					buildWorkfileRawFruit(io, conn);
					//buildWorkfilePackaging(io, conn);

				}
				if (io.getWarehouse().equals("490")) {
					buildWorkfileRawFruit(io, conn);
					//buildWorkfilePackaging(io, conn);
				}
				

				

			} catch (Exception e) {
				throwError.append(e);
			} finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
			
			if (!throwError.toString().equals("")) {
				throw new Exception(throwError.toString());
			}

		}
	}
	
	private static void buildWorkfileRawFruit(InqOperations io, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();

		Statement stmt = null;

		try {

			stmt = conn.createStatement();

			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			LinkedHashMap<String, ManufacturingOrderDetail> lhm = io.getBean().getRawFruitMOs();

			for (String key : lhm.keySet()) {
				ManufacturingOrderDetail m = lhm.get(key);

				if (m.getUsageRawFruitForecast().compareTo(BigDecimal.ZERO) != 0
						|| m.getUsageRawFruitPlanned().compareTo(BigDecimal.ZERO) != 0
						|| m.getUsageRawFruitActual().compareTo(BigDecimal.ZERO) != 0) {

					try {
						String insert = BuildSQL.insertRow(io, m, key, "RAWFRUIT");
						stmt.addBatch(insert);
					} catch (Exception e) {
						System.err.println(e);
					}

				}

			}

			stmt.executeBatch();

		} catch (Exception e) {
			throwError.append(e);
		} finally {
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingWorkfile.");
			throwError.append("buildWorkfile() ");
			throw new Exception(throwError.toString());
		}
	}

	private static void buildWorkfilePackaging(InqOperations io, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();

		Statement stmt = null;

		try {

			stmt = conn.createStatement();

			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PKG);
			LinkedHashMap<String, ManufacturingOrderDetail> lhm = io.getBean().getPackagingMOs();

			int i=0;
			for (String key : lhm.keySet()) {
				ManufacturingOrderDetail m = lhm.get(key);

				if (m.getProductionForecast().compareTo(BigDecimal.ZERO) != 0
						|| m.getProductionPlanned().compareTo(BigDecimal.ZERO) != 0
						|| m.getProduction().compareTo(BigDecimal.ZERO) != 0) {
					try {
						String insert = BuildSQL.insertRow(io, m, key, "FINISHEDGOODSCS");
						stmt.addBatch(insert);
						i++;
					} catch (Exception e) {
						System.err.println(e);
					}

				}

			}
			
			if (i > 0) {
				stmt.executeBatch();
			}

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throwError.append(e);
				}
			}
		}
		

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingWorkfile.");
			throwError.append("buildWorkfile() ");
			throw new Exception(throwError.toString());
		}
	}
	
	private static void buildWorkfileProcessingProduction(InqOperations io, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();

		Statement stmt = null;

		try {

			stmt = conn.createStatement();

			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			LinkedHashMap<String, ManufacturingOrderDetail> lhm = io.getBean().getProcessingMOs();

			int i=0;
			for (String key : lhm.keySet()) {
				ManufacturingOrderDetail m = lhm.get(key);

				if (m.getProductionForecast().compareTo(BigDecimal.ZERO) != 0
						|| m.getProductionPlanned().compareTo(BigDecimal.ZERO) != 0
						|| m.getProduction().compareTo(BigDecimal.ZERO) != 0) {
					try {
						String insert = BuildSQL.insertRow(io, m, key, "FINISHEDGOODSLB");
						stmt.addBatch(insert);
						i++;
					} catch (Exception e) {
						System.err.println(e);
					}

				}

			}
			
			if (i > 0) {
				stmt.executeBatch();
			}

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throwError.append(e);
				}
			}
		}
		

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingWorkfile.");
			throwError.append("buildWorkfile() ");
			throw new Exception(throwError.toString());
		}
	}


	public static class UnitTests {

		@Test
		public void testClearWorkfile() throws Exception {

			InqOperations io = new InqOperations();
			io.setEnvironment("PRD");
			io.setFiscalYear("2013");
			io.setFiscalPeriodStart("1");

			clearWorkfile(io);
		}

		@Test
		public void testBuildWorkfile() throws Exception {
			
			for (int i=5; i<=5; i++) {
			
				InqOperations io = new InqOperations();
				io.setEnvironment("PRD");
				io.setFiscalYear("2013");
				io.setFiscalPeriodStart(String.valueOf(i));
				io.setFiscalPeriodEnd(String.valueOf(i));
	
				clearWorkfile(io);
	

				
				for (String warehouse : warehouses) {


					
					io.setWarehouse(warehouse);
					ServiceOperationsReporting.getOperationsWarehouse(io);
					Warehouse whse = io.getBean().getWarehouse();
					
	
					Integer[] weeks = periods.get(Integer.parseInt(io.getFiscalPeriodStart()));
	
					for (int week : weeks) {
						
						System.out.println("Period:\t" + i + "\tWeek:\t" + week + "\tWarehouse:\t" + warehouse);
						
						io.setBean(new BeanOperationsReporting());
						io.getBean().setWarehouse(whse);
						io.setFiscalWeekStart(String.valueOf(week));
						io.setFiscalWeekEnd(String.valueOf(week));
						io.buildDateRangeFromWeek();
						try {
							buildWorkfile(io);
						} catch (Exception e) {
							System.err.println(e);
						}
	
					}
	
				}
	
			}
		}
	}

}
