package irmaANALYSIS;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import math.geom2d.Point2D;

public class timelineAFA extends timeline{
	
	ArrayList<Line> lines;
	
	timelineAFA(Sample _s){
		super(_s);
		        
        lines = new ArrayList<Line>();
        drawTimeline(0, s.getShortestDataset());
	}
	
	@Override
	public void updateTimeline() {
		for (int i = 0; i < lines.size(); i++) {
			Line l = lines.get(i);
			l.setStartX(i*stepSize+0.5);
			l.setEndX(i*stepSize+0.5);
			l.setStrokeWidth(1*zoomFactor);
		}
		layerContainer.setPrefWidth(lines.size()*zoomFactor*2);
	
	}
	
	@Override
	public void drawTimeline(int _begin, int _end) {			// function to draw the standard timeline
		
		playbackLayer.getChildren().add(playbackLine);
		//timelineCanvas.setPrefSize(1200,400);
	    int rangeLength = _end - _begin;
	    double originX =  120;
	    stepSize = zoomFactor*2;
	    layerContainer.setPrefWidth(rangeLength * stepSize);

		for (int i = _begin; i < _end; i = i + 1) {
			double lHeight = s.getDOA(i)*200;					// get line height as current Deviation of Attention
			Point2D currentAFA = s.getAFA(i);					// get Vector of current Average Focus of Attention
			double[] c = this.getColor(currentAFA);				// get color of current AFA
			Line line = new Line(i*stepSize+0.5, originX+lHeight, i*stepSize+0.5, originX-lHeight); // make lines
			line.setStroke(Color.rgb((int) c[0], (int) c[1], (int) c[2]));	// set color for line
			line.setStrokeWidth(1*zoomFactor);
			lines.add(line);
		}		
		dataLayer.getChildren().addAll(lines);				// add all line Nodes to parent Pane
		dataLayer.setPrefWidth(rangeLength*zoomFactor*2);
	}		
}
