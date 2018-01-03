package tesi.dataQuality;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tesi.dataQuality.view.RootLayoutController;

/**
 * Classe che rappresenta il punto di partenza del sistema
 * @author PC-Simone
 *
 */
public class MainApp extends Application {
	
    private Stage primaryStage;
    private BorderPane rootLayout;

	@Override
	public void start(Stage primarySt) throws Exception {
		this.primaryStage=primarySt;
		this.primaryStage.setTitle("DataQualityChecker");
		this.primaryStage.setResizable(false);

		//this.primaryStage.getIcons().add(new Image("file:resources/images/Education.png"));

		initRootLayout();

		showRequest();
	}

    public void showRequest() {
        try {
            // Carica la schermata di scelta
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Settings.fxml"));
            AnchorPane request = (AnchorPane) loader.load();

            // Setta al centro del root layout la schermata di scelta
            rootLayout.setCenter(request);

            // Give the controller access to the main app
            //  SettingsController controller=loader.getController();
            //controller.setMainApp(this);
        } catch (IOException e) {
        	System.out.println("IO Error");
        }
    }

	/**
	 * Inizializza il root layout
	 */
	public void initRootLayout() {
	    try {
	        //carica il layout da RootLayout.fxml
	        FXMLLoader loader = new FXMLLoader();
	        
	        loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
	        rootLayout = (BorderPane) loader.load();

	        // mostra il root layout.
	        Scene scene = new Scene(rootLayout);
	        primaryStage.setScene(scene);

	        // da al controller accesso all'app
	        RootLayoutController controller = loader.getController();

	        primaryStage.show();
	    } catch (IOException e) {
	    	System.out.println("IO Error");
	    }

	}

	public static void main(String[] args) {
		launch(args);
	}
}
