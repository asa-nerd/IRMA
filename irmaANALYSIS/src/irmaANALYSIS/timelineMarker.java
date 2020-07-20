package irmaANALYSIS;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class timelineMarker {
	Line markerLine;
	Polygon MarkerTriangle = new Polygon();
	Rectangle r;
	Label l = new Label();
	Group g = new Group();
	
	timelineMarker(double _xPos){
		
		Color markerColor = Color.rgb(230,220,100);
		Polygon MarkerTriangle = new Polygon();
		MarkerTriangle.getPoints().addAll(0.0, 0.0, -5.0,-10.0, +5.0,-10.0);
		MarkerTriangle.setFill(markerColor);
		r = new Rectangle(0,0,30,15);
		r.setFill(markerColor);
		l.setTextFill(Color.BLACK);
		l.getStyleClass().add("label");
		l.setText(String.valueOf(_xPos));
		markerLine = new Line(0,0,0,200);
		markerLine.setStroke(markerColor);
		g.getChildren().addAll(MarkerTriangle,markerLine, r, l);
		g.getStyleClass().add("marker");
        g.relocate(_xPos, 20);
	}
	
	public Group getMarkerNode() {
		return g;
	}
	
	
}