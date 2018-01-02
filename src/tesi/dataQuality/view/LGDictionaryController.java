package tesi.dataQuality.view;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import tesi.dataQuality.LanguageTool.WordsUtilsLT;
import tesi.dataQuality.model.Parola;


public class LGDictionaryController {
	
	private ObservableList<Parola> words=FXCollections.observableArrayList();
	
	public LGDictionaryController(){
		words.addAll(WordsUtilsLT.getToIgnoreShow());
	}
	
	@FXML
	private Button elimina;
	@FXML
	private Button eliminaTT;
	@FXML
	private Button aggiungi;
	@FXML
	private Button fine;
	@FXML
	private TextField toAddWord;
	@FXML
	private RadioButton temp;
	@FXML
	private RadioButton def;
	@FXML
	private TableColumn<Parola, String> wordColumn;
	@FXML
	private TableView<Parola> wordTable;

	private RootLayoutController main;	

	private Stage dialogSt;
	
	@FXML
	private void initialize() {
		wordColumn.setCellValueFactory(cellData -> cellData.getValue().wordProperty());
		wordTable.setItems(words);
	}
	
	@FXML
	public void gestoreDeleteAll() {
		wordTable.getItems().removeAll(WordsUtilsLT.getToIgnoreShow());
		WordsUtilsLT.deleteAllPermanently();
		WordsUtilsLT.deleteAllTemporarily();
	}
	
	@FXML
	public void gestoreAdd() {
    	if(!toAddWord.getText().trim().equals("")&&toAddWord.getText()!=null){
    		Parola word = new Parola(toAddWord.getText());
    		words.add(word);
    		if(temp.isSelected()) {
    			WordsUtilsLT.addWordTemporarily(toAddWord.getText());
    		}
    		else {
    			WordsUtilsLT.addWordPermanently(toAddWord.getText());
    		}
    	}
    	else {
    		Alert alert=new Alert(AlertType.WARNING);
    		alert.setTitle("Nessuna selezione!");
    		alert.setHeaderText("Nessuna parola inserita!");
    		alert.setContentText("Inserire una parola da inserire nella lista delle eccezioni.");

    		alert.showAndWait();
    	}
		
	}
	
	
	@FXML
	public void gestoreFatto() {
		dialogSt.close();
	}
	
    @FXML
    public void gestoreDelete() throws IOException, SQLException{
    	int index=wordTable.getSelectionModel().getSelectedIndex();
    	if(index>=0){
    		Parola word=wordTable.getItems().remove(index);
    		WordsUtilsLT.deleteWordPermanently(word.getWord());
    		WordsUtilsLT.deleteWordTemporarily(word.getWord());
    	}
    	else{
    		//nessuna selezione
    		Alert alert=new Alert(AlertType.WARNING);
    		alert.setTitle("Nessuna selezione!");
    		alert.setHeaderText("Nessuna parola selezionata");
    		alert.setContentText("Selezionare una parola in tabella.");

    		alert.showAndWait();
    	}

    }

    public void setDialogStage(Stage dialogSt) {
        this.dialogSt = dialogSt;
    }

    public void setMainApp(RootLayoutController main) {
        this.main= main;
    }

    public RootLayoutController getMainApp(){
    	return main;
    }
	
}
