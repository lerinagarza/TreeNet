package com.treetop.businessobjects;

import java.math.BigDecimal;
import java.util.Vector;

/**
 * 
 * @author thaile
 *
 */


public class BlendSheet {
	
	
	private	BigDecimal					volumeCases				= BigDecimal.ZERO;
	private	BigDecimal					volumeGallons			= BigDecimal.ZERO;
	private	BigDecimal					runCount				= BigDecimal.ZERO;
	private	Vector<BlendSheetMaterial>	materials				= new Vector<BlendSheetMaterial>();		
	
	

	public BigDecimal getVolumeCases() {
		return volumeCases;
	}

	public void setVolumeCases(BigDecimal volumeCases) {
		this.volumeCases = volumeCases;
	}

	public BigDecimal getVolumeGallons() {
		return volumeGallons;
	}

	public void setVolumeGallons(BigDecimal volumeGallons) {
		this.volumeGallons = volumeGallons;
	}

	public Vector<BlendSheetMaterial> getMaterials() {
		return materials;
	}

	public void setMaterials(Vector<BlendSheetMaterial> materials) {
		this.materials = materials;
	}

	public BigDecimal getRunCount() {
		return runCount;
	}

	public void setRunCount(BigDecimal runCount) {
		this.runCount = runCount;
	}


}
