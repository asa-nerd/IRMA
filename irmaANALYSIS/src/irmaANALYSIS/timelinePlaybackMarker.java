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
		
		double currentPos;							// Variable to track the Position of the Playback Head
		double initialX;
		int timerPos;								// stores the current position of playback in timecode position of data
		Line playbackLine;
		Rectangle r;
		Label l = new Label("0");
		Group g = new Group();
		Color markerColor = Color.rgb(180,75,75);
		
	timelinePlaybackMarker(){
		initialX = 0;
		currentPos = 0;
		timerPos = 0;
		
		playbackLine = new Line(0,0,0,250);
        r = new Rectangle(0,0,40,20);
        r.relocate(0, 5);
		playbackLine.setStroke(markerColor);
		l.relocate(2, 7);
		l.getStyleClass().add("label");
        r.setFill(markerColor);
        g.getChildren().addAll(playbackLine, r, l);
        g.getStyleClass().add("playbackmarker");
        g.relocate(0, 20);
        
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
	
	public void updateTimerPos(int _t) {
		timerPos = _t;
	}
	
	public int getTimerPos() {
		return timerPos;
	}
	
	/*public double getPosition() {
		return currentPos;
	}*/
	
	public void moveTo(double posX) {
		g.relocate(posX, 20);	
		currentPos = posX;
	}
	
	public void setLabel(double _t) {
		//double t = (double) _t;
		//_t = _t/2;
		String txt = String.valueOf(_t);
		l.setText(txt);
	}

}
