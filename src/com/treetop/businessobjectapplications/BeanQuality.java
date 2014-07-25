/*
 * Created on June 29, 2010
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

import com.treetop.businessobjects.*;

/**
 * @author deisen
 *
 * Bean to combine processing results for sending to the servlet. (Quality)
 */
public class BeanQuality {	
		
	protected	QaSpecification specification     = new QaSpecification();	
	protected	QaFormula       formula           = new QaFormula();			
	protected	QaMethod        method            = new QaMethod();
	
	protected	BeanItem		itemInformation	  = new BeanItem();
	
	protected	Vector			revReasonSpecification = new Vector();
	protected	Vector		    revReasonFormula       = new Vector();		
	protected	Vector          revReasonMethod        = new Vector();	
	
	protected	QaSpecificationPackaging 	specPackaging		    = new QaSpecificationPackaging();
	protected	Vector<QaTestParameters>	specAnalyticalTests     = new Vector<QaTestParameters>();
	protected	Vector<QaTestParameters>	specMicroTests		    = new Vector<QaTestParameters>();
	protected	Vector<QaTestParameters>	specProcessParameters   = new Vector<QaTestParameters>();
	protected	Vector<QaTestParameters>	specAdditiveAndPreserve = new Vector<QaTestParameters>();
	
	protected	Vector<ProductStructureMaterial> productStructure 	 = new Vector<ProductStructureMaterial>(); // tw 11/8/11
	
	protected	Vector<QaFormulaDetail>		formulaPreBlend		     = new Vector<QaFormulaDetail>();  //tw 8/16/11
	protected	Vector<QaFormulaDetail>		formulaProduction	     = new Vector<QaFormulaDetail>();  //tw 8/16/11
	protected	Vector<QaFormulaDetail>		formulaPreBlendSauce	 = new Vector<QaFormulaDetail>();  //tw 8/16/11
	protected	Vector<QaTestParameters>	formulaRawFruitTests	 = new Vector<QaTestParameters>();  //tw 8/16/11	
	
	protected	Vector<QaFruitVariety>		varietiesIncluded  		 = new Vector<QaFruitVariety>();
	protected	Vector<QaFruitVariety>		varietiesExcluded  		 = new Vector<QaFruitVariety>();
	
	protected   Vector  		listSpecification = new Vector();
	protected   Vector  		listFormula       = new Vector();
	protected   Vector  	    listMethod        = new Vector();
	
	protected   String          environment       = new String();
	protected   String          statusMessage     = new String();
	
	/**
	 *  // Constructor
	 */
	public BeanQuality() {
		super(); 
	}	
	
	/**
	 * * @return Returns the specification header class.
	 */
	public QaSpecification getSpecification() {
		return specification;
	}
	/**
	 * @param Sets the specification header class.
	 */
	public void setSpecification(QaSpecification specification) {
		this.specification = specification;
	}
	/**
	 * * @return Returns the formula header class.
	 */
	public QaFormula getFormula() {
		return formula;
	}
	/**
	 * @param Sets the formula header class.
	 */
	public void setFormula(QaFormula formula) {
		this.formula = formula;
	}
	/**
	 * * @return Returns the method header class.
	 */
	public QaMethod getMethod() {
		return method;
	}
	/**
	 * @param Sets the method header class.
	 */
	public void setMethod(QaMethod method) {
		this.method = method;
	}
	/**
	 * * @return Returns the specification header with revision information only.
	 */
	public Vector getRevReasonSpec() {
		return revReasonSpecification;
	}
	/**
	 * @param Sets the specification header with revision information only.
	 */
	public void setRevReasonSpecification(Vector revReasonSpecification) {
		this.revReasonSpecification = revReasonSpecification;
	}
	/**
	 * * @return Returns the formula header with revision information only.
	 */
	public Vector getRevReasonFormula() {
		return revReasonFormula;
	}
	/**
	 * @param Sets the formula header with revision information only.
	 */
	public void setRevReasonFormula(Vector revReasonFormula) {
		this.revReasonFormula = revReasonFormula;
	}
	/**
	 * * @return Returns the method header with revision information only.
	 */
	public Vector getRevReasonMethod() {
		return revReasonMethod;
	}
	/**
	 * @param Sets the formula method with revision information only.
	 */
	public void setRevReasonMethod(Vector revReasonMethod) {
		this.revReasonMethod = revReasonMethod;
	}
	/**
	 * * @return Returns the specification analytical tests.
	 */
	public Vector getSpecAnalyticalTests() {
		return specAnalyticalTests;
	}
	/**
	 * * @return Returns the specification process parameters.
	 */
	public Vector getSpecProcessParameters() {
		return specProcessParameters;
	}
	/**
	 * * @return Returns the vector of specification header or detail.
	 */
	public Vector getListSpecification() {
		return listSpecification;
	}
	/**
	 * @param Sets the vector of specification header or detail.
	 */
	public void setListSpecification(Vector listSpecification) {
		this.listSpecification = listSpecification;
	}
	/**
	 * * @return Returns the vector of formula header or detail.
	 */
	public Vector getListFormula() {
		return listFormula;
	}
	/**
	 * @param Sets the vector of formula header or detail.
	 */
	public void setListFormula(Vector listFormula) {
		this.listFormula = listFormula;
	}
	/**
	 * * @return Returns the vector of method header or detail.
	 */
	public Vector getListMethod() {
		return listMethod;
	}
	/**
	 * @param Sets the vector of method header or detail.
	 */
	public void setListMethod(Vector listMethod) {
		this.listMethod = listMethod;
	}
	/**
	 * @return Returns the environment code. (library)
	 */
	public String getEnvironment() {
		return environment;
	}
	/**
	 * @param Sets the environment code. (library)
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	/**
	 * @return Returns the status message.
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param Sets the status message.
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Vector getRevReasonSpecification() {
		return revReasonSpecification;
	}

	public Vector getSpecMicroTests() {
		return specMicroTests;
	}

	public Vector getFormulaPreBlend() {
		return formulaPreBlend;
	}

	public Vector getFormulaPreBlendSauce() {
		return formulaPreBlendSauce;
	}

	public Vector getFormulaProduction() {
		return formulaProduction;
	}

	public Vector getVarietiesExcluded() {
		return varietiesExcluded;
	}

	public Vector getVarietiesIncluded() {
		return varietiesIncluded;
	}

	public Vector<QaTestParameters> getFormulaRawFruitTests() {
		return formulaRawFruitTests;
	}

	public void setFormulaRawFruitTests(
			Vector<QaTestParameters> formulaRawFruitTests) {
		this.formulaRawFruitTests = formulaRawFruitTests;
	}

	public void setFormulaPreBlend(Vector<QaFormulaDetail> formulaPreBlend) {
		this.formulaPreBlend = formulaPreBlend;
	}

	public void setFormulaPreBlendSauce(Vector<QaFormulaDetail> formulaPreBlendSauce) {
		this.formulaPreBlendSauce = formulaPreBlendSauce;
	}

	public void setFormulaProduction(Vector<QaFormulaDetail> formulaProduction) {
		this.formulaProduction = formulaProduction;
	}

	public void setVarietiesExcluded(Vector<QaFruitVariety> varietiesExcluded) {
		this.varietiesExcluded = varietiesExcluded;
	}

	public void setVarietiesIncluded(Vector<QaFruitVariety> varietiesIncluded) {
		this.varietiesIncluded = varietiesIncluded;
	}

	public Vector<ProductStructureMaterial> getProductStructure() {
		return productStructure;
	}

	public void setProductStructure(
			Vector<ProductStructureMaterial> productStructure) {
		this.productStructure = productStructure;
	}

	public QaSpecificationPackaging getSpecPackaging() {
		return specPackaging;
	}

	public void setSpecPackaging(QaSpecificationPackaging specPackaging) {
		this.specPackaging = specPackaging;
	}

	public void setSpecAnalyticalTests(Vector<QaTestParameters> specAnalyticalTests) {
		this.specAnalyticalTests = specAnalyticalTests;
	}

	public void setSpecMicroTests(Vector<QaTestParameters> specMicroTests) {
		this.specMicroTests = specMicroTests;
	}

	public void setSpecProcessParameters(
			Vector<QaTestParameters> specProcessParameters) {
		this.specProcessParameters = specProcessParameters;
	}

	public Vector<QaTestParameters> getSpecAdditiveAndPreserve() {
		return specAdditiveAndPreserve;
	}

	public void setSpecAdditiveAndPreserve(
			Vector<QaTestParameters> specAdditiveAndPreserve) {
		this.specAdditiveAndPreserve = specAdditiveAndPreserve;
	}

	public BeanItem getItemInformation() {
		return itemInformation;
	}

	public void setItemInformation(BeanItem itemInformation) {
		this.itemInformation = itemInformation;
	}

}
