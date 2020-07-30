package irmaANALYSIS;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
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
import math.geom2d.Point2D;

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
	
	VBox buttonPanelRight;
	Group buttonCloseTL;
	Group buttonMoveTL;
	
	Sample s;
	int id;
	
	double zoomFactor = 1;
	double yScale = 1;
	double stepSize = zoomFactor*2;
	double minStepSizeZoom;									// used for the zoom slider
	int zoomFirstTimeCode;
	int zoomLastTimeCode;
	
	Slider zoomXSlider;
	Slider zoomYSlider;
	Slider clusterIntervalSlider;
	Label zoomXLabel;
	Label zoomYLabel;
	Label clusterIntervalLabel;
	
	
	timelinePlaybackMarker playMarker;
	ArrayList<Line> gridLines;
	ArrayList<timelineMarker> markerList;
	int markerCounter = 0;

	timelineScale scale;
	 
	timeline(Sample _s, int _id, int _initialTimeCode){
		s = _s;
		id = _id;
		scale = new timelineScale(s.getShortestDataset());
		
		gridLines = new ArrayList<Line>();
		markerList = new ArrayList<timelineMarker>();
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
		scaleLayer.setStyle("-fx-background-color: rgba(255,255,255, 0.1);");
		
		scaleLayer.setPickOnBounds(false);										// Make all Layers of Timeline transparent for MouseClicks
		sectionLayer.setPickOnBounds(false);
		markerLayer.setPickOnBounds(false);
		playbackLayer.setPickOnBounds(false);
				
		mainContainer.getStyleClass().add("timeline");
		
		VBox.setMargin(mainContainer,new Insets(10,0,10,0));
		
		mainContainer.setPrefSize(1300, 250);
		mainContainer.setMinSize(1160, 250);
		guiContainer.setPrefSize(108, 250);
		guiContainer.setMinSize(108, 250);
		visualContainer.setPrefSize(1200, 220);
		visualContainer.setMinSize(1200, 220);
		scrollContainer.setPrefSize(1200, 250);
		scrollContainer.setMinSize(1200, 250);
		scrollContainer.setMaxSize(1200, 250);
		layerContainer.setPrefSize(1200, 237);
		layerContainer.setMinSize(1200, 237);
		layerContainer.setMaxHeight(220);
		
		playbackLayer.getChildren().add(playMarker.getMarkerNode());					// Add Playback Marker Node
		playbackLayer.getChildren().add(scale.getScaleNode());							// Add Scale Marker Node
		
		layerContainer.getChildren().addAll(scaleLayer, dataLayer, markerLayer, sectionLayer, playbackLayer);
		scrollContainer.setContent(layerContainer);
		visualContainer.getChildren().add(scrollContainer);
		//mainContainer.getChildren().addAll(guiContainer, visualContainer);
		
		buttonPanelRight = new VBox();
		//buttonPanelRight.getStyleClass().add("buttonPanelRight");
		buttonCloseTL = new Group();
		buttonCloseTL.getStyleClass().add("rbutton");
		Rectangle bcbg = new Rectangle(0,0,21,21);
		bcbg.setFill(Color.WHITE);
		buttonCloseTL.setOnMouseEntered(e ->{ bcbg.setFill(Color.rgb(112,112,112)); });
		buttonCloseTL.setOnMouseExited(e ->{ bcbg.setFill(Color.WHITE); });
		buttonCloseTL.setOnMousePressed(e ->{GUI.visTemp.discardTimeline(id); });
		
		VBox.setMargin(buttonCloseTL,new Insets(0,0,6,6));
		Line l1 = new Line(6,6,15,15);
		Line l2 = new Line(6,15,15,6);
		buttonCloseTL.getChildren().addAll(bcbg, l1, l2);
		
		buttonMoveTL = new Group();
		Rectangle bmbg = new Rectangle(0,0,21,21);	
		bmbg.setFill(Color.WHITE);
		//bmbg.getStyleClass().add("rbutton");
		buttonMoveTL.setOnMouseEntered(e ->{ bmbg.setFill(Color.rgb(112,112,112)); });
		buttonMoveTL.setOnMouseExited(e ->{ bmbg.setFill(Color.WHITE); });
		VBox.setMargin(buttonMoveTL,new Insets(0,0,6,6));
		Line bm1 = new Line(3,5.5,17,5.5);
		Line bm2 = new Line(3,10.5,17,10.5);
		Line bm3 = new Line(3,15.5,17,15.5);
		buttonMoveTL.getChildren().addAll(bmbg, bm1, bm2, bm3);
		buttonPanelRight.getChildren().addAll(buttonCloseTL, buttonMoveTL);
		
		mainContainer.getChildren().addAll(guiContainer, visualContainer, buttonPanelRight);
		
        MenuPullDown m1 = new MenuPullDown("Config");
		MenuItem c2=new MenuItem("Follow Playback");
		MenuItem c3=new MenuItem("Discard Timeline");
		m1.getItems().addAll(c2, c3);
		VBox.setMargin(m1,new Insets(0,0,6,8));
		
		
		MenuPullDown m3 = new MenuPullDown("Filter");
		MenuItem f1=new MenuItem("Personal Background");
		m3.getItems().addAll(f1);
		VBox.setMargin(m3,new Insets(6,0,6,8));
        
		MenuPullDown m4 = new MenuPullDown("Export");
		//MenuItem e1=new MenuItem("PNG");
		MenuItem e2=new MenuItem("PDF");
		MenuItem e3=new MenuItem("PDF - All");
		MenuItem e4=new MenuItem("JSON");
		MenuItem e5=new MenuItem("Sound Wave");
		//MenuItem e6=new MenuItem("Current View All");
		
		m4.getItems().addAll(e2,e3,e4,e5);
		VBox.setMargin(m4,new Insets(6,0,6,8));
		
		/*MenuPullDown m5 = new MenuPullDown("Data Header");
		MenuItem d1=new MenuItem("Show Data Header");
		m5.getItems().addAll(d1);
		VBox.setMargin(m5,new Insets(0,0,6,0));*/
		
		double lData = s.getShortestDataset() * stepSize;				// visual width of the dataset stepSize is 2 initially
		double lView = layerContainer.getPrefWidth();					// visual width of Layer Container node
		minStepSizeZoom = (double) lView/ (double) lData;				// now calculate for zoom slider the minimum step size 	
																		// so all data fits nicely in the display when maximum zoomed out
				
        VBox sliderContainer = new VBox();
        VBox.setMargin(sliderContainer,new Insets(12,6,6,8));
		zoomXSlider = new Slider(minStepSizeZoom,10, 1);
        zoomYSlider = new Slider(0.1, 5, 1);
        clusterIntervalSlider = new Slider(1, 100, 1);
        zoomXLabel = new Label("Zoom X");
        zoomYLabel = new Label("Zoom Y");
        clusterIntervalLabel = new Label("Cluster Interval");
        VBox.setMargin(zoomXLabel,new Insets(2,0,6,0));
        VBox.setMargin(zoomYLabel,new Insets(2,0,6,0));
        VBox.setMargin(clusterIntervalLabel,new Insets(2,0,6,0));
        zoomXSlider.setPrefSize(90, 20);
        zoomYSlider.setPrefSize(90, 20);
        clusterIntervalSlider.setPrefSize(90, 20);
        zoomXSlider.setMaxSize(90, 20);
        zoomYSlider.setMaxSize(90, 20);
        clusterIntervalSlider.setMaxSize(90, 20);
        zoomXLabel.getStyleClass().add("sliderlabel");
        zoomYLabel.getStyleClass().add("sliderlabel");
        clusterIntervalLabel.getStyleClass().add("sliderlabel");
        sliderContainer.getChildren().addAll( zoomXSlider, zoomXLabel, zoomYSlider, zoomYLabel, clusterIntervalSlider, clusterIntervalLabel);
        
        guiContainer.getChildren().addAll(m1,m3,m4,sliderContainer);
		
		c3.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent event) {
			GUI.visTemp.discardTimeline(id);
		}});

		/*e1.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
			File file = fileChooser.showSaveDialog(null);
			
		}});*/
		
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
		
		zoomXSlider.valueProperty().addListener((observable, oldValue, newValue) -> {	// Adding Listener to value property.
			double currentScrollPaneX = scrollContainer.getHvalue();
        	zoomFactor = (Double) newValue;
        	stepSize = zoomFactor*2;												// calculate new step size for this timelines
        	updateTimeline();														// Update all UI Elements according to new Zoom Level
        	updatePlaybackHead();
        	updateMarkers(stepSize);
        	updateSections(stepSize);
        	scale.updateScale(layerContainer.getBoundsInParent().getWidth(), stepSize);
        	updateScale();
        	scrollContainer.setHvalue(currentScrollPaneX);
		});
		
		zoomYSlider.valueProperty().addListener((observable, oldValue, newValue) -> {	// Adding Listener to value property.
			yScale = (Double) newValue;
        	updateTimeline();
		});  
		
		updatePlaybackHead();
		updatePlaybackTimer(_initialTimeCode);
	}
	
	public HBox getTimeline() {
		return mainContainer;
	}
	
	
	public void updateTimeline() {
		
	}
	
	public void drawTimeline(int _begin, int _end) {
		
	}
	
	public void makeRollovers(ArrayList<Line> list) {
		list.forEach((l) -> {
			l.setOnMouseEntered(e ->{ l.setStroke(Color.WHITE);	});
			l.setOnMouseExited(e ->{ 
				double[] c = (double[]) l.getProperties().get("color");
        		l.setStroke(Color.rgb((int) c[0], (int) c[1], (int) c[2]));
			});
			l.setOnMousePressed(e ->{ 
				int tc = (int) l.getProperties().get("timeCode");
            	timelineMarker tm = new timelineMarker(tc, markerCounter, stepSize);
            	makeMarkerContextMenu(tm);
            	markerList.add(tm);
            	markerLayer.getChildren().add(tm.getMarkerNode());
            	markerCounter ++;
			});			
		});
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
		playMarker.moveTo(playMarker.getTimerPos()*stepSize);
		playMarker.synchronizeStepSize(stepSize);
	}
	
	
	public void updatePlaybackTimer(int _t) {
		playMarker.updateTimerPos(_t);
		playMarker.moveTo((double)_t*stepSize);
	}
	
	// Marker functions
	// --------------------------------------------------------------
	
	public ArrayList<timelineMarker> getMarkerList(){
		return markerList;
	}
	
	public void updateMarkers(double stepSize) {
		markerList.forEach( (m) -> { m.updateMarkerPos(stepSize);});
	}
	
	public void deleteMarker(int _id) {
		final int id = _id;
		markerList.removeIf(m -> (m.id == id)); 
	}
	
	public double findNextMarkerPosTC(double _currentPos) {					// returns the TimeCode of the next Marker
		double nextPos = s.getShortestDataset();							// start with assuming that end of sample is end of section aka there is only one marker
		for(int i = 0; i < markerList.size(); i++) {
			timelineMarker m = markerList.get(i);
			double checkX = m.getMarkerTimeCode();
			if (checkX < nextPos && checkX > _currentPos) {
				nextPos = checkX;
			}
		}	
		return nextPos;
	}
	
	public void makeMarkerContextMenu(timelineMarker _tm) {
		ContextMenu contextMenu = new ContextMenu();
        contextMenu.getStyleClass().add("MenuPullDown");
        MenuItem cm1 = new MenuItem("Make new Section"); 
        MenuItem cm2 = new MenuItem("Delete Section"); 
        MenuItem cm3 = new MenuItem("Average of Section"); 
        MenuItem cm4 = new MenuItem("Display Section in Spatial Visualizer");
        MenuItem cm5 = new MenuItem("Delete Marker");
        contextMenu.getItems().addAll(cm1,cm2,cm3, cm4, cm5); 
		_tm.makeSectionContextMenu(contextMenu);
		
		
		// Event Handlers for Context Menu (as Lambda Functions)
		cm1.setOnAction(e ->{ makeSection(_tm); });											// Menu: Make new Section for this Marker	
		cm2.setOnAction(e ->{ System.out.println("Delete Section");	
			markerLayer.getChildren().remove(_tm.getSection().getSectionNode());
			_tm.clearSection();
		});
		cm3.setOnAction(e ->{ System.out.println("Average of Section");	});
		cm4.setOnAction(e ->{ GUI.visSpat.drawSection((int) _tm.section.getStartTimeCode(), (int) _tm.section.getEndTimeCode()); });
		cm5.setOnAction(e ->{ 
			markerLayer.getChildren().remove(_tm.getMarkerNode());		// Remove Marker from Layer Node
			markerLayer.getChildren().remove(_tm.getSection().getSectionNode()); // Remove Selection from Layer Node 
			markerList.removeIf(m -> (m.id == _tm.id)); 				// Remove from Marker ArrayList
		});
	}
	
	// Section functions
	// --------------------------------------------------------------
	
	public void makeSection(timelineMarker _tm) {
		double startPos = _tm.getMarkerTimeCode();
		//double endPos = findNextMarkerPosTC(startPos);
		double endPos = startPos + 200;
		timelineSection section = new timelineSection(startPos, endPos, stepSize); 	
		_tm.setSection(section);
		markerLayer.getChildren().add(_tm.getSection().getSectionNode());
	}
	
	public void updateSections(double _newStepSize) {
		markerList.forEach((m) -> { 
			if (m.section != null) {
				m.section.updateSectionPos(_newStepSize); 
			}
		});
	}
	
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
