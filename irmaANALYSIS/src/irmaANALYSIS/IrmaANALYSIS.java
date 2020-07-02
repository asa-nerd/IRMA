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
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

//import javax.swing.JFrame;
//import java.awt.Graphics;
//import java.awt.Color;

import javafx.stage.Stage;


public class IrmaANALYSIS extends Application{

	Button button;
	
	public static void main(String[] args){
		launch(args);
	}
		
	@Override
	public void start(Stage primaryStage) throws Exception{
		//System.setProperty("apple.laf.useScreenMenuBar", "true");				// on OSX use Menu on top menu bar
		
		
		/*JSONParser parser = new JSONParser();									// pre-load sample data
		Sample s = new Sample();
		for (int i = 2; i < 4; i++) {
			try (Reader reader = new FileReader("data/ConcertTympanic_"+i+".json")) {
				JSONArray jsonData = (JSONArray) parser.parse(reader);
				Subject newSubject = new Subject(jsonData);
				s.addSubject(newSubject);            
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Visualizer v = new Visualizer (s);										// initialize visualizer 
		v.drawTimeline(0, 400);													// draw first timeline
		*/
		primaryStage.setTitle("irmaAnalysis");

		GUI gui = new GUI();
        MenuBarFX mbfx = new MenuBarFX(primaryStage);
        
        gui.rootLayout.setTop(mbfx.getMainMenu());

		Scene scene = new Scene(gui.rootLayout, 1200, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}