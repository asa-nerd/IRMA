package irmaANALYSIS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class timelineSection {
	double startTimeCode;
	double endTimeCode;
	double sectionLength;
	double stepSize;
	Rectangle r;
	Color markerColor = Color.rgb(230,220,100,0.2);
	Group g = new Group();
	
	timelineSection(double _startTimeCode, double _endTimeCode, double _stepSize){
		startTimeCode = _startTimeCode;
		endTimeCode = _endTimeCode;
		sectionLength = endTimeCode - startTimeCode;
		stepSize = _stepSize;
		r = new Rectangle(0,10, sectionLength*stepSize, 200);
		r.setFill(markerColor);
		g.getChildren().add(r);
		g.relocate(startTimeCode*stepSize, 10);
		System.out.println(startTimeCode +","+ endTimeCode);
	}
	
	public Group getSectionNode() {
		return g;
	}
	
	public void updateSectionPos(double _stepSizeNew) {
		g.relocate(startTimeCode*_stepSizeNew-(_stepSizeNew/4), 20);
		r.setWidth(sectionLength*_stepSizeNew);
	}
	
	/*
	public double getStart() {
		return startTimeCode;
	}
	public double getEnd() {
		return endTimeCode;
	}*/
}
