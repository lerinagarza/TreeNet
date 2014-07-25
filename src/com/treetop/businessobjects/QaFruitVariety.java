/*
 * Created on July 5, 2010 
 */

package com.treetop.businessobjects;

/**
 * @author deisen
 * 
 *   8/16/11 tw - changed to not Extend QaSpecifiction
 *      Added the fields for Spec and Formula along with Type and Revision Date/Time
 *
 * Contains crop and fruit variety information.
 * 	
 */

public class QaFruitVariety {
	
								// from File 	dbprd/QAPRFVAR
								//           m3djdprd/MPDOPT
	
	protected	String			companyNumber			= "";	//FVCONO
	protected	String			divisionNumber			= "";	//FVDIVI
	protected	String			applicationType			= "";	//FVATYP
	   // use the idNumber as Either the Spec Number or the Formula Number
	   // which one would be determined by the typeCode
	protected	String			idNumber				= "";   //FVSPNO
	protected	String			revisionDate			= "";	//FVRDTE
	protected	String			revisionTime			= "";	//FVRTME
	
	protected	String			attributeCropModel		= "";	//FVATMO
	protected	String			attributeCropModelDescription = ""; // ADTX40 starting at field 3
	protected	String			attributeIdentity		= "";	//FVATID
	protected	String			fruitVarietyValue		= "";	//FVVALU
	protected	String			fruitVarietyDescription	= "";	//PFTX30
	protected	String			includeExclude 			= "";	//FVIECD
	protected	String			includePercentage		= "";	//FVIPCT
	protected	String			includeMinimumPercent	= "";	//FVMIIP
	protected	String			includeMaximumPercent	= "";	//FVMXIP	
	protected	String			updatedDate				= "";	//FVUDTE
	protected	String			updatedTime				= "";	//FVUTME
	protected	String			updatedUser				= "";	//FVUUSR
	protected	String			createdDate				= "";	//FVCDTE
	protected	String			createdTime				= "";	//FVCTME
	protected	String			createdUser				= "";	//FVCUSR
		
	/**
	 *  // Constructor
	 */
	public QaFruitVariety() {
		super();
	}

	/**
	 * @return Returns the attribute crop model.
	 */
	public String getAttributeCropModel() {
		return attributeCropModel;
	}
	/**
	 * @param Sets the attribute crop model.
	 */
	public void setAttributeCropModel(String attributeCropModel) {
		this.attributeCropModel = attributeCropModel;
	}
	/**
	 * @return Returns the attribute identity code.
	 */
	public String getAttributeIdentity() {
		return attributeIdentity;
	}
	/**
	 * @param Sets the attribute identity code.
	 */
	public void setAttributeIdentity(String attributeIdentity) {
		this.attributeIdentity = attributeIdentity;
	}
	/**
	 * @return Returns the fruit variety value (code).
	 */
	public String getFruitVarietyValue() {
		return fruitVarietyValue;
	}
	/**
	 * @param Sets the fruit variety value (code).
	 */
	public void setFruitVarietyValue(String fruitVarietyValue) {
		this.fruitVarietyValue = fruitVarietyValue;
	}
	/**
	 * @return Returns the fruit variety description.
	 */
	public String getFruitVarietyDescription() {
		return fruitVarietyDescription;
	}
	/**
	 * @param Sets the fruit variety description.
	 */
	public void setFruitVarietyDescription(String fruitVarietyDescription) {
		this.fruitVarietyDescription = fruitVarietyDescription;
	}	
	/**
	 * @return Returns the include/exclude code.
	 */
	public String getIncludeExclude() {
		return includeExclude;
	}
	/**
	 * @param Sets the include/exclude code.
	 */
	public void setIncludeExclude(String includeExclude) {
		this.includeExclude = includeExclude;
	}
	/**
	 * @return Returns the include percentage.
	 */
	public String getIncludePercentage() {
		return includePercentage;
	}
	/**
	 * @param Sets the include percentage.
	 */
	public void setIncludePercentage(String includePercentage) {
		this.includePercentage = includePercentage;
	}
	/**
	 * @return Returns the include minimum percentage.
	 */
	public String getIncludeMinimumPercent() {
		return includeMinimumPercent;
	}
	/**
	 * @param Sets the include minimum percentage.
	 */
	public void setIncludeMinimumPercent(String includeMinimumPercent) {
		this.includeMinimumPercent = includeMinimumPercent;
	}
	/**
	 * @return Returns the include minimum percentage.
	 */
	public String getIncludeMaximumPercent() {
		return includeMaximumPercent;
	}
	/**
	 * @param Sets the include minimum percentage.
	 */
	public void setIncludeMaximumPercent(String includeMaximumPercent) {
		this.includeMaximumPercent = includeMaximumPercent;
	}
	public String getAttributeCropModelDescription() {
		return attributeCropModelDescription;
	}

	public void setAttributeCropModelDescription(
			String attributeCropModelDescription) {
		this.attributeCropModelDescription = attributeCropModelDescription;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public String getDivisionNumber() {
		return divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getRevisionTime() {
		return revisionTime;
	}

	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

}