package tesi.dataQuality.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
