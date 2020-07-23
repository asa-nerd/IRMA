package irmaANALYSIS;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class timelineSection {
	double startTimeCode;
	double endTimeCode;
	double sectionLength;
	Rectangle r;
	Color markerColor = Color.rgb(180,75,75,0.2);
	Group g = new Group();
	
	timelineSection(double _startTimeCode, double _endTimeCode){
		startTimeCode = _startTimeCode;
		endTimeCode = _endTimeCode;
		sectionLength = endTimeCode - startTimeCode;
		r = new Rectangle(0,10, sectionLength, 200);
		r.setFill(markerColor);
		g.getChildren().add(r);
		g.relocate(startTimeCode, 10);
		System.out.println(startTimeCode +","+ endTimeCode);
	}
	
	public Group getSectionNode() {
		return g;
	}
	
	public void updateSectionPos(double _stepSizeNew) {
		g.relocate(startTimeCode*_stepSizeNew-(_stepSizeNew/4), 20);
	}
	
	/*
	public double getStart() {
		return startTimeCode;
	}
	public double getEnd() {
		return endTimeCode;
	}*/
}
