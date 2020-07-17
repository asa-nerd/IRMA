package irmaANALYSIS;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
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
	static Pane triangleCanvas;
	static Pane dataCanvas;
	
	Image triangleImage;
	 
	static int scaleTriangle = 350;
	static int heightTriangle = (int) Math.round(Math.sqrt(3)/2 * scaleTriangle);
	
	static ArrayList<Circle> subjectCircles;
	static ArrayList<Line> subjectLines;
	static Circle circleAFA;
	
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
		m.getItems().addAll(c1, c2, c3, c4);
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
        
        makeBGTriangle();
        
        
        HBox.setMargin(triangleContainer, new Insets(50, 0, 0, 0));
        
        
	}
	
	public HBox getSpatialContainer() {
		return mainContainer;
	}
	
	public void makeBGTriangle() {
		Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
        		(double)scaleTriangle/2, 0.0,  
        		(double)scaleTriangle, (double)heightTriangle,
        		0.0, (double) heightTriangle);
        triangle.setFill(Color.rgb(255,255,255,0.1));
        //triangle.setStroke(Color.RED);
        
        Polygon greenCorner = new Polygon();
        greenCorner.setFill(Color.rgb(0, 255, 0));
        greenCorner.getPoints().addAll(
        		0.0, (double) heightTriangle,
        		20.0, (double) heightTriangle,
				10.0, (double) heightTriangle-17.3							
        		);
        greenCorner.setStrokeWidth(0);
        Polygon redCorner = new Polygon();
        redCorner.setFill(Color.rgb(255, 0, 0));
        redCorner.getPoints().addAll(
        		(double)scaleTriangle-20, (double) heightTriangle,
        		(double)scaleTriangle, (double) heightTriangle,
        		(double)scaleTriangle-10.0, (double) heightTriangle-17.3							
        		);
        redCorner.setStrokeWidth(0);
        Polygon blueCorner = new Polygon();
        blueCorner.setFill(Color.rgb(0, 0, 255));
        blueCorner.getPoints().addAll(
        		(double)scaleTriangle/2, 0.0,
        		(double)scaleTriangle/2+10.0, (double) 17.3,
        		(double)scaleTriangle/2-10.0, (double) 17.3							
        		);
        blueCorner.setStrokeWidth(0);
        
        triangleCanvas.getChildren().addAll(triangle, greenCorner, redCorner, blueCorner);
	}
	
	public static void drawSampleVector(int _t){
		dataCanvas.getChildren().clear(); 					// empty the data Pane
		subjectLines.clear();
		subjectCircles.clear();
		
		Point2D pointAFA = Sample.getAFA(_t);				// get AFA
		double afaX = pointAFA.x()*scaleTriangle+scaleTriangle/2;
		double afaY = pointAFA.y()*heightTriangle+heightTriangle*2/3;
		double[] colorAFA = getColor(pointAFA);				// get color for current AFA
															
		Circle circleAFA = new Circle(afaX,afaY,7);			// make, style and position Circle for AFA
		circleAFA.setStrokeWidth(1);
		circleAFA.setFill(Color.rgb((int) colorAFA[0], (int) colorAFA[1], (int) colorAFA[2]));
		circleAFA.setStroke(Color.WHITE);
		
		
		for (Subject sub: Sample.SubjectsList) {			// loop all subjects
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
