package com.treetop.servlets;

import com.treetop.data.*;
import com.treetop.*;
import java.util.*;
import java.math.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
/**
 * This Servlet will control which jsp's get thrown,
 *     example,  inqRandDFormula.jsp
 *				 listRandDFormula.jsp
 *               dtlRandDFormula.jsp
 *               updRandDFormula.jsp
 *    
 * Creation date: (6/17/2003 9:57:57 AM)
 * @author:  Teri Walton
 *
 *  PARAMETERS used (sent into) this Servlet
 *    "msg"         = Message sent for display onto the page
 *    "requestType" = Valid Request Type's
 *                	  inq, 
 *					  list, (List of Parameters to narrow the list displayed)
 *                    detail, (A Parameter(Formula Number))
 *                    add, 
 *					  addfinish, (List of parameters to add the record)
 *                    copy, (A Paramter(Formula Number to be copied from))
 *                    update, (A Parameter(Formula you wish to change/update))
 *					  updatefinish, (List of Parameters for changing the record)
 *                    delete, (A Parameter(Formula Number to be Deleted))
 *        default will be inq.
 *    All other parameters will be defined within the method's
 */
public class CtlRandDFormula extends javax.servlet.http.HttpServlet {

	private String       requestType;
	private String       message;
	private String[]     generalInfo;
	private Integer      formulaNumber;
	private RandDFormula chosenFormula;

/**
 * Add a Formula using the RandDFormula Class.
 * Creation date: (6/20/2003 3:59:40 PM)
 */
private void addUpdateFormula(javax.servlet.http.HttpServletRequest request,
  		  				      javax.servlet.http.HttpServletResponse response)  
{
	try
	{
		int formulaNumber              = Integer.parseInt(request.getParameter("formulaNumber"));
		String formulatype             = "RD";
		String name                    = request.getParameter("formulaName");
		String technician              = request.getParameter("tech");
		String variety                 = request.getParameter("variety");
		String preservative            = request.getParameter("preservative");
		
        String dateArray[]             = SystemDate.getSystemDate();
        java.sql.Date currentDate      = java.sql.Date.valueOf(dateArray[7]);
 		java.sql.Time currentTime      = java.sql.Time.valueOf(dateArray[8]);
 	 	String currentUser             = com.treetop.SessionVariables.getSessionttiProfile(request, response);
		String comment                 = request.getParameter("comment");
		// Detail Information
		int lineCount = 0;
		BigDecimal totalQty    = new BigDecimal("0");
		BigDecimal totalDryWgt = new BigDecimal("0");
		BigDecimal lineCost    = new BigDecimal("0");
		BigDecimal totalCost   = new BigDecimal("0");
		BigDecimal zero        = new BigDecimal("0");

		//New Lines. 15 Lines...
		for (int x=1; x < 16; x++)
		{
 			if (request.getParameter(x + "delete") == null &&
	 			request.getParameter(x + "ingDesc") != null && 
				!request.getParameter(x + "ingDesc").equals(""))
			{
				lineCount = lineCount + 1;
				
				String testQty = request.getParameter(x + "qty");
				if (testQty == null || testQty.length() == 0)
				   testQty = "0";
 				totalQty = totalQty.add(new BigDecimal(testQty));

 				String testDryWgt = request.getParameter(x + "drywgt");
 				if (testDryWgt == null || testDryWgt.length() == 0)
 				   testDryWgt = "0";
				totalDryWgt = totalDryWgt.add(new BigDecimal(testDryWgt));
			}
		}
		
		Integer[] sequenceNumber       = new Integer[lineCount];
		String[] supplier              = new String[lineCount];
		String[] supplierCode          = new String[lineCount];
		String[] ingredientDescription = new String[lineCount];
		String[] resource              = new String[lineCount];
		BigDecimal[] quantity          = new BigDecimal[lineCount];
		String[] unitOfMeasure         = new String[lineCount];
		BigDecimal[] formulaPercent    = new BigDecimal[lineCount];
		BigDecimal[] dryWeight         = new BigDecimal[lineCount];
		BigDecimal[] weightPercent     = new BigDecimal[lineCount];
		BigDecimal[] costPerPound      = new BigDecimal[lineCount];

		lineCount = -1;
		for (int x = 1; x < 16; x++)
		{
			if (request.getParameter(x + "delete") == null &&
				request.getParameter(x + "ingDesc") != null && 
				!request.getParameter(x + "ingDesc").equals(""))
			{
				lineCount = lineCount + 1;

				String testSequenceNumber = request.getParameter(x + "seq");
				if (testSequenceNumber == null || testSequenceNumber.length() == 0)
				   testSequenceNumber = "0";
				sequenceNumber[lineCount]        = new Integer(testSequenceNumber);			
	
				supplier[lineCount]              = request.getParameter(x + "supplier");
				supplierCode[lineCount]          = request.getParameter(x + "supplierCode");
				ingredientDescription[lineCount] = request.getParameter(x + "ingDesc");
				resource[lineCount]  			 = request.getParameter(x + "resource");

				String testQuantity = request.getParameter(x + "qty");
				if (testQuantity == null || testQuantity.length() == 0)
				   testQuantity = "0";
				quantity[lineCount]              = new BigDecimal(testQuantity);
				unitOfMeasure[lineCount]         = request.getParameter(x + "uom");

				int testQty = totalQty.compareTo(zero);
				if (testQty != 0)
				{
					BigDecimal testOne = new BigDecimal(0);
					testOne = quantity[lineCount].divide(totalQty, 5, BigDecimal.ROUND_HALF_UP);
					
					formulaPercent[lineCount]        = quantity[lineCount].divide(totalQty, 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				}
				else
				{
					formulaPercent[lineCount] = zero;
				}
				
				String testDryWgt = request.getParameter(x + "drywgt");
				if (testDryWgt == null || testDryWgt.length() == 0)
				   testDryWgt = "0";
				dryWeight[lineCount]             = new BigDecimal(testDryWgt);
				testQty = totalDryWgt.compareTo(zero);
				if (testQty != 0)
				{
					weightPercent[lineCount]         = dryWeight[lineCount].divide(totalDryWgt, 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
				}
				else
				{
					weightPercent[lineCount] = zero;
				}

				String testCostPerLb = request.getParameter(x + "costperlb");
				if (testCostPerLb == null || testCostPerLb.length() == 0)
				   testCostPerLb = "0";
				costPerPound[lineCount]          = new BigDecimal(testCostPerLb);
			
			}
		}

		if (this.requestType.equals("addFinish"))
		{
			RandDFormula.addToRandDFormula(formulaNumber,
								      formulatype,
								      name,
								      technician,
								      variety,
			  					      preservative,
								      currentDate,
			  					      currentTime,
								      currentUser,
			  					      comment,
									  sequenceNumber,
									  supplier,
									  supplierCode,
									  ingredientDescription,
									  resource,
									  quantity,
									  unitOfMeasure,
									  formulaPercent,
									  dryWeight,
									  weightPercent,
									  costPerPound);		
		}

		if (this.requestType.equals("updateFinish"))
		{
			RandDFormula.updateRandDFormula(formulaNumber,
								      formulatype,
								      name,
								      technician,
								      variety,
			  					      preservative,
								      currentDate,
			  					      currentTime,
								      currentUser,
			  					      comment,
									  sequenceNumber,
									  supplier,
									  supplierCode,
									  ingredientDescription,
									  resource,
									  quantity,
									  unitOfMeasure,
									  formulaPercent,
									  dryWeight,
									  weightPercent,
									  costPerPound);		
		}
		return;
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.addUpdateFormula: " + e);
	}  	    	
}
/**
 * Get the class back (based on ONE Formula) to build a parameter list
 *    Used to pre-load the fields for a Copy or Update.
 * Creation date: (6/24/2003 2:56:40 PM)
 */
private String buildParmList(javax.servlet.http.HttpServletRequest request,
					       javax.servlet.http.HttpServletResponse response)  
{
	String parmList = "";
	try
	{
		Integer newFormulaNumber = new Integer(0);
		RandDFormula formulaClass = new RandDFormula();

		Vector userListBox          = new Vector();
		userListBox                 = SampleRequestUsers.findTypeByName("tech");
		String dropDownUser         = "";

		if(!this.requestType.equals("add"))
		{
			String requestFormula = request.getParameter("formulaNumber");
			newFormulaNumber = new Integer(requestFormula);
			formulaClass = new RandDFormula(newFormulaNumber);
		}

		//Start with Heading stuff
		
		if (this.requestType.equals("copy") ||
			this.requestType.equals("add"))
		{
			int nextFormula = RandDFormula.nextFormulaNumber();
			newFormulaNumber = new Integer(nextFormula);
		}
	
		parmList = "&requestType=" + this.requestType +
 				   "&formulaNumber=" + newFormulaNumber;

		if (!this.requestType.equals("add"))
		{

			parmList = parmList +
				   "&formulaType=RD" +
				   "&formulaName="   + formulaClass.getName().trim() +
				   "&tech="          + formulaClass.getTechnician().trim() +
				   "&variety="       + formulaClass.getVariety().trim() +
				   "&preservative="  + formulaClass.getPreservative().trim() +
				   "&creationDate="  + formulaClass.getCreationDate() +
				   "&revisionDate="  + formulaClass.getUpdateDate();

		request.setAttribute("comment", formulaClass.getComment().trim());			   

    	    int lineCount = formulaClass.getSequenceNumber().length;
        
    	    for (int x = 1; x <= lineCount; x++)
     	   {
	     	   if (formulaClass.getSequenceNumber()[(x-1)] != null)
	      	  {
		      	  parmList = parmList +
		        		   "&" + x + "seq=" + 
		        		   		 x +
	 		       		   "&" + x + "supplier=" + 
	 		       		   		 formulaClass.getSupplier()[(x-1)].trim() +
	 		       		   "&" + x + "supplierCode=" + 
	 		       		   		 formulaClass.getSupplierCode()[(x-1)].trim() +
	 		       		   "&" + x + "ingDesc=" + 
	 		       		   		 formulaClass.getIngredientDescription()[(x-1)].trim() +
	 		       		   "&" + x + "resource=" + 
	 		       		   		 formulaClass.getResource()[(x-1)].trim() +
	 		       		   "&" + x + "qty=" + 
	 		       		   		 formulaClass.getQuantity()[(x-1)] +
	 		       		   "&" + x + "drywgt=" + 
	 		       		   		 formulaClass.getDryWeight()[(x-1)] +
	 		       		   "&" + x + "costperlb=" +
	 		       		         formulaClass.getCostPerPound()[(x-1)];
		 		 }	
  	      }
     		dropDownUser = SampleRequestUsers.buildDropDownNameForProfile(userListBox, 
	     		                                                formulaClass.getTechnician().trim(),
	     		                                                "Technician");
		}
		else
		{
			 dropDownUser = SampleRequestUsers.buildDropDownNameForProfile(userListBox, 
				                                                 "",
				                                                 "Technician");

		}

		request.setAttribute("dropdownUser", dropDownUser);
		
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.buildParmList: " + e);
	}
	return parmList;
}
/**
 * Add a Formula using the RandDFormula Class.
 * Creation date: (6/20/2003 3:59:40 PM)
 */
private void deleteFormula(javax.servlet.http.HttpServletRequest request,
    				       javax.servlet.http.HttpServletResponse response)  
{
	try
	{
		validateFormulaNumber(request,
			                  response);

		SampleRequestOrder thisOrder = new SampleRequestOrder();
		
		Vector listSamples = thisOrder.findSamplesbyFormula(this.formulaNumber);
		if (listSamples == null ||
			listSamples.size() == 0)
		{
			// Can Delete
			RandDFormula thisFormula = new RandDFormula();
			boolean didDelete        = thisFormula.deleteByFormulaNumber(this.formulaNumber.intValue());
			if (didDelete != true)
			   this.message = this.message +
			        "Formula Number " +
			        this.formulaNumber +
			        " had a problem when trying to delete.  " +
			        "The formula was not deleted.\n";
		}
		else
		{
			this.message = this.message +
			        "Formula Number " +
			        this.formulaNumber +
			        " is attached to one or more samples.  " +
			        "This Formula cannot be deleted.\n"; 
			        
		}



		
		return;
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.deleteFormula: " + e);
	}  	    	
}
/**
 * Process incoming HTTP GET requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}
/**
 * Process incoming HTTP POST requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}
/**
 * Returns the servlet info string.
 */
public String getServletInfo() {

	return super.getServletInfo();

}
/**
 * Initializes the servlet.
 */
public void init() {
	// insert code to initialize the servlet here

}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
public void newMethod() {}
/**
 * Use to send information to the detail Page.
 * Creation date: (6/20/2003 9:33:40 AM)
 */
private void pageDtl(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
		Vector formulaDetail = new Vector(); // To be put in a Session Variable to be
	                                       //  accessed in the JSP.
	    Vector customerList = new Vector(); // To be put in a Session Variable to be
	                                        //  accessed in the JSP.
		validateFormulaNumber(request,
			                  response);
	                                        
		try {	
			String technician = this.chosenFormula.getTechnician();
			SampleRequestUsers getTechName = new SampleRequestUsers("tech", technician);
			chosenFormula.setTechnician(getTechName.getUserName());
		}
		catch(Exception e)
		{
			System.out.println("No Tech Found in File");
		}

		
		formulaDetail.addElement(this.chosenFormula);
	    
		request.setAttribute("formulaDetail", formulaDetail);
		request.setAttribute("customerList", customerList);
		request.setAttribute("generalInfo", this.generalInfo);		
		//***** Go to the Dtl JSP *****//
		getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/dtlRandDFormula.jsp?" +
				"msg=" + this.message).
			forward(request, response);
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.pageDtl: " + e);
	}  	    	
}
/**
 * Use to send information to the inquiry Page.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
private void pageInq(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
		String sendTech = "";
		String  tech                  = request.getParameter("tech");
		    if (tech != null && tech.length() != 0 && !tech.equals("None"))
		       sendTech = tech;
		    
		SampleRequestUsers userInfo = new SampleRequestUsers();
		Vector userListBox          = new Vector();
		userListBox                 = SampleRequestUsers.findTypeByName("tech");
		String dropDownUser         = SampleRequestUsers.buildDropDownNameForProfile(userListBox, 
			                                                               sendTech,
			                                                               "*all");

		request.setAttribute("dropdownUser", dropDownUser);
		
		//***** Go to the Inq JSP *****//
		getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/inqRandDFormula.jsp?" +
				"msg=" + this.message).
			forward(request, response);
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.pageInq: " + e);
	}  	    	
}
/**
 * Use to send information to the List Jsp.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
private void pageList(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
		//*** Use for information about what was queried.
		String queryInfo = "";
 
        Vector ListFormula = new Vector(); // To be put in a Session Variable to be
	                                       //  accessed in the JSP. 

		if (this.formulaNumber != null &&
			this.requestType.equals("list"))
		{
			ListFormula.addElement(this.chosenFormula);
		}
		else
		{
			Integer fromFormula           = (Integer)       request.getAttribute("fromFormula");
			 int fromF = fromFormula.intValue();
			 if (fromF != 0)
			    queryInfo = queryInfo + "From Formula: <i>" + request.getParameter("fromFormula") + "</i> ";
			Integer toFormula             = (Integer)       request.getAttribute("toFormula");
			 int toF = toFormula.intValue();
			 if (toF != 999999999)
			    queryInfo = queryInfo + "To Formula: <i>" + request.getParameter("toFormula") + "</i> ";
			   
			java.sql.Date fromCreateDate = null;			     
			String newFromCreateDate = (String) request.getAttribute("fromCreateDate");
			 if (newFromCreateDate != null)
			 {
  			    fromCreateDate = java.sql.Date.valueOf(newFromCreateDate);
			    queryInfo = queryInfo + "From Creation Date: <i>" + request.getParameter("fromCreateDate") + "</i> ";
			 }

			java.sql.Date toCreateDate = null;
			String newToCreateDate     = (String) request.getAttribute("toCreateDate");
			 if (newToCreateDate != null)
			 {
 			    toCreateDate = java.sql.Date.valueOf(newToCreateDate);
		 	    queryInfo = queryInfo + "To Creation Date: <i>" + request.getParameter("toCreateDate") + "</i> ";
			 }

			java.sql.Date fromReviseDate = null;
			String newFromReviseDate     = (String) request.getAttribute("fromReviseDate");
			 if (newFromReviseDate != null)
			 {
 			   fromReviseDate = java.sql.Date.valueOf(newFromReviseDate);
			   queryInfo = queryInfo + "From Revision Date: <i>" + request.getParameter("fromReviseDate") + "</i> ";
			 }

			java.sql.Date toReviseDate = null;
			String newToReviseDate     = (String) request.getAttribute("toReviseDate");
			 if (newToReviseDate != null)
			 {
				toReviseDate = java.sql.Date.valueOf(newToReviseDate);
			    queryInfo = queryInfo + "To Revision Date: <i>" + request.getParameter("toReviseDate") + "</i> ";
			 }
			    
			String  formulaName           = request.getParameter("formulaName");
			 if (formulaName != null && formulaName.length() != 0)
			   queryInfo = queryInfo + "Formula Name: <i>" + formulaName + "</i> ";
			String  resource              = request.getParameter("resource");
			 if (resource != null && resource.length() != 0)
			   queryInfo = queryInfo + "Resource: <i>" + resource + "</i> ";
			String  ingredientDescription = request.getParameter("ingredientDescription");
			 if (ingredientDescription != null && ingredientDescription.length() != 0)
			   queryInfo = queryInfo + "IngredientDescription: <i>" + ingredientDescription + "</i> ";
			String  supplier              = request.getParameter("supplier");
			 if (supplier != null && supplier.length() != 0)
			   queryInfo = queryInfo + "Supplier: <i>" + supplier + "</i> ";
			String  variety               = request.getParameter("variety");
			 if (variety != null && variety.length() != 0)
			   queryInfo = queryInfo + "Variety: <i>" + variety + "</i> ";
			String  preservative          = request.getParameter("preservative");
			 if (preservative != null && preservative.length() != 0)
			   queryInfo = queryInfo + "Preservative: <i>" + preservative + "</i> ";
			String  tech                  = request.getParameter("tech");
			 if (tech != null && tech.length() != 0 && !tech.equals("None"))
			   queryInfo = queryInfo + "Technician: <i>" + tech + "</i> ";
			String  comment               = request.getParameter("comment");
			 if (comment != null && comment.length() != 0)
			   queryInfo = queryInfo + "Comment: <i>" + comment + "</i> ";
			String  customerName          = request.getParameter("customerName");
			 if (customerName != null && customerName.length() != 0)
			   queryInfo = queryInfo + "Customer Name: <i>" + customerName + "</i> ";

		    this.generalInfo[0] = queryInfo;
			   
			String  orderby               = request.getParameter("orderby");

			RandDFormula newClass = new RandDFormula();
			
			ListFormula = newClass.findFormulas("RD",//Formula Type
												   fromFormula,
												   toFormula,
												   fromCreateDate,
												   toCreateDate,
												   fromReviseDate,
												   toReviseDate,
												   formulaName,
												   resource,
												   ingredientDescription,
												   supplier,
												   variety,
												   preservative,
												   tech,
												   comment,
												   customerName,
												   orderby);
	}

		request.setAttribute("listFormulas", ListFormula);
		request.setAttribute("generalInfo", this.generalInfo);
				 	 
	  //***** Go to the List JSP *****//
	  getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/listRandDFormula.jsp?" +
				"msg=" + this.message).
			forward(request, response);
	  
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.pageList: " + e);
	}
	   	    	
}
/**
 * Use to send information to the Add/Update Page.
 * Creation date: (6/20/2003 2:56:40 PM)
 */
private void pageUpd(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in Variables.
		String parmList = "";
 		if (this.requestType.equals("addFinish") || 
			this.requestType.equals("updateFinish"))
		{
		    addUpdateFormula(request,
   		     	         response);
		    this.message = "Completed Normally";
		}
		else
		   parmList = buildParmList(request,response);
		 
				//***** Go to the Upd JSP *****//
		   getServletConfig().getServletContext().
			   getRequestDispatcher("/JSP/SampleRequest/updRandDFormula.jsp?" +
				   "msg=" + this.message +
				   parmList).
			   forward(request, response);
	
		return;
	
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.pageUpd: " + e);
	}  	    	
}
/**
 * Control for R and D Formula's servlets, (JSP's)
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 * 
 */
public void performTask(javax.servlet.http.HttpServletRequest request, 
					    javax.servlet.http.HttpServletResponse response) 
{
	try
	{
	   this.requestType = request.getParameter("requestType");
 	  	 if (this.requestType == null)
     	  this.requestType = "inq";
       String urlAddress = "/web/CtlRandDFormula?requestType=inq";
       if (this.requestType.equals("list"))
    	   urlAddress = "/web/CtlRandDFormula?requestType=list";
       if (this.requestType.equals("detail"))
       		urlAddress = "/web/CtlRandDFormula?requestType=detail";
       if (this.requestType.equals("add") ||
	       this.requestType.equals("update") ||
	       this.requestType.equals("copy") ||
	       this.requestType.equals("addFinish") ||
	       this.requestType.equals("updateFinish") ||
	       this.requestType.equals("delete"))
       		urlAddress = "/web/CtlRandDFormula?requestType=update";
		//********************************************************************
		// Execute security servlet. 
		//********************************************************************
		// Allow Session Variable Access
		HttpSession sess = request.getSession(true);
		
		  // Set the Status
		SessionVariables.setSessionttiSecStatus(request,response,"");
		  // Decide which URL to use, based on Request Type.
	     
		SessionVariables.setSessionttiTheURL(request, response, urlAddress);
		
		  // Call the security Servlet
  	    getServletConfig().getServletContext().
        getRequestDispatcher("/TTISecurity" ).
	    include(request, response);

	    // Decision of whether or not to use the Inq, List or Detail
	    if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
		{
			response.sendRedirect("/web/TreeNetInq?msg=" + 
				SessionVariables.getSessionttiSecStatus(request,response));
			return;
		}	    
	      // remove the Status and the Url
	    sess.removeAttribute("ttiTheURL");
	    sess.removeAttribute("ttiSecStatus");
	    //********************************************************************
	// Use this Array to send to the Jsp's for anything other than a Vector
	//  Each Jsp may use a different number of array values.
	//  Look in the page methods for details on what the values mean for that page.
	this.generalInfo   = new String[2];
	this.formulaNumber = null;
	this.chosenFormula = null;
	     
    //** Always test for a message, and send it on.
  	this.message = request.getParameter("msg");
    if (this.message == null)
       this.message = "";

    if (this.requestType.equals("inq"))
    	pageInq(request,
                response);
    	
    if (this.requestType.equals("delete"))
    {
    	deleteFormula(request,
                response);
 	  	validateParameters(request,
		                   response);
    }
    	
    if (this.requestType.equals("list"))
 	  	validateParameters(request,
		                   response);

    if (this.requestType.equals("detail"))
    	pageDtl(request,
                response);

    if (this.requestType.equals("add") || 
	    this.requestType.equals("update") ||
	    this.requestType.equals("copy"))
    	pageUpd(request,
                response);

    if (this.requestType.equals("addFinish") || 
	    this.requestType.equals("updateFinish"))
    	validateParameters(request,
                response);



	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		theException.printStackTrace();
	}
}
/**
 * Use to Check Parameters, 
 *		If they should be there,
 *      If numbers are valid,
 *      If dates are valid,
 *      if from's are less than to's.
 *
 * Creation date: (6/17/2003 10:29:40 AM)
 */
private void validateFormulaNumber(javax.servlet.http.HttpServletRequest request,
								   javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
  		//*** Test Formula Numbers, make sure they are numbers.
 		String testFormulaNumber = request.getParameter("formulaNumber");
 		if (this.requestType.equals("delete"))
 			testFormulaNumber = request.getParameter("deleteFormulaNumber");
		if (testFormulaNumber != null && testFormulaNumber.length() != 0)
		{
			try
			{
				this.formulaNumber = new Integer(testFormulaNumber);
				try
				{
		           this.chosenFormula = new RandDFormula(this.formulaNumber);
				}
				catch(Exception e)
				{
					this.message = this.message +
					     "The Formula Number chosen (" +
					     request.getParameter("formulaNumber") +
					     ")is not a valid Formula Number.\n";
				}
				
			}
			catch(NumberFormatException nfe)
			{
				//System.out.println("Error in CtlRandDFormula.validateFormulaNumber. " +
				//				   "formulaNumber is not a number." +
				//				   nfe);
					this.message = this.message +
				               "There is a problem with the Formula Number, " + 
						           "the number chosen (" + 
						           request.getParameter("formulaNumber") + 
						           ")is not valid.\n";
			}		
		}
		
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.validateFormulaNumber: " + e);
	}
	   	    	
}
/**
 * Use to Check Parameters, 
 *		If they should be there,
 *      If numbers are valid,
 *      If dates are valid,
 *      if from's are less than to's.
 *
 * Creation date: (6/17/2003 10:29:40 AM)
 */
private void validateParameters(javax.servlet.http.HttpServletRequest request,
								javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
		//** Bring in Variables.
		if (this.requestType.equals("list") ||
			this.requestType.equals("delete"))
		{
			//*** Test Formula Numbers, make sure they are numbers.
			if (this.requestType.equals("list"))
			{
			   validateFormulaNumber(request,
				                  response);
			}
			
			String fromFormula = request.getParameter("fromFormula");
			if (fromFormula == null || fromFormula.length() == 0)
			   fromFormula = "0";
			String toFormula = request.getParameter("toFormula");
			if (toFormula == null || toFormula.length() == 0)
			   toFormula = "999999999";
					
 			try
			{
				Integer testedFromFormula   = new Integer(fromFormula);
				Integer testedToFormula     = new Integer(toFormula);
				int x = testedFromFormula.compareTo(testedToFormula);
				
				if (x > 0)
				   this.message = this.message +
				                   "For the Formula Number Fields, the From Formula is " +
			                       "greater than the to Formula.\n"; 
			    else
			    {
  					request.setAttribute("fromFormula", testedFromFormula);
  					request.setAttribute("toFormula", testedToFormula);
			    } 
			}
			catch(NumberFormatException nfe)
			{
				//System.out.println("Error in CtlRandDFormula.validateParameters. " +
				//				   "formulaNumber is not a number." +
				//				   nfe);
				this.message = this.message +
				      "There is a problem with the Formula Number, " + 
					  "the numbers chosen are not valid.\n";
			}

			//*** Test Creation Dates, make sure they are valid dates. and the from is before the to.
			int intFromDate = 0;
			int intToDate = 99999999;
			String fromDate = request.getParameter("fromCreateDate");
			String toDate = request.getParameter("toCreateDate");
			
			if (fromDate != null && fromDate.length() != 0)
			{
				String fromdate[] = CheckDate.validateDate(fromDate);
				if (!fromdate[6].equals(""))
				{			
					this.message = this.message +
					    fromdate[6] + " ( " + fromDate +  " ) " +
						"(CtlRandDFormula.validateParameters -- From Creation Date)\n";
				}
				else
				{
					intFromDate = Integer.parseInt(fromdate[5]);
					request.setAttribute("fromCreateDate", fromdate[7]); 
				}
			}

			
			if (toDate != null && toDate.length() != 0)
			{
				String todate[] = CheckDate.validateDate(toDate);
				if (!todate[6].equals(""))
				{			
					this.message = this.message +
					     todate[6] + " ( " + toDate +  " ) " +
						"(CtlRandFormula.validateParameters -- To Creation Date)\n";
				}
				else
				{
					intToDate = Integer.parseInt(todate[5]);
					request.setAttribute("toCreateDate", todate[7]); 
				}
			}

			if (intToDate < intFromDate) //** Error if True	
    		{
				this.message = this.message +
				       "Ending Creation Date must be greater than Start Creation Date." +
						" Start Date of " + fromDate +  " " +
						" Ending Date of " + toDate +  " " +
		   				"(CtlRandDFormula.validateParameters)\n";	
 	 		}
    		

			intFromDate = 0;
			intToDate = 99999999;
			fromDate = request.getParameter("fromReviseDate");
			toDate = request.getParameter("toReviseDate");

			
			if (fromDate != null && fromDate.length() != 0)
			{
				String fromdate[] = CheckDate.validateDate(fromDate);
				if (!fromdate[6].equals(""))
				{			
					this.message = this.message +
					     fromdate[6] + " ( " + fromDate +  " ) " +
						"(CtlRandDFormula.validateParameters -- From Revision Date)/n";
				}
				else
				{
					intFromDate = Integer.parseInt(fromdate[5]);
					request.setAttribute("fromReviseDate", fromdate[7]); 
				}
			}

			
			if (toDate != null && toDate.length() != 0)
			{
				String todate[] = CheckDate.validateDate(toDate);
				if (!todate[6].equals(""))
				{			
					this.message = this.message +
					    todate[6] + " ( " + toDate +  " ) " +
						"(CtlRandDFormula.validateParameters -- To Revision Date)\n";
				}
				else
				{
					intToDate = Integer.parseInt(todate[5]);
					request.setAttribute("toReviseDate", todate[7]); 
				}
			}

			if (intToDate < intFromDate) //** Error if True	
    		{
				this.message = this.message +
				     "Ending Revision Date must be greater than Start Revision Date." +
						" Start Date of " + fromDate +  " " +
						" Ending Date of " + toDate +  " " +
		   				"(CtlRandDFormula.validateParameters)\n";	
 	 		}

			if (this.message.equals("") ||
				this.requestType.equals("delete"))
			  	pageList(request,
  		                 response);
			else
				pageInq(request,
  		                 response);
			
		}
		else
		{ //Add, Copy, Update
			for (int x=1; x < 16; x++) // Test new Section
			{
				//  Test the Sequence Number
				String parmSequence = x + "seq";
 				String seqNumber = request.getParameter(parmSequence);
				if (seqNumber != null && seqNumber.length() != 0)
				{
					try
					{
						Integer testedSeqNumber = new Integer(seqNumber);
					}
					catch(NumberFormatException nfe)
					{
						//System.out.println("Error in CtlRandDFormula.validateParameters. " +
						//				   "sequenceNumber is not a valid number." +
						//				   nfe);
						this.message = this.message + 
						    "There is a problem with the Sequence Number, " + seqNumber + 
							  ", the number chosen is not valid.\n";
					}		
				}
				
				//  Test the Quantity
				String parmQuantity = x + "qty";
 				String quantity = request.getParameter(parmQuantity);
				if (quantity != null && quantity.length() != 0)
				{
					try
					{
						BigDecimal testedQuantity = new BigDecimal(quantity);
					}
					catch(NumberFormatException nfe)
					{
						//System.out.println("Error in CtlRandDFormula.validateParameters. " +
						//				   "Quantity is not a valid number." +
						//				   nfe);
						this.message = this.message + 
						   "There is a problem with the Quantity, " + quantity + 
							  ", the number chosen is not valid.\n";
					}		
				}

				//  Test the Dry Weight
				String parmDryWgt = x + "drywgt";
 				String drywgt = request.getParameter(parmDryWgt);
				if (drywgt != null && drywgt.length() != 0)
				{
					try
					{
						BigDecimal testedDryWgt = new BigDecimal(drywgt);
					}
					catch(NumberFormatException nfe)
					{
						//System.out.println("Error in CtlRandDFormula.validateParameters. " +
						//				   "Dry Weight is not a valid number." +
						//				   nfe);
						this.message = this.message + 
						     "There is a problem with the Dry Weight, " + drywgt + 
							  ", the number chosen is not valid.\n";
					}		
				}

				//  Test the Cost Per Pound
				String parmCostPerLb = x + "costperlb";
 				String costperlb = request.getParameter(parmCostPerLb);
				if (costperlb != null && costperlb.length() != 0)
				{
					try
					{
						BigDecimal testedCostPerLb = new BigDecimal(costperlb);
					}
					catch(NumberFormatException nfe)
					{
						//System.out.println("Error in CtlRandDFormula.validateParameters. " +
						//				   "Cost Per Pound is not a valid number." +
						//				   nfe);
						this.message = this.message + 
						     "There is a problem with the Cost Per Pound, " + costperlb + 
							  ", the number chosen is not valid.\n";
					}		
				}
			}

			
			if (this.message.equals(""))
			  	pageUpd(request,
  		                 response);
			else
			{
				getServletConfig().getServletContext().
				getRequestDispatcher("/web/JSP/SampleRequest/updRandDFormula.jsp?" +
					"msg=" + this.message).
				forward(request, response);
			}	
		}

		
		

	}
	catch(Exception e)
	{
		System.out.println("Error in CtlRandDFormula.validateParameters: " + e);
	}
	   	    	
}
}
