package irmaANALYSIS;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class timelinePlaybackMarker {
		Line playbackLine;
		Rectangle r;
		Label l = new Label("10 s");
		Group g = new Group();
		Color markerColor = Color.rgb(180,75,75);
		double initialX;
	     
	timelinePlaybackMarker(){
		initialX = 0;
		
		
		playbackLine = new Line(0,0,0,200);
        r = new Rectangle(0,0,40,20);
		playbackLine.setStroke(markerColor);
		l.relocate(2, 2);
		l.getStyleClass().add("label");
        r.setFill(markerColor);
        g.getChildren().addAll(playbackLine, r, l);
        g.getStyleClass().add("playbackmarker");
        g.relocate(0, 40);
        
        g.setOnMouseEntered(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent e) {
        		playbackLine.setStroke(Color.WHITE);
        	}
        	
        });
        g.setOnMouseExited(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent e) {
        		playbackLine.setStroke(Color.RED);
        	}
        	
        });

        g.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
               // initialX = g.getLayoutX();
                //initialX = g.getBoundsInParent().getMinX();
                Bounds b = g.getBoundsInLocal();
                Bounds screenBounds = g.localToScreen(b);
               // initialX= screenBounds.getMinX();
              }
 
          });
        g.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
            	//playbackLine.getScene().getWindow().setX(me.getScreenX() - initialX);
            	//playbackLine.getScene().getWindow().setY(me.getScreenY() - initialY);
            	//playbackLine.setEndX(me.getScreenX() - initialX);
            	//playbackLine.setStartX(me.getScreenX() - initialX);
            	//moveTo(me.getScreenX()-150);
            	moveTo(me.getX());
   
            }
         });
        g.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
            	initialX = me.getX();
            }
        });
            
	}
	
	public Group getMarkerNode() {
		return g;
	}
	
	public void moveTo(double posX) {
		g.relocate(posX, 40);	
	}
	
	public void setLabel(double _t) {
		//double t = (double) _t;
		_t = _t/2;
		String txt = String.valueOf(_t);
		l.setText(txt);
	}

}
