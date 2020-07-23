package irmaANALYSIS;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.*;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import math.geom2d.Point2D;

import irmaANALYSIS.VisualizerTemporal;

public class timeline {
	
	HBox mainContainer;
	VBox guiContainer;
	Pane visualContainer;
	ScrollPane scrollContainer;
	StackPane layerContainer;
	Pane scaleLayer;
	Pane dataLayer;
	Pane sectionLayer;
	Pane markerLayer;
	Pane playbackLayer;
	
	Sample s;
	int id;
	
	double zoomFactor = 1;
	double yScale = 1;
	double stepSize = zoomFactor*2;
	double minStepSizeZoom;									// used for the zoom slider
	int zoomFirstTimeCode;
	int zoomLastTimeCode;
	
	timelinePlaybackMarker playMarker;
	Slider zoomXSlider;
	Slider zoomYSlider;
	Slider clusterIntervalSlider;
	Label zoomXLabel;
	Label zoomYLabel;
	Label clusterIntervalLabel;
	
	ArrayList<Line> gridLines;
	ArrayList<timelineMarker> markerList;
	ArrayList<timelineSection> sectionList;
	
	//Rectangle timelineScale;
	timelineScale scale;
	 

	timeline(Sample _s, int _id){
		s = _s;
		id = _id;
		scale = new timelineScale(s.getShortestDataset());
		
		gridLines = new ArrayList<Line>();
		markerList = new ArrayList<timelineMarker>();
		sectionList = new ArrayList<timelineSection>();
		playMarker = new timelinePlaybackMarker();
		
		mainContainer = new HBox();
		guiContainer = new VBox();
		visualContainer = new Pane();
		scrollContainer = new ScrollPane();
		layerContainer = new StackPane();
		scaleLayer = new Pane();
		dataLayer = new Pane();
		sectionLayer = new Pane();
		markerLayer = new Pane();
		playbackLayer = new Pane();
		layerContainer.setStyle("-fx-background-color: rgba(255,255,255, 0.2);");
		
		scaleLayer.setPickOnBounds(false);										// Make all Layers of Timeline transparent for MouseClicks
		sectionLayer.setPickOnBounds(false);
		markerLayer.setPickOnBounds(false);
		playbackLayer.setPickOnBounds(false);
				
		mainContainer.getStyleClass().add("timeline");
		
		mainContainer.setPrefSize(1400, 250);
		mainContainer.setMinSize(1160, 250);
		guiContainer.setPrefSize(108, 250);
		guiContainer.setMinSize(108, 250);
		visualContainer.setPrefSize(1200, 220);
		visualContainer.setMinSize(1200, 220);
		scrollContainer.setPrefSize(1200, 250);
		scrollContainer.setMinSize(1200, 250);
		layerContainer.setPrefSize(1200, 220);
		//layerContainer.setMaxSize(1200, 220);
		layerContainer.setMinSize(1200, 220);
		layerContainer.setMaxHeight(220);
		
		playbackLayer.getChildren().add(playMarker.getMarkerNode());					// Add Playback Marker Node
		playbackLayer.getChildren().add(scale.getScaleNode());							// Add Scale Marker Node
		
		layerContainer.getChildren().addAll(scaleLayer, dataLayer, markerLayer, sectionLayer, playbackLayer);
		scrollContainer.setContent(layerContainer);
		visualContainer.getChildren().add(scrollContainer);
		mainContainer.getChildren().addAll(guiContainer, visualContainer);
		
        MenuPullDown m1 = new MenuPullDown("Config");
		MenuItem c1=new MenuItem("Color On/Off");
		MenuItem c2=new MenuItem("Follow PLayback");
		MenuItem c3=new MenuItem("Discard Timeline");
		m1.getItems().addAll(c1, c2, c3);
		VBox.setMargin(m1,new Insets(0,0,6,0));
		
		/*
		MenuPullDown m2 = new MenuPullDown("Visual");
		MenuItem v1=new MenuItem("Sample – Activity");
		MenuItem v2=new MenuItem("Sample – Average Focus of Attention");
		MenuItem v3=new MenuItem("Subjects – Activity");
		MenuItem v4=new MenuItem("Subjects – Focus of Attention");
		m2.getItems().addAll(v1, v2, v3, v4);
		VBox.setMargin(m2,new Insets(0,0,10,0));*/
		
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
		
		double lData = s.getShortestDataset() * stepSize;				// visual width of the dataset stepSize is 2 initially
		double lView = layerContainer.getPrefWidth();					// visual width of Layer Container node
		minStepSizeZoom = (double) lView/ (double) lData;				// now calculate for zoom slider the minimum step size 	
																		// so all data fits nicely in the display when maximum zoomed out
				
        zoomXSlider = new Slider(minStepSizeZoom,10, 1);
        zoomYSlider = new Slider(0.1, 5, 1);
        clusterIntervalSlider = new Slider(1, 100, 1);
        zoomXLabel = new Label("Zoom X");
        zoomYLabel = new Label("Zoom Y");
        clusterIntervalLabel = new Label("Cluster Interval");
        zoomXSlider.setPrefSize(90, 20);
        zoomYSlider.setPrefSize(90, 20);
        zoomXLabel.getStyleClass().add("sliderlabel");
        zoomYLabel.getStyleClass().add("sliderlabel");
        clusterIntervalLabel.getStyleClass().add("sliderlabel");
		guiContainer.getChildren().addAll(m1,m3,m4,m5, zoomXSlider, zoomXLabel, zoomYSlider, zoomYLabel, clusterIntervalSlider, clusterIntervalLabel);
		
		c3.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent event) {
			VisualizerTemporal.discardTimeline(id);
		}});

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
		
		zoomFirstTimeCode = 0;
		zoomLastTimeCode = (int) Math.floor(1400/stepSize);
				//s.getShortestDataset();
		
		zoomXSlider.valueProperty().addListener(new ChangeListener<Number>() { 		 	// Adding Listener to value property.
            public void changed(ObservableValue <? extends Number >  
                	observable, Number oldValue, Number newValue){ 
            	double currentScrollPaneX = scrollContainer.getHvalue();
            	zoomFactor = (Double) newValue;
            	stepSize = zoomFactor*2;
            	
            	zoomLastTimeCode = zoomFirstTimeCode + (int) Math.round(1160/stepSize); // Calculate first and last visible TimeCode of Zoom Window
            	int centerTimeCode = (zoomLastTimeCode-zoomFirstTimeCode)/2;			// central TimeCode
            	
            	//System.out.println(zoomLastTimeCode);
            	updateTimeline();
            	updatePlaybackHead();
            	updateMarkers(stepSize);
            	scale.updateScale(layerContainer.getBoundsInParent().getWidth(), stepSize);
            	updateScale();
            	scrollContainer.setHvalue(currentScrollPaneX);
            } 
        });
		
		zoomYSlider.valueProperty().addListener(new ChangeListener<Number>() { 		 	// Adding Listener to value property.
            public void changed(ObservableValue <? extends Number >  
                      observable, Number oldValue, Number newValue){ 
            	yScale = (Double) newValue;
            	updateTimeline();
            } 
        });
		
		scrollContainer.hvalueProperty().addListener(new ChangeListener<Number>() {
	          public void changed(ObservableValue<? extends Number> ov,
	              Number old_val, Number new_val) {
	                  //System.out.println(new_val.intValue());
	        	  	  //scrollContainer.setHmax(s.getShortestDataset());
	                 // System.out.println(scrollContainer.getHvalue());
	                  
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
	
	public void makeRollovers(ArrayList<Line> list) {
		for (int i = 0; i < list.size(); i++) {
			Line l = list.get(i);
			l.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        	@Override
	        	public void handle(MouseEvent e) {
	        		l.setStroke(Color.WHITE);
	        	}	
	        });
			l.setOnMouseExited(new EventHandler<MouseEvent>() {
	        	@Override
	        	public void handle(MouseEvent e) {
	        		double[] c = (double[]) l.getProperties().get("color");
	        		l.setStroke(Color.rgb((int) c[0], (int) c[1], (int) c[2]));
	        	}	
	        });
			l.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	            	int tc = (int) l.getProperties().get("timeCode");
	            	timelineMarker tm = new timelineMarker(tc, stepSize);
	            	markerList.add(tm);
	            	
	            	markerLayer.getChildren().add(tm.getMarkerNode());
	                
	                System.out.println(markerList.size());
	                tm = null;
	                for (int i = 0; i < markerList.size(); i++) {
	                	timelineMarker hg = markerList.get(i);
	                	System.out.print(i+":"+hg.getMarkerX()+"--");
	                }
	            }
	         });
		}
	}
	
	// Manage Scale 
	// --------------------------------------------------------------
	
	public void drawScale(int _begin, int _end) {
		for (int i = _begin; i < _end; i+= 20) {
			Line l = new Line(i*stepSize+0.5, 0, i*stepSize+0.5, 240);
			l.setStroke(Color.rgb(255,255,255, 0.2));
			l.setStrokeWidth(1);
			gridLines.add(l);
						
		}
		scaleLayer.getChildren().addAll(gridLines);
	}
	
	public void updateScale() {
		for (int i = 0; i < gridLines.size(); i++) {
			Line l = gridLines.get(i);
			l.setStartX(i*20*stepSize+0.5);
			l.setEndX(i*20*stepSize+0.5);
		}
	}
	
	// Playback Head 
	// --------------------------------------------------------------
	
	public void updatePlaybackHead() {
		int pos = playMarker.getTimerPos();
		playMarker.moveTo(pos*stepSize);
	}
	
	
	public void updatePlaybackTimer(int _t) {
		playMarker.updateTimerPos(_t);
	}
	
	public void drawPlaybackPosition(double _t) {
		playMarker.moveTo(_t*stepSize);
		playMarker.setLabel(_t);
	}
	
	// Marker functions
	// --------------------------------------------------------------
	
	public void updateMarkers(double stepSize) {
		for(int i = 0; i < markerList.size(); i++) {
			timelineMarker m = markerList.get(i);
			m.updateMarkerPos(stepSize);
		}
	}
	
	/*
	public void makeMarker(double _xPos) {								// make new Marker and update all sections
		double start = 0.0;
		double end = 0.0;
		timelineMarker tm = new timelineMarker(_xPos, 2);					// make new marker
		markerList.add(tm);
		sectionList.clear();
		markerLayer.getChildren().add(tm.getMarkerNode());
		for (int i = 0; i < markerList.size(); i ++) {					// update sections
			if (markerList.size() == 1) {
				start = 0.0;
				end = markerList.get(0).getMarkerX();
				sectionList.add(new timelineSection(start, end));
				start = markerList.get(0).getMarkerX();
				end = s.getShortestDataset();
				sectionList.add(new timelineSection(start, end));
			}else {
				if (i == 0) {												// if first marker
					start = 0.0;
					end = markerList.get(i).getMarkerX();
					sectionList.add(new timelineSection(start, end));
				}else if (i == markerList.size()-1){						// if last marker
					start = markerList.get(i).getMarkerX();
					end = s.getShortestDataset();
					sectionList.add(new timelineSection(start, end));
				}else {														// if not first or last
					start = markerList.get(i).getMarkerX();
					//end = markerList.get(i+1).getMarkerX();
					end = markerList.get(findNextMarker(start)).getMarkerX();
					sectionList.add(new timelineSection(start, end));
				}
			}
			//sectionList.add(new timelineSection(start, end));			// add section
		} 
		sectionLayer.getChildren().clear();
		for (int i = 0; i < sectionList.size(); i ++) {					// redraw sections
			
			sectionLayer.getChildren().add(sectionList.get(i).getSectionNode());
		}
	}
	
	public int findNextMarker(double _testX) {
		double nextX = s.getShortestDataset();
		int nextId = 0;
		for (int i = 0; i < sectionList.size(); i ++) {	
			double currentNextX = sectionList.get(i).getStart();
			if (currentNextX > _testX && currentNextX < nextX) {
				nextX = currentNextX;
				nextId = i;
			}
		}
		return nextId;
	}
	
	*/
	
	// Helper Functions to calculate and draw timeline
	
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
