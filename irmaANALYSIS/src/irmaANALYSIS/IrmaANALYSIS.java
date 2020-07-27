//  ------------------------------------------------------------

//  irmaANALYSIS V 0.2
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

/*

Point2D: http://geom-java.sourceforge.net/api/index.html


*/

package irmaANALYSIS;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IrmaANALYSIS extends Application{
	
	private static Stage pStage;
	
	
	private double xOffset = 0;
    private double yOffset = 0;
    
	public static void main(String[] args){
		launch(args);
	}
		
	@Override
	public void start(Stage primaryStage){
		//System.setProperty("apple.laf.useScreenMenuBar", "true");				// on OSX use Menu on top menu bar
		
		primaryStage.setTitle("irmaAnalysis");
		//primaryStage.initStyle(StageStyle.TRANSPARENT);
		
		Sample sample = new Sample();
		GUI gui = new GUI(primaryStage, sample);
		
		VBox root = gui.getLayout();
		Scene scene = new Scene(root, 1400, 900);
		
		/*root.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
	        
	        //move around here
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	primaryStage.setX(event.getScreenX() - xOffset);
	            	primaryStage.setY(event.getScreenY() - yOffset);
	            }
	        });*/
		
		scene.getStylesheets().add("/Ressources/stylesheet.css");
		primaryStage.setScene(scene);
		pStage = primaryStage;
		primaryStage.show();
	}
	
	public static Stage getPrimaryStage() {
        return pStage;
    }
	
	private void setPrimaryStage(Stage pStage) {
		IrmaANALYSIS.pStage = pStage;
    }
}