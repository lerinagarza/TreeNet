/*
 * Created on October 27, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Specification information - currently only CPG
 */
public class SpecificationTestProcess extends SpecificationNEW{

	protected	String		testProcess			= "";
	protected	String		testProcessDescription		= "";
	protected	String		testProcessUOM		= "";
	protected	String		target				= "";
	protected	String		minValue			= "";
	protected	String		maxValue			= "";
	protected	String		method				= "";
	protected	String		methodName			= "";
	protected	String		printSeqNumber		= "";
	protected	String		testValue			= "";
	protected	String		testUOM				= "";

	/**
	 *  // Constructor
	 */
	public SpecificationTestProcess() {
		super();
	}
	/**
	 * @return Returns the maxValue.
	 */
	public String getMaxValue() {
		return maxValue;
	}
	/**
	 * @param maxValue The maxValue to set.
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return Returns the minValue.
	 */
	public String getMinValue() {
		return minValue;
	}
	/**
	 * @param minValue The minValue to set.
	 */
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	/**
	 * @return Returns the printSeqNumber.
	 */
	public String getPrintSeqNumber() {
		return printSeqNumber;
	}
	/**
	 * @param printSeqNumber The printSeqNumber to set.
	 */
	public void setPrintSeqNumber(String printSeqNumber) {
		this.printSeqNumber = printSeqNumber;
	}
	/**
	 * @return Returns the target.
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target The target to set.
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return Returns the testProcess.
	 */
	public String getTestProcess() {
		return testProcess;
	}
	/**
	 * @param testProcess The testProcess to set.
	 */
	public void setTestProcess(String testProcess) {
		this.testProcess = testProcess;
	}
	/**
	 * @return Returns the testUOM.
	 */
	public String getTestUOM() {
		return testUOM;
	}
	/**
	 * @param testUOM The testUOM to set.
	 */
	public void setTestUOM(String testUOM) {
		this.testUOM = testUOM;
	}
	/**
	 * @return Returns the testValue.
	 */
	public String getTestValue() {
		return testValue;
	}
	/**
	 * @param testValue The testValue to set.
	 */
	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}
	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return Returns the testProcessDescription.
	 */
	public String getTestProcessDescription() {
		return testProcessDescription;
	}
	/**
	 * @param testProcessDescription The testProcessDescription to set.
	 */
	public void setTestProcessDescription(String testProcessDescription) {
		this.testProcessDescription = testProcessDescription;
	}
	/**
	 * @return Returns the testProcessUOM.
	 */
	public String getTestProcessUOM() {
		return testProcessUOM;
	}
	/**
	 * @param testProcessUOM The testProcessUOM to set.
	 */
	public void setTestProcessUOM(String testProcessUOM) {
		this.testProcessUOM = testProcessUOM;
	}
}
