package tesi.dataQuality.model;

import java.util.TreeSet;

/**
 * Classe model per il salvataggio di tutti i
 * dati necessari per la creazione del report
 * @author PC-Simone
 *
 */
public class Report {
	
	private int analyzed=0;
	private int withMistakes=0;
	private TreeSet<Campo> wrongSentences = new TreeSet<Campo>();
	private long inizio=0;
	private long fine=0;
	private long totalTime=0;
	
	public Report() {
		
	}
	
	public void reset() {
		this.analyzed=0;
		this.withMistakes=0;
		this.wrongSentences.clear();
		this.inizio=0;
		this.fine=0;
		this.totalTime=0;
	}
	
	public long getTotalTime() {
		return totalTime;
	}
	
	public void addTime() {
		totalTime=totalTime+getMill();
	}
	
	public long getMill() {
		return fine-inizio;
	}
	
	public void setInizio() {
		inizio=getTime();
	}
	
	public void setFine() {
		fine=getTime();
	}
	
	public long getTime() {
		return System.currentTimeMillis();
	}
	
	public void incrAnalyzed() {
		analyzed++;
	}
	
	public int getAnalyzed() {
		return analyzed;
	}
	
	public void incrWithMistakes() {
		withMistakes++;
	}
	
	public int getWithMistakes() {
		return withMistakes;
	}
	
	public void addSentence(Campo field) {
		wrongSentences.add(field);
	}
	
	public TreeSet<Campo> getSentence() {
		return wrongSentences;
	}

}
