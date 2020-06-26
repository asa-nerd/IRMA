//  ------------------------------------------------------------
//  irmaANALYSIS V 0.2
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;
import processing.core.*;
import processing.data.*;
import processing.video.*;
import processing.awt.*;
import controlP5.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;



public class IrmaANALYSIS extends PApplet implements PConstants{
	
	Sample s;
	Visualizer v;
	//Menu_bar menuBar;
	
	public void settings(){
		size(1000, 600);
		
		//buildMenuBar();
		//menuBar = new Menu_bar(this, "Media", 300,300);
		
		s = new Sample();
		
		for (int i = 1; i <=10; i++) {
			JSONArray values = loadJSONArray("./data/ConcertTympanic_"+i+".json");
			s.addSubject(new Subject(values, "filename", this));
		}
		println(s.SubjectsList.size());
	    println(s.getSubject(0).totalActivity);
	    v = new Visualizer(s, this);
	}
	
	public void draw(){
		background(64);
		//ellipse(mouseX, mouseY, 50, 50);
	}
	
	void buildMenuBar() {
		menuBar = new Menu_bar(this, "Media", 100, 100);
		}
	
	public static void main(String[] args){
		String[] processingArgs = {"irmaANALYSIS"};
		IrmaANALYSIS p = new IrmaANALYSIS();
		PApplet.runSketch(processingArgs, p);	
	}
}