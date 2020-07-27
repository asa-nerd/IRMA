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
	}
	
	public Group getScaleNode() {
		return g;
	}
	
	public void updateScale(double _width, double _stepSize) {
		bgRect.setWidth(_width);
		labelList.forEach((l) -> {l.setVisible(false);});
		lineList.forEach((l) -> {l.setVisible(false);});
		
		int viewStep = 0;
		if (_stepSize < 1) {
			viewStep = 4;
		}else if(_stepSize >= 1 && _stepSize < 3) {
			viewStep = 2;			
		}else if(_stepSize >= 3) {
			viewStep = 1;			
		}
		
		for(int i = 0; i < labelList.size(); i += viewStep) {
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
