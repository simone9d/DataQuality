package tesi.dataQuality.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe model necessaria per la visualizzazione
 * delle parole nelle tabelle
 * @author PC-Simone
 *
 */
public class Parola implements Comparable<Parola> {
	
	private final StringProperty parola;
	
    public Parola(String str){
    	this.parola=new SimpleStringProperty(str);
    }
    
	public String getWord() {
		return parola.get();
	}

    public void setWord(String Name) {
        this.parola.set(Name);
    }

    public StringProperty wordProperty() {
        return parola;
    }

	@Override
	public int compareTo(Parola o) {
		return this.getWord().compareTo(o.getWord());
	}

}
