//  ------------------------------------------------------------

//  irmaANALYSIS V 0.2
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

/*

Point2D: http://geom-java.sourceforge.net/api/index.html


*/

package irmaANALYSIS;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IrmaANALYSIS extends Application{
	
	
	public static void main(String[] args){
		launch(args);
	}
		
	@Override
	public void start(Stage primaryStage){
		//System.setProperty("apple.laf.useScreenMenuBar", "true");				// on OSX use Menu on top menu bar
		
		primaryStage.setTitle("irmaAnalysis");
		Sample sample = new Sample();
		GUI gui = new GUI(primaryStage, sample);
		Scene scene = new Scene(gui.getLayout(), 1200, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}