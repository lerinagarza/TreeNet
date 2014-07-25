package com.treetop.businessobjects;

import java.math.BigDecimal;

public class RawFruitAgreementLine {

    private int id = 0;
    private int sequence = 0;
	private String crop = "";
	private String type = "";
	private String run = "";
	private String category = "";
	private String variety = "";
	private String varietyMisc = "";
	
	
	private BigDecimal bins = BigDecimal.ZERO;
	private String binType = "";
	
	private String paymentType = "";
	private BigDecimal juicePrice = BigDecimal.ZERO;
	private BigDecimal jpPrice = BigDecimal.ZERO;
	private BigDecimal peelerPrice = BigDecimal.ZERO;
	private BigDecimal premiumPrice = BigDecimal.ZERO;
	private BigDecimal freshSlicePrice = BigDecimal.ZERO;
	private BigDecimal price = BigDecimal.ZERO;
	
	
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
	public BigDecimal getBins() {
		return bins;
	}
	public void setBins(BigDecimal bins) {
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
	public BigDecimal getJuicePrice() {
		return juicePrice;
	}
	public void setJuicePrice(BigDecimal juicePrice) {
		this.juicePrice = juicePrice;
	}
	public BigDecimal getJpPrice() {
		return jpPrice;
	}
	public void setJpPrice(BigDecimal jpPrice) {
		this.jpPrice = jpPrice;
	}
	public BigDecimal getPeelerPrice() {
		return peelerPrice;
	}
	public void setPeelerPrice(BigDecimal peelerPrice) {
		this.peelerPrice = peelerPrice;
	}
	public BigDecimal getPremiumPrice() {
		return premiumPrice;
	}
	public void setPremiumPrice(BigDecimal premiumPrice) {
		this.premiumPrice = premiumPrice;
	}
	public BigDecimal getFreshSlicePrice() {
		return freshSlicePrice;
	}
	public void setFreshSlicePrice(BigDecimal freshSlicePrice) {
		this.freshSlicePrice = freshSlicePrice;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getKey() {
		return this.getCrop().trim().toLowerCase() + "-" + 
			this.getType().trim().toLowerCase() + "-" + 
			this.getRun().trim().toLowerCase() + "-" +
			this.getCategory().trim().toLowerCase() + "-" +
			this.getVariety().trim().toLowerCase() + "-" +
			this.getVarietyMisc().trim().toLowerCase();
		
	}
	
	@Override
	public String toString() {
		return this.getKey();
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
