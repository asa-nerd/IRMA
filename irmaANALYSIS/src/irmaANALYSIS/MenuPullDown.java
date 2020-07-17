package irmaANALYSIS;

import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;


/*
  MenuButton
   |——— label (LabeledImpl)
   |——— arrowButton (StackPane)
            |——— arrow (StackPane)
 */
public class MenuPullDown extends MenuButton {

	MenuPullDown(String s){
		super(s);
		this.setPrefSize(90, 20);
		this.getStyleClass().add("MenuPullDown");
	}
}
