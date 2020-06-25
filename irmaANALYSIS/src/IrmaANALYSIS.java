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
	
	ArrayList<Subject> subjects = new ArrayList<Subject>();
	
	JSONArray values;
	
	public void settings(){
		size(500, 500);
		values = loadJSONArray("");
	    //subjects.add(new Subject(values, selection.getName(), ow));
	    subjects.add(new Subject(values, "filename", this));
	}
	

	
	public void draw(){
		ellipse(mouseX, mouseY, 50, 50);
		
	}
	
	public void mousePressed(){
		background(64);
	}
	
	public static void main(String[] args){
		String[] processingArgs = {"irmaANALYSIS"};
		IrmaANALYSIS p = new IrmaANALYSIS();
		PApplet.runSketch(processingArgs, p);
		
	}
}