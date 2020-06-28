//  ------------------------------------------------------------

//  irmaANALYSIS V 0.2
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

package irmaANALYSIS;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

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
		
		JSONParser parser = new JSONParser();
		Sample s = new Sample();
		//FileReader r;
		try (Reader reader = new FileReader("data/ConcertTympanic_2.json")) {
			JSONArray jsonData = (JSONArray) parser.parse(reader);
			//System.out.println(jsonData);
			Subject newSubject = new Subject(jsonData);
			
			s.addSubject(newSubject);
			//System.out.println(jsonData);
            
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}