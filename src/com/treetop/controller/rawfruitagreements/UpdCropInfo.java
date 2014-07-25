package com.treetop.controller.rawfruitagreements;



import com.treetop.controller.BaseViewBeanR4;
import com.treetop.services.ServiceRawFruitAgreement;
import com.treetop.utilities.html.HtmlOption;

import javax.servlet.http.HttpServletRequest;
import java.util.Vector;

public class UpdCropInfo extends BaseViewBeanR4{

    private String sequence = "";
	private String crop = "";
	private String type = "";
	private String run = "";
	private String category = "";
	private String variety = "";
	private String varietyMisc = "";
	
	
	private String bins = "";
	private String binType = "";
	
	private String paymentType = "";
	private String juicePrice = "";
	private String jpPrice = "";
	private String peelerPrice = "";
	private String premiumPrice = "";
	private String freshSlicePrice = "";
	private String price = "";

    private Vector<HtmlOption> dropDownCrop = new Vector<HtmlOption>();
    private Vector<HtmlOption> dropDownType = new Vector<HtmlOption>();
    private Vector<HtmlOption> dropDownCategory = new Vector<HtmlOption>();
    private Vector<HtmlOption> dropDownVariety = new Vector<HtmlOption>();
    private Vector<HtmlOption> dropDownBinType = new Vector<HtmlOption>();
    private Vector<HtmlOption> dropDownPaymentType = new Vector<HtmlOption>();


    public UpdCropInfo(HttpServletRequest request){
        populate(request);
    }

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

    public void buildDropDowns() {

        this.setDropDownCrop(ServiceRawFruitAgreement.buildDropDownCrop());
        this.setDropDownType(ServiceRawFruitAgreement.buildDropDownType());
        this.setDropDownCategory(ServiceRawFruitAgreement.buildDropDownCategory());
        this.setDropDownVariety(ServiceRawFruitAgreement.buildDropDownVariety());
        this.setDropDownBinType(ServiceRawFruitAgreement.buildDropDownBinType());
        this.setDropDownPaymentType(ServiceRawFruitAgreement.buildDropDownPaymentType());


    }

	public String getCrop() {
		return crop;
	}

	public void setCrop(String crop) {
		this.crop = crop;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public String getVarietyMisc() {
		return varietyMisc;
	}

	public void setVarietyMisc(String varietyMisc) {
		this.varietyMisc = varietyMisc;
	}

	public String getBins() {
		return bins;
	}

	public void setBins(String bins) {
		this.bins = bins;
	}

	public String getBinType() {
		return binType;
	}

	public void setBinType(String binType) {
		this.binType = binType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getJuicePrice() {
		return juicePrice;
	}

	public void setJuicePrice(String juicePrice) {
		this.juicePrice = juicePrice;
	}

	public String getJpPrice() {
		return jpPrice;
	}

	public void setJpPrice(String jpPrice) {
		this.jpPrice = jpPrice;
	}

	public String getPeelerPrice() {
		return peelerPrice;
	}

	public void setPeelerPrice(String peelerPrice) {
		this.peelerPrice = peelerPrice;
	}

	public String getPremiumPrice() {
		return premiumPrice;
	}

	public void setPremiumPrice(String premiumPrice) {
		this.premiumPrice = premiumPrice;
	}

	public String getFreshSlicePrice() {
		return freshSlicePrice;
	}

	public void setFreshSlicePrice(String freshSlicePrice) {
		this.freshSlicePrice = freshSlicePrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Vector<HtmlOption> getDropDownCrop() {
        return dropDownCrop;
    }

    public void setDropDownCrop(Vector<HtmlOption> dropDownCrop) {
        this.dropDownCrop = dropDownCrop;
    }

    public Vector<HtmlOption> getDropDownType() {
        return dropDownType;
    }

    public void setDropDownType(Vector<HtmlOption> dropDownType) {
        this.dropDownType = dropDownType;
    }

    public Vector<HtmlOption> getDropDownCategory() {
        return dropDownCategory;
    }

    public void setDropDownCategory(Vector<HtmlOption> dropDownCategory) {
        this.dropDownCategory = dropDownCategory;
    }

    public Vector<HtmlOption> getDropDownVariety() {
        return dropDownVariety;
    }

    public void setDropDownVariety(Vector<HtmlOption> dropDownVariety) {
        this.dropDownVariety = dropDownVariety;
    }

    public Vector<HtmlOption> getDropDownBinType() {
        return dropDownBinType;
    }

    public void setDropDownBinType(Vector<HtmlOption> dropDownBinType) {
        this.dropDownBinType = dropDownBinType;
    }

    public Vector<HtmlOption> getDropDownPaymentType() {
        return dropDownPaymentType;
    }

    public void setDropDownPaymentType(Vector<HtmlOption> dropDownPaymentType) {
        this.dropDownPaymentType = dropDownPaymentType;
    }
}
