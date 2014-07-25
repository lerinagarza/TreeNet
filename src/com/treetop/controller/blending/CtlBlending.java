package com.treetop.controller.blending;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Item;
import com.treetop.businessobjects.ManufacturingOrder;
import com.treetop.businessobjects.Warehouse;
import com.treetop.controller.BaseController;
import com.treetop.controller.UrlPathMapping;
import com.treetop.services.ServiceBlending;
import com.treetop.utilities.UtilityDateTime;

@UrlPathMapping("requestType")
public class CtlBlending extends BaseController {
	
	private String productionSchedule(HttpServletRequest request) {
		InqBlending inqBlending = new InqBlending(request);
		
		if (!inqBlending.getSubmit().equals("")) {
		
			inqBlending.validate();
			try {
				ServiceBlending.listProductionPlan(inqBlending);
			} catch (Exception e) {
				inqBlending.setErrorMessage("Something happened when trying to get the planned production data.");
			}
			
			/*
			ManufacturingOrder m = new ManufacturingOrder();
			DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(inqBlending.getFromDate());
			dt = UtilityDateTime.addDaysToDate(dt, 0);
			m.setActualStartDate(dt);
			Warehouse w = new Warehouse();
			w.setFacility("301");
			m.setWarehouse(w);
			m.setProduction(new BigDecimal(100));
			Item item = new Item();
			item.setItemNumber("123456");
			item.setItemDescription("Planned proudction item");
			m.setItem(item);
			
			inqBlending.getBean().getListProduction().addElement(m);
			
			ManufacturingOrder ma = new ManufacturingOrder();
			DateTime dta = UtilityDateTime.getDateFromyyyyMMdd(inqBlending.getFromDate());
			dta = UtilityDateTime.addDaysToDate(dta, 0);
			ma.setActualStartDate(dta);
			Warehouse wa = new Warehouse();
			wa.setFacility("301");
			ma.setWarehouse(wa);
			ma.setProduction(new BigDecimal(175));
			Item itema = new Item();
			itema.setItemNumber("123999");
			itema.setItemDescription("Other planned proudction item");
			ma.setItem(itema);
			
			inqBlending.getBean().getListProduction().addElement(ma);
			
			ManufacturingOrder mb = new ManufacturingOrder();
			DateTime dtb = UtilityDateTime.getDateFromyyyyMMdd(inqBlending.getFromDate());
			dtb = UtilityDateTime.addDaysToDate(dtb, 0);
			mb.setActualStartDate(dtb);
			Warehouse wb = new Warehouse();
			wb.setFacility("301");
			mb.setWarehouse(wb);
			mb.setProduction(new BigDecimal(225));
			Item itemb = new Item();
			itemb.setItemNumber("124000");
			itemb.setItemDescription("Another planned proudction item");
			mb.setItem(itemb);
			
			inqBlending.getBean().getListProduction().addElement(mb);
			
			ManufacturingOrder m2 = new ManufacturingOrder();
			DateTime dt2 = UtilityDateTime.getDateFromyyyyMMdd(inqBlending.getFromDate());
			dt2 = UtilityDateTime.addDaysToDate(dt2, 0);
			m2.setActualStartDate(dt2);
			Warehouse w2 = new Warehouse();
			w2.setFacility("303");
			m2.setWarehouse(w2);
			m2.setProduction(new BigDecimal(250));
			Item item2 = new Item();
			item2.setItemNumber("123456");
			item2.setItemDescription("Planned proudction item");
			m2.setItem(item2);
			
			inqBlending.getBean().getListProduction().addElement(m2);
			
			if (Integer.parseInt(inqBlending.getWeeks()) >= 2) {
				ManufacturingOrder m3 = new ManufacturingOrder();
				DateTime dt3 = UtilityDateTime.getDateFromyyyyMMdd(inqBlending.getFromDate());
				dt3 = UtilityDateTime.addDaysToDate(dt3, 7);
				m3.setActualStartDate(dt3);
				Warehouse w3 = new Warehouse();
				w3.setFacility("301");
				m3.setWarehouse(w3);
				m3.setProduction(new BigDecimal(125));
				Item item3 = new Item();
				item3.setItemNumber("123456");
				item3.setItemDescription("Planned proudction item");
				m3.setItem(item3);
				
				inqBlending.getBean().getListProduction().addElement(m3);
				
				ManufacturingOrder m4 = new ManufacturingOrder();
				DateTime dt4 = UtilityDateTime.getDateFromyyyyMMdd(inqBlending.getFromDate());
				dt4 = UtilityDateTime.addDaysToDate(dt4, 7);
				m4.setActualStartDate(dt4);
				Warehouse w4 = new Warehouse();
				w4.setFacility("309");
				m4.setWarehouse(w4);
				m4.setProduction(new BigDecimal(350));
				Item item4 = new Item();
				item4.setItemNumber("123456");
				item4.setItemDescription("Planned proudction item");
				m4.setItem(item4);
				
				inqBlending.getBean().getListProduction().addElement(m4);
			}
			
			if (Integer.parseInt(inqBlending.getWeeks()) >= 3) {
				ManufacturingOrder m5 = new ManufacturingOrder();
				DateTime dt5 = UtilityDateTime.getDateFromyyyyMMdd(inqBlending.getFromDate());
				dt5 = UtilityDateTime.addDaysToDate(dt5, 14);
				m5.setActualStartDate(dt5);
				Warehouse w5 = new Warehouse();
				w5.setFacility("309");
				m5.setWarehouse(w5);
				m5.setProduction(new BigDecimal(625));
				Item item5 = new Item();
				item5.setItemNumber("123456");
				item5.setItemDescription("Planned proudction item");
				m5.setItem(item5);
				
				inqBlending.getBean().getListProduction().addElement(m5);
			}
			*/
		}
		
		
		request.setAttribute("inqViewBean", inqBlending);
		return "/view/blending/inqBlending.jsp";
	}
	
	@Override
	protected boolean isSecurityEnabled() {
		return false;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		return productionSchedule(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		return null;
	}

}
