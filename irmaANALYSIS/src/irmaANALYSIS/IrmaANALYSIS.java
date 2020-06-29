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

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

public class IrmaANALYSIS {
	
	
	//Visualizer v;
	
	//IrmaANALYSIS(){
		//JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("./data/ConcertTympanic_1.json"));
		//Object obj;
		//obj = parser.parse(new FileReader("ConcertTympanic_1.json"));
		
		//JSONArray jsonObject = (JSONArray)obj;
	//	System.out.print(new File(".").getAbsolutePath());
		
	//}
	
	/*
	public void settings(){
		//size(1000, 600);
				
		//s = new Sample();
	
		
		for (int i = 1; i <=10; i++) {
			//JSONArray values = loadJSONArray("./data/ConcertTympanic_"+i+".json");
			//s.addSubject(new Subject(values, "filename", this));
		}
		
	    //v = new Visualizer(s, this);
	  
	}*/
	
	/*
	public void draw(){
		background(255);
		
		//v.drawTimeline(0, 1200);
		noLoop();
	}*/
	
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Demo program for JFrame");
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		JSONParser parser = new JSONParser();
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
		Visualizer v = new Visualizer (s);
		v.drawTimeline(0, 400);
		
	}
}