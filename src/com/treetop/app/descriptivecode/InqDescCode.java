package com.treetop.app.descriptivecode;

import java.util.*;
import com.treetop.viewbeans.*;
import com.treetop.services.ServiceDescriptiveCode;
import com.treetop.SessionVariables;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InqDescCode extends BaseViewBeanR1 {
	
	public String	requestType	= "";
	
	public String	inqUser 		= "";
	public String[]	inqRoles		= {""};
	public String[]	inqGroups		= {""};
	public String	inqSearch		= "";
	
	public String	inqType			= "";
	public String	inqTypeDescr80	= "";
	public String	inqTypeDescr40	= "";
	
	public String	inqKey00		= "";
	public String	inqKey01		= "";
	public String	inqKey02		= "";
	public String	inqKey03		= "";
	public String	inqKey04		= "";
	public String	inqKey05		= "";
	public String	inqKey06		= "";
	public String	inqKey07		= "";
	public String	inqKey08		= "";
	public String	inqKey09		= "";
	public String	inqKey10		= "";
	
	public String 	inqKeyStr		= "";
	
	public String	inqDescrLong	= "";
	public String	inqDescrShort	= "";
	
	public String	inqMessage		= "";
	
	public String	inqAlpha01		= "";
	public String	inqAlpha02		= "";
	public String	inqAlpha03		= "";
	public String	inqAlpha04		= "";
	public String	inqAlpha05		= "";
	public String	inqAlphaStr	= ""; 
	
	public String	inqNum01		= "";
	public String	inqNum02		= "";
	public String	inqNum03		= "";
	public String	inqNum04		= "";
	public String	inqNum05		= "";
	
	public String	inqErrMsg		= "";
	public String	inqMoreButton	= "";

	public String getInqMoreButton() {
		return inqMoreButton;
	}

	public void setInqMoreButton(String inqMoreButton) {
		this.inqMoreButton = inqMoreButton;
	}

	public void validate(){
		if (!this.getInqKey00().equals("") && (this.getInqTypeDescr80().equals("") || this.getInqTypeDescr40().equals(""))) {
			ServiceDescriptiveCode.getDescriptiveCodeHeaderDescr(this);
		}
		if (this.getInqType() == null || this.getInqType().equals("")) {
			this.setInqType("V");
		}
	}
	
	public void validate(HttpServletRequest request, HttpServletResponse response){
		validate();
		
		if (SessionVariables.getSessionttiProfile(request, response) != null) {
			this.setInqUser((String)SessionVariables.getSessionttiProfile(request, response));
		}
		if (SessionVariables.getSessionttiUserRoles(request, response) != null) {
			this.setInqRoles((String []) SessionVariables.getSessionttiUserRoles(request, response));
		}
		if (SessionVariables.getSessionttiUserGroups(request, response) != null) {
			this.setInqGroups((String []) SessionVariables.getSessionttiUserGroups(request, response));
		}
	}


	public String getInqAlpha01() {
		return inqAlpha01;
	}

	public void setInqAlpha01(String inqAlpha01) {
		this.inqAlpha01 = inqAlpha01;
	}

	public String getInqAlpha02() {
		return inqAlpha02;
	}

	public void setInqAlpha02(String inqAlpha02) {
		this.inqAlpha02 = inqAlpha02;
	}

	public String getInqAlpha03() {
		return inqAlpha03;
	}

	public void setInqAlpha03(String inqAlpha03) {
		this.inqAlpha03 = inqAlpha03;
	}

	public String getInqAlpha04() {
		return inqAlpha04;
	}

	public void setInqAlpha04(String inqAlpha04) {
		this.inqAlpha04 = inqAlpha04;
	}

	public String getInqAlpha05() {
		return inqAlpha05;
	}

	public void setInqAlpha05(String inqAlpha05) {
		this.inqAlpha05 = inqAlpha05;
	}

	public String getInqAlphaStr() {
		return inqAlphaStr;
	}

	public void setInqAlphaStr(String inqAlphaStr) {
		this.inqAlphaStr = inqAlphaStr;
	}

	public String getInqDescrLong() {
		return inqDescrLong;
	}

	public void setInqDescrLong(String inqDescrLong) {
		this.inqDescrLong = inqDescrLong;
	}

	public String getInqDescrShort() {
		return inqDescrShort;
	}

	public void setInqDescrShort(String inqDescrShort) {
		this.inqDescrShort = inqDescrShort;
	}

	public String getInqErrMsg() {
		return inqErrMsg;
	}

	public void setInqErrMsg(String inqErrMsg) {
		this.inqErrMsg = inqErrMsg;
	}

	public String[] getInqGroups() {
		return inqGroups;
	}

	public void setInqGroups(String[] inqGroups) {
		this.inqGroups = inqGroups;
	}

	public String getInqKey01() {
		return inqKey01;
	}

	public void setInqKey01(String inqKey01) {
		this.inqKey01 = inqKey01;
	}

	public String getInqKey02() {
		return inqKey02;
	}

	public void setInqKey02(String inqKey02) {
		this.inqKey02 = inqKey02;
	}

	public String getInqKey03() {
		return inqKey03;
	}

	public void setInqKey03(String inqKey03) {
		this.inqKey03 = inqKey03;
	}

	public String getInqKey04() {
		return inqKey04;
	}

	public void setInqKey04(String inqKey04) {
		this.inqKey04 = inqKey04;
	}

	public String getInqKey05() {
		return inqKey05;
	}

	public void setInqKey05(String inqKey05) {
		this.inqKey05 = inqKey05;
	}

	public String getInqKey06() {
		return inqKey06;
	}

	public void setInqKey06(String inqKey06) {
		this.inqKey06 = inqKey06;
	}

	public String getInqKey07() {
		return inqKey07;
	}

	public void setInqKey07(String inqKey07) {
		this.inqKey07 = inqKey07;
	}

	public String getInqKey08() {
		return inqKey08;
	}

	public void setInqKey08(String inqKey08) {
		this.inqKey08 = inqKey08;
	}

	public String getInqKey09() {
		return inqKey09;
	}

	public void setInqKey09(String inqKey09) {
		this.inqKey09 = inqKey09;
	}

	public String getInqKey10() {
		return inqKey10;
	}

	public void setInqKey10(String inqKey10) {
		this.inqKey10 = inqKey10;
	}

	public String getInqNum01() {
		return inqNum01;
	}

	public void setInqNum01(String inqNum01) {
		this.inqNum01 = inqNum01;
	}

	public String getInqNum02() {
		return inqNum02;
	}

	public void setInqNum02(String inqNum02) {
		this.inqNum02 = inqNum02;
	}

	public String getInqNum03() {
		return inqNum03;
	}

	public void setInqNum03(String inqNum03) {
		this.inqNum03 = inqNum03;
	}

	public String getInqNum04() {
		return inqNum04;
	}

	public void setInqNum04(String inqNum04) {
		this.inqNum04 = inqNum04;
	}

	public String getInqNum05() {
		return inqNum05;
	}

	public void setInqNum05(String inqNum05) {
		this.inqNum05 = inqNum05;
	}

	public String[] getInqRoles() {
		return inqRoles;
	}

	public void setInqRoles(String[] inqRoles) {
		this.inqRoles = inqRoles;
	}

	public String getInqType() {
		return inqType;
	}

	public void setInqType(String inqType) {
		this.inqType = inqType;
	}

	public String getInqTypeDescr40() {
		return inqTypeDescr40;
	}

	public void setInqTypeDescr40(String inqTypeDescr40) {
		this.inqTypeDescr40 = inqTypeDescr40;
	}

	public String getInqUser() {
		return inqUser;
	}

	public void setInqUser(String inqUser) {
		this.inqUser = inqUser;
	}

	public String getInqSearch() {
		return inqSearch;
	}

	public void setInqSearch(String inqSearch) {
		this.inqSearch = inqSearch;
	}

	public String getInqKeyStr() {
		return inqKeyStr;
	}

	public void setInqKeyStr(String inqKeyStr) {
		this.inqKeyStr = inqKeyStr;
	}

	public String getInqMessage() {
		return inqMessage;
	}

	public void setInqMessage(String inqMessage) {
		this.inqMessage = inqMessage;
	}

	public String getInqTypeDescr80() {
		return inqTypeDescr80;
	}

	public void setInqTypeDescr80(String inqTypeDescr80) {
		this.inqTypeDescr80 = inqTypeDescr80;
	}

	public String getInqKey00() {
		return inqKey00;
	}

	public void setInqKey00(String inqKey00) {
		this.inqKey00 = inqKey00;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	
	
}

