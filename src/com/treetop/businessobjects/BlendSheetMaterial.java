package com.treetop.businessobjects;

import java.math.BigDecimal;

/**
 * 
 * @author thaile
 *
 */

public class BlendSheetMaterial {
	
	private	BigDecimal	quantity				= BigDecimal.ZERO;
	private	String		quantityUom				= "";
	private	Item		materialItem			= new Item();
	private	BigDecimal	materialWasteFactor		= BigDecimal.ZERO;
	
	
	
	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUom() {
		return quantityUom;
	}

	public void setQuantityUom(String quantityUom) {
		this.quantityUom = quantityUom;
	}

	public Item getMaterialItem() {
		return materialItem;
	}

	public void setMaterialItem(Item materialItem) {
		this.materialItem = materialItem;
	}

	public BigDecimal getMaterialWasteFactor() {
		return materialWasteFactor;
	}
	
	public void setMaterialWasteFactor(BigDecimal materialWasteFactor) {
		this.materialWasteFactor = materialWasteFactor;
	}

}
