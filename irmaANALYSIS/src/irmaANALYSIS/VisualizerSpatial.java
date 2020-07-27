package irmaANALYSIS;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.scene.shape.*;
import math.geom2d.Point2D;
import irmaANALYSIS.GUI;
import static irmaANALYSIS.VisualizerTemporal.getColor;

import java.util.ArrayList;

public class VisualizerSpatial {
	Sample s;
	
	HBox mainContainer;
	VBox guiContainer;
	StackPane triangleContainer;
	Pane triangleCanvas;
	Pane dataCanvas;
	
	Image triangleImage;
	 
	int scaleTriangle = 350;
	int heightTriangle = (int) Math.round(Math.sqrt(3)/2 * scaleTriangle);
	Color timerSignColor = Color.rgb(180,75,75);
	//int mainTimer = 0;
	Rectangle timerBackground;
	Label timerDisplay;
	Label timerSecondsDisplay;
	Duration timerDuration;
	
	ArrayList<Circle> subjectCircles;
	ArrayList<Line> subjectLines;
	Circle circleAFA;
	
	VisualizerSpatial(Sample _s){
		s = _s;
		mainContainer = new HBox();
		
		guiContainer = new VBox();
		triangleContainer = new StackPane();
		triangleCanvas = new Pane();
		dataCanvas = new Pane();

		MenuPullDown m = new MenuPullDown("Config");
		MenuItem c1=new MenuItem("Color Overlay");
		MenuItem c2=new MenuItem("Color Edges");
		MenuItem c3=new MenuItem("Dimensions Labels");
		MenuItem c4=new MenuItem("Subject Labels");
		MenuItem c5=new MenuItem("Convex Hull");
		MenuItem c6=new MenuItem("Cluster Analysis");
		m.getItems().addAll(c1, c2, c3, c4, c5, c6);
		VBox.setMargin(m,new Insets(6,6,6,6));
		
		MenuPullDown m2 = new MenuPullDown("Export");
		MenuItem e1=new MenuItem("Export PDF"); 
		MenuItem e2=new MenuItem("Export PNG"); 
		m2.getItems().addAll(e1, e2);
		VBox.setMargin(m2,new Insets(6,6,6,6));
		
		guiContainer.setPrefSize(114, 400);
        guiContainer.getStyleClass().add("spatialVisualizerGui");
		guiContainer.getChildren().add(m);
		guiContainer.getChildren().add(m2);
		
		
        triangleContainer.getChildren().addAll(triangleCanvas, dataCanvas);
        mainContainer.getChildren().addAll(guiContainer, triangleContainer);

        subjectCircles = new ArrayList<Circle>();
        subjectLines = new ArrayList<Line>();
        circleAFA = new Circle();

        HBox.setMargin(triangleContainer, new Insets(50, 0, 0, 0));
        
        // Event Handlers for Menu (as Lambda Functions)
 		e1.setOnAction(e ->{ 
 			PrinterJob job = PrinterJob.createPrinterJob();
            if(job != null){
         		job.showPrintDialog(IrmaANALYSIS.getPrimaryStage()); 
                boolean printed = job.printPage(triangleContainer);
                if (printed) {
                    job.endJob();
                }
            }
 		});     
 		
 		
 		timerBackground = new Rectangle(300,0,80,50);
 		timerBackground.setFill(timerSignColor);
 		timerDisplay = new Label("TC: ");
 		timerSecondsDisplay = new Label("Sec: ");
 		timerDisplay.relocate(305, 2);
 		timerSecondsDisplay.relocate(305, 27);
 		triangleCanvas.getChildren().addAll(timerBackground, timerDisplay, timerSecondsDisplay);
 		makeBGTriangle();
	}
	
	public HBox getSpatialContainer() {
		return mainContainer;
	}
	
	public void updateTimerDisplay(int _t) {
		String txt = String.valueOf(_t);
		timerDisplay.setText("TC: "+txt);
		
		timerDuration= new Duration(_t*500);
		String sec = String.valueOf(timerDuration.toSeconds());
		timerSecondsDisplay.setText("Sec: "+sec);
	}
	
	public void clearSpatial() {
		dataCanvas.getChildren().clear();
		subjectCircles.clear();
		subjectLines.clear();
	}
	
	public void makeBGTriangle() {
		Polygon triangle = new Polygon();
        	triangle.getPoints().addAll((double)scaleTriangle/2, 0.0, (double)scaleTriangle, (double)heightTriangle, 0.0, (double) heightTriangle);
        	triangle.setFill(Color.rgb(255,255,255,0.1));
        Polygon greenCorner = new Polygon();
        	greenCorner.setFill(Color.rgb(0, 255, 0));
        	greenCorner.getPoints().addAll(0.0, (double) heightTriangle, 20.0, (double) heightTriangle, 10.0, (double) heightTriangle-17.3);
        	greenCorner.setStrokeWidth(0);
        Polygon redCorner = new Polygon();
        	redCorner.setFill(Color.rgb(255, 0, 0));
        	redCorner.getPoints().addAll((double)scaleTriangle-20, (double) heightTriangle, (double)scaleTriangle, (double) heightTriangle, (double)scaleTriangle-10.0, (double) heightTriangle-17.3);
        	redCorner.setStrokeWidth(0);
        Polygon blueCorner = new Polygon();
        	blueCorner.setFill(Color.rgb(0, 0, 255));
        	blueCorner.getPoints().addAll((double)scaleTriangle/2, 0.0,(double)scaleTriangle/2+10.0, (double) 17.3, (double)scaleTriangle/2-10.0, (double) 17.3);
        	blueCorner.setStrokeWidth(0);
        triangleCanvas.getChildren().addAll(triangle, greenCorner, redCorner, blueCorner);
	}
	
	public void drawSection(int _begin, int _end) {
		clearSpatial();										// empty the data Pane 
		
		// 1. Make measuring points for all subjects in section
		for (int i = _begin; i < _end; i++) {
			for (Subject subject: s.SubjectsList) {	
				Point2D p = subject.getPointByIndex(i);
				double px = p.x()*scaleTriangle+scaleTriangle/2;
				double py = p.y()*heightTriangle+heightTriangle*2/3;
				double[] colorP = getColor(p);					// get color for current subject
				
				Circle c = new Circle(px,py,1);					// make and style circle
				c.setStrokeType(StrokeType.OUTSIDE);
				c.setStrokeWidth(2);
				//c.setFill(Color.BLACK);
				c.setFill(Color.rgb(0,0,0, 0.25));
				c.setStroke(Color.rgb((int) colorP[0], (int) colorP[1], (int) colorP[2], 0.25));
				subjectCircles.add(c);
			}
		}
		
		dataCanvas.getChildren().addAll(subjectCircles);
		
		// 2. Make AFA from all subjects over duration of section
		Point2D cp = s.getSectionAFA(_begin, _end);
		double afaX = cp.x()*scaleTriangle+scaleTriangle/2;
		double afaY = cp.y()*heightTriangle+heightTriangle*2/3;
		double[] colorAFA = getColor(cp);
		Circle circleAFA = new Circle(afaX,afaY,7);			// make, style and position Circle for AFA
		circleAFA.setStrokeWidth(1);
		circleAFA.setFill(Color.rgb((int) colorAFA[0], (int) colorAFA[1], (int) colorAFA[2]));
		circleAFA.setStroke(Color.WHITE);
		dataCanvas.getChildren().add(circleAFA);
		
	}
	
	public void drawSampleVector(int _t){
		dataCanvas.getChildren().clear(); 					// empty the data Pane
		subjectLines.clear();
		subjectCircles.clear();
		
		Point2D pointAFA = s.getAFA(_t);				// get AFA
		double afaX = pointAFA.x()*scaleTriangle+scaleTriangle/2;
		double afaY = pointAFA.y()*heightTriangle+heightTriangle*2/3;
		double[] colorAFA = getColor(pointAFA);				// get color for current AFA
															
		Circle circleAFA = new Circle(afaX,afaY,7);			// make, style and position Circle for AFA
		circleAFA.setStrokeWidth(1);
		circleAFA.setFill(Color.rgb((int) colorAFA[0], (int) colorAFA[1], (int) colorAFA[2]));
		circleAFA.setStroke(Color.WHITE);
		
		
		for (Subject sub: s.SubjectsList) {			// loop all subjects
			Point2D p = sub.getPointByIndex(_t);
			double px = p.x()*scaleTriangle+scaleTriangle/2;
			double py = p.y()*heightTriangle+heightTriangle*2/3;
			double[] colorP = getColor(p);					// get color for current subject
			
			Circle c = new Circle(px,py,1);					// make and style circle
			c.setStrokeType(StrokeType.OUTSIDE);
			c.setStrokeWidth(2);
			c.setFill(Color.BLACK);
			c.setStroke(Color.rgb((int) colorP[0], (int) colorP[1], (int) colorP[2]));
			subjectCircles.add(c);
			Line l = new Line(px,py,afaX,afaY);				// make and style line
			l.setStrokeWidth(1);
			l.setStroke(Color.WHITE);
			subjectLines.add(l);
		}
			
		dataCanvas.getChildren().addAll(subjectLines);
		dataCanvas.getChildren().addAll(subjectCircles);
		dataCanvas.getChildren().add(circleAFA);
	}
	
}
