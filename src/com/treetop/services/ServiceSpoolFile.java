package com.treetop.services;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.treetop.utilities.GeneralUtility;

public class ServiceSpoolFile {

	/**
	 * @author kkeife. Return a vector containing spool file data.
	 */

	public static File convertSpoolFileToPdf(String environment, String tableName, boolean dropTable)
			throws Exception {
		
		File f = null;
		
		StringBuffer throwError = new StringBuffer();
		Vector<String> returnValue = null;
		Connection conn = null;

		try {
			conn = ServiceConnection.getConnectionStack13();
			f = convertSpoolFileToPdf(environment, tableName, dropTable, conn);

		} catch (Exception e) {
			throwError.append(e);
		}

		finally {

			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack13(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceSpooledFiles.");
			throwError.append("getSpoolFileData(");
			throwError.append("String). ");
			throw new Exception(throwError.toString());
		}

		return f;
	}

	/**
	 * @author kkeife. Return all records in a spool file.
	 */

	private static File convertSpoolFileToPdf(String environment, String tableName, boolean dropTable,
			Connection conn) throws Exception {
		
		File f = null;
		
		if (environment == null || environment.equals("")) {
			environment = "PRD";
		}
		String ttLibrary = GeneralUtility.getTTLibrary(environment);

		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		Statement listThem = null;

		Statement deleteThem = null;

		Vector<String> returnValue = new Vector<String>();

		try {

			try {
				String sql = ("SELECT * FROM " + ttLibrary + "." + tableName);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);

			} catch (Exception e) {
				throwError.append("Error occured retrieving or executing a sql statement. " + e);
			}

			f = pdfOutput(tableName, rs);


		} catch (Exception e) {
			throwError.append(e);
		}

		
		
		try {
			if (dropTable) {
				String sql = "DROP TABLE " + ttLibrary + "." + tableName;
				deleteThem = conn.createStatement();
				deleteThem.executeUpdate(sql);
			}
		} catch (Exception e) {

		}

		finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}

			if (listThem != null) {
				try {
					listThem.close();
				} catch (Exception e) {
				}
			}
			if (deleteThem != null) {
				try {
					deleteThem.close();
				} catch (Exception e) {
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.utilities.");
			throwError.append("GeneralUtility.");
			throwError.append("getSpoolFileData(");
			throwError.append("String, conn). ");
			throw new Exception(throwError.toString());
		}

		return f;
	}
	
	
	private static File pdfOutput(String fileName, ResultSet rs)  throws Exception {
		
		float pageWidth = 792;
		float pageHeight = 612;
		
		float leftMargin = 32;
		float topMargin = pageHeight - 42;
		
		float fontSize = 6;
		float lineHeight = 9;
		
		PDDocument doc = new PDDocument();
        
		PDFont font = PDType1Font.COURIER_BOLD; 
		
        PDPage page = new PDPage(new PDRectangle(pageWidth,pageHeight));
        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        contentStream.setFont(font, fontSize );
        contentStream.beginText();
        contentStream.moveTextPositionByAmount(leftMargin, topMargin);

        String pageBreak = "";
        String lineBreak = "";
        String data 	 = "";
        
        boolean first = true;
        while (rs.next()) {
        	
        	pageBreak = rs.getString("INUSKB").trim();
        	lineBreak = rs.getString("INUSPB").trim();
        	int lineBreaks = 1;
        	try {
        		lineBreaks = Integer.parseInt(lineBreak);
        	} catch (Exception e) {
        		
        	}

        	
        	for (int i=0; !first && i<lineBreaks; i++) {
        		contentStream.moveTextPositionByAmount(0, -lineHeight);
        	}
        	
        	if (!first && !pageBreak.equals("")) {
        		contentStream.endText();
        		contentStream.close();
        		
        		page = new PDPage(new PDRectangle(pageWidth,pageHeight));
                doc.addPage(page);
                contentStream = new PDPageContentStream(doc, page);
                contentStream.setFont(font , fontSize );
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(leftMargin, topMargin);
        	}
        	
        	data = rs.getString("INUDTA");
        	
        	contentStream.drawString(data);

            first = false;
        }

        contentStream.endText();
		contentStream.close();
		        
        File f = File.createTempFile(fileName,".pdf");
        FileOutputStream fos = new FileOutputStream(f);
        
		doc.save(fos);
		
		fos.close();
		doc.close();
		
		return f;
		
	}
	
	
	
}
