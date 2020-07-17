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
import irmaANALYSIS.timelineAFA;

public class VisualizerTemporal {
	Sample s;
	ScrollPane sc;
	GraphicsContext gc1, gc2;
	
	VBox moduleContainer; 
	HBox guiContainer;
	StackPane timelineContainer;
	Pane timelineCanvas;
	Pane playbackCanvas;
	
	double  zoomFactor = 1;
	double stepSize = zoomFactor*2;
	
	Line playbackLine;
	Slider zoomXSlider;
	
	ArrayList<Line> lines;
	
	ArrayList<timelineAFA> timelines;
	
	
	VisualizerTemporal (Sample _s){
		s = _s;
		sc = new ScrollPane();
			moduleContainer = new VBox();
				guiContainer = new HBox();
				timelineContainer = new StackPane();
					timelineCanvas = new Pane();
					playbackCanvas = new Pane();
		sc.setMinSize(1300, 400);
		sc.setPrefSize(1360, 400);
		sc.setMaxSize(1400, 400);
		sc.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        playbackLine = new Line(0*stepSize, 0, 0*stepSize, 400);
        playbackLine.setStroke(Color.RED);
        
        zoomXSlider = new Slider(0.1,10,1);
      
        guiContainer.getChildren().add(zoomXSlider);
        timelineContainer.getChildren().addAll(timelineCanvas, playbackCanvas);
        
       // moduleContainer.getChildren().addAll(guiContainer, timelineContainer);

       // GUI.rootLayout.setBottom(sc);
        
        lines = new ArrayList<Line>();
        timelines = new ArrayList<timelineAFA>();
     
        zoomXSlider.valueProperty().addListener(new ChangeListener<Number>() { 		 // Adding Listener to value property.
	            public void changed(ObservableValue <? extends Number >  
	                      observable, Number oldValue, Number newValue){ 
	            	zoomFactor = (Double) newValue;
	            	stepSize = zoomFactor*2;
	            	updateTimeline();
	            } 
        });

        sc.setContent(moduleContainer);
	}
	
	
	
	public ScrollPane getTemporalContainer() {
		return sc;
	}
	
	public void makeTimelineElement(Sample _s) {
		timelineAFA newTL = new timelineAFA(_s);
		timelines.add(newTL);
		moduleContainer.getChildren().add(newTL.getTimeline());
		
	}
	
	public void movePlaybackLines(int _t) {
		for (int i = 0; i < timelines.size(); i++) {
			timelineAFA tl = timelines.get(i);
			tl.drawPlaybackPosition(_t);
			
		}
	}
	
	
	public void drawPlaybackPosition(double _t) {
		playbackLine.setStartX(_t*stepSize);
		playbackLine.setStartY(60);
		playbackLine.setEndX(_t*stepSize);
		playbackLine.setEndY(180);
	}
	
	public void updateTimeline() {
		for (int i = 0; i < lines.size(); i++) {
			Line l = lines.get(i);
			l.setStartX(i*stepSize+0.5);
			l.setEndX(i*stepSize+0.5);
			l.setStrokeWidth(1*zoomFactor);
		}
		timelineCanvas.setPrefSize(lines.size()*zoomFactor*2,400);
	}
	
	
	public void drawTimeline(int _begin, int _end) {			// function to draw the standard timeline
		
		playbackCanvas.getChildren().add(playbackLine);
		timelineCanvas.setPrefSize(1200,400);
	    int rangeLength = _end - _begin;
	    double originX =  200;
	    stepSize = zoomFactor*2;
	    timelineCanvas.setPrefWidth(rangeLength * stepSize);

		for (int i = _begin; i < _end; i = i + 1) {
			double lHeight = s.getDOA(i)*400;					// get line height as current Deviation of Attention
			Point2D currentAFA = s.getAFA(i);					// get Vector of current Average Focus of Attention
			double[] c = this.getColor(currentAFA);				// get color of current AFA
			Line line = new Line(i*stepSize+0.5, originX+lHeight, i*stepSize+0.5, originX-lHeight); // make lines
			line.setStroke(Color.rgb((int) c[0], (int) c[1], (int) c[2]));	// set color for line
			line.setStrokeWidth(1*zoomFactor);
			lines.add(line);
		}		
		timelineCanvas.getChildren().addAll(lines);				// add all line Nodes to parent Pane
		timelineCanvas.setPrefSize(rangeLength*zoomFactor*2,400);
	}
	
	public static double[] getColor(Point2D _p) {
		double[] rgb = {0,0,0};
		Point2D topCorner = new Point2D(0.0, -0.577350269189626);
		Point2D rightCorner = new Point2D(0.5, 0.288675134594813);
		Point2D leftCorner = new Point2D(-0.5, 0.288675134594813);
		Point2D currentPoint = _p;
		double propRed = distancePointLine(currentPoint, leftCorner, topCorner); 		// get red
		rgb[0] = mapValue(propRed, 0.0, 0.88/2, 0, 255); 										// map Value and save it to rgb array
		double propGreen = distancePointLine(currentPoint, topCorner, rightCorner); 	// get green
	    rgb[1] = mapValue(propGreen, 0, 0.88/2, 0, 255); 											// map Value and save it to rgb array
		double propBlue = distancePointLine(currentPoint, leftCorner, rightCorner);	// get blue
	    rgb[2] = mapValue(propBlue, 0, 0.88/2, 0, 255); 											// map Value and save it to rgb array				
		return rgb;
		
	}
	
	public static double distancePointLine(Point2D PV, Point2D LV1, Point2D LV2) {
		// PV = Point ; LV1 = Line Point 1 ; LV2 = Line Point 2
		//double dist = Math.abs((L2.y()-L1.y())*P.x()-(L2.x()-L1.x())*P.y()+L2.x()*L1.y()-L2.y()*L1.x()) / Math.sqrt(Math.sqrt((L2.y()-L1.y())+Math.sqrt(L2.x()-L1.x())));
										
	    Point2D slope = new Point2D (LV2.x() - LV1.x(), LV2.y() - LV1.y());		// slope of line
	    double lineLengthi = slope.x() * slope.x() + slope.y() * slope.y();     // squared length of line;

	    Point2D s = new Point2D(PV.x() - LV1.x(), PV.y() - LV1.y());
		double ti = (s.x()* slope.x()+ s.y()*slope.y())/lineLengthi;
		Point2D p = new Point2D(slope.x() * ti, slope.y() * ti);				// crawl the line acoording to its slope to distance t
		Point2D projectionOnLine = new Point2D(LV1.x()+p.x(), LV1.y()+p.y());	// add the starting coordinates			
		Point2D subber = new Point2D(projectionOnLine.x()- PV.x(), projectionOnLine.y()- PV.y()); // now calculate the distance of the measuring point to the projected point on the line
		double dist = (float) Math.sqrt(subber.x() * subber.x() + subber.y() * subber.y());
		return dist;
	}
	
	public double map(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
		}
	
	public static double mapValue(double _value, double _valMin, double _valMax, double _destMin, double _destMax){
        double proportion = (_value - _valMin) / (_valMax - _valMin);  // calculate the proportion between 0 and 1
        double scal = _destMax - _destMin;                             
        double result = (proportion * scal) + _valMin;                 // now scale it according to the desired scale
        if (result > _destMax){result = _destMax;}                  // coinstrain value to 0 - 255
        if (result < _destMin){result = _destMin;}
        return result;
    }
	

}
