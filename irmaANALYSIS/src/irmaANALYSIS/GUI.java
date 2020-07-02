package irmaANALYSIS;

import javafx.scene.control.*;
import javafx.scene.layout.*;

// left: Subjects
// center: Video
// right: triangle
// bottom: timeline

public class GUI {
	BorderPane rootLayout;
	VBox left, right, center, bottom;
	Button b1, b2;
	
	GUI(){
		rootLayout = new BorderPane();
		left = new VBox();
		right = new VBox();
		center = new VBox();
		left.setStyle("-fx-background-color: #00ff00");
		left.setMinWidth(300.0);
		left.setPrefWidth(300.0);
		left.setMaxWidth(300.0);
		left.setMinHeight(200.0);
		left.setPrefHeight(400.0);
		left.setMaxHeight(600.0);
		b1 = new Button("Play");
		b2 = new Button("Pause");
		bottom = new VBox(b1, b2);
		rootLayout.setLeft(left);
		rootLayout.setRight(right);
		rootLayout.setCenter(center);
		rootLayout.setBottom(bottom);
		//rootLayout.getLef
	}
	
	public void setSize(long w, long h) {
		
	}
}
