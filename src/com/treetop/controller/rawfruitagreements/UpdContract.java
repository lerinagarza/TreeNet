package com.treetop.controller.rawfruitagreements;

import javax.servlet.http.HttpServletRequest;

import com.treetop.controller.BaseViewBeanR4;
import com.treetop.services.ServiceRawFruitAgreement;
import com.treetop.utilities.html.HtmlOption;

import java.util.Vector;

public class UpdContract extends BaseViewBeanR4 {

    private String writeUpNumber = "";
	private String supplierNumber = "";
	private String entryDate = "";
	private String revisionDate = "";
	private String cropYear = "";
    private String fieldRep = "";

    private Vector<HtmlOption> dropDownCropYear = new Vector<HtmlOption>();
	
	public UpdContract(HttpServletRequest request) {
		populate(request);
	}


    public void buildDropDowns() {
        this.setDropDownCropYear(ServiceRawFruitAgreement.buildDropDownCropYear());
    }

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}


	public String getSupplierNumber() {
		return supplierNumber;
	}


	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}


	public String getEntryDate() {
		return entryDate;
	}


	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}


	public String getRevisionDate() {
		return revisionDate;
	}


	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}


	public String getCropYear() {
		return cropYear;
	}


	public void setCropYear(String cropYear) {
		this.cropYear = cropYear;
	}


    public Vector<HtmlOption> getDropDownCropYear() {
        return dropDownCropYear;
    }

    public void setDropDownCropYear(Vector<HtmlOption> dropDownCropYear) {
        this.dropDownCropYear = dropDownCropYear;
    }

    public String getWriteUpNumber() {
        return writeUpNumber;
    }

    public void setWriteUpNumber(String writeUpNumber) {
        this.writeUpNumber = writeUpNumber;
    }

    public String getFieldRep() {
        return fieldRep;
    }

    public void setFieldRep(String fieldRep) {
        this.fieldRep = fieldRep;
    }
}
