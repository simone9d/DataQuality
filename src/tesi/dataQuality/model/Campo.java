package tesi.dataQuality.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.TreeMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Campo implements Comparable<Campo>{
	private StringProperty campo;
	private String pk;
	private LinkedList<String> LTcorrection=new LinkedList<String>();
	private String JSCorrection= new String();
	private String GMCorrection=new String();
	private String column=new String();
	private TreeMap<Integer,Integer> mistakesPos = new TreeMap<Integer,Integer>();
	private int mistCount=1;
	private BigDecimal lat;
	private BigDecimal lon; 
	
	public void setMist() {
		mistCount=1;
	}
	
	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = new BigDecimal(lat);
	}

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = new BigDecimal(lon);
	}

	public int getMistCount() {
		return mistCount;
	}

	public void incrMistCount() {
		this.mistCount = mistCount+1;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}
	
	public void addMist(int p1, int p2) {
		mistakesPos.put(p1, p2);
	}
	
	public TreeMap<Integer, Integer> getMist(){
		return this.mistakesPos;
	}

	public Campo(String campo, String pk) {
		this.campo=new SimpleStringProperty(campo);
		this.pk=pk;
	}
	
	public String getCampo() {
		return campo.get();
	}
	
	public StringProperty campoProperty() {
		return campo;
	}
	
	public String getPk() {
		return pk;
	}
	
	public void setPk(String pk) {
		this.pk = pk;
	}
	
	public void setCorrection(String str) {
		LTcorrection.addLast(str);
	}

	public String getLTcorrection() {
		StringBuilder str = new StringBuilder();
			for(String corr : this.LTcorrection) {
				str.append(corr);
				str.append("\n");
			}
		return str.toString();
	}
	
	public LinkedList<String> getAllLTCorrs(){
		return this.LTcorrection;
	}

	public String getJSCorrection() {
		return JSCorrection;
	}

	public void setJSCorrection(String jSCorrection) {
		JSCorrection = jSCorrection;
	}

	public String getGMCorrection() {
		return GMCorrection;
	}

	public void setGMCorrection(String gMCorrection) {
		GMCorrection = gMCorrection;
	}
	
	public int compareTo(Campo field) {
		return this.getCampo().compareTo(field.getCampo());
	}
	
}