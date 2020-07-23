package irmaANALYSIS;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import math.geom2d.Point2D;

public class timelineAFA extends timeline{
	ArrayList<Line> lines;
	ArrayList<Double> linieLengths;
	double originY =  120;
	
	timelineAFA(Sample _s, int _id){
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
			l.setStartY(originY+len*yScale);
			l.setEndY(originY-len*yScale);
			l.setStartX(i*stepSize+0.5);
			l.setEndX(i*stepSize+0.5);
			l.setStrokeWidth(1*zoomFactor);
		}
		layerContainer.setPrefWidth(lines.size()*zoomFactor*2);	// Adjust the width of the container
	
	}
	
	@Override
	public void drawTimeline(int _begin, int _end) {			// function to draw the standard timeline
		
		// General definitions
	    int rangeLength = _end - _begin;
	    stepSize = zoomFactor*2;
	    layerContainer.setPrefWidth(rangeLength * stepSize);

	    // Definitions specific for timeline "Average Focus of Attention"
		for (int i = _begin; i < _end; i = i + 1) {
			double lHeight = s.getDOA(i) *200;					// get line height as current Deviation of Attention
			linieLengths.add(lHeight);
			Point2D currentAFA = s.getAFA(i);					// get Vector of current Average Focus of Attention
			double[] c = this.getColor(currentAFA);				// get color of current AFA
			Line line = new Line(i*stepSize+0.5, originY+lHeight*yScale, i*stepSize+0.5, originY-lHeight*yScale); // make lines
			line.getProperties().put("timeCode", i);
			line.getProperties().put("color", c);

			line.setStroke(Color.rgb((int) c[0], (int) c[1], (int) c[2]));	// set color for line
			line.setStrokeWidth(1*zoomFactor);
			
			lines.add(line);
		}
		
		// add Rollover and Clickability
		makeRollovers(lines);
		
		// Add objects to timeline
		dataLayer.getChildren().addAll(lines);					// add all line Nodes to parent Pane
		dataLayer.setPrefWidth(rangeLength*zoomFactor*2);
	}		
}
