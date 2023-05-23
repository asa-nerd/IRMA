//  ------------------------------------------------------------
//
//  irmaANALYSIS V 0.9) 
//  Andreas Pirchner, 2020-2023
//
//  ------------------------------------------------------------

package meadisAnalysis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IrmaANALYSIS extends Application{
	
	private static Stage pStage;
    
	public static void main(String[] args){
		launch(args);
	}
		
	@Override
	public void start(Stage primaryStage){
		
		primaryStage.setTitle("IRMAanalysis 0.9");
		primaryStage.initStyle(StageStyle.DECORATED);
		
		Sample sample = new Sample();
		GUI gui = new GUI(primaryStage, sample);
		VBox root = gui.getLayout();
		Scene scene = new Scene(root, 1400, 900);
		scene.getStylesheets().add("/Ressources/stylesheet.css");
		primaryStage.setScene(scene);
		pStage = primaryStage;
		primaryStage.show();
	}
	
	public static Stage getPrimaryStage() {
        return pStage;
    }
	
}