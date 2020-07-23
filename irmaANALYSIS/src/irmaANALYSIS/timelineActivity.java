package irmaANALYSIS;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import math.geom2d.Point2D;

public class timelineActivity extends timeline{
	ArrayList<Line> lines;
	ArrayList<Double> linieLengths;
	double originY =  240;
	
	timelineActivity(Sample _s, int _id){
		super(_s, _id);
		lines = new ArrayList<Line>();
		linieLengths = new ArrayList<Double>();
		drawScale(0, s.getShortestDataset());
		drawTimeline(0, s.getShortestDataset());
	}
	
	@Override
	public void updateTimeline() {
		for (int i = 0; i < lines.size(); i++) {
			double len = linieLengths.get(i);
			Line l = lines.get(i);
			l.setEndY(originY-len*yScale);
			l.setStartX(i*stepSize+0.5);
			l.setEndX(i*stepSize+0.5);
			l.setStrokeWidth(1*zoomFactor);
		}
		layerContainer.setPrefWidth(lines.size()*zoomFactor*2);
	}
	
	@Override
	public void drawTimeline(int _begin, int _end) {
		
		// General definitions
		//playbackLayer.getChildren().add(playbackLine);
	    int rangeLength = _end - _begin;
	    stepSize = zoomFactor*2;
	    layerContainer.setPrefWidth(rangeLength * stepSize);

	    // Definitions specific for timeline "Activity"
	    for (int i = _begin; i < _end; i = i + 1) {
	    	double lHeight = s.getActivity(i)*200;					// get line height as current Deviation of Attention
			linieLengths.add(lHeight);
	    	Line line = new Line(i*stepSize+0.5, originY, i*stepSize+0.5, originY-lHeight*yScale); // make lines
			line.setStroke(Color.rgb(255,255,255));													// set color for line
			line.setStrokeWidth(1*zoomFactor);
			lines.add(line);
	    }
			    
	    // Add objects to timeline
		dataLayer.getChildren().addAll(lines);				// add all line Nodes to parent Pane
		dataLayer.setPrefWidth(rangeLength*zoomFactor*2);
		
	}
}