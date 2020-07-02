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
		bottom = new VBox();
		rootLayout.setLeft(left);
		rootLayout.setRight(right);
		rootLayout.setCenter(center);
		rootLayout.setBottom(bottom);
		b1 = new Button();
		b2 = new Button();
	}
}
