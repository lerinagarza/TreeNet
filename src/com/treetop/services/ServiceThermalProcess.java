package com.treetop.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.treetop.controller.thermal.InqThermal;

@RunWith(Suite.class)
public class ServiceThermalProcess {

	private static DecimalFormat df = new DecimalFormat("#.#");
	
	public static void discreteProcessCalcLogKill(InqThermal inqThermal) {

		double r = 0; 

		double s = Double.parseDouble(inqThermal.getSecondsValue());
		double t = Double.parseDouble(inqThermal.getTempValue());
		double d = Double.parseDouble(inqThermal.getDeeValue());
		double z = Double.parseDouble(inqThermal.getZeeValue());
		
		r = (s / 60) * Math.pow(10,  (t-60)/z  )  /  d;
		
		inqThermal.setReductionValue(df.format(r));
		
	}
	
	public static void discreteProcessCalcD(InqThermal inqThermal) {
		double d = 0;
		double s = Double.parseDouble(inqThermal.getSecondsValue());
		double t = Double.parseDouble(inqThermal.getTempValue());
		double r = Double.parseDouble(inqThermal.getReductionValue());
		double z = Double.parseDouble(inqThermal.getZeeValue());
		
		d = (  (s / 60) * Math.pow(10, (t - 60) / z)  ) / r;

		inqThermal.setDeeValue(df.format(d));
		
	}
	
	public static void discreteProcessCalcSeconds(InqThermal inqThermal) {
		
		double s = 0; 

		double t = Double.parseDouble(inqThermal.getTempValue());
		double r = Double.parseDouble(inqThermal.getReductionValue());
		double d = Double.parseDouble(inqThermal.getDeeValue());
		double z = Double.parseDouble(inqThermal.getZeeValue());
		
		s = (60 * d * r) / Math.pow(10, (t - 60) / z);
		
		inqThermal.setSecondsValue(df.format(s));
		
	}
	
	public static void discreteProcessCalcTemp(InqThermal inqThermal) {
		
		double t = 0; 

		double s = Double.parseDouble(inqThermal.getSecondsValue());
		double r = Double.parseDouble(inqThermal.getReductionValue());
		double d = Double.parseDouble(inqThermal.getDeeValue());
		double z = Double.parseDouble(inqThermal.getZeeValue());
		
		t = z * Math.log10((60 * d * r) / s) + 60;
		
		inqThermal.setTempValue(df.format(t));
		
	}
	
	public static void discreteProcessCalcZ(InqThermal inqThermal) {
		
		double z = 0;
		
		double s = Double.parseDouble(inqThermal.getSecondsValue());
		double r = Double.parseDouble(inqThermal.getReductionValue());
		double d = Double.parseDouble(inqThermal.getDeeValue());
		double t = Double.parseDouble(inqThermal.getTempValue());
		
		z = (t-60) / Math.log10((60 * d * r) / s);
		
		inqThermal.setZeeValue(df.format(z));
		
	}
	
	
	public static void accumulatedProcessCalculation(InqThermal inqThermal) {
		
		double s = Double.parseDouble(inqThermal.getSecondsValue());
		double d = Double.parseDouble(inqThermal.getDeeValue());
		double z = Double.parseDouble(inqThermal.getZeeValue());
		
		double temp1 = Double.parseDouble(inqThermal.getTemperature1());
		double temp2 = Double.parseDouble(inqThermal.getTemperature2());
		double temp3 = Double.parseDouble(inqThermal.getTemperature3());
		double temp4 = Double.parseDouble(inqThermal.getTemperature4());
		
		List<Double> temps = new ArrayList<Double>();
		if (temp1 != 0) {
			temps.add(temp1);
		}
		if (temp2 != 0) {
			temps.add(temp2);
		}
		if (temp3 != 0) {
			temps.add(temp3);
		}
		if (temp4 != 0) {
			temps.add(temp4);
		}
		
		double accPO = 0;
		double accR = 0;
		
		
		for (double t : temps) {
			
			accPO = accPO + ( (s / 60) * Math.pow(10, (t - 60 ) / z) ) / d;
			accR = accR + (s / 60) / Math.pow(10,  (60-t) / z );
		}
		
		inqThermal.setPoValue(String.valueOf(df.format(accPO)));
		inqThermal.setLogReduction(String.valueOf(df.format(accR)));
		
	}
	
	
	public static void calculateVariationOfRWithTemperatures(InqThermal inqThermal) {
		
		Vector<BigDecimal> rValues = new Vector<BigDecimal>();
		
		int minTemperature = Integer.parseInt(inqThermal.getMinTempValue());
		int maxTemperature = Integer.parseInt(inqThermal.getMaxTempValue());
		int range = maxTemperature - minTemperature;
		for (int i=0; i<=range; i++) {
			
			inqThermal.setTempValue(String.valueOf(minTemperature));
			discreteProcessCalcLogKill(inqThermal);
			BigDecimal r = new BigDecimal(inqThermal.getReductionValue());

			inqThermal.getLogReductionValues().add(r);
			
			minTemperature++;
		}
		
		System.out.println(inqThermal.getLogReductionValues());
	
	}
	
	
	
	
	public static class UnitTests {
		
		private static InqThermal inqThermal;
		
		@Before
		public void setup() {
			inqThermal = new InqThermal();
			inqThermal.setDeeValue("10");
			inqThermal.setZeeValue("10");
			inqThermal.setSecondsValue("22.8");
			inqThermal.setTempValue("94.0");
			inqThermal.setReductionValue("95.5");
			
			inqThermal.setMinTempValue("60");
			inqThermal.setMaxTempValue("70");
			
			inqThermal.setTemperature1("80");
			inqThermal.setTemperature2("94");
			inqThermal.setTemperature3("0");
			inqThermal.setTemperature4("0");
		
			
		}
		
		@Test
		public void testDiscreteProcessCalcLogKill() {

			inqThermal.setReductionValue("");
			
			discreteProcessCalcLogKill(inqThermal);
			
			assertEquals("Log Kill", "95.5", inqThermal.getReductionValue());
			
		}
		
		@Test
		public void testDiscreteProcessCalcD() {

			
			inqThermal.setDeeValue("");
			
			discreteProcessCalcD(inqThermal);
			
			assertEquals("D-value", "10", inqThermal.getDeeValue());
			
		}
		
		@Test
		public void testDiscreteProcessCalcZ() {

			inqThermal.setZeeValue("");
			
			discreteProcessCalcZ(inqThermal);
			
			assertEquals("Z-value", "10", inqThermal.getZeeValue());
			
		}
		
		@Test
		public void testDiscreteProcessCalcSeconds() {

			inqThermal.setSecondsValue("");
			
			discreteProcessCalcSeconds(inqThermal);
			
			assertEquals("Seconds", "22.8", inqThermal.getSecondsValue());
			
		}
		
		@Test
		public void testDiscreteProcessCalcTemp() {
			
			inqThermal.setTempValue("");
			
			discreteProcessCalcTemp(inqThermal);
			
			assertEquals("Temperature", "94", inqThermal.getTempValue());
			
		}
		
		@Test
		public void testAccumulatedProcessCalculation() {
						
			accumulatedProcessCalculation(inqThermal);
			
			assertEquals("Accumulated PO", "99.3", inqThermal.getPoValue());
			assertEquals("Accumulated Log Reduction", "992.5", inqThermal.getLogReduction());
			
		}
		
		@Test
		public void testCalculateVariationOfRWithTemperatures() {
			
			calculateVariationOfRWithTemperatures(inqThermal);
			
			assertEquals("element count", 11, inqThermal.getLogReductionValues().size());
			
			
		}
		
	}
	
	
}
