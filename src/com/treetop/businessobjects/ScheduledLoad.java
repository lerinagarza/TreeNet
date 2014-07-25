/*
 * Created on October 25, 2010
 *
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Scheduled Load Header Information
 * 		-- Generally loaded from file GRPYSCHH
 */
public class ScheduledLoad{
	
	protected	String		scheduleLoadNo		   = "";	//SCHTRACK
	protected	String		haulingCompany		   = "";	//SCHHAUL
	protected   String      haulingCompanyName     = "";    //IDSUNM - cidmas
	protected   String      haulerVerificationNo   = "";    //SCHLOADID
	protected   String      loadType               = "";    //SCHLTYP
	protected   String      orchardRun             = "";    //SCHORFL
	protected	String		receivingLocationNo	   = "";	//SCHRLOC
	protected   String      receivingLocationDesc  = "";    //MWWHNM - mitwhl
	protected	String		receivingDockNo		   = "";	//SCHTODK
	protected	String		receivingDockDesc	   = "";	//RLCDOCKD - grpyrloc
	protected   String      loadReceivedFlag       = "";    //SCHRECF
	protected	String		scheduledDeliveryDate  = "";	//SCHSDELD
	protected	String		scheduledDeliveryTime  = "";	//SCHSDELT
	protected	String		actualDeliveryDate     = "";	//SCHADELD
	protected	String		actualDeliveryTime	   = "";    //SCHADELT
	protected   String		headerComment		   = "";	//SCHCOMT
	protected	String		createUser			   = "";	//SCHCRTU
	protected	String		createDate			   = "0";	//SCHCRTD
	protected	String		createTime			   = "0";	//SCHCRTT
	protected	String		changeUser			   = "";	//SCHCHGU
	protected	String		changeDate			   = "0";	//SCHCHGD
	protected	String		changeTime			   = "0";	//SCHCHGT
	protected	String		emptyBinCount          = "0";   //SCHBINO
	protected	String		distributionOrder	   = "";	//SCHDO#
	protected	String		transferFlag		   = "";	//SCHTRFL
	
	protected   String      availScheduledQtyTotal = "0";   //in use with AvailableFruitByWhseDetail
	protected   String      allScheduledQtyTotal   = "0";   //in use with ScheduledLoadDetail
	
	
	/**
	 *  // Constructor
	 */
	public ScheduledLoad() {
		super();

	}


	public String getActualDeliveryDate() {
		return actualDeliveryDate;
	}


	public void setActualDeliveryDate(String actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}


	public String getActualDeliveryTime() {
		return actualDeliveryTime;
	}


	public void setActualDeliveryTime(String actualDeliveryTime) {
		this.actualDeliveryTime = actualDeliveryTime;
	}


	public String getAllScheduledQtyTotal() {
		return allScheduledQtyTotal;
	}


	public void setAllScheduledQtyTotal(String allScheduledQtyTotal) {
		this.allScheduledQtyTotal = allScheduledQtyTotal;
	}


	public String getAvailScheduledQtyTotal() {
		return availScheduledQtyTotal;
	}


	public void setAvailScheduledQtyTotal(String availScheduledQtyTotal) {
		this.availScheduledQtyTotal = availScheduledQtyTotal;
	}


	public String getChangeDate() {
		return changeDate;
	}


	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}


	public String getChangeTime() {
		return changeTime;
	}


	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}


	public String getChangeUser() {
		return changeUser;
	}


	public void setChangeUser(String changeUser) {
		this.changeUser = changeUser;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getHaulerVerificationNo() {
		return haulerVerificationNo;
	}


	public void setHaulerVerificationNo(String haulerVerificationNo) {
		this.haulerVerificationNo = haulerVerificationNo;
	}


	public String getHaulingCompany() {
		return haulingCompany;
	}


	public void setHaulingCompany(String haulingCompany) {
		this.haulingCompany = haulingCompany;
	}


	public String getHaulingCompanyName() {
		return haulingCompanyName;
	}


	public void setHaulingCompanyName(String haulingCompanyName) {
		this.haulingCompanyName = haulingCompanyName;
	}


	public String getHeaderComment() {
		return headerComment;
	}


	public void setHeaderComment(String headerComment) {
		this.headerComment = headerComment;
	}


	public String getLoadReceivedFlag() {
		return loadReceivedFlag;
	}


	public void setLoadReceivedFlag(String loadReceivedFlag) {
		this.loadReceivedFlag = loadReceivedFlag;
	}


	public String getLoadType() {
		return loadType;
	}


	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}


	public String getOrchardRun() {
		return orchardRun;
	}


	public void setOrchardRun(String orchardRun) {
		this.orchardRun = orchardRun;
	}


	public String getReceivingLocationDesc() {
		return receivingLocationDesc;
	}


	public void setReceivingLocationDesc(String receivingLocationDesc) {
		this.receivingLocationDesc = receivingLocationDesc;
	}


	public String getReceivingLocationNo() {
		return receivingLocationNo;
	}


	public void setReceivingLocationNo(String receivingLocationNo) {
		this.receivingLocationNo = receivingLocationNo;
	}


	public String getScheduledDeliveryDate() {
		return scheduledDeliveryDate;
	}


	public void setScheduledDeliveryDate(String scheduledDeliveryDate) {
		this.scheduledDeliveryDate = scheduledDeliveryDate;
	}


	public String getScheduledDeliveryTime() {
		return scheduledDeliveryTime;
	}


	public void setScheduledDeliveryTime(String scheduledDeliveryTime) {
		this.scheduledDeliveryTime = scheduledDeliveryTime;
	}


	public String getScheduleLoadNo() {
		return scheduleLoadNo;
	}


	public void setScheduleLoadNo(String scheduleLoadNo) {
		this.scheduleLoadNo = scheduleLoadNo;
	}


	public String getReceivingDockDesc() {
		return receivingDockDesc;
	}


	public void setReceivingDockDesc(String receivingDockDesc) {
		this.receivingDockDesc = receivingDockDesc;
	}


	public String getReceivingDockNo() {
		return receivingDockNo;
	}


	public void setReceivingDockNo(String receivingDockNo) {
		this.receivingDockNo = receivingDockNo;
	}


	public String getEmptyBinCount() {
		return emptyBinCount;
	}


	public void setEmptyBinCount(String emptyBinCount) {
		this.emptyBinCount = emptyBinCount;
	}


	public String getDistributionOrder() {
		return distributionOrder;
	}


	public void setDistributionOrder(String distributionOrder) {
		this.distributionOrder = distributionOrder;
	}


	public String getTransferFlag() {
		return transferFlag;
	}


	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}
	

}
