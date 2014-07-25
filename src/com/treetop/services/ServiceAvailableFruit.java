/*
 * Created on July 19, 2010
 */
package com.treetop.services;


import java.sql.*;
import java.util.*;
import java.math.*;
import java.io.*;

import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.app.rawfruit.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;
import com.treetop.viewbeans.CommonRequestBean;
import com.ibm.as400.access.*;


/**
 * @author thaile.
 * Services class for processor fruit
 * inquiries and updates
 * 
 */
public class ServiceAvailableFruit extends BaseService {
	
	private static AS400 as400 = null;
	
	public ServiceAvailableFruit() {
		super();
	}
	
/**
 * Main testing.
 */
public static void main(String[] args)
{

	try {	
		
		String stopHere = "yes"; 
		
		//test dropDown for Field Rep
		if ("x" == "y")
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment("TST");
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			Vector x = dropDownFieldRep(crb);
			stopHere = "STOP";
		}
		
		//test dropDown (Crop/Grade/Variety/Organic).
		if ("x" == "y")
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment("TST");
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			Vector x = dropDownCropCode(crb);
			stopHere = "STOP";
			x = dropDownGrade(crb);
			stopHere = "STOP";
			x = dropDownOrganic(crb);
			stopHere = "STOP";
			x = dropDownVariety(crb);
			stopHere = "STOP";
			x = dropDownReceivingWarehouse(crb);
			stopHere = "STOP";
			x = dropDownLoadType(crb);
			stopHere = "STOP";
			x = dropDownHauler(crb);
			stopHere = "STOP";
		}
		
		
		
		//test dropdown warehouse.
		if ("x" == "y")
		{
			InqAvailableFruit iaf = new InqAvailableFruit();
			iaf.setEnvironment("TST");
			String[] roles = {"3", "2"};
			iaf.setUserProfile("ZIRKLE");
			iaf.setRoles(roles);
			Vector x = dropDownWarehouse(iaf);
			stopHere = "x";
		}
		
		
		
		//test find Scheduled Load
		if ("x" == "y")
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("TST");
			crb.setIdLevel1("123456789");
			BeanAvailFruit baf = findScheduledLoad(crb);
			stopHere = "STOP";
		}
		
		
		
		//test find Whse Fruit By Warehouse
		if ("x" == "y")
		{
			UpdAvailableFruit uaf = new UpdAvailableFruit();
			uaf.setCompanyNumber("100");
			uaf.setDivisionNumber("100");
			uaf.setEnvironment("TST");
			uaf.setWhseNo("1613");
			uaf.setLocAddNo("101");
			BeanAvailFruit baf = findWhseFruit(uaf);
			stopHere = "STOP";
		}
		
		
		
		//test update Available Fruit
		if ("x" == "y")
		{
			UpdAvailableFruitDetail dtl = new UpdAvailableFruitDetail();
			dtl.setBinQuantity("50");
			dtl.setCompanyNumber("100");
			dtl.setCrop("APPLE");
			dtl.setDivisionNumber("100");
			dtl.setEnvironment("TST");
			dtl.setGrade("PEELER");
			dtl.setOrganic("ORG");
			dtl.setUpdateUser("THAILE");
			dtl.setVariety("GRNYSM");
			dtl.setLocAddNo("101");
			dtl.setWhseNo("1613");
			
			Vector x = new Vector();
			x.addElement(dtl);
			
			UpdAvailableFruit uaf = new UpdAvailableFruit();
			uaf.setListAvailFruitDetail(x);
			uaf.setCompanyNumber("100");
			uaf.setDivisionNumber("100");
			uaf.setEnvironment("TST");
			uaf.setWhseNo("1613");
			uaf.setLocAddNo("101");
			
			BeanAvailFruit bean = updateAvailableFruit(uaf);
			stopHere = "STOP";
		}
		
		
		
		//test list Available Fruit
		if ("x" == "y")
		{
			InqAvailableFruit iaf = new InqAvailableFruit();
			iaf.setInqCompanyNumber("100");
			iaf.setInqDivisionNumber("100");
			iaf.setEnvironment("TST");
		//	iaf.setInqWhseNumber("14691");
			//iaf.setInqCrop("APPLE");
			//iaf.setInqGrade("PEELER");
			iaf.setInqFieldRepresentative("23");
			BeanAvailFruit baf = listAvailableFruit(iaf);
			stopHere = "STOP";
		}
		
		
		
		//test Get the Next Schedule Number
		if ("x" == "y")
		{
			String env = "TST";
			String no  = "";
			no = nextScheduleNo(env);
			stopHere = "STOP";
		}
		
		
		
		//test add scheduled loads.
		if ("x" == "y")
		{
			UpdScheduledFruit usf = new UpdScheduledFruit();
			usf.setCompanyNumber("100");
			usf.setDivisionNumber("100");
			usf.setEnvironment("TST");
			usf.setLoadNumber("123456789");//treacking - load no
			usf.setHaulingCompany("KERSHAW");
			usf.setTruckType("BELLY");
			//set the header ordhard run flag.;
			usf.setRecLoc("901");
			usf.setHaulerVerification("KER123");
			usf.setScheduledDeliveryDate("20101225");
			usf.setScheduledDeliveryTime("1234");
			usf.setUpdating("");
			usf.setLoadReceivedFlag("0");
			
			Vector temp = new Vector();
			
			UpdScheduledFruitDetail usfd = new UpdScheduledFruitDetail();
			usfd.setCompanyNumber("100");
			usfd.setDivisionNumber("100");
			usfd.setEnvironment("TST");
			usfd.setWhseNo("1613");
			usfd.setLocAddNo("101");
			usfd.setCrop("APPLE");
			usfd.setVariety("RED");
			usfd.setGrade("JUICE");
			usfd.setOrganic("ORG");
			usfd.setBinQuantity("888");
			usfd.setOrchardRun("1");
			usfd.setCashPrice("1221.99");
			usfd.setComments("delete me. I am a test record");
			usfd.setLoadNumber("123456789");//treacking - load no
			usfd.setWhseLoadNumber("wln456");
			usfd.setMemberCash("M");
			usfd.setPressure("35 psi");
			usfd.setWhseLotNumber("LOTNO123A7");
			usfd.setWhseItemNumber("WIN67h");
			usfd.setItemNumber("TIN67t");
			temp.addElement(usfd);
			
			usfd = new UpdScheduledFruitDetail();
			usfd.setCompanyNumber("100");
			usfd.setDivisionNumber("100");
			usfd.setEnvironment("TST");
			usfd.setWhseNo("1613");
			usfd.setLocAddNo("101");
			usfd.setCrop("APPLE");
			usfd.setVariety("RED");
			usfd.setGrade("JUICE");
			usfd.setOrganic("CON");
			usfd.setBinQuantity("887");
			usfd.setOrchardRun("1");
			usfd.setCashPrice("1221.99");
			usfd.setComments("delete me. I am a test record");
			usfd.setLoadNumber("123456789");//treacking - load no
			usfd.setWhseLoadNumber("wln456");
			usfd.setMemberCash("M");
			usfd.setPressure("35 psi");
			usfd.setWhseLotNumber("LOTNO123A7");
			usfd.setWhseItemNumber("WIN67h");
			usfd.setItemNumber("TIN67t");
			temp.addElement(usfd);
			
			usf.setListScheduledFruitDetail(temp);
			
			BeanAvailFruit baf = addScheduledLoads(usf);
			stopHere = "STOP";
		}
		
		
		
		//test list scheduled loads
		if ("x" == "y")
		{
			InqScheduledFruit isf = new InqScheduledFruit();
			isf.setInqCompanyNumber("100");
			isf.setInqDivisionNumber("100");
			isf.setEnvironment("TST");
			isf.setInqWhseNo("14691");
			//isf.setInqHaulingCompany("15187");
			//isf.setInqReceivingLocation("901");
			//isf.setInqDeliveryDateFrom("20100905");
			//isf.setInqDeliveryDateTo("20100906");
			//iaf.setInqGrade("PEELER");
			isf.setInqFieldRepresentative("23");
			BeanAvailFruit baf = listScheduledLoads(isf);
			stopHere = "STOP";
		}
		
		
		
		//test verify Scheduled Load Number
		if ("x" == "y")
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("TST");
			crb.setIdLevel1("123456789");
			String returnValue = verifyScheduleLoadNo(crb);
			stopHere = "STOP";
		}
		
		
		
		//test update scheduled loads.
		if ("x" == "y")
		{
			Vector temp = new Vector();
			
			UpdScheduledFruit usf = new UpdScheduledFruit();
			usf.setCompanyNumber("100");
			usf.setDivisionNumber("100");
			usf.setEnvironment("TST");
			usf.setLoadNumber("123456789");//treacking - load no
			usf.setHaulingCompany("KERSHAW");
			usf.setTruckType("BELLY");
			//set the header ordhard run flag.;
			usf.setRecLoc("901");
			usf.setHaulerVerification("KER123");
			usf.setScheduledDeliveryDate("20101225");
			usf.setScheduledDeliveryTime("1234");
			usf.setCreateDate("20100926");
			usf.setCreateTime("120001");
			usf.setCreateUser("THAILE");
			usf.setUpdating("yes");
			usf.setLoadReceivedFlag("0");
			
			UpdScheduledFruitDetail usfd = new UpdScheduledFruitDetail();
			usfd.setCompanyNumber("100");
			usfd.setDivisionNumber("100");
			usfd.setEnvironment("TST");
			usfd.setWhseNo("9999");
			usfd.setLocAddNo("999");
			usfd.setLoadNumber("123456789");//tracking - load no
			usfd.setLoadLineNumber("1");//tracking sequence no
			usfd.setCrop("APPLE");
			usfd.setVariety("RED");
			usfd.setGrade("JUICE");
			usfd.setOrganic("ORG");
			usfd.setBinQuantity("667");	
			usfd.setOrchardRun("1");
			usfd.setCashPrice("1221.99");
			usfd.setComments("delete me. I am a test record");
			usfd.setWhseLoadNumber("wln456");
			usfd.setMemberCash("M");
			usfd.setPressure("35 psi");
			usfd.setWhseLotNumber("LOTNO123A7");
			usfd.setWhseItemNumber("WIN67h");
			usfd.setItemNumber("TIN67t");
			usfd.setUpdateUser("LOSE OR");
			temp.addElement(usfd);
			
			usfd = new UpdScheduledFruitDetail();
			usfd.setCompanyNumber("100");
			usfd.setDivisionNumber("100");
			usfd.setEnvironment("TST");
			usfd.setWhseNo("9999");
			usfd.setLocAddNo("999");
			usfd.setLoadNumber("123456789");//treacking - load no
			usfd.setLoadLineNumber("2");//tracking sequence no
			usfd.setCrop("APPLE");
			usfd.setVariety("RED");
			usfd.setGrade("JUICE");
			usfd.setOrganic("");
			usfd.setBinQuantity("668");
			usfd.setOrchardRun("1");
			usfd.setCashPrice("1221.98");
			usfd.setComments("delete me. I am a test record");
			usfd.setWhseLoadNumber("wln456");
			usfd.setMemberCash("M");
			usfd.setPressure("35 psi");
			usfd.setWhseLotNumber("LOTNO123A8");
			usfd.setWhseItemNumber("WIN67h");
			usfd.setItemNumber("TIN67t");
			usfd.setUpdateUser("LOSE OR");
			temp.addElement(usfd);
			
			usfd = new UpdScheduledFruitDetail();
			usfd.setCompanyNumber("100");
			usfd.setDivisionNumber("100");
			usfd.setEnvironment("TST");
			usfd.setWhseNo("9999");
			usfd.setLocAddNo("999");
			usfd.setLoadNumber("123456789");//treacking - load no
			usfd.setLoadLineNumber("3");//tracking sequence no
			usfd.setCrop("APPLE");
			usfd.setVariety("RED");
			usfd.setGrade("JUICE");
			usfd.setOrganic("CON");
			usfd.setBinQuantity("669");
			usfd.setOrchardRun("1");
			usfd.setCashPrice("1221.99");
			usfd.setComments("delete me. I am a test record");
			usfd.setWhseLoadNumber("wln456");
			usfd.setMemberCash("M");
			usfd.setPressure("35 psi");
			usfd.setWhseLotNumber("LOTNO123A7");
			usfd.setWhseItemNumber("WIN67h");
			usfd.setItemNumber("TIN67t");
			usfd.setUpdateUser("LOSE OR");
			temp.addElement(usfd);
			
			usf.setListScheduledFruitDetail(temp);
			
			BeanAvailFruit baf = updateScheduledLoad(usf);
			stopHere = "STOP";
		}
		
		
		
		//test update Scheduled Load Status
		if ("x" == "y")
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("TST");
			crb.setIdLevel1("123456789");
			crb.setIdLevel2("C");
			String returnValue = updateScheduleLoadStatus(crb);
			stopHere = "STOP";
		}
		
		
		stopHere = "ends here";


	} catch (Exception e) {
		System.out.println(e);	
	}
}

/**
 * Load class fields from result set.
 */
			
private static Vector loadFields(String requestType,
						         ResultSet rs)
throws Exception
{
	StringBuffer throwError    = new StringBuffer();
	String       movexCustomer = new String("M3");
	Vector       returnValue   = new Vector();
			
	try { //catch all
		
		if (requestType.trim().equals("findWhseFruitHeader")) 
		{
			AvailFruitByWhse afbw = new AvailFruitByWhse();
		  
			afbw.setWhseNumber(rs.getString("WHSNO").trim());
			afbw.setWhseAddressNumber(rs.getString("SAADID").trim());
			
			if (rs.getString("INVCHGU") != null)
			{
				afbw.setChangeUser(rs.getString("INVCHGU").trim());
				afbw.setChangeDate(rs.getString("INVCHGD").trim());
				afbw.setChangeTime(rs.getString("INVCHGT").trim());
			}
			
			afbw.setWhseDescription(rs.getString("IDSUNM").trim());
			Warehouse whse = new Warehouse();
			whse.setWarehouse(rs.getString("SASUNO").trim());
			whse.setWarehouseDescription(rs.getString("SASUNM").trim());
			whse.setAddress1(rs.getString("SAADR1").trim());
			whse.setAddress2(rs.getString("SAADR2").trim());
			whse.setAddress3(rs.getString("SAADR3").trim());
			whse.setAddress4(rs.getString("SAADR4").trim());
			whse.setCity(rs.getString("SATOWN").trim());
			whse.setState(rs.getString("SAECAR").trim());
			whse.setZip(rs.getString("SAPONO").trim());
			afbw.setWarehouse(whse);
	
			returnValue.addElement(afbw);
		}
		
		
		
		if (requestType.trim().equals("findWhseFruitHeaderLoads")) 
		{
			AvailFruitByWhse afbw = new AvailFruitByWhse();
		  
			afbw.setWhseNumber(rs.getString("SCLWHSE").trim());
			afbw.setWhseAddressNumber(rs.getString("SCLDID").trim());
			afbw.setChangeUser(rs.getString("SCLCHGU").trim());
			afbw.setChangeDate(rs.getString("SCLCHGD").trim());
			afbw.setChangeTime(rs.getString("SCLCHGT").trim());
			afbw.setWhseDescription(rs.getString("whIDSUNM").trim());
			Warehouse whse = new Warehouse();
			whse.setWarehouse(rs.getString("SCLWHSE").trim());
			whse.setWarehouseDescription(rs.getString("SASUNM").trim());
			whse.setAddress1(rs.getString("SAADR1").trim());
			whse.setAddress2(rs.getString("SAADR2").trim());
			whse.setAddress3(rs.getString("SAADR3").trim());
			whse.setAddress4(rs.getString("SAADR4").trim());
			whse.setCity(rs.getString("SATOWN").trim());
			whse.setState(rs.getString("SAECAR").trim());
			whse.setZip(rs.getString("SAPONO").trim());
			afbw.setWarehouse(whse);
	
			returnValue.addElement(afbw);
		}
		

		
		if (requestType.trim().equals("findWhseFruitDetail")) 
		{
			AvailFruitByWhseDetail afbwd = new AvailFruitByWhseDetail();
			
			afbwd.setWhseNumber(rs.getString("INVWHSE").trim());
			afbwd.setWhseAddressNumber(rs.getString("INVDID").trim());
			afbwd.setChangeUser(rs.getString("INVCHGU").trim());
			afbwd.setChangeDate(rs.getString("INVCHGD").trim());
			afbwd.setChangeTime(rs.getString("INVCHGT").trim());
			afbwd.setWhseDescription(rs.getString("IDSUNM").trim());
			Warehouse whse = new Warehouse();
			whse.setWarehouse(rs.getString("INVWHSE").trim());
			whse.setWarehouseDescription(rs.getString("SASUNM").trim());
			whse.setAddress1(rs.getString("SAADR1").trim());
			whse.setAddress2(rs.getString("SAADR2").trim());
			whse.setAddress3(rs.getString("SAADR3").trim());
			whse.setAddress4(rs.getString("SAADR4").trim());
			whse.setCity(rs.getString("SATOWN").trim());
			whse.setState(rs.getString("SAECAR").trim());
			whse.setZip(rs.getString("SAPONO").trim());
			afbwd.setWarehouse(whse);
			
			afbwd.setWhseLoadNo(rs.getString("INVWLOAD").trim());
			afbwd.setCropCode(rs.getString("INVCROP").trim());	
			afbwd.setGradeCode(rs.getString("INVCATGY").trim());
			afbwd.setOrganicCode(rs.getString("INVORGCON").trim());
			afbwd.setVarietyCode(rs.getString("INVVTY").trim());
			afbwd.setStickerFree(rs.getString("INVSTICK").trim());
			afbwd.setTons(rs.getString("INVTON").trim());
			afbwd.setPounds(rs.getString("INVLBS").trim());
			afbwd.setBinCount(rs.getString("INVBIN").trim());
			afbwd.setAvailDate(rs.getString("INVAVAILD").trim());
			afbwd.setDateLotCreated(rs.getString("INVBORND").trim());
			afbwd.setWhseAssignedLot(rs.getString("INVLOT").trim());
			afbwd.setWhseItemNo(rs.getString("INVWITM").trim());
			afbwd.setTtiItemNo(rs.getString("INVTITM").trim());
			afbwd.setComment(rs.getString("INVCOMT").trim());
			afbwd.setRecvFlag(rs.getString("INVPREC").trim());
			afbwd.setPickupFlag(rs.getString("INVPSCL").trim());
			afbwd.setInventoryType(rs.getString("INVITY").trim());
			afbwd.setPurchaseQuantity(rs.getString("INVPQY").trim());
			afbwd.setPurchasePrice(rs.getString("INVPPC").trim());
			
			if (rs.getString("ccGRPNAME") != null)
				afbwd.setCropCodeDesc(rs.getString("ccGRPNAME").trim());
			if (rs.getString("vaGRPNAME") != null)
				afbwd.setVarietyDesc(rs.getString("vaGRPNAME").trim());
			if (rs.getString("gaGRPNAME") != null)
				afbwd.setGradeDesc(rs.getString("gaGRPNAME").trim());
			if (rs.getString("ogGRPNAME") != null)
				afbwd.setOrganicDesc(rs.getString("ogGRPNAME").trim());
			
			returnValue.addElement(afbwd);
		}
		
				
		if (requestType.trim().equals("findScheduledLoadDetail"))
		{
			ScheduledLoadDetail sld = new ScheduledLoadDetail();
			
			//ScheduledLoadHeader data.
			sld.setScheduleLoadNo(rs.getString("SCHTRACK").trim());
			sld.setHaulingCompany(rs.getString("SCHHAUL"));
			
			if (rs.getString("huIDSUNM") != null)
				sld.setHaulingCompanyName(rs.getString("huIDSUNM").trim());
			
			sld.setHaulerVerificationNo(rs.getString("SCHLOADID").trim());
			sld.setLoadType(rs.getString("SCHLTYP").trim());
			sld.setOrchardRun(rs.getString("SCHORFL").trim());
			sld.setReceivingLocationNo(rs.getString("SCHRLOC").trim());
			
			if (rs.getString("TWMWWHNM") != null)
				sld.setReceivingLocationDesc(rs.getString("TWMWWHNM").trim());
			
			sld.setReceivingDockNo(rs.getString("SCHTODK").trim());
			
			if (rs.getString("TLRLCDOCKD") != null)
				sld.setReceivingDockDesc(rs.getString("TLRLCDOCKD").trim());
			
			sld.setLoadReceivedFlag(rs.getString("SCHRECF").trim());
			sld.setScheduledDeliveryDate(rs.getString("SCHSDELD").trim());
			sld.setScheduledDeliveryTime(rs.getString("SCHSDELT").trim());
			sld.setActualDeliveryDate(rs.getString("SCHADELD").trim());
			sld.setActualDeliveryTime(rs.getString("SCHADELT").trim());
			sld.setHeaderComment(rs.getString("SCHCOMT").trim());
			sld.setEmptyBinCount(rs.getString("SCHBINO").trim());
			sld.setDistributionOrder(rs.getString("SCHDO#").trim());  // 6/13/11 tw
			sld.setTransferFlag(rs.getString("SCHTRFL").trim());  // 6/13/11 tw
			sld.setCreateUser(rs.getString("SCHCRTU").trim());
			sld.setCreateDate(rs.getString("SCHCRTD").trim());
			sld.setCreateTime(rs.getString("SCHCRTT").trim());
			sld.setChangeUser(rs.getString("SCHCHGU").trim());
			sld.setChangeDate(rs.getString("SCHCHGD").trim());
			sld.setChangeTime(rs.getString("SCHCHGT").trim());
			
			//ScheduledLoadDetail detail.
			sld.setWhseNumber(rs.getString("SCDWHSE").trim());
			sld.setWhseAddressNumber(rs.getString("SCDDID").trim());
			sld.setWhseLoadNo(rs.getString("SCDLOAD").trim());
			sld.setScheduledLoadLineNo(rs.getString("SCDTRLINE").trim());
			
			sld.setShippingLocationNo(rs.getString("SCDFRTTW").trim());
			
			if (rs.getString("FWMWWHNM") != null)
				sld.setShippingLocationDesc(rs.getString("FWMWWHNM").trim());
			
			sld.setShippingDockNo(rs.getString("SCDFRDK").trim());
			
			if (rs.getString("FLRLCDOCKD") != null)
				sld.setShippingDockDesc(rs.getString("FLRLCDOCKD").trim());
			
			sld.setCropCode(rs.getString("SCDCROP").trim());	
			sld.setGradeCode(rs.getString("SCDCATGY").trim());
			sld.setVarietyCode(rs.getString("SCDVTY").trim());
			sld.setOrganicCode(rs.getString("SCDORGCON").trim());
			sld.setOrchardRunDtlFlag(rs.getString("SCDORRUN").trim()); // 6/8/11 tw
			sld.setStickerFreeFlag(rs.getString("SCDSTICK").trim()); // 3/29/11 tw
			sld.setMemberOrCash(rs.getString("SCDMBRC").trim()); // 6/8/11 tw
			
			if (rs.getString("ccGRPNAME") != null)
				sld.setCropCodeDesc(rs.getString("ccGRPNAME").trim());
			if (rs.getString("vaGRPNAME") != null)
				sld.setVarietyDesc(rs.getString("vaGRPNAME").trim());
			if (rs.getString("gaGRPNAME") != null)
				sld.setGradeDesc(rs.getString("gaGRPNAME").trim());
			if (rs.getString("ogGRPNAME") != null)
				sld.setOrganicDesc(rs.getString("ogGRPNAME").trim());
			
			sld.setTons(rs.getString("SCDTON").trim());
			sld.setPounds(rs.getString("SCDLBS").trim());
			sld.setBinCount(rs.getString("SCDBIN").trim());
			
			if (rs.getString("whIDSUNM") != null)
			   sld.setWhseDescription(rs.getString("whIDSUNM").trim());
			
			Warehouse whse = new Warehouse();
			whse.setWarehouse(rs.getString("SCDWHSE").trim());
			if (rs.getString("SASUNM") != null)
			{
				whse.setWarehouseDescription(rs.getString("SASUNM").trim());
				whse.setAddress1(rs.getString("SAADR1").trim());
				whse.setAddress2(rs.getString("SAADR2").trim());
				whse.setAddress3(rs.getString("SAADR3").trim());
				whse.setAddress4(rs.getString("SAADR4").trim());
				whse.setCity(rs.getString("SATOWN").trim());
				whse.setState(rs.getString("SAECAR").trim());
				whse.setZip(rs.getString("SAPONO").trim());
			}
			sld.setWarehouse(whse);
			
			sld.setOrchardRunDtlFlag(rs.getString("SCDORRUN").trim());
			sld.setScheduledPickupDate(rs.getString("SCDSCHD").trim());
			sld.setScheduledPickupTime(rs.getString("SCDSCHT").trim());
			sld.setLastTestedPressure(rs.getString("SCDPRESS").trim());
			sld.setCashPrice(rs.getString("SCDPRICE").trim());
			sld.setWhseAssignedLot(rs.getString("SCDLOT").trim());
			sld.setWhseAssignedItem(rs.getString("SCDWITM").trim());
			sld.setTreeTopItem(rs.getString("SCDTITM").trim());
			if (rs.getString("MMITDS") != null)
				sld.setTreeTopItemDesc(rs.getString("MMITDS").trim());
			sld.setDetailComment(rs.getString("SCDCOMT").trim());
			sld.setAvailableDate(rs.getString("SCDAVAILD").trim());
			
			returnValue.addElement(sld);
		}
		
		
		
		if (requestType.trim().equals("listScheduledLoadDetail"))
		{
			ScheduledLoadDetail sld = new ScheduledLoadDetail();
			
			//ScheduledLoadHeaderData
			sld.setScheduleLoadNo(rs.getString("SCHTRACK").trim());
			sld.setHaulingCompany(rs.getString("SCHHAUL"));
			
			if (rs.getString("huIDSUNM") != null)
				sld.setHaulingCompanyName(rs.getString("huIDSUNM").trim());
			
			sld.setHaulerVerificationNo(rs.getString("SCHLOADID").trim());
			sld.setLoadType(rs.getString("SCHLTYP").trim());
			sld.setOrchardRun(rs.getString("SCHORFL").trim());
			sld.setReceivingLocationNo(rs.getString("SCHRLOC").trim());
			
			if (rs.getString("TWMWWHNM") != null)
				sld.setReceivingLocationDesc(rs.getString("TWMWWHNM").trim());
			
			sld.setReceivingDockNo(rs.getString("SCHTODK").trim());
			
			if (rs.getString("TLRLCDOCKD") != null)
				sld.setReceivingDockDesc(rs.getString("TLRLCDOCKD").trim());
			
			sld.setLoadReceivedFlag(rs.getString("SCHRECF").trim());
			sld.setScheduledDeliveryDate(rs.getString("SCHSDELD").trim());
			sld.setScheduledDeliveryTime(rs.getString("SCHSDELT").trim());
			sld.setActualDeliveryDate(rs.getString("SCHADELD").trim());
			sld.setActualDeliveryTime(rs.getString("SCHADELT").trim());
			sld.setHeaderComment(rs.getString("SCHCOMT").trim());
			sld.setEmptyBinCount(rs.getString("SCHBINO").trim()); // 6/13/11 tw
			sld.setDistributionOrder(rs.getString("SCHDO#").trim()); // 6/13/11 tw
			sld.setTransferFlag(rs.getString("SCHTRFL").trim()); // 6/13/11 tw
			sld.setCreateUser(rs.getString("SCHCRTU").trim());
			sld.setCreateDate(rs.getString("SCHCRTD").trim());
			sld.setCreateTime(rs.getString("SCHCRTT").trim());
			sld.setChangeUser(rs.getString("SCHCHGU").trim());
			sld.setChangeDate(rs.getString("SCHCHGD").trim());
			sld.setChangeTime(rs.getString("SCHCHGT").trim());
			
			//ScheduledLoadDetail detail.
			sld.setWhseNumber(rs.getString("SCDWHSE").trim());
			sld.setWhseAddressNumber(rs.getString("SCDDID").trim());
			sld.setWhseLoadNo(rs.getString("SCDLOAD").trim());
			sld.setScheduledLoadLineNo(rs.getString("SCDTRLINE").trim());
			
			sld.setShippingLocationNo(rs.getString("SCDFRTTW").trim());
			
			if (rs.getString("FWMWWHNM") != null)
				sld.setShippingLocationDesc(rs.getString("FWMWWHNM").trim());
			
			sld.setShippingDockNo(rs.getString("SCDFRDK").trim());
			
			if (rs.getString("FLRLCDOCKD") != null)
				sld.setShippingDockDesc(rs.getString("FLRLCDOCKD").trim());
			
			sld.setCropCode(rs.getString("SCDCROP").trim());	
			sld.setGradeCode(rs.getString("SCDCATGY").trim());
			sld.setVarietyCode(rs.getString("SCDVTY").trim());
			sld.setOrganicCode(rs.getString("SCDORGCON").trim());
			sld.setOrchardRunDtlFlag(rs.getString("SCDORRUN").trim());
			sld.setMemberOrCash(rs.getString("SCDMBRC").trim());
			sld.setStickerFreeFlag(rs.getString("SCDSTICK").trim()); // 3/29/11 tw
			
			if (rs.getString("ccGRPNAME") != null)
				sld.setCropCodeDesc(rs.getString("ccGRPNAME").trim());
			if (rs.getString("vaGRPNAME") != null)
				sld.setVarietyDesc(rs.getString("vaGRPNAME").trim());
			if (rs.getString("gaGRPNAME") != null)
				sld.setGradeDesc(rs.getString("gaGRPNAME").trim());
			if (rs.getString("ogGRPNAME") != null)
				sld.setOrganicDesc(rs.getString("ogGRPNAME").trim());
			
			sld.setTons(rs.getString("SCDTON").trim());
			sld.setPounds(rs.getString("SCDLBS").trim());
			sld.setBinCount(rs.getString("SCDBIN").trim());
			
			if (rs.getString("whIDSUNM") != null)
			   sld.setWhseDescription(rs.getString("whIDSUNM").trim());
			
			Warehouse whse = new Warehouse();
			whse.setWarehouse(rs.getString("SCDWHSE").trim());
			if (rs.getString("SASUNM") != null)
			{
			   whse.setWarehouseDescription(rs.getString("SASUNM").trim());
			   whse.setAddress1(rs.getString("SAADR1").trim());
			   whse.setAddress2(rs.getString("SAADR2").trim());
			   whse.setAddress3(rs.getString("SAADR3").trim());
			   whse.setAddress4(rs.getString("SAADR4").trim());
			   whse.setCity(rs.getString("SATOWN").trim());
			   whse.setState(rs.getString("SAECAR").trim());
			   whse.setZip(rs.getString("SAPONO").trim());
			}
			sld.setWarehouse(whse);
			
			sld.setOrchardRunDtlFlag(rs.getString("SCDORRUN").trim());
			sld.setScheduledPickupDate(rs.getString("SCDSCHD").trim());
			sld.setScheduledPickupTime(rs.getString("SCDSCHT").trim());
			sld.setLastTestedPressure(rs.getString("SCDPRESS").trim());
			sld.setCashPrice(rs.getString("SCDPRICE").trim());
			sld.setWhseAssignedLot(rs.getString("SCDLOT").trim());
			sld.setWhseAssignedItem(rs.getString("SCDWITM").trim());
			sld.setTreeTopItem(rs.getString("SCDTITM").trim());
		
			sld.setDetailComment(rs.getString("SCDCOMT").trim());
			sld.setAvailableDate(rs.getString("SCDAVAILD").trim());
			
			returnValue.addElement(sld);
		}
		
	} catch(Exception e)	
	{
		throwError.append(" Problem loading data(" + requestType + "). " + e) ;
	}

	
//  *************************************************************************************					
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("loadFields(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * @author deisen.
 * Load class fields from result set for a drop down box. (single)
 */

private static DropDownSingle loadFieldsDropDown(String requestType, 
						          		  		ResultSet rs)
throws Exception
{
	StringBuffer   throwError  = new StringBuffer();
	DropDownSingle returnValue = new DropDownSingle();
	
	try { 
		
		if  (requestType.trim().equals("CROPCODE") ||
			 requestType.trim().equals("GRADE") ||
			 requestType.trim().equals("ORGANIC") ||
			 requestType.trim().equals("LOAD"))	
		{
			returnValue.setValue(rs.getString("GRPKEY1").trim());
			returnValue.setDescription(rs.getString("GRPNAME").trim());
		}
		
	//	if (requestType.trim().equals("ReceivingWarehouse"))
	//	{
	//		returnValue.setValue(rs.getString("mwwhlo"));
	//		returnValue.setDescription(rs.getString("mwwhnm").trim());
	//	}
		
		if (requestType.trim().equals("Hauler"))
		{
			returnValue.setValue(rs.getString("idsuno"));
			returnValue.setDescription(rs.getString("idsunm").trim());
		}
		
		if (requestType.trim().equals("FieldRep"))
		{
			returnValue.setValue(rs.getString("ctstky"));
			returnValue.setDescription(rs.getString("cttx40").trim());
		}
		if (requestType.trim().equals("Facility"))
		{
			returnValue.setValue(rs.getString("cffaci"));
			returnValue.setDescription(rs.getString("cffacn").trim());
		}

	} catch(Exception e)
	{
		throwError.append(" Problem loading the Drop Down Single class");
		throwError.append(" from the result set. " + e) ;
	}

//  *************************************************************************************				
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("loadFieldsDropDownSingle(String, String, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Return available fruit by warehouse.
 */
public static BeanAvailFruit findWhseFruit(UpdAvailableFruit uaf)
throws Exception
{
			
	StringBuffer 	throwError  = new StringBuffer();
	BeanAvailFruit  returnValue = new BeanAvailFruit();
	Connection 		conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		returnValue = findWhseFruit(uaf, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("findWhseFruit(");
		throwError.append("UpdAvailFruit). ");
		returnValue.setThrowError(throwError.toString());
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * Return available fruit by warehouse.
 */
private static BeanAvailFruit findWhseFruit(UpdAvailableFruit uaf, 
											Connection conn)
throws Exception
{
	StringBuffer      throwError    = new StringBuffer();
	ResultSet         rs            = null;
	ResultSet         rsSum         = null;
	ResultSet         rsRouge       = null;
	Statement         listThem      = null;
	Statement         sumThem       = null;
	Statement		  listRouge     = null;
	Statement         insertIt      = null;
	Statement         deleteIt      = null;
	AvailFruitByWhse  afbw			= new AvailFruitByWhse();
	BeanAvailFruit    returnValue   = new BeanAvailFruit();
	String			  requestType   = "";
	String			  sqlString		= "";
	String 			  addZeroInv    = "";
			
	try { //catch all
		
		//set accumulators to zero.
		BigDecimal availScheduledQtyTotal = new BigDecimal("0");
		BigDecimal allScheduledQtyTotal = new BigDecimal("0");
		
		//Add additional zero warehouse record if a rouge scheduled load exists.
		try {
			requestType = "findRougeSchedLoadsByWhse";
			Vector parmClass = new Vector();
			parmClass.addElement(uaf);
			sqlString = buildSqlStatement(requestType, parmClass);
			listRouge = conn.createStatement();
			rsRouge = listRouge.executeQuery(sqlString);
		 
			 //load zero balance entries into whse inventory file
			 String crop   = "";
			 String vty    = "";
			 String catgy  = "";
			 String orgcon = "";
			 
			 while (throwError.toString().trim().equals("") && rsRouge.next())
			 {
				 
				 if (addZeroInv.equals("") || !crop.equals(rsRouge.getString("SCDCROP").trim()) ||
					 !vty.equals(rsRouge.getString("SCDVTY").trim()) ||
					 !catgy.equals(rsRouge.getString("SCDCATGY").trim()) ||
					 !orgcon.equals(rsRouge.getString("SCDORGCON").trim()))
				 {
					 //add a temporary warehouse record here.
						Vector parms = new Vector(); 				
		 				parms.addElement(uaf);
		 				parms.addElement(rsRouge);
		 				sqlString = buildSqlStatement("addZeroInventoryRecord", parms);
		 				insertIt = conn.createStatement();
		 				insertIt.executeUpdate(sqlString);
		 				insertIt.close();
				 }
				 
				 addZeroInv  = "yes";
				 crop   = rsRouge.getString("SCDCROP").trim();
				 vty    = rsRouge.getString("SCDVTY").trim();
				 catgy  = rsRouge.getString("SCDCATGY").trim();
				 orgcon = rsRouge.getString("SCDORGCON").trim();
			 }
			 
			 
			 try {
				 listRouge.close();
			 } catch (Exception e) {}
			 
			 
		 } catch(Exception e)
		 {
		   	throwError.append("Error adding rouge scheduled load whse entry (findRougeSchedLoadsByWhse). " + e);
		 }
			 
		
		
		
		//find any current warehouse data.
		try {
			requestType = "findWhseFruit";
			Vector parmClass = new Vector();
			parmClass.addElement(uaf);
			sqlString = buildSqlStatement(requestType, parmClass);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sqlString);
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (findWhseFruit). " + e);
		 }
		 
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 //vector to hold all detail.
				 Vector dtl = new Vector();
				 
				 //check for duplicate entries fields.
				 // after every build verify these are not identical.
				 // if so set the duplicated flag on both bus. objects.
				 String lastCropCode    = "";
				 String lastGradeCode   = "";
				 String lastOrganicCode = "";
				 String lastVarietyCode = "";
				 String lastStickerFree = "";
				 
				 for (int x = 0; rs.next(); x++)
				 {
					 if (x == 0)
					 {
						 Vector rtn = loadFields("findWhseFruitHeader", rs);
						 returnValue.setEnvironment(uaf.getEnvironment());
						 returnValue.setAvailFruitByWhse((AvailFruitByWhse) rtn.elementAt(x) );
					 }
					 
					 //accumulate the scheduled to deliver by available inventory.
					 String scheduledQty = "0";
					 
					 //Verify there is any available fruit detail first.
					 if (rs.getString("INVWHSE") != null)
					 {
						 try {
							 requestType = "SchedInvByAvailFruit";
							 Vector parmClass = new Vector();
							 parmClass.addElement(rs);
							 parmClass.addElement(uaf);
							 sqlString = buildSqlStatement(requestType, parmClass);
							 sumThem = conn.createStatement();
							 rsSum = sumThem.executeQuery(sqlString);
							 
							 if (rsSum.next() && rsSum.getString("total") != null)
							 {
								 scheduledQty = rsSum.getString("total").trim();
								 availScheduledQtyTotal = availScheduledQtyTotal.add(rsSum.getBigDecimal("total"));
							 }
							 
				     	 } catch(Exception e)
						 {
							throwError.append(" Error occured while building class in SchedInvByAvailFruit. " + e);
						 }
						 
				     	 Vector temp = loadFields("findWhseFruitDetail", rs);
				     	 AvailFruitByWhseDetail afbwd = (AvailFruitByWhseDetail) temp.elementAt(0);
				     	 afbwd.setScheduledQty(scheduledQty);
				     	 
				     	 //check for duplicates.
				     	 if (!afbwd.getCropCode().trim().equals("") ||
				     		 !afbwd.getGradeCode().trim().equals("") || 
				     		 !afbwd.getVarietyCode().trim().equals("") ||
				     		 !afbwd.getOrganicCode().trim().equals("") ||
				     		 !afbwd.getStickerFree().trim().equals(""))
				     		 
				     	 {
				     		 if (afbwd.getCropCode().trim().equals(lastCropCode) &&
				     			 afbwd.getGradeCode().trim().equals(lastGradeCode) && 
						     	 afbwd.getVarietyCode().trim().equals(lastVarietyCode) &&
						     	 afbwd.getOrganicCode().trim().equals(lastOrganicCode) &&
						     	 afbwd.getStickerFree().trim().equals(lastStickerFree))
				     		 {
				     			 if (x > 0)
				     			 {
				     				 afbwd.setDuplicated("1");
				     				 AvailFruitByWhseDetail lastOne = (AvailFruitByWhseDetail) dtl.elementAt(x-1);
				     				 lastOne.setDuplicated("1");
				     				 dtl.setElementAt(lastOne, x-1);
				     			 }
				     		 }
				     	 }
				     	 
				     	 lastCropCode    = afbwd.getCropCode().trim();
				     	 lastGradeCode   = afbwd.getGradeCode().trim();
				     	 lastVarietyCode = afbwd.getVarietyCode().trim();
				     	 lastOrganicCode = afbwd.getOrganicCode().trim();
				     	 lastStickerFree = afbwd.getStickerFree().trim();
				     		 
				     	 dtl.addElement(afbwd);
					 }
				 }
				 
				 AvailFruitByWhse temp = returnValue.getAvailFruitByWhse();
				 temp.setAvailScheduledQtyTotal(availScheduledQtyTotal.toString());
				 //add the allAvailScheduledQtyTotal HERE.
				 
				 returnValue.setAvailFruitByWhse(temp);
				 returnValue.setAvailFruitByWhseDetail(dtl);	
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class in findWhseFruitHeader. " + e);
			 } 		
		 }
		 
		 try {//delete zero entries entered into Whse inventory file if performed eariler.
			 if (throwError.toString().trim().equals("") && !addZeroInv.equals("") )
			 {
				Vector parms = new Vector();
				parms.addElement(uaf);
				sqlString = buildSqlStatement("deleteZeroInventoryRecord", parms);
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			 }
		 } catch (Exception e)
		 {
			 throwError.append(" Error occured at delete of Zero Inventory file entry. " + e);
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {rs.close();} catch(Exception el){};
	   }
	   if (rsSum != null)
	   {
		   try {rsSum.close();} catch(Exception el){};
	   }
	   if (rsRouge != null)
	   {
		   try {rsRouge.close();} catch(Exception el){};
	   }      
	   
	   if (listThem != null)
	   {
		   try {listThem.close();} catch(Exception el){}
	   }
	   if (sumThem != null)
	   {
		   try {sumThem.close();} catch(Exception el){}
	   }
	   if (listRouge != null)
	   {
		   try {listRouge.close();} catch(Exception el){}
	   }
	   if (insertIt != null)
	   {
		   try {insertIt.close();} catch(Exception el){}
	   }
	   if (deleteIt != null)
	   {
		   try {deleteIt.close();} catch(Exception el){}
	   }
	   
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("findWhseFruit(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Build an SQL statement for specifications.
 */
	
private static String buildSqlStatement(String requestType,
									    Vector requestClass)
throws Exception 
{
	StringBuffer sqlString       = new StringBuffer();	
	StringBuffer throwError      = new StringBuffer();
		
	try { //catch all
		
		if (requestType.equals("findWhseFruit"))
		{
			UpdAvailableFruit uaf = (UpdAvailableFruit) requestClass.elementAt(0);
			
			sqlString.append("select invwhse, invdid, invwload, invbin, invton, invlbs, invavaild, ");
			sqlString.append("invbornd, invlot, invwitm, invtitm, invprec, invpscl, invcomt, invchgu, invchgd, invchgt, ");
			sqlString.append("invcrop, invcatgy, invvty, invorgcon, invstick, invorfl, invmbrc, invity, invpqy, invppc, ");
			sqlString.append("sasuno, sasunm, saadr1, saadr2, saadr3, saadr4, satown, saecar, sapono, saadid, ");
			sqlString.append("idsunm, whsno, ");
			sqlString.append(" cc.GRPNAME as ccGRPNAME, va.GRPNAME as vaGRPNAME, ");
			sqlString.append("ga.GRPNAME as gaGRPNAME, og.GRPNAME as ogGRPNAME ");
			sqlString.append("from db" + uaf.getEnvironment().trim() +  ".grpywhse ");
			sqlString.append("left outer join db" + uaf.getEnvironment().trim() + ".grpywinv on whsno = invwhse and whsdid = invdid ");
			sqlString.append("and INVITY = '" + uaf.getInventoryType() + "' ");
			sqlString.append("inner join m3djd" + uaf.getEnvironment().trim() + ".cidadr on sacono = 100 and whsno = sasuno ");
			sqlString.append("and saadte = 2 and whsdid = saadid ");
			sqlString.append("inner join m3djd" + uaf.getEnvironment().trim() + ".cidmas on idcono = 100 and whsno = idsuno ");
			sqlString.append("left outer join db" + uaf.getEnvironment().trim() +  ".grpygroup cc ");
			sqlString.append("on cc.GRPUSE = 'CROPCODE' and invcrop = cc.GRPKEY1 ");
			sqlString.append("left outer join db" + uaf.getEnvironment().trim() +  ".grpygroup va ");
			sqlString.append("on va.GRPUSE = 'VARIETY' and invcrop = va.GRPKEY1 AND invvty = va.GRPKEY2 ");
			sqlString.append("left outer join db" + uaf.getEnvironment().trim() +  ".grpygroup ga ");
			sqlString.append("on ga.GRPUSE = 'GRADE' and invcatgy = ga.GRPKEY1 ");
			sqlString.append("left outer join db" + uaf.getEnvironment().trim() +  ".grpygroup og ");
			sqlString.append("on og.GRPUSE = 'ORGANIC' and invorgcon = og.GRPKEY1 ");
			sqlString.append("where whsno = '" + uaf.getWhseNo().trim() + "' ");
			sqlString.append("  and whsdid = '" + uaf.getLocAddNo().trim() + "' ");
			
			sqlString.append("order by whsno, whsdid, invcrop, invvty, invcatgy, invorgcon, invstick, invorfl, invmbrc "); // 2/18/11 wth
		}
		
				
		if (requestType.equals("findScheduledLoadByScheduleNo"))
		{
			CommonRequestBean crb = (CommonRequestBean) requestClass.elementAt(0);
			
			sqlString.append("select scdwhse, scddid, scdload, scdtrack, scdtrline, scdcrop, scdcatgy, ");
			sqlString.append("scdvty, scdorgcon, scdmbrc, scdorrun, scdbin, scdton, scdlbs, scdschd, ");
			sqlString.append("scdscht, scdprice, scdlot, scdpress, scdwitm, scdtitm, scdavaild, scdcomt, ");
			sqlString.append("scdfrttw, scdfrdk, scdstick, "); // 2/18/11 wth
			sqlString.append("mmitds, "); // 8/2/11 tw
			sqlString.append("schtrack, schhaul, schltyp, schorfl, schrloc, schloadid, schrecf, schsdeld, ");
			sqlString.append("schsdelt, schadeld, schadelt, schcomt, schcrtu, schcrtd, schcrtt, ");
			sqlString.append("schchgu, schchgd, schchgt, schdo#, schbino, schtrfl, "); // 6/13/11 tw
			sqlString.append("schtodk, tl.rlcdockd as tlrlcdockd, fl.rlcdockd as flrlcdockd, "); //7/26/11 tw
			sqlString.append("sasuno, sasunm, saadr1, saadr2, saadr3, saadr4, satown, saecar, sapono, ");
			sqlString.append("a.idsunm as whidsunm, b.idsunm as huidsunm, invwhse, ");
			sqlString.append("tw.mwwhnm as twmwwhnm, fw.mwwhnm as fwmwwhnm, ");
			sqlString.append("cc.GRPNAME as ccGRPNAME, va.GRPNAME as vaGRPNAME, ");
			sqlString.append("ga.GRPNAME as gaGRPNAME, og.GRPNAME as ogGRPNAME ");
			sqlString.append("from db" + crb.getEnvironment().trim() + ".grpyschh ");
			sqlString.append("inner join db" + crb.getEnvironment().trim() + ".grpyschd on schtrack = scdtrack ");
			sqlString.append("left outer join m3djd" + crb.getEnvironment().trim() + ".mitmas on mmcono = 100 and scdtitm = mmitno ");
			sqlString.append("left outer join m3djd" + crb.getEnvironment().trim() + ".cidadr on sacono = 100 and scdwhse = sasuno ");
			sqlString.append("and saadte = 2 and scddid = saadid ");
			sqlString.append("left outer join m3djd" + crb.getEnvironment().trim() + ".cidmas a on a.idcono = 100 and scdwhse = a.idsuno ");
			sqlString.append("left outer join db" + crb.getEnvironment().trim() + ".grpywinv ");
			sqlString.append("on scdwhse = invwhse and scddid = invdid ");
			sqlString.append("and scdcrop = invcrop and scdcatgy = invcatgy and scdvty = invvty ");
			sqlString.append("and scdorgcon = invorgcon ");
			sqlString.append("and scdstick = invstick and scdorrun = invorfl and scdmbrc = invmbrc "); // 2/18/11 wth
//		  join to get the "To M3 Warehouse" information
			sqlString.append("left outer join m3djd" + crb.getEnvironment().trim() + ".MITWHL tw on tw.mwcono = 100 and schrloc = tw.mwwhlo ");
			// join to get the "To Dock" information
			sqlString.append("left outer join db" + crb.getEnvironment().trim() +  ".grpyrloc tl on schrloc = tl.rlcno and schtodk = tl.rlcdock ");
			// join to get the "From M3 Warehouse" information
			sqlString.append("left outer join m3djd" + crb.getEnvironment().trim() + ".MITWHL fw on fw.mwcono = 100 and scdfrttw = fw.mwwhlo ");
			// join to get the "From Dock" information
			sqlString.append("left outer join db" + crb.getEnvironment().trim() +  ".grpyrloc fl on scdfrttw = fl.rlcno and scdfrdk = fl.rlcdock ");
			sqlString.append("left outer join db" + crb.getEnvironment().trim() +  ".grpygroup cc ");
			sqlString.append("on cc.GRPUSE = 'CROPCODE' and scdcrop = cc.GRPKEY1 ");
			sqlString.append("left outer join db" + crb.getEnvironment().trim() +  ".grpygroup va ");
			sqlString.append("on va.GRPUSE = 'VARIETY' and scdcrop = va.GRPKEY1 AND scdvty = va.GRPKEY2 ");
			sqlString.append("left outer join db" + crb.getEnvironment().trim() +  ".grpygroup ga ");
			sqlString.append("on ga.GRPUSE = 'GRADE' and scdcatgy = ga.GRPKEY1 ");
			sqlString.append("left outer join db" + crb.getEnvironment().trim() +  ".grpygroup og ");
			sqlString.append("on og.GRPUSE = 'ORGANIC' and scdorgcon = og.GRPKEY1 ");
			//sqlString.append("WHERE SCLRECD = '' "); 
			sqlString.append("left outer join m3djd" + crb.getEnvironment().trim() + ".cidmas b on b.idcono = 100 ");
			sqlString.append("and b.idsuty = '5' and schhaul = b.idsuno ");
			sqlString.append("WHERE schtrack = " + crb.getIdLevel1() + " ");
			
			sqlString.append("ORDER BY scdschd, scdscht, scdwhse, scddid, scdcrop, scdvty, scdcatgy, scdorgcon "); // 3/29/11 tw
		}
		
		
		
		if (requestType.equals("SchedInvByAvailFruit"))
		{
			ResultSet rs = (ResultSet) requestClass.elementAt(0);
			UpdAvailableFruit uaf = (UpdAvailableFruit) requestClass.elementAt(1);
			
			sqlString.append("SELECT SUM(SCDBIN) as total FROM db" + uaf.getEnvironment().trim() + ".GRPYSCHD ");
			sqlString.append("INNER JOIN db" + uaf.getEnvironment().trim() + ".GRPYSCHH ");
			sqlString.append("ON SCDTRACK = SCHTRACK ");
			sqlString.append("INNER JOIN db" + uaf.getEnvironment().trim() + ".GRPYWINV ");
			sqlString.append("ON SCDWHSE = INVWHSE AND SCDDID = INVDID ");
			sqlString.append("AND SCDCROP = INVCROP AND SCDCATGY = INVCATGY ");
			sqlString.append("AND SCDVTY = INVVTY AND SCDORGCON = INVORGCON ");
			sqlString.append("AND SCDSTICK = INVSTICK AND INVITY = '' ");
			//AND SCDORRUN = INVORFL AND SCDMBRC = INVMBRC "); // 2/18/11 wth
			sqlString.append("WHERE SCDWHSE = '" + rs.getString("INVWHSE").trim() + "' ");
			sqlString.append("AND SCDDID = '" + rs.getString("INVDID").trim() + "' ");
			sqlString.append("AND SCDCROP = '" + rs.getString("INVCROP").trim() + "' ");
			sqlString.append("AND SCDVTY  = '" + rs.getString("INVVTY").trim() + "' ");
			sqlString.append("AND SCDCATGY = '" + rs.getString("INVCATGY").trim() + "' ");
			sqlString.append("AND SCDORGCON = '" + rs.getString("INVORGCON").trim() + "' ");
			sqlString.append("AND SCDSTICK = '" + rs.getString("INVSTICK").trim() + "' "); // 2/18/11 wth
		//	sqlString.append("AND SCDORRUN = '" + rs.getString("INVORFL").trim() + "' "); // 2/18/11 wth
		//	sqlString.append("AND SCDMBRC = '" + rs.getString("INVMBRC").trim() + "' "); // 2/18/11 wth
			sqlString.append("AND (SChRECf = '' or (SChRECf = 'R' AND INVCHGD <= SCHSDELD) ) ");
		}
		
		
		
		if (requestType.equals("TotalScheduledFruitByWhse"))
		{
			ResultSet rs = (ResultSet) requestClass.elementAt(0);
			UpdAvailableFruit uaf = (UpdAvailableFruit) requestClass.elementAt(1);
			
			sqlString.append("SELECT SUM(SCDBIN) as total FROM db" + uaf.getEnvironment().trim() + ".GRPYSCHD "); 
			sqlString.append("WHERE SCDWHSE = '" + rs.getString("SCDWHSE").trim() + "' ");
			sqlString.append("AND SCDDID = '" + rs.getString("SCDDID").trim() + "' ");
			//sqlString.append("AND SCLRECD = '' ");
		}
		
		
		
		if (requestType.trim().equals("deleteAvailableFruit")) 
		{
			// use the first element to delete all entries for warehouse in database prior to add.
			UpdAvailableFruit uaf = (UpdAvailableFruit) requestClass.elementAt(0);
				
			if ((!uaf .getWhseNo().trim().equals("")) &&
			    (!uaf .getLocAddNo().trim().equals("")) )	
			{	
				sqlString.append("DELETE FROM db" + uaf.getEnvironment() + ".GRPYWINV ");
				sqlString.append("WHERE INVWHSE = '" + uaf.getWhseNo().trim() + "' ");
				sqlString.append("AND INVDID = '" + uaf.getLocAddNo().trim() + "' ");
				// added code to differenciate between to purchase or not
				sqlString.append("AND INVITY = '" + uaf.getInventoryType().trim() + "' ");
			}
		}
		
		
		
		if (requestType.trim().equals("deleteZeroInventoryRecord")) 
		{
			// remove any zero inventory entries for temporary usage.
			UpdAvailableFruit uaf = (UpdAvailableFruit) requestClass.elementAt(0);
				
			if ((!uaf .getWhseNo().trim().equals("")) &&
			    (!uaf .getLocAddNo().trim().equals("")) )	
			{	
				sqlString.append("DELETE FROM db" + uaf.getEnvironment() + ".GRPYWINV ");
				sqlString.append("WHERE INVWHSE = '" + uaf.getWhseNo().trim() + "' ");
				sqlString.append("AND INVDID = '" + uaf.getLocAddNo().trim() + "' ");
				sqlString.append("AND INVWLOAD = '9999999999999' ");
				sqlString.append("AND INVCHGU = 'ZEROINV' ");
			}
		}
		
				
		if (requestType.trim().equals("deleteScheduledLoadHeader")) 
		{
			// use the first element to delete all entries for the scheduled load.
			UpdScheduledFruit usf = (UpdScheduledFruit) requestClass.elementAt(0);
			UpdScheduledFruitDetail usfd = (UpdScheduledFruitDetail) usf.getListScheduledFruitDetail().elementAt(0);
				
			if (!usfd.getLoadNumber().trim().equals("") )	
			{	
				sqlString.append("DELETE FROM db" + usfd.getEnvironment().trim() + ".GRPYSCHH ");
				sqlString.append("WHERE SCHTRACK = '" + usfd.getLoadNumber().trim() + "' ");
			}
		}
		
				
		if (requestType.trim().equals("deleteScheduledLoadDetail")) 
		{
			// use the first element to delete all entries for the scheduled load.
			UpdScheduledFruit usf = (UpdScheduledFruit) requestClass.elementAt(0);
			UpdScheduledFruitDetail usfd = (UpdScheduledFruitDetail) usf.getListScheduledFruitDetail().elementAt(0);
				
			if (!usfd.getLoadNumber().trim().equals("") )	
			{	
				sqlString.append("DELETE FROM db" + usfd.getEnvironment().trim() + ".GRPYSCHD ");
				sqlString.append("WHERE SCDTRACK = '" + usfd.getLoadNumber().trim() + "' ");
			}
		}
		
		
		if (requestType.trim().equals("addAvailableFruit")) 
		{		
			UpdAvailableFruitDetail uafd = (UpdAvailableFruitDetail) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(uafd.getEnvironment().trim());
			// 3/22/11 TW - Change to send in the System Date
			//DateTime dt = UtilityDateTime.getSystemDate();
						
			sqlString.append("INSERT INTO " + libraryTT + ".GRPYWINV ");
			sqlString.append("VALUES(");
			sqlString.append("'" + uafd.getWhseNo().trim() + "', ");//INVWHSE
			sqlString.append("'" + uafd.getLocAddNo().trim() + "',");//INVDID
			sqlString.append("'',");//INVWLOAD
			sqlString.append("'" + uafd.getCrop().trim() + "',");//INVCROP
			sqlString.append("'" + uafd.getGrade().trim() + "',");//INVCATGY
			sqlString.append("'" + uafd.getVariety().trim() + "',");//INVVTY
			sqlString.append("'" + uafd.getOrganic().trim() + "', ");//INVORGCON
			sqlString.append(uafd.getBinQuantity().trim() + ",");//INVBIN
			sqlString.append("0,");//INVTON
			sqlString.append("0,");//INVLBS
			sqlString.append("0,");//INVAVAILD
			sqlString.append("0,");//INVBORND
			sqlString.append("'',");//INVLOT
			sqlString.append("'',");//INVWITM
			sqlString.append("'',");//INVTITM
			sqlString.append("'',");//INVCOMT
			sqlString.append("'',");//INVPREC
			sqlString.append("'',");//INVPSCL
			sqlString.append("'" + uafd.getUpdateUser().trim() + "',");//INVCHGU
			//sqlString.append(dt.getDateFormatyyyyMMdd() + ",");//INVCHGD
			//sqlString.append(dt.getTimeFormathhmmss() + ", ");//INVCHGT
			// 3/22/11 -- change to send in System Date
			sqlString.append(uafd.getUpdateDate() + ",");//INVCHGD
			sqlString.append(uafd.getUpdateTime() + ", ");//INVCHGT
			
			sqlString.append("'" + uafd.getStickerFree() + "', ");//INVSTICK
			sqlString.append("'', ");//INVORFL
			sqlString.append("'', ");//INVMBRC
			sqlString.append("'" + uafd.getInventoryType() + "', ");//INVITY
			sqlString.append("0, ");//INVPQY
			sqlString.append("0 ");//INVPPC

			sqlString.append(")");
		}
		
		if (requestType.trim().equals("addZeroInventoryRecord")) 
		{		
			DateTime dt = UtilityDateTime.getSystemDate();
			UpdAvailableFruit uaf = (UpdAvailableFruit) requestClass.elementAt(0);
			ResultSet rs = (ResultSet) requestClass.elementAt(1);
					
			sqlString.append("INSERT INTO DB" + uaf.getEnvironment() + ".GRPYWINV ");
			sqlString.append("VALUES(");
			sqlString.append("'" + rs.getString("SCDWHSE").trim() + "', ");//INVWHSE
			sqlString.append("'" + rs.getString("SCDDID").trim() + "',");//INVDID
			sqlString.append("'9999999999999',");//INVWLOAD
			sqlString.append("'" + rs.getString("SCDCROP").trim() + "',");//INVCROP
			sqlString.append("'" + rs.getString("SCDCATGY").trim() + "',");//INVCATGY
			sqlString.append("'" + rs.getString("SCDVTY").trim() + "',");//INVVTY
			sqlString.append("'" + rs.getString("SCDORGCON").trim() + "', ");//INVORGCON
			sqlString.append("0, ");//INVBIN
			sqlString.append("0,");//INVTON
			sqlString.append("0,");//INVLBS
			sqlString.append("0,");//INVAVAILD
			sqlString.append("0,");//INVBORND
			sqlString.append("'',");//INVLOT
			sqlString.append("'',");//INVWITM
			sqlString.append("'',");//INVTITM
			sqlString.append("'',");//INVCOMT
			sqlString.append("'',");//INVPREC
			sqlString.append("'',");//INVPSCL
			sqlString.append("'ZEROINV',");//INVCHGU
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");//INVCHGD
			sqlString.append(dt.getTimeFormathhmmss() + ", ");//INVCHGT
			sqlString.append("'" + rs.getString("SCDSTICK") +"', ");//INVSTICK
			sqlString.append("'', ");//INVORFL
			sqlString.append("'', ");//INVMBRC
			sqlString.append("'', ");//INVITY
			sqlString.append("0, ");//INVPQY
			sqlString.append("0 ");//INVPPC

			sqlString.append(")");
		}
		
		if (requestType.equals("Warehouse DropDown") ||
			requestType.equals("Warehouse DropDownDual")	)
		{
			InqAvailableFruit iaf = (InqAvailableFruit) requestClass.elementAt(0);
			String where = "WHERE SECUSER = '" + iaf.getUserProfile().trim() + "' ";
			
			for (int x = 0; x < iaf.getRoles().length ; x++)
			{
				if (iaf.getRoles()[x].equals("1") || iaf.getRoles()[x].equals("8"))
					where = "";
			}
			if (!iaf.getWhseNo().trim().equals(""))
				where = where + " AND SECWHSE = '" + iaf.getWhseNo() + "' ";
		    if (requestType.equals("Warehouse DropDown"))
			   sqlString.append("select IDCFI1, SECWHSE, SECDID, IDSUNM, SAADID, SASUNM "); //20110422 -TW (Add North South Desination)
		    else
		       sqlString.append("select SECWHSE, IDSUNM, SAADID, SASUNM "); // 20110510 - TW	
			sqlString.append("from db" + iaf.getEnvironment().trim() + ".GRPYSEC ");
			sqlString.append("inner join m3djd" + iaf.getEnvironment().trim() + ".CIDMAS ");
			sqlString.append("on IDCONO = 100 and SECWHSE = IDSUNO ");
			sqlString.append("inner join m3djd" + iaf.getEnvironment().trim() + ".CIDADR ");
			sqlString.append("on SACONO = 100 and SECWHSE = SASUNO ");
			sqlString.append("and SAADTE = 2 and SECDID = SAADID ");
			sqlString.append(where);
			if (requestType.equals("Warehouse DropDown"))
			{
			   sqlString.append(" group by IDCFI1, SECWHSE, SECDID, IDSUNM, SAADID, SASUNM "); //20110422-TW
			   sqlString.append(" order by IDCFI1, IDSUNM, SAADID ");//20110119 -- 20110422 - TW
			}else{
			   sqlString.append(" group by SECWHSE, IDSUNM, SAADID, SASUNM "); //20110511-TW
			   sqlString.append(" order by IDSUNM, SAADID ");//20110119 -- 20110511 - TW
			}
		}
		
		if (requestType.equals("AvailableFruitInq"))
		{
			InqAvailableFruit iaf = (InqAvailableFruit) requestClass.elementAt(0);
			
			sqlString.append("select invwhse, invdid, invwload, invbin, invton, invlbs, invavaild, ");
			sqlString.append("invbornd, invlot, invwitm, invtitm, invprec, invpscl, invcomt, invchgu, invchgd, invchgt, ");
			sqlString.append("invcrop, invcatgy, invvty, invorgcon, invstick, invorfl, invmbrc, "); // 2/18/11 wth
			sqlString.append("invity, invpqy, invppc, "); // 2/25/13 TW
			sqlString.append("sasuno, sasunm, saadr1, saadr2, saadr3, saadr4, satown, saecar, sapono, saadid, ");
			sqlString.append("idsunm, whsno, ");  
			sqlString.append(" cc.GRPNAME as ccGRPNAME, va.GRPNAME as vaGRPNAME, ");
			sqlString.append("ga.GRPNAME as gaGRPNAME, og.GRPNAME as ogGRPNAME ");
			sqlString.append("from db" + iaf.getEnvironment().trim() +  ".grpywhse ");
			sqlString.append("inner join db" + iaf.getEnvironment().trim() + ".grpywinv on whsno = invwhse and whsdid = invdid ");
			sqlString.append("inner join m3djd" + iaf.getEnvironment().trim() + ".cidadr on sacono = 100 and whsno = sasuno ");
			sqlString.append("and saadte = 2 and whsdid = saadid ");
			sqlString.append("inner join m3djd" + iaf.getEnvironment().trim() + ".cidmas on idcono = 100 and whsno = idsuno ");
			sqlString.append("left outer join db" + iaf.getEnvironment().trim() +  ".grpygroup cc ");
			sqlString.append("on cc.GRPUSE = 'CROPCODE' and invcrop = cc.GRPKEY1 ");
			sqlString.append("left outer join db" + iaf.getEnvironment().trim() +  ".grpygroup va ");
			sqlString.append("on va.GRPUSE = 'VARIETY' and invcrop = va.GRPKEY1 AND invvty = va.GRPKEY2 ");
			sqlString.append("left outer join db" + iaf.getEnvironment().trim() +  ".grpygroup ga ");
			sqlString.append("on ga.GRPUSE = 'GRADE' and invcatgy = ga.GRPKEY1 ");
			sqlString.append("left outer join db" + iaf.getEnvironment().trim() +  ".grpygroup og ");
			sqlString.append("on og.GRPUSE = 'ORGANIC' and invorgcon = og.GRPKEY1 ");
			
			//add selection criteria.
			StringBuffer where = new StringBuffer();
			
			if (!iaf.getInqRegion().trim().equals(""))
				where.append("where idcfi1 = '" + iaf.getInqRegion() + "' ");
			
			if (!iaf.getInqWhseNo().trim().equals(""))
			{
				if (where.toString().trim().equals(""))
					where.append("where ");
				else
					where.append("and ");
				
				where.append("invwhse = '" + iaf.getInqWhseNo().trim() + "' ");
			}
			
			if (!iaf.getInqLocAddNo().trim().equals(""))
			{
				if (where.toString().trim().equals(""))
					where.append("where ");
				else
					where.append("and ");
				
				where.append("invdid = '" + iaf.getInqLocAddNo().trim() + "' ");
			}
			
			if (!iaf.getInqCrop().trim().equals(""))
			{
				if (where.toString().trim().equals(""))
					where.append("where ");
				else
					where.append("and ");
				
				where.append("invcrop = '" + iaf.getInqCrop().trim() + "' ");
			}
			
			if (!iaf.getInqVariety().trim().equals(""))
			{
				if (where.toString().trim().equals(""))
					where.append("where ");
				else
					where.append("and ");
				
				where.append("invvty = '" + iaf.getInqVariety().trim() + "' ");
			}
			
			if (!iaf.getInqGrade().trim().equals(""))
			{
				if (where.toString().trim().equals(""))
					where.append("where ");
				else
					where.append("and ");
				
				where.append("invcatgy = '" + iaf.getInqGrade().trim() + "' ");
			}
			
			if (!iaf.getInqOrganic().trim().equals(""))
			{
				if (where.toString().trim().equals(""))
					where.append("where ");
				else
					where.append("and ");
				
				where.append("invorgcon = '" + iaf.getInqOrganic().trim() + "' ");
			}
			
			//Added field representative 2011-02-11 JH
			if (!iaf.getInqFieldRepresentative().trim().equals(""))
			{
				if (where.toString().trim().equals(""))
					where.append("where ");
				else
					where.append("and ");
				
				where.append("idcfi3 = '" + iaf.getInqFieldRepresentative().trim() + "' ");
			}
			//	Added sticker Free 2011-04-18 TW
			if (!iaf.getInqStickerFree().trim().equals(""))
			{
				if (where.toString().trim().equals(""))
					where.append("where ");
				else
					where.append("and ");
				
				where.append("invstick = '" + iaf.getInqStickerFree().trim() + "' ");
			}
			// Added Purchase Available Fruit 2013-02-25 TW
			if (where.toString().trim().equals(""))
				where.append("where ");
			else
				where.append("and ");
			if (iaf.getInqFruitAvailToPurchase().trim().equals("N") ||
				iaf.getInqFruitAvailToPurchase().trim().equals(""))
				where.append("invity = '' ");
			else
				where.append("invity <> '' ");
		
			sqlString.append(where);
			
			if (iaf.getOrderBy().trim().equals("whseDescription"))
			{
				sqlString.append("order by idsunm "); // 4/18/11 tw
				if (!iaf.getOrderStyle().trim().equals("")) // 4/18/11 tw
					sqlString.append(iaf.getOrderStyle().trim()); // 4/18/11 tw
				sqlString.append(", sasunm, invcrop, invvty, invcatgy, invorgcon, invstick, invorfl, invmbrc "); // 4/18/11 tw
			}
			if (iaf.getOrderBy().trim().equals("whseAddressDescription"))
			{
				sqlString.append("order by sasunm "); // 4/18/11 tw
				if (!iaf.getOrderStyle().trim().equals("")) // 4/18/11 tw
					sqlString.append(iaf.getOrderStyle().trim()); // 4/18/11 tw
				sqlString.append(", idsunm, invcrop, invvty, invcatgy, invorgcon, invstick, invorfl, invmbrc "); // 4/18/11 tw
			}
			if (iaf.getOrderBy().trim().equals("crop")) // 4/18/11 tw
			{
				sqlString.append("order by invcrop "); // 4/18/11 tw
				if (!iaf.getOrderStyle().trim().equals("")) // 4/18/11 tw
					sqlString.append(iaf.getOrderStyle().trim()); // 4/18/11 tw
				sqlString.append(", invvty, invcatgy, invorgcon, invstick, invorfl, invmbrc "); // 2/18/11 wth
			}
			if (iaf.getOrderBy().trim().equals("variety"))
			{
				sqlString.append("order by invvty "); // 4/18/11 tw
				if (!iaf.getOrderStyle().trim().equals("")) // 4/18/11 tw
					sqlString.append(iaf.getOrderStyle().trim()); // 4/18/11 tw
				sqlString.append(", invcrop, invcatgy, invorgcon, invstick, invorfl, invmbrc "); // 4/18/11 tw
			}
			if (iaf.getOrderBy().trim().equals("grade"))
			{
				sqlString.append("order by invcatgy "); // 4/18/11 tw
				if (!iaf.getOrderStyle().trim().equals("")) // 4/18/11 tw
					sqlString.append(iaf.getOrderStyle().trim()); // 4/18/11 tw
				sqlString.append(", invcrop, invvty, invorgcon, invstick, invorfl, invmbrc "); // 4/18/11 tw
			}
			if (iaf.getOrderBy().trim().equals("fruitType"))
			{
				sqlString.append("order by invorgcon "); // 4/18/11 tw
				if (!iaf.getOrderStyle().trim().equals("")) // 4/18/11 tw
					sqlString.append(iaf.getOrderStyle().trim()); // 4/18/11 tw
				sqlString.append(", invcrop, invvty, invcatgy, invstick, invorfl, invmbrc "); // 4/18/11 tw
			}
			if (iaf.getOrderBy().trim().equals("stickerFree"))
			{
				sqlString.append("order by invstick "); // 4/18/11 tw
				if (!iaf.getOrderStyle().trim().equals("")) // 4/18/11 tw
					sqlString.append(iaf.getOrderStyle().trim()); // 4/18/11 tw
				sqlString.append(", invcrop, invvty, invcatgy, invorgcon, invorfl, invmbrc "); // 4/18/11 tw
			}
		}
		
		
		
		if (requestType.trim().equals("addScheduledLoadHeader"))
		{		
			UpdScheduledFruit usf = (UpdScheduledFruit) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(usf.getEnvironment().trim());
			DateTime dt = UtilityDateTime.getSystemDate();
						
			sqlString.append("INSERT INTO db" + usf.getEnvironment().trim() + ".GRPYSCHH ");
			sqlString.append("VALUES(");
			sqlString.append(usf.getLoadNumber().trim() + ", ");//SCHTRACK
			sqlString.append("'" + usf.getHaulingCompany().trim() + "', ");//SCHHAUL
			sqlString.append("'" + usf.getTruckType().trim() + "',");//SCHLTYP
			sqlString.append("'" + usf.getLoadOrchardRun() + "', ");//orchard run flag for header
			sqlString.append("'" + usf.getRecLoc().trim() + "', ");//SCHRLOC
			sqlString.append("'" + usf.getHaulerVerification() + "', ");//SCHLOADID
			sqlString.append("'" + usf.getLoadReceivedFlag() + "', ");//SCHRECF
			sqlString.append(usf.getScheduledDeliveryDate() + ", ");//SCHSDELT
			sqlString.append(usf.getScheduledDeliveryTime() + ", ");//SCHSDELT
			sqlString.append(usf.getActualReceivingDate() + ", ");//actual delivery date SCHADELD
			sqlString.append(usf.getActualReceivingTime() + ", ");//actual delivery time SCHADELT
			sqlString.append("'" + usf.getComments().trim() + "', ");//SCHCOMT
			
			if (usf.getUpdating().equals("yes"))
			{
				sqlString.append("'" + usf.getCreateUser() + "', ");//SCHCRTU
				sqlString.append(usf.getCreateDate() + ", ");//SCHCRTD
				sqlString.append(usf.getCreateTime() + ", ");//SCHCRTT
				sqlString.append("'" + usf.getUpdateUser().trim() + "',");//SCHCHGU
				sqlString.append(dt.getDateFormatyyyyMMdd() + ",");//SCHCHGD
				sqlString.append(dt.getTimeFormathhmmss() + ", ");//SCHCHGT
			} else
			{
				sqlString.append("'" + usf.getUpdateUser().trim() + "',");//SCHCRTU
				sqlString.append(dt.getDateFormatyyyyMMdd() + ",");//SCHCRTD
				sqlString.append(dt.getTimeFormathhmmss() + ", ");//SCHCRTT
				sqlString.append("'" + usf.getUpdateUser().trim() + "',");//SCHCHGU
				sqlString.append(dt.getDateFormatyyyyMMdd() + ",");//SCHCHGD
				sqlString.append(dt.getTimeFormathhmmss() + ", ");//SCHCHGT
			}
			
			sqlString.append("0, ");//SCHSPUD added 20110119
			sqlString.append("'" + usf.getDock().trim() + "', ");//SCHTODK
			sqlString.append("'" + usf.getDistributionOrder().trim() + "', ");//SCHDO#
			sqlString.append("0, ");//SCHBINO
			sqlString.append("'" + usf.getTransferLoadFlag().trim() + "', ");//SCHTRFL
			sqlString.append("'' ");//SCHDELU

			sqlString.append(")");
		}
		
		
		
		if (requestType.trim().equals("addScheduledLoadDetail"))
		{		
			UpdScheduledFruitDetail usfd = (UpdScheduledFruitDetail) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(usfd.getEnvironment().trim());
			DateTime dt = UtilityDateTime.getSystemDate();
						
			sqlString.append("INSERT INTO db" + usfd.getEnvironment().trim() + ".GRPYSCHD ");
			sqlString.append("VALUES(");
			sqlString.append("'" + usfd.getWhseNo().trim() + "', ");//SCDWHSE
			sqlString.append("'" + usfd.getLocAddNo().trim() + "',");//SCDDID
			sqlString.append("'" + usfd.getWhseLoadNumber() + "', ");//SCDLOAD
			sqlString.append(usfd.getLoadNumber() + ", ");//SCDTRACK
			sqlString.append(usfd.getLoadLineNumber() + ", ");//SCDTRLINE
			sqlString.append("'" + usfd.getCrop().trim() + "',");//SCDCROP
			sqlString.append("'" + usfd.getGrade().trim() + "',");//SCDCATGY
			sqlString.append("'" + usfd.getVariety().trim() + "',");//SCDVTY
			sqlString.append("'" + usfd.getOrganic().trim() + "', ");//SCDORGCON
			sqlString.append("'" + usfd.getMemberCash() + "', ");//SCDMBRC
			sqlString.append("'" + usfd.getOrchardRun() + "', ");//SCDORRUN
			sqlString.append(usfd.getBinQuantity().trim() + ",");//SCDBIN
			sqlString.append("0,");//SCDTON
			sqlString.append("0,");//SCDLBS

			sqlString.append(usfd.getScheduledPickUpDate() + ", ");//scheduled pickup date SCDSCHD
			sqlString.append(usfd.getScheduledPickUpTime() + ", ");//scheduled delivery time SCSTIME
			
			sqlString.append(usfd.getCashPrice() + ", ");//SCDPRICE
			sqlString.append("'" + usfd.getWhseLotNumber().trim() + "', ");//SCDLOT
			sqlString.append("'" + usfd.getPressure().trim() + "', ");//SCDPRESS
			sqlString.append("'" + usfd.getWhseItemNumber().trim() + "', ");//SCDWITM
			sqlString.append("'" + usfd.getItemNumber().trim() + "', ");//SCDTITM
			sqlString.append(usfd.getAvailableDate() + ", ");//SCDAVAILD - available date
			sqlString.append("'" + usfd.getComments().trim() + "', ");//SCDCOMT
			sqlString.append("'" + usfd.getFromRecLoc().trim() + "', ");//SCDFRTTW
			sqlString.append("'" + usfd.getFromDock().trim() + "', ");//SCDFRDK
			sqlString.append("'" + usfd.getStickerFree() + "' ");//SCDSTICK //3/29/11 tw
			sqlString.append(")");
		}
		
		if (requestType.equals("ScheduledLoadInq"))
		{
			InqScheduledFruit isf = (InqScheduledFruit) requestClass.elementAt(0);
			
			sqlString.append("select scdwhse, scddid, scdload, scdtrack, scdtrline, scdcrop, scdcatgy, ");
			sqlString.append("scdvty, scdorgcon, scdmbrc, scdorrun, scdbin, scdton, scdlbs, scdschd, ");
			sqlString.append("scdscht, scdprice, scdlot, scdpress, scdwitm, scdtitm, scdavaild, scdcomt, scdstick, ");
			sqlString.append("scdfrttw, scdfrdk, "); // 6/13/11  tw
			sqlString.append("schtrack, schhaul, schltyp, schorfl, schrloc, schloadid, schrecf, schsdeld, ");
			sqlString.append("schsdelt, schadeld, schadelt, schcomt, schcrtu, schcrtd, schcrtt, ");
			sqlString.append("schchgu, schchgd, schchgt, ");
			sqlString.append("schtodk, tl.rlcdockd as tlrlcdockd, fl.rlcdockd as flrlcdockd, "); // 5/5/11 tw
			sqlString.append("schdo#, schbino, schtrfl, "); // 6/13/11 tw
			sqlString.append("sasuno, sasunm, saadr1, saadr2, saadr3, saadr4, satown, saecar, sapono, saadid, ");
			sqlString.append("a.idsunm as whidsunm, b.idsunm as huidsunm, ");
			sqlString.append("tw.mwwhnm as twmwwhnm, fw.mwwhnm as fwmwwhnm, ");
			sqlString.append(" cc.GRPNAME as ccGRPNAME, va.GRPNAME as vaGRPNAME, ");
			sqlString.append("ga.GRPNAME as gaGRPNAME, og.GRPNAME as ogGRPNAME ");
			// Changed the main file - to reflect Transfers as well -- all types of loads  7/19/11 - TW
			//sqlString.append("from db" + isf.getEnvironment().trim() +  ".grpywhse ");
			sqlString.append("from db" + isf.getEnvironment().trim() + ".grpyschh ");
			sqlString.append("inner join db" + isf.getEnvironment().trim() + ".grpyschd on scdtrack = schtrack ");
			//  Not needed, want to display ALL loads -- if a filter warehouse was changed it should still display 7/19/11 TW
			//sqlString.append("left outer join db" + isf.getEnvironment().trim() + ".grpywhse on whsno = scdwhse and whsdid = scddid ");
			sqlString.append("left outer join m3djd" + isf.getEnvironment().trim() + ".cidadr on sacono = 100 and scdwhse = sasuno ");
			sqlString.append("and saadte = 2 and scddid = saadid ");
			sqlString.append("left outer join m3djd" + isf.getEnvironment().trim() + ".cidmas a on a.idcono = 100 and scdwhse = a.idsuno ");
			
			//update available fruit page only.
			if (isf.getRequestType().trim().equals("updAvailFruit"))
			{
				sqlString.append("left outer join db" + isf.getEnvironment().trim() +  ".grpywinv ");
				sqlString.append("on scdwhse = invwhse and scddid = invdid ");
				sqlString.append("and scdcrop = invcrop and scdcatgy = invcatgy ");
				sqlString.append("and scdvty = invvty and scdorgcon = invorgcon ");
				sqlString.append("and scdstick = invstick ");
				//and scdorrun = invorfl and scdmbrc = invmbrc "); // 2/18/11 wth
			}
			//  join to get the "To M3 Warehouse" information
			sqlString.append("left outer join m3djd" + isf.getEnvironment().trim() + ".MITWHL tw on tw.mwcono = 100 and schrloc = tw.mwwhlo ");
			// join to get the "To Dock" information
			sqlString.append("left outer join db" + isf.getEnvironment().trim() +  ".grpyrloc tl on schrloc = tl.rlcno and schtodk = tl.rlcdock ");
			// join to get the "From M3 Warehouse" information
			sqlString.append("left outer join m3djd" + isf.getEnvironment().trim() + ".MITWHL fw on fw.mwcono = 100 and scdfrttw = fw.mwwhlo ");
			// join to get the "From Dock" information
			sqlString.append("left outer join db" + isf.getEnvironment().trim() +  ".grpyrloc fl on scdfrttw = fl.rlcno and scdfrdk = fl.rlcdock ");
			sqlString.append("left outer join db" + isf.getEnvironment().trim() +  ".grpygroup cc ");
			sqlString.append("on cc.GRPUSE = 'CROPCODE' and scdcrop = cc.GRPKEY1 ");
			sqlString.append("left outer join db" + isf.getEnvironment().trim() +  ".grpygroup va ");
			sqlString.append("on va.GRPUSE = 'VARIETY' and scdcrop = va.GRPKEY1 AND scdvty = va.GRPKEY2 ");
			sqlString.append("left outer join db" + isf.getEnvironment().trim() +  ".grpygroup ga ");
			sqlString.append("on ga.GRPUSE = 'GRADE' and scdcatgy = ga.GRPKEY1 ");
			sqlString.append("left outer join db" + isf.getEnvironment().trim() +  ".grpygroup og ");
			sqlString.append("on og.GRPUSE = 'ORGANIC' and scdorgcon = og.GRPKEY1 ");
			sqlString.append("left outer join m3djd" + isf.getEnvironment().trim() + ".cidmas b on b.idcono = 100 ");
			sqlString.append("and b.idsuty = '5' and schhaul = b.idsuno ");
			
			//add selection criteria.
			StringBuffer where = new StringBuffer();
			
			if (!isf.getInqScheduledLoadNumber().trim().equals("") &&
				!isf.getInqScheduledLoadNumber().trim().equals("0"))
			{
				where.append("where ");
				where.append("scdtrack = " + isf.getInqScheduledLoadNumber().trim() + " ");
			}
			else
			{	
				if (!isf.getInqRegion().trim().equals(""))
					where.append("where a.idcfi1 = '" + isf.getInqRegion() + "' ");
			
				if (!isf.getInqWhseNo().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scdwhse = '" + isf.getInqWhseNo().trim() + "' ");
				}	
			
				if (!isf.getInqLocAddNo().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scddid = '" + isf.getInqLocAddNo().trim() + "' ");
				}
			
				if (!isf.getInqDeliveryDateFrom().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("schsdeld >= " + isf.getInqDeliveryDateFrom().trim() + " ");
				}
			
				if (!isf.getInqDeliveryDateTo().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("schsdeld <= " + isf.getInqDeliveryDateTo().trim() + " ");
				}
			
				if (!isf.getInqFacility().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("tw.mwfaci = '" + isf.getInqFacility().trim() + "' ");
				}
				
				if (!isf.getInqRecLoc().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("schrloc = '" + isf.getInqRecLoc().trim() + "' ");
				}
			
				if (!isf.getInqDock().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("schtodk = '" + isf.getInqDock().trim() + "' ");
				}
				
				if (!isf.getInqShipFacility().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("fw.mwfaci = '" + isf.getInqShipFacility().trim() + "' ");
				}
				
				if (!isf.getInqShipLoc().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scdfrttw = '" + isf.getInqShipLoc().trim() + "' ");
				}
			
				if (!isf.getInqShipDock().trim().equals(""))
				{
					if (where.toString().trim().equals(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scdfrdk = '" + isf.getInqShipDock().trim() + "' ");
				}
			
				if (!isf.getInqHaulingCompany().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("schhaul = '" + isf.getInqHaulingCompany().trim() + "' ");
				}
			
				if (!isf.getInqCrop().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scdcrop = '" + isf.getInqCrop().trim() + "' ");
				}
			
				if (!isf.getInqGrade().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scdcatgy = '" + isf.getInqGrade().trim() + "' ");
				}
			
				if (!isf.getInqVariety().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scdvty = '" + isf.getInqVariety().trim() + "' ");
				}
			
				if (!isf.getInqOrganic().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scdorgcon = '" + isf.getInqOrganic().trim() + "' ");
				}
			
				if (!isf.getInqStickerFree().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("scdstick = 'Y' ");
				}
			
				if (!isf.getInqCashFruit().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
			
					where.append("scdmbrc = 'Y' ");
				}
				
				if (!isf.getInqStatus().trim().equals("all") && 
						!isf.getRequestType().trim().equals("updAvailFruit"))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("schrecf = '" + isf.getInqStatus().trim() + "' ");
				}
			
				//update available fruit page only.
				if (isf.getRequestType().trim().equals("updAvailFruit"))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("(schrecf = '' or (SChRECf = 'R' AND INVCHGD <= SCHSDELD) ) ");
				}
			
				//Added field representative 2011-02-11 JH
				if (!isf.getInqFieldRepresentative().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				
					where.append("a.idcfi3 = '" + isf.getInqFieldRepresentative().trim() + "' ");
				}
				//	Added filter for Transfer Loads 2011-07-19 TW
				if (!isf.getInqTransfer().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and ");
				    if (isf.getInqTransfer().trim().equals("N"))
				       where.append("schtrfl <> 'Y' ");
				    else
				       where.append("schtrfl = 'Y' ");
				}
				//	Added filter for Bulk Loads 2013-02-27 TW
				if (!isf.getInqBulkOnly().trim().equals(""))
				{
					if (where.toString().trim().equalsIgnoreCase(""))
						where.append("where ");
					else
						where.append("and schltyp = 'BULK' ");
				}
			}
			
			sqlString.append(where);
			
			if (!isf.getInqOrderBySupplier().trim().equals("")) // tw 6/6/12 -- added to sort by Supplier
			{
				sqlString.append("order by a.idsunm, schsdeld, schsdelt, scdtrack ");
			}else{
			   if (isf.getOrderBy().trim().equals(""))
				   sqlString.append("order by schsdeld, schsdelt, scdtrack ");
			}
		
		}
		
		if (requestType.equals("sumScheduledBins"))
		{
			ResultSet rs = (ResultSet) requestClass.elementAt(0);
			InqAvailableFruit iaf = (InqAvailableFruit) requestClass.elementAt(1); 
		
			sqlString.append("select sum(scdbin) as total ");
			sqlString.append("from db" + iaf.getEnvironment().trim() + ".grpyschd ");
			sqlString.append("inner join db" + iaf.getEnvironment().trim() + ".grpywinv on scdwhse = invwhse and scddid = invdid ");
			sqlString.append("and scdcrop = invcrop and scdcatgy = invcatgy and scdvty = invvty ");
			sqlString.append("and scdorgcon = invorgcon ");
			sqlString.append("and scdstick = invstick ");
			//and scdorrun = invorfl and scdmbrc = invmbrc "); // 2/18/11 wth
			sqlString.append("inner join db" + iaf.getEnvironment().trim() + ".GRPYSCHH ON SCDTRACK = SCHTRACK ");
			sqlString.append("where invwhse = '" + rs.getString("invwhse").trim() + "' ");
			sqlString.append("and invdid = '" + rs.getString("invdid").trim() + "' ");//added 20110119 to fix sum of sched inv bins.
			sqlString.append("and invcrop = '" + rs.getString("invcrop").trim() + "' ");
			sqlString.append("and invcatgy = '" + rs.getString("invcatgy").trim() + "' ");
			sqlString.append("and invvty = '" + rs.getString("invvty").trim() + "' ");
			sqlString.append("and invorgcon = '" + rs.getString("invorgcon").trim() + "' ");
			sqlString.append("and invstick = '" + rs.getString("invstick").trim() + "' "); // 2/18/11 wth
		//	sqlString.append("and invorfl = '" + rs.getString("invorfl").trim() + "' "); // 2/18/11 wth
		//	sqlString.append("and invmbrc = '" + rs.getString("invmbrc").trim() + "' "); // 2/18/11 wth
			sqlString.append("AND (SChRECf = '' or (SChRECf = 'R' AND INVCHGD <= SCHSDELD) ) ");
		}
		
		
		if (requestType.equals("verifyScheduleLoadNo"))
		{
			CommonRequestBean crb = (CommonRequestBean) requestClass.elementAt(0);
			
			sqlString.append("select schtrack ");
			sqlString.append("from db" + crb.getEnvironment() + ".GRPYSCHH ");
			sqlString.append("where schtrack = " + crb.getIdLevel1().trim() + " ");
			sqlString.append("AND (schrecf <> 'C' and schrecf <> 'D') ");
		}
		
		
		if (requestType.equals("updateScheduleLoadStatus"))
		{
			CommonRequestBean crb = (CommonRequestBean) requestClass.elementAt(0);
			DateTime rightNow = UtilityDateTime.getSystemDate();
			sqlString.append("update db" + crb.getEnvironment() + ".GRPYSCHH ");
			sqlString.append("set schrecf = '" + crb.getIdLevel2().trim() + "', ");
			sqlString.append("schchgu = '" + crb.getUser().trim() + "', ");
			sqlString.append("schchgd = " + rightNow.getDateFormatyyyyMMdd() + ", ");
			sqlString.append("schchgt = " + rightNow.getTimeFormathhmmss() + " ");
			if (crb.getIdLevel2().trim().equals("R"))
			{
				sqlString.append(", ");
				sqlString.append("schadeld = " + rightNow.getDateFormatyyyyMMdd() + ", ");
				sqlString.append("schadelt = " + rightNow.getTimeFormathhmm() + " ");
				if (!crb.getIdLevel3().trim().equals("0") &&
					!crb.getIdLevel3().trim().equals(""))
					sqlString.append(", schbino = " + crb.getIdLevel3() + " ");
			}
			sqlString.append("where schtrack = " + crb.getIdLevel1().trim() + " ");
		}
		
		
		if (requestType.equals("findRougeSchedLoadsByWhse"))
		{
			UpdAvailableFruit uaf = (UpdAvailableFruit) requestClass.elementAt(0);
			
			sqlString.append("SELECT * FROM DB" + uaf.getEnvironment() + ".GRPYSCHD ");
			sqlString.append("INNER JOIN  DB" + uaf.getEnvironment() + ".GRPYSCHH ");
			sqlString.append("ON SCDTRACK = SCHTRACK ");
			sqlString.append("WHERE SCHRECF = ' ' ");
			sqlString.append("AND SCDWHSE = '" + uaf.getWhseNo().trim() + "' ");
			sqlString.append("AND SCDDID = '" + uaf.getLocAddNo().trim() + "' ");
			sqlString.append("and not exists (select * from db");
			sqlString.append(uaf.getEnvironment() + ".GRPYWINV ");
			sqlString.append("where scdwhse = invwhse and scddid = invdid ");
			sqlString.append("and scdcrop = invcrop and scdcatgy = invcatgy ");
			sqlString.append("and scdvty = invvty and scdorgcon = invorgcon ");
			sqlString.append("and scdstick = invstick) ");
			//and scdorrun = invorfl "); // 2/18/11 wth
			//sqlString.append("and scdmbrc = invmbrc) "); // 2/18/11 wth
		}
		
		
	
	//catch all
	} catch (Exception e) {
			throwError.append(" Error building sql statement" +
						      " for request type " + requestType + ". " + e);
	}

	
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceAvailableFruit.");
		throwError.append("buildSqlStatement(String, Vector)");
		throw new Exception(throwError.toString());
	}
		
	return sqlString.toString();
}



/**
 * Return a drop down single box for crop codes.
 */

public static Vector dropDownCropCode(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("CROPCODE"); //key field to access crop code data.
		dropDownBox = dropDownGenericSingle(requestBean, conn);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownCropCode(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}


/**
 * @author deisen.
 * Return a drop down single box for generic requests.
 */

private static Vector dropDownGenericSingle(CommonRequestBean requestBean,
                                            Connection conn)
throws Exception
{
	StringBuffer           throwError  = new StringBuffer();
	ResultSet              rs          = null;
	Statement              listThem    = null;
	Vector<DropDownSingle> dropDownBox = new Vector<DropDownSingle>();
		
	try {
		
		try {
			
			String sql = new String();
			sql = buildSqlDropDown(requestBean);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement to build a drop down list. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {			 
				
				 while (rs.next())
				 {			 		
					 DropDownSingle oneGenericCode = loadFieldsDropDown(requestBean.getIdLevel1(), rs);					
					 dropDownBox.addElement(oneGenericCode);					
				 }				 
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building drop down vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownGenericSingle(");
		throwError.append("CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return dropDownBox;
}

/**
 * @author deisen.
 * Build an SQL statement for drop down box requests.
 */
	
private static String buildSqlDropDown(CommonRequestBean requestBean)
throws Exception 
{
	StringBuffer sqlString  = new StringBuffer();	
	StringBuffer throwError = new StringBuffer();
	
		
	try {	
		String libraryTT = GeneralUtility.getTTLibrary(requestBean.getEnvironment().trim());
		
		if (requestBean.getIdLevel1().trim().equals("CROPCODE") ||
			requestBean.getIdLevel1().trim().equals("GRADE") ||	
			requestBean.getIdLevel1().trim().equals("ORGANIC") ||
			requestBean.getIdLevel1().trim().equals("LOAD"))
		{			
			sqlString.append("SELECT GRPKEY1, GRPNAME ");
			sqlString.append("FROM " + libraryTT + ".GRPYGROUP ");
			sqlString.append("WHERE ");
			sqlString.append("GRPUSE  = '" + requestBean.getIdLevel1().trim() + "' ");
			sqlString.append("ORDER BY GRPNAME ");
		}
		
		if (requestBean.getIdLevel1().trim().equals("VARIETY"))
		{
			sqlString.append("SELECT x.GRPKEY1 as xGRPKEY1, x.GRPNAME as xGRPNAME, ");
			sqlString.append("y.GRPKEY2 as yGRPKEY2, y.GRPNAME as yGRPNAME ");
			sqlString.append("FROM " + libraryTT + ".GRPYGROUP y ");
			sqlString.append("LEFT OUTER JOIN " + libraryTT + ".GRPYGROUP x ");
			sqlString.append(" on y.grpkey1 = x.grpkey1 and x.grpuse = 'CROPCODE' ");
			sqlString.append("WHERE ");
			sqlString.append("y.GRPUSE  = '" + requestBean.getIdLevel1().trim() + "' ");
			sqlString.append("ORDER BY x.GRPNAME, y.GRPNAME ");
		}
		
		if (requestBean.getIdLevel1().trim().equals("ReceivingWarehouse"))
		{
			// 5/2/11 TW - Changed to a Dual Drop Down List - Whse and Dock
			sqlString.append("SELECT MWWHLO, MWWHNM, RLCDOCK, RLCDOCKD ");
			sqlString.append("FROM M3DJD" + requestBean.getEnvironment() + ".MITWHL ");
			sqlString.append("INNER JOIN DB" + requestBean.getEnvironment() + ".GRPYRLOC " );
			sqlString.append("ON MWWHLO = RLCNO ");
			sqlString.append("WHERE MWCONO = 100 ");
			sqlString.append("ORDER BY MWWHLO, RLCDOCK ");
		}
		
		if (requestBean.getIdLevel1().trim().equals("Hauler"))
		{
			sqlString.append("SELECT IDSUNO, IDSUNM ");
			sqlString.append("FROM M3DJD" + requestBean.getEnvironment() + ".CIDMAS ");
			sqlString.append("INNER JOIN DB" + requestBean.getEnvironment() + ".GRPYHAUL " );
			sqlString.append("ON HAULNO = IDSUNO ");
			sqlString.append("WHERE IDCONO = 100 ");
			sqlString.append("ORDER BY IDSUNM ");
		}
		
		if (requestBean.getIdLevel1().trim().equals("FieldRep"))                    //Added 2011-01-26 JH
		{
			sqlString.append("SELECT CTSTKY, CTTX40 ");
			sqlString.append("FROM M3DJD" + requestBean.getEnvironment() + ".CSYTAB ");
			sqlString.append("WHERE CTCONO = 100 AND CTSTCO='CFS3' and CTSTKY<>''");
			sqlString.append("ORDER BY CTTX40");
		}
		//  Added Facility 2011-05-03 - TW
		if (requestBean.getIdLevel1().trim().equals("Facility"))
		{
			sqlString.append("SELECT CFFACI, CFFACN ");
			sqlString.append("FROM M3DJD" + requestBean.getEnvironment() + ".CFACIL ");
			sqlString.append("INNER JOIN M3DJD" + requestBean.getEnvironment() + ".MITWHL ");
			sqlString.append("ON CFCONO = MWCONO AND CFFACI = MWFACI ");
			sqlString.append("INNER JOIN DB" + requestBean.getEnvironment() + ".GRPYRLOC " );
			sqlString.append("ON MWWHLO = RLCNO ");
			sqlString.append("WHERE CFCONO = 100 ");
			sqlString.append("GROUP BY CFFACI, CFFACN ");
			sqlString.append("ORDER BY CFFACI ");
		}

	} catch (Exception e) {
			throwError.append(" Error building a drop down sql statement" +
						      " for request type " + requestBean.getIdLevel1() + ". " + e);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceQuality.");
		throwError.append("buildSqlDropDown(CommonRequestBean)");
		throw new Exception(throwError.toString());
	}
		
	return sqlString.toString();
}

/**
 * Return a drop down single box for Raw Fruit Grades.
 */

public static Vector dropDownGrade(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("GRADE"); //key field to access crop code data.
		dropDownBox = dropDownGenericSingle(requestBean, conn);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownGrade(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * Return a drop down single box for Organic.
 */

public static Vector dropDownOrganic(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("ORGANIC"); //key field to access crop code data.
		dropDownBox = dropDownGenericSingle(requestBean, conn);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownOrganic(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * Return a drop down single box for Fruit Variety.
 */

public static Vector dropDownVariety(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("VARIETY"); //key field to access crop code data.
		dropDownBox = dropDownGenericDual(requestBean, conn);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownGrade(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * Return a drop down single box for Field Representative.
 */

public static Vector dropDownFieldRep(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("FieldRep"); //key field to access field representative.
		dropDownBox = dropDownGenericSingle(requestBean, conn);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownFieldRep(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * @author deisen.
 * Return a drop down single box for generic requests.
 */

private static Vector dropDownGenericDual(CommonRequestBean requestBean,
                                          Connection conn)
throws Exception
{
	StringBuffer           throwError  = new StringBuffer();
	ResultSet              rs          = null;
	Statement              listThem    = null;
	Vector<DropDownDual> dropDownBox = new Vector<DropDownDual>();
		
	try {
		
		try {
			
			String sql = new String();
			sql = buildSqlDropDown(requestBean);
			listThem = conn.createStatement();
			rs = listThem.executeQuery(sql);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement to build a drop down list. " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
			 try {			 
				
				 while (rs.next())
				 {			 		
					 DropDownDual oneDDD = loadFieldsDropDownDual(requestBean.getIdLevel1(), rs);					
					 dropDownBox.addElement(oneDDD);					
				 }				 
			 	
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building drop down vector from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownGenericDual(");
		throwError.append("CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return dropDownBox;
}

/**
 * @author deisen.
 * Load class fields from result set for a drop down box. (dual)
 */

private static DropDownDual loadFieldsDropDownDual(String requestType, 
						          		  	       ResultSet rs)
throws Exception
{
	StringBuffer   throwError  = new StringBuffer();
	DropDownDual returnValue = new DropDownDual();
	
	try { 
		
//		if  (!requestType.trim().equals("")&& !requestType.trim().equals("Warehouse"))	
//		{
//			
		
		if (requestType.trim().equals("Warehouse")) //20110422-TW
		{
			returnValue.setMasterValue(rs.getString("SECWHSE").trim()); //20110422-TW
			returnValue.setMasterDescription(rs.getString("IDSUNM").trim()); //20110422-TW
			returnValue.setSlaveValue(rs.getString("SAADID").trim()); //20110422-TW
			returnValue.setSlaveDescription(rs.getString("SASUNM").trim()); //20110422-TW
		}
		else
		{
			if  (!requestType.trim().equals("")&& requestType.trim().equals("ReceivingWarehouse"))	
			{
				returnValue.setMasterValue(rs.getString("MWWHLO").trim());
				returnValue.setMasterDescription(rs.getString("MWWHNM").trim());
				returnValue.setSlaveValue(rs.getString("RLCDOCK").trim());
				returnValue.setSlaveDescription(rs.getString("RLCDOCKD").trim());
			}
			else
			{
				if (!requestType.trim().equals(""))
				{
					returnValue.setMasterValue(rs.getString("xGRPKEY1").trim());
					returnValue.setMasterDescription(rs.getString("xGRPNAME").trim());
					returnValue.setSlaveValue(rs.getString("yGRPKEY2").trim());
					returnValue.setSlaveDescription(rs.getString("yGRPNAME").trim());
				}
			}
		}

	} catch(Exception e)
	{
		throwError.append(" Problem loading the Drop Down Dual class");
		throwError.append(" from the result set. " + e) ;
	}

//  *************************************************************************************				
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("loadFieldsDropDownDual(String, String, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Update Available Fruit At Shipping Warehouses.
 */

public static BeanAvailFruit updateAvailableFruit(UpdAvailableFruit inValues)
throws Exception
{			
	StringBuffer throwError    = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	Connection   conn          = null;
	
	try {
		
		conn = ConnectionStack4.getConnection();
		returnValue = updateAvailableFruit(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("updateAvailableFruit(");
		throwError.append("UpdAvailableFruit). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
				
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * Update Available Fruit At Shipping Warehouses.
 */

private static BeanAvailFruit updateAvailableFruit(UpdAvailableFruit inValues, 
							 	                   Connection conn)
throws Exception
{
	StringBuffer throwError    = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	
	try {
 		
 		try {
 			
 			returnValue = deleteAvailableFruit(inValues, conn);
 		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement (delete). " + e);
 		 }	
 		 
		try {
			  
			returnValue = addAvailableFruit(inValues, conn);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (insert). " + e);
		 }
		 
		 //reload a new Bean to return to the dsiplay with the most current values.
		 try {
			 
			 //fill in the new UpadteAvailableFruit stuff here to return the new bean.
			 
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (insert). " + e);
		 }
	
	} catch (Exception e)
	{
		throwError.append(e);
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("updateAvailableFruit(");
		throwError.append("Vector, conn). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Delete available fruit. 
 */

private static BeanAvailFruit deleteAvailableFruit(UpdAvailableFruit inValues, 
												   Connection conn)
throws Exception
{
	StringBuffer throwError     = new StringBuffer();
	String       sql            = new String();
	Statement    deleteIt       = null;	
	BeanAvailFruit  returnValue = new BeanAvailFruit();
	
	try {
		
		try {
			Vector parms = new Vector();
			parms.addElement(inValues);
			sql = buildSqlStatement("deleteAvailableFruit", parms);
			deleteIt = conn.createStatement();
			deleteIt.executeUpdate(sql);			   		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (deleteIt != null)
	   {
		   try {
			   deleteIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("deleteAvailableFruit(");
		throwError.append("UpdAvailableFruit, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());			
	}

	return returnValue;
}

/**
 * Add new available fruit by whse entries.
 */

private static BeanAvailFruit addAvailableFruit(UpdAvailableFruit inValues, 
							 	         		Connection conn)
throws Exception
{
	StringBuffer         throwError  = new StringBuffer();
	String               sql         = new String();
	Statement            insertIt    = null;
	BeanAvailFruit       returnValue = new BeanAvailFruit();
	
	try {
		
 		try {
 			// 3/22/11 TW - added system date here, so all the records are updated with the same date
 			DateTime dt = UtilityDateTime.getSystemDate(); // 3/22/11
 			for (int x = 0; inValues.getListAvailFruitDetail().size() > x; x++)
 			{			   
 				UpdAvailableFruitDetail uafd = (UpdAvailableFruitDetail) inValues.getListAvailFruitDetail().elementAt(x);
 				uafd.setUpdateDate(dt.getDateFormatyyyyMMdd()); // 3/22/11
 				uafd.setUpdateTime(dt.getTimeFormathhmmss()); // 3/22/11
 				Vector parms = new Vector(); 				
 				parms.addElement(uafd);
 				sql = buildSqlStatement("addAvailableFruit", parms);
 				insertIt = conn.createStatement();
 				insertIt.executeUpdate(sql);
 				insertIt.close();
 			}
 		    		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
 		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (insertIt != null)
	   {
		   try {
			  insertIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("addAvailableFruit(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Return scheduled loads by warehouse.
 */
public static BeanAvailFruit findScheduledLoad(CommonRequestBean crb)
throws Exception
{
			
	StringBuffer 	throwError  = new StringBuffer();
	BeanAvailFruit  returnValue = new BeanAvailFruit();
	Connection 		conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		returnValue = findScheduledLoad(crb, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("findScheduledLoad(");
		throwError.append("CommonRequestBean). ");
		returnValue.setThrowError(throwError.toString());
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * Return scheduled load by schedule Number.
 */
private static BeanAvailFruit findScheduledLoad(CommonRequestBean crb, 
											    Connection conn)
throws Exception
{
	StringBuffer      throwError    = new StringBuffer();
	ResultSet         rs            = null;
	Statement         findIt        = null;
	AvailFruitByWhse  afbw			= new AvailFruitByWhse();
	BeanAvailFruit    returnValue   = new BeanAvailFruit();
	String			  requestType   = "";
	String			  sqlString		= "";
			
	try { //catch all
		
		//find any current warehouse data.
		try {
			requestType = "findScheduledLoadByScheduleNo";
			Vector parmClass = new Vector();
			parmClass.addElement(crb);
			sqlString = buildSqlStatement(requestType, parmClass);
			findIt = conn.createStatement();
			rs = findIt.executeQuery(sqlString);
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (findScheduledLoadByScheduleNo). " + e);
		 }
		 
		 if (throwError.toString().trim().equals(""))
		 {
			 try {
				 
				 
				 //vector to hold all detail.
				 Vector dtl = new Vector();

				 while(rs.next())
				 {
					 Vector temp = loadFields("findScheduledLoadDetail", rs);
					 ScheduledLoadDetail sld = (ScheduledLoadDetail) temp.elementAt(0);
					 dtl.addElement(sld);
					 returnValue.setScheduledLoadDetail(dtl);	
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class in method. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (findIt != null)
	   {
		   try {
			  findIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("findScheduledLoad(");
		throwError.append("CommonRequestBean, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Return a drop down dual box for Fruit Warehouse.
 */

public static Vector dropDownWarehouse(InqAvailableFruit requestBean)
throws Exception
{
			
	StringBuffer throwError           		 = new StringBuffer();
	//Vector<DropDownDual>   dualDropDownBox 	 = new Vector<DropDownDual>(); // 20110422 - TW
	//Vector<DropDownTriple> tripleDropDownBox = new Vector<DropDownTriple>(); //20110422 - TW
	Vector dropDownBox = new Vector();
	Connection   conn                 = null;
	ResultSet    rs                   = null;
	Statement    listThem             = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		String sql= new String();
		Vector parmClass = new Vector();
		parmClass.addElement(requestBean);
		String sqlRequest = "Warehouse DropDown";
		if (requestBean.getRequestType().trim().equals("updSchedFruit"))
			sqlRequest = "Warehouse DropDownDual";
		sql = buildSqlStatement(sqlRequest, parmClass);
		listThem = conn.createStatement();
		rs = listThem.executeQuery(sql);
		
		while (rs.next())
		{
			if (requestBean.getRequestType().trim().equals("updSchedFruit") ||
				requestBean.getRequestType().trim().equals("addSchedFruitLoad"))
			{
				dropDownBox.addElement(loadFieldsDropDownDual("Warehouse", rs)); //20110510 - TW
			}else{
				dropDownBox.addElement(loadFieldsDropDownTriple("Warehouse", rs)); //20110510 - TW
			}
		}
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownWarehouse(");
		throwError.append("InqAvailableFruit). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * Find available fruit by warehouse supplier
 * @param -- InqAvailableFruit - selection criteria.
 * @return BeanAvailFruit -- Vector of AvailFruitbyWhseDetail.
 * @throws Exception
 */
public static BeanAvailFruit listAvailableFruit(InqAvailableFruit inqValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack4.getConnection();
		returnValue = listAvailableFruit(inqValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	
	finally {

		try
		{
			if (conn != null)
				ConnectionStack4.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors..
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAvailableFruit.");
			throwError.append("listAvailableFruit(");
			throwError.append("inqAvailableFruit). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 * Find available fruit by warehouse supplier
 * @param -- InqAvailableFruit - selection criteria.
 * @param -- Connection
 * @return BeanAvailFruit -- Vector of AvailFruitbyWhseDetail.
 * @return Connection
 * @throws Exception
 */
private static BeanAvailFruit listAvailableFruit(InqAvailableFruit inqValues, 
												 Connection conn)
throws Exception
{
	// Method level variables
	StringBuffer throwError = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	ResultSet rs = null; 
	Statement listThem = null;
	ResultSet rsSum = null;
	Statement sumThem = null;
	
	try
	{
		try {
		   // Get a list of available fruit based on inquiry criteria
		   listThem = conn.createStatement();
		   Vector parmClass = new Vector();
		   parmClass.addElement(inqValues);
		   String sqlString = buildSqlStatement("AvailableFruitInq", parmClass);
		   rs = listThem.executeQuery(sqlString);
		 
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}
		 
		Vector<AvailFruitByWhseDetail> listHits = new Vector();
		
		if (throwError.toString().trim().equals(""))
		{
			try
			{
				
			 	while (rs.next() && throwError.toString().equals(""))
			    {
			 		Vector temp = loadFields("findWhseFruitDetail", rs);
			     	AvailFruitByWhseDetail afbwd = (AvailFruitByWhseDetail) temp.elementAt(0);
			     	String scheduledBins = "0";
			     	
			     	//accumulate scheduled bins for each record.
			     	try {
			     		sumThem = conn.createStatement();
			 		    Vector parmClass = new Vector();
					    parmClass.addElement(rs);
					    parmClass.addElement(inqValues);
					    String sqlString = buildSqlStatement("sumScheduledBins", parmClass);
					    rsSum = sumThem.executeQuery(sqlString);
					    
					    if (rsSum.next() && rsSum.getString("total") != null)
					    	scheduledBins = rsSum.getString("total");
					    
					    try { //close statement here.
					    	sumThem.close();
					    	rsSum.close();
					    } catch (Exception e) {
					    }
			     		
			     	} catch (Exception e) {
			     		throwError.append("Error in sumScheduledBins. ");
			     	}
			     	
			     	afbwd.setScheduledQty(scheduledBins);
			 		listHits.addElement(afbwd);
	     		}
			 	
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql statement. " + e);
			}
			
			returnValue.setAvailFruitByWhseDetail(listHits);
		}
		 
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
		}
		
		
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("listAvailableFruit(");
			throwError.append("inqAvailableFruit, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 * Retrieving the next available schedule number.
 */
	
public static synchronized String nextScheduleNo(String environment)							
throws Exception 
{
	StringBuffer throwError  = new StringBuffer();
	String       returnValue = "";
		
	try {

		as400 = ConnectionStack.getAS400Object();		
		ProgramCall pgm   = new ProgramCall(as400);
		AS400Text library = new AS400Text(3); 
		
		ProgramParameter[] parmList = new ProgramParameter[2];
		parmList[0] = new ProgramParameter(100);
		parmList[1] = new ProgramParameter(library.toBytes(environment.trim())); 
				pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/CLPNXSCHD.PGM", parmList);
	
		if (pgm.run() != true)
		{
			throwError.append("Error trying to get next Schedule Number. ");
		} else {
			AS400PackedDecimal number = new AS400PackedDecimal(10, 0);
			byte[] data = parmList[0].getOutputData();
			double dd   = number.toDouble(data, 0);
			int    id   = (int) dd;
			as400.disconnectService(AS400.COMMAND);
			returnValue = Integer.toString(id);
		}
	} catch (Exception e) {
			throwError.append(" Error retrieving next available formula identification number. " + e);			
	}
	
	finally {
		if (as400 !=null)
			ConnectionStack.returnAS400Object(as400);
	}
		
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceAvailableFruit.");
		throwError.append("nextScheduleNumber(String)");
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}

/**
 * Add Scheduled loads From Shipping Warehouses.
 */

public static BeanAvailFruit addScheduledLoads(UpdScheduledFruit inValues)
throws Exception
{			
	StringBuffer throwError    = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	Connection   conn          = null;
	
	try {
		
		conn = ConnectionStack4.getConnection();
		returnValue = addScheduledLoads(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("addScheduledLoads(");
		throwError.append("UpdScheduledFruit). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
				
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * Add Scheduled Loads From Shipping Warehouses.
 */

private static BeanAvailFruit addScheduledLoads(UpdScheduledFruit inValues, Connection conn)
throws Exception
{
	StringBuffer         throwError  = new StringBuffer();
	String               sql         = new String();
	Statement            insertIt    = null;
	BeanAvailFruit       returnValue = new BeanAvailFruit();
	
	try {
 		for (int x = 0; inValues.getListScheduledFruitDetail().size() > x; x++)
 		{
 			if (x == 0)//insert the header on the first pass.
 			{
 				Vector parms = new Vector();
 				parms.addElement(inValues);
 				sql = buildSqlStatement("addScheduledLoadHeader", parms);
 				insertIt = conn.createStatement();
 	 			insertIt.executeUpdate(sql);
 	 			insertIt.close();
 			}
 			UpdScheduledFruitDetail usfd = (UpdScheduledFruitDetail) inValues.getListScheduledFruitDetail().elementAt(x);
 			
			//load sequence value for usfd
 			String seq = Integer.toString(x);
 			usfd.setLoadLineNumber(seq);
 			
 			Vector parms = new Vector(); 				
 			parms.addElement(usfd);
 			
 			sql = buildSqlStatement("addScheduledLoadDetail", parms);
 			insertIt = conn.createStatement();
 			insertIt.executeUpdate(sql);
 			insertIt.close();
 		}
 		    		   
 	} catch(Exception e)
 	{
 		throwError.append("Error occured retrieving or executing a sql statement. " + e);
 	}	
	
	
	finally {
	  
	   if (insertIt != null)
	   {
		   try {
			  insertIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("addScheduledLoads(");
		throwError.append("Vector, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Find scheduled loads by inquiry criteria
 * @param -- InqScheduledFruit - selection criteria.
 * @return BeanAvailFruit -- Vector of AvailFruitbyWhseDetail.
 * @throws Exception
 */
public static BeanAvailFruit listScheduledLoads(InqScheduledFruit inqValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack4.getConnection();
		returnValue = listScheduledLoads(inqValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	
	finally {

		try
		{
			if (conn != null)
				ConnectionStack4.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors..
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAvailableFruit.");
			throwError.append("listScheduledLoads(");
			throwError.append("inqScheduledFruit). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 * Find scheduled loads by inquiry criteria
 * @param -- InqScheduledFruit - selection criteria.
 * @param -- Connection
 * @return BeanAvailFruit -- Vector of ScheduledLoadDetail.
 * @return Connection
 * @throws Exception
 */
private static BeanAvailFruit listScheduledLoads(InqScheduledFruit inqValues, 
												 Connection conn)
throws Exception
{
	// Method level variables
	StringBuffer throwError = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	ResultSet rs = null; 
	Statement listThem = null;
	
	try
	{
		try {
		   // Get a list of scheduled loads based on inquiry criteria
		   listThem = conn.createStatement();
		   Vector parmClass = new Vector();
		   parmClass.addElement(inqValues);
		   String sqlString = buildSqlStatement("ScheduledLoadInq", parmClass);
		   rs = listThem.executeQuery(sqlString);
		 
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}
		 
		Vector<ScheduledLoadDetail> listHits = new Vector();
		
		if (throwError.toString().trim().equals(""))
		{
			try
			{
				
			 	while (rs.next() && throwError.toString().equals(""))
			    {
			 		Vector temp = loadFields("listScheduledLoadDetail", rs);
			     	ScheduledLoadDetail sld = (ScheduledLoadDetail) temp.elementAt(0);
			 		listHits.addElement(sld);
	     		}
			 	
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql statement. " + e);
			}
			
			returnValue.setScheduledLoadDetail(listHits);
		}
		 
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
		}
		
		
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAvailableFruit.");
			throwError.append("listScheduledLoads(");
			throwError.append("InqScheduledFruit, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 * Return a drop down single box of Valid Raw Fruit Facilities
 */

public static Vector dropDownFacility(CommonRequestBean requestBean)
throws Exception 
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("Facility"); //key field to access crop code data.
		dropDownBox = dropDownGenericSingle(requestBean, conn);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownFacility(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * Return a drop down single box for Truck Load Types.
 */

public static Vector dropDownLoadType(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("LOAD"); //key field to access crop code data.
		dropDownBox = dropDownGenericSingle(requestBean, conn);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownLoadType(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * Return a drop down single box of Haulers.
 */

public static Vector dropDownHauler(CommonRequestBean requestBean)
throws Exception
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("Hauler"); //key field to access crop code data.
		dropDownBox = dropDownGenericSingle(requestBean, conn);		
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownReceivingWarehouse(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

/**
 * validate the schedule load number exists.
 * use IdLevel1 for schedule load number.
 */

public static String verifyScheduleLoadNo(CommonRequestBean requestBean)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	String       returnValue = new String();
	Connection   conn        = null;
	
	try {
		
		conn = com.treetop.utilities.ConnectionStack4.getConnection();
		returnValue = verifyScheduleLoadNo(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   com.treetop.utilities.ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("verifyScheduleLoadNo(");
		throwError.append("CommonRequestBean). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * validate schedule load number.
 */

private static String verifyScheduleLoadNo(CommonRequestBean requestBean,
						                   Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	String       sql         = new String();
	ResultSet    rs          = null;
	Statement    listThem    = null;
	String		 requestType = new String();
	String       returnValue = new String();
	
	try {
		
 		try {	
			
			Vector<CommonRequestBean> commonRequest = new Vector<CommonRequestBean>();
			commonRequest.addElement(requestBean);
			
			if (!requestBean.getIdLevel1().trim().equals("") )
			{
				requestType = "verifyScheduleLoadNo";
				sql = buildSqlStatement(requestType, commonRequest);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);
				
				if (!rs.next())
					returnValue = "The schedule load number (" + requestBean.getIdLevel1() + ") could not be found";
			}
			
			else
				returnValue = "The schedule request number should not be blank";
	
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("verfiyScheduleLoadNo(");
		throwError.append("CommonRequestBean, conn). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Update scheduled loads.
 * by scheduled load nubmer.
 * @param UpdScheduled Fruit
 * @return BeanAvailableFruit
 */

public static BeanAvailFruit updateScheduledLoad(UpdScheduledFruit inValues)
throws Exception
{			
	StringBuffer throwError    = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	Connection   conn          = null;
	
	try {
		
		conn = ConnectionStack4.getConnection();
		returnValue = updateScheduledLoad(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("updateScheduledLoad(");
		throwError.append("UpdAvailableFruit). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
				
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * Update scheduled loads by Schedule Number.
 */

private static BeanAvailFruit updateScheduledLoad(UpdScheduledFruit inValues, 
							 	                  Connection conn)
throws Exception
{
	StringBuffer throwError    = new StringBuffer();
	BeanAvailFruit returnValue = new BeanAvailFruit();
	Statement updIt = null; 
	
	try { //catch all

 		try {
 			
 			returnValue = deleteScheduledLoads(inValues, conn);
 		   
 		 } catch(Exception e)
 		 {
 		   	throwError.append("Error occured retrieving or executing a sql statement (delete). " + e);
 		 }	
 		 
		try {
			  
			returnValue = addScheduledLoads(inValues, conn);
		   
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (insert). " + e);
		 }
		 
		 //reload a new Bean to return to the dsiplay with the most current values.
		 try {
			 
			 //fill in the new UpadteAvailableFruit stuff here to return the new bean.
			 
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement (insert). " + e);
		 }
	} catch(Exception e)
	{
		throwError.append(" Problem updating A scheduled load. " + e) ;
		// close statement
	} finally {
		try {
			if (updIt != null)
				updIt.close();
		} catch (Exception e) {
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("updateScheduledLoad(");
		throwError.append("UpdScheduledFruit, conn). ");

		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Delete scheduled loads. 
 */

private static BeanAvailFruit deleteScheduledLoads(UpdScheduledFruit inValues, 
												  Connection conn)
throws Exception
{
	StringBuffer throwError     = new StringBuffer();
	String       sql            = new String();
	Statement    deleteIt       = null;	
	BeanAvailFruit  returnValue = new BeanAvailFruit();
	
	try {
		
		try {
			Vector parms = new Vector();
			parms.addElement(inValues);
			
			//remove header with matching tracking no.
			sql = buildSqlStatement("deleteScheduledLoadHeader", parms);
			deleteIt = conn.createStatement();
			deleteIt.executeUpdate(sql);	
			deleteIt.close();
			
			//remove detail records with matching tracking no.
			sql = buildSqlStatement("deleteScheduledLoadDetail", parms);
			deleteIt = conn.createStatement();
			deleteIt.executeUpdate(sql);	
			deleteIt.close();
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
	  
	   if (deleteIt != null)
	   {
		   try {
			   deleteIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("deleteScheduledLoads(");
		throwError.append("UpdAvailableFruit, conn). ");
		
		returnValue.setStatusMessage(throwError.toString().trim());	
		
		throw new Exception(throwError.toString());			
	}

	return returnValue;
}

/**
 * Update load status.
 * use IdLevel1 for schedule load number.
 * use IdLevel2 for status value.
 */

public static String updateScheduleLoadStatus(CommonRequestBean requestBean)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	String       returnValue = new String();
	Connection   conn        = null;
	
	try {
		
		conn = com.treetop.utilities.ConnectionStack4.getConnection();
		returnValue = updateScheduleLoadStatus(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   com.treetop.utilities.ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("updateScheduleLoadStatus(");
		throwError.append("CommonRequestBean). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 * Update load status.
 * use IdLevel1 for schedule load number.
 * use IdLevel2 for status value.
 */

private static String updateScheduleLoadStatus(CommonRequestBean requestBean,
						                       Connection conn)
throws Exception
{
	StringBuffer throwError  = new StringBuffer();
	String       sql         = new String();
	Statement    updateIt    = null;
	String		 requestType = new String();
	String       returnValue = new String();
	
	try { //catch all
		
 		try {	//update scheduled load
 			requestType = "updateScheduleLoadStatus";
 			Vector parmClass = new Vector();
 			parmClass.addElement(requestBean);
			sql = buildSqlStatement(requestType, parmClass);
			updateIt = conn.createStatement();
			updateIt.executeUpdate(sql);
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
		 }	

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		try {
			updateIt.close();
		} catch(Exception el){
		}
		
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("updateScheduleLoadStatus(");
		throwError.append("CommonRequestBean, conn). ");
		
		returnValue = throwError.toString().trim();	
		
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * @author twalto.
 * Load class fields from result set for a drop down box. (triple)
 *   Created 4/22/11 - TW
 */

private static DropDownTriple loadFieldsDropDownTriple(String requestType, 
						          		  	           ResultSet rs)
throws Exception
{
	StringBuffer   throwError  = new StringBuffer();
	DropDownTriple returnValue = new DropDownTriple();
	
	try { 
		
		if (requestType.trim().equals("Warehouse"))
		{
			returnValue.setList1Value(rs.getString("IDCFI1").trim());
			returnValue.setList1Description(rs.getString("IDCFI1").trim());
			returnValue.setList2Value(rs.getString("SECWHSE").trim());
			returnValue.setList2Description(rs.getString("IDSUNM").trim());
			returnValue.setList3Value(rs.getString("SAADID").trim());
			returnValue.setList3Description(rs.getString("SASUNM").trim());
		}

	} catch(Exception e)
	{
		throwError.append(" Problem loading the Drop Down Triple class");
		throwError.append(" from the result set. " + e) ;
	}

//  *************************************************************************************				
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("loadFieldsDropDownTriple(String, String, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}

	return returnValue;
}

/**
 * Return a drop down single box of Receiving Warehouses.
 */

public static Vector dropDownReceivingWarehouse(CommonRequestBean requestBean)
throws Exception 
{
			
	StringBuffer throwError  = new StringBuffer();
	Vector       dropDownBox = new Vector();
	Connection   conn        = null;
	
	try {
		conn = ConnectionStack4.getConnection();
		requestBean.setIdLevel1("ReceivingWarehouse"); //key field to access crop code data.
		//dropDownBox = dropDownGenericSingle(requestBean, conn); 5/3/11 TW Changed to a Dual
		dropDownBox = dropDownGenericDual(requestBean, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ConnectionStack4.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceAvailableFruit.");
		throwError.append("dropDownReceivingWarehouse(");
		throwError.append("CommonRequestBean). ");
		throw new Exception(throwError.toString());
	}
	
	return dropDownBox;
}

}



