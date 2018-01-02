package tesi.dataQuality.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tesi.dataQuality.MainApp;

public class RootLayoutController {
	
	@FXML
	private BorderPane border;

    /**
     * Chiude l'applicazione.
     */
    @FXML
    private void gestoreExit() {
        System.exit(0);
    }
    
    @FXML
    private void gestoreDZLT() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/LGDictionary.fxml"));
			AnchorPane manipulation;
			manipulation = (AnchorPane) loader.load();
			
			// Crea il dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Eccezioni LanguageTool");
			dialogStage.setResizable(false);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(null);
			Scene scene = new Scene(manipulation);
			dialogStage.setScene(scene);

			LGDictionaryController dict=loader.getController();
			dict.setDialogStage(dialogStage);
			dict.setMainApp(this);

			// Visualizza la schermata di login
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
        
    }
    
    
    @FXML
    private void gestoreDZJS() {
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/JSDictionary.fxml"));
			AnchorPane manipulation;
			manipulation = (AnchorPane) loader.load();
			
			// Crea il dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Eccezioni JaSpell");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(null);
			Scene scene = new Scene(manipulation);
			dialogStage.setScene(scene);

			JSDictionaryController dict=loader.getController();
			dict.setDialogStage(dialogStage);
			dict.setMainApp(this);

			// Visualizza la schermata di login
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Apre finestra di informazioni sull'app
     */
    @FXML
    private void gestoreInfo() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Data_Quality_Prototype");
        alert.setResizable(false);
        alert.setHeaderText("Info");
        alert.setContentText("Autori:\nSimone De Nisi mat. 633496");

        alert.showAndWait();
    }

}