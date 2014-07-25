package com.treetop.controller.thermal;

import com.treetop.controller.BaseViewBeanR4;
import com.treetop.controller.UrlPathMapping;

import java.math.*;
import java.util.Vector;

@UrlPathMapping("requestType")
public class InqThermal extends BaseViewBeanR4 {

	private String reductionValue = "";
	private String reductionValueErrorMessage = "";
	private boolean reductionValueHighlight = false;
	private String deeValue = "";
	private String deeValueErrorMessage = "";
	private boolean deeValueHighlight = false;
	private String zeeValue = "";
	private String zeeValueErrorMessage = "";
	private boolean zeeValueHighlight = false;
	private String secondsValue = "";
	private String secondsValueErrorMessage = "";
	private boolean secondsValueHighlight = false;
	private String tempValue = "";
	private String tempValueErrorMessage = "";
	private String temperatureCount = "";
	private String temperatureCountErrorMessage = "";
	private boolean temperatureValueHighlight = false;
	private String submit = "";
	private String temperature1 = "";
	private String temperature1ErrorMessage = "";
	private String poValue = "";
	private String logReduction = "";
	private String temperature2 = "";
	private String temperature2ErrorMessage = "";
	private String temperature3 = "";
	private String temperature3ErrorMessage = "";
	private String temperature4 = "";
	private String temperature4ErrorMessage = "";
	private String minTempValue = "";
	private String minTempValueErrorMessage = "";
	private String maxTempValue = "";
	private String maxTempValueErrorMessage = "";
	private Vector<BigDecimal> logReductionValues = new Vector<BigDecimal>();
	private String p1UnitType = "C";
	private String cTempValue = "";
	private String fTempValue = "";
	
	
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Validate P1 Discrete Process Calculation
	 * 
	 */
	public void validateP1() {
		
		int nonZeroCount = 0;
		int zeroCount    = 0;
	
		// all calculation fields must be numeric
		try {//reduction value	
			
			if (this.getReductionValue().trim().equals("")) 
				this.setReductionValue("0");
			
			BigDecimal bd = new BigDecimal(this.getReductionValue());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x == 0) {
				zeroCount++;
				this.setReductionValueHighlight(true);
				this.setReductionValue("0");
			}
			else {
				nonZeroCount++;
				this.setReductionValueHighlight(false);
			}
		} catch (Exception e) {
			this.setReductionValueErrorMessage("Value " + this.getReductionValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//zee value

			if (this.getZeeValue().trim().equals("")) 
				this.setZeeValue("0");
			
			BigDecimal bd = new BigDecimal(this.getZeeValue());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x == 0) {
				zeroCount++;
				this.setZeeValueHighlight(true);
				this.setZeeValue("0");
			}
			else {
				nonZeroCount++;
				this.setZeeValueHighlight(false);
			}
			
		} catch (Exception e) {
			this.setZeeValueErrorMessage("Value " + this.getZeeValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
			
		try {//dee value
			
			if (this.getDeeValue().trim().equals("")) 
				this.setDeeValue("0");
			
			BigDecimal bd = new BigDecimal(this.getDeeValue());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x == 0) {
				zeroCount++;
				this.setDeeValueHighlight(true);
				this.setDeeValue("0");
			}
			else {
				nonZeroCount++;
				this.setDeeValueHighlight(false);
			}
			
		} catch (Exception e) {
			this.setDeeValueErrorMessage("Value " + this.getDeeValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//seconds value

			if (this.getSecondsValue().trim().equals("")) 
				this.setSecondsValue("0");
			
			BigDecimal bd = new BigDecimal(this.getSecondsValue());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x == 0) {
				zeroCount++;
				this.setSecondsValueHighlight(true);
				this.setSecondsValue("0");
			}
			else {
				nonZeroCount++;
				this.setSecondsValueHighlight(false);
			}
			
		} catch (Exception e) {
			this.setSecondsValueErrorMessage("Value " + this.getSecondsValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//temp value
			
			if (this.getTempValue().trim().equals("")) 
				this.setTempValue("0");
			
			BigDecimal bd = new BigDecimal(this.getTempValue());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x == 0) {
				zeroCount++;
				this.setTemperatureValueHighlight(true);
				this.setTempValue("0");
			}
			else {
				nonZeroCount++;
				this.setTemperatureValueHighlight(false);
			}
			
		} catch (Exception e) {
			this.setTempValueErrorMessage("Value " + this.getTempValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
			
		// four fields must contain a non zero value
		// and one field must be zero.
		if (this.getErrorMessage().equals(""))
		{
			if (zeroCount != 1 || nonZeroCount != 4)
			{
				this.setErrorMessage("To Calculate: Leave one field empty. ");
				this.setReductionValueErrorMessage("*");
				this.setDeeValueErrorMessage("*");
				this.setZeeValueErrorMessage("*");
				this.setSecondsValueErrorMessage("*");
				this.setTempValueErrorMessage("*");
			}
			
		}
		
		// input field tempValue can be celsius or fahrenheit.
		// pass celsius to service. determine both.
		BigDecimal bd = new BigDecimal(this.getTempValue());
		
		if (!bd.toString().trim().equals("0")) {
			
			if (this.getP1UnitType().equals("C")) {
				this.setcTempValue(this.getTempValue());
				double c = new Double(bd.toString());
				c = c * 9;
				c = c / 5;
				c = c + 32;
				this.setfTempValue(new BigDecimal(Double.toString(c)).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			} else {
				this.setfTempValue(this.getTempValue());
				double f = new Double(bd.toString());
				f = f - 32;
				f = f * 5;
				f = f / 9;
				this.setcTempValue(new BigDecimal(Double.toString(f)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				this.setTempValue(this.getcTempValue());
			}
		}
	
		
		//Highlight the calculated field.
		if (!this.getErrorMessage().trim().equals("")) {
			this.setDeeValueHighlight(false);
			this.setZeeValueHighlight(false);
			this.setSecondsValueHighlight(false);
			this.setTemperatureValueHighlight(false);
			this.setReductionValueHighlight(false);
		}
		
	}
	
	
	/**
	 * Validate P1 Discrete Process Calculation
	 * 
	 */
	public void validateP2() {
		
		// all calculation fields must be numeric
		try {//holding time in seconds

			if (this.getSecondsValue().trim().equals("")) 
				this.setSecondsValue("0");
			
			BigDecimal bd = new BigDecimal(this.getSecondsValue());
			int x = bd.compareTo(new BigDecimal("0"));
			if (x == 0)
			{
				this.setSecondsValueErrorMessage("Seconds value must not be zero. ");
				this.setErrorMessage("Input field problem. ");
			}
		} catch (Exception e) {
			this.setSecondsValueErrorMessage("Value " + this.getSecondsValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//d-value of organism	

			if (this.getDeeValue().trim().equals("")) 
				this.setDeeValue("0");
			
			BigDecimal bd = new BigDecimal(this.getDeeValue());
			int x = bd.compareTo(new BigDecimal("0"));
			if (x == 0)
			{
				this.setDeeValueErrorMessage("D-Value must not be zero. ");
				this.setErrorMessage("Input field problem. ");
			}
		} catch (Exception e) {
			this.setDeeValueErrorMessage("Value " + this.getDeeValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//z-value of organism	

			if (this.getZeeValue().trim().equals("")) 
				this.setZeeValue("0");
			
			BigDecimal bd = new BigDecimal(this.getZeeValue());
			int x = bd.compareTo(new BigDecimal("0"));
			if (x == 0)
			{
				this.setZeeValueErrorMessage("Z-Value must not be zero. ");
				this.setErrorMessage("Input field problem. ");
			}
		} catch (Exception e) {
			this.setZeeValueErrorMessage("Value " + this.getZeeValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		//verify at least one temperature value is not zero.
		String temperaturesAllZeros = "yes";
		
		try {//temperature 1

			if (this.getTemperature1().trim().equals("")) 
				this.setTemperature1("0");
			
			BigDecimal bd = new BigDecimal(this.getTemperature1());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x != 0)
				temperaturesAllZeros = "no";
				
		} catch (Exception e) {
			this.setTemperature1ErrorMessage("Value " + this.getTemperature1() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//temperature 2

			if (this.getTemperature2().trim().equals("")) 
				this.setTemperature2("0");
			
			BigDecimal bd = new BigDecimal(this.getTemperature2());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x != 0)
				temperaturesAllZeros = "no";
				
		} catch (Exception e) {
			this.setTemperature2ErrorMessage("Value " + this.getTemperature2() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//temperature 3

			if (this.getTemperature3().trim().equals("")) 
				this.setTemperature3("0");
			
			BigDecimal bd = new BigDecimal(this.getTemperature3());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x != 0)
				temperaturesAllZeros = "no";
				
		} catch (Exception e) {
			this.setTemperature3ErrorMessage("Value " + this.getTemperature3() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//temperature 4

			if (this.getTemperature4().trim().equals("")) 
				this.setTemperature4("0");
			
			BigDecimal bd = new BigDecimal(this.getTemperature4());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x != 0)
				temperaturesAllZeros = "no";
				
		} catch (Exception e) {
			this.setTemperature4ErrorMessage("Value " + this.getTemperature4() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		if (this.getErrorMessage().trim().equals("")){
			
			if (temperaturesAllZeros.equals("yes")) {
				this.setErrorMessage("Enter a temperature reading. ");
				this.setTemperature1ErrorMessage("*");
				this.setTemperature2ErrorMessage("*");
				this.setTemperature3ErrorMessage("*");
				this.setTemperature4ErrorMessage("*");
			}
		}
	}


	public String getReductionValue() {
		return reductionValue;
	}


	public void setReductionValue(String reductionValue) {
		this.reductionValue = reductionValue;
	}


	public String getReductionValueErrorMessage() {
		return reductionValueErrorMessage;
	}


	public void setReductionValueErrorMessage(String reductionValueErrorMessage) {
		this.reductionValueErrorMessage = reductionValueErrorMessage;
	}


	public String getDeeValue() {
		return deeValue;
	}


	public void setDeeValue(String deeValue) {
		this.deeValue = deeValue;
	}


	public String getDeeValueErrorMessage() {
		return deeValueErrorMessage;
	}


	public void setDeeValueErrorMessage(String deeValueErrorMessage) {
		this.deeValueErrorMessage = deeValueErrorMessage;
	}


	public String getZeeValue() {
		return zeeValue;
	}


	public void setZeeValue(String zeeValue) {
		this.zeeValue = zeeValue;
	}


	public String getZeeValueErrorMessage() {
		return zeeValueErrorMessage;
	}


	public void setZeeValueErrorMessage(String zeeValueErrorMessage) {
		this.zeeValueErrorMessage = zeeValueErrorMessage;
	}


	public String getSecondsValue() {
		return secondsValue;
	}


	public void setSecondsValue(String secondsValue) {
		this.secondsValue = secondsValue;
	}


	public String getSecondsValueErrorMessage() {
		return secondsValueErrorMessage;
	}


	public void setSecondsValueErrorMessage(String secondsValueErrorMessage) {
		this.secondsValueErrorMessage = secondsValueErrorMessage;
	}


	public String getTempValue() {
		return tempValue;
	}


	public void setTempValue(String tempValue) {
		this.tempValue = tempValue;
	}


	public String getTempValueErrorMessage() {
		return tempValueErrorMessage;
	}


	public void setTempValueErrorMessage(String tempValueErrorMessage) {
		this.tempValueErrorMessage = tempValueErrorMessage;
	}

	public String getTemperatureCount() {
		return temperatureCount;
	}

	public void setTemperatureCount(String temperatureCount) {
		this.temperatureCount = temperatureCount;
	}

	public String getTemperatureCountErrorMessage() {
		return temperatureCountErrorMessage;
	}

	public void setTemperatureCountErrorMessage(String temperatureCountErrorMessage) {
		this.temperatureCountErrorMessage = temperatureCountErrorMessage;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getTemperature1() {
		return temperature1;
	}

	public void setTemperature1(String temperature1) {
		this.temperature1 = temperature1;
	}

	public String getTemperature1ErrorMessage() {
		return temperature1ErrorMessage;
	}

	public void setTemperature1ErrorMessage(String temperature1ErrorMessage) {
		this.temperature1ErrorMessage = temperature1ErrorMessage;
	}

	public String getTemperature2() {
		return temperature2;
	}

	public void setTemperature2(String temperature2) {
		this.temperature2 = temperature2;
	}

	public String getTemperature2ErrorMessage() {
		return temperature2ErrorMessage;
	}

	public void setTemperature2ErrorMessage(String temperature2ErrorMessage) {
		this.temperature2ErrorMessage = temperature2ErrorMessage;
	}

	public String getTemperature3() {
		return temperature3;
	}

	public void setTemperature3(String temperature3) {
		this.temperature3 = temperature3;
	}

	public String getTemperature3ErrorMessage() {
		return temperature3ErrorMessage;
	}

	public void setTemperature3ErrorMessage(String temperature3ErrorMessage) {
		this.temperature3ErrorMessage = temperature3ErrorMessage;
	}

	public String getTemperature4() {
		return temperature4;
	}

	public void setTemperature4(String temperature4) {
		this.temperature4 = temperature4;
	}

	public String getTemperature4ErrorMessage() {
		return temperature4ErrorMessage;
	}

	public void setTemperature4ErrorMessage(String temperature4ErrorMessage) {
		this.temperature4ErrorMessage = temperature4ErrorMessage;
	}

	public String getLogReduction() {
		return logReduction;
	}

	public void setLogReduction(String logReduction) {
		this.logReduction = logReduction;
	}

	public String getPoValue() {
		return poValue;
	}

	public void setPoValue(String poValue) {
		this.poValue = poValue;
	}

	public String getMinTempValue() {
		return minTempValue;
	}

	public void setMinTempValue(String minTempValue) {
		this.minTempValue = minTempValue;
	}

	public String getMinTempValueErrorMessage() {
		return minTempValueErrorMessage;
	}

	public void setMinTempValueErrorMessage(String minTempValueErrorMessage) {
		this.minTempValueErrorMessage = minTempValueErrorMessage;
	}

	public String getMaxTempValue() {
		return maxTempValue;
	}

	public void setMaxTempValue(String maxTempValue) {
		this.maxTempValue = maxTempValue;
	}

	public String getMaxTempValueErrorMessage() {
		return maxTempValueErrorMessage;
	}

	public void setMaxTempValueErrorMessage(String maxTempValueErrorMessage) {
		this.maxTempValueErrorMessage = maxTempValueErrorMessage;
	}
	
	

	public Vector<BigDecimal> getLogReductionValues() {
		return logReductionValues;
	}

	public void setLogReductionValues(Vector<BigDecimal> logReductionValues) {
		this.logReductionValues = logReductionValues;
	}

	public boolean isReductionValueHighlight() {
		return reductionValueHighlight;
	}

	public void setReductionValueHighlight(boolean reductionValueHighlight) {
		this.reductionValueHighlight = reductionValueHighlight;
	}

	public boolean isDeeValueHighlight() {
		return deeValueHighlight;
	}

	public void setDeeValueHighlight(boolean deeValueHighlight) {
		this.deeValueHighlight = deeValueHighlight;
	}

	public boolean isZeeValueHighlight() {
		return zeeValueHighlight;
	}

	public void setZeeValueHighlight(boolean zeeValueHighlight) {
		this.zeeValueHighlight = zeeValueHighlight;
	}

	public boolean isSecondsValueHighlight() {
		return secondsValueHighlight;
	}

	public void setSecondsValueHighlight(boolean secondsValueHighlight) {
		this.secondsValueHighlight = secondsValueHighlight;
	}

	public boolean isTemperatureValueHighlight() {
		return temperatureValueHighlight;
	}

	public void setTemperatureValueHighlight(boolean temperatureValueHighlight) {
		this.temperatureValueHighlight = temperatureValueHighlight;
	}

	public String getP1UnitType() {
		return p1UnitType;
	}

	public void setP1UnitType(String p1UnitType) {
		this.p1UnitType = p1UnitType;
	}

	public String getcTempValue() {
		return cTempValue;
	}

	public void setcTempValue(String cTempValue) {
		this.cTempValue = cTempValue;
	}

	public String getfTempValue() {
		return fTempValue;
	}

	public void setfTempValue(String fTempValue) {
		this.fTempValue = fTempValue;
	}

	/**
	 * Validate P3 Variation of R With Temperature
	 * 
	 */
	public void validateP3() {
		
		// all calculation fields must be numeric
		try {//dee value
			
			if (this.getDeeValue().trim().equals("")) 
				this.setDeeValue("0");
			
			BigDecimal bd = new BigDecimal(this.getDeeValue());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x == 0)
			{
				this.setDeeValueErrorMessage("Value must not be zero. ");
				this.setErrorMessage("Input field problem. ");
			}
			
		} catch (Exception e) {
			this.setDeeValueErrorMessage("Value " + this.getDeeValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//zee value
			
			if (this.getZeeValue().trim().equals("")) 
				this.setZeeValue("0");
			
			BigDecimal bd = new BigDecimal(this.getZeeValue());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x == 0)
			{
				this.setZeeValueErrorMessage("Value must not be zero. ");
				this.setErrorMessage("Input field problem. ");
			}
			
		} catch (Exception e) {
			this.setZeeValueErrorMessage("Value " + this.getZeeValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//seconds value
			
			if (this.getSecondsValue().trim().equals("")) 
				this.setSecondsValue("0");
			
			BigDecimal bd = new BigDecimal(this.getSecondsValue());
			int x = bd.compareTo(new BigDecimal("0"));
			
			if (x == 0)
			{
				this.setSecondsValueErrorMessage("Value must not be zero. ");
				this.setErrorMessage("Input field problem. ");
			}
			
		} catch (Exception e) {
			this.setSecondsValueErrorMessage("Value " + this.getSecondsValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//minimum temperature value
			
			if (this.getMinTempValue().trim().equals("")) 
				this.setMinTempValue("0");
			
			BigDecimal bd = new BigDecimal(this.getMinTempValue());
			int x = bd.compareTo(new BigDecimal("0"));
		} catch (Exception e) {
			this.setMinTempValueErrorMessage("Value " + this.getMinTempValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		try {//maximum temperature value
			
			if (this.getMaxTempValue().trim().equals("")) 
				this.setMaxTempValue("0");
			
			BigDecimal bd = new BigDecimal(this.getMaxTempValue());
			int x = bd.compareTo(new BigDecimal("0"));
		} catch (Exception e) {
			this.setMaxTempValueErrorMessage("Value " + this.getMaxTempValue() + " is not a valid number. ");
			this.setErrorMessage("Input field problem. ");
		}
		
		//test to verify minimum is equal to or less than maximum.
		if(!this.getSubmit().equals("") && this.getErrorMessage().equals("")){
			
			int min = 0;
			if(this.getErrorMessage().trim().equals("")) {
				try {
					//int value without rounding.
					double d = Double.parseDouble(this.getMinTempValue());
					min = (int) d;
				} catch (Exception e) {
					this.setErrorMessage("Error with minimum value condictioning results. ");
				}
			}
			
			int max = 0;
			if(this.getErrorMessage().trim().equals("")) {
				try {
					//int value without rounding.
					double d = Double.parseDouble(this.getMaxTempValue());
					max = (int) d;
				} catch (Exception e) {
					this.setErrorMessage("Error with minimum value condictioning results. ");
				}
			}
			
			if(min >= max && this.getErrorMessage().equals("")) 
				this.setErrorMessage("Minimun value must be less than maximum value. ");
			
			//test to verify range of temperature results is less than 31.
			if(this.getErrorMessage().trim().equals("")) {
				int total = max - min;
				
				if (total <= 0)
					this.setErrorMessage("Temperature count invalid. ");
				if (total > 200)
					this.setErrorMessage("The temperature range is to large. ");
				
			}
			
		}	
	}
	
	
}
