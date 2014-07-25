/*
 * Created on April 2, 2012
 *    Author Teri Walton
 * 
 * Will be used to control any reports needed Billing for contract manufacturing
 * 
 */
package com.treetop.controller.operations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import com.treetop.SessionVariables;
import com.treetop.businessobjects.KeyValue;
import com.treetop.businessobjects.ManufacturingOrderDetail;
import com.treetop.controller.BaseController;
import com.treetop.services.ServiceOperationsReporting;
import com.treetop.services.ServiceOperationsReporting.ReportingType;
import com.treetop.services.ServiceOperationsReportingFinancials;
import com.treetop.services.ServiceOperationsReportingHR;
import com.treetop.services.ServiceOperationsReportingManufacturing;
import com.treetop.utilities.html.HTMLHelpersMasking;

/**
 * 7/31/12
 * 
 * @author jhagle
 */

public class CtlOperations extends BaseController {
	
	private String inqOperations(HttpServletRequest request) {

		try {

			InqOperations io = new InqOperations(request);

			if (	!io.getWarehouse().equals("")
					&& io.getWarehouseError().equals("")
					&& !io.getFiscalYear().equals("")
					&& !io.getFiscalPeriodStart().equals("")
					&& !io.getFiscalWeekStart().equals("")
					&& io.getDateError().equals("")) {
				
				// all conditions met to view detail page
				
				//if there is no query string, then submit
				//  i.e.  entering in the address bar of the browser 
				//		https://treenet.treetop.com/web/CtlOperations/weekly/209/201335
				if (request.getQueryString() == null && request.getPathInfo() != null) {
					io.setSubmit("y");
				}
				
			}

			if (io.getSubmit().trim().equals("")) {
				
				//just go to the inquiry page

			} else {
				
				//build everything you need
				
				String warehouse = io.getWarehouse().trim();
				
				if (warehouse.equals("209")) {
					buildSelah(request, io);
				} else if (warehouse.equals("230")) {
					buildWenatchee(request, io);
				} else if (warehouse.equals("240")) {
					buildRoss(request, io);
				} else if (warehouse.equals("251")) {
					buildOxnard(request, io);
				} else if (warehouse.equals("280")) {
					buildMedford(request, io);
				} else if (warehouse.equals("290")) {
					buildWoodburn(request, io);
				} else if (warehouse.equals("469")) {
					buildProsser(request, io);
				} else if (warehouse.equals("490")) {
					buildFreshSlice(request, io);
				} else if (warehouse.equals("342")
						|| warehouse.equals("343")
						|| warehouse.equals("345")
						|| warehouse.equals("346")
						|| warehouse.equals("349")
						|| warehouse.equals("350")
						|| warehouse.equals("380")
						|| warehouse.equals("384")) {
					buildCoPack(request, io);
				} else if (warehouse.equals("EXEC")) {
					
					ServiceOperationsReporting.listTotalAmount(io);
					
					
				}
				
				
				
				
				KeyValue keys = io.getCommentKeys();
				keys.setEnvironment(io.getEnvironment());
				keys.setEntryType("OperationsReporting");
				keys.setKey1(""); // will be set within the jsp (defines which section, eg. packaging)
				keys.setKey2(io.getWarehouse());
				
				keys.setKey3(io.getFiscalYear());
				
				keys.setVisibleOnLoad(true);
				keys.setViewOnly(false);
				keys.setHeaderText(null);
				
				if (io.getRequestType().equals("") || io.getRequestType().equals("weekly")) {
					keys.setKey4("");	//Fiscal Period
					keys.setKey5(io.getFiscalWeekStart());
				} else {
					//monthly summary
					
					String[] commentTypes = {
							"safety",
							"quality",
							"rawFruit",
							"processing",
							"processingLabor",
							"packaging",
							"ingredientPackaging",
							"juiceBlending",
							"ingredientBlending",
							"frozenCherries",
							"maintenance",
							"labAndQuality",
							"utilities",
							"inventoryAdjustments",
							"journalEntries",
							"financials",
							"warehousing",
							"summary"
					};
					
					ServiceOperationsReporting.getWeeklyComments(io, commentTypes);
					
					keys.setDescriptionAsHeader(true);
					keys.setKey4(io.getFiscalPeriodStart());	//Fiscal Period 
					keys.setKey5("");
				}
				
				
				
				
				//if viewing the exec summary page, only allow group 139 or role 8 to add/edit comments
				if (warehouse.equals("EXEC")) {
					
					String[] groups = SessionVariables.getSessionttiUserGroups(request, null);
					String[] roles = SessionVariables.getSessionttiUserRoles(request, null);
					
						//group 139 = Ops Rpt Executives                      role 8 = IS
					if (Arrays.asList(groups).contains("139") || Arrays.asList(roles).contains("8")) {
						keys.setViewOnly(false);
					} else {
						keys.setViewOnly(true);
					}
					
				}
				
			}
			
			io.setForecastLabel(ServiceOperationsReporting.FORECAST_LABEL);
			
			request.setAttribute("inqOperations", io);
			

		} catch (Exception e) {
			System.out.println("Error Found/Caught in CtlOperations.inqOperations():" + e);
		}

		return "/view/operations/inqOperations.jsp";
		//return null;
	}

	
	private void buildSelah(HttpServletRequest request, InqOperations io) {
		
		try {
			

			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PKG);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.BLND);
			ServiceOperationsReportingFinancials.getMaintenanceDollars(io);
			ServiceOperationsReportingFinancials.getUtilitiesDollars(io);
			ServiceOperationsReportingFinancials.getWarehousingDollars(io);
			ServiceOperationsReportingFinancials.getQualityDollars(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(io);
			String rate = HTMLHelpersMasking.maskPercent(benefitRate);
			io.setBenefitRate(rate);

			
			// combine two elements for Selah's packaging
			LinkedHashMap<String, ManufacturingOrderDetail> lhm = io.getBean().getPackagingMOs();
			ManufacturingOrderDetail osc = lhm.get("OSC:Line 2");
			ManufacturingOrderDetail nfc = lhm.get("NFC:Line 2");
			ManufacturingOrderDetail ln2 = lhm.get("Juice:Line 2");
			ln2.addValues(osc);
			ln2.addValues(nfc);
			
			lhm.remove("OSC:Line 2");
			lhm.remove("NFC:Line 2");
			

		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildSelah(request, io).  " + e);
		}
		
	}
	
	private void buildProsser(HttpServletRequest request, InqOperations io) {
		
		try {
			
			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PKG);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.FZCHRY);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.BLND);
			ServiceOperationsReportingFinancials.getMaintenanceDollars(io);
			ServiceOperationsReportingFinancials.getUtilitiesDollars(io);
			ServiceOperationsReportingFinancials.getWarehousingDollars(io);
			ServiceOperationsReportingFinancials.getQualityDollars(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(io);
			String rate = HTMLHelpersMasking.maskPercent(benefitRate);
			io.setBenefitRate(rate);
		
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildSelah(request, io).  " + e);
		}
		
	}
	
	private void buildWoodburn(HttpServletRequest request, InqOperations io) {
		
		try {

			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			ServiceOperationsReportingFinancials.getMaintenanceDollars(io);
			ServiceOperationsReportingFinancials.getUtilitiesDollars(io);
			ServiceOperationsReportingFinancials.getWarehousingDollars(io);
			ServiceOperationsReportingFinancials.getQualityDollars(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(io);
			String rate = HTMLHelpersMasking.maskPercent(benefitRate);
			io.setBenefitRate(rate);
			
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildSelah(request, io).  " + e);
		}
		
	}
	
	private void buildRoss(HttpServletRequest request, InqOperations io) {
		
		try {

			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PKG);
			ServiceOperationsReportingFinancials.getMaintenanceDollars(io);
			ServiceOperationsReportingFinancials.getUtilitiesDollars(io);
			ServiceOperationsReportingFinancials.getWarehousingDollars(io);
			ServiceOperationsReportingFinancials.getQualityDollars(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(io);
			String rate = HTMLHelpersMasking.maskPercent(benefitRate);
			io.setBenefitRate(rate);
		
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildSelah(request, io).  " + e);
		}
		
	}
	
	private void buildFreshSlice(HttpServletRequest request, InqOperations io) {
		
		try {


			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			ServiceOperationsReportingFinancials.getMaintenanceDollars(io);
			ServiceOperationsReportingFinancials.getUtilitiesDollars(io);
			ServiceOperationsReportingFinancials.getWarehousingDollars(io);
			ServiceOperationsReportingFinancials.getQualityDollars(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(io);
			String rate = HTMLHelpersMasking.maskPercent(benefitRate);
			io.setBenefitRate(rate);

			
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildSelah(request, io).  " + e);
		}
		
	}
	
	private void buildCoPack(HttpServletRequest request, InqOperations io) {
		
		try {

			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildSelah(request, io).  " + e);
		}
		
	}
	
	private void buildWenatchee(HttpServletRequest request, InqOperations io) {
		
		try {
			
			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PKG);
			ServiceOperationsReportingFinancials.getMaintenanceDollars(io);
			ServiceOperationsReportingFinancials.getUtilitiesDollars(io);
			ServiceOperationsReportingFinancials.getWarehousingDollars(io);
			ServiceOperationsReportingFinancials.getQualityDollars(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(io);
			String rate = HTMLHelpersMasking.maskPercent(benefitRate);
			io.setBenefitRate(rate);
			
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildSelah(request, io).  " + e);
		}
		
	}
	
	private void buildOxnard(HttpServletRequest request, InqOperations io) {
		
		try {

			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			ServiceOperationsReportingFinancials.getMaintenanceDollars(io);
			ServiceOperationsReportingFinancials.getUtilitiesDollars(io);
			ServiceOperationsReportingFinancials.getWarehousingDollars(io);
			ServiceOperationsReportingFinancials.getQualityDollars(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(io);
			String rate = HTMLHelpersMasking.maskPercent(benefitRate);
			io.setBenefitRate(rate);
		
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildSelah(request, io).  " + e);
		}
		
	}
	
	private void buildMedford(HttpServletRequest request, InqOperations io) {
		
		try {
						
			ServiceOperationsReporting.getOperationsWarehouse(io);
			ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
			
			ServiceOperationsReportingFinancials.getMaintenanceDollars(io);
			ServiceOperationsReportingFinancials.getUtilitiesDollars(io);
			ServiceOperationsReportingFinancials.getWarehousingDollars(io);
			ServiceOperationsReportingFinancials.getQualityDollars(io);
			ServiceOperationsReportingFinancials.getJournalEntries(io);
			ServiceOperationsReportingFinancials.getInventoryAdjustments(io);

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(io);
			String rate = HTMLHelpersMasking.maskPercent(benefitRate);
			io.setBenefitRate(rate);

		
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.buildMedford(request, io).  " + e);
		}
		
	}
	
	
	
	@Override
	protected boolean isSecurityEnabled() {
		return true;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		
		return inqOperations(request);
	

	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		return "https://develop.treetop.com/web/CtlOperations";
	}
	
}
