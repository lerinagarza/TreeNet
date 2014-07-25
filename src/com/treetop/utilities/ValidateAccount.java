package com.treetop.utilities;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.*;
import com.treetop.businessobjects.*;
import com.treetop.services.ServiceConnection;
import com.treetop.viewbeans.CommonRequestBean;


public class ValidateAccount {

	private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat HHMMSS = new SimpleDateFormat("HHmmss");
	
	private static final String lib = "M3DJD";
	
	
	public static void main(String[] args) {
		CommonRequestBean crb = new CommonRequestBean();
		crb.setEnvironment("TST");
		crb.setCompanyNumber("100");
		crb.setDivisionNumber("100");
		
		AccountString acctStr = new AccountString();
		AccountID dim1 = new AccountID("1","40100");
		AccountID dim2 = new AccountID("2","100");
		AccountID dim3 = new AccountID("3","101");
		acctStr.setDimension1(dim1);
		acctStr.setDimension2(dim2);
		acctStr.setDimension3(dim3);
		
		acctStr = validateBudAccountString(crb, acctStr);

	}

	private static String buildSQL(String requestType, CommonRequestBean crb) {
		StringBuffer sql = new StringBuffer();
		String env = (String) crb.getEnvironment();
		String company = (String) crb.getCompanyNumber();
		String division = (String) crb.getDivisionNumber();
		
		if (requestType.equals("validateAccountID")) {
			String dimension = (String) crb.getIdLevel1();
			String accountID = (String) crb.getIdLevel2();
			sql.append(" SELECT eaaitp, eaaitm, eatx15, eatx40");
			
			if (dimension.equals("1")) {
				sql.append(" , eaaicl, cttx15, cttx40, ");
				sql.append(" eaacr2, eaacr3, eaacr4, eaacr5, eaacr6, eaacr7,");
				sql.append(" eaacb2, eaacb3, eaacb4, eaacb5, eaacb6, eaacb7");
			}
			
			sql.append(" FROM " + lib + env + ".fchacc");
			
			if (dimension.equals("1")) {
				sql.append(" LEFT OUTER JOIN " + lib + env + ".csytab ON");
				sql.append(" eacono=ctcono and eadivi=ctdivi and ctstco='AICL' and eaaicl=left(ctstky,3)");
			}
			
			sql.append(" WHERE eacono=" + company + " and eadivi='" + division + "' and ealccd=0");
			sql.append(" and eaaitp=" + dimension);
			sql.append(" and eaaitm='" + accountID + "'");
		}
		
		return sql.toString();
	}
	
	private static Vector loadFields(String requestType, ResultSet rs) {
		Vector returnValue = new Vector();
		
		if (requestType.equals("validateAccountID")) {
			AccountID accountID = new AccountID();
			try {

				accountID.setDimension(rs.getString("EAAITP").trim());
				accountID.setAccountID(rs.getString("EAAITM").trim());
				accountID.setAccountDescriptionShort(rs.getString("EATX15").trim());
				accountID.setAccountDescriptionLong(rs.getString("EATX40").trim());

				if (rs.getString("EAAITP").equals("1")) {
					accountID.setAccountGroup(rs.getString("EAAICL").trim());
					accountID.setAccountGroupDescriptionShort(rs.getString("CTTX15").trim());
					accountID.setAccountGroupDescriptionLong(rs.getString("CTTX40").trim());

					accountID.setCrossReferenceBudDim2(rs.getString("EAACB2").trim());
					accountID.setCrossReferenceBudDim3(rs.getString("EAACB3").trim());
					accountID.setCrossReferenceBudDim4(rs.getString("EAACB4").trim());
					accountID.setCrossReferenceBudDim5(rs.getString("EAACB5").trim());
					accountID.setCrossReferenceBudDim6(rs.getString("EAACB6").trim());
					accountID.setCrossReferenceBudDim7(rs.getString("EAACB7").trim());

					accountID.setCrossReferenceGLDim2(rs.getString("EAACR2").trim());
					accountID.setCrossReferenceGLDim3(rs.getString("EAACR3").trim());
					accountID.setCrossReferenceGLDim4(rs.getString("EAACR4").trim());
					accountID.setCrossReferenceGLDim5(rs.getString("EAACR5").trim());
					accountID.setCrossReferenceGLDim6(rs.getString("EAACR6").trim());
					accountID.setCrossReferenceGLDim7(rs.getString("EAACR7").trim());
				}
				accountID.setValid(true);

			} catch (Exception e) {

			}
			returnValue.addElement(accountID);
		}

		return returnValue;
	}
	
	public static AccountID validateAccountID(CommonRequestBean crb, AccountID accountID) {
		StringBuffer throwError = new StringBuffer();

		Connection conn 		= null;
		
		try {
			conn = ServiceConnection.getConnectionStack15();
			accountID = validateAccountID(conn, crb, accountID);
			if (!accountID.isValid()) {
				accountID.setError("Accounting ID " + accountID.getAccountID() + " does not exist or is blocked.");
			}
		} catch (Exception e ){
			System.out.println("Error validateAccountID()  " + e);
		} finally {
			try {
				ServiceConnection.returnConnectionStack15(conn);
			} catch (Exception e) {}
		}
		
		return accountID;
	}
	private static AccountID validateAccountID(Connection conn, CommonRequestBean crb, AccountID accountID)
	throws Exception {
		StringBuffer throwError = new StringBuffer();
		
		Statement stmt = null;
		ResultSet rs = null;
		String requestType = "validateAccountID";
		
		crb.setIdLevel1(accountID.getDimension());
		crb.setIdLevel2(accountID.getAccountID());
		
		String sql = buildSQL(requestType, crb);
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		
		try {
			if (rs.next()) {
				Vector validatedAccount = loadFields(requestType, rs); 
				accountID = (AccountID) validatedAccount.elementAt(0);
			}
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (Exception e) {}
			if (!throwError.toString().trim().equals("")) {
				throw new Exception(throwError.toString());
			}
		}
		
		
		return accountID;
	}
	
	public static AccountString validateBudAccountString(CommonRequestBean crb, AccountString acctStr) {
		StringBuffer throwError = new StringBuffer();

		Connection conn 		= null;
		
		try {
			conn = ServiceConnection.getConnectionStack15();
			acctStr = validateAccountString(conn, crb, "BUD", acctStr);
		} catch (Exception e ){
			System.out.println("Error validateAccountID()  " + e);
		} finally {
			try {
				ServiceConnection.returnConnectionStack15(conn);
			} catch (Exception e) {}
		}
		
		return acctStr;
	}
	public static AccountString validateGLAccountString(CommonRequestBean crb, AccountString acctStr) {
		StringBuffer throwError = new StringBuffer();

		Connection conn 		= null;
		
		try {
			conn = ServiceConnection.getConnectionStack15();
			acctStr = validateAccountString(conn, crb, "GL", acctStr);
		} catch (Exception e ){
			System.out.println("Error validateAccountID()  " + e);
		} finally {
			try {
				ServiceConnection.returnConnectionStack15(conn);
			} catch (Exception e) {}
		}
		
		return acctStr;
	}
	
	
	private static AccountString validateAccountString(Connection conn, CommonRequestBean crb, String checkType, AccountString acctStr) {
		AccountID dim1 = acctStr.getDimension1();
		AccountID dim2 = acctStr.getDimension2();
		AccountID dim3 = acctStr.getDimension3();
		AccountID dim4 = acctStr.getDimension4();
		AccountID dim5 = acctStr.getDimension5();
		AccountID dim6 = acctStr.getDimension6();
		AccountID dim7 = acctStr.getDimension7();
		
		try {
			dim1 = validateAccountID(crb, acctStr.getDimension1());

			String crossReferenceDim2 = "";
			String crossReferenceDim3 = "";
			String crossReferenceDim4 = "";
			String crossReferenceDim5 = "";
			String crossReferenceDim6 = "";
			String crossReferenceDim7 = "";

			if (checkType.equals("GL")) {
				crossReferenceDim2 = dim1.getCrossReferenceGLDim2();
				crossReferenceDim3 = dim1.getCrossReferenceGLDim3();
				crossReferenceDim4 = dim1.getCrossReferenceGLDim4();
				crossReferenceDim5 = dim1.getCrossReferenceGLDim5();
				crossReferenceDim6 = dim1.getCrossReferenceGLDim6();
				crossReferenceDim7 = dim1.getCrossReferenceGLDim7();
			}
			if (checkType.equals("BUD")) {
				crossReferenceDim2 = dim1.getCrossReferenceBudDim2();
				crossReferenceDim3 = dim1.getCrossReferenceBudDim3();
				crossReferenceDim4 = dim1.getCrossReferenceBudDim4();
				crossReferenceDim5 = dim1.getCrossReferenceBudDim5();
				crossReferenceDim6 = dim1.getCrossReferenceBudDim6();
				crossReferenceDim7 = dim1.getCrossReferenceBudDim7();
			}

			if (dim1.isValid()) {
				crb.setIdLevel3(dim1.getAccountID());
				dim2 = accountCrossReferenceCheck(conn, crb, dim2, crossReferenceDim2);
				dim3 = accountCrossReferenceCheck(conn, crb, dim3, crossReferenceDim3);
				dim4 = accountCrossReferenceCheck(conn, crb, dim4, crossReferenceDim4);
				dim5 = accountCrossReferenceCheck(conn, crb, dim5, crossReferenceDim5);
				dim6 = accountCrossReferenceCheck(conn, crb, dim6, crossReferenceDim6);
				dim7 = accountCrossReferenceCheck(conn, crb, dim7, crossReferenceDim7);
				
				if (dim1.isValid() && dim2.isValid() && dim3.isValid() && dim4.isValid() &&
					dim5.isValid() && dim6.isValid() && dim7.isValid()) {
					
					
					acctStr.setDimension1(dim1);
					acctStr.setDimension2(dim2);
					acctStr.setDimension3(dim3);
					acctStr.setDimension4(dim4);
					acctStr.setDimension5(dim5);
					acctStr.setDimension6(dim6);
					acctStr.setDimension7(dim7);
					
					acctStr.setValid(true);
				} else {
					StringBuffer error = new StringBuffer();
					if (!dim1.isValid()) {
						error.append("Dim 1: ");
						error.append(dim1.getError() + "  ");
					}
					if (!dim2.isValid()) {
						error.append("Dim 2: ");
						error.append(dim2.getError() + "  ");
					}
					if (!dim3.isValid()) {
						error.append("Dim 3: ");
						error.append(dim3.getError() + "  ");
					}
					if (!dim4.isValid()) {
						error.append("Dim 4: ");
						error.append(dim4.getError() + "  ");
					}
					if (!dim5.isValid()) {
						error.append("Dim 5: ");
						error.append(dim5.getError() + "  ");
					}
					if (!dim6.isValid()) {
						error.append("Dim 6: ");
						error.append(dim6.getError() + "  ");
					}
					if (!dim7.isValid()) {
						error.append("Dim 7: ");
						error.append(dim7.getError());
					}
					acctStr.setError(error.toString().trim());
				}
			} else {
				acctStr.setError("Dim 1: Accounting ID " + dim1.getAccountID() + " does not exist or is blocked.");
			}
		} catch (Exception e ){
			
		}
		return acctStr;
	}
	
	private static AccountID accountCrossReferenceCheck(Connection conn, CommonRequestBean crb, AccountID accountID, String referenceType) {
		//No check required
		if (referenceType.equals("0")) {
			accountID.setValid(true);
		}
		
		//Mandatory field. The value you enter need not be defined as an accounting ID.
		if (referenceType.equals("1")) {
			if (!accountID.getAccountID().trim().equals("")) {
				accountID.setValid(true);
			} else {
				accountID.setError("Dimension " + accountID.getDimension() + " is required for Account " + crb.getIdLevel1() + ".");
			}
		}
		
		//Mandatory field. The value you enter need not be defined as an accounting ID. 
		//It must, however, be within the interval entered in the account cross reference.
		if (referenceType.equals("2")) {
			//not configured
			accountID.setError("Account Cross Reference (ACR) Rule '2' is not configured for use.  Use CRS630 to verify ACR setup.");
		}
		
		//Mandatory field. The accounting ID you enter must be defined as an accounting ID.
		if (referenceType.equals("3")) {
			if (!accountID.getAccountID().trim().equals("")) {
					accountID = validateAccountID(crb, accountID);
			} else {
				accountID.setError("Dimension " + accountID.getDimension() + " is required for Account " + crb.getIdLevel3() +
						" and must be a valid Accounting Identity");
			}
		}
		
		//Mandatory field. The accounting ID you enter must be defined as an accounting ID as 
		//well as be within the interval entered in the account cross reference.
		if (referenceType.equals("4")) {
			//not configured
			accountID.setError("Account Cross Reference (ACR) Rule '4' is not configured for use.  Use CRS630 to verify ACR setup.");
		}
		
		//May not be entered.
		if (referenceType.equals("5")) {
			if (accountID.getAccountID().trim().equals("")) {
				accountID.setValid(true);
			} else {
				accountID.setError("Dimension " + accountID.getDimension() + " may not be entered for Account " + crb.getIdLevel3() + ".");
			}
		}
		
		//Optional field. If you enter a value, it need not be defined as an accounting ID,
		//but it must be within the interval defined in the account cross reference.
		if (referenceType.equals("6")) {
			//not configured
			accountID.setError("Account Cross Reference (ACR) Rule '6' is not configured for use.  Use CRS630 to verify ACR setup.");
		}
		
		
		//Optional field. If you enter an accounting ID it must be defined as an accounting ID.
		if (referenceType.equals("7")) {
			if (accountID.getAccountID().trim().equals("")) {
				accountID.setValid(true);
			} else {
					accountID = validateAccountID(crb, accountID);
				if (!accountID.isValid()) {
					accountID.setError("Accounting Identity " + accountID.getAccountID() + " is not valid.");
				}
			}
		}
		
		//Optional field. If you enter an accounting ID it must be defined as accounting ID, 
		//as well as be within the interval defined in the account cross reference.
		if (referenceType.equals("8")) {
			//not configured
			accountID.setError("Account Cross Reference (ACR) Rule '8' is not configured for use.  Use CRS630 to verify ACR setup.");
		}

		return accountID;
	}
}

