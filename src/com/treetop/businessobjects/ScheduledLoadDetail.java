/*
 * Created on August 24, 2010
 * Modified October 25, 2010
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Scheduled Load By Warehouse Detail Information
 * 		-- Generally loaded from file GRPYSCHD, GRPYGROUP, 
 *                                    CIDMAS, MITWHL
 */
public class ScheduledLoadDetail extends ScheduledLoad{
	
	protected	String		whseNumber			  = "";	 //SCDWHSE
	protected	String		whseAddressNumber	  = "";	 //SCDDID	
	protected	String		whseLoadNo			  = "";	 //SCDLOAD
	protected   String      scheduledLoadLineNo   = "";  //SCDTRLINE
	protected	String		cropCode			  = "";	 //SCDCROP
	protected   String      cropCodeDesc    	  = "";  //GRPNAME
	protected	String		gradeCode			  = "";	 //SCDCATGY
	protected   String      gradeDesc       	  = "";  //GRPNAME
	protected	String		organicCode			  = "";	 //SCDORGCON
	protected   String      organicDesc     	  = "";  //GRPNAME
	protected	String		varietyCode			  = "";	 //SCDVTY
	protected   String      varietyDesc     	  = "";  //GRPNAME
	
	protected	String		tons				  = "";	 //SCDTON
	protected	String		pounds				  = "0"; //SCDLBS
	protected	String		binCount			  = "0"; //SCDBIN
	
	protected   Warehouse	warehouse			  = new Warehouse();
	protected   String      whseDescription       = "";  //IDSUNM - cidmas
	
	protected   String      orchardRunDtlFlag     = "";  //SCDORRUN
	protected	String		stickerFreeFlag			  = "";  //SCDSTICK
	protected   String      scheduledPickupDate   = "";  //SCDSCHD
	protected   String      scheduledPickupTime   = "";  //SCDSCHT
	protected   String      lastTestedPressure    = "";  //SCDPRESS
	protected   String      cashPrice             = "";  //SCDPRICE
	protected   String      whseAssignedLot       = "";  //SCDLOT
	protected   String      whseAssignedItem      = "";  //SCDWITM
	protected   String      treeTopItem           = "";  //SCDTITM
	protected	String 		treeTopItemDesc		  = "";  //MMITDS (from the MITMAS file)
	protected   String      detailComment         = "";  //SCDCOMT
	protected   String      availableDate         = "";  //SCDAVAILD
	protected   String      memberOrCash		  = "";	 //SCDMBRC
	// for use with Transfers
	protected	String		shippingLocationNo	  = "";	//SCDFRTTW
	protected   String      shippingLocationDesc  = ""; //MWWHNM - mitwhl
	protected	String		shippingDockNo		  = "";	//SCDFRDK
	protected	String		shippingDockDesc	  = "";	//RLCDOCKD - grpyrloc
	
	protected   String		noAvailFruitFlag      = "";  //
	
	/**
	 *  // Constructor
	 */
	public ScheduledLoadDetail() {
		super();

	}



	public String getAvailableDate() {
		return availableDate;
	}



	public void setAvailableDate(String availableDate) {
		this.availableDate = availableDate;
	}



	public String getBinCount() {
		return binCount;
	}



	public void setBinCount(String binCount) {
		this.binCount = binCount;
	}



	public String getCashPrice() {
		return cashPrice;
	}



	public void setCashPrice(String cashPrice) {
		this.cashPrice = cashPrice;
	}



	public String getCropCode() {
		return cropCode;
	}



	public void setCropCode(String cropCode) {
		this.cropCode = cropCode;
	}



	public String getCropCodeDesc() {
		return cropCodeDesc;
	}



	public void setCropCodeDesc(String cropCodeDesc) {
		this.cropCodeDesc = cropCodeDesc;
	}



	public String getDetailComment() {
		return detailComment;
	}



	public void setDetailComment(String detailComment) {
		this.detailComment = detailComment;
	}



	public String getGradeCode() {
		return gradeCode;
	}



	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}



	public String getGradeDesc() {
		return gradeDesc;
	}



	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}



	public String getLastTestedPressure() {
		return lastTestedPressure;
	}



	public void setLastTestedPressure(String lastTestedPressure) {
		this.lastTestedPressure = lastTestedPressure;
	}



	public String getNoAvailFruitFlag() {
		return noAvailFruitFlag;
	}



	public void setNoAvailFruitFlag(String noAvailFruitFlag) {
		this.noAvailFruitFlag = noAvailFruitFlag;
	}



	public String getOrchardRunDtlFlag() {
		return orchardRunDtlFlag;
	}



	public void setOrchardRunDtlFlag(String orchardRunDtlFlag) {
		this.orchardRunDtlFlag = orchardRunDtlFlag;
	}



	public String getOrganicCode() {
		return organicCode;
	}



	public void setOrganicCode(String organicCode) {
		this.organicCode = organicCode;
	}



	public String getOrganicDesc() {
		return organicDesc;
	}



	public void setOrganicDesc(String organicDesc) {
		this.organicDesc = organicDesc;
	}



	public String getPounds() {
		return pounds;
	}



	public void setPounds(String pounds) {
		this.pounds = pounds;
	}



	public String getScheduledLoadLineNo() {
		return scheduledLoadLineNo;
	}



	public void setScheduledLoadLineNo(String scheduledLoadLineNo) {
		this.scheduledLoadLineNo = scheduledLoadLineNo;
	}



	public String getScheduledPickupDate() {
		return scheduledPickupDate;
	}



	public void setScheduledPickupDate(String scheduledPickupDate) {
		this.scheduledPickupDate = scheduledPickupDate;
	}



	public String getScheduledPickupTime() {
		return scheduledPickupTime;
	}



	public void setScheduledPickupTime(String scheduledPickupTime) {
		this.scheduledPickupTime = scheduledPickupTime;
	}



	public String getTons() {
		return tons;
	}



	public void setTons(String tons) {
		this.tons = tons;
	}



	public String getTreeTopItem() {
		return treeTopItem;
	}



	public void setTreeTopItem(String treeTopItem) {
		this.treeTopItem = treeTopItem;
	}



	public String getVarietyCode() {
		return varietyCode;
	}



	public void setVarietyCode(String varietyCode) {
		this.varietyCode = varietyCode;
	}



	public String getVarietyDesc() {
		return varietyDesc;
	}



	public void setVarietyDesc(String varietyDesc) {
		this.varietyDesc = varietyDesc;
	}



	public Warehouse getWarehouse() {
		return warehouse;
	}



	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}



	public String getWhseAddressNumber() {
		return whseAddressNumber;
	}



	public void setWhseAddressNumber(String whseAddressNumber) {
		this.whseAddressNumber = whseAddressNumber;
	}



	public String getWhseAssignedItem() {
		return whseAssignedItem;
	}



	public void setWhseAssignedItem(String whseAssignedItem) {
		this.whseAssignedItem = whseAssignedItem;
	}



	public String getWhseAssignedLot() {
		return whseAssignedLot;
	}



	public void setWhseAssignedLot(String whseAssignedLot) {
		this.whseAssignedLot = whseAssignedLot;
	}



	public String getWhseDescription() {
		return whseDescription;
	}



	public void setWhseDescription(String whseDescription) {
		this.whseDescription = whseDescription;
	}



	public String getWhseLoadNo() {
		return whseLoadNo;
	}



	public void setWhseLoadNo(String whseLoadNo) {
		this.whseLoadNo = whseLoadNo;
	}



	public String getWhseNumber() {
		return whseNumber;
	}



	public void setWhseNumber(String whseNumber) {
		this.whseNumber = whseNumber;
	}



	public String getMemberOrCash() {
		return memberOrCash;
	}



	public void setMemberOrCash(String memberOrCash) {
		this.memberOrCash = memberOrCash;
	}



	public String getStickerFreeFlag() {
		return stickerFreeFlag;
	}



	public void setStickerFreeFlag(String stickerFreeFlag) {
		this.stickerFreeFlag = stickerFreeFlag;
	}



	public String getShippingDockDesc() {
		return shippingDockDesc;
	}



	public void setShippingDockDesc(String shippingDockDesc) {
		this.shippingDockDesc = shippingDockDesc;
	}



	public String getShippingDockNo() {
		return shippingDockNo;
	}



	public void setShippingDockNo(String shippingDockNo) {
		this.shippingDockNo = shippingDockNo;
	}



	public String getShippingLocationDesc() {
		return shippingLocationDesc;
	}



	public void setShippingLocationDesc(String shippingLocationDesc) {
		this.shippingLocationDesc = shippingLocationDesc;
	}



	public String getShippingLocationNo() {
		return shippingLocationNo;
	}



	public void setShippingLocationNo(String shippingLocationNo) {
		this.shippingLocationNo = shippingLocationNo;
	}



	public String getTreeTopItemDesc() {
		return treeTopItemDesc;
	}



	public void setTreeTopItemDesc(String treeTopItemDesc) {
		this.treeTopItemDesc = treeTopItemDesc;
	}



}
