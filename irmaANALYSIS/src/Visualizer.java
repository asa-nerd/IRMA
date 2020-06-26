//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Class to Visualize sample data
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

import processing.core.*;
import processing.data.*;
import java.util.ArrayList;

public class Visualizer {
	Sample s;
	PApplet p;
	
	Visualizer (Sample _s, PApplet _p){
		s = _s;
		p = _p;
	}
	
	public void drawTimeline(int _begin, int _end) {			// function to draw the standard timeline
		
		for (int i = _begin; i < _end; i = i + 1) {
			float lHeight = s.getDOA(i)*400;						// get line height as current Deviation of Attention
			PVector currentAFA = s.getAFA(i);					// get Vector of current Average Focus of Attention
			
			float[] c = this.getColor(currentAFA);				// get color of current AFA
			//p.println(c[0]+","+ c[1]+","+ c[2]);
			p.stroke(c[0], c[1], c[2]);							// set color for line
			p.line(i, 200, i, 200-lHeight);					// draw lines
			p.line(i, 200, i, 200+lHeight);
		}		
	}
	
	public float[] getColor(PVector _p) {
		float[] rgb = {0,0,0};
		PVector topCorner = new PVector(0.0f, -0.577350269189626f);
		PVector rightCorner = new PVector(0.5f, 0.288675134594813f);
		PVector leftCorner = new PVector(-0.5f, 0.288675134594813f);
		PVector currentPoint = _p;
		float propRed = this.distancePointLine(currentPoint, leftCorner, topCorner); 	// get red
		rgb[0] = p.map(propRed, 0.0f, 0.88f, 0.0f, (float) 255*1); 						// map Value and save it to rgb array
		float propGreen = this.distancePointLine(currentPoint, topCorner, rightCorner); // get green
	    rgb[1] = p.map(propGreen, 0f, 0.88f, 0f, (float) 255*1); 						// map Value and save it to rgb array
		float propBlue = this.distancePointLine(currentPoint, rightCorner, leftCorner);	// get blue
	    rgb[2] = p.map(propBlue, 0f, 0.88f, 0f, (float) 255*1); 						// map Value and save it to rgb array				
		return rgb;
		
	}
	
	public float distancePointLine(PVector _pointVector, PVector _lineVector1, PVector _lineVector2) {
	    PVector PV = _pointVector;
	    PVector LV1 = _lineVector1;
	    PVector LV2 = _lineVector2;
		
	    PVector slope = LV2.sub(LV1); 								// slope of line
	    float lineLengthi = slope.x * slope.x + slope.y * slope.y;  // squared length of line;
		PVector s = new PVector(PV.x - LV1.x, PV.y - LV1.y);
		float ti = s.dot(slope) / lineLengthi;
		PVector p = new PVector(slope.x * ti, slope.y * ti);		// crawl the line acoording to its slope to distance t
		PVector projectionOnLine = LV1.add(p);						// add the starting coordinates
		PVector subber = projectionOnLine.sub(PV);				// now calculate the distance of the measuring point to the projected point on the line
		float dist = (float) Math.sqrt(subber.x * subber.x + subber.y * subber.y);
		
		return dist;
	}

}
