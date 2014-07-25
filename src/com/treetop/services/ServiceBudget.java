package com.treetop.services;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Vector;

import com.lawson.api.BUS100MI;
import com.lawson.api.BUS100MIAddBudgetLines;
import com.lawson.api.BUS100MIDelBudgetLines;
import com.treetop.businessobjects.AccountID;
import com.treetop.businessobjects.AccountString;
import com.treetop.controller.budget.InqBudget;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.ValidateAccount;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.viewbeans.CommonRequestBean;

public class ServiceBudget {
	
	public static void main(String[] args) {
		try {
			
			InqBudget inqBudget = new InqBudget();
			
			inqBudget.setEnvironment("TST");
			inqBudget.setAuthorization("Basic amhhZ2xlOnZ5MXVndw==");
			
			inqBudget.setCompany("100");
			inqBudget.setDivision("100");
			inqBudget.setBudgetNumber("13");
			inqBudget.setBudgetVersion("TST");
			inqBudget.setDepartment("FINANCIAL");
			inqBudget.setForecastMiss("DEC");
			
			//delBudgetLines(inqBudget);
			
			//adjustDecemberForecastMiss(inqBudget);
			adjustForecastMiss(inqBudget);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static Vector<DropDownDual> getDropDownBudget(InqBudget inqBudget) throws Exception {
		
		Vector<DropDownDual> items = new Vector<DropDownDual>();
		
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15();
			stmt = conn.createStatement();
			
			String sql = BuildSQL.listBudgets(inqBudget);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {

				DropDownDual dd = new DropDownDual();
				
				dd.setMasterValue(rs.getString("BDBUNO").trim());
				dd.setSlaveValue(rs.getString("BDBVER").trim());
				dd.setSlaveDescription(rs.getString("BDTX40").trim());
				
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
	
	public static Vector<HtmlOption> getDropDownDeparments(InqBudget inqBudget) throws Exception {
		
		Vector<HtmlOption> items = new Vector<HtmlOption>();
		
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15();
			stmt = conn.createStatement();
			
			String sql = BuildSQL.listDeparments(inqBudget);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {

				HtmlOption dd = new HtmlOption();
				
				dd.setValue(rs.getString("DCPA01").trim());
				dd.setDescription(rs.getString("DCPA01").trim());
				
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
	
	private static void delBudgetLines(InqBudget inqBudget) throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15();
			stmt = conn.createStatement();
			
			String sql = BuildSQL.listBudgetLines(inqBudget);
			rs = stmt.executeQuery(sql);
			
			Vector<BUS100MIDelBudgetLines> lines = inqBudget.getBean().getDelBudgetLines();

			while (rs.next()) {

				BUS100MIDelBudgetLines del = new BUS100MIDelBudgetLines();
				del.setEnvironment(inqBudget.getEnvironment());
				del.setAuthorization(inqBudget.getAuthorization());
				
				del.setCompany(rs.getString("CONO").trim());
				del.setDivision(rs.getString("DIVI").trim());
				del.setBudgetNumber(rs.getString("BUNO").trim());
				del.setBudgetVersion(rs.getString("BVER").trim());
				
				del.setDimension1(rs.getString("DIM1").trim());
				del.setDimension2(rs.getString("DIM2").trim());
				del.setDimension3(rs.getString("DIM3").trim());
				del.setDimension4(rs.getString("DIM4").trim());
				del.setDimension5(rs.getString("DIM5").trim());
				del.setDimension6(rs.getString("DIM6").trim());
				del.setDimension7(rs.getString("DIM7").trim());
				
				lines.add(del);

			}
			
			lines = BUS100MI.delBudgetLines(lines);

			
						
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
		
	}
	
	
	private static boolean validateAccountString(InqBudget inqBudget) {
		StringBuffer errors = new StringBuffer();
		boolean valid = true;
		
		Vector<BUS100MIAddBudgetLines> lines = inqBudget.getBean().getAddBudgetLines();
		Vector<AccountString> invalidAccountStrings = inqBudget.getBean().getInvalidAccountStrings();
		
		for (BUS100MIAddBudgetLines line : lines) {
			
			AccountString acctStr = new AccountString();
			acctStr.setDimension1(new AccountID("1", line.getDimension1()));
			acctStr.setDimension2(new AccountID("2", line.getDimension2()));
			acctStr.setDimension3(new AccountID("3", line.getDimension3()));
			acctStr.setDimension4(new AccountID("4", line.getDimension4()));
			acctStr.setDimension5(new AccountID("5", line.getDimension5()));
			acctStr.setDimension6(new AccountID("6", line.getDimension6()));
			acctStr.setDimension7(new AccountID("7", line.getDimension7()));
			

			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment(line.getEnvironment());
			crb.setCompanyNumber(line.getCompany());
			crb.setDivisionNumber(line.getDivision());
			
			acctStr = ValidateAccount.validateBudAccountString(crb, acctStr);
			if (!acctStr.isValid()) {
				valid = false;
				invalidAccountStrings.addElement(acctStr);
			}
			
			if (!validateAccountAmounts(line, acctStr)) {
				valid = false;
				invalidAccountStrings.addElement(acctStr);
			}
			
			

		}
		
		return valid;
	}
	
	private static boolean validateAccountAmounts(BUS100MIAddBudgetLines line, AccountString acctStr) {
		boolean valid = true;
		
		try {
			if (!line.getAmount1().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount1());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount1() + "\" in Period01 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount2().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount2());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount2() + "\" in Period02 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount3().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount3());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount3() + "\" in Period03 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount4().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount4());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount4() + "\" in Period04 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount5().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount5());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount5() + "\" in Period05 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount6().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount6());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount6() + "\" in Period06 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount7().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount7());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount7() + "\" in Period07 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount8().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount8());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount8() + "\" in Period08 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount9().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount9());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount9() + "\" in Period09 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount10().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount10());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount10() + "\" in Period10 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount11().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount11());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount11() + "\" in Period11 is not a valid number");
			acctStr.setValid(false);
		}
		
		try {
			if (!line.getAmount12().equals("")) {
				BigDecimal amount = new BigDecimal(line.getAmount12());
			}
		} catch (Exception e) {
			valid = false;
			acctStr.setError(acctStr.getError() + "    Amount \"" + line.getAmount12() + "\" in Period12 is not a valid number");
			acctStr.setValid(false);
		}
				
		return valid;
	}
	

	
	private static void consolidateUpload(InqBudget inqBudget) {
		
		Vector<BUS100MIAddBudgetLines> lines = inqBudget.getBean().getAddBudgetLines();
		
		Vector<BUS100MIAddBudgetLines> consolidatedLines = new Vector<BUS100MIAddBudgetLines>();
		LinkedHashMap<String,BUS100MIAddBudgetLines> lhm = new LinkedHashMap<String,BUS100MIAddBudgetLines>();
		
		for (BUS100MIAddBudgetLines line : lines) {
			
			String key = line.getDimension1() + 
						 line.getDimension2() +
						 line.getDimension3() +
						 line.getDimension4() +
						 line.getDimension5() +
						 line.getDimension6() +
						 line.getDimension7();
			if (lhm.get(key) == null) {
				lhm.put(key, line);
			} else {
				BUS100MIAddBudgetLines existing = lhm.get(key); 
				
				existing.setAmount1(consolidateAmounts(existing.getAmount1(), line.getAmount1()));
				existing.setAmount2(consolidateAmounts(existing.getAmount2(), line.getAmount2()));
				existing.setAmount3(consolidateAmounts(existing.getAmount3(), line.getAmount3()));
				existing.setAmount4(consolidateAmounts(existing.getAmount4(), line.getAmount4()));
				existing.setAmount5(consolidateAmounts(existing.getAmount5(), line.getAmount5()));
				existing.setAmount6(consolidateAmounts(existing.getAmount6(), line.getAmount6()));
				existing.setAmount7(consolidateAmounts(existing.getAmount7(), line.getAmount7()));
				existing.setAmount8(consolidateAmounts(existing.getAmount8(), line.getAmount8()));
				existing.setAmount9(consolidateAmounts(existing.getAmount9(), line.getAmount9()));
				existing.setAmount10(consolidateAmounts(existing.getAmount10(), line.getAmount10()));
				existing.setAmount11(consolidateAmounts(existing.getAmount11(), line.getAmount11()));
				existing.setAmount12(consolidateAmounts(existing.getAmount12(), line.getAmount12()));
				
								
			}
	
		}
		
		
		for (String lineKey : lhm.keySet()) {
			BUS100MIAddBudgetLines consolidatedLine = lhm.get(lineKey);
			consolidatedLines.addElement(consolidatedLine);
		}
		
		inqBudget.getBean().setAddBudgetLines(consolidatedLines);
		
	}
	
	private static String consolidateAmounts(String existingAmt, String newAmt) {
		
		BigDecimal eAmt = BigDecimal.ZERO; 
		BigDecimal nAmt = BigDecimal.ZERO;
		try {
			eAmt = new BigDecimal(existingAmt);
		} catch (Exception e) {}
		try {
			nAmt = new BigDecimal(newAmt);
		} catch (Exception e) {}
		
		return eAmt.add(nAmt).toString();
		
	}
	
	private static void processUpload(InqBudget inqBudget, Vector<String> contents) {
		Vector<BUS100MIAddBudgetLines> lines = 	inqBudget.getBean().getAddBudgetLines();
		boolean first = true;
		for (String line : contents) {
			
			//skip the first line
			//contains column headers
			if (first) {
				first = false;
				continue;
			}
			
			
			BUS100MIAddBudgetLines ln = new BUS100MIAddBudgetLines();
			String[] cols = line.split(",");
			int i=0;
			for (String col : cols) {
				
				ln.setEnvironment(inqBudget.getEnvironment());
				ln.setCompany(inqBudget.getCompany());
				ln.setDivision(inqBudget.getDivision());
				ln.setBudgetNumber(inqBudget.getBudgetNumber());
				ln.setBudgetVersion(inqBudget.getBudgetVersion());
						
				
				switch (i) {
					case 0 : ln.setDimension1(col);	break;
					case 1 : ln.setDimension2(col);	break;
					case 2 : ln.setDimension3(col);	break;
					case 3 : ln.setDimension4(col);	break;
					case 4 : ln.setDimension5(col);	break;
					case 5 : ln.setDimension6(col);	break;
					case 6 : ln.setDimension7(col);	break;
					case 7 : ln.setAmount1(col);	break;
					case 8 : ln.setAmount2(col);	break;
					case 9 : ln.setAmount3(col);	break;
					case 10: ln.setAmount4(col);	break;
					case 11: ln.setAmount5(col);	break;
					case 12: ln.setAmount6(col);	break;
					case 13: ln.setAmount7(col);	break;
					case 14: ln.setAmount8(col);	break;
					case 15: ln.setAmount9(col);	break;
					case 16: ln.setAmount10(col);	break;
					case 17: ln.setAmount11(col);	break;
					case 18: ln.setAmount12(col);	break;
				}
				
				i++;
			}
			lines.addElement(ln);
		}

	}
	
	public static File previewForecastMiss(InqBudget inqBudget) throws Exception {
		
		Hashtable<String,Vector<BUS100MIAddBudgetLines>> lines = calculateForecastMiss(inqBudget); 
		
		Vector<BUS100MIAddBudgetLines> adjLines = lines.get("adj");
		Vector<BUS100MIAddBudgetLines> origLines = lines.get("orig");
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		File file = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".csv");
				
		FileWriter fw = new FileWriter(file);
		
		fw.append("DIM1,");
		fw.append("DIM2,");
		fw.append("DIM3,");
		fw.append("DIM4,");
		fw.append("DIM5,");
		fw.append("DIM6,");
		fw.append("DIM7,");
		
		fw.append("ACT01,");
		fw.append("ACT02,");
		fw.append("ACT03,");
		fw.append("ACT04,");
		
		if (inqBudget.getForecastMiss().equals("DEC")) {
			fw.append("FCST05,");
			fw.append("FCST06,");
			fw.append("FCST07,");
			fw.append("FCST08,");
		}
		
		if (inqBudget.getForecastMiss().equals("APR")) {
			fw.append("ACT05,");
			fw.append("ACT06,");
			fw.append("ACT07,");
			fw.append("ACT08,");
		}
		
		fw.append("FCST09,");
		fw.append("FCST10,");
		fw.append("FCST11,");
		fw.append("FCST12,");
		fw.append("FCST_TOT,");
		fw.append(",");
		
		if (inqBudget.getForecastMiss().equals("DEC")) {
			
			fw.append("ACT04,");
			fw.append("FCST04,");
			fw.append("FCST_MISS,");
			fw.append("FCST05,");
			fw.append("ADJ_FCST05");
			
		}
		
		
		
		if (inqBudget.getForecastMiss().equals("APR")) {
			
			fw.append("ACT08,");
			fw.append("FCST08,");
			fw.append("FCST_MISS,");
			fw.append("FCST09,");
			fw.append("ADJ_FCST09");
		}
		
		
		
		fw.append("\n");
		
		
		for (int i=0; i<adjLines.size(); i++) {
			BUS100MIAddBudgetLines adjLine = adjLines.elementAt(i);
			BUS100MIAddBudgetLines origLine = origLines.elementAt(i);
			
			fw.append(adjLine.getDimension1() + ",");
			fw.append(adjLine.getDimension2() + ",");
			fw.append(adjLine.getDimension3() + ",");
			fw.append(adjLine.getDimension4() + ",");
			fw.append(adjLine.getDimension5() + ",");
			fw.append(adjLine.getDimension6() + ",");
			fw.append(adjLine.getDimension7() + ",");
			
			BigDecimal adj01 = new BigDecimal(adjLine.getAmount1());
			BigDecimal adj02 = new BigDecimal(adjLine.getAmount2());
			BigDecimal adj03 = new BigDecimal(adjLine.getAmount3());
			BigDecimal adj04 = new BigDecimal(adjLine.getAmount4());
			BigDecimal adj05 = new BigDecimal(adjLine.getAmount5());
			BigDecimal adj06 = new BigDecimal(adjLine.getAmount6());
			BigDecimal adj07 = new BigDecimal(adjLine.getAmount7());
			BigDecimal adj08 = new BigDecimal(adjLine.getAmount8());
			BigDecimal adj09 = new BigDecimal(adjLine.getAmount9());
			BigDecimal adj10 = new BigDecimal(adjLine.getAmount10());
			BigDecimal adj11 = new BigDecimal(adjLine.getAmount11());
			BigDecimal adj12 = new BigDecimal(adjLine.getAmount12());
			
			BigDecimal tot = BigDecimal.ZERO 
				.add(adj01)
				.add(adj02)
				.add(adj03)
				.add(adj04)
				.add(adj05)
				.add(adj06)
				.add(adj07)
				.add(adj08)
				.add(adj09)
				.add(adj10)
				.add(adj11)
				.add(adj12)
				;
			
			fw.append(df.format(adj01) + ",");
			fw.append(df.format(adj02) + ",");
			fw.append(df.format(adj03) + ",");
			fw.append(df.format(adj04) + ",");
			fw.append(df.format(adj05) + ",");
			fw.append(df.format(adj06) + ",");
			fw.append(df.format(adj07) + ",");
			fw.append(df.format(adj08) + ",");
			fw.append(df.format(adj09) + ",");
			fw.append(df.format(adj10) + ",");
			fw.append(df.format(adj11) + ",");
			fw.append(df.format(adj12) + ",");
			fw.append(df.format(tot) + ",");
			
			fw.append(",");
			
			if (inqBudget.getForecastMiss().equals("DEC")) {
				
				BigDecimal act04 = new BigDecimal(adjLine.getAmount4());
				BigDecimal fcst04 = new BigDecimal(origLine.getAmount4());
				BigDecimal fcstMiss = fcst04.subtract(adj04);
				BigDecimal fcst05 = new BigDecimal(origLine.getAmount5());
				BigDecimal adjFcst05 = new BigDecimal(adjLine.getAmount5());

				fw.append(df.format(act04) + ",");
				fw.append(df.format(fcst04) + ",");
				fw.append(df.format(fcstMiss) + ",");
				fw.append(df.format(fcst05) + ",");
				fw.append(df.format(adjFcst05));

			}
			
			if (inqBudget.getForecastMiss().equals("APR")) {
				
				BigDecimal act08 = new BigDecimal(adjLine.getAmount8());
				BigDecimal fcst08 = new BigDecimal(origLine.getAmount8());
				BigDecimal fcstMiss = fcst08.subtract(act08);
				
				BigDecimal fcst09 = new BigDecimal(origLine.getAmount9());
				BigDecimal adjFcst09 = new BigDecimal(adjLine.getAmount9());
				
				fw.append(df.format(act08) + ",");
				fw.append(df.format(fcst08) + ",");
				fw.append(df.format(fcstMiss) + ",");
				fw.append(df.format(fcst09) + ",");
				fw.append(df.format(adjFcst09));
				
				
			}
			
			
			fw.append("\n");
			
		}
		
		fw.flush();
		
		return file;
		
	}
	
	public static void adjustForecastMiss(InqBudget inqBudget) throws Exception {
		
		Hashtable<String,Vector<BUS100MIAddBudgetLines>> lines = calculateForecastMiss(inqBudget); 
		
		Vector<BUS100MIAddBudgetLines> adjLines = lines.get("adj");
		updateForecastMiss(inqBudget, adjLines);
		
	}
	
	private static Hashtable<String,Vector<BUS100MIAddBudgetLines>>
		calculateForecastMiss(InqBudget inqBudget) throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		
		Hashtable<String,Vector<BUS100MIAddBudgetLines>> lines = new Hashtable<String,Vector<BUS100MIAddBudgetLines>>(); 
		
		
		Vector<BUS100MIAddBudgetLines> adjLines = new Vector<BUS100MIAddBudgetLines>();
		Vector<BUS100MIAddBudgetLines> origLines = new Vector<BUS100MIAddBudgetLines>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15();
			stmt = conn.createStatement();
			
			String sql = BuildSQL.getForecastAndActuals(inqBudget);
			rs = stmt.executeQuery(sql);
						
			int year = 2000 + Integer.parseInt(inqBudget.getBudgetNumber());
			
			while (rs.next()) {

				BUS100MIAddBudgetLines add = LoadFields.adjustedForecastLines(inqBudget, rs);
				adjLines.addElement(add);
				
				BUS100MIAddBudgetLines orig = LoadFields.originalForecastLines(inqBudget, rs);
				origLines.addElement(orig);
								
			}
			
			lines.put("adj",adjLines);
			lines.put("orig", origLines);
			
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
			throw new Exception("Error adjusting forecast miss   " + throwError.toString());
		}
		
		return lines;
		
	}
	
	
	
	private static void updateForecastMiss(InqBudget inqBudget, Vector<BUS100MIAddBudgetLines> lines) throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		
		try {
			
			//delete all budget lines for this department
			delBudgetLines(inqBudget);
			
			//insert new lines with the adjusted forecast
			lines = BUS100MI.addBudgetLines(lines);
			
		} catch (Exception e) {
			throwError.append(e);
		}
		
		if (!throwError.toString().trim().equals("")) {
			throw new Exception("Error executing APIs to adjust forecast miss   " + throwError.toString());
		}
		
	}
	
	
	private static void uploadData(InqBudget inqBudget) throws Exception {
		StringBuffer throwError = new StringBuffer();
		
		Vector<BUS100MIAddBudgetLines> lines = inqBudget.getBean().getAddBudgetLines();
		
		try {
			
			 lines = BUS100MI.addBudgetLines(lines);

		} catch (Exception e) {
			throwError.append(e);
		}
		
		if (!throwError.toString().trim().equals("")) {
			throw new Exception ("Error executing BUS100MIAddBudgetLines API  "
					+ " @ com.treetop.services.ServiceBudet.uploadData()  "
					+ throwError.toString());
		}

	}
	
	private static Vector<AccountString> listBudgetDeparmentAccountStrings(InqBudget inqBudget) throws Exception {
		Vector<AccountString> acctStrings = new Vector<AccountString>();
		
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15();
			stmt = conn.createStatement();
			
			String sql = BuildSQL.listBudgetDeparmentAccountStrings(inqBudget);
			rs = stmt.executeQuery(sql);

			
			while (rs.next()) {

				AccountString acctString = new AccountString();
				acctString.setDimension1(new AccountID("1", rs.getString("DCPK02").trim(), rs.getString("DCPK01").trim()));
				acctString.setDimension2(new AccountID("2", rs.getString("DCPK03").trim()));
				acctString.setDimension3(new AccountID("3", rs.getString("DCPK04").trim()));
				acctString.setDimension4(new AccountID("4", rs.getString("DCPK05").trim()));
				acctString.setDimension5(new AccountID("5", rs.getString("DCPK06").trim()));
				acctString.setDimension6(new AccountID("6", rs.getString("DCPK07").trim()));
				acctString.setDimension7(new AccountID("7", rs.getString("DCPK08").trim()));
				
				acctStrings.addElement(acctString);

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
			throw new Exception("Error validated budget deparments" + throwError.toString());
		}
		
		return acctStrings;
	}
	
	private static boolean validateBudgetDepartments(InqBudget inqBudget, Vector<AccountString> acctStrings) throws Exception {
		boolean valid = true;
		
		Vector<BUS100MIAddBudgetLines> lines = inqBudget.getBean().getAddBudgetLines();
		Vector<AccountString> invalidAccountStrings = inqBudget.getBean().getInvalidAccountStrings();
		
		for (BUS100MIAddBudgetLines line : lines) {
			boolean thisLineValid = false;
			
			AccountString upload = new AccountString();
			upload.setDimension1(new AccountID("1", line.getDimension1()));
			upload.setDimension2(new AccountID("2", line.getDimension2()));
			upload.setDimension3(new AccountID("3", line.getDimension3()));
			upload.setDimension4(new AccountID("4", line.getDimension4()));
			upload.setDimension5(new AccountID("5", line.getDimension5()));
			upload.setDimension6(new AccountID("6", line.getDimension6()));
			upload.setDimension7(new AccountID("7", line.getDimension7()));
			
			for (AccountString acctString : acctStrings) {

				String dim1 = acctString.getDimension1().getAccountID();
				String dim2 = acctString.getDimension2().getAccountID();
				String dim3 = acctString.getDimension3().getAccountID();
				String dim4 = acctString.getDimension4().getAccountID();
				String dim5 = acctString.getDimension5().getAccountID();
				String dim6 = acctString.getDimension6().getAccountID();
				String dim7 = acctString.getDimension7().getAccountID();
				
				if ((upload.getDimension1().getAccountID().equals(dim1) || dim1.equals("*"))
						&& (upload.getDimension2().getAccountID().equals(dim2) || dim2.equals("*"))
						&& (upload.getDimension3().getAccountID().equals(dim3) || dim3.equals("*"))
						&& (upload.getDimension4().getAccountID().equals(dim4) || dim4.equals("*"))
						&& (upload.getDimension5().getAccountID().equals(dim5) || dim5.equals("*"))
						&& (upload.getDimension6().getAccountID().equals(dim6) || dim6.equals("*"))
						&& (upload.getDimension7().getAccountID().equals(dim7) || dim7.equals("*"))
				) {
					
					thisLineValid = true;
					break;
					
				}
					
				
			}
			
			if (!thisLineValid) {
				valid = false;
				upload.setError("Account String is not defined for " 
						+ " Budget Department: " + inqBudget.getDepartment() + "");
				invalidAccountStrings.addElement(upload);
			}
			
		}
		
		return valid;
	}
	
	public static void listBudgetDepartmentDefinitions(InqBudget inqBudget) throws Exception {
		

		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;

		try {
			
			conn = ServiceConnection.getConnectionStack15();
			listBudgetDepartmentDefinitions(inqBudget, conn);
						
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack15(conn);
				} catch (Exception e) {}
			}
		}
		
		if (!throwError.toString().trim().equals("")) {
			throw new Exception("Error validated budget deparments" + throwError.toString());
		}

		
	}
	
	private static void listBudgetDepartmentDefinitions(InqBudget inqBudget, Connection conn) throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		Vector<AccountString> acctStrings = inqBudget.getBean().getDepartmentDefinitions();
		
		try {

			stmt = conn.createStatement();
			
			String sql = BuildSQL.listBudgetDeparmentDefinition(inqBudget);
			rs = stmt.executeQuery(sql);

			
			while (rs.next()) {

				AccountString acctString = new AccountString();
				acctString.setDimension1(new AccountID("1", rs.getString("DCPK02").trim(), rs.getString("DCPK01").trim()));
				acctString.setDimension2(new AccountID("2", rs.getString("DCPK03").trim()));
				acctString.setDimension3(new AccountID("3", rs.getString("DCPK04").trim()));
				acctString.setDimension4(new AccountID("4", rs.getString("DCPK05").trim()));
				acctString.setDimension5(new AccountID("5", rs.getString("DCPK06").trim()));
				acctString.setDimension6(new AccountID("6", rs.getString("DCPK07").trim()));
				acctString.setDimension7(new AccountID("7", rs.getString("DCPK08").trim()));
				
				acctString.setBudgetDepartment(rs.getString("DCPA01").trim());
				acctString.setBudgetResponsible(rs.getString("DCPA02").trim());
				
				acctString.setDescription(rs.getString("DCPT80").trim());
				
				int dim1 = rs.getInt("DCPN01");
				int dim2 = rs.getInt("DCPN02");
				int dim3 = rs.getInt("DCPN03");
				int dim4 = rs.getInt("DCPN04");
				int dim5 = rs.getInt("DCPN05");
				
				StringBuffer summaryLevel = new StringBuffer();
				if (dim1 == 1) {
					if (!summaryLevel.toString().equals("")) {
						summaryLevel.append(", ");
					}
					summaryLevel.append("1");
				}
				if (dim2 == 1) {
					if (!summaryLevel.toString().equals("")) {
						summaryLevel.append(", ");
					}
					summaryLevel.append("2");
				}
				if (dim3 == 1) {
					if (!summaryLevel.toString().equals("")) {
						summaryLevel.append(", ");
					}
					summaryLevel.append("3");
				}
				if (dim4 == 1) {
					if (!summaryLevel.toString().equals("")) {
						summaryLevel.append(", ");
					}
					summaryLevel.append("4");
				}
				if (dim5 == 1) {
					if (!summaryLevel.toString().equals("")) {
						summaryLevel.append(", ");
					}
					summaryLevel.append("5");
				}
				
				if (!summaryLevel.toString().equals("")) {
					summaryLevel.insert(0, "Dim ");
				}
				acctString.setComment(summaryLevel.toString());
				
				acctStrings.addElement(acctString);

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
		}
		
		if (!throwError.toString().trim().equals("")) {
			throw new Exception("Error validated budget deparments" + throwError.toString());
		}
		
	}
	
	
	
	
	public static void upload(InqBudget inqBudget, Vector<String> data) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<String> responses = new Vector<String>();
		
		processUpload(inqBudget, data);
		
		

		if (validateAccountString(inqBudget)) {
			
			if (validateBudgetDepartments(inqBudget, listBudgetDeparmentAccountStrings(inqBudget))) {
					
				delBudgetLines(inqBudget);
	
				consolidateUpload(inqBudget);
	
				try {
					uploadData(inqBudget);
				} catch (Exception e) {
					throwError.append(e);
				}
				
			}	//end if valid department
			
		} //end if valid account strings
		
		if (!throwError.toString().trim().equals("")) {
			throw new Exception ("Error executing upload  "
					+ " @ com.treetop.services.ServiceBudet.upload()  "
					+ throwError.toString());
		}

	}
	
	
	
	private static class BuildSQL {
		

		private static String listBudgetDeparmentAccountStrings(InqBudget inqBudget) {
			
			StringBuffer sql = new StringBuffer();
			
			String ttLibrary = GeneralUtility.getTTLibrary(inqBudget.getEnvironment());
			String library = GeneralUtility.getLibrary(inqBudget.getEnvironment());
			
			sql.append(" SELECT  ");
			sql.append(" DCPK01,");
			sql.append(" CASE WHEN DCPK01<>'*' AND DCPK02='*' THEN EAAITM ELSE DCPK02 END AS DCPK02, ");
			sql.append(" DCPK03, ");
			sql.append(" DCPK04, ");
			sql.append(" DCPK05, ");
			sql.append(" DCPK06, ");
			sql.append(" DCPK07, ");
			sql.append(" DCPK08 ");
			
			sql.append(" FROM ");
			sql.append(" " + ttLibrary + ".DCPALL ");
			sql.append(" LEFT OUTER JOIN " + library + ".FCHACC ON ");
			sql.append(" EACONO=100 AND EADIVI='100' AND EAAITP=1 ");
			sql.append(" AND EAAICL=DCPK01 ");
			sql.append(" AND DCPK01<>'*' ");
			sql.append(" AND DCPK02='*' ");
			
			sql.append(" WHERE DCPTYP='V' ");
			
			sql.append(" AND DCPK00='BUDDEPT' ");
			sql.append(" AND DCPA01='" + inqBudget.getDepartment() + "' ");
			
			sql.append(" ORDER BY DCPK01, DCPK03, DCPK04, EAAITM ");
			
			return sql.toString();
		}
		
		private static String listBudgetDeparmentDefinition(InqBudget inqBudget) {
			
			StringBuffer sql = new StringBuffer();
			
			String ttLibrary = GeneralUtility.getTTLibrary(inqBudget.getEnvironment());
			String library = GeneralUtility.getLibrary(inqBudget.getEnvironment());
			
			sql.append(" SELECT  ");
			sql.append(" DCPK01,");
			sql.append(" DCPK02, ");
			sql.append(" DCPK03, ");
			sql.append(" DCPK04, ");
			sql.append(" DCPK05, ");
			sql.append(" DCPK06, ");
			sql.append(" DCPK07, ");
			sql.append(" DCPK08, ");
			sql.append(" DCPA01, ");
			sql.append(" DCPA02, ");
			sql.append(" DCPT40, ");
			sql.append(" DCPT80, ");
			sql.append(" DCPMSG, ");
			sql.append(" DCPN01, ");
			sql.append(" DCPN02, ");
			sql.append(" DCPN03, ");
			sql.append(" DCPN04, ");
			sql.append(" DCPN05 ");
			
			sql.append(" FROM ");
			sql.append(" " + ttLibrary + ".DCPALL ");
			
			sql.append(" WHERE DCPTYP='V' ");
			
			sql.append(" AND DCPK00='BUDDEPT' ");
			sql.append(" AND DCPA01='" + inqBudget.getDepartment() + "' ");
			
			sql.append(" ORDER BY DCPK01, DCPK02, DCPK03, DCPK04, DCPK05, DCPK06, DCPK07, DCPK08 ");
			
			return sql.toString();
		}
		
		
		private static String getForecastAndActuals(InqBudget inqBudget) {
			StringBuffer sql = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqBudget.getEnvironment());
			String ttLibrary = GeneralUtility.getTTLibrary(inqBudget.getEnvironment());
			
			int year = 2000 + Integer.parseInt(inqBudget.getBudgetNumber());
			String vsn = inqBudget.getBudgetVersion();
			
			sql.append(" SELECT \r");
			sql.append(" DIM1, DIM2, DIM3, DIM4, DIM5, DIM6, DIM7, \r");
			
			DecimalFormat df = new DecimalFormat("00");
			
			for (int i=1; i<=12; i++) {
				String per = df.format(i);
				sql.append(" SUM(ACT" + per + ") AS ACT" + per + ", \r");
			}
			
			for (int i=1; i<=12; i++) {
				String per = df.format(i);
				sql.append(" SUM(FCST" + per + ") AS FCST" + per + " ");
				if (i < 12) {
					sql.append(", \r");
				}
			}

			sql.append("\r");
			
			sql.append(" FROM ( \r");
			sql.append("  \r");
			
			sql.append(" SELECT \r");
			sql.append(" CASE WHEN DCPN01=1 THEN TRIM(RKBIT1) ELSE '' END AS DIM1, \r");
			sql.append(" CASE WHEN DCPN02=1 THEN TRIM(RKBIT2) ELSE '' END AS DIM2, \r");
			sql.append(" CASE WHEN DCPN03=1 THEN TRIM(RKBIT3) ELSE '' END AS DIM3, \r");
			sql.append(" CASE WHEN DCPN04=1 THEN TRIM(RKBIT4) ELSE '' END AS DIM4, \r");
			sql.append(" CASE WHEN DCPN05=1 THEN TRIM(RKBIT5) ELSE '' END AS DIM5, \r");
			
			sql.append(" CASE WHEN (TRIM(RKBIT1) LIKE '%98' OR TRIM(RKBIT1) LIKE '%99') THEN RKBIT6 ELSE '' END AS DIM6, \r");
			sql.append(" '' AS DIM7, \r");
			
			
			for (int i=1; i<=12; i++) {
				String per = df.format(i);
				sql.append(" SUM(CASE WHEN RVBVER=''    AND RVPERI=" + i + " THEN (RVACAD+RVACAC) ");
				sql.append(" ELSE 0 END) AS ACT" + per + ", \r");
			}
			
			for (int i=1; i<=12; i++) {
				String per = df.format(i);
				sql.append(" SUM(CASE WHEN RVBVER='" + vsn + "' AND RVPERI=" + i + " THEN (RVACAD+RVACAC) ");
				sql.append(" ELSE 0 END) AS FCST" + per + "");
				
				if (i < 12) {
					sql.append(", \r");
				}
			}
			
			sql.append("\r");
			
			sql.append(" FROM \r");
			sql.append(" " + library + ".FBAVAL \r");
			sql.append(" INNER JOIN " + library + ".FBAKEY ON \r");
			sql.append("       RVCONO=RKCONO AND (RKDIVI='" + inqBudget.getDivision() + "') \r");
			sql.append("       AND RKBAKY=8 \r");
			sql.append("       AND RVPTID=RKPTID \r");
			sql.append(" LEFT OUTER JOIN " + library + ".FCHACC AS DIM1 ON \r");
			sql.append("      DIM1.EACONO=100 AND DIM1.EADIVI='100' AND DIM1.EAAITP=1 AND DIM1.EAAITM=RKBIT1 \r");
			sql.append(" INNER JOIN " + ttLibrary + ".DCPALL ON \r");
			sql.append("       DCPTYP='V' \r");
			sql.append("       AND DCPA01='" + inqBudget.getDepartment() + "' \r");
			sql.append("       AND DCPK00='BUDDEPT' \r");
			sql.append("       AND (DCPK01=EAAICL OR DCPK01='*') \r");
			sql.append("       AND (DCPK02=RKBIT1 OR DCPK02='*') \r");
			sql.append("       AND (DCPK03=RKBIT2 OR DCPK03='*') \r");
			sql.append("       AND (DCPK04=RKBIT3 OR DCPK04='*') \r");
			sql.append("       AND (DCPK05=RKBIT4 OR DCPK05='*') \r");
			sql.append("       AND (DCPK06=RKBIT5 OR DCPK06='*') \r");
			sql.append("       AND (DCPK07=RKBIT6 OR DCPK07='*') \r");
			sql.append("       AND (DCPK08=RKBIT7 OR DCPK08='*') \r");

			sql.append(" WHERE RVCONO=" + inqBudget.getCompany() + " ");
			
			sql.append(" AND RVYEA4=" + year + " ");
			sql.append(" AND RVPERI>=1 AND RVPERI<=12 \r");
			
			sql.append(" GROUP BY ");
			sql.append(" RKBIT1, RKBIT2, RKBIT3, RKBIT4, RKBIT5, RKBIT6, ");
			sql.append(" DCPN01, DCPN02, DCPN03, DCPN04, DCPN05 \r");

			sql.append(" ) AS A \r");

			
			sql.append(" GROUP BY ");
			sql.append(" DIM1, DIM2, DIM3, DIM4, DIM5, DIM6, DIM7 \r");
			
			sql.append(" ORDER BY ");
			sql.append(" DIM1, DIM2, DIM3, DIM4, DIM5, DIM6, DIM7 \r");
			
			
			return sql.toString();
		}
		
		private static String listBudgetLines(InqBudget inqBudget) {
			StringBuffer sql = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqBudget.getEnvironment());
			String ttLibrary = GeneralUtility.getTTLibrary(inqBudget.getEnvironment());
			
			sql.append(" SELECT CONO, DIVI, BUNO, BVER, DIM1, DIM2, DIM3, DIM4, DIM5, DIM6, DIM7 ");
			sql.append(" FROM ( ");
			sql.append(" SELECT ");
			sql.append(" BCCONO AS CONO, ");
			sql.append(" BCDIVI AS DIVI, ");
			sql.append(" BCBUNO AS BUNO, ");
			sql.append(" BCBVER AS BVER, ");
			sql.append(" EAAICL AS AICL, ");
			sql.append(" BCAIT1 AS DIM1, ");
			sql.append(" BCAIT2 AS DIM2, ");
			sql.append(" BCAIT3 AS DIM3, ");
			sql.append(" BCAIT4 AS DIM4, ");
			sql.append(" BCAIT5 AS DIM5, ");
			sql.append(" BCAIT6 AS DIM6, ");
			sql.append(" BCAIT7 AS DIM7 ");
						
			sql.append(" FROM ");
			sql.append(" " + library + ".FBUDET ");
			sql.append(" LEFT OUTER JOIN " + library + ".FCHACC AS DIM1 ON ");
			sql.append("      DIM1.EACONO=BCCONO AND DIM1.EADIVI=BCDIVI ");
			sql.append("      AND DIM1.EAAITP=1 AND DIM1.EAAITM=BCAIT1 ");
			sql.append(" WHERE ");
			sql.append(" BCCONO=" + inqBudget.getCompany() + " ");
			sql.append(" AND BCDIVI='" + inqBudget.getDivision() + "' ");
			sql.append(" AND BCBUNO=" + inqBudget.getBudgetNumber() + " ");
			sql.append(" AND BCBVER='" + inqBudget.getBudgetVersion() + "' ");
			sql.append(" GROUP BY ");
			sql.append(" BCCONO, BCDIVI, BCBUNO, BCBVER, EAAICL, ");
			sql.append(" BCAIT1, BCAIT2, BCAIT3, BCAIT4, BCAIT5, BCAIT6, BCAIT7 ");
			sql.append(" ) AS A ");
			
			sql.append(" INNER JOIN " + ttLibrary + ".DCPALL ON ");
			sql.append(" DCPK00='BUDDEPT' ");
			sql.append(" AND DCPA01='" + inqBudget.getDepartment() + "' ");
			sql.append(" AND (DCPK01=AICL OR DCPK01='*') ");		//Account Group
			sql.append(" AND (DCPK02=DIM1 OR DCPK02='*') ");		//Account
			sql.append(" AND (DCPK03=DIM2 OR DCPK03='*') ");		//Facility
			sql.append(" AND (DCPK04=DIM3 OR DCPK04='*') ");		//Cost Center
			sql.append(" AND (DCPK05=DIM4 OR DCPK05='*') ");		//Product Group

			
			return sql.toString();
		}
		
		private static String listBudgetValues(InqBudget inqBudget) {
			StringBuffer sql = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqBudget.getEnvironment());
			String ttLibrary = GeneralUtility.getTTLibrary(inqBudget.getEnvironment());
			
			sql.append(" SELECT CONO, DIVI, BUNO, BVER, DIM1, DIM2, DIM3, DIM4, DIM5, DIM6, DIM7 ");
			sql.append(" FROM ( ");
			sql.append(" SELECT ");
			sql.append(" BCCONO AS CONO, ");
			sql.append(" BCDIVI AS DIVI, ");
			sql.append(" BCBUNO AS BUNO, ");
			sql.append(" BCBVER AS BVER, ");
			sql.append(" EAAICL AS AICL, ");
			sql.append(" BCAIT1 AS DIM1, ");
			sql.append(" BCAIT2 AS DIM2, ");
			sql.append(" BCAIT3 AS DIM3, ");
			sql.append(" BCAIT4 AS DIM4, ");
			sql.append(" BCAIT5 AS DIM5, ");
			sql.append(" BCAIT6 AS DIM6, ");
			sql.append(" BCAIT7 AS DIM7, ");
			
						
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '01' THEN BCBLAM ELSE 0 END) AS PER01, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '02' THEN BCBLAM ELSE 0 END) AS PER02, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '03' THEN BCBLAM ELSE 0 END) AS PER03, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '04' THEN BCBLAM ELSE 0 END) AS PER04, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '05' THEN BCBLAM ELSE 0 END) AS PER05, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '06' THEN BCBLAM ELSE 0 END) AS PER06, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '07' THEN BCBLAM ELSE 0 END) AS PER07, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '08' THEN BCBLAM ELSE 0 END) AS PER08, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '09' THEN BCBLAM ELSE 0 END) AS PER09, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '10' THEN BCBLAM ELSE 0 END) AS PER10, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '11' THEN BCBLAM ELSE 0 END) AS PER11, ");
			sql.append(" SUM(CASE WHEN RIGHT(BCBUPE,2) = '12' THEN BCBLAM ELSE 0 END) AS PER12 ");


			sql.append(" FROM ");
			sql.append(" " + library + ".FBUDET ");
			sql.append(" LEFT OUTER JOIN " + library + ".FCHACC AS DIM1 ON ");
			sql.append("      DIM1.EACONO=BCCONO AND DIM1.EADIVI=BCDIVI ");
			sql.append("      AND DIM1.EAAITP=1 AND DIM1.EAAITM=BCAIT1 ");
			sql.append(" WHERE ");
			sql.append(" BCCONO=" + inqBudget.getCompany() + " ");
			sql.append(" AND BCDIVI='" + inqBudget.getDivision() + "' ");
			sql.append(" AND BCBUNO=" + inqBudget.getBudgetNumber() + " ");
			sql.append(" AND BCBVER='" + inqBudget.getBudgetVersion() + "' ");
			sql.append(" GROUP BY ");
			sql.append(" BCCONO, BCDIVI, BCBUNO, BCBVER, EAAICL, ");
			sql.append(" BCAIT1, BCAIT2, BCAIT3, BCAIT4, BCAIT5, BCAIT6, BCAIT7 ");
			sql.append(" ) AS A ");
			
			sql.append(" INNER JOIN " + ttLibrary + ".DCPALL ON ");
			sql.append(" DCPK00='BUDDEPT' ");
			sql.append(" AND DCPA01='" + inqBudget.getDepartment() + "' ");
			sql.append(" AND (DCPK01=AICL OR DCPK01='*') ");		//Account Group
			sql.append(" AND (DCPK02=DIM1 OR DCPK02='*') ");		//Account
			sql.append(" AND (DCPK03=DIM2 OR DCPK03='*') ");		//Facility
			sql.append(" AND (DCPK04=DIM3 OR DCPK04='*') ");		//Cost Center
			sql.append(" AND (DCPK05=DIM4 OR DCPK05='*') ");		//Product Group

			
			return sql.toString();
		}
		
		private static String listBudgets(InqBudget inqBudget) {
			StringBuffer sql = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqBudget.getEnvironment());
			
			sql.append(" SELECT BDBUNO, BDBVER, BDTX40 ");
			
			sql.append(" FROM " + library + ".FBUDEF ");
			
			sql.append(" WHERE ");
			sql.append(" BDCONO=" + inqBudget.getCompany() + " ");
			sql.append(" AND BDDIVI='" + inqBudget.getDivision() + "' ");
			sql.append(" AND BDBLCC=0 ");

			sql.append(" ORDER BY BDBUNO, BDBVER ");
			
			return sql.toString();
		}
		
		private static String listDeparments(InqBudget inqBudget) {
			StringBuffer sql = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqBudget.getEnvironment());
			
			sql.append(" SELECT DCPA01 ");
			
			sql.append(" FROM DBTST.DCPALL ");
			
			sql.append(" WHERE ");
			sql.append(" DCPTYP='V' ");
			sql.append(" AND DCPK00='BUDDEPT' ");

			sql.append(" GROUP BY DCPA01 ");
			sql.append(" ORDER BY DCPA01 ");
			
			return sql.toString();
		}
		
	}
	
	private static class LoadFields {
		
		private static BUS100MIAddBudgetLines adjustedForecastLines(InqBudget inqBudget, ResultSet rs) throws Exception {
			BUS100MIAddBudgetLines add = new BUS100MIAddBudgetLines();
			
			
			add.setEnvironment(inqBudget.getEnvironment());
			add.setAuthorization(inqBudget.getAuthorization());
			
			add.setCompany(inqBudget.getCompany());
			add.setDivision(inqBudget.getDivision());
			add.setBudgetNumber(inqBudget.getBudgetNumber());
			add.setBudgetVersion(inqBudget.getBudgetVersion());
			
			add.setDimension1(rs.getString("DIM1").trim());
			add.setDimension2(rs.getString("DIM2").trim());
			add.setDimension3(rs.getString("DIM3").trim());
			add.setDimension4(rs.getString("DIM4").trim());
			
			if (inqBudget.getDepartment().equals("MAINT")) {
				// for maintenance, hard code dim 5
				if (rs.getString("DIM1").trim().equals("64220")) {
					//labor
					add.setDimension5("B03");
				}
				if (rs.getString("DIM1").trim().equals("64550")) {
					// parts
					add.setDimension5("B04");
				}
				
			} else {
				add.setDimension5(rs.getString("DIM5").trim());
			}
			
			
			add.setDimension6(rs.getString("DIM6").trim());
			add.setDimension7(rs.getString("DIM7").trim());
			
			
			
			
			if (inqBudget.getForecastMiss().equals("DEC")) {
				
				//Calculate forecast miss and adjusted forecast month values
				BigDecimal act04 = BigDecimal.ZERO;
				try {
					act04 = rs.getBigDecimal("ACT04");
				} catch (Exception e) {}
				
				BigDecimal fcst04 = BigDecimal.ZERO;
				try {
					fcst04 = rs.getBigDecimal("FCST04");
				} catch (Exception e) {}
				
				BigDecimal fcst05 = BigDecimal.ZERO;
				try {
					fcst05 = rs.getBigDecimal("FCST05");
				} catch (Exception e) {}
				
				
				BigDecimal adjFcst05 = BigDecimal.ZERO;
				adjFcst05 = fcst04.subtract(act04);
				adjFcst05 = fcst05.add(adjFcst05);
				
				add.setAmount1(rs.getString("ACT01").trim());
				add.setAmount2(rs.getString("ACT02").trim());
				add.setAmount3(rs.getString("ACT03").trim());
				add.setAmount4(rs.getString("ACT04").trim());
				add.setAmount5(adjFcst05.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				add.setAmount6(rs.getString("FCST06").trim());
				add.setAmount7(rs.getString("FCST07").trim());
				add.setAmount8(rs.getString("FCST08").trim());
				add.setAmount9(rs.getString("FCST09").trim());
				add.setAmount10(rs.getString("FCST10").trim());
				add.setAmount11(rs.getString("FCST11").trim());
				add.setAmount12(rs.getString("FCST12").trim());
				
			}
			
			if (inqBudget.getForecastMiss().equals("APR")) {
				
				//Calculate forecast miss and adjusted forecast month values
				BigDecimal act08 = BigDecimal.ZERO;
				try {
					act08 = rs.getBigDecimal("ACT08");
				} catch (Exception e) {}
				
				BigDecimal fcst08 = BigDecimal.ZERO;
				try {
					fcst08 = rs.getBigDecimal("FCST08");
				} catch (Exception e) {}
				
				BigDecimal fcst09 = BigDecimal.ZERO;
				try {
					fcst09 = rs.getBigDecimal("FCST09");
				} catch (Exception e) {}
				
				
				BigDecimal adjFcst09 = BigDecimal.ZERO;
				adjFcst09 = fcst08.subtract(act08).add(fcst09);
				
				
				add.setAmount1(rs.getString("ACT01").trim());
				add.setAmount2(rs.getString("ACT02").trim());
				add.setAmount3(rs.getString("ACT03").trim());
				add.setAmount4(rs.getString("ACT04").trim());
				add.setAmount5(rs.getString("ACT05").trim());
				add.setAmount6(rs.getString("ACT06").trim());
				add.setAmount7(rs.getString("ACT07").trim());
				add.setAmount8(rs.getString("ACT08").trim());
				
				//TODO temporary fix only
				//add.setAmount9(adjFcst09.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				add.setAmount9(rs.getString("FCST09").trim());
				
				add.setAmount10(rs.getString("FCST10").trim());
				add.setAmount11(rs.getString("FCST11").trim());
				add.setAmount12(rs.getString("FCST12").trim());
				
			}
			return add;
		}
		
		private static BUS100MIAddBudgetLines originalForecastLines(InqBudget inqBudget, ResultSet rs) throws Exception {
			BUS100MIAddBudgetLines add = new BUS100MIAddBudgetLines();
						
			add.setEnvironment(inqBudget.getEnvironment());
			add.setAuthorization(inqBudget.getAuthorization());
			
			add.setCompany(inqBudget.getCompany());
			add.setDivision(inqBudget.getDivision());
			add.setBudgetNumber(inqBudget.getBudgetNumber());
			add.setBudgetVersion(inqBudget.getBudgetVersion());
			
			add.setDimension1(rs.getString("DIM1").trim());
			add.setDimension2(rs.getString("DIM2").trim());
			add.setDimension3(rs.getString("DIM3").trim());
			add.setDimension4(rs.getString("DIM4").trim());

			if (inqBudget.getDepartment().equals("MAINT")) {
				// for maintenance, hard code dim 5
				if (rs.getString("DIM1").trim().equals("64220")) {
					//labor
					add.setDimension5("B03");
				}
				if (rs.getString("DIM1").trim().equals("64550")) {
					// parts
					add.setDimension5("B04");
				}
				
			} else {
				add.setDimension5(rs.getString("DIM5").trim());
			}
			
			add.setDimension6(rs.getString("DIM6").trim());
			add.setDimension7(rs.getString("DIM7").trim());
			
			add.setAmount1(rs.getString("FCST01").trim());
			add.setAmount2(rs.getString("FCST02").trim());
			add.setAmount3(rs.getString("FCST03").trim());
			add.setAmount4(rs.getString("FCST04").trim());
			add.setAmount5(rs.getString("FCST05").trim());
			add.setAmount6(rs.getString("FCST06").trim());
			add.setAmount7(rs.getString("FCST07").trim());
			add.setAmount8(rs.getString("FCST08").trim());
			add.setAmount9(rs.getString("FCST09").trim());
			add.setAmount10(rs.getString("FCST10").trim());
			add.setAmount11(rs.getString("FCST11").trim());
			add.setAmount12(rs.getString("FCST12").trim());
			
			return add;
		}
		
	}

}
