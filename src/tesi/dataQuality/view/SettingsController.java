package tesi.dataQuality.view;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tesi.dataQuality.MainApp;
import tesi.dataQuality.DAO.MySqlDao;
import tesi.dataQuality.analyzer.AnalyzerController;
import tesi.dataQuality.model.Column;

public class SettingsController {
	
	@FXML
	private PasswordField PWMask;
	@FXML
	private TextField PW;
	@FXML
	private Line dbLine1;
	@FXML
	private Line dbLine2;
	@FXML
	private MenuButton slctDB;	
	@FXML
	private Line tableLine;
	@FXML
	private Label extTable;
	@FXML
	private ScrollPane panelTables;
	@FXML
	private AnchorPane paneTables;
	@FXML
	private Label lblDB;
	@FXML
	private GridPane grid;
	@FXML
	private GridPane table;
	@FXML
	private ScrollPane panelResults;
	@FXML
	private AnchorPane paneResults;
	@FXML
	private Button analize;
	@FXML
	private TextField userName;
	@FXML
	private ScrollPane settings;
	@FXML 
	private AnchorPane panel;
	@FXML
	private GridPane gridda;

	
	private boolean showPW=false;
	
	@FXML
	private void initialize() {
		slctDB.setDisable(true);
		analize.setDisable(true);
	}
	
	@FXML
	public void togglevisiblePassword() {
		if(!showPW) {
			PW.setText(PWMask.getText());
	        PW.setVisible(true);
	        PWMask.setVisible(false);
	        showPW=true;
		}
		else {
			PWMask.setText(PW.getText());
		    PWMask.setVisible(true);
		    PW.setVisible(false);
		    showPW=false;
		}
	}
	
	@FXML
	private void insPW() {
		slctDB.getItems().clear();
		MySqlDao.pw=PWMask.getText();
		MySqlDao.user=userName.getText();
		
		ResultSet rs=MySqlDao.getDatabases();
		
		if(MySqlDao.connected()) {
			try {
				while(rs.next()) {
					MenuItem item = new MenuItem(rs.getString(1));
					item.setOnAction(new EventHandler<ActionEvent>() {
					    @Override
					    public void handle(ActionEvent event) {
					    	slctDB.setText(item.getText());
					    	MySqlDao.db=item.getText();
					    	showTables();
					    }
					});
					slctDB.getItems().add(item);
				}
			} catch (SQLException e) {}
			
			PW.setDisable(true);
			PWMask.setDisable(true);
			dbLine1.setVisible(true);
			dbLine2.setVisible(true);
			slctDB.setVisible(true);
			slctDB.setDisable(false);
			lblDB.setVisible(true);
			
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Login failed");
	        alert.setHeaderText("Info");
	        alert.setContentText("Wrong Password!\nPlease, insert PW again.");
	        alert.showAndWait();
		}
					
	}
	
	private void showTables() {
		int rowIndex=0;
		grid.getChildren().clear();
		
		panelTables.setVisible(true);
		paneTables.setVisible(true);
		extTable.setVisible(true);
		tableLine.setVisible(true);
		
		ResultSet rs = MySqlDao.getTable();
		final ToggleGroup group = new ToggleGroup();
		
		try {
			while(rs.next()) {
				RadioButton rb = new RadioButton(rs.getString(1));

				rb.setVisible(true);
				rb.setToggleGroup(group);
				grid.addRow(rowIndex++, rb);
				
				
				
			}
			
			group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov,
			        Toggle old_toggle, Toggle new_toggle) {
			            if (group.getSelectedToggle() != null) {
			            	RadioButton selectedRadioButton =(RadioButton) group.getSelectedToggle();
			            	MySqlDao.table=selectedRadioButton.getText();
			            	setter();
			            }                
			        }
			});			
			
		} catch (SQLException e) {}

	}
	
	private void setter() {
		gridda.getChildren().clear();
		analize.setVisible(true);
		analize.setDisable(false);
				
		try {
			ResultSetMetaData rsmd=MySqlDao.getColumn();
			int column = rsmd.getColumnCount();
			Column.cleanList();
			
			int j = 0;
			for(int i = 1; i<=(column*3);i++) {				
				Column col = new Column(rsmd.getColumnLabel(i-(1*j)),i-(1*j));
				
				gridda.addRow(i,new Label(rsmd.getColumnLabel(i-(1*j))));
						
				CheckBox ACLT = new CheckBox("Language Tool");
				ACLT.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent event) {
				    	if(ACLT.isSelected()) {
				    		col.setLanguageTool(true);
				    	}
				    	else {
				    		col.setLanguageTool(false);;
				    	}
				    }
				});
				gridda.addRow(i+1, ACLT);
				
				CheckBox CCGM = new CheckBox("JaSpell");
				CCGM.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if(CCGM.isSelected()) {
							col.setJaspell(true);
						}
						else {
							col.setJaspell(false);
						}
						
					}
					
				});
				gridda.addRow(i+1, CCGM);
				
				CheckBox ACGM = new CheckBox("Google Maps");
				ACGM.setOnAction(new EventHandler<ActionEvent>() {
					@Override
				    public void handle(ActionEvent event) {
				    	if(ACGM.isSelected()) {
				    		col.setGoogleMaps(true);
				    	}
				    	else {
				    		col.setGoogleMaps(false);
				    	}
				    }
				});
				gridda.addRow(i+1, ACGM);
				
				gridda.addRow(i+2);
				gridda.add(new Label("-----------------------"), 0, i+2);
				gridda.add(new Label("-----------------------"), 1, i+2);
				gridda.add(new Label("------------------------"), 2, i+2);
				
				Column.addList(col);
				i=i+2;
				j=j+2;
			}			
			
		} catch (SQLException e) {}
		
	}
	
	@FXML
	private void gestoreAnalizza() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("analyzer/analyzer.fxml"));
			AnchorPane manipulation = (AnchorPane) loader.load();
			
			// Crea il dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Analyzer");
			dialogStage.setResizable(false);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(null);
			Scene scene = new Scene(manipulation);
			dialogStage.setScene(scene);
			
			AnalyzerController settings=loader.getController();
			
			dialogStage.showAndWait();
		} catch (IOException e) {}
		
		
	}


}
