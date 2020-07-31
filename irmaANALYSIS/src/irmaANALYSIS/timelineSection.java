package irmaANALYSIS;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class timelineSection {
	double startTimeCode;
	double endTimeCode;
	double sectionLength;
	double stepSize;
	
	double startXScreen;					// Variables for dragging interaction
    double startXScrollPane;
    
	Label endLabel;
	Rectangle background;
	Rectangle grip;
	Color markerColor = Color.rgb(230,220,100,0.2);
	Color gripColor = Color.rgb(230,220,100);
	Group g = new Group();
	
	timelineSection(double _startTimeCode, double _endTimeCode, double _stepSize){
		startTimeCode = _startTimeCode;
		endTimeCode = _endTimeCode;
		sectionLength = endTimeCode - startTimeCode;
		stepSize = _stepSize;
		endLabel = new Label(String.valueOf(endTimeCode));
		endLabel.getStyleClass().add("label");
		endLabel.relocate(sectionLength*stepSize-40, 180);
		background = new Rectangle(0,0, sectionLength*stepSize, 190);
		background.setFill(markerColor);
		background.getStyleClass().add("background");
		grip = new Rectangle(sectionLength*stepSize-2.5, 0, 5, 190);
		grip.getStyleClass().add("grip");
		grip.setFill(gripColor);
		g.getChildren().addAll(background, grip, endLabel);
		g.getStyleClass().add("section");
		g.relocate(startTimeCode*stepSize, 25);
		
		grip.setOnMousePressed(evt ->{
        	startXScreen = evt.getScreenX();
        	startXScrollPane = g.getBoundsInParent().getMaxX()-5;
        });
		grip.setOnMouseDragged(evt -> {
        	double distance = evt.getScreenX() - startXScreen;
        	double newXPixelPos = startXScrollPane + distance;
        	endTimeCode = Math.round(newXPixelPos/stepSize);
        	if (endTimeCode >= startTimeCode + 30) {
	        	endLabel.setText(String.valueOf(endTimeCode));
	        	sectionLength = endTimeCode - startTimeCode;
	        	background.setWidth(sectionLength*stepSize);
	        	endLabel.relocate(sectionLength*stepSize-40, 0);
	        	grip.relocate(sectionLength*stepSize, 0);
	        	//GUI.visSpat.drawSection((int) startTimeCode, (int) endTimeCode); // TODO
        	}
        	
        });
	}
	
	public Group getSectionNode() {
		return g;
	}
	
	public void updateSectionPos(double _stepSizeNew) {
		g.relocate(startTimeCode*_stepSizeNew, 20);
		grip.relocate(sectionLength*_stepSizeNew-2.5, 0);
		endLabel.relocate(sectionLength*_stepSizeNew-40, 180);
		background.setWidth(sectionLength*_stepSizeNew);
	}
	
	public void moveSection(double _newTimeCode) {
		g.relocate(_newTimeCode*stepSize, 25);
		startTimeCode = _newTimeCode;
		endTimeCode = startTimeCode+sectionLength;
		endLabel.setText(String.valueOf(endTimeCode));
		
	}
	
	public void synchronizeStepSize(double _stepSizeNew) {
		stepSize = _stepSizeNew;
	}
	
	public double getStartTimeCode() {
		return startTimeCode;
	}
	public double getEndTimeCode() {
		return endTimeCode;
	}
}
