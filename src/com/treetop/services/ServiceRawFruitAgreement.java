package com.treetop.services;

import com.treetop.businessobjects.*;
import com.treetop.controller.rawfruitagreements.InqRawFruitAgreements;
import com.treetop.controller.rawfruitagreements.UpdContract;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.HtmlOption;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;

import static org.junit.Assert.fail;

public class ServiceRawFruitAgreement {

	@Test
	public void testGetRawFruitAgreement() {
        try {
            InqRawFruitAgreements inqRFA = new InqRawFruitAgreements();
            inqRFA.setEnvironment("TST");
            inqRFA.setWriteUpNumber("123");
            getAgreement(inqRFA);

        } catch (Exception e)         {
            fail(e.toString());
        }
	}

    public static Vector<HtmlOption> buildDropDownCropYear() {

        Vector<HtmlOption> options = new Vector<HtmlOption>();

        int year = Calendar.getInstance().get(Calendar.YEAR) - 2;

        for (int i=0; i<=3; i++) {
            HtmlOption option = new HtmlOption();
            option.setValue(String.valueOf(year));
            option.setDescription(String.valueOf(year));
            options.addElement(option);
            year++;
        }


        return options;
    }

    public static Vector<HtmlOption> buildDropDownCrop() {

        Vector inValues = new Vector();
        inValues.addElement("payCode");
        Vector values = ServiceRawFruit.dropDownCrop(inValues);

        return HtmlOption.convertDropDownSingleVector(values);
    }


    public static Vector<HtmlOption> buildDropDownType() {

        Vector inValues = new Vector();
        inValues.addElement("payCode");
        Vector values = ServiceRawFruit.dropDownConvOrganic(inValues);

        return HtmlOption.convertDropDownSingleVector(values);

    }

    public static Vector<HtmlOption> buildDropDownCategory() {

        Vector inValues = new Vector();
        inValues.addElement("payCode");
        Vector values = ServiceRawFruit.dropDownCategory(inValues);

        return HtmlOption.convertDropDownSingleVector(values);

    }

    public static Vector<HtmlOption> buildDropDownVariety() {

        Vector inValues = new Vector();
        inValues.addElement("payCode");
        Vector values = ServiceRawFruit.dropDownVariety(inValues);

        return HtmlOption.convertDropDownSingleVector(values);

    }

    public static Vector<HtmlOption> buildDropDownBinType() {

        Vector inValues = new Vector();
        inValues.addElement("payCode");
        Vector values = ServiceRawFruit.dropDownBinType(inValues);

        return HtmlOption.convertDropDownSingleVector(values);

    }

    public static Vector<HtmlOption> buildDropDownPaymentType() {

        Vector inValues = new Vector();
        inValues.addElement("payCode");
        Vector values = ServiceRawFruit.dropDownPaymentType(inValues);

        return HtmlOption.convertDropDownSingleVector(values);

    }

	public static void getAgreement(InqRawFruitAgreements inqRawFruitAgreements)
     {
        RawFruitAgreement rfa = null;

        Connection conn = null;
        try {

            conn = ServiceConnection.getConnectionStack15();
            rfa = getAgreement(conn, inqRawFruitAgreements);
            inqRawFruitAgreements.setAgreement(rfa);

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                ServiceConnection.returnConnectionStack15(conn);
            } catch (Exception e) {}
        }


		/*
		rfa.setSupplierNumber("1234");
		rfa.setEntryDate("20140602");
		rfa.setRevisedDate("20140722");
		rfa.setCropYear("2013");
		rfa.setFieldRep("Ian");
		
		Contact contact1 = new Contact();
		contact1.setName("Farmer Joe");
		contact1.getPhoneNumbers().add(new PhoneNumber("Mobile","5095551234"));
		contact1.getPhoneNumbers().add(new PhoneNumber("Office","5092489876"));
		rfa.getContacts().add(contact1);
		
		Contact contact2 = new Contact();
		contact2.setName("Farmer Jane");
		contact2.getPhoneNumbers().add(new PhoneNumber("Fax","5094561379"));
		rfa.getContacts().add(contact2);
		
		rfa.setCarrierName("Sir Hauls-a-lot");
		rfa.setCarrierComments("Be careful with my fruit!!!");
		
		Address orchardLocation = new Address();
		orchardLocation.setAddressId("001");
		orchardLocation.setStreet1("789 Way Out Here Ln");
		orchardLocation.setCity("Boonies");
		orchardLocation.setState("WA");
		orchardLocation.setZip("12345");
		orchardLocation.setLatitude(46.549074);
		orchardLocation.setLongitude(-120.675097);
		orchardLocation.setDrivingDirections("Just keep driving...  I'm sure you'll find it eventually.");
		rfa.setOrchardLocation(orchardLocation);
		
		Address supplierLocation = new Address();
		supplierLocation.setStreet1("123 Main St");
		supplierLocation.setCity("Big City");
		supplierLocation.setState("WA");
		supplierLocation.setZip("98765");
		rfa.setSupplierLocation(supplierLocation);
		
		RawFruitAgreementLine l1 = new RawFruitAgreementLine();
        l1.setSequence("1");
		l1.setCrop("Apple");
		l1.setType("Organic");
		l1.setRun("Orchard Run");
		l1.setCategory("Category?");
		l1.setVariety("Golden Delicious");
		l1.setVarietyMisc("Misc2");
		
		l1.setBins(new BigDecimal(1240));
		l1.setBinType("Wooden boxes");
		
		l1.setPaymentType("On Receipt");
		
		l1.setJuicePrice(new BigDecimal(120));
		l1.setJpPrice(new BigDecimal(125));
		l1.setPeelerPrice(new BigDecimal(145));
		l1.setPremiumPrice(new BigDecimal(162));
		l1.setFreshSlicePrice(new BigDecimal(240));
		l1.setPrice(new BigDecimal(0));
		rfa.getLines().add(l1);
		
		RawFruitAgreementLine l2 = new RawFruitAgreementLine();
        l2.setSequence("2");
		l2.setCrop("Apple");
		l2.setType("Conventional");
		l2.setRun("Machine Run");
		l2.setCategory("Category?");
		l2.setVariety("Gala");
		l2.setVarietyMisc("Misc2");
		
		l2.setBins(new BigDecimal(600));
		l2.setBinType("Plastic");
		
		l2.setPaymentType("On Receipt");
		
		l2.setJuicePrice(new BigDecimal(60));
		l2.setJpPrice(new BigDecimal(62));
		l2.setPeelerPrice(new BigDecimal(74));
		l2.setPremiumPrice(new BigDecimal(83));
		l2.setFreshSlicePrice(new BigDecimal(140));
		l2.setPrice(new BigDecimal(0));
		rfa.getLines().add(l2);
		
		RawFruitAgreementLine l3 = new RawFruitAgreementLine();
        l3.setSequence("3");
		l3.setCrop("Apple");
		l3.setType("Conventional");
		l3.setRun("Machine Run");
		l3.setCategory("Category?");
		l3.setVariety("Braeburn");
		l3.setVarietyMisc("");
		
		l3.setBins(new BigDecimal(85));
		l3.setBinType("Plastic");
		
		l3.setPaymentType("On Receipt");
		
		l3.setJuicePrice(new BigDecimal(45));
		l3.setJpPrice(new BigDecimal(47));
		l3.setPeelerPrice(new BigDecimal(52));
		l3.setPremiumPrice(new BigDecimal(63));
		l3.setFreshSlicePrice(new BigDecimal(68));
		l3.setPrice(new BigDecimal(0));
		rfa.getLines().add(l3);

*/

		
	}

    private static RawFruitAgreement getAgreement(Connection conn, InqRawFruitAgreements inqRawFruitAgreements)
    throws Exception {
        RawFruitAgreement rawFruitAgreement = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = conn.createStatement();
            String sql = BuildSQL.getAgreementHeader(inqRawFruitAgreements);
            rs = stmt.executeQuery(sql);
            rawFruitAgreement = LoadFields.getAgreementHeader(rs);


        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception e) {}
        }
        return rawFruitAgreement;

    }

    public static void updateAgreement(UpdContract updContract) {

        RawFruitAgreement rfa;

        Connection conn = null;
        try {

            conn = ServiceConnection.getConnectionStack15();
            deleteAgreementHeader(conn, updContract);
            insertAgreementHeader(conn, updContract);

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                ServiceConnection.returnConnectionStack15(conn);
            } catch (Exception e) {}
        }

    }

    private static void deleteAgreementHeader(Connection conn, UpdContract updContract)
            throws Exception {

        Statement stmt = null;
        try {

            stmt = conn.createStatement();
            String sql = BuildSQL.deleteAgreementHeader(updContract);
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {}
        }

    }

    private static void insertAgreementHeader(Connection conn, UpdContract updContract)
    throws Exception {

        PreparedStatement pstmt = null;
        try {

            String sql = BuildSQL.insertAgreementHeader(updContract);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, updContract.getWriteUpNumber());
            pstmt.setString(2, updContract.getSupplierNumber());

            DateTime entryDate = UtilityDateTime.getDateFromMMddyyyyWithSlash(updContract.getEntryDate());
            pstmt.setString(3, entryDate.getDateFormatyyyyMMdd());

            DateTime revisionDate = UtilityDateTime.getDateFromMMddyyyyWithSlash(updContract.getRevisionDate());
            pstmt.setString(4, revisionDate.getDateFormatyyyyMMdd());

            pstmt.setString(5, updContract.getCropYear());
            pstmt.setString(6, updContract.getFieldRep());

            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                pstmt.close();
            } catch (Exception e) {}
        }

    }




    public static RawFruitAgreementLine getAgreementLine() {
        RawFruitAgreementLine l1 = new RawFruitAgreementLine();
        l1.setCrop("Apple");
        l1.setType("Organic");
        l1.setRun("Orchard Run");
        l1.setCategory("Category?");
        l1.setVariety("Golden Delicious");
        l1.setVarietyMisc("Misc2");

        l1.setBins(new BigDecimal(1240));
        l1.setBinType("Wooden boxes");

        l1.setPaymentType("On Receipt");

        l1.setJuicePrice(new BigDecimal(120));
        l1.setJpPrice(new BigDecimal(125));
        l1.setPeelerPrice(new BigDecimal(145));
        l1.setPremiumPrice(new BigDecimal(162));
        l1.setFreshSlicePrice(new BigDecimal(240));
        l1.setPrice(new BigDecimal(0));

        return l1;
    }

    private static class BuildSQL {

        private static String getAgreementHeader(InqRawFruitAgreements inqRawFruitAgreements) {

            String ttLibrary = GeneralUtility.getTTLibrary(inqRawFruitAgreements.getEnvironment());
            String library = GeneralUtility.getLibrary(inqRawFruitAgreements.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT AHSUNO, AHENDT, AHRVDT, AHCRPY, AHFREP, IDSUNM \r");
            sql.append(" FROM " + ttLibrary + ".GRPRFAHD \r");
            sql.append(" LEFT OUTER JOIN " + library + ".CIDMAS ON \r");
            sql.append(" IDCONO=100 AND IDSUAL='RFS' AND IDSUNO=AHSUNO \r");
            sql.append(" WHERE AHWNBR='" + inqRawFruitAgreements.getWriteUpNumber() + "' \r");

            return sql.toString();

        }

        private static String deleteAgreementHeader(UpdContract updContract) {

            String ttLibrary = GeneralUtility.getTTLibrary(updContract.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" DELETE " + ttLibrary + ".GRPRFAHD \r");
            sql.append(" WHERE AHWNBR='" + updContract.getWriteUpNumber() + "' \r");

            return sql.toString();

        }

        private static String insertAgreementHeader(UpdContract updContract) {

            String ttLibrary = GeneralUtility.getTTLibrary(updContract.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" INSERT INTO " + ttLibrary + ".GRPRFAHD \r");
            sql.append(" VALUES ( \r");
            sql.append(" ?,  ?, ?, ?, ?, ? \r");
            sql.append(" ) \r");

            return sql.toString();

        }

    }

    private static class LoadFields {

        private static RawFruitAgreement getAgreementHeader(ResultSet rs) throws Exception {

            RawFruitAgreement rfa = new RawFruitAgreement();
            if (rs.next()) {
                rfa.setSupplierNumber(rs.getString("AHSUNO").trim());
                rfa.setSupplierName(rs.getString("IDSUNM").trim());
                rfa.setEntryDate(rs.getString("AHENDT"));
                rfa.setRevisedDate(rs.getString("AHRVDT"));
                rfa.setCropYear(rs.getString("AHCRPY").trim());
                rfa.setFieldRep(rs.getString("AHFREP").trim());
            }
            return rfa;

        }

    }

}
