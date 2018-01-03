package tesi.dataQuality.analyzer;

import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Italian;
import org.languagetool.rules.RuleMatch;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import checker.JaSpell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import tesi.dataQuality.DAO.MySqlDao;
import tesi.dataQuality.LanguageTool.WordsUtilsLT;
import tesi.dataQuality.model.Campo;
import tesi.dataQuality.model.Column;
import tesi.dataQuality.utility.StringUtils;

/**
 * Classe che "disegna" l'interfaccia grafica
 * e che effettua i controlli tramite i tool
 * JaSpell, GoogleMaps e LanguageTool.
 * @author PC-Simone
 *
 */
public class AnalyzerController {
	
	@FXML
	private TabPane panels;
	

	@FXML
	private void initialize() {
		
		for(Column col : Column.getList()) {
		
		col.getLTrep().reset();
		col.getJSrep().reset();
		col.getGMrep().reset();
		
		Tab panel = new Tab(col.getCampo());
		
		if(col.isLanguageTool()||col.isJaspell()||col.isGoogleMaps()) {
			panels.getTabs().add(panel);
		}
		
				
		ResultSet rs = MySqlDao.getSentences();
			
		try {
			
			col.getFields().clear();
			while(rs.next()) {
				Campo field = new Campo(rs.getString(col.getIndex()),rs.getString(1));
				field.setColumn(col.getCampo());
				
				col.addFields(field);
			}
			
			
			
			if(col.isLanguageTool()) {
				LTAnalysis(col);				
			}
							
			if(col.isJaspell()) {
				JSAnalysis(col);
			}
			
			if(col.isGoogleMaps()) {
				GMAnalysis(col);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL Error");
		}
		
		
				
		AnchorPane anc = new AnchorPane();
		anc.setMinHeight(0);
		anc.setMinWidth(0);
		anc.setPrefHeight(630);
		anc.setPrefWidth(850);
		panel.setContent(anc);
		
		Label lbl = new Label("Language Tool");
		lbl.setLayoutX(80);
		lbl.setLayoutY(28);
		anc.getChildren().add(lbl);
		
		PieChart PC = new PieChart();
		PC.setPrefHeight(370);
		PC.setPrefWidth(370);
		PC.setVisible(false);
		ObservableList list = FXCollections.observableArrayList();
		list.add(new PieChart.Data("Errate " + col.getLTrep().getWithMistakes(), col.getLTrep().getWithMistakes()));
		list.add(new PieChart.Data("Corrette " + (col.getLTrep().getAnalyzed()-col.getLTrep().getWithMistakes()), (col.getLTrep().getAnalyzed()-col.getLTrep().getWithMistakes())));
        PC.setData(list);
        PC.setLayoutX(20);
        
        NumberAxis yAxis = new NumberAxis();
        CategoryAxis xAxis = new CategoryAxis();
        BarChart<String,Number> BC = new BarChart<>(xAxis, yAxis);
		
        String[] months = new String[2];
        months[0]="Errate " + col.getLTrep().getWithMistakes();
        months[1]="Corrette " + (col.getLTrep().getAnalyzed()-col.getLTrep().getWithMistakes());
        ObservableList<String> monthNames = FXCollections.observableArrayList();
        monthNames.addAll(Arrays.asList(months));
        xAxis.setCategories(monthNames);
        
        int[] monthCounter = new int[2];
        monthCounter[0]=col.getLTrep().getWithMistakes();
		monthCounter[1]=col.getLTrep().getAnalyzed()-col.getLTrep().getWithMistakes();
        
        
        XYChart.Series series1 = new XYChart.Series();
        for (int i = 0; i < monthCounter.length; i++) {
            series1.getData().add(new XYChart.Data(monthNames.get(i), monthCounter[i]));
        }
        
        BC.getData().addAll(series1);
        
        BC.setVisible(false);
        BC.setPrefHeight(370);
        BC.setPrefWidth(370);
        
		MenuButton slctCh = new MenuButton();
		slctCh.getItems().clear();
		MenuItem item = new MenuItem("Istogramma");
		item.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	BC.setVisible(true);
		    	PC.setVisible(false);
		    	slctCh.setText(item.getText());	    	
		    }
		});
		slctCh.getItems().add(item);
		
		MenuItem item1 = new MenuItem("Torta");
		item1.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	slctCh.setText(item1.getText());
		    	BC.setVisible(false);
		    	PC.setVisible(true);
		    }
		});
		slctCh.getItems().add(item1);
		
		anc.getChildren().add(slctCh);
		slctCh.setLayoutX(200);
		slctCh.setLayoutY(25);
		
		anc.getChildren().add(BC);
		BC.setLayoutX(20);
		BC.setLayoutY(80);
		anc.getChildren().add(PC);
		PC.setLayoutY(80);
			
		Button repLT = new Button("Report");
		repLT.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	
		    	FileChooser fileChooser = new FileChooser();

		        // Imposta l'estensione del file
		        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF file (*.pdf)", "*.pdf");
		        fileChooser.getExtensionFilters().add(extFilter);

		        // Mostra la finestra di salvataggio
		        File file = fileChooser.showSaveDialog(null);
		        
		        if(file!=null) {
		        	 try {
		            	Document document = new Document();
		            	
						PdfWriter.getInstance(document, new FileOutputStream(file.getPath()));
						
						document.open();
						document.addTitle("Report LanguageTool");
						
						Paragraph stats = new Paragraph();
						stats.add(new Paragraph(" "));
						stats.add(new Paragraph(col.getLTrep().getAnalyzed()+" analyzed sentences."));
						stats.add(new Paragraph(col.getLTrep().getWithMistakes()+" sentences with mistakes."));
						stats.add(new Paragraph(col.getLTrep().getMill() +" milliseconds spent."));
						
						stats.add(new Paragraph(" "));
						stats.add(new Paragraph(" "));
						
						for(Campo sentence : col.getLTrep().getSentence()) {
							stats.add(new Paragraph(sentence.getCampo()));
							for(String str : sentence.getAllLTCorrs()) {
								stats.add(new Paragraph(str));
							}
							stats.add(new Paragraph("------------------------------------------------------------------------------"));
						}
						
						document.add(stats);
						
						document.close();
					} catch (FileNotFoundException | DocumentException e) {
						System.out.println("Error");
					}
		        	 
		        }
		    	
		    
		    }
	       
		});
		
		anc.getChildren().add(repLT);
		repLT.setLayoutX(170);
		repLT.setLayoutY(480);
	
		Line ln = new Line();
		anc.getChildren().add(ln);
		ln.setStartX(410);
		ln.setStartY(0);
		ln.setEndX(410);
		ln.setEndY(510);
		
		
		//destra
		Label lblJS = new Label("JaSpell");
		lblJS.setLayoutX(530);
		lblJS.setLayoutY(30);
		anc.getChildren().add(lblJS);
		
		PieChart PCJS = new PieChart();
		PCJS.setPrefHeight(370);
		PCJS.setPrefWidth(370);
		PCJS.setVisible(false);
		ObservableList listJS = FXCollections.observableArrayList();
		
		listJS.add(new PieChart.Data("Errate " + col.getJSrep().getWithMistakes(), col.getJSrep().getWithMistakes()));
		listJS.add(new PieChart.Data("Corrette " + (col.getJSrep().getAnalyzed()-col.getJSrep().getWithMistakes()), (col.getJSrep().getAnalyzed()-col.getJSrep().getWithMistakes())));
        PCJS.setData(listJS);
        PCJS.setLayoutX(420);
        
        NumberAxis yAxisJS = new NumberAxis();
        CategoryAxis xAxisJS = new CategoryAxis();
        BarChart<String,Number> BCJS = new BarChart<>(xAxisJS, yAxisJS);
		
        String[] monthsJS = new String[2];
        monthsJS[0]="Errate " + col.getJSrep().getWithMistakes();
        monthsJS[1]="Corrette " + (col.getJSrep().getAnalyzed()-col.getJSrep().getWithMistakes());
        ObservableList<String> monthNamesJS = FXCollections.observableArrayList();
        monthNamesJS.addAll(Arrays.asList(monthsJS));
        xAxisJS.setCategories(monthNamesJS);
        
        int[] monthCounterJS = new int[2];
        monthCounterJS[0]=col.getJSrep().getWithMistakes();
		monthCounterJS[1]=col.getJSrep().getAnalyzed()-col.getJSrep().getWithMistakes();
        
        
        XYChart.Series series1JS = new XYChart.Series();
        for (int i = 0; i < monthCounterJS.length; i++) {
            series1JS.getData().add(new XYChart.Data(monthNamesJS.get(i), monthCounterJS[i]));
        }
        
        BCJS.getData().addAll(series1JS);
        
        BCJS.setVisible(false);
        BCJS.setPrefHeight(370);
        BCJS.setPrefWidth(370);
        
        
		
		MenuButton slctChJS = new MenuButton();
		slctChJS.getItems().clear();
		MenuItem itemJS = new MenuItem("Istogramma");
		itemJS.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	BCJS.setVisible(true);
		    	PCJS.setVisible(false);
		    	slctChJS.setText(itemJS.getText());		    	
		    }
		});
		slctChJS.getItems().add(itemJS);
		
		MenuItem item1JS = new MenuItem("Torta");
		item1JS.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	slctChJS.setText(item1JS.getText());
		    	BCJS.setVisible(false);
		    	PCJS.setVisible(true);
		    }
		});
		slctChJS.getItems().add(item1JS);
		
		anc.getChildren().add(slctChJS);
		slctChJS.setLayoutX(620);
		slctChJS.setLayoutY(25);
		
		anc.getChildren().add(BCJS);
		BCJS.setLayoutX(445);
		BCJS.setLayoutY(80);
		anc.getChildren().add(PCJS);
		PCJS.setLayoutY(80);
			
		Button repJS = new Button("Report");
		repJS.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	
		    	FileChooser fileChooser = new FileChooser();

		        // Imposta l'estensione del file
		        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF file (*.pdf)", "*.pdf");
		        fileChooser.getExtensionFilters().add(extFilter);

		        // Mostra la finestra di salvataggio
		        File file = fileChooser.showSaveDialog(null);
		        
		        if(file!=null) {
		        	 try {
		            	Document document = new Document();
						PdfWriter.getInstance(document, new FileOutputStream(file.getPath()));
						
						document.open();
						document.addTitle("Report JaSpell");
						
						Paragraph stats = new Paragraph();
						stats.add(new Paragraph(" "));
						stats.add(new Paragraph(col.getJSrep().getAnalyzed()+" analyzed sentences."));
						stats.add(new Paragraph(col.getJSrep().getWithMistakes()+" sentences with mistakes."));
						stats.add(new Paragraph(col.getJSrep().getMill() +" milliseconds spent."));
						
						stats.add(new Paragraph(" "));
						stats.add(new Paragraph(" "));
						
						for(Campo sentence : col.getJSrep().getSentence()) {
							stats.add(new Paragraph(sentence.getCampo()));
							
							stats.add(new Paragraph("Possibile correzione:"));
							
							stats.add(new Paragraph(sentence.getJSCorrection()));
							
							stats.add(new Paragraph("------------------------------------------------------------------------------"));
						}
						
						document.add(stats);
						
						document.close();
					} catch (FileNotFoundException | DocumentException e) {
						System.out.println("Error");
					}
		        }	
		    }
	       
		});
		
		anc.getChildren().add(repJS);
		repJS.setLayoutX(600);
		repJS.setLayoutY(480);
		
		Label lblGM = new Label("Analisi degli indirizzi disponibile per il download --> ");
		anc.getChildren().add(lblGM);
		lblGM.setLayoutX(200);
		lblGM.setLayoutY(535);
		Button repGM = new Button("Report");
		anc.getChildren().add(repGM);
		repGM.setLayoutX(550);
		repGM.setLayoutY(531);
		repGM.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	FileChooser fileChooser = new FileChooser();

		        // Imposta l'estensione del file
		        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF file (*.pdf)", "*.pdf");
		        fileChooser.getExtensionFilters().add(extFilter);

		        // Mostra la finestra di salvataggio
		        File file = fileChooser.showSaveDialog(null);
		        
		        if(file!=null) {
		        	 try {
		            	Document document = new Document();
						PdfWriter.getInstance(document, new FileOutputStream(file.getPath()));
						
						document.open();
						document.addTitle("Report GoogleMaps");
						
						Paragraph stats = new Paragraph();
						stats.add(new Paragraph(" "));
						stats.add(new Paragraph(col.getGMrep().getAnalyzed()+" analyzed adresses."));
						stats.add(new Paragraph(col.getGMrep().getMill() +" milliseconds spent."));
						
						stats.add(new Paragraph(" "));
						stats.add(new Paragraph(" "));
						
						for(Campo sentence : col.getGMrep().getSentence()) {
							stats.add(new Paragraph(sentence.getCampo()));
							
							stats.add(new Paragraph(sentence.getGMCorrection()));
							
							stats.add(new Paragraph("------------------------------------------------------------------------------"));
						}
						
						document.add(stats);
						
						document.close();
					} catch (FileNotFoundException | DocumentException e) {
						System.out.println("Error");
					}
		        }
				    	
		    }
		});
		
		if(!col.isLanguageTool()) {
			slctCh.setDisable(true);
			repLT.setDisable(true);
		}
		else {
			slctCh.setDisable(false);
			repLT.setDisable(false);
			
		}
		
		if(!col.isJaspell()) {
			slctChJS.setDisable(true);
			repJS.setDisable(true);
		}
		else {
			slctChJS.setDisable(false);
			repJS.setDisable(false);
		}

		if(!col.isGoogleMaps()) {
			lblGM.setText("Non è stata effettuata un'analisi sugli indirizzi");
			lblGM.setLayoutX(270);
			repGM.setVisible(false);
		}
		else {
			lblGM.setText("Analisi degli indirizzi disponibile per il download -->");
			lblGM.setLayoutX(200);
			repGM.setVisible(true);
		
		}
		
		}
				
	}

	private void GMAnalysis(Column col) {

		try {
			col.getGMrep().reset();
			col.getGMrep().setInizio();
			for(Campo field : col.getFields()){
				col.getGMrep().incrAnalyzed();
				String toCheck = new String(field.getCampo());
				final Geocoder geocoder = new Geocoder();
				
				GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(toCheck).setLanguage("it").getGeocoderRequest(); 
				GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
				List<GeocoderResult> results = geocoderResponse.getResults();
				if(results.size()==0) {
					field.setGMCorrection("Indirizzo inesistente");
				}
				else {
					field.setGMCorrection(results.get(0).getFormattedAddress());
					field.setLat(results.get(0).getGeometry().getLocation().getLat().floatValue());
					field.setLon(results.get(0).getGeometry().getLocation().getLng().floatValue());
					
				}
				col.getGMrep().addSentence(field);
			}
			
			col.getGMrep().setFine();
			col.getGMrep().addTime();
		} catch (IOException e) {
			System.out.println("IO Error");
		}
		
		
	}

	private void JSAnalysis(Column col) {
		
		try {
			col.getJSrep().reset();
			col.getJSrep().setInizio();
			for(Campo field : col.getFields()){
				boolean mistaken=false;
				col.getJSrep().incrAnalyzed();
				String toCheck=StringUtils.normalize(new String(field.getCampo()));
					
				String correction="";
				correction = JaSpell.check("-c/"+toCheck+"/--lang=it");
				
				if(!correction.equals(toCheck)) {
					field.setJSCorrection("Possibile correzione:");
					field.setJSCorrection(correction);
					mistaken=true;
				}
							
				if(mistaken) {
					col.getJSrep().incrWithMistakes();
					col.getJSrep().addSentence(field);
								
				}
			}
			
			col.getJSrep().setFine();
			col.getJSrep().addTime();
		} catch (Exception e) {
			System.out.println("Error");
		}
	
	}
	
	private void LTAnalysis(Column col) {
		try {
			col.getLTrep().reset();
			col.getLTrep().setInizio();
			for(Campo field : col.getFields()){
				boolean mistaken=false;
				col.getLTrep().incrAnalyzed();
				String toCheck=StringUtils.normalize(new String(field.getCampo()));
				
				JLanguageTool langToolIT = new JLanguageTool(new Italian());
				
				langToolIT.disableRule("WHITESPACE_PUNCTUATION");
				//langToolIT.disableRule("COMMA_PARENTHESIS_WHITESPACE");
				langToolIT.disableRule("DOUBLE_PUNCTUATION");
				langToolIT.disableRule("UNPAIRED_BRACKETS");
				langToolIT.disableRule("UPPERCASE_SENTENCE_START");
				langToolIT.disableRule("ITALIAN_WORD_REPEAT_RULE");
				langToolIT.disableRule("WHITESPACE_RULE");
				langToolIT.disableRule("ARTICOLATA_SOSTANTIVO");
				langToolIT.disableRule("CONCORDANZA_PREPOSIZIONE");
				langToolIT.disableRule("GR_01_001");
				langToolIT.disableRule("GR_02_001");
				langToolIT.disableRule("GR_04_001");
				langToolIT.disableRule("GR_04_002");
				langToolIT.disableRule("GR_05_002");
				langToolIT.disableRule("GR_07_001");
				langToolIT.disableRule("GR_09_001");
				langToolIT.disableRule("GR_09_002");
				langToolIT.disableRule("GR_09_003");
				langToolIT.disableRule("GR_09_004");
				langToolIT.disableRule("DOPPIO_VERBO_AUSILIARE_TERZA_PERSONA");
				langToolIT.disableRule("GR_10_001");
				langToolIT.disableRule("GR_10_002");
				langToolIT.disableRule("GR_10_003");
				langToolIT.disableRule("ST_01_002");
				langToolIT.disableRule("ST_01_003");
				langToolIT.disableRule("ST_01_004");
				langToolIT.disableRule("ST_01_005");
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
				langToolIT.disableRule("ER_02_003");
				langToolIT.disableRule("ER_02_004");
				langToolIT.disableRule("ER_01_003");
				langToolIT.disableRule("ER_02_005");
				
					List<RuleMatch> matches = langToolIT.check(toCheck);
					for (RuleMatch match : matches) {
						  if(match.getRule().getId().equals("MORFOLOGIK_RULE_IT_IT")) {
							  if(!WordsUtilsLT.checkToIgnore(toCheck.substring(match.getFromPos(), match.getToPos()))) {
								  field.setCorrection("Errore potenziale ai caratteri " + match.getFromPos() + "-" + match.getToPos() + ": " +  match.getMessage());
								  field.setCorrection("Correzione(i) suggerita(e): " + match.getSuggestedReplacements());
								  mistaken=true;
								  field.addMist(match.getFromPos(), match.getToPos());
							  }
						  }
						  else {
							  field.setColumn("Errore potenziale ai caratteri " + match.getFromPos() + "-" + match.getToPos() + ": " +  match.getMessage());
							  field.setColumn("Correzione(i) suggerita(e): " + match.getSuggestedReplacements());
							  mistaken=true;
						  }
						  						  					  
						}
				
				if(mistaken) {
					col.getLTrep().incrWithMistakes();
					col.getLTrep().addSentence(field);
				}
				
			}
			
			col.getLTrep().setFine();
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	
	
}
