package irmaANALYSIS;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class timelineMarker {
	Line markerLine;
	int id;
	double xPosTimeCode;

	Polygon MarkerTriangle = new Polygon();
	Rectangle r;
	Label l = new Label();
	Group g = new Group();
	
	timelineMarker(double _xPos, double _stepSize){
		xPosTimeCode = _xPos;
		Color markerColor = Color.rgb(230,220,100);
		Polygon MarkerTriangle = new Polygon();
		MarkerTriangle.getPoints().addAll(0.0, 0.0, -10.0,-10.0, +10.0,-10.0);
		MarkerTriangle.setFill(markerColor);
		r = new Rectangle(0,185,30,200);
		r.setFill(markerColor);
		l.setTextFill(Color.BLACK);
		l.relocate(0, 185);
		l.getStyleClass().add("label");
		l.setText(String.valueOf(xPosTimeCode));
		markerLine = new Line(0,0,0,200);
		markerLine.setStroke(markerColor);
		g.getChildren().addAll(MarkerTriangle,markerLine, r, l);
		g.getStyleClass().add("marker");
        g.relocate(xPosTimeCode*_stepSize-(_stepSize/4), 20);

        // create the context menu 
        ContextMenu contextMenu = new ContextMenu();
        MenuItem cm1 = new MenuItem("Make new Section"); 
        MenuItem cm2 = new MenuItem("Delete Section"); 
        MenuItem cm3 = new MenuItem("Average of Section"); 
        MenuItem cm4 = new MenuItem("Display Section in Spatial Visualizer");
        MenuItem cm5 = new MenuItem("Delete Marker");
        contextMenu.getItems().addAll(cm1,cm2,cm3, cm4, cm5); 
        l.setContextMenu(contextMenu); 
        
        cm1.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent e) {
	            System.out.println("Make new Section");
	            timelineSection s = new timelineSection(xPosTimeCode, xPosTimeCode+30);
	            //sectionLayer.
	        }
	    });
        
        cm2.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent e) {
	            System.out.println("Delete Section");
	        }
	    });
        
        cm3.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent e) {
	            System.out.println("Average of Section");
	        }
	    });
    
        cm4.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent e) {
	            System.out.println("Display Section in Spatial");
	        }
	    });
        
        cm5.setOnAction(new EventHandler<ActionEvent>() { public void handle(ActionEvent e) {
                System.out.println("Delete");
            }
        });

	}
	
	public Group getMarkerNode() {
		return g;
	}
	
	public void updateMarkerPos(double _stepSizeNew) {
		g.relocate(xPosTimeCode*_stepSizeNew-(_stepSizeNew/4), 20);
	}
	
	public double getMarkerX() {
		return xPosTimeCode;
	}
		
}