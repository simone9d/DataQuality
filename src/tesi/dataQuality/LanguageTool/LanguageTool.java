package tesi.dataQuality.LanguageTool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.languagetool.JLanguageTool;
import org.languagetool.language.Italian;
import org.languagetool.rules.RuleMatch;

import tesi.dataQuality.utility.StringUtils;

public class LanguageTool implements Runnable {

	public LanguageTool(String str){
		this.toCheck=str;
	}
		
	private String toCheck = new String();
	
	public String getToCheck() {
		return toCheck;
	}
	public void setToCheck(String toCheck) {
		this.toCheck = toCheck;
	}
	public LinkedList<String> getCorrections() {
		return corrections;
	}
	public void addCorrections(String str) {
		LanguageTool.corrections.addLast(str);
	}
	private static LinkedList<String> corrections = new LinkedList<String>();
	
	private Italian it= new Italian();
	
	@Override
	public void run() {
		
		try {
			corrections.clear();
			String toCheck=StringUtils.normalize(new String(this.toCheck));
			JLanguageTool langToolIT = new JLanguageTool(it);
			
			langToolIT.disableRule("WHITESPACE_PUNCTUATION");
			langToolIT.disableRule("COMMA_PARENTHESIS_WHITESPACE");
			langToolIT.disableRule("DOUBLE_PUNCTUATION");
			langToolIT.disableRule("UNPAIRED_BRACKETS");
			langToolIT.disableRule("UPPERCASE_SENTENCE_START");
			langToolIT.disableRule("ITALIAN_WORD_REPEAT_RULE");
			langToolIT.disableRule("WHITESPACE_RULE");
			langToolIT.disableRule("GR_01_001");
			langToolIT.disableRule("GR_02_001");
			langToolIT.disableRule("GR_04_001");
			langToolIT.disableRule("GR_04_002");
			langToolIT.disableRule("GR_05_002");
			langToolIT.disableRule("GR_09_001");
			langToolIT.disableRule("GR_09_002");
			langToolIT.disableRule("GR_09_003");
			langToolIT.disableRule("GR_09_004");
			langToolIT.disableRule("GR_10_001");
			langToolIT.disableRule("ST_01_002");
			langToolIT.disableRule("ST_01_004");	
			langToolIT.disableRule("ST_02_001");
			langToolIT.disableRule("ST_03_001");
			langToolIT.disableRule("ST_03_002");
			langToolIT.disableRule("ST_04_001");
			langToolIT.disableRule("ST_04_002");
			langToolIT.disableRule("ER_01_001");
			langToolIT.disableRule("ER_01_001a");
			langToolIT.disableRule("ER_01_002");
			langToolIT.disableRule("ER_01_002a");
			langToolIT.disableRule("ER_01_003");
			langToolIT.disableRule("ER_01_004");
			langToolIT.disableRule("ER_02_001");
			langToolIT.disableRule("ER_02_002");
			langToolIT.disableRule("ER_02_004");
			langToolIT.disableRule("ER_01_003");
			langToolIT.disableRule("ER_02_005");
			
			List<RuleMatch> matches = langToolIT.check(toCheck);
			
			for (RuleMatch match : matches) {
				  if(match.getRule().getId().equals("MORFOLOGIK_RULE_IT_IT")) {
					  if(!WordsUtilsLT.checkToIgnore(toCheck.substring(match.getFromPos(), match.getToPos()))) {
						  addCorrections("Errore potenziale ai caratteri " + match.getFromPos() + "-" + match.getToPos() + ": " +  match.getMessage());
						  addCorrections("Correzione(i) suggerita(e): " + match.getSuggestedReplacements());
					  }
				  }
				  else {
					  addCorrections("Errore potenziale ai caratteri " + match.getFromPos() + "-" + match.getToPos() + ": " +  match.getMessage());
					  addCorrections("Correzione(i) suggerita(e): " + match.getSuggestedReplacements());
				  }
				  						  					  
				}
			
			FileWriter fw = new FileWriter("support/corrections.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter scrivi = new PrintWriter (bw);
			for(String str : corrections) {
				scrivi.println(str);
			}
			scrivi.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
