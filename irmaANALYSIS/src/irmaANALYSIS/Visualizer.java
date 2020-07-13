package irmaANALYSIS;
//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Class to Visualize sample data
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import math.geom2d.Point2D;


import irmaANALYSIS.GUI;

public class Visualizer {
	Sample s;
	ScrollPane sc;
	GraphicsContext gc1, gc2;
	double  zoomFactor = 4;
	VBox moduleContainer; 
	HBox guiContainer;
	StackPane timelineContainer;
	Pane timelineCanvas;
	Pane playbackCanvas;
	
	double stepSize = zoomFactor*2;
	
	Line playbackLine;
	Slider zoomXSlider;
	
	ArrayList<Line> lines;
	
	
	Visualizer (Sample _s){
		s = _s;
		sc = new ScrollPane();
			moduleContainer = new VBox();
				guiContainer = new HBox();
				timelineContainer = new StackPane();
					timelineCanvas = new Pane();
					playbackCanvas = new Pane();
		sc.setMinSize(1200, 400);
		sc.setMaxSize(1200, 400);
		sc.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        playbackLine = new Line(0*stepSize, 0, 0*stepSize, 400);
        playbackLine.setStroke(Color.RED);
        
        zoomXSlider = new Slider(0.1,10,1);
     // Adding Listener to value property. 
        zoomXSlider.valueProperty().addListener(new ChangeListener<Number>() { 
  
	            public void changed(ObservableValue <? extends Number >  
	                      observable, Number oldValue, Number newValue){ 
	            	zoomFactor = (Double) newValue;
	            	stepSize = zoomFactor*2;
	            	updateTimeline();
	            } 
        }); 
        guiContainer.getChildren().add(zoomXSlider);
        timelineContainer.getChildren().addAll(timelineCanvas, playbackCanvas);
        
        moduleContainer.getChildren().addAll(guiContainer, timelineContainer);
        sc.setContent(moduleContainer);
        GUI.rootLayout.setBottom(sc);
        
        lines = new ArrayList<Line>();
	}
	
	
	public Pane getTimelinePane() {
		return timelineCanvas;
	}
	
	
	public void drawPlaybackPosition(double _t) {
		playbackLine.setStartX(_t*stepSize);
		playbackLine.setStartY(0);
		playbackLine.setEndX(_t*stepSize);
		playbackLine.setEndY(400);
	}
	
	public void updateTimeline() {
		//int lineListLength = timelineCanvas.getChildren().size();
		
		for (int i = 0; i < lines.size(); i++) {
			Line l = lines.get(i);
			l.setStartX(i*stepSize+0.5);
			l.setEndX(i*stepSize+0.5);
		}
		timelineCanvas.setPrefSize(lines.size()*zoomFactor*2,400);
	}
	
	public void drawTimeline(int _begin, int _end) {			// function to draw the standard timeline
		
		playbackCanvas.getChildren().add(playbackLine);
		
		
		
		//stackedCanvas.setStyle("-fx-background-color: black;");
		timelineCanvas.setPrefSize(1200,400);
	    int rangeLength = _end - _begin;
	    stepSize = zoomFactor*2;
	    timelineCanvas.setPrefWidth(rangeLength * stepSize);
		
	    
		double originX =  200;

		for (int i = _begin; i < _end; i = i + 1) {
			double lHeight = s.getDOA(i)*400;					// get line height as current Deviation of Attention
			Point2D currentAFA = s.getAFA(i);					// get Vector of current Average Focus of Attention
			//float[] c = this.getColor(currentAFA);				// get color of current AFA
			//p.println(c[0]+","+ c[1]+","+ c[2]);
			//p.stroke(c[0], c[1], c[2]);							// set color for line
			Line line = new Line(i*stepSize+0.5, originX+lHeight, i*stepSize+0.5, originX-lHeight); // make lines
			line.setStroke(Color.BLACK);
			line.setStrokeWidth(1*zoomFactor);
			lines.add(line);
		}		
		timelineCanvas.getChildren().addAll(lines);				// add all line Nodes to parent Pane
		timelineCanvas.setPrefSize(rangeLength*zoomFactor*2,400);
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
