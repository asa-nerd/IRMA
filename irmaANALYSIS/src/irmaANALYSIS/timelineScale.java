package irmaANALYSIS;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class timelineScale {
	Group g;
	Rectangle bgRect;
	
	int sampleDataLength;
	int firstVisibleTimeCode;
	int lastVisibleTimeCode;
	int labelTimeCodeInterval = 10;
	
	ArrayList<Label> labelList;
	ArrayList<Line> lineList;
	
	timelineScale(int _sampleDataLength){
		sampleDataLength = _sampleDataLength;
		g = new Group();
		labelList = new ArrayList<Label>();
		lineList = new ArrayList<Line>();
		
		bgRect = new Rectangle(0,0,1200,20);
		bgRect.setFill(Color.WHITE);
		g.getChildren().add(bgRect);
		g.getStyleClass().add("scale");
		for(int i = 0; i < sampleDataLength; i +=labelTimeCodeInterval) {
			Label l = new Label(String.valueOf(i));
			Line li = new Line(i*2,15,i*2,20);
			li.setStroke(Color.rgb(112, 112, 112));
			l.getStyleClass().add("label");
			l.relocate(i*2, 5);
			labelList.add(l);
			lineList.add(li);
		}
		g.getChildren().addAll(labelList);
		g.getChildren().addAll(lineList);

		
		g.setOnMousePressed(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent e) {
	        	//makeMarker(e.getX());
	        	System.out.println("Marker");
	         }

	     });
		
	}
	
	public Group getScaleNode() {
		return g;
	}
	

	
	public void updateScale(double _width, double _stepSize) {
		bgRect.setWidth(_width);
		System.out.println(_stepSize);
		
		for(int i = 0; i < labelList.size(); i +=1) {
			Label l = labelList.get(i);
			Line li = lineList.get(i);
			l.setVisible(false);
			li.setVisible(false);
		}
		
		if (_stepSize < 1) {
			for(int i = 0; i < labelList.size(); i +=4) {
				Label l = labelList.get(i);
				Line li = lineList.get(i);
				l.setVisible(true);
				li.setVisible(true);
				l.relocate(i*_stepSize*labelTimeCodeInterval, 5);
				li.setStartX(i*_stepSize*labelTimeCodeInterval);
				li.setEndX(i*_stepSize*labelTimeCodeInterval);
			}
		}else if(_stepSize >= 1 && _stepSize < 3) {
			for(int i = 0; i < labelList.size(); i +=2) {
				Label l = labelList.get(i);
				Line li = lineList.get(i);
				l.setVisible(true);
				li.setVisible(true);
				l.relocate(i*_stepSize*labelTimeCodeInterval, 5);
				li.setStartX(i*_stepSize*labelTimeCodeInterval);
				li.setEndX(i*_stepSize*labelTimeCodeInterval);
			}
			
		}else if(_stepSize >= 3) {
			for(int i = 0; i < labelList.size(); i +=1) {
				Label l = labelList.get(i);
				Line li = lineList.get(i);
				l.setVisible(true);
				li.setVisible(true);
				l.relocate(i*_stepSize*labelTimeCodeInterval, 5);
				li.setStartX(i*_stepSize*labelTimeCodeInterval);
				li.setEndX(i*_stepSize*labelTimeCodeInterval);
			}
			
		}
		
	}
}
