/*
 * Created on Apr 4, 2006
 *
 */
package com.treetop.businessobjects;

/**
 * @author thaile
 *
 * Production and Forecast Model Information
 */
public class Model {
	
	private String	modelType = "";
	private String	modelName = "";
	private	String	task = "";
	private String	resource = "";

	/**
	 * @return Returns the modelName.
	 */
	public String getModelName() {
		return modelName;
	}
	/**
	 * @param modelName The modelName to set.
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	/**
	 * @return Returns the modelType.
	 */
	public String getModelType() {
		return modelType;
	}
	/**
	 * @param modelType The modelType to set.
	 */
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	/**
	 * @return Returns the resource.
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource The resource to set.
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return Returns the task.
	 */
	public String getTask() {
		return task;
	}
	/**
	 * @param task The task to set.
	 */
	public void setTask(String task) {
		this.task = task;
	}
}
