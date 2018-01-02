package tesi.dataQuality.JaSpell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;
import tesi.dataQuality.model.Parola;

public class WordsUtilsJS {
	
	/** The single instance of this class. */
	private static final WordsUtilsJS _theInstance = new WordsUtilsJS();
	private static TreeSet<String> toIgnorePermanently = new TreeSet<String>();
	
	public static TreeSet<String> getToIgnoreWords(){
		toIgnorePermanently.addAll(load());
		save();
		return toIgnorePermanently;
	}
	
	public static TreeSet<Parola> getToIgnoreShow(){
		TreeSet<Parola> words = new TreeSet<Parola>();
		toIgnorePermanently.addAll(load());
		for(String str : toIgnorePermanently) {
			words.add(new Parola(str));
		}
		save();
		return words;
	}
	
	public static WordsUtilsJS getInstance() {
		return _theInstance;
	}
	
	public static void deleteWordPermanently(String word){
		WordsUtilsJS.toIgnorePermanently.remove(word);
		save();
	}
	
	public static void addWordPermanently(String word){
		WordsUtilsJS.toIgnorePermanently.add(word);
		save();
	}
	
	/**
	 *  Sole constructor, private because this is a Singleton class.
	 */
	private WordsUtilsJS() {
	}

	private static TreeSet<String> load(){
		TreeSet<String> words = new TreeSet<String>();
		
		try {
			FileReader fr = new FileReader("dict/italian.txt");
			BufferedReader br = new BufferedReader(fr);
			String redString = br.readLine();
			
			
			
			while(br.readLine()!=null) {
				int len=redString.length();	
				redString=redString.substring(0, len-4);
				words.add(redString);
				
				redString=br.readLine();
			}
			
			br.close();
			
		} catch (IOException e) {}
		
		return words;
				
	}

	private static void save(){
		FileWriter fw;
		try {
			fw = new FileWriter("dict/italian.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter scrivi = new PrintWriter (bw);
			
			for(String str : toIgnorePermanently) {
				StringBuilder strB=new StringBuilder();
				strB.append(str);
				strB.append(" : 1");
				scrivi.println(strB.toString());
			}
			scrivi.close();
		} catch (IOException e) {}
		
		
	}

}
