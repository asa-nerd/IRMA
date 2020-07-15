package irmaANALYSIS;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.*;
import math.geom2d.Point2D;
import irmaANALYSIS.GUI;
import static irmaANALYSIS.Visualizer.getColor;

import java.util.ArrayList;

public class triangleWidget {
	Sample s;
	
	VBox guiContainer;
	StackPane triangleContainer;
	static Pane triangleCanvas;
	static Pane dataCanvas;
	
	Canvas background, layer1;
	static GraphicsContext gB, gL;
	Image triangleImage;
	Pane canvasContainer; 
	static int scaleTriangle = 200;
	static int heightTriangle = (int) Math.round(Math.sqrt(3)/2 * scaleTriangle);
	
	static ArrayList<Circle> subjectCircles;
	static ArrayList<Line> subjectLines;
	static Circle circleAFA;
	
	triangleWidget(Sample _s){
		s = _s;
		canvasContainer = new Pane();
		
		guiContainer = new VBox();
		triangleContainer = new StackPane();
		triangleCanvas = new Pane();
		dataCanvas = new Pane();
		
		background = new Canvas(300, 300);
		layer1 = new Canvas(300, 300);
        gB = background.getGraphicsContext2D();
        gL = layer1.getGraphicsContext2D();
        
        
      //  triangleImage = new Image("file:data/colorTriangle.png");
        
        triangleContainer.getChildren().addAll(triangleCanvas, dataCanvas);
        
        canvasContainer.getChildren().addAll(background, layer1);
        GUI.rootLayout.setRight(triangleContainer);
        
        /*gB.clearRect(0, 0, background.getWidth(), background.getHeight());
        gB.drawImage(triangleImage, 0, 0, scaleTriangle, heightTriangle);
        gB.setStroke(Color.BLACK);
        gB.setLineWidth(1);
        */
        subjectCircles = new ArrayList<Circle>();
        subjectLines = new ArrayList<Line>();
        circleAFA = new Circle();
        
        makeBGTriangle();
	}
	
	public void makeBGTriangle() {
		Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
        		(double)scaleTriangle/2, 0.0,  
        		(double)scaleTriangle, (double)heightTriangle,
        		0.0, (double) heightTriangle);
        triangle.setFill(Color.GRAY);
        triangle.setStroke(Color.RED);
        triangleCanvas.getChildren().add(triangle);
	}
	
	public static void drawSampleVector(int _t){
		dataCanvas.getChildren().clear(); 					// empty the data Pane
		subjectLines.clear();
		subjectCircles.clear();
		
		Point2D pointAFA = Sample.getAFA(_t);				// get AFA
		double afaX = pointAFA.x()*scaleTriangle+scaleTriangle/2;
		double afaY = pointAFA.y()*heightTriangle+heightTriangle*2/3;
															
		Circle circleAFA = new Circle(afaX,afaY,7);			// make, style and position Circle for AFA
		circleAFA.setStrokeWidth(1);
		circleAFA.setFill(Color.BLUE);
		circleAFA.setStroke(Color.WHITE);
		
		
		for (Subject sub: Sample.SubjectsList) {			// loop all subjects
			Point2D p = sub.getPointByIndex(_t);
			double px = p.x()*scaleTriangle+scaleTriangle/2;
			double py = p.y()*heightTriangle+heightTriangle*2/3;
			
			Circle c = new Circle(px,py,1);				// make and style circle
			c.setStrokeType(StrokeType.OUTSIDE);
			c.setStrokeWidth(2);
			c.setFill(Color.BLACK);
			c.setStroke(Color.WHITE);
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
	
	public void updateSampleVector(int _t) {
		for (Subject sub: Sample.SubjectsList) {
			Point2D p = sub.getPointByIndex(_t);
			double px = p.x()*scaleTriangle+scaleTriangle/2;
			double py = p.y()*heightTriangle+heightTriangle*2/3;
			double[] c = getColor(p);										// get color for current subject
		}
	}
	
	public void updateSubject(int _nr, double[] color, double _x, double _y) {
		// get Subject
		// update position
	}

	
	public void clearCanvas() {
		gL.clearRect(0, 0, layer1.getWidth(), layer1.getHeight());
	}
	
	public static void drawSample(int _t) {
		
		for (Subject sub: Sample.SubjectsList) {
			Point2D p = sub.getPointByIndex(_t);
			double px = p.x()*scaleTriangle+scaleTriangle/2;
			double py = p.y()*heightTriangle+heightTriangle*2/3;
			//double px = (p.x()*100)+100;
			//double py = (p.y()*100)+87;
			double[] c = getColor(p);						// get color for current subject
			gL.setFill(Color.rgb((int) c[0], (int) c[1], (int) c[2]));
			gL.fillOval(px-3, py-3, 6, 6);
			gL.setFill(Color.BLACK);
			gL.fillOval(px-1, py-1, 2, 2);
		}
		
	}
	
	public static void drawAFA(int _t) {
		Point2D p = Sample.getAFA(_t);
		double px = p.x()*scaleTriangle+scaleTriangle/2;
		double py = p.y()*heightTriangle+heightTriangle*2/3;
		//double px = (p.x()*100)+100;
		//double py = (p.y()*100)+87;
		double[] c = getColor(p);
		gL.setFill(Color.rgb((int) c[0], (int) c[1], (int) c[2]));
		gL.fillOval(px-5, py-5, 10, 10);
		gL.setFill(Color.WHITE);
		gL.fillOval(px-1, py-1, 2, 2);
		gL.setStroke(Color.WHITE);
		gL.strokeOval(px-5, py-5, 10, 10);
	}
	
	public static void drawConnections(int _t) {
		Point2D pointAFA = Sample.getAFA(_t);
		double afaX = pointAFA.x()*scaleTriangle+scaleTriangle/2;
		double afaY = pointAFA.y()*heightTriangle+heightTriangle*2/3;
		//double afaX = (pointAFA.x()*100)+100;;
		//double afaY = (pointAFA.y()*100)+87;
		
		gL.setStroke(Color.WHITE);
		for (Subject sub: Sample.SubjectsList) {
			Point2D p = sub.getPointByIndex(_t);
			double px = p.x()*scaleTriangle+scaleTriangle/2;
			double py = p.y()*heightTriangle+heightTriangle*2/3;
			//double px = (p.x()*100)+100;
			//double py = (p.y()*100)+87;
			gL.strokeLine(afaX, afaY, px, py);
		}
	}
	
	
}
