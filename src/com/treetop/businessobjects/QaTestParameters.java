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
 * Contains analyical test and process parameter information - these are stored in the "attributes of M3".
 * 	
 */
public class QaTestParameters{
								// from File 	dbprd/QAPQPROC
								//              dbprd/QAPKMEHD
	                            //           m3djdprd/MATRMA 
	protected	String			companyNumber			= "";	//TPCONO
	protected	String			divisionNumber			= "";	//TPDIVI
	protected	String			applicationType			= "";	//TPATYP
	   // use the idNumber as Either the Spec Number or the Formula Number
	   // which one would be determined by the typeCode
	protected	String			idNumber				= "";   //TPSPNO
	protected	String			revisionDate			= "";	//TPRDTE
	protected	String			revisionTime			= "";	//TPRTME
	
	protected	String			identificationCode		= "";	//TPCODE
	protected	String			attributeGroup			= "";	//TPATGR
	protected	String			attributeIdentity		= "";	//TPATID 
	protected	String			attributeDescription	= "";	//AATX30
	protected	String			attributeType			= "";	//AAATVC
	protected	String			sequenceNumber			= "";	//TPSEQ#
	protected	String			unitOfMeasure			= "";	//TPUOFM
	protected   String			decimalPlaces			= "";   //AADCCD
	protected	String			targetValue				= "";	//TPTARG
	protected	String			minimumStandard			= "";	//TPMNST
	protected	String			maximumStandard			= "";	//TPMXST
	protected	String			testedAtValue			= "";	//TPTVAL
	protected	String			testedAtUnitOfMeasure	= "";	//TPTUOM
	protected	String			defaultOnCOA			= "";	//TPPCOA
	protected	String			methodNumber			= "";	//TPMENO
	protected	String			methodName				= "";	//MHNAME  
	protected	String			methodRevDate			= "";	//TPMEDT
	protected	String			methodRevTime			= "";	//TPMETM
	protected	String			methodDescription		= "";  
	protected	String			updatedDate				= "";	//TPUDTE
	protected	String			updatedTime				= "";	//TPUTME
	protected	String			updatedUser				= "";	//TPUUSR
	protected	String			createdDate				= "";	//TPCDTE
	protected	String			createdTime				= "";	//TPCTME
	protected	String			createdUser				= "";	//TPCUSR
		
	/**
	 *  // Constructor
	 */
	public QaTestParameters() {
		super();
	}
	/**
	 * @return Returns the record identification type code.
	 */
	public String getIdentificationCode() {
		return identificationCode;
	}
	/**
	 * @param Sets the record identification type code.
	 */
	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}
	/**
	 * @return Returns the attribute grouping code.
	 */
	public String getAttributeGroup() {
		return attributeGroup;
	}
	/**
	 * @param Sets the attribute grouping code.
	 */
	public void setAttributeGroup(String attributeGroup) {
		this.attributeGroup = attributeGroup;
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
	 * @return Returns the attribute description.
	 */
	public String getAttributeDescription() {
		return attributeDescription;
	}
	/**
	 * @param Sets the attribute description.
	 */
	public void setAttributeDescription(String attributeDescription) {
		this.attributeDescription = attributeDescription;
	}
	/**
	 * @return Returns the sequence number (creates unique record).
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param Sets the sequence number (creates unique record).
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the attribute unit of measure.
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	/**
	 * @param Sets the attribute unit of measure.
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	/**
	 * @return Returns the attribute target value.
	 */
	public String getTargetValue() {
		return targetValue;
	}
	/**
	 * @param Sets the attribute target value.
	 */
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}
	/**
	 * @return Returns the attribute minimum standard value.
	 */
	public String getMinimumStandard() {
		return minimumStandard;
	}
	/**
	 * @param Sets the attribute minimum standard value.
	 */
	public void setMinimumStandard(String minimumStandard) {
		this.minimumStandard = minimumStandard;
	}
	/**
	 * @return Returns the attribute maximum standard value.
	 */
	public String getMaximumStandard() {
		return maximumStandard;
	}
	/**
	 * @param Sets the attribute maximum standard value.
	 */
	public void setMaximumStandard(String maximumStandard) {
		this.maximumStandard = maximumStandard;
	}
	/**
	 * @return Returns the analytical tested at value.
	 */
	public String getTestedAtValue() {
		return testedAtValue;
	}
	/**
	 * @param Sets the analytical tested at value.
	 */
	public void setTestedAtValue(String testedAtValue) {
		this.testedAtValue = testedAtValue;
	}
	/**
	 * @return Returns the analytical tested at value unit of measure.
	 */
	public String getTestedAtUnitOfMeasure() {
		return testedAtUnitOfMeasure;
	}
	/**
	 * @param Sets the analytical tested at value unit of measure.
	 */
	public void setTestedAtUnitOfMeasure(String testedAtUnitOfMeasure) {
		this.testedAtUnitOfMeasure = testedAtUnitOfMeasure;
	}
	/**
	 * @return Returns the method number.
	 */
	public String getMethodNumber() {
		return methodNumber;
	}
	/**
	 * @param Sets the method number.
	 */
	public void setMethodNumber(String methodNumber) {
		this.methodNumber = methodNumber;
	}
	/**
	 * @return Returns the method revision date.
	 */
	public String getMethodRevDate() {
		return methodRevDate;
	}
	/**
	 * @param Sets the method revision date.
	 */
	public void setMethodRevDate(String methodRevDate) {
		this.methodRevDate = methodRevDate;
	}
	/**
	 * @return Returns the method revision time.
	 */
	public String getMethodRevTime() {
		return methodRevTime;
	}
	/**
	 * @param Sets the method revision time.
	 */
	public void setMethodRevTime(String methodRevTime) {
		this.methodRevTime = methodRevTime;
	}
	public String getMethodDescription() {
		return methodDescription;
	}
	public void setMethodDescription(String methodDescription) {
		this.methodDescription = methodDescription;
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
	public String getDecimalPlaces() {
		return decimalPlaces;
	}
	public void setDecimalPlaces(String decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}
	public String getDefaultOnCOA() {
		return defaultOnCOA;
	}
	public void setDefaultOnCOA(String defaultOnCOA) {
		this.defaultOnCOA = defaultOnCOA;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}