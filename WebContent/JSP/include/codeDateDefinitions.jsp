<%
// 
//  Retrieve the set attributes.
   String ownerCode = (String) request.getAttribute("ownerCode");
   if (ownerCode == null)
     ownerCode = "TT";
   String ownerDescription = (String) request.getAttribute("ownerDescription");
    if (ownerDescription == null)
     ownerDescription = "Tree Top";  
   String productionDate = (String) request.getAttribute("productionDate");
   if (productionDate == null)
     productionDate = "";
   String codeDateValue = (String) request.getAttribute("codeDateValue");
   if (codeDateValue == null)
     codeDateValue = "";
%>
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="https://image.treetop.com/webapp/Stylesheet.css" />
   </head>
   <body>
<%
//*******************************************************************************************
//*******************************************************************************************
//   Description includes ALL of the Owner codes except for:
//          TROPICANA
//			NORTHLAND
  if (!ownerCode.equals("TR") &&    // Tropicana
      !ownerCode.equals("NT") &&    // Northland
      !ownerCode.equals("JM") &&    // Japanese
      !ownerCode.equals("OS") &&    // Ocean Spray
      !ownerCode.equals("CA") &&    // Coke, Non-Carbonated Beverage Group
      !ownerCode.equals("CU") &&    // Coke, Non-Carbonated Beverage Group
      !ownerCode.equals("CF"))      // Coke Foods
  {
    String[] codeDateByCharacter = new String[10];
    for (int x = 0; x < 10; x++)
	{
       codeDateByCharacter[x] = "&nbsp;";
    }    
    int codeDateLength = codeDateValue.length();
    if (codeDateValue.length() > 0)
    {
    	for (int x = 0; x < codeDateValue.length(); x++)
	    {
	       String characterValue = codeDateValue.substring(x, (x + 1));
	       if (x < 5 ||
	           (!characterValue.equals("M") &&
	            !characterValue.equals("H")))   
	         codeDateByCharacter[x] = codeDateValue.substring(x, (x + 1));
	    }
	}
%>
  <table class="table00001" cellspacing="0" style="width:100%">
    <tr>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-left:1px solid #CCCC99">
        &nbsp;
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99">
        <table class="table00001" cellspacing="0">      		
          <tr class="tr02001">
            <td class="td041CL001" style="width:20%">
              <b>Owner Code:</b>
            </td>
            <td class="td041CL001">
              <b>
                <%= ownerCode.trim() %>&nbsp;-&nbsp;<%= ownerDescription %>            
              </b>
            </td>            
          </tr> 
          <tr>
            <td class="td071CC001" colspan="2">
              <table class="table010001" cellspacing="0" cellpadding="0" border="1">
                <tr class="tr01001">
                  <td class="td071CL001" colspan="13">
                   &nbsp;Production Date Used:  &nbsp;<b><%= productionDate %></b>
                  </td>  
                </tr>
                <tr class="tr01001">
                  <td class="td044CC001" style="width:12%"> 
                    Position:
                  </td>                
                  <td class="td044CC001" style="width:5%"> 
                    1
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    2
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    3
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    4
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    5
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    6
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    7
                  </td> 
                  <td class="td044CC001" style="width:5%">
                    &nbsp;
                  </td>
                   <td class="td044CC001" style="width:5%"> 
                    8
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    9
                  </td>  
                </tr>
                <tr class="tr00001">
                  <td class="td044CC001"> 
                    Code Type:
                  </td>                     
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    Y
                  </td>
                  <td class="td041CC001"> 
                    C
                  </td>       
                  <td class="td041CC001"> 
                    H
                  </td>  
                  <td class="td041CC001"> 
                    H
                  </td>  
                  <td class="td041CC001"> 
                    :
                  </td>                          
                  <td class="td041CC001"> 
                    M
                  </td>       
                  <td class="td041CC001"> 
                    M
                  </td>                                                                                                                                          
                </tr>   
                <tr class="tr01001"> 
                  <td class="td044CC001"> 
                    Code Value:
                  </td>                     
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[0] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[1] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[2] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[3] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[4] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[5] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[6] %></b>
                  </td>   
                   <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[7] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[8] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[8] %></b>
                  </td>                                                                                                                                                                    
                </tr>                             
              </table>  
            </td>
          </tr> 
          <tr>
            <td colspan="2" style="border-top:1px solid #006400"> 
              <table class="table00001" cellspacing="0">     
                <tr>
                  <td class="td044CL002" style="width:5%">
                    1
                  </td>  
                  <td class="td044CL002">
                    M
                  </td>                   
                  <td class="td044CL002">
                    Current Month (1-9, ABC)
                  </td>         

                </tr>
                <tr>  
                  <td class="td044CL002">
                    2 - 3
                  </td>  
                   <td class="td044CL002">
                    D
                  </td>                             
                  <td class="td044CL002">
                    Current Day
                  </td>         
                                                          
                </tr>
                 <tr>  
                  <td class="td044CL002">
                    4
                  </td>  
                 <td class="td044CL002">
                    Y (example, 9 for 99, 0 for 00, 1 for 01)
                  </td>                    
                  <td class="td044CL002">
                    Current Year (Standard)
                  </td>         
                                                                     
                </tr>
                <tr>  
                  <td class="td044CL002" valign="top">
                    5
                  </td>  
                   <td class="td044CL002" valign="top">
                    C
                  </td>                       
                  <td class="td044CL002">
                    Plant Code<br>
                    &nbsp;&nbsp;&nbsp;Based on Plant and Year combined, (Alpha Character 1 Long)<br>
                    &nbsp;&nbsp;&nbsp;(example, Selah = A, Ross 1999 = B, Ross 2002 = E)<br>
                    &nbsp;&nbsp;&nbsp;Found in File GNPQCODE
                  </td>         
                                                                
                </tr>
                <tr>  
                  <td class="td044CL002">
                    6 - 7
                  </td>  
                   <td class="td044CL002">
                    H
                  </td>                      
                  <td class="td044CL002">
                    Current Hour
                  </td>         
                                                                 
                </tr>  
                <tr>  
                 
                  <td class="td044CL002">
                    8 - 9
                  </td>  
                   <td class="td044CL002">
                    M
                  </td>                       
                  <td class="td044CL002">
                    Current Minute
                  </td>         
                                                               
                </tr>                                                                 
              </table>
            </td>
          </tr>     
        </table>
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-right:1px solid #CCCC99">
        &nbsp;
      </td>
    </tr>
  </table>
<%
   }
//*******************************************************************************************
//*******************************************************************************************
//    TROPICANA
  if (ownerCode.equals("TR"))     // Tropicana
  {
    String[] codeDateByCharacter = new String[10];
    for (int x = 0; x < 10; x++)
	{
       codeDateByCharacter[x] = "&nbsp;";
    }    
    int codeDateLength = codeDateValue.length();
    if (codeDateValue.length() > 0)
    {
    	for (int x = 0; x < codeDateValue.length(); x++)
	    {
	       String characterValue = codeDateValue.substring(x, (x + 1));
	       if (x < 8 ||
	           !characterValue.equals("B"))   
	         codeDateByCharacter[x] = codeDateValue.substring(x, (x + 1));
	    }
	}
%>
  <table class="table00001" cellspacing="0" style="width:100%">
    <tr>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-left:1px solid #CCCC99">
        &nbsp;
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99">
        <table class="table00001" cellspacing="0">      		
          <tr class="tr02001">
            <td class="td041CL001" style="width:20%">
              <b>Owner Code:</b>
            </td>
            <td class="td041CL001">
              <b>
                <%= ownerCode.trim() %>&nbsp;-&nbsp;<%= ownerDescription %>            
              </b>
            </td>            
          </tr> 
          <tr>
            <td class="td071CC001" colspan="2">
              <table class="table010001" cellspacing="0" cellpadding="0" border="1">
                <tr class="tr01001">
                  <td class="td071CL001" colspan="13">
                   &nbsp;Production Date Used:  &nbsp;<b><%= productionDate %></b>
                  </td>  
                </tr>
                <tr class="tr01001">
                  <td class="td044CC001" style="width:12%"> 
                    Position:
                  </td>                
                  <td class="td044CC001" style="width:5%"> 
                    1
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    2
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    3
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    4
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    5
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    6
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    7
                  </td> 
                  <td class="td044CC001" style="width:5%">
                    8
                  </td>
                   <td class="td044CC001" style="width:5%"> 
                    9
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    10
                  </td>  
                </tr>
                <tr class="tr00001">
                  <td class="td044CC001"> 
                    Code Type:
                  </td>                     
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>       
                  <td class="td041CC001"> 
                    Y
                  </td>  
                  <td class="td041CC001"> 
                    Y
                  </td>  
                  <td class="td041CC001"> 
                    S
                  </td>                          
                  <td class="td041CC001"> 
                    B
                  </td>       
                  <td class="td041CC001"> 
                    B
                  </td>                                                                                                                                          
                </tr>   
                <tr class="tr01001"> 
                  <td class="td044CC001"> 
                    Code Value:
                  </td>                     
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[0] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[1] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[2] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[3] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[4] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[5] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[6] %></b>
                  </td>   
                   <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[7] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[8] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[9] %></b>
                  </td>                                                                                                                                                                    
                </tr>                             
              </table>  
            </td>
          </tr> 
          <tr>
            <td colspan="2" style="border-top:1px solid #006400"> 
              <table class="table00001" cellspacing="0">     
                <tr>
                  <td class="td044CL002" style="width:5%">
                    1 - 3
                  </td>  
                  <td class="td044CL002">
                    M (example JAN, FEB, MAR)
                  </td>                   
                  <td class="td044CL002">
                    Month (6Mo from Current Monday)
                  </td>         

                </tr>
                <tr>  
                  <td class="td044CL002">
                    4 - 5
                  </td>  
                   <td class="td044CL002">
                    D
                  </td>                             
                  <td class="td044CL002">
                    Day (6Mo from Current Monday)
                  </td>         
                </tr>
                 <tr>  
                  <td class="td044CL002">
                    6 - 7
                  </td>  
                 <td class="td044CL002">
                    Y
                  </td>                    
                  <td class="td044CL002">
                    Year (6Mo from Current Monday)
                  </td>                                              
                </tr>
                <tr>  
                  <td class="td044CL002" valign="top">
                    8
                  </td>  
                   <td class="td044CL002" valign="top">
                    S
                  </td>                       
                  <td class="td044CL002">
                    Shift (Based on Actual Time and Date)
                  </td>                                         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    9 - 10
                  </td>  
                   <td class="td044CL002">
                    B
                  </td>                      
                  <td class="td044CL002">
                    Blank
                  </td>                                          
                </tr>  
              </table>
            </td>
          </tr>     
        </table>
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-right:1px solid #CCCC99">
        &nbsp;
      </td>
    </tr>
  </table>
<%
   }
//*******************************************************************************************
//*******************************************************************************************
//    NORTHLAND
  if (ownerCode.equals("NT"))     // Northland
  {
    String[] codeDateByCharacter = new String[5];
    for (int x = 0; x < 5; x++)
	{
       codeDateByCharacter[x] = "&nbsp;";
    }    
    int codeDateLength = codeDateValue.length();
    if (codeDateValue.length() > 0)
    {
    	for (int x = 0; x < codeDateValue.length(); x++)
	    {
	       codeDateByCharacter[x] = codeDateValue.substring(x, (x + 1));
	    }
	}
%>
  <table class="table00001" cellspacing="0" style="width:100%">
    <tr>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-left:1px solid #CCCC99">
        &nbsp;
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99">
        <table class="table00001" cellspacing="0">      		
          <tr class="tr02001">
            <td class="td041CL001" style="width:20%">
              <b>Owner Code:</b>
            </td>
            <td class="td041CL001">
              <b>
                <%= ownerCode.trim() %>&nbsp;-&nbsp;<%= ownerDescription %>            
              </b>
            </td>            
          </tr> 
          <tr>
            <td class="td071CC001" colspan="2">
              <table class="table010001" cellspacing="0" cellpadding="0" border="1">
                <tr class="tr01001">
                  <td class="td071CL001" colspan="8">
                   &nbsp;Production Date Used:  &nbsp;<b><%= productionDate %></b>
                  </td>  
                </tr>
                <tr class="tr01001">
                  <td class="td044CC001" style="width:12%"> 
                    Position:
                  </td>                
                  <td class="td044CC001" style="width:5%"> 
                    1
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    2
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    3
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    4
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    5
                  </td> 
                </tr>
                <tr class="tr00001">
                  <td class="td044CC001"> 
                    Code Type:
                  </td>                     
                  <td class="td041CC001"> 
                    P
                  </td>
                  <td class="td041CC001"> 
                    Y
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>       
                </tr>   
                <tr class="tr01001"> 
                  <td class="td044CC001"> 
                    Code Value:
                  </td>                     
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[0] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[1] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[2] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[3] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[4] %></b>
                  </td>   
                </tr>                             
              </table>  
            </td>
          </tr> 
          <tr>
            <td colspan="2" style="border-top:1px solid #006400"> 
              <table class="table00001" cellspacing="0">     
                <tr>
                  <td class="td044CL002" style="width:5%">
                    1
                  </td>  
                  <td class="td044CL002">
                    P
                  </td>                   
                  <td class="td044CL002">
                    'P'
                  </td>         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    2
                  </td>  
                   <td class="td044CL002">
                    Y (example, 9 for 99, 0 for 00, 1 for 01)
                  </td>                             
                  <td class="td044CL002">
                    Current Year (Julian)
                  </td>         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    3 - 5
                  </td>  
                 <td class="td044CL002">
                    D
                  </td>                    
                  <td class="td044CL002">
                    Current Day (Julian)
                  </td>                                              
                </tr>
              </table>
            </td>
          </tr>     
        </table>
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-right:1px solid #CCCC99">
        &nbsp;
      </td>
    </tr>
  </table>
<%
   }
//*******************************************************************************************
//*******************************************************************************************
//    COKE, NON-CARBONATED BEVERAGE GROUP 
  if (ownerCode.equals("CA") ||
      ownerCode.equals("CU"))     // Coke Non-Carbonated Beverage Group
  {
    String[] codeDateByCharacter = new String[7];
    for (int x = 0; x < 7; x++)
	{
       codeDateByCharacter[x] = "&nbsp;";
    }    
    int codeDateLength = codeDateValue.length();
    if (codeDateValue.length() > 0)
    {
    	for (int x = 0; x < codeDateValue.length(); x++)
	    {
           codeDateByCharacter[x] = codeDateValue.substring(x, (x + 1));
	    }
	}
%>
  <table class="table00001" cellspacing="0" style="width:100%">
    <tr>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-left:1px solid #CCCC99">
        &nbsp;
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99">
        <table class="table00001" cellspacing="0">      		
          <tr class="tr02001">
            <td class="td041CL001" style="width:20%">
              <b>Owner Code:</b>
            </td>
            <td class="td041CL001">
              <b>
                <%= ownerCode.trim() %>&nbsp;-&nbsp;<%= ownerDescription %>            
              </b>
            </td>            
          </tr> 
          <tr>
            <td class="td071CC001" colspan="2">
              <table class="table010001" cellspacing="0" cellpadding="0" border="1">
                <tr class="tr01001">
                  <td class="td071CL001" colspan="13">
                   &nbsp;Production Date Used:  &nbsp;<b><%= productionDate %></b>
                  </td>  
                </tr>
                <tr class="tr01001">
                  <td class="td044CC001" style="width:12%"> 
                    Position:
                  </td>                
                  <td class="td044CC001" style="width:5%"> 
                    1
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    2
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    3
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    4
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    5
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    6
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    7
                  </td> 
                </tr>
                <tr class="tr00001">
                  <td class="td044CC001"> 
                    Code Type:
                  </td>                     
                  <td class="td041CC001"> 
                    J
                  </td>
                  <td class="td041CC001"> 
                    J
                  </td>
                  <td class="td041CC001"> 
                    J
                  </td>
                  <td class="td041CC001"> 
                    J
                  </td>
                  <td class="td041CC001"> 
                    J
                  </td>       
                  <td class="td041CC001"> 
                    C
                  </td>  
                  <td class="td041CC001"> 
                    A
                  </td>  
                </tr>   
                <tr class="tr01001"> 
                  <td class="td044CC001"> 
                    Code Value:
                  </td>                     
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[0] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[1] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[2] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[3] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[4] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[5] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[6] %></b>
                  </td>   
                </tr>                             
              </table>  
            </td>
          </tr> 
          <tr>
            <td colspan="2" style="border-top:1px solid #006400"> 
              <table class="table00001" cellspacing="0">     
                <tr>
                  <td class="td044CL002" style="width:5%">
                    1 - 5
                  </td>  
                  <td class="td044CL002">
                    J (Year and Date) 
                  </td>                   
                  <td class="td044CL002">
                    Current Julian Date
                  </td>         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    6
                  </td>  
                   <td class="td044CL002">
                    C
                  </td>                             
                  <td class="td044CL002">
                    'C'
                  </td>         
                </tr>
                 <tr>  
                  <td class="td044CL002">
                    7
                  </td>  
                 <td class="td044CL002">
                    A
                  </td>                    
                  <td class="td044CL002">
                    'A'
                  </td>                                              
                </tr>
              </table>
            </td>
          </tr>     
        </table>
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-right:1px solid #CCCC99">
        &nbsp;
      </td>
    </tr>
  </table>
<%
   }
//*******************************************************************************************
//*******************************************************************************************
//    OCEAN SPRAY
  if (ownerCode.equals("OS"))     // Ocean Spray
  {
    String[] codeDateByCharacter = new String[11];
    for (int x = 0; x < 11; x++)
	{
       codeDateByCharacter[x] = "&nbsp;";
    }    
    int codeDateLength = codeDateValue.length();
    if (codeDateValue.length() > 0)
    {
    	for (int x = 0; x < codeDateValue.length(); x++)
	    {
	       String characterValue = codeDateValue.substring(x, (x + 1));
	       if (x < 6 ||
	           (!characterValue.equals("M") &&
	            !characterValue.equals("H")))   
	         codeDateByCharacter[x] = codeDateValue.substring(x, (x + 1));
	    }
	}
%>
  <table class="table00001" cellspacing="0" style="width:100%">
    <tr>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-left:1px solid #CCCC99">
        &nbsp;
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99">
        <table class="table00001" cellspacing="0">      		
          <tr class="tr02001">
            <td class="td041CL001" style="width:20%">
              <b>Owner Code:</b>
            </td>
            <td class="td041CL001">
              <b>
                <%= ownerCode.trim() %>&nbsp;-&nbsp;<%= ownerDescription %>            
              </b>
            </td>            
          </tr> 
          <tr>
            <td class="td071CC001" colspan="2">
              <table class="table010001" cellspacing="0" cellpadding="0" border="1">
                <tr class="tr01001">
                  <td class="td071CL001" colspan="13">
                   &nbsp;Production Date Used:  &nbsp;<b><%= productionDate %></b>
                  </td>  
                </tr>
                <tr class="tr01001">
                  <td class="td044CC001" style="width:12%"> 
                    Position:
                  </td>                
                  <td class="td044CC001" style="width:5%"> 
                    1
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    2
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    3
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    4
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    5
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    6
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    7
                  </td> 
                  <td class="td044CC001" style="width:5%">
                    8
                  </td>
                  <td class="td044CC001" style="width:5%">
                    &nbsp;
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    9
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    10
                  </td>  
                </tr>
                <tr class="tr00001">
                  <td class="td044CC001"> 
                    Code Type:
                  </td>                     
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    Y
                  </td>       
                  <td class="td041CC001"> 
                    Y
                  </td>  
                  <td class="td041CC001"> 
                    H
                  </td>  
                  <td class="td041CC001"> 
                    H
                  </td>   
                  <td class="td041CC001"> 
                    :
                  </td>                                               
                  <td class="td041CC001"> 
                    M
                  </td>       
                  <td class="td041CC001"> 
                    M
                  </td>                                                                                                                                          
                </tr>   
                <tr class="tr01001"> 
                  <td class="td044CC001"> 
                    Code Value:
                  </td>                     
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[0] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[1] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[2] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[3] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[4] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[5] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[6] %></b>
                  </td>   
                   <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[7] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[8] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[9] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[10] %></b>
                  </td>                                                                                                                                                                                            
                </tr>                             
              </table>  
            </td>
          </tr> 
          <tr>
            <td colspan="2" style="border-top:1px solid #006400"> 
              <table class="table00001" cellspacing="0">     
                <tr>
                  <td class="td044CL002" style="width:5%">
                    1 - 2
                  </td>  
                  <td class="td044CL002">
                    M 
                  </td>                   
                  <td class="td044CL002">
                    Current Month 
                  </td>         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    3 - 4
                  </td>  
                   <td class="td044CL002">
                    D
                  </td>                             
                  <td class="td044CL002">
                    Current Day
                  </td>         
                </tr>
                 <tr>  
                  <td class="td044CL002">
                    5 - 6
                  </td>  
                 <td class="td044CL002">
                    Y
                  </td>                    
                  <td class="td044CL002">
                    Current Year 
                  </td>                                              
                </tr>
                <tr>  
                  <td class="td044CL002" valign="top">
                    7 - 8
                  </td>  
                   <td class="td044CL002" valign="top">
                    H
                  </td>                       
                  <td class="td044CL002">
                    Current Hour
                  </td>                                         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    9 - 10
                  </td>  
                   <td class="td044CL002">
                    M
                  </td>                      
                  <td class="td044CL002">
                    Current Minute
                  </td>                                          
                </tr>  
              </table>
            </td>
          </tr>     
        </table>
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-right:1px solid #CCCC99">
        &nbsp;
      </td>
    </tr>
  </table>
<%
   }  
//*******************************************************************************************
//*******************************************************************************************
//    COKE FOODS
  if (ownerCode.equals("CF"))     // Coke Foods
  {
    String[] codeDateByCharacter = new String[10];
    for (int x = 0; x < 10; x++)
	{
       codeDateByCharacter[x] = "&nbsp;";
    }    
    int codeDateLength = codeDateValue.length();
    if (codeDateValue.length() > 0)
    {
    	for (int x = 0; x < codeDateValue.length(); x++)
	    {
	       String characterValue = codeDateValue.substring(x, (x + 1));
	       if (x < 7 ||
	           !characterValue.equals("B"))   
	         codeDateByCharacter[x] = codeDateValue.substring(x, (x + 1));
	    }
	}
%>
  <table class="table00001" cellspacing="0" style="width:100%">
    <tr>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-left:1px solid #CCCC99">
        &nbsp;
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99">
        <table class="table00001" cellspacing="0">      		
          <tr class="tr02001">
            <td class="td041CL001" style="width:20%">
              <b>Owner Code:</b>
            </td>
            <td class="td041CL001">
              <b>
                <%= ownerCode.trim() %>&nbsp;-&nbsp;<%= ownerDescription %>            
              </b>
            </td>            
          </tr> 
          <tr>
            <td class="td071CC001" colspan="2">
              <table class="table010001" cellspacing="0" cellpadding="0" border="1">
                <tr class="tr01001">
                  <td class="td071CL001" colspan="13">
                   &nbsp;Production Date Used:  &nbsp;<b><%= productionDate %></b>
                  </td>  
                </tr>
                <tr class="tr01001">
                  <td class="td044CC001" style="width:12%"> 
                    Position:
                  </td>                
                  <td class="td044CC001" style="width:5%"> 
                    1
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    2
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    3
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    4
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    5
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    6
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    7
                  </td> 
                  <td class="td044CC001" style="width:5%">
                    8
                  </td>
                  <td class="td044CC001" style="width:5%">
                    9
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    10
                  </td> 
                </tr>
                <tr class="tr00001">
                  <td class="td044CC001"> 
                    Code Type:
                  </td>                     
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    M
                  </td>       
                  <td class="td041CC001"> 
                    Y
                  </td>  
                  <td class="td041CC001"> 
                    Y
                  </td>   
                  <td class="td041CC001"> 
                    B
                  </td>                                               
                  <td class="td041CC001"> 
                    B
                  </td>       
                  <td class="td041CC001"> 
                    B
                  </td>                                                                                                                                          
                </tr>   
                <tr class="tr01001"> 
                  <td class="td044CC001"> 
                    Code Value:
                  </td>                     
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[0] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[1] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[2] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[3] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[4] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[5] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[6] %></b>
                  </td>   
                   <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[7] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[8] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[9] %></b>
                  </td> 
                </tr>                             
              </table>  
            </td>
          </tr> 
          <tr>
            <td colspan="2" style="border-top:1px solid #006400"> 
              <table class="table00001" cellspacing="0">     
                <tr>
                  <td class="td044CL002" style="width:5%">
                    1 - 2
                  </td>  
                  <td class="td044CL002">
                    D 
                  </td>                   
                  <td class="td044CL002">
                    Current Day
                  </td>         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    3 - 5
                  </td>  
                   <td class="td044CL002">
                    M (example JAN, FEB, MAR)
                  </td>                             
                  <td class="td044CL002">
                    Current Month
                  </td>         
                </tr>
                 <tr>  
                  <td class="td044CL002">
                    6 - 7
                  </td>  
                 <td class="td044CL002">
                    Y
                  </td>                    
                  <td class="td044CL002">
                    Current Year 
                  </td>                                              
                </tr>
                <tr>  
                  <td class="td044CL002" valign="top">
                    8 - 10
                  </td>  
                   <td class="td044CL002" valign="top">
                    B
                  </td>                       
                  <td class="td044CL002">
                    Blank
                  </td>                                         
                </tr>
              </table>
            </td>
          </tr>     
        </table>
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-right:1px solid #CCCC99">
        &nbsp;
      </td>
    </tr>
  </table>
<%
   } 
//*******************************************************************************************
//*******************************************************************************************
//    JAPANESE
  if (ownerCode.equals("JM"))     // Japanese
  {
    String[] codeDateByCharacter = new String[11];
    for (int x = 0; x < 11; x++)
	{
       codeDateByCharacter[x] = "&nbsp;";
    }    
    int codeDateLength = codeDateValue.length();
    if (codeDateValue.length() > 0)
    {
    	for (int x = 0; x < codeDateValue.length(); x++)
	    {
	       String characterValue = codeDateValue.substring(x, (x + 1));
	       if (x < 6 ||
	           (!characterValue.equals("M") &&
	            !characterValue.equals("H")))   
	         codeDateByCharacter[x] = codeDateValue.substring(x, (x + 1));
	    }
	}
%>
  <table class="table00001" cellspacing="0" style="width:100%">
    <tr>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-left:1px solid #CCCC99">
        &nbsp;
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99">
        <table class="table00001" cellspacing="0">      		
          <tr class="tr02001">
            <td class="td041CL001" style="width:20%">
              <b>Owner Code:</b>
            </td>
            <td class="td041CL001">
              <b>
                <%= ownerCode.trim() %>&nbsp;-&nbsp;<%= ownerDescription %>            
              </b>
            </td>            
          </tr> 
          <tr>
            <td class="td071CC001" colspan="2">
              <table class="table010001" cellspacing="0" cellpadding="0" border="1">
                <tr class="tr01001">
                  <td class="td071CL001" colspan="13">
                   &nbsp;Production Date Used:  &nbsp;<b><%= productionDate %></b>
                  </td>  
                </tr>
                <tr class="tr01001">
                  <td class="td044CC001" style="width:12%"> 
                    Position:
                  </td>                
                  <td class="td044CC001" style="width:5%"> 
                    1
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    2
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    3
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    4
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    5
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    6
                  </td> 
                  <td class="td044CC001" style="width:5%"> 
                    7
                  </td> 
                  <td class="td044CC001" style="width:5%">
                    8
                  </td>
                  <td class="td044CC001" style="width:5%">
                    &nbsp;
                  </td>
                  <td class="td044CC001" style="width:5%"> 
                    9
                  </td> 
                   <td class="td044CC001" style="width:5%"> 
                    10
                  </td>  
                </tr>
                <tr class="tr00001">
                  <td class="td044CC001"> 
                    Code Type:
                  </td>                     
                  <td class="td041CC001"> 
                    Y
                  </td>
                  <td class="td041CC001"> 
                    Y
                  </td>
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    M
                  </td>
                  <td class="td041CC001"> 
                    D
                  </td>       
                  <td class="td041CC001"> 
                    D
                  </td>  
                  <td class="td041CC001"> 
                    H
                  </td>  
                  <td class="td041CC001"> 
                    H
                  </td>   
                  <td class="td041CC001"> 
                    :
                  </td>                                               
                  <td class="td041CC001"> 
                    M
                  </td>       
                  <td class="td041CC001"> 
                    M
                  </td>                                                                                                                                          
                </tr>   
                <tr class="tr01001"> 
                  <td class="td044CC001"> 
                    Code Value:
                  </td>                     
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[0] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[1] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[2] %></b>
                  </td>
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[3] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[4] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[5] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[6] %></b>
                  </td>   
                   <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[7] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[8] %></b>
                  </td>   
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[9] %></b>
                  </td> 
                  <td class="td071CC001"> 
                    <b><%= codeDateByCharacter[10] %></b>
                  </td>                                                                                                                                                                                            
                </tr>                             
              </table>  
            </td>
          </tr> 
          <tr>
            <td colspan="2" style="border-top:1px solid #006400"> 
              <table class="table00001" cellspacing="0">     
                <tr>
                  <td class="td044CL002" style="width:5%">
                    1 - 2
                  </td>  
                  <td class="td044CL002">
                    Y 
                  </td>                   
                  <td class="td044CL002">
                    Current Year 
                  </td>         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    3 - 4
                  </td>  
                   <td class="td044CL002">
                    M
                  </td>                             
                  <td class="td044CL002">
                    Current Month
                  </td>         
                </tr>
                 <tr>  
                  <td class="td044CL002">
                    5 - 6
                  </td>  
                 <td class="td044CL002">
                    D
                  </td>                    
                  <td class="td044CL002">
                    Current Day
                  </td>                                              
                </tr>
                <tr>  
                  <td class="td044CL002" valign="top">
                    7 - 8
                  </td>  
                   <td class="td044CL002" valign="top">
                    H
                  </td>                       
                  <td class="td044CL002">
                    Current Hour
                  </td>                                         
                </tr>
                <tr>  
                  <td class="td044CL002">
                    9 - 10
                  </td>  
                   <td class="td044CL002">
                    M
                  </td>                      
                  <td class="td044CL002">
                    Current Minute
                  </td>                                          
                </tr>  
              </table>
            </td>
          </tr>     
        </table>
      </td>
      <td class="td041CC002" style="border-top:1px solid #CCCC99;
      								border-right:1px solid #CCCC99">
        &nbsp;
      </td>
    </tr>
  </table>
<%
   }      
%>
   </body>
</html>
