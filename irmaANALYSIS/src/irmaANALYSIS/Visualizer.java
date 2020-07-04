package irmaANALYSIS;
//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Class to Visualize sample data
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import math.geom2d.Point2D;

import irmaANALYSIS.GUI;

public class Visualizer {
	Sample s;
	ScrollPane sc;
	Canvas layer1, layer2;
	GraphicsContext gc1, gc2;
	double canvasHeight;
	Pane canvasContainer; 
	
	Visualizer (Sample _s){
		s = _s;
		sc = new ScrollPane();
		canvasContainer = new Pane();
		sc.setMinSize(1200, 300);
		sc.setMaxSize(1200, 300);
		sc.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		layer1 = new Canvas(1000, 400);
		layer2 = new Canvas(1000, 400);
        gc1 = layer1.getGraphicsContext2D();
        gc2 = layer2.getGraphicsContext2D();
        canvasHeight = 400;
        canvasContainer.getChildren().addAll(layer1,layer2);
        sc.setContent(canvasContainer);
        GUI.rootLayout.setBottom(sc);
	}
	
	
	public void clearCanvas() {
		gc2.clearRect(0, 0, layer2.getWidth(), layer2.getHeight());
	}
	
	public void drawPlaybackPosition(double _t) {
		gc2.setStroke(Color.RED);
		gc2.strokeLine(_t, 0, _t, 400);
	}
	
	public void drawTimeline(int _begin, int _end) {			// function to draw the standard timeline
		
		double originX = canvasHeight - 200;

		for (int i = _begin; i < _end; i = i + 1) {
			double lHeight = s.getDOA(i)*400;					// get line height as current Deviation of Attention
			Point2D currentAFA = s.getAFA(i);					// get Vector of current Average Focus of Attention
			gc1.setStroke(Color.BLUE);
			gc1.setLineWidth(1);
			//float[] c = this.getColor(currentAFA);				// get color of current AFA
			//p.println(c[0]+","+ c[1]+","+ c[2]);
			//p.stroke(c[0], c[1], c[2]);							// set color for line
			gc1.strokeLine(i*2+0.5, originX, i*2+0.5, originX-lHeight);						// draw lines
			gc1.strokeLine(i*2+0.5, originX, i*2+0.5, originX+lHeight);
		}		
		//canvasHeight +=400;
		//canvas.setHeight(canvasHeight);
	}
	/*
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
	*/

}
