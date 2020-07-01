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
		
		//JFrame MainFrame = new JFrame("irmaANALYSIS V1.0");						// Main window
		//MenuBar b = new MenuBar(MainFrame);										// make the top menu bar					
		
		//MainFrame.setSize(1200, 800);
		//MainFrame.setLocationRelativeTo(null);
		//MainFrame.setVisible(true);
		
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
		button = new Button();
		button.setText("Load");
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		
		// create a menu 
        //Menu m = new Menu("Menu"); 
  
        // create menuitems 
        
  
        // add menu items to menu 
       
  
        // create a menubar 
        //MenuBar mb = new MenuBar(); 
        //mb.useSystemMenuBarProperty().set(true);
        
        // add menu to menubar 
        MenuBarFX mbfx = new MenuBarFX();
        //MenuBar mb = new MenuBar();
       // mb = mbfx.getMainMenu(); 
        		
        //VBox vb = new VBox(mb);
		Scene scene = new Scene(mbfx.getMainMenu(), 1200, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}