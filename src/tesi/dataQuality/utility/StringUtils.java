package tesi.dataQuality.utility;

import java.util.Map.Entry;
import java.util.TreeMap;

public class StringUtils {
	
	private static final StringUtils _instance=new StringUtils();
	
	public StringUtils() {}
	
	public static StringUtils getInstance() {
		return _instance;
	}
	
	public static String normalize(String str) {
		str=str.replaceAll("v+[.]q+[.]p+[.]r+[.]d+[.]", "vino di qualità prodotto in regione determinata");
		str=str.replaceAll("escl+[.]", "escluso");
		str=str.replaceAll("incl+[.]", "incluso");
		str=str.replaceAll("n+[.]c+[.]a+[.]", "non classificate altrove");
		str=str.replaceAll("dtex", "decitex");
		str=str.replaceAll(">", "maggiore");
		str=str.replaceAll("<", "minore");
		str=str.replaceAll("largh+[.]", "larghezza");
		str=str.replaceAll("semplic+[.]", "semplicemente");
		str=str.replaceAll("prod+[.]", "prodotti");
		//
		str=str.replace('-', ' ');
		return str;
	}

	public static String[] getSuggs(String str) {
		String stringa = new String(str);
		stringa =stringa.substring(29,stringa.length()-1);
		String[] temp = stringa.split(", ");
		return temp;
	}

	public static String getCorrectionLT(TreeMap<Integer, Integer> mist, String campo, String scelta, int j) {
		StringBuilder sentence = new StringBuilder();
		int start=0;
		int end=0;
		int ciclo=1;
		for(Entry<Integer, Integer> entry : mist.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			if(ciclo == j) {
				start=entry.getKey();
				end=entry.getValue();
			}
			ciclo=ciclo+1;
			
		}
		sentence.append(campo.substring(0, start));
		sentence.append(scelta);
		sentence.append(campo.substring(end));
		
		return sentence.toString();
	}
	
}