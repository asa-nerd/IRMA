package irmaANALYSIS;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import math.geom2d.Point2D;

public class timelineSubjectsAttention extends timeline{
	ArrayList<ArrayList<Line>> SubjectLines;				// ArrayList to hold the Line-Arraylist for each Subject
	
	
	timelineSubjectsAttention(Sample _s, int _id){
		super(_s, _id);
		SubjectLines = new ArrayList<ArrayList<Line>>();
		
		for(Subject sub : _s.SubjectsList) {
			ArrayList<Line> lines = new ArrayList<Line>();
			SubjectLines.add(lines);
		}
		drawScale(0, s.getShortestDataset());
		drawTimeline(0, s.getShortestDataset());
	}
	
	@Override
	public void updateTimeline() {
		for (int k = 0; k < s.SubjectsList.size(); k ++) {
		   	Subject sub = s.getSubject(k);
		   	ArrayList<Line> subjectLines = SubjectLines.get(k);
			for (int i = 0; i < subjectLines.size(); i++) {
				Line l = subjectLines.get(i);
				l.setStartX(i*stepSize+0.5);
				l.setEndX(i*stepSize+0.5);
				l.setStrokeWidth(1*zoomFactor);
			}
			layerContainer.setPrefWidth(subjectLines.size()*zoomFactor*2);
		}
	}
	
	@Override
	public void drawTimeline(int _begin, int _end) {
		
	   // General definitions
	   //playbackLayer.getChildren().add(playbackLine);
	   int rangeLength = _end - _begin;
	   double originX =  40;
	   stepSize = zoomFactor*2;
	   layerContainer.setPrefWidth(rangeLength * stepSize);

	   // Definitions specific for timeline "Subjects Activity"
	   for (int k = 0; k < s.SubjectsList.size(); k ++) {
		   	Subject sub = s.getSubject(k);
		   	ArrayList<Line> subjectLines = SubjectLines.get(k);
		    for (int i = _begin; i < _end; i = i + 1) {
		    	//double lHeight = sub.getActivity(i)*200;												// get line height as current Activity
		    	Point2D currentA = sub.getPointByIndex(i);														// get measuring point of subject
		    	double[] c = this.getColor(currentA);													// get color of measuring point
		    	Line line = new Line(i*stepSize+0.5, originX, i*stepSize+0.5, originX-5);		 		// make lines
				line.setStroke(Color.rgb((int) c[0],(int) c[1],(int) c[2]));													// set color for line
				line.setStrokeWidth(1*zoomFactor);
				line.getProperties().put("timeCode", i);
				line.getProperties().put("color", c);
				subjectLines.add(line);
		    }
		    // add Rollover and Clickability
			makeRollovers(subjectLines);
		    dataLayer.getChildren().addAll(subjectLines);				// add all line Nodes to parent Pane
		    originX += 20;
	   }
	   dataLayer.setPrefWidth(rangeLength*zoomFactor*2);
		
	}
}
