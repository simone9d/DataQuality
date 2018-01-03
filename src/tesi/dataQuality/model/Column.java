package tesi.dataQuality.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe model che rappresenta una colonna di una tabella
 * di un determinato database
 * @author PC-Simone
 *
 */
public class Column {
	
	private static List<Column> column = new LinkedList<Column>();
	
	private String campo;
	private boolean languageTool;
	private boolean jaspell;
	private boolean googleMaps;
	private final int index;
	private LinkedList<Campo> fields;
	private Report LTrep=new Report();
	private Report JSrep=new Report();
	private Report GMrep=new Report();
	
	public void setLTrep(Report lTrep) {
		LTrep = lTrep;
	}

	public void setJSrep(Report jSrep) {
		JSrep = jSrep;
	}

	public void setGMrep(Report gMrep) {
		GMrep = gMrep;
	}
	
	public Report getLTrep() {
		return LTrep;
	}

	public Report getJSrep() {
		return JSrep;
	}

	public Report getGMrep() {
		return GMrep;
	}

	public static LinkedList<Column> getList(){
		return (LinkedList<Column>) column;
	}
	
	public static void cleanList() {
		column.clear();
	}
	
	public static void addList(Column col) {
		((LinkedList<Column>) column).addLast(col);
	}
	
	public Column(String str, int index) {
		this.index=index;
		this.campo=str;
		this.languageTool=false;
		this.jaspell=false;
		this.googleMaps=false;
		this.fields=new LinkedList<Campo>();
	}
	
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public boolean isLanguageTool() {
		return languageTool;
	}
	public void setLanguageTool(boolean languageTool) {
		this.languageTool = languageTool;
	}
	public boolean isJaspell() {
		return jaspell;
	}
	public void setJaspell(boolean jaspell) {
		this.jaspell = jaspell;
	}
	public boolean isGoogleMaps() {
		return googleMaps;
	}
	public void setGoogleMaps(boolean googleMaps) {
		this.googleMaps = googleMaps;
	}
	
	public String toString() {
		StringBuilder mess=new StringBuilder();
		mess.append(this.campo);
		mess.append("\n");
		if(this.languageTool) {
			mess.append("LanguageTool --> true \n");
		}
		else {
			mess.append("LanguageTool --> false \n");
		}
		
		if(this.jaspell) {
			mess.append("JaSpell --> true \n");
		}
		else {
			mess.append("JaSpell --> false \n");
		}
		
		if(this.googleMaps) {
			mess.append("Google Maps --> true \n");
		}
		else {
			mess.append("GoogleMaps --> false \n");
		}
		
		return mess.toString();
	}
	

	public int getIndex() {
		return index;
	}

	public LinkedList<Campo> getFields() {
		return fields;
	}

	public void addFields(Campo field) {
		this.fields.addLast(field);
	}
}
