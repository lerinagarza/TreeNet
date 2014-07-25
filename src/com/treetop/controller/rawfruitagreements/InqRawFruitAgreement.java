package com.treetop.controller.rawfruitagreements;

import javax.servlet.http.HttpServletRequest;

import com.treetop.businessobjects.RawFruitAgreement;
import com.treetop.businessobjects.RawFruitAgreementLine;
import com.treetop.controller.BaseViewBeanR4;
import com.treetop.controller.UrlPathMapping;


@UrlPathMapping({"requestType","writeUpNumber","detailSequence"})
public class InqRawFruitAgreement extends BaseViewBeanR4{

    private int id = 0;
	private String writeUpNumber = "";
	private RawFruitAgreement agreement = null;
	private RawFruitAgreementLine lineAgreement = null;
	

    public InqRawFruitAgreement(){}

	public InqRawFruitAgreement(HttpServletRequest request) {
		populate(request);
	}

	@Override
	public void validate() {
		
		
	}

	public String getWriteUpNumber() {
		return writeUpNumber;
	}

	public void setWriteUpNumber(String writeUpNumber) {
		this.writeUpNumber = writeUpNumber;
	}

	public RawFruitAgreement getAgreement() {
		return agreement;
	}

	public void setAgreement(RawFruitAgreement agreement) {
		this.agreement = agreement;
	}

	public RawFruitAgreementLine getLineAgreement() {
		return lineAgreement;
	}

	public void setLineAgreement(RawFruitAgreementLine lineAgreement) {
		this.lineAgreement = lineAgreement;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}