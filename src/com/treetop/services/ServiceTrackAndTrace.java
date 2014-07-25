package com.treetop.services;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.treetop.app.transaction.InqTransaction;

@RunWith(Suite.class)
public class ServiceTrackAndTrace {

	private static Hashtable<String, String> programs = null;
	
	static {
		//Mapping request type to RPG program
		programs = new Hashtable<String, String>();
		
		programs.put("inqSingleIngredientForward",	"INC152");
		programs.put("inqProductionDayForwardOS", 	"INC172");
		programs.put("inqProductionDayForward", 	"INC157");
		programs.put("inqProductionDayBack", 		"INC163");
		programs.put("inqFruitToShipping", 			"INC166");
		
		
	}
	
	/**
	 * This returns HTML for spool file data for screen presentation
	 * 
	 * @param InqTransaction
	 * @return Vector<String>
	 * @throws Exception
	 */

	public static void submitTrackAndTrace(InqTransaction it) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<String> spooledData = new Vector<String>();

		if (it.getInqEnvironment() == null || it.getInqEnvironment().trim().equals("")) {
			it.setInqEnvironment("PRD");
		}


		AS400 system = ServiceConnection.getAS400();
		

		try {
			
			submitTrackAndTrace(it, system);

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			try {
				ServiceConnection.returnAS400(system);
			} catch (Exception e) {
				System.out.println("Error disconnecting AS400 system:  " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append(" @ com.treetop.Services.");
				throwError.append("ServiceTransaction.");
				throwError.append("submitTrackAndTrace(InqTransaction). ");
				throw new Exception(throwError.toString());
			}
		}

	}

	/**
	 * Builds spooled data from RPG program.
	 * 
	 * @param InqTransaction
	 * @param AS400
	 * @return Vector
	 * @throws Exception
	 */

	private static void submitTrackAndTrace(InqTransaction it, AS400 system) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<String> errorMessage = new Vector<String>();
		
		

		ProgramCall pgm = new ProgramCall(system);
		String pgmLib = "MOVEX";
		String pgmName = programs.get(it.getRequestType());
		ProgramParameter[] parmList = null;

		try {
			if (it.requestType.equals("inqProductionDayForwardOS")) {
				parmList = new ProgramParameter[2];
				parmList[0] = new ProgramParameter(new AS400Text(30).toBytes(it.getInqManufactureLabel()));
				parmList[1] = new ProgramParameter(10);
			}

			if (it.requestType.equals("inqSingleIngredientForward")) {
				parmList = new ProgramParameter[3];
				parmList[0] = new ProgramParameter(new AS400Text(15).toBytes(it.getInqItem()));
				parmList[1] = new ProgramParameter(new AS400Text(20).toBytes(it.getInqLot()));
				parmList[2] = new ProgramParameter(10);
			}

			if (it.getRequestType().equals("inqProductionDayBack")
					|| it.getRequestType().equals("inqProductionDayForward")
					|| it.getRequestType().equals("inqFruitToShipping")) {

				if (!it.getInqOrder().equals("")) {
					it.setInqFacility("");
					it.setInqDateYYYYMMDD("");
					it.setInqItem("");
				}

				parmList = new ProgramParameter[5];
				parmList[0] = new ProgramParameter(new AS400Text(3).toBytes(it.getInqFacility()));
				parmList[1] = new ProgramParameter(new AS400Text(8).toBytes(it.getInqDateYYYYMMDD()));
				parmList[2] = new ProgramParameter(new AS400Text(15).toBytes(it.getInqItem()));
				parmList[3] = new ProgramParameter(new AS400Text(10).toBytes(it.getInqOrder()));
				parmList[4] = new ProgramParameter(10);
			}

			String pgmPath = "/QSYS.LIB/" + pgmLib + ".LIB/" + pgmName + ".PGM";
			pgm.setProgram(pgmPath, parmList);

			if (pgm.run() != true) {
				System.out.println("Program failed!");
				throwError.append("Request not Submitted:  ");
				AS400Message[] messagelist = pgm.getMessageList();
				
				errorMessage.addElement("<h1>Error submitting Track and Trace Report.</h1>" + "\r");
				errorMessage.addElement("<h3>Please contact the IS Help Desk at x1425 with the following information.</h3>" + "\r");
				
				errorMessage.addElement("<p>Requesting:\t\t" + it.getRequestType() + "</p>\r");
				
				for (AS400Message message : pgm.getMessageList()) {
					System.out.println("Error:");
					System.out.println("\tDefault reply= " + message.getDefaultReply());
					System.out.println("\tFile Name= " + message.getFileName());
					System.out.println("\tHelp= " + message.getHelp());
					System.out.println("\tID= " + message.getID());
					System.out.println("\tLibrary Name= " + message.getLibraryName());
					System.out.println("\tPath= " + message.getPath());
					System.out.println("\tSeverity= " + message.getSeverity());
					System.out.println("\tText= " + message.getText());
					System.out.println("\tType= " + message.getType());
					System.out.println("\tDate= " + message.getDate());
					errorMessage.addElement("<div>" + message.getText() + "<br>\r");
					errorMessage.addElement(message.getHelp() + "</div>\r");
				}	
				
				
			} else {

				String fileName = "";
				if (it.requestType.equals("inqProductionDayForwardOS")) {
					AS400Text rtntext = new AS400Text(10, system);
					fileName = (String) rtntext.toObject(parmList[1].getOutputData());

				} else if (it.requestType.equals("inqSingleIngredientForward")) {
					AS400Text rtntext = new AS400Text(10, system);
					fileName = (String) rtntext.toObject(parmList[2].getOutputData());

				} else {
					AS400Text rtntext = new AS400Text(10, system);
					fileName = (String) rtntext.toObject(parmList[4].getOutputData());
				}
					
				File f = ServiceSpoolFile.convertSpoolFileToPdf(it.getInqEnvironment(), fileName, true);
				it.getBean().setSpoolFileOutput(f);

			}
			
			it.getBean().setErrorMessage(errorMessage.toString());

		} catch (Exception e) {
			throwError.append(e);
			System.out.println(throwError.toString());
		} finally {
			if (!throwError.toString().equals("")) {
				throw new Exception(throwError.toString());
			}
		}

	}
	
	public static class UnitTests {
		private static InqTransaction inq = null;

		@Before
		public void setup() {
			inq = new InqTransaction();
			inq.setInqOrder("1004913");
		}
		
		@After
		public void tearDown() {
			inq.getBean().getSpoolFileOutput().delete();
		}

		@Test
		public void testProductionDayForward() {
			inq.setRequestType("inqProductionDayForward");
			try {
				submitTrackAndTrace(inq);
			} catch (Exception e) {
				fail(e.toString());
			}
		}
		
		@Test
		public void inqProductionDayForwardOS() {
			inq.setRequestType("inqProductionDayBack");
			try {
				submitTrackAndTrace(inq);
			} catch (Exception e) {
				fail(e.toString());
			}
		}
		
		@Test
		public void testProductionDayBack() {
			inq.setRequestType("inqProductionDayBack");
			try {
				submitTrackAndTrace(inq);
			} catch (Exception e) {
				fail(e.toString());
			}
		}
		
		@Test
		public void testFruitToShipping() {
			inq.setRequestType("inqFruitToShipping");
			try {
				submitTrackAndTrace(inq);
			} catch (Exception e) {
				fail(e.toString());
			}
		}
		
		@Test
		public void testSingleIngredientForward() {
			inq.setRequestType("inqSingleIngredientForward");
			try {
				submitTrackAndTrace(inq);
			} catch (Exception e) {
				fail(e.toString());
			}
		}

	}
	
}
