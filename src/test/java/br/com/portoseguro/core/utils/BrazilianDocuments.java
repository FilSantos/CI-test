package br.com.portoseguro.core.utils;

import org.apache.commons.lang3.StringUtils;

public class BrazilianDocuments {
	
	private char digit1;
	private char digit2;
	
	private static int[] generateRandomDocument (boolean person) {
		
		int[] document = new int[person ? 9 : 12];
		
		for (int i = 0; i < (person ? 9 : 11); i++) {	
			document[i] = (int) (i>9 && !person ? 0 : Math.random() * 9);
		}
		return document;
		
	}

	private static int mod(int number, int factor) {
		return (int) Math.round(number - (Math.floor(number / factor) * factor));
		
	}
	
	private String removeSpecialCharacters(String document) {
		return document.replace(".", "").replace("-", "").replace("/", "");
		
	}

	public static String person(boolean formatted) {
		
		int[] n = generateRandomDocument(true);
	
		int d1 = n[8] * 2 + n[7] * 3 + n[6] * 4 + n[5] * 5 + n[4] * 6 + n[3] * 7 + n[2] * 8 + n[1] * 9 + n[0] * 10;
		d1 = 11 - (mod(d1, 11));
		if (d1 >= 10) d1 = 0;

		int d2 = d1 * 2 + n[8] * 3 + n[7] * 4 + n[6] * 5 + n[5] * 6 + n[4] * 7 + n[3] * 8 + n[2] * 9 + n[1] * 10 + n[0] * 11;
		d2 = 11 - (mod(d2, 11));
		if (d2 >= 10) d2 = 0;
		
		return (String) (formatted ? 	n[0] + n[1] + n[2] + '.' + n[3] + n[4] + n[5] + '.' + n[6] + n[7] + n[8] + '-' + d1 + d2 : 
										String.valueOf(n[0]) + n[1] + n[2] + n[3] + n[4] + n[5] + n[6] + n[7] + n[8] + d1 + d2);
		
	}

	public String company(boolean mainCompany, boolean formatted) {
		
		int[] n = generateRandomDocument(false);
		
		int d1 = n[11] * 2 + n[10] * 3 + n[9] * 4 + n[8] * 5 + n[7] * 6 + n[6] * 7 + n[5] * 8 + n[4] * 9 + n[3] * 2 + n[2] * 3 + n[1] * 4 + n[0] * 5;
		d1 = 11 - (mod(d1, 11));
		if (d1 >= 10) d1 = 0;

		int d2 = d1 * 2 + n[11] * 3 + n[10] * 4 + n[9] * 5 + n[8] * 6 + n[7] * 7 + n[6] * 8 + n[5] * 9 + n[4] * 2 + n[3] * 3 + n[2] * 4 + n[1] * 5 + n[0] * 6;
		d2 = 11 - (mod(d2, 11));
		if (d2 >= 10) d2 = 0;

		return formatted ? n[0] + n[1] + "." + n[2] + n[3] + n[4] + "." + n[5] + n[6] + n[7] + "/" + n[8] + n[9] + n[10] + n[11] + "-" + d1 + d2 :
			String.valueOf( n[0] + n[1] + n[2] + n[3] + n[4] + n[5] + n[6] + n[7] + n[8] + n[9] + n[10] + n[11] + d1 + d2);
		
	}
	
public boolean validPerson(String document) {
		
		document = removeSpecialCharacters(document);
		if (SequenceNumber(document)==false || document.length() != 11) return false;
		
		try {
			digit1 = validaDigito(document,10,9,0);
			digit2 = validaDigito(document, 11, 10,0);
			return digit1 == document.charAt(9) && digit2 == document.charAt(10) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validCompany(String document) {
		
		document = removeSpecialCharacters(document); 
		if (SequenceNumber(document)==false || document.length() != 14) return false;
		
		try {
			digit1 = validaDigito(document,2,0,11);
			digit2 = validaDigito(document,2,0,12);
			return digit1 == document.charAt(12) && digit2 == document.charAt(13) ? true : false;
			
		} catch (Exception e) {
			return false;
		}
		
	}

	private boolean SequenceNumber(String document) {
		
		for (int i = 0; i < document.length(); i++) {
			if (document.equals(StringUtils.repeat(String.valueOf(i), document.length()))) return false;
		}
		return true;
	
	}
	private char validaDigito(String document, int factor, int digit, int startAt) {
		document = document.trim();
		int sm = 0;
		
		if (document.length() == 11) { // Person
			for (int i = startAt; i <digit; i++) {       
				sm = sm + ((document.charAt(i) - 48) * factor);
				factor --;
			}
			
		}else{ //Company
			for (int i = startAt; i >= digit; i--) {
				sm = sm + ((document.charAt(i) - 48) * factor);
				factor ++;
				if (factor == 10)	factor = 2;				
			}
		}
		
		int r = document.length() == 11 ? 11 - (sm % 11) :  sm % 11;
		if (document.length() == 11) { // Person
			return  r == 10 || r == 11 ? '0' : (char) (r + 48);
		} else { // Company
			return (r == 0) || (r == 1) ? '0' :  (char) ((11 - r) + 48);
			
		}
		
	}
}