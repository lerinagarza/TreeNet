/*
 * Created on Jul 27, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.utilities.html;

/**
 * @author twalto
 * @deprecated
 * Use this class to put Javascript Scripts into, for use in JSP's
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JavascriptInfo {
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for testing the length of a textarea box.
	 *
	 *   Send into the Javascript the Maximum Length for characters
	 *    in this box.
	 * Update date: TWalton 9/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getCheckTextareaLength() 
	{
		StringBuffer returnFunction = new StringBuffer();
		
		returnFunction.append("<script language=\"javascript\">");
		returnFunction.append("function CheckTextareaLength(length) ");
		returnFunction.append("{ ");
		returnFunction.append("if (window.event.srcElement.value.length >= length) ");
		returnFunction.append("{ ");
		returnFunction.append("alert(\"You have exceeded the maximum length of \" + length + \" ");
		returnFunction.append("characters permitted here. \");");
		returnFunction.append("return false; ");
		returnFunction.append("} ");
		returnFunction.append("} ");
		returnFunction.append("</script>");
		
		return returnFunction.toString();
	}
	/**
	 * Use this method to get the javascript 
	 *   To create a confirm box, with OK or Cancel.
	 *   
	 *   The function Name should be unique
	 *     for each instance on your page.
	 *   Define what to do will default "Click OK or Cancel to Continue";
	 *   If they choose OK, they will need a URL with parameters of where to go.
	 * Update date: TWalton 9/12/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getConfirmAlertBox(String functionName, 
											String defineWhatToDo) 
	{
		StringBuffer returnString = new StringBuffer();
		returnString.append("<script language=\"JavaScript\">");
		returnString.append("function ");
		returnString.append(functionName);
		returnString.append("(urlWhere) ");
		returnString.append("{ ");
		returnString.append("input_box = confirm(\"");
		if (defineWhatToDo.trim().length() == 0)
		  returnString.append("Click OK or Cancel to Continue");
		else
		  returnString.append(defineWhatToDo);
		returnString.append("\");");
		returnString.append("if (input_box == true) ");
		returnString.append("{ ");
		returnString.append("window.location = urlWhere;");
		returnString.append(" }");
		returnString.append("} ");
		returnString.append("</script>");	
		
		return returnString.toString();
	}
	/**
	 * This is the javascript which will allow
	 *    one drop down box to be created from
	 *    the information chosen within the first
	 *    box.
	 * Using this javascript allows more than one per page.
	 *
	 *  This code is for drop down boxes which are pre-loaded,
	 *    and HAVE to have something chosen.
	 *
	 *   Send in the names of the drop down list which
	 *    will dynamically change.
	 *
	 * Creation date: (11/21/2003 9:28:36 AM)
	 * @return java.lang.String // Was called Dynamic Drop Down Script
	 *  Found script at ** www.fiendish.demon.co.uk/html/listfill.html **
	 * Update date: 8/12/05 TWalton
	 */
	public static String getDualDropDown(String masterName,
		                                 String slaveName) 
	{
	//*************************************************************	
	// What else needs to be done to make this script work.
	//  ** Arrays have to be created in the HEAD Section
	//       ie:   <script language="javascript">
	//          var lists = new Array();
	//
	//          lists['01']    = new Array();
	//          lists['01'][0] = new Array(
	//  	                    'List',
	//	                        'Printer Friendly'
	//                           );
	//          lists['01'][1] = new Array(
	//	                        '01',
	//	                        '02'
	//                           );
	//
	//          lists['02']    = new Array();
	//          lists['02'][0] = new Array(
	//	                        'Printer Friendly'
	//                           );
	//          lists['02'][1] = new Array(
	//	                        '02'
	//                           );
	//               </script>
	//
	//** Load information into the Body Tag (To Pre-load the info)
	//   ie:<body onload="changeList(document.forms['name of the form'].name of master)">
	//
	//** How the select section will work
	//        <select name="template" size=1 onchange="changeList(this)"> ** template is the master name.
	//          <option value="01">Summary
	//          <option value="02">Detail
	//        </select>
	//        <select name="version" size=1>  ** version is the slave name.
	//        </select>
	//*************************************************************
	    StringBuffer returnScript = new StringBuffer();
	    returnScript.append("<script language=\"javascript\">");
	
	   // This function goes through the options for the given
	   // drop down box and removes them in preparation for
	   // a new set of values
	
	   returnScript.append("function emptyList( box ) { ");
	      
		   // Set each option to null thus removing it
	   returnScript.append("while ( box.options.length )");
	   returnScript.append(" box.options[0] = null; } ");
	
	// This function assigns new drop down options to the given
	// drop down box from the list of lists specified
	
	   returnScript.append("function fillList( box, arr ) {");
	   
		// arr[0] holds the display text
		// arr[1] are the values
		returnScript.append("for ( i = 0; i < arr[0].length; i++ ) {");
		
			// Create a new drop down option with the
			// display text and value from arr
		returnScript.append("option = new Option( arr[0][i], arr[1][i] );");
			// Add to the end of the existing options
		returnScript.append("box.options[box.length] = option;}");
		// Preselect option 0
		returnScript.append("box.selectedIndex=0; }");
	
	// This function performs a drop down list option change by first
	// emptying the existing option list and then assigning a new set
	    
	    returnScript.append("function change");
	    returnScript.append(masterName);
	    returnScript.append("( box ) {");
	    
		// Isolate the appropriate list by using the value
		// of the currently selected option
		returnScript.append("list = lists[box.options[box.selectedIndex].value];");
		// Next empty the version list
		returnScript.append("emptyList( box.form.");
		returnScript.append(slaveName);
		returnScript.append(" );");
		// Then assign the new list values
		returnScript.append("fillList( box.form.");
		returnScript.append(slaveName);
		returnScript.append(", list ); }");
	    returnScript.append("</script>");
	  
		return returnScript.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for deciding whether an input
	 *   number is actually a number.
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getNumericCheck() {
		StringBuffer returnString = new StringBuffer();

		returnString.append("<script language=\"JavaScript1.2\">");
		returnString.append("function checknumeric(numfield, name) ");
		returnString.append("{ ");
		returnString.append("var valid     = \"0123456789,.-\"; ");
		returnString.append("var ok        = \"yes\"; ");
		returnString.append("var decimalok = \"no\"; ");
		returnString.append("var temp; ");
		returnString.append("for (var i=0; i < numfield.value.length; i++) ");
		returnString.append("{ ");
		returnString.append("temp = \"\" + numfield.value.substring(i, i+1); ");
		returnString.append("if (valid.indexOf(temp) == \"-1\") ");
		returnString.append("ok = \"no\"; ");
		returnString.append("if (numfield.value.substring(i, i+1) == \".\") ");
		returnString.append("{ ");
		returnString.append("if (decimalok == \"yes\") ");
		returnString.append("ok = \"no\"; ");
		returnString.append("else ");
		returnString.append("decimalok = \"yes\"; ");
		returnString.append("} ");
		returnString.append("if (numfield.value.substring(i, i+1) == \",\") ");
		returnString.append("{ ");
		returnString.append("if (decimalok == \"yes\") ");
		returnString.append("ok = \"no\"; ");
		returnString.append("} ");
		returnString.append("} ");
		returnString.append("if (ok == \"no\") ");
		returnString.append("{ ");
		returnString.append("alert(\"The value \" + numfield.value + \" that");
		returnString.append(
			" was entered for \" + name + \" was not a valid number.");
		returnString.append("  Please choose another number value.\");");
		returnString.append("return false; ");
		returnString.append("} ");
		returnString.append("else ");
		returnString.append("return true; ");
		returnString.append("} ");
		returnString.append("</script>");

		return returnString.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for validating a field is not blank,
	 *   a required field.
	 *
	 *  Retrieved this JavaScript from
	 *    The JavaScript Source -- http://javascript.internet.com
	 *    Original created by Wayne Nolting
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getRequiredField() {
		StringBuffer returnString = new StringBuffer();

		returnString.append("<script language=\"JavaScript\">");

		returnString.append("function verify(fieldValue, fieldName) { ");
		returnString.append("var themessage = \"\"; ");
		returnString.append("if (fieldValue.value==\"\") { ");
		returnString.append(
			"themessage = themessage + fieldName + \" must be filled in. \"; ");
		returnString.append("}");
		returnString.append("if (themessage != \"\") { ");
		returnString.append("alert(themessage);");
		returnString.append("return false;");
		returnString.append("}");
		returnString.append(" }");
		returnString.append("</script>");

		return returnString.toString();
	}
	/**
	 * Use this method to get the javascript 
	 *   is used to pop up the calendar html
	 *   Please send in the name of the variable
	 *   you would like to choose.
	 *   This javascript goes right after the
	 *   end of the form tag.  It creates seperate
	 *   instances of the calender.  Will need
	 *   to use this method for each instance on
	 *   the page.
	 *   The function Name and the field Name should be unique
	 *     for each instance on your page.
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getCalendarFoot(
		String formName,
		String functionName,
		String fieldName) {

		StringBuffer returnString = new StringBuffer();

		returnString.append("<script language=\"JavaScript1.2\">");
		returnString.append("var ");
		returnString.append(functionName);
		returnString.append(" = new calendar2(document.forms['");
		returnString.append(formName);
		returnString.append("'].elements['");
		returnString.append(fieldName);
		returnString.append("']);");
		returnString.append(functionName);
		returnString.append(".year_scroll = false;");
		returnString.append(functionName);
		returnString.append(".time_comp = false;");
		returnString.append("</script>");

		return returnString.toString();
	}
	/**
	 * Use this method to get the javascript 
	 *   is used to pop up the calendar html
	 *   will let you choose a date and
	 *   put it into an input field.
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getCalendarHead() {
		StringBuffer returnString = new StringBuffer();
		// Title: Tigra Calendar
		// URL: http://www.softcomplex.com/products/tigra_calendar/
		// Version: 3.2 (American date format)
		// Date: 10/14/2002 (mm/dd/yyyy)
		// Feedback: feedback@softcomplex.com (specify product title in the subject)
		// Note: Permission given to use this script in ANY kind of applications if
		//    header lines are left unchanged.
		// Note: Script consists of two files: calendar?.js and calendar.html
		// About us: Our company provides offshore IT consulting services.
		//    Contact us at sales@softcomplex.com if you have any programming task you
		//    want to be handled by professionals. Our typical hourly rate is $20.		

		returnString.append("<script language=\"JavaScript1.2\">");

		//		if two digit year input dates after this year considered 20 century.
		returnString.append("var NUM_CENTYEAR = 30; ");
		//		is time input control required by default 					
		returnString.append("var BUL_TIMECOMPONENT = false; ");
		//		are year scrolling buttons required by default 				
		returnString.append("var BUL_YEARSCROLL = true; ");
		returnString.append("var calendars = []; ");
		returnString.append("var RE_NUM = /^\\-?\\d+$/; ");
		returnString.append("function calendar2(obj_target) ");
		returnString.append("{ ");
		//		assing methods 				
		returnString.append("this.gen_date = cal_gen_date2; ");
		returnString.append("this.gen_time = cal_gen_time2; ");
		returnString.append("this.gen_tsmp = cal_gen_tsmp2; ");
		returnString.append("this.prs_date = cal_prs_date2; ");
		returnString.append("this.prs_time = cal_prs_time2; ");
		returnString.append("this.prs_tsmp = cal_prs_tsmp2; ");
		returnString.append("this.popup    = cal_popup2; ");
		//		validate input parameters 			
		returnString.append("if (!obj_target) ");
		returnString.append(
			"return cal_error(\"Error calling the calendar: no target control specified\"); ");
		returnString.append("if (obj_target.value == null) ");
		returnString.append(
			"return cal_error(\"Error calling the calendar: parameter specified is not valid target control\"); ");
		returnString.append("this.target = obj_target; ");
		returnString.append("this.time_comp = BUL_TIMECOMPONENT; ");
		returnString.append("this.year_scroll = BUL_YEARSCROLL; ");
		//		register in global collections			
		returnString.append("this.id = calendars.length; ");
		returnString.append("calendars[this.id] = this; ");
		returnString.append("} ");
		returnString.append("function cal_popup2 (str_datetime) ");
		returnString.append("{ ");
		returnString.append(
			"this.dt_current = this.prs_tsmp(str_datetime ? str_datetime : this.target.value); ");
		returnString.append("if (!this.dt_current) return; ");
		returnString.append("var obj_calwindow = window.open( ");
		returnString.append(
			"'/web/HTML/calendar.html?datetime=' + this.dt_current.valueOf()+ '&id=' + this.id, ");
		returnString.append(
			"'Calendar', 'width=200,height='+(this.time_comp ? 215 : 190)+ ");
		returnString.append(
			"',status=no,resizable=no,top=200,left=200,dependent=yes,alwaysRaised=yes' ");
		returnString.append("); ");
		returnString.append("obj_calwindow.opener = window; ");
		returnString.append("obj_calwindow.focus(); ");
		returnString.append("} ");
		//		timestamp generating function "			
		returnString.append("function cal_gen_tsmp2 (dt_datetime) ");
		returnString.append("{ ");
		returnString.append(
			"return(this.gen_date(dt_datetime) + ' ' + this.gen_time(dt_datetime)); ");
		returnString.append("} ");
		//		date generating function 	
		returnString.append("function cal_gen_date2 (dt_datetime) ");
		returnString.append("{ ");
		returnString.append("return ( ");
		returnString.append(
			"(dt_datetime.getMonth() < 9 ? '0' : '') + (dt_datetime.getMonth() + 1) + \"/\" ");
		returnString.append(
			"+ (dt_datetime.getDate() < 10 ? '0' : '') + dt_datetime.getDate() + \"/\" ");
		returnString.append("+ dt_datetime.getFullYear() ");
		returnString.append("); ");
		returnString.append("} ");
		//		time generating function			
		returnString.append("function cal_gen_time2 (dt_datetime) ");
		returnString.append("{ ");
		returnString.append("return ( ");
		returnString.append(
			"(dt_datetime.getHours() < 10 ? '0' : '') + dt_datetime.getHours() + \":\" ");
		returnString.append(
			"+ (dt_datetime.getMinutes() < 10 ? '0' : '') + (dt_datetime.getMinutes()) + \":\" ");
		returnString.append(
			"+ (dt_datetime.getSeconds() < 10 ? '0' : '') + (dt_datetime.getSeconds()) ");
		returnString.append("); ");
		returnString.append("} ");
		//		timestamp parsing function			
		returnString.append("function cal_prs_tsmp2 (str_datetime) ");
		returnString.append("{ ");
		//		if no parameter specified return current timestamp			
		returnString.append("if (!str_datetime) ");
		returnString.append("return (new Date()); ");
		//		if positive integer treat as milliseconds from epoch			
		returnString.append("if (RE_NUM.exec(str_datetime)) ");
		returnString.append("return new Date(str_datetime); ");
		//		else treat as date in string format			
		returnString.append("var arr_datetime = str_datetime.split(' '); ");
		returnString.append(
			"return this.prs_time(arr_datetime[1], this.prs_date(arr_datetime[0])); ");
		returnString.append("} ");
		//		date parsing function			
		returnString.append("function cal_prs_date2 (str_date) ");
		returnString.append("{ ");
		returnString.append("var arr_date = str_date.split('/'); ");
		returnString.append("if (arr_date.length != 3) ");
		returnString.append(
			"return alert (\"Invalid date format: '\" + str_date + \"'.\\nFormat accepted is dd-mm-yyyy.\"); ");
		returnString.append("if (!arr_date[1]) ");
		returnString.append(
			"return alert (\"Invalid date format: '\" + str_date + \"'.\\nNo day of month value can be found.\"); ");
		returnString.append("if (!RE_NUM.exec(arr_date[1])) ");
		returnString.append(
			"return alert (\"Invalid day of month value: '\" + arr_date[1] + \"'.\\nAllowed values are unsigned integers.\"); ");
		returnString.append("if (!arr_date[0]) ");
		returnString.append(
			"return alert (\"Invalid date format: '\" + str_date + \"'.\\nNo month value can be found.\"); ");
		returnString.append("if (!RE_NUM.exec(arr_date[0])) ");
		returnString.append(
			"return alert (\"Invalid month value: '\" + arr_date[0] + \"'.\\nAllowed values are unsigned integers.\"); ");
		returnString.append("if (!arr_date[2]) ");
		returnString.append(
			"return alert (\"Invalid date format: '\" + str_date + \"'.\\nNo year value can be found.\"); ");
		returnString.append("if (!RE_NUM.exec(arr_date[2])) ");
		returnString.append(
			"return alert (\"Invalid year value: '\" + arr_date[2] + \"'.\\nAllowed values are unsigned integers.\"); ");
		returnString.append("var dt_date = new Date(); ");
		returnString.append("dt_date.setDate(1); ");
		returnString.append("if (arr_date[0] < 1 || arr_date[0] > 12) ");
		returnString.append(
			"return alert (\"Invalid month value: '\" + arr_date[0] + \"'.\\nAllowed range is 01-12.\"); ");
		returnString.append("dt_date.setMonth(arr_date[0]-1); ");
		returnString.append("if (arr_date[2] < 100) ");
		returnString.append(
			"arr_date[2] = Number(arr_date[2]) + (arr_date[2] < NUM_CENTYEAR ? 2000 : 1900); ");
		returnString.append("dt_date.setFullYear(arr_date[2]); ");
		returnString.append(
			"var dt_numdays = new Date(arr_date[2], arr_date[0], 0); ");
		returnString.append("dt_date.setDate(arr_date[1]); ");
		returnString.append("if (dt_date.getMonth() != (arr_date[0]-1)) ");
		returnString.append(
			"return alert (\"Invalid day of month value: '\" + arr_date[1] + \"'.\\nAllowed range is 01-\"+dt_numdays.getDate()+\".\"); ");
		returnString.append("return (dt_date); ");
		returnString.append("} ");
		//		time parsing function 			
		returnString.append("function cal_prs_time2 (str_time, dt_date) ");
		returnString.append("{ ");
		returnString.append("if (!dt_date) return null; ");
		returnString.append(
			"var arr_time = String(str_time ? str_time : '').split(':'); ");
		//** Not using the time function.
		//	        returnString.append("if (!arr_time[0]) dt_date.setHours(0); ");
		//	        returnString.append("else if (RE_NUM.exec(arr_time[0])) ");
		//		    returnString.append("if (arr_time[0] < 24) dt_date.setHours(arr_time[0]); ");
		//		    returnString.append("else return cal_error (\"Invalid hours value: '\" + arr_time[0] + \"'.\nAllowed range is 00-23.\"); ");
		//	        returnString.append("else return cal_error (\"Invalid hours value: '\" + arr_time[0] + \"'.\nAllowed values are unsigned integers.\"); ");

		//	        returnString.append("if (!arr_time[1]) dt_date.setMinutes(0); ");
		//	        returnString.append("else if (RE_NUM.exec(arr_time[1])) ");
		//	    	returnString.append("if (arr_time[1] < 60) dt_date.setMinutes(arr_time[1]); ");
		//		    returnString.append("else return cal_error (\"Invalid minutes value: '\" + arr_time[1] + \"'.\nAllowed range is 00-59.\"); ");
		//	        returnString.append("else return cal_error (\"Invalid minutes value: '\" + arr_time[1] + \"'.\nAllowed values are unsigned integers.\"); ");

		// 	        returnString.append("if (!arr_time[2]) dt_date.setSeconds(0); ");
		//	        returnString.append("else if (RE_NUM.exec(arr_time[2])) ");
		//		    returnString.append("if (arr_time[2] < 60) dt_date.setSeconds(arr_time[2]); ");
		//		    returnString.append("else return cal_error (\"Invalid seconds value: '\" + arr_time[2] + \"'.\nAllowed range is 00-59.\"); ");
		//	        returnString.append("else return cal_error (\"Invalid seconds value: '\" + arr_time[2] + \"'.\nAllowed values are unsigned integers.\"); ");

		returnString.append("dt_date.setMilliseconds(0); ");
		returnString.append("return dt_date; ");
		returnString.append("} ");
		returnString.append("function cal_error (str_message) ");
		returnString.append("{ ");
		returnString.append("alert (str_message); ");
		returnString.append("return null; ");
		returnString.append("} ");
		returnString.append("</script>");

		return returnString.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for generating a menu box, from and edit button
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getEditButton() {
		StringBuffer returnString = new StringBuffer();

		//----------------------------------------
		// What the menu part will look like
		//----------------------------------------
		returnString.append("<style>");
		returnString.append(".spanstyle");
		returnString.append("{ ");
		returnString.append("position:absolute; ");
		returnString.append("z-index:100; ");
		returnString.append("background-color:#ffffcc; ");
		returnString.append("width:250px; ");
		returnString.append("right:65; ");
		returnString.append("}");
		returnString.append("</style>");
		//************************************/
		//*  Display the Menu Options        */
		//************************************/
		returnString.append("<script language=\"JavaScript1.2\">");
		returnString.append("var head1=\"display:'none'\"; ");
		returnString.append("function viewmenu(header1)");
		returnString.append("{ ");
		returnString.append(" var head1=header1.style; ");
		returnString.append(" if (head1.display==\"none\") ");
		returnString.append(" { ");
		returnString.append("  head1.display=\"\"; ");
		returnString.append(" }else{ ");
		returnString.append("  head1.display=\"none\"; ");
		returnString.append(" } ");
		returnString.append("} ");
		returnString.append("</script>");

		return returnString.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for generating a menu box, from and edit button
	 * Need a larger Width, because of all the links on ONE Line.
	 * Update date: TWalton 3/20/2006 - 
	 */
	public static String getEditButtonOneLine() {
		StringBuffer returnString = new StringBuffer();

		//----------------------------------------
		// What the menu part will look like
		//----------------------------------------
		returnString.append("<style>");
		returnString.append(".spanstyle");
		returnString.append("{ ");
		returnString.append("position:absolute; ");
		returnString.append("z-index:100; ");
		returnString.append("background-color:#ffffcc; ");
		returnString.append("width:550px; ");
		returnString.append("right:65; ");
		returnString.append("}");
		returnString.append("</style>");
		//************************************/
		//*  Display the Menu Options        */
		//************************************/
		returnString.append("<script language=\"JavaScript1.2\">");
		returnString.append("var head1=\"display:'none'\"; ");
		returnString.append("function viewmenu(header1)");
		returnString.append("{ ");
		returnString.append(" var head1=header1.style; ");
		returnString.append(" if (head1.display==\"none\") ");
		returnString.append(" { ");
		returnString.append("  head1.display=\"\"; ");
		returnString.append(" }else{ ");
		returnString.append("  head1.display=\"none\"; ");
		returnString.append(" } ");
		returnString.append("} ");
		returnString.append("</script>");

		return returnString.toString();
	}
	
	/**
	 *   RIGHT JUSTIFIED
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code.  To be put where the Expanding 
	 *   section should be.
	 * *** REMEMBER to put the END of the span tag 
	 *       in the JSP where it goes.
	 *     ALSO you will need to use the get
	 *        getExpandingSectionHead method to get 
	 *        the javascript code which goes in
	 *        the <head> section
     *
	 *	 *	Send in:
	 *      String  expand       -O - Open, C - Closed (Which way to start)
	 *      int     fontSize     -Font size of the Title of the section
	 *      String  sectionName  -Value displayed at the top of the expanding section
	 *                                this value can be a link.
	 * 		int     sectionNumber-Which Expanding section this is.
	 *      int     imageNumber  -Which Image on the screen this is.
	 *      int     expandObject = (1,2) Default will be 1 (2 for link as heading)
	 * ---------------------------------------
	 * Create date: TWalton 3/30/2005
	 * Update date: TWalton 4/20/2006 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 * ---------------------------------------
	 */
	public static String getExpandingSectionRight(
		String expand,
		String sectionName,
		int fontSize,
		int sectionNumber,
		int imageNumber,
		int expandObject,
		int textColor) {

		//---------------------------------------
		// Open or Closed - Default Open
		//---------------------------------------
		String firstImage = "minusbox3.gif";
		String displayHead = "";
		String changeImage = "changeImage";
		if (expand != null && expand.equals("C")) {
			firstImage = "plusbox3.gif";
			displayHead = "style=\"display:none\" ";
			changeImage = "changeImage1";
		}
		//---------------------------------------
		// Link(2) or Not(1)
		//---------------------------------------
		if (expandObject == 0)
			expandObject = 1;
		//---------------------------------------
		// Determine Font Size default 12
		//---------------------------------------		  
		if (fontSize == 0)
			fontSize = 12;
		//---------------------------------------
		// Determine the Color of the Text
		//---------------------------------------
		String changeText = "990000";
		if (textColor == 1) //Pale Yellow
			changeText = "ffffcc";
		if (textColor == 2) //Light Olive
			changeText = "cccc99";
		if (textColor == 3) //Gold
			changeText = "d6b364";
		if (textColor == 4) //Dark Green
			changeText = "006400";
		if (textColor == 6) //Navy
			changeText = "003366";
		if (textColor == 7) //Black
			changeText = "000000";

		//---------------------------------------------
		// Build Code for Start of Expanding Section
		//    REMEMBER you must put an end span where you want to stop
		//---------------------------------------------  

		StringBuffer returnString = new StringBuffer();
		returnString.append("<div style=\"color:#");
		returnString.append(changeText); // What color is the font
		returnString.append(";");
		returnString.append("font-weight:bold;");
		returnString.append("font-family:arial;");
		returnString.append("font-size:");
		returnString.append(fontSize); // Size of the Font
		returnString.append("pt;");
		returnString.append("text-align:right;\">");
		returnString.append(
			"&nbsp;<img src=\"https://image.treetop.com/webapp/");
		returnString.append(firstImage);
		// Which image to display first plus or minus
		returnString.append("\" ");
		returnString.append("style=\"cursor:hand\" ");
		returnString.append("onClick=\"doit(document.all[this.sourceIndex+");
		returnString.append(expandObject);
		returnString.append("]); ");
		returnString.append(changeImage);
		returnString.append("(");
		returnString.append(sectionNumber);
		returnString.append(",");
		returnString.append(imageNumber);
		returnString.append(");\">");
		returnString.append("&nbsp;&nbsp;");
		returnString.append(sectionName);
		returnString.append("</div>");
		returnString.append("<span ");
		returnString.append(displayHead);
		returnString.append("style=&{head};>");

		return returnString.toString();
	}
	/**
	 * @deprecated
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for building the plus - minus boxes 
	 *   on the expanding sections
	 *	Send in:
	 *		 expandOpen (Y if you want a display starting open)
	 *       numberOfOpenSections (The Number of expanding sections wanted that are to start as open.
	 *       expandClosed (Y if you want a display starting closed) (You can have both open and closed)
	 *       numberOfClosedSections (The Number of expanding sections wanted that are to start as closed.
	 * Create date: TWalton 7/23/2004
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getExpandingSectionHead(
		String expandOpen,
		int numberOfOpenSections,
		String expandClosed,
		int numberOfClosedSections) {
		if (expandOpen == null)
			expandOpen = "";
		if (expandClosed == null)
			expandClosed = "";

		StringBuffer returnString = new StringBuffer();

		returnString.append("<script language=\"JavaScript1.2\">");
		/************************************************************************************/
		/* Define Variable and Create Function for Information within the Expanding Section */
		/*    To Display or not to Display                                                  */
		/************************************************************************************/
		returnString.append("var head=\"display:'none'\";");
		returnString.append("function doit(header)");
		returnString.append("{");
		returnString.append(" var head=header.style;");
		returnString.append(" if (head.display==\"none\")");
		returnString.append(" {");
		returnString.append("  head.display=\"\";");
		returnString.append(" }else{");
		returnString.append("  head.display=\"none\";");
		returnString.append(" }");
		returnString.append("}");
		/*************************************************************************************/
		/* Define Variable and Create Function for Changing Images for the Expanding Section */
		/*************************************************************************************/
		if (expandOpen.toUpperCase().equals("Y")) {
			returnString.append("var imageURL = new Array(");
			returnString.append(numberOfOpenSections);
			returnString.append(");");
			returnString.append("for (i = 0; i <= ");
			returnString.append(numberOfOpenSections);
			returnString.append("; i++)");
			returnString.append("{");
			returnString.append(
				" imageURL[i] = \"https://image.treetop.com/webapp/minusbox3.gif\";");
			returnString.append("}");
			returnString.append("function changeImage(recordCount,imageCount)");
			returnString.append("{");
			returnString.append(" i = recordCount;");
			returnString.append(" z = imageCount;");
			returnString.append(
				" if (imageURL[i]==\"https://image.treetop.com/webapp/plusbox3.gif\")");
			returnString.append(" {");
			returnString.append(
				"  imageURL[i] = \"https://image.treetop.com/webapp/minusbox3.gif\";");
			returnString.append(" }else{");
			returnString.append(
				"  imageURL[i] = \"https://image.treetop.com/webapp/plusbox3.gif\";");
			returnString.append(" }");
			returnString.append(" document.images[z].src = imageURL[i];");
			returnString.append("}");
		}
		if (expandClosed.toUpperCase().equals("Y")) {
			returnString.append("var imageURL1 = new Array(");
			returnString.append(numberOfClosedSections);
			returnString.append(");");
			returnString.append("for (i = 0; i <= ");
			returnString.append(numberOfClosedSections);
			returnString.append("; i++)");
			returnString.append("{");
			returnString.append(
				" imageURL1[i] = \"https://image.treetop.com/webapp/plusbox3.gif\";");
			returnString.append("}");
			returnString.append(
				"function changeImage1(recordCount,imageCount)");
			returnString.append("{");
			returnString.append(" i = recordCount;");
			returnString.append(" z = imageCount;");
			returnString.append(
				" if (imageURL1[i]==\"https://image.treetop.com/webapp/minusbox3.gif\")");
			returnString.append(" {");
			returnString.append(
				"  imageURL1[i] = \"https://image.treetop.com/webapp/plusbox3.gif\";");
			returnString.append(" }else{");
			returnString.append(
				"  imageURL1[i] = \"https://image.treetop.com/webapp/minusbox3.gif\";");
			returnString.append(" }");
			returnString.append(" document.images[z].src = imageURL1[i];");
			returnString.append("}");
		}
		returnString.append("</script>");

		return returnString.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for changing the words on the submit
	 *   button.
	 *	@deprecated
	 *  Retrieved this JavaScript from
	 *    The JavaScript Source -- http://javascript.internet.com
	 *    Original created by Mike Fernandez
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getChangeSubmitButton() {
		StringBuffer returnString = new StringBuffer();

		returnString.append("<script language=\"JavaScript\">");
		returnString.append("function submitForm(s) { ");
		returnString.append("s.value = \"  Processing Request...  \";");
		returnString.append("return true; ");
		returnString.append("} ");
		returnString.append("</script>");

		return returnString.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for Only allowing a button to be clicked ONE time.
	 * @deprecated
	 *  Retrieved this JavaScript from
	 *   Copyright 2000 by William and Will Bontrager.
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 */
	public static String getClickButtonOnlyOnce() {
		StringBuffer returnString = new StringBuffer();

		returnString.append("<script language=\"JavaScript\">");
		returnString.append("counter = 0; ");
		returnString.append("function countClicks() {");
		returnString.append("counter++;");
		returnString.append("if(counter > 1) {");
		returnString.append("alert(");
		returnString.append(
			"\"Processing your request may take some time. \\n");
		returnString.append("One click is sufficient. \\n");
		returnString.append("Thank you for your patience.\"); ");
		returnString.append("return false; ");
		returnString.append("} ");
		returnString.append("return true; ");
		returnString.append("} ");
		returnString.append("</script>");

		return returnString.toString();
	}
	/**
	 * This is the javascript which goes into
	 *  the head section, for validating a valid
	 *  email address.
	 *  Access it as an onblur in the email input field.
	 *
	 * This script and many more are available free online at 
	 *  The JavaScript Source!! http://javascript.internet.com 
	 *
	 * Creation date: (11/25/2003 11:11:28 AM)
	 * @return java.lang.String
	 * Moved from JavaScriptInfo to JavascriptInfo and changed to StringBuffer
	 *     4/19/06  -- TWalton
	 */
	public static String getEmailCheck() 
	{

		StringBuffer returnString = new StringBuffer();
		
		returnString.append("<script language=\"JavaScript\">");
		returnString.append("function checkEmail(emailField, name) ");
		returnString.append("{ ");
		returnString.append("if (/^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/.test(emailField.value) || ");
		returnString.append("emailField.value.length == 0)");
		returnString.append("{ return (true); } ");
		returnString.append("alert(\"Invalid E-mail Address! Please re-enter \" + name + \".\");");
		returnString.append("return (false);");
		returnString.append("}");
		returnString.append("</script>");
		
		return returnString.toString();
	}
	/**
	 * This is the javascript which will allow
	 *    one drop down box to be created from
	 *    the information chosen within the first
	 *    box.
	 *
	 *  This code is for drop down boxes which are pre-loaded,
	 *    and HAVE to have something chosen.
	 *
	 *   Send in the names of the drop down list which
	 *    will dynamically change.
	 *
	 * Creation date: (11/21/2003 9:28:36 AM)
	 * @return java.lang.String
	 *  Found script at ** www.fiendish.demon.co.uk/html/listfill.html **
	 *  Moved from JavaScriptInfo to JavascriptInfo - used StringBuffer -- 4/20/06 TWalton
	 */
	public static String getDynamicDropDownScript(String slaveName) 
	{
	//*************************************************************	
	// What else needs to be done to make this script work.
	//  ** Arrays have to be created in the HEAD Section
	//       ie:   <script language="javascript">
	//          var lists = new Array();
	//
	//          lists['01']    = new Array();
	//          lists['01'][0] = new Array(
	//  	                    'List',
	//	                        'Printer Friendly'
	//                           );
	//          lists['01'][1] = new Array(
	//	                        '01',
	//	                        '02'
	//                           );
	//
	//          lists['02']    = new Array();
	//          lists['02'][0] = new Array(
	//	                        'Printer Friendly'
	//                           );
	//          lists['02'][1] = new Array(
	//	                        '02'
	//                           );
	//               </script>
	//
	//** Load information into the Body Tag (To Pre-load the info)
	//   ie:<body onload="changeList(document.forms['name of the form'].name of master)">
	//
	//** How the select section will work
	//        <select name="template" size=1 onchange="changeList(this)"> ** template is the master name.
	//          <option value="01">Summary
	//          <option value="02">Detail
	//        </select>
	//        <select name="version" size=1>  ** version is the slave name.
	//        </select>
	//*************************************************************		
		StringBuffer returnString = new StringBuffer();
		returnString.append("<script language=\"javascript\">");
	
	   // This function goes through the options for the given
	   // drop down box and removes them in preparation for
	   // a new set of values
	   returnString.append("function emptyList( box ) ");
	   returnString.append("{ ");
	      
		   // Set each option to null thus removing it
	   returnString.append("while ( box.options.length ) box.options[0] = null; ");
	   returnString.append("} ");
	
	// This function assigns new drop down options to the given
	// drop down box from the list of lists specified
	   returnString.append("function fillList( box, arr ) ");
	   returnString.append("{");
	   
		// arr[0] holds the display text
		// arr[1] are the values
	      returnString.append("for ( i = 0; i < arr[0].length; i++ ) ");
	      returnString.append("{");
			// Create a new drop down option with the
			// display text and value from arr
	          returnString.append("option = new Option( arr[0][i], arr[1][i] );");
			// Add to the end of the existing options
	          returnString.append("box.options[box.length] = option;");
	          returnString.append("}");
		// Preselect option 0
	      returnString.append("box.selectedIndex=0;");
	      returnString.append("}");
	
	// This function performs a drop down list option change by first
	// emptying the existing option list and then assigning a new set
	  returnString.append("function changeList( box ) ");
	  returnString.append("{");
		// Isolate the appropriate list by using the value
		// of the currently selected option
	  returnString.append("list = lists[box.options[box.selectedIndex].value];");
		// Next empty the version list
	  returnString.append("emptyList( box.form.version );");
		// Then assign the new list values
	  returnString.append("fillList( box.form.version, list );");
	  returnString.append("}");
	  returnString.append("</script>");
	  
		return returnString.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for making a section visible or not visible
	 * 
	 *  Send in:	functionName 		- Name of the Function on the JSP
	 *          	valuesForSection1	- Values which will make section 1 display
	 * 				defaultSectionValue - Value to load the page, a default
	 * 				idSection1			- Value which relates directly to the 
	 * 									  id information for section 1.
	 * 				idSection2			- Value which relates directly to the 
	 * 									  id information for section 2.
	 * 				styleNameSection1	- Value which relates directly to the 
	 * 									  name of the style in section 1.
	 *  			styleNameSection2	- Value which relates directly to the 
	 * 									  name of the style in section 2.
	 * 
	 * Example of code for the JSP
	 *    -- head section
	 *       JavaScriptInfo.changeVisibility2Sections("JumpToIt", 
	 *                           					  arrayOfValues,
	 * 												  "01",
	 * 												  "foo",
	 * 												  "bar",
	 * 												  "displayFoo",
	 * 												  "displayBar");
	 * 
	 * <select name="thisOne" onChange="JumpToIt(this)">
	 *  <option value="foo">foo
	 *  <option value="bar">bar
	 * <select>
	 * <div id="foo" style="displayFoo:none" style=&{displayFoo};>now you see me...</div>
	 *
	 * <div id="bar" style="displayBar:none" style=&{displayBar};>...now you don't.</div> 
	 * 
	 * Moved from JavaScriptInfo to JavascriptInfo - changed to use StringBuffer - 4/19/06 TWalton
	 */
	public static String changeVisibility2Sections(String functionName,
												   String[] valuesForSection1,
	                                               String defaultSectionValue,
	                                               String idSection1,
	                                               String idSection2,
	                                               String styleNameSection1,
	                                               String styleNameSection2) 
	{
		
		StringBuffer valueInfo = new StringBuffer();
		try
		{
			int arrayLength = valuesForSection1.length;
			if (arrayLength > 0)
			{
				for (int x = 0; x < arrayLength; x++)
				{
					if (!valueInfo.toString().equals(""))
					   valueInfo.append(" || ");
					valueInfo.append("newPage == \"");
					valueInfo.append(valuesForSection1[x]);
					valueInfo.append("\" ");          
				}	
			}
			if (valueInfo.toString().equals(""))
				valueInfo.append("newPage == null");
		}
		catch(Exception e)
		{
			// just catch it.
		}
		
		StringBuffer returnString = new StringBuffer();
		returnString.append("<script language=\"JavaScript1.2\">");
		returnString.append("var " + styleNameSection1 + "=\"display:'none'\";");
		returnString.append("var " + styleNameSection2 + "=\"display:'none'\";");
		returnString.append("function " + functionName + "(list) ");
		returnString.append("{ ");
		returnString.append("var newPage = '" + defaultSectionValue + "'; ");
		returnString.append("if (list == null) ");
		returnString.append("newPage = '" + defaultSectionValue + "'; ");
		returnString.append(" else ");
		returnString.append("newPage = list.options[list.selectedIndex].value; ");
		returnString.append("var " + styleNameSection1 + "=" + idSection1 + ".style; ");
		returnString.append("var " + styleNameSection2 + "=" + idSection2 + ".style; ");
		returnString.append("if (" + valueInfo.toString() + ")");
		returnString.append("{ ");
		returnString.append(styleNameSection1 + ".display=\"\"; ");
		returnString.append(styleNameSection2 + ".display=\"none\"; ");
		returnString.append("} ");
		returnString.append("else ");
		returnString.append("{ ");
		returnString.append(styleNameSection1 + ".display=\"none\"; ");
		returnString.append(styleNameSection2 + ".display=\"\"; ");
		returnString.append("} ");
		returnString.append("}");
		returnString.append("</script>");
		
		return returnString.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code.  To be put where the Expanding 
	 *   section should be.
	 * *** REMEMBER to put the END of the span tag 
	 *       in the JSP where it goes.
	 *     ALSO you will need to use the get
	 *        getExpandingSectionHead method to get 
	 *        the javascript code which goes in
	 *        the <head> section
	 *	Send in:
	 *      String  expand       -O - Open, C - Closed (Which way to start)
	 * 			default open
	 *      String  sectionName  -Value displayed at the top of the expanding section
	 *                                this value can be a link.
	 *      int     fontSize     -Font size of the Title of the section
	 *  		default 12
	 * 		int     sectionNumber-Which Expanding section this is.
	 *      int     imageNumber  -Which Image on the screen this is.
	 *      int     expandObject = (1,2) Default will be 1 (2 for link as heading)
	 * 			default 1 (non link)
	 *      int     textColor    = Same as the stylesheet
	 * 			default RED 990000
	 * ---------------------------------------
	 * Create date: TWalton 7/23/2004
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 * ---------------------------------------
	 */
	public static String getExpandingSection(
		String expand,
		String sectionName,
		int fontSize,
		int sectionNumber,
		int imageNumber,
		int expandObject,
		int textColor) {
	
		//---------------------------------------
		// Open or Closed - Default Open
		//---------------------------------------
		String firstImage = "minusbox3.gif";
		String displayHead = "";
		String changeImage = "changeImage";
		if (expand != null && expand.equals("C")) {
			firstImage = "plusbox3.gif";
			displayHead = "style=\"display:none\" ";
			changeImage = "changeImage1";
		}
		//---------------------------------------
		// Link(2) or Not(1)
		//---------------------------------------
		if (expandObject == 0)
			expandObject = 1;
		//---------------------------------------
		// Determine Font Size default 12
		//---------------------------------------		  
		if (fontSize == 0)
			fontSize = 12;
		//---------------------------------------
		// Determine the Color of the Text
		//---------------------------------------
		String changeText = "990000";
		if (textColor == 1) //Pale Yellow
			changeText = "ffffcc";
		if (textColor == 2) //Light Olive
			changeText = "cccc99";
		if (textColor == 3) //Gold
			changeText = "d6b364";
		if (textColor == 4) //Dark Green
			changeText = "006400";
		if (textColor == 6) //Navy
			changeText = "003366";
		if (textColor == 7) //Black
			changeText = "000000";
	
		//---------------------------------------------
		// Build Code for Start of Expanding Section
		//    REMEMBER you must put an end span where you want to stop
		//---------------------------------------------  
	
		StringBuffer returnString = new StringBuffer();
	
		returnString.append("<div style=\"color:#");
		returnString.append(changeText); // What color is the font
		returnString.append(";");
		returnString.append("font-weight:bold;");
		returnString.append("font-family:arial;");
		returnString.append("font-size:");
		returnString.append(fontSize); // Size of the Font
		returnString.append("pt;");
		returnString.append("text-align:left;width:80%;\">");
		returnString.append(
			"&nbsp;<img src=\"https://image.treetop.com/webapp/");
		returnString.append(firstImage);
		// Which image to display first plus or minus
		returnString.append("\" ");
		returnString.append("style=\"cursor:hand\" ");
		returnString.append("onClick=\"doit(document.all[this.sourceIndex+");
		returnString.append(expandObject);
		returnString.append("]); ");
		returnString.append(changeImage);
		returnString.append("(");
		returnString.append(sectionNumber);
		returnString.append(",");
		returnString.append(imageNumber);
		returnString.append(");\">");
		returnString.append("&nbsp;&nbsp;");
		returnString.append(sectionName);
		returnString.append("</div>");
		returnString.append("<span ");
		returnString.append(displayHead);
		returnString.append("style=&{head};>");
	
		return returnString.toString();
	}
	/**
	 * Use this method to return a String,
	 *   this string includes the Javascript
	 *   code for generating a menu box, from and more button
	 * Update date: TWalton 7/28/2005 - 
	 * 				Moved from com.treetop.JavaScriptInfo Added StringBuffer
	 * 				TWalton 5/7/2008
	 * 				Changed the background colors to use the new style
	 */
	public static String getMoreButton() {
		StringBuffer returnString = new StringBuffer();
	
		//----------------------------------------
		// What the menu part will look like
		//----------------------------------------
		returnString.append("<style>");
		returnString.append(".spanstyle");
		returnString.append("{ ");
		returnString.append("position:absolute; ");
		returnString.append("z-index:100; ");
		returnString.append("background-color:#F3FAFF; ");
		returnString.append("width:250px; ");
		returnString.append("right:65; ");
		returnString.append("}");
		returnString.append("</style>");
		//************************************/
		//*  Display the Menu Options        */
		//************************************/
		returnString.append("<script language=\"JavaScript1.2\">");
		returnString.append("var head1=\"display:'none'\"; ");
		returnString.append("function viewmenu(header1)");
		returnString.append("{ ");
		returnString.append(" var head1=header1.style; ");
		returnString.append(" if (head1.display==\"none\") ");
		returnString.append(" { ");
		returnString.append("  head1.display=\"\"; ");
		returnString.append(" }else{ ");
		returnString.append("  head1.display=\"none\"; ");
		returnString.append(" } ");
		returnString.append("} ");
		returnString.append("</script>");
	
		return returnString.toString();
	}
	/**
	 * This is the javascript which will allow
	 *    one drop down box to be created from
	 *    the information chosen within the first
	 *    box.
	 * Using this javascript allows more than one per page.
	 *
	 *  This code is for drop down boxes which are pre-loaded,
	 *    and HAVE to have something chosen.
	 *
	 *   Send in the names of the drop down list which
	 *    will dynamically change.
	 *
	 * Creation date: (11/21/2003 9:28:36 AM)
	 * @return java.lang.String // Was called Dynamic Drop Down Script
	 *  Found script at ** www.fiendish.demon.co.uk/html/listfill.html **
	 * Update date: 8/12/05 TWalton
	 */
	public static String getDualDropDownNew(String masterName,
		                                    String slaveName) 
	{
	//*************************************************************	
	// What else needs to be done to make this script work.
	//  ** Arrays have to be created in the HEAD Section
	//       ie:   <script language="javascript">
	//          var lists = new Array();
	//
	//          lists['01']    = new Array();
	//          lists['01'][0] = new Array(
	//  	                    'List',
	//	                        'Printer Friendly'
	//                           );
	//          lists['01'][1] = new Array(
	//	                        '01',
	//	                        '02'
	//                           );
	//
	//          lists['02']    = new Array();
	//          lists['02'][0] = new Array(
	//	                        'Printer Friendly'
	//                           );
	//          lists['02'][1] = new Array(
	//	                        '02'
	//                           );
	//               </script>
	//
	//** Load information into the Body Tag (To Pre-load the info)
	//   ie:<body onload="changeList(document.forms['name of the form'].name of master)">
	//
	//** How the select section will work
	//        <select name="template" size=1 onchange="changeList(this)"> ** template is the master name.
	//          <option value="01">Summary
	//          <option value="02">Detail
	//        </select>
	//        <select name="version" size=1>  ** version is the slave name.
	//        </select>
	//*************************************************************
	    StringBuffer returnScript = new StringBuffer();
	    returnScript.append("<script language=\"javascript\">");
	
	   // This function goes through the options for the given
	   // drop down box and removes them in preparation for
	   // a new set of values
	
	   returnScript.append("function emptyList( box ) { ");
	      
		   // Set each option to null thus removing it
	   returnScript.append("while ( box.options.length )");
	   returnScript.append(" box.options[0] = null; } ");
	
	// This function assigns new drop down options to the given
	// drop down box from the list of lists specified
	
	   returnScript.append("function fillList( box, arr ) {");
	
		// arr[0] holds the display text
		// arr[1] are the values
		returnScript.append("for ( i = 0; i < arr[0].length; i++ ) {");
		
			// Create a new drop down option with the
			// display text and value from arr
		returnScript.append("option = new Option( arr[0][i], arr[1][i] );");
			// Add to the end of the existing options
		returnScript.append("box.options[box.length] = option;}");
		// Preselect option 0
		returnScript.append("box.selectedIndex=0; }");
	
	// This function performs a drop down list option change by first
	// emptying the existing option list and then assigning a new set
	    
	    returnScript.append("function change");
	    returnScript.append(masterName);
	    returnScript.append("( box ) {");
	    
		// Isolate the appropriate list by using the value
		// of the currently selected option
		returnScript.append("list = lists" + masterName + "[box.options[box.selectedIndex].value];");
		// Next empty the version list
		returnScript.append("emptyList( box.form.");
		returnScript.append(slaveName);
		returnScript.append(" );");
		// Then assign the new list values
		returnScript.append("fillList( box.form.");
		returnScript.append(slaveName);
		returnScript.append(", list ); }");
	    returnScript.append("</script>");
	
		return returnScript.toString();
	}
	/**
	 * This is the javascript which will allow
	 *    one drop down box to be created from
	 *    the information chosen within the first
	 *    box.
	 * Using this javascript allows more than one per page.
	 *
	 *  This code is for drop down boxes which are pre-loaded,
	 *    and HAVE to have something chosen.
	 *
	 *   Send in the names of the drop down list which
	 *    will dynamically change.
	 *
	 * Creation date: (4/13/2011 twalto)
	 *  Code was copied from originally :
     *    Triple Combo Script Credit
     *    By Philip M: http://www.codingforums.com/member.php?u=186
     *    Visit http://javascriptkit.com for this and over 400+ other scripts
     *    
	 * Update date: 
	 */
	public static String getTripleDropDown(String formName,
										   String list1Name,
		                                   String list2Name,
		                                   String list3Name) 
	{
	//*************************************************************	
	// What else needs to be done to make this script work.
	//  ** Arrays have to be created in the HEAD Section --
	//   use the DropDownTriple Utility to create
	//
	//** Load information into the Body Tag (To Pre-load the info)
	//   ie:<body onload="changeList(document.forms['name of the form'].name of master)">
	//
	//*************************************************************
	    StringBuffer returnScript = new StringBuffer();
	    returnScript.append("<script language=\"javascript\">");
	    
	    // number of selected lists in the set
	    returnScript.append("var nLists = 3; "); 
	    
	       // this function is used on every click of the list
	       // it fills the selection information in for the next box
		
		returnScript.append("function fillSelect(currCat,currList) { ");
		
		   // Which step in the drop down lists are we on?
		   returnScript.append("var step = Number(currList.name.replace(/\\D/g,\"\"));");
		   // Build the lists from the right starting point
		   returnScript.append("if (step == 1){");
		     returnScript.append("document.forms['" + formName + "']");
		      returnScript.append("['" + list1Name + "'].length = 1;");
		     returnScript.append("document.forms['" + formName + "']");
		      returnScript.append("['" + list1Name + "'].selectedIndex = 0;");
		   returnScript.append("}");	   
		   returnScript.append("if (step == 1 || step == 2){");
		     returnScript.append("document.forms['" + formName + "']");
		      returnScript.append("['" + list2Name + "'].length = 1;");
		     returnScript.append("document.forms['" + formName + "']");
		      returnScript.append("['" + list2Name + "'].selectedIndex = 0;");
		   returnScript.append("}");
		   returnScript.append("document.forms['" + formName + "']");
		    returnScript.append("['" + list3Name + "'].length = 1;");
		   returnScript.append("document.forms['" + formName + "']");
		    returnScript.append("['" + list3Name + "'].selectedIndex = 0;");
		    // format each category
		   returnScript.append("var nCat = categories[currCat];");
		   returnScript.append("for (each in nCat) {");
		   returnScript.append("var nOption = document.createElement('option'); ");
		   returnScript.append("var nData = document.createTextNode(nCat[each]); ");
		   returnScript.append("nOption.setAttribute('value',nCat[each]); ");
		   returnScript.append("nOption.appendChild(nData); ");
		   returnScript.append("currList.appendChild(nOption); ");
		   returnScript.append("} ");

		returnScript.append("} ");

		// Function to initially LOAD the beginning list
		returnScript.append("function init() {");
		returnScript.append("fillSelect('startList',document.");
		returnScript.append("forms['" + formName + "']['" + list1Name + "'])");
		returnScript.append("} ");
		
		// Initial Load of Page
		returnScript.append("navigator.appName == \"Microsoft Internet Explorer\" ? ");
		returnScript.append("attachEvent('onload', init, false) : addEventListener('load', init, false);");	

	    returnScript.append("</script>"); 

	    return returnScript.toString();
	}

}
