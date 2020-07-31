package irmaANALYSIS;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class timelineMarker {
	Line markerLine;
	int id;
	double stepSize;
	double xPosTimeCode;
	double sectionStartTC;
	double sectionEndTC;
	
	Polygon MarkerTriangle = new Polygon();
	Rectangle r;
	Label l = new Label();
	Group g = new Group();
	
	timelineSection section;
	
	//double clickOffsetX;
	double startXScreen;					// Variables for dragging interaction
    double startXScrollPane;
	
	timelineMarker(double _xPos, int _id, double _stepSize){
		xPosTimeCode = _xPos;
		id = _id;
		stepSize = _stepSize;
		Color markerColor = Color.rgb(230,220,100);
		Polygon MarkerTriangle = new Polygon();
		MarkerTriangle.getPoints().addAll(0.0, 0.0, -5.0,-5.0, +5.0,-5.0);
		MarkerTriangle.setFill(markerColor);
		r = new Rectangle(0,200,30,220);
		r.setFill(markerColor);
		l.setTextFill(Color.BLACK);
		l.relocate(0, 200);
		l.getStyleClass().add("label");
		l.setText(String.valueOf(xPosTimeCode));
		markerLine = new Line(0,0,0,200);
		markerLine.setStroke(markerColor);
		g.getChildren().addAll(MarkerTriangle,markerLine, r, l);
		
		g.getStyleClass().add("marker");
        g.relocate(xPosTimeCode*_stepSize-5, 15);
        
        
        g.setOnMousePressed(evt ->{
        	startXScreen = evt.getScreenX();
        	startXScrollPane = g.getBoundsInParent().getMinX();
        });
        g.setOnMouseDragged(evt -> {
        	double distance = evt.getScreenX() - startXScreen;
        	double newXPixelPos = startXScrollPane + distance;
        	xPosTimeCode = Math.round(newXPixelPos/stepSize);
        	if (xPosTimeCode < 0) {
        		xPosTimeCode = 0;
        	}
        	if (xPosTimeCode > GUI.s.getShortestDataset()) {
        		xPosTimeCode = GUI.s.getShortestDataset();
        	}
        	l.setText(String.valueOf(xPosTimeCode));
        	g.relocate(xPosTimeCode*stepSize-5, 15);
        	if (section != null) {
        		section.synchronizeStepSize(stepSize);
        		section.moveSection(xPosTimeCode);
        		//GUI.visSpat.drawSection((int) xPosTimeCode, (int) (xPosTimeCode+section.getEndTimeCode())); //TODO
        	}
        	
        });
	}
	
	public Group getMarkerNode() {
		return g;
	}
	
	//public void setSection(double _startTC, double _endTC) {
	public void setSection(timelineSection _ts) {
		section = _ts;
	}
	
	public timelineSection getSection() {
		return section;
	}
	
	public void clearSection() {
		section = null;
	}
	
	public double getSectionStartTC() {
		return sectionStartTC;
	}
	
	public double getSectionEndTC() {
		return sectionEndTC;
		
	}
	
	public void makeSectionContextMenu(ContextMenu _contextMenu) {
		l.setContextMenu(_contextMenu); 
	}
	
	public void synchronizeStepSize(double _stepSizeNew) {
		stepSize = _stepSizeNew;
		if (section != null) {
			section.synchronizeStepSize(_stepSizeNew);
		}
	}
	
	public void updateMarkerPos(double _stepSizeNew) {
		g.relocate(xPosTimeCode*_stepSizeNew-5, 20);
		stepSize = _stepSizeNew;
	}
	
	public double getMarkerTimeCode() {
		return xPosTimeCode;
	}
		
}