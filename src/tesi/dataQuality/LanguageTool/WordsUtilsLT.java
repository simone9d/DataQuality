package tesi.dataQuality.LanguageTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

import tesi.dataQuality.model.Parola;

/**
 * Classe per la gestione delle eccezioni
 * per il tool LanguageTool
 * @author PC-Simone
 *
 */
public class WordsUtilsLT {
	
	/** The single instance of this class. */
	private static final WordsUtilsLT _theInstance = new WordsUtilsLT();
	private static Set<String> toIgnorePermanently = new TreeSet<String>(); 
	private static Set<String> toIgnoreTemporarily = new TreeSet<String>();
	
	public static TreeSet<String> getToIgnoreWords(){
		toIgnorePermanently.addAll(load());
		Set<String> toIgnoreWords = new TreeSet<String>();
		toIgnoreWords.clear();
		toIgnoreWords.addAll(toIgnorePermanently);
		toIgnoreWords.addAll(toIgnoreTemporarily);
		save();
		return (TreeSet<String>) toIgnoreWords;
	}
	
	public static TreeSet<Parola> getToIgnoreShow(){
		Set<Parola> words = new TreeSet<Parola>();
		for(String str : toIgnoreTemporarily) {
			words.add(new Parola(str));
		}
		toIgnorePermanently.addAll(load());
		for(String str : toIgnorePermanently) {
			words.add(new Parola(str));
		}
		save();
		return (TreeSet<Parola>) words;
	}
	
	public static WordsUtilsLT getInstance() {
		return _theInstance;
	}
	
	public static void deleteWordPermanently(String word){
		WordsUtilsLT.toIgnorePermanently.remove(word);
		save();
	}
	
	public static void addWordPermanently(String word){
		WordsUtilsLT.toIgnorePermanently.add(word);
		save();
	}
	
	public static void deleteAllPermanently(){
		WordsUtilsLT.toIgnorePermanently.clear();
		save();
	}

	public static void deleteAllTemporarily() {
		WordsUtilsLT.toIgnoreTemporarily.clear();
	}
	
	public static void deleteWordTemporarily(String word) {
		WordsUtilsLT.toIgnoreTemporarily.remove(word);
	}

	public static void addWordTemporarily(String word) {
		WordsUtilsLT.toIgnoreTemporarily.add(word);
	}
	
	public static boolean checkToIgnore(String str) {
		toIgnorePermanently.addAll(load());
		if(WordsUtilsLT.toIgnorePermanently.contains(str) || WordsUtilsLT.toIgnoreTemporarily.contains(str)) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 *  Sole constructor, private because this is a Singleton class.
	 */
	private WordsUtilsLT() {
	}

	@SuppressWarnings("unchecked")
	private static TreeSet<String> load(){
		File f = new File("support/toIgnorePermanentlyLT.dat");
		
		if(f.exists() && !f.isDirectory()) {
			try {
				FileInputStream inFile = new FileInputStream("support/toIgnorePermanentlyLT.dat");
				ObjectInputStream inStream = new ObjectInputStream(inFile);
				Set<String> words = (TreeSet<String>) inStream.readObject();
				inStream.close();
				return (TreeSet<String>) words;
				
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error");
			}
			
			
		}
		
		
		Set<String> toIgn= new TreeSet<String>();
		ignore((TreeSet<String>) toIgn);
		
		return (TreeSet<String>) toIgn;
				
	}

	private static void ignore(TreeSet<String> toIgn) {
		toIgn.add("decitex");
		toIgn.add("caoliniche");
		toIgn.add("sillimanite");
		toIgn.add("chamotte");
		toIgn.add("dinas");
		toIgn.add("asfaltiti");
		toIgn.add("pellet");
		toIgn.add("boniti");
		toIgn.add("seppiole");
		toIgn.add("pellets");
		toIgn.add("Müsli");
		toIgn.add("oleostearina");
		toIgn.add("linters");
		toIgn.add("Degras");
		toIgn.add("Fontal");
	}

	private static void save(){
		try {
			FileOutputStream outFile = new FileOutputStream("support/toIgnorePermanentlyLT.dat");
			ObjectOutputStream outStream = new ObjectOutputStream(outFile);
			outStream.writeObject(WordsUtilsLT.toIgnorePermanently);
			outStream.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
		
	}

}
