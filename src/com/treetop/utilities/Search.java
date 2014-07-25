package com.treetop.utilities;

import java.util.*;

public class Search {

	public Search() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inString = "\"search this\" and,these \"and this\"";
		Vector keywords = getKeywords(inString);
		printKeywords(keywords);
	}
	
	
	/**
	 * Receives a search string and returns a vector of keywords with special characters removed.
	 * Words enclosed in double quotes will be kept together.
	 * @author jhagle
	 * @param String inSearch
	 * @return Vector keywords
	 */
	public static Vector getKeywords(String inString) {
		Vector keywords = new Vector();
		//Remove special characters
		Vector chars = new Vector();
		chars.addElement(",");
		chars.addElement("\'");
		chars.addElement("\"");
		chars.addElement("&");
		chars.addElement("%");
		chars.addElement(";");
		chars.addElement(":");
		
		/*	Set search string into StringBuffer converting all single quotes to *
		 *	double quotes to keep keyword together.								*/
		StringBuffer search = new StringBuffer(inString.replaceAll("\'", "\""));
		
		if (inString != null && !inString.trim().equals("")) {

			//keep words within quotes together
			if (search.indexOf("\"") >= 0){
				int s = 0;
				int e = 0;
				boolean first = true;
				for (int i=0; i<search.length(); i++) {
					if (search.substring(i,i+1).equals("\"")) {
						if (first) {
							s = i;
							first = false;
						} else {
							e = i;
							

							
							keywords.addElement(FindAndReplace.removeCharacters(search.substring(s+1,e).trim(), chars));
							search.delete(s,e+1);
							first = true;
							i = 0;
						}
						
					}
				} //end for loop
			}
			
			//get remainig single words seperated by a space or comma
			String[] single = search.toString().split(" |,");
			
			for (int i = 0; i<single.length; i++) {
				if (!single[i].trim().equals(""))
				keywords.addElement(FindAndReplace.removeCharacters(single[i].trim(), chars));
			}
		}
		return keywords;
	}

	

	private static void printKeywords(Vector keywords) {
		System.out.print("{");
		for (int i = 0; i<keywords.size(); i++) {
			if (i == 0) {System.out.print(keywords.elementAt(i).toString());}
			else {System.out.print("|" + keywords.elementAt(i).toString());}
		}
		System.out.println("}");
	}
}

