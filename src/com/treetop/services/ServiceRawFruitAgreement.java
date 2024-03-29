package com.treetop.services;

import com.treetop.businessobjects.*;
import com.treetop.controller.rawfruitagreements.InqRawFruitAgreement;
import com.treetop.controller.rawfruitagreements.UpdContract;
import com.treetop.controller.rawfruitagreements.UpdCropInfo;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.HtmlOption;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.fail;

public class ServiceRawFruitAgreement {

	@Test
	public void testGetRawFruitAgreement() {
        try {
            InqRawFruitAgreement inqRFA = new InqRawFruitAgreement();
            inqRFA.setEnvironment("TST");
            inqRFA.setId("1");
            getAgreement(inqRFA);


            System.out.println("complete");

        } catch (Exception e) {
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

	public static void getAgreement(InqRawFruitAgreement inqRawFruitAgreement) {

        Connection conn = null;
        try {

            conn = ServiceConnection.getConnectionStack15();
            getAgreement(conn, inqRawFruitAgreement);
            getAgreementLines(conn, inqRawFruitAgreement);

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

    private static RawFruitAgreement getAgreement(Connection conn, InqRawFruitAgreement inqRawFruitAgreement)
    throws Exception {
        RawFruitAgreement rawFruitAgreement = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = conn.createStatement();
            String sql = BuildSQL.getAgreementHeader(inqRawFruitAgreement);
            rs = stmt.executeQuery(sql);
            rawFruitAgreement = LoadFields.getAgreementHeader(rs);
            inqRawFruitAgreement.setAgreement(rawFruitAgreement);


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

            pstmt.setInt(1, updContract.getId());
            pstmt.setString(2, updContract.getWriteUpNumber());
            pstmt.setString(3, updContract.getSupplierNumber());

            DateTime entryDate = UtilityDateTime.getDateFromMMddyyyyWithSlash(updContract.getEntryDate());
            pstmt.setString(4, entryDate.getDateFormatyyyyMMdd());

            DateTime revisionDate = UtilityDateTime.getDateFromMMddyyyyWithSlash(updContract.getRevisionDate());
            pstmt.setString(5, revisionDate.getDateFormatyyyyMMdd());

            pstmt.setString(6, updContract.getCropYear());
            pstmt.setString(7, updContract.getFieldRep());

            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                pstmt.close();
            } catch (Exception e) {}
        }

    }


     private static void getAgreementLines(Connection conn, InqRawFruitAgreement inqRawFruitAgreement) {

        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = conn.createStatement();
            String sql = BuildSQL.getAgreementLines(inqRawFruitAgreement);
            rs = stmt.executeQuery(sql);

            List<RawFruitAgreementLine> lines = LoadFields.getAgreementLines(rs);
            inqRawFruitAgreement.getAgreement().setLines(lines);


        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception e) {}
        }

    }


    public static void updateAgreementLine(UpdCropInfo updCropInfo) {


        Connection conn = null;
        try {

            conn = ServiceConnection.getConnectionStack15();
            deleteAgreementLine(conn, updCropInfo);
            insertAgreementLine(conn, updCropInfo);

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                ServiceConnection.returnConnectionStack15(conn);
            } catch (Exception e) {}
        }

    }

    private static void deleteAgreementLine(Connection conn, UpdCropInfo updCropInfo)
            throws Exception {

        Statement stmt = null;
        try {

            stmt = conn.createStatement();
            String sql = BuildSQL.deleteAgreementLine(updCropInfo);
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {}
        }

    }


    private static void insertAgreementLine(Connection conn, UpdCropInfo updCropInfo)
            throws Exception {

        PreparedStatement pstmt = null;
        try {

            String sql = BuildSQL.insertAgreementLine(updCropInfo);
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, updCropInfo.getId());
            pstmt.setInt(2, updCropInfo.getSequence());
            pstmt.setString(3, updCropInfo.getCrop());
            pstmt.setString(4, updCropInfo.getType());
            pstmt.setString(5, updCropInfo.getRun());
            pstmt.setString(6, updCropInfo.getCategory());
            pstmt.setString(7, updCropInfo.getVariety());
            pstmt.setString(8, updCropInfo.getVarietyMisc());
            pstmt.setBigDecimal(9, new BigDecimal(updCropInfo.getBins()));
            pstmt.setString(10, updCropInfo.getBinType());
            pstmt.setString(11, updCropInfo.getPaymentType());
            pstmt.setBigDecimal(12, new BigDecimal(updCropInfo.getJuicePrice()));
            pstmt.setBigDecimal(13, new BigDecimal(updCropInfo.getJpPrice()));
            pstmt.setBigDecimal(14, new BigDecimal(updCropInfo.getPeelerPrice()));
            pstmt.setBigDecimal(15, new BigDecimal(updCropInfo.getPremiumPrice()));
            pstmt.setBigDecimal(16, new BigDecimal(updCropInfo.getFreshSlicePrice()));
            pstmt.setBigDecimal(17, new BigDecimal(updCropInfo.getPrice()));


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

        private static String getAgreementHeader(InqRawFruitAgreement inqRawFruitAgreement) {

            String ttLibrary = GeneralUtility.getTTLibrary(inqRawFruitAgreement.getEnvironment());
            String library = GeneralUtility.getLibrary(inqRawFruitAgreement.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT AHCTID, AHWNBR, AHSUNO, AHENDT, AHRVDT, AHCRPY, AHFREP, IDSUNM \r");
            sql.append(" FROM " + ttLibrary + ".GRPRFAHD \r");
            sql.append(" LEFT OUTER JOIN " + library + ".CIDMAS ON \r");
            sql.append(" IDCONO=100 AND IDSUAL='RFS' AND IDSUNO=AHSUNO \r");
            sql.append(" WHERE AHCTID='" + inqRawFruitAgreement.getId() + "' \r");

            return sql.toString();

        }

        private static String deleteAgreementHeader(UpdContract updContract) {

            String ttLibrary = GeneralUtility.getTTLibrary(updContract.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" DELETE " + ttLibrary + ".GRPRFAHD \r");
            sql.append(" WHERE AHCTID='" + updContract.getId() + "' \r");

            return sql.toString();

        }

        private static String deleteAgreementLine(UpdCropInfo updCropInfo) {

            String ttLibrary = GeneralUtility.getTTLibrary(updCropInfo.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" DELETE " + ttLibrary + ".GRPRFACP \r");
            sql.append(" WHERE ACCNID='" + updCropInfo.getId() + "' \r");
            sql.append("   AND ACCSEQ='" + updCropInfo.getSequence() + "' \r");

            return sql.toString();

        }

        private static String insertAgreementHeader(UpdContract updContract) {

            String ttLibrary = GeneralUtility.getTTLibrary(updContract.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" INSERT INTO " + ttLibrary + ".GRPRFAHD \r");
            sql.append(" VALUES ( \r");
            sql.append(" ?, ?, ?, ?, ?, ?, ? \r");
            sql.append(" ) \r");

            return sql.toString();

        }

        private static String insertAgreementLine(UpdCropInfo updCropInfo) {

            String ttLibrary = GeneralUtility.getTTLibrary(updCropInfo.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" INSERT INTO " + ttLibrary + ".GRPRFACP \r");
            sql.append(" VALUES ( \r");
            sql.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? \r");
            sql.append(" ) \r");

            return sql.toString();

        }

        private static String getAgreementLines(InqRawFruitAgreement inqRawFruitAgreement) {

            String ttLibrary = GeneralUtility.getTTLibrary(inqRawFruitAgreement.getEnvironment());

            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT *  \r");
            sql.append(" FROM " + ttLibrary + ".GRPRFACP \r");
            sql.append(" WHERE ACCTID='" + inqRawFruitAgreement.getId() + "' \r");
            sql.append(" ORDER BY ACCROP, ACTYPE, ACORUN, ACCATG, ACVARI, ACVARM \r");

            return sql.toString();

        }

    }

    private static class LoadFields {

        private static RawFruitAgreement getAgreementHeader(ResultSet rs) throws Exception {

            RawFruitAgreement rfa = new RawFruitAgreement();
            if (rs.next()) {
                rfa.setId(rs.getInt("AHCTID"));
                rfa.setWriteUpNumber(rs.getString("AHWNBR").trim());
                rfa.setSupplierNumber(rs.getString("AHSUNO").trim());
                rfa.setSupplierName(rs.getString("IDSUNM").trim());
                rfa.setEntryDate(rs.getString("AHENDT"));
                rfa.setRevisedDate(rs.getString("AHRVDT"));
                rfa.setCropYear(rs.getString("AHCRPY").trim());
                rfa.setFieldRep(rs.getString("AHFREP").trim());
            }

            return rfa;

        }

        private static List<RawFruitAgreementLine> getAgreementLines(ResultSet rs) throws Exception {

            List<RawFruitAgreementLine> lines = new ArrayList<RawFruitAgreementLine>();

            while (rs.next()) {

                RawFruitAgreementLine line = new RawFruitAgreementLine();

                line.setId(rs.getInt("ACCTID"));
                line.setSequence(rs.getInt("ACCSEQ"));
                line.setCrop(rs.getString("ACCROP").trim());
                line.setType(rs.getString("ACTYPE").trim());
                line.setRun(rs.getString("ACORUN").trim());
                line.setCategory(rs.getString("ACCATG").trim());
                line.setVariety(rs.getString("ACVARI").trim());
                line.setVarietyMisc(rs.getString("ACVARM").trim());

                line.setBins(rs.getBigDecimal("ACBINS"));
                line.setBinType(rs.getString("ACBINT").trim());
                line.setPaymentType(rs.getString("ACPMTP").trim());
                line.setJuicePrice(rs.getBigDecimal("ACJCPR"));
                line.setJpPrice(rs.getBigDecimal("ACJCPR"));
                line.setPeelerPrice(rs.getBigDecimal("ACPEPR"));
                line.setPremiumPrice(rs.getBigDecimal("ACPRPR"));
                line.setFreshSlicePrice(rs.getBigDecimal("ACFSPR"));
                line.setPrice(rs.getBigDecimal("ACOTPR"));



                lines.add(line);


            }


            return lines;

        }

    }

}
