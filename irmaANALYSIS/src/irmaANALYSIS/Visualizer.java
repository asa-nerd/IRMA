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
			System.out.println(currentAFA.x()+" ,"+ currentAFA.y());
			double[] c = this.getColor(currentAFA);				// get color of current AFA
			Line line = new Line(i*stepSize+0.5, originX+lHeight, i*stepSize+0.5, originX-lHeight); // make lines
			line.setStroke(Color.rgb((int) c[0], (int) c[1], (int) c[2]));	// set color for line
			line.setStrokeWidth(1*zoomFactor);
			lines.add(line);
		}		
		timelineCanvas.getChildren().addAll(lines);				// add all line Nodes to parent Pane
		timelineCanvas.setPrefSize(rangeLength*zoomFactor*2,400);
	}
	
	public double[] getColor(Point2D _p) {
		double[] rgb = {0,0,0};
		Point2D topCorner = new Point2D(0.0, -0.577350269189626);
		Point2D rightCorner = new Point2D(0.5, 0.288675134594813);
		Point2D leftCorner = new Point2D(-0.5, 0.288675134594813);
		Point2D currentPoint = _p;
		double propRed = this.distancePointLine(currentPoint, leftCorner, topCorner); 		// get red
		rgb[0] = map(propRed, 0.0, 0.88, 0.0, 255); 										// map Value and save it to rgb array
		double propGreen = this.distancePointLine(currentPoint, topCorner, rightCorner); 	// get green
	    rgb[1] = map(propGreen, 0, 0.88, 0, 255); 											// map Value and save it to rgb array
		double propBlue = this.distancePointLine(currentPoint, rightCorner, leftCorner);	// get blue
	    rgb[2] = map(propBlue, 0, 0.88, 0, 255); 											// map Value and save it to rgb array				
		return rgb;
		
	}
	
	public double distancePointLine(Point2D _pointVector, Point2D _lineVector1, Point2D _lineVector2) {
		//Distance = (| a*x1 + b*y1 + c |) / (sqrt( a*a + b*b))
		
		
		Point2D P = _pointVector;
		Point2D L1 = _lineVector1;
		Point2D L2 = _lineVector2;
		double dist = Math.abs((L2.y()-L1.y())*P.x()-(L2.x()-L1.x())*P.y()+L2.x()*L1.y()-L2.y()*L1.x()) / Math.sqrt(Math.sqrt((L2.y()-L1.y())+Math.sqrt(L2.x()-L1.x())));
		
	   /* //PVector slope = LV2.sub(LV1); 								// slope of line
	    Point2D slope = new Point2D (LV2.x() - LV1.x(), LV2.y() - LV1.y());
	    Point2D s = new Point2D(PV.x() - LV1.x(), PV.y() - LV1.y());
		double ti = (s.x()* slope.x()+ s.y()*slope.y())/ 
	    float ti = s.dot(slope) / lineLengthi;
		Point2D p = new Point2D(slope.x() * ti, slope.y() * ti);		// crawl the line acoording to its slope to distance t
		//PVector projectionOnLine = LV1.add(p);						// add the starting coordinates
		Point2D projectionOnLine = new Point2D(LV1.x()+p.x(), LV1.y()+p.y());
		//PVector subber = projectionOnLine.sub(PV);				// now calculate the distance of the measuring point to the projected point on the line
		Point2D subber = new Point2D(projectionOnLine.x()- PV.x(), projectionOnLine.y()- PV.y());
		double dist = (float) Math.sqrt(subber.x() * subber.x() + subber.y() * subber.y());
		*/
		return dist;
	}
	public double map(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
		}
	

}
