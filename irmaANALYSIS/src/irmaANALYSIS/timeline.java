package irmaANALYSIS;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import math.geom2d.Point2D;

public class timeline {
	
	HBox mainContainer;
	VBox guiContainer;
	Pane visualContainer;
	ScrollPane scrollContainer;
	StackPane layerContainer;
	Pane scaleLayer;
	Pane dataLayer;
	Pane markerLayer;
	Pane playbackLayer;
	
	Sample s;
	
	double  zoomFactor = 1;
	double stepSize = zoomFactor*2;
	
	Line playbackLine;
	Slider zoomXSlider;

	timeline(Sample _s){
		s = _s;
		mainContainer = new HBox();
		guiContainer = new VBox();
		visualContainer = new Pane();
		scrollContainer = new ScrollPane();
		layerContainer = new StackPane();
		scaleLayer = new Pane();
		dataLayer = new Pane();
		markerLayer = new Pane();
		playbackLayer = new Pane();
		
		layerContainer.getChildren().addAll(scaleLayer, dataLayer, markerLayer, playbackLayer);
		scrollContainer.setContent(layerContainer);
		visualContainer.getChildren().add(scrollContainer);
		mainContainer.getChildren().addAll(guiContainer, visualContainer);
		
		mainContainer.getStyleClass().add("timeline-main");
		
		mainContainer.setPrefSize(1160, 250);
		mainContainer.setMinSize(1160, 250);
		guiContainer.setPrefSize(108, 250);
		guiContainer.setMinSize(108, 250);
		visualContainer.setPrefSize(1020, 220);
		visualContainer.setMinSize(1020, 220);
		scrollContainer.setPrefSize(1020, 250);
		scrollContainer.setMinSize(1020, 250);
		layerContainer.setPrefSize(1020, 220);
		layerContainer.setMinSize(1020, 220);
		layerContainer.setMaxHeight(220);
		
		playbackLine = new Line();
        playbackLine.setStroke(Color.RED);
        
        
        MenuPullDown m1 = new MenuPullDown("Config");
		MenuItem c1=new MenuItem("Color On/Off");
		MenuItem c2=new MenuItem("Follow PLayback");
		m1.getItems().addAll(c1, c2);
		VBox.setMargin(m1,new Insets(0,0,6,0));
		
		MenuPullDown m2 = new MenuPullDown("Visual");
		MenuItem v1=new MenuItem("Sample – Activity");
		MenuItem v2=new MenuItem("Sample – Average Focus of Attention");
		MenuItem v3=new MenuItem("Subjects – Activity");
		MenuItem v4=new MenuItem("Subjects – Focus of Attention");
		m2.getItems().addAll(v1, v2, v3, v4);
		VBox.setMargin(m2,new Insets(0,0,10,0));
		
		MenuPullDown m3 = new MenuPullDown("Filter");
		MenuItem f1=new MenuItem("Personal Background");
		m3.getItems().addAll(f1);
		VBox.setMargin(m3,new Insets(0,0,6,0));
        
		MenuPullDown m4 = new MenuPullDown("Export");
		MenuItem e1=new MenuItem("PNG");
		MenuItem e2=new MenuItem("PDF");
		MenuItem e3=new MenuItem("PDF - All");
		MenuItem e4=new MenuItem("JSON");
		MenuItem e5=new MenuItem("Sound Wave");
		MenuItem e6=new MenuItem("Current View All");
		
		m4.getItems().addAll(e1,e2,e3,e4,e5,e6);
		VBox.setMargin(m4,new Insets(0,0,6,0));
		
		MenuPullDown m5 = new MenuPullDown("Data Header");
		MenuItem d1=new MenuItem("Show Data Header");
		m5.getItems().addAll(d1);
		VBox.setMargin(m5,new Insets(0,0,6,0));
		
        zoomXSlider = new Slider(0.1,10,1);
		guiContainer.getChildren().addAll(m1, m2, m3,m4,m5, zoomXSlider);
		
		e1.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
			File file = fileChooser.showSaveDialog(null);
			
		}});
		
		e2.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent event) {
			PrinterJob job = PrinterJob.createPrinterJob();
	           if(job != null){
	        		job.showPrintDialog(IrmaANALYSIS.getPrimaryStage()); 
	        		boolean printed = job.printPage(scrollContainer);
	                if (printed) {
	                    job.endJob();
	                }else{
	                    System.out.println("Print failed");
	                }
	           }
		}});
		
		e3.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent event) {
           PrinterJob job = PrinterJob.createPrinterJob();
           if(job != null){
        		job.showPrintDialog(IrmaANALYSIS.getPrimaryStage()); 
                boolean printed = job.printPage(layerContainer);
                if (printed) {
                    job.endJob();
                }else{
                    System.out.println("Print failed");
                }
           }
	    }});
		
		e4.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent event) {
			
		}});
		
		zoomXSlider.valueProperty().addListener(new ChangeListener<Number>() { 		 	// Adding Listener to value property.
            public void changed(ObservableValue <? extends Number >  
                      observable, Number oldValue, Number newValue){ 
            	zoomFactor = (Double) newValue;
            	stepSize = zoomFactor*2;
            	updateTimeline();
            } 
        });
	}
	
	public HBox getTimeline() {
		return mainContainer;
	}
	
	public void updateTimeline() {
		
	}
	
	public void drawTimeline(int _begin, int _end) {
		
	}
	
	public void drawPlaybackPosition(double _t) {
		playbackLine.setStartX(_t*stepSize);
		playbackLine.setStartY(0);
		playbackLine.setEndX(_t*stepSize);
		playbackLine.setEndY(200);
	}
	
	public static double[] getColor(Point2D _p) {
		double[] rgb = {0,0,0};
		Point2D topCorner = new Point2D(0.0, -0.577350269189626);
		Point2D rightCorner = new Point2D(0.5, 0.288675134594813);
		Point2D leftCorner = new Point2D(-0.5, 0.288675134594813);
		Point2D currentPoint = _p;
		double propRed = distancePointLine(currentPoint, leftCorner, topCorner); 		// get red
		rgb[0] = mapValue(propRed, 0.0, 0.88/2, 0, 255); 								// map Value and save it to rgb array
		double propGreen = distancePointLine(currentPoint, topCorner, rightCorner); 	// get green
	    rgb[1] = mapValue(propGreen, 0, 0.88/2, 0, 255); 								// map Value and save it to rgb array
		double propBlue = distancePointLine(currentPoint, leftCorner, rightCorner);		// get blue
	    rgb[2] = mapValue(propBlue, 0, 0.88/2, 0, 255); 								// map Value and save it to rgb array				
		return rgb;
		
	}
	
	public static double distancePointLine(Point2D PV, Point2D LV1, Point2D LV2) {
		// PV = Point ; LV1 = Line Point 1 ; LV2 = Line Point 2
		//double dist = Math.abs((L2.y()-L1.y())*P.x()-(L2.x()-L1.x())*P.y()+L2.x()*L1.y()-L2.y()*L1.x()) / Math.sqrt(Math.sqrt((L2.y()-L1.y())+Math.sqrt(L2.x()-L1.x())));
										
	    Point2D slope = new Point2D (LV2.x() - LV1.x(), LV2.y() - LV1.y());				// slope of line
	    double lineLengthi = slope.x() * slope.x() + slope.y() * slope.y();     		// squared length of line;

	    Point2D s = new Point2D(PV.x() - LV1.x(), PV.y() - LV1.y());
		double ti = (s.x()* slope.x()+ s.y()*slope.y())/lineLengthi;
		Point2D p = new Point2D(slope.x() * ti, slope.y() * ti);						// crawl the line acoording to its slope to distance t
		Point2D projectionOnLine = new Point2D(LV1.x()+p.x(), LV1.y()+p.y());			// add the starting coordinates			
		Point2D subber = new Point2D(projectionOnLine.x()- PV.x(), projectionOnLine.y()- PV.y()); // now calculate the distance of the measuring point to the projected point on the line
		double dist = (float) Math.sqrt(subber.x() * subber.x() + subber.y() * subber.y());
		return dist;
	}
	
	public double map(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
		}
	
	public static double mapValue(double _value, double _valMin, double _valMax, double _destMin, double _destMax){
        double proportion = (_value - _valMin) / (_valMax - _valMin);  					// calculate the proportion between 0 and 1
        double scal = _destMax - _destMin;                             
        double result = (proportion * scal) + _valMin;                 					// now scale it according to the desired scale
        if (result > _destMax){result = _destMax;}                  					// coinstrain value to 0 - 255
        if (result < _destMin){result = _destMin;}
        return result;
    }
}
