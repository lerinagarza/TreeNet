/*
 * Created on July 19, 2010
 *
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Available Fruit By Warehouse Detail Information
 * 		-- Generally loaded from file GRPYWINV and GRPYGROUP
 */
public class AvailFruitByWhseDetail extends AvailFruitByWhse{
	
	protected	String		whseLoadNo		= "";	//INVLOAD
	protected	String		cropCode		= "";	//INVCROP
	protected   String      cropCodeDesc    = "";   //GRPNAME
	protected	String		gradeCode		= "";	//INVGRADE
	protected   String      gradeDesc       = "";   //GRPNAME
	protected	String		organicCode		= "";	//INVORGA
	protected   String      organicDesc     = "";   //GRPNAME
	protected	String		varietyCode		= "";	//INVVARI
	protected   String      varietyDesc     = "";   //GRPNAME
	protected	String		stickerFree		= "";	//INVSTICK
	protected   String      page			= "";   //GRPPAGE
	protected	String		tons			= "";	//INVTON
	protected	String		pounds			= "0";	//INVLBS
	protected	String		binCount		= "0";	//INVBIN
	protected	String		availDate		= "0";	//INVVAILD
	protected   String      dateLotCreated  = "";   //INVBORND
	protected	String		whseAssignedLot	= "";	//INVLOT
	protected	String		whseItemNo		= "";	//INVWITM
	protected	String		ttiItemNo		= "";	//INVTITM
	protected	String		comment			= "";	//INVCOMT
	protected	String		RecvFlag		= "";	//INVPREC
	protected	String		pickupFlag		= "";	//INVPSCL
	protected   String		scheduledQty    = "";	//*******
	protected   String      duplicated      = "";   //when built.
	protected	String		purchaseQuantity	= ""; //INVPQY
	protected	String		purchasePrice		= ""; //INVPPC
	
	/**
	 *  // Constructor
	 */
	public AvailFruitByWhseDetail() {
		super();

	}

	public String getAvailDate() {
		return availDate;
	}


	public void setAvailDate(String availDate) {
		this.availDate = availDate;
	}


	public String getBinCount() {
		return binCount;
	}


	public void setBinCount(String binCount) {
		this.binCount = binCount;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getGradeCode() {
		return gradeCode;
	}


	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}


	public String getOrganicCode() {
		return organicCode;
	}


	public void setOrganicCode(String organicCode) {
		this.organicCode = organicCode;
	}


	public String getPounds() {
		return pounds;
	}


	public void setPounds(String pounds) {
		this.pounds = pounds;
	}

	public String getTons() {
		return tons;
	}


	public void setTons(String tons) {
		this.tons = tons;
	}


	public String getTtiItemNo() {
		return ttiItemNo;
	}


	public void setTtiItemNo(String ttiItemNo) {
		this.ttiItemNo = ttiItemNo;
	}


	public String getVarietyCode() {
		return varietyCode;
	}


	public void setVarietyCode(String varietyCode) {
		this.varietyCode = varietyCode;
	}


	public String getWhseAssignedLot() {
		return whseAssignedLot;
	}


	public void setWhseAssignedLot(String whseAssignedLot) {
		this.whseAssignedLot = whseAssignedLot;
	}


	public String getWhseItemNo() {
		return whseItemNo;
	}


	public void setWhseItemNo(String whseItemNo) {
		this.whseItemNo = whseItemNo;
	}


	public String getWhseLoadNo() {
		return whseLoadNo;
	}


	public void setWhseLoadNo(String whseLoadNo) {
		this.whseLoadNo = whseLoadNo;
	}

	public String getPickupFlag() {
		return pickupFlag;
	}


	public void setPickupFlag(String pickupFlag) {
		this.pickupFlag = pickupFlag;
	}


	public String getDateLotCreated() {
		return dateLotCreated;
	}


	public void setDateLotCreated(String dateLotCreated) {
		this.dateLotCreated = dateLotCreated;
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public String getRecvFlag() {
		return RecvFlag;
	}


	public void setRecvFlag(String recvFlag) {
		RecvFlag = recvFlag;
	}


	public String getCropCode() {
		return cropCode;
	}


	public void setCropCode(String cropCode) {
		this.cropCode = cropCode;
	}


	public String getScheduledQty() {
		return scheduledQty;
	}


	public void setScheduledQty(String scheduledQty) {
		this.scheduledQty = scheduledQty;
	}


	public String getCropCodeDesc() {
		return cropCodeDesc;
	}


	public void setCropCodeDesc(String cropCodeDesc) {
		this.cropCodeDesc = cropCodeDesc;
	}


	public String getGradeDesc() {
		return gradeDesc;
	}


	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}


	public String getOrganicDesc() {
		return organicDesc;
	}


	public void setOrganicDesc(String organicDesc) {
		this.organicDesc = organicDesc;
	}


	public String getVarietyDesc() {
		return varietyDesc;
	}


	public void setVarietyDesc(String varietyDesc) {
		this.varietyDesc = varietyDesc;
	}


	public String getDuplicated() {
		return duplicated;
	}


	public void setDuplicated(String duplicated) {
		this.duplicated = duplicated;
	}

	public String getStickerFree() {
		return stickerFree;
	}

	public void setStickerFree(String stickerFree) {
		this.stickerFree = stickerFree;
	}

	public String getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(String purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	

}
