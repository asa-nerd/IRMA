package irmaANALYSIS;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuBarFX {
	
	MenuBar mb;
	Menu fileMenu, controlMenu, analyzeMenu;
	MenuItem f1, f2, f3, f4, c1, c2, c3, a1, a2, a3, a4; 
	VBox vb;
    
	MenuBarFX(Stage stage){
		MenuBar mb = new MenuBar();
		fileMenu = new Menu("Project"); 
		controlMenu = new Menu("Control");
	    analyzeMenu = new Menu("Analyze");
		
	    
	     f1=new MenuItem("Load Sample Data");  
	     f2=new MenuItem("Load Project");
	     f3=new MenuItem("Save Project");
	     f4=new MenuItem("Quit");
	     c1=new MenuItem("Play");  
	     c2=new MenuItem("Pause");  
	     c3=new MenuItem("Stop");  
	     a1=new MenuItem("Sample – Average Focus");  
	     a2=new MenuItem("Sample – Activity ");  
	     a3=new MenuItem("Subjects – Focus");  
	     a4=new MenuItem("Subjects – Activity");
	     
	     f1.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
	     f4.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
	     f1.setOnAction(new EventHandler<ActionEvent>() {
	         public void handle(ActionEvent event) {
	           System.out.println("Load Sample Data");
	           FileChooser fileChooser = new FileChooser();
	           fileChooser.setTitle("Load Sample Data");
	           fileChooser.showOpenDialog(stage);
	         }
	       });
	     
	     f4.setOnAction(new EventHandler<ActionEvent>() {
	         public void handle(ActionEvent event) {
	           System.out.println("Quit");
	           System.exit(0);
	         }
	       });
	    
	     fileMenu.getItems().addAll(f1, new SeparatorMenuItem(), f2, f3, new SeparatorMenuItem(), f4);
	     controlMenu.getItems().addAll(c1, c2, c3);
	     analyzeMenu.getItems().addAll(a1, a2, new SeparatorMenuItem(), a3, a4);

	    mb.getMenus().addAll(fileMenu, controlMenu, analyzeMenu);
	    vb = new VBox(mb);
	}
	
	public VBox getMainMenu() {
		return vb;
	}
}
