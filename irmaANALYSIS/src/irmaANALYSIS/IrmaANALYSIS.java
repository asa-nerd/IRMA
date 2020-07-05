//  ------------------------------------------------------------

//  irmaANALYSIS V 0.2
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

/*

Point2D: http://geom-java.sourceforge.net/api/index.html


*/

package irmaANALYSIS;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;


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
		
		//JMetro jMetro = new JMetro(Style.LIGHT);
		//jMetro.setScene(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}