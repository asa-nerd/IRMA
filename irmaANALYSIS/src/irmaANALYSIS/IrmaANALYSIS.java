//  ------------------------------------------------------------

//  irmaANALYSIS V 0.2
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

/*

Point2D: http://geom-java.sourceforge.net/api/index.html


*/

package irmaANALYSIS;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

//import javax.swing.JFrame;
//import java.awt.Graphics;
//import java.awt.Color;

import javafx.stage.Stage;
import javafx.scene.*;
import javafx.event.*;
import javafx.application.*;

public class IrmaANALYSIS extends Application{

	
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
		System.out.println("HI");
	}
}