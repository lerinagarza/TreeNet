package com.treetop.app.descriptivecode;

import java.util.*;

import com.treetop.SessionVariables;
import com.treetop.viewbeans.*;
import com.treetop.services.ServiceDescriptiveCode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdDescCode extends BaseViewBeanR1 {
	
	public String	requestType	= "";
	
	public String	updUser 		= "";
	public String[]	updRoles		= {""};
	public String[]	updGroups		= {""};
	public String	updSearch		= "";
	
	public String	updType			= "";
	public String	updTypeDescr80	= "";
	public String	updTypeDescr40	= "";
	
	public String	updKey00		= "";
	public String	updKey01		= "";
	public String	updKey02		= "";
	public String	updKey03		= "";
	public String	updKey04		= "";
	public String	updKey05		= "";
	public String	updKey06		= "";
	public String	updKey07		= "";
	public String	updKey08		= "";
	public String	updKey09		= "";
	public String	updKey10		= "";
	
	public String 	updKeyStr		= "";
	
	public String	updDescrLong	= "";
	public String	updDescrShort	= "";
	public String	updMessage		= "";
	
	public String	updAlpha01		= "";
	public String	updAlpha02		= "";
	public String	updAlpha03		= "";
	public String	updAlpha04		= "";
	public String	updAlpha05		= "";
	public String	updAlphaStr	= ""; 
	
	public String	updNum01		= "";
	public String	updNum02		= "";
	public String	updNum03		= "";
	public String	updNum04		= "";
	public String	updNum05		= "";

	
	public String	updSubmit		= "";
	public String	updErrMsg		= "";

	public void validate() {
		if (!this.getUpdKey00().equals("") && (this.getUpdTypeDescr80().equals("") || this.getUpdTypeDescr40().equals(""))) {
			ServiceDescriptiveCode.getDescriptiveCodeHeaderDescr(this);
		}
		if (this.getUpdType() == null || this.getUpdType().equals("")) {
			this.setUpdType("V");
		}
		if (this.getUpdNum01().equals("")) {
			this.setUpdNum01("0");
		}
		if (this.getUpdNum02().equals("")) {
			this.setUpdNum02("0");
		}
		if (this.getUpdNum03().equals("")) {
			this.setUpdNum03("0");
		}
		if (this.getUpdNum04().equals("")) {
			this.setUpdNum04("0");
		}
		if (this.getUpdNum05().equals("")) {
			this.setUpdNum05("0");
		}
		
	
	}
	
	public void validate(HttpServletRequest request, HttpServletResponse response) {
		validate();
		if (SessionVariables.getSessionttiProfile(request, response) != null) {
			this.setUpdUser((String)SessionVariables.getSessionttiProfile(request, response));
		}
		if (SessionVariables.getSessionttiUserRoles(request, response) != null) {
			this.setUpdRoles((String []) SessionVariables.getSessionttiUserRoles(request, response));
		}
		if (SessionVariables.getSessionttiUserGroups(request, response) != null) {
			this.setUpdGroups((String []) SessionVariables.getSessionttiUserGroups(request, response));
		}
	}

	public String getUpdAlpha01() {
		return updAlpha01;
	}

	public void setUpdAlpha01(String updAlpha01) {
		this.updAlpha01 = updAlpha01;
	}

	public String getUpdAlpha02() {
		return updAlpha02;
	}

	public void setUpdAlpha02(String updAlpha02) {
		this.updAlpha02 = updAlpha02;
	}

	public String getUpdAlpha03() {
		return updAlpha03;
	}

	public void setUpdAlpha03(String updAlpha03) {
		this.updAlpha03 = updAlpha03;
	}

	public String getUpdAlpha04() {
		return updAlpha04;
	}

	public void setUpdAlpha04(String updAlpha04) {
		this.updAlpha04 = updAlpha04;
	}

	public String getUpdAlpha05() {
		return updAlpha05;
	}

	public void setUpdAlpha05(String updAlpha05) {
		this.updAlpha05 = updAlpha05;
	}

	public String getUpdAlphaStr() {
		return updAlphaStr;
	}

	public void setUpdAlphaStr(String updAlphaStr) {
		this.updAlphaStr = updAlphaStr;
	}

	public String getUpdDescrLong() {
		return updDescrLong;
	}

	public void setUpdDescrLong(String updDescrLong) {
		this.updDescrLong = updDescrLong;
	}

	public String getUpdDescrShort() {
		return updDescrShort;
	}

	public void setUpdDescrShort(String updDescrShort) {
		this.updDescrShort = updDescrShort;
	}

	public String getUpdErrMsg() {
		return updErrMsg;
	}

	public void setUpdErrMsg(String updErrMsg) {
		this.updErrMsg = updErrMsg;
	}

	public String[] getUpdGroups() {
		return updGroups;
	}

	public void setUpdGroups(String[] updGroups) {
		this.updGroups = updGroups;
	}

	public String getUpdKey01() {
		return updKey01;
	}

	public void setUpdKey01(String updKey01) {
		this.updKey01 = updKey01;
	}

	public String getUpdKey02() {
		return updKey02;
	}

	public void setUpdKey02(String updKey02) {
		this.updKey02 = updKey02;
	}

	public String getUpdKey03() {
		return updKey03;
	}

	public void setUpdKey03(String updKey03) {
		this.updKey03 = updKey03;
	}

	public String getUpdKey04() {
		return updKey04;
	}

	public void setUpdKey04(String updKey04) {
		this.updKey04 = updKey04;
	}

	public String getUpdKey05() {
		return updKey05;
	}

	public void setUpdKey05(String updKey05) {
		this.updKey05 = updKey05;
	}

	public String getUpdKey06() {
		return updKey06;
	}

	public void setUpdKey06(String updKey06) {
		this.updKey06 = updKey06;
	}

	public String getUpdKey07() {
		return updKey07;
	}

	public void setUpdKey07(String updKey07) {
		this.updKey07 = updKey07;
	}

	public String getUpdKey08() {
		return updKey08;
	}

	public void setUpdKey08(String updKey08) {
		this.updKey08 = updKey08;
	}

	public String getUpdKey09() {
		return updKey09;
	}

	public void setUpdKey09(String updKey09) {
		this.updKey09 = updKey09;
	}

	public String getUpdKey10() {
		return updKey10;
	}

	public void setUpdKey10(String updKey10) {
		this.updKey10 = updKey10;
	}

	public String getUpdKeyStr() {
		return updKeyStr;
	}

	public void setUpdKeyStr(String updKeyStr) {
		this.updKeyStr = updKeyStr;
	}

	public String getUpdNum01() {
		return updNum01;
	}

	public void setUpdNum01(String updNum01) {
		this.updNum01 = updNum01;
	}

	public String getUpdNum02() {
		return updNum02;
	}

	public void setUpdNum02(String updNum02) {
		this.updNum02 = updNum02;
	}

	public String getUpdNum03() {
		return updNum03;
	}

	public void setUpdNum03(String updNum03) {
		this.updNum03 = updNum03;
	}

	public String getUpdNum04() {
		return updNum04;
	}

	public void setUpdNum04(String updNum04) {
		this.updNum04 = updNum04;
	}

	public String getUpdNum05() {
		return updNum05;
	}

	public void setUpdNum05(String updNum05) {
		this.updNum05 = updNum05;
	}

	public String[] getUpdRoles() {
		return updRoles;
	}

	public void setUpdRoles(String[] updRoles) {
		this.updRoles = updRoles;
	}

	public String getUpdSearch() {
		return updSearch;
	}

	public void setUpdSearch(String updSearch) {
		this.updSearch = updSearch;
	}

	public String getUpdType() {
		return updType;
	}

	public void setUpdType(String updType) {
		this.updType = updType;
	}

	public String getUpdTypeDescr40() {
		return updTypeDescr40;
	}

	public void setUpdTypeDescr40(String updTypeDescr40) {
		this.updTypeDescr40 = updTypeDescr40;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public String getUpdSubmit() {
		return updSubmit;
	}

	public void setUpdSubmit(String updSubmit) {
		this.updSubmit = updSubmit;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getUpdKey00() {
		return updKey00;
	}

	public void setUpdKey00(String updKey00) {
		this.updKey00 = updKey00;
	}

	public String getUpdMessage() {
		return updMessage;
	}

	public void setUpdMessage(String updMessage) {
		this.updMessage = updMessage;
	}

	public String getUpdTypeDescr80() {
		return updTypeDescr80;
	}

	public void setUpdTypeDescr80(String updTypeDescr80) {
		this.updTypeDescr80 = updTypeDescr80;
	}

	
	
}

