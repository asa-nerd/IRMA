package irmaANALYSIS;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class MenuBarFX {
	
	MenuBar mb;
	Menu fileMenu, controlMenu, analyzeMenu;
	MenuItem f1, f2, c1, c2, c3, a1, a2, a3, a4; 
	VBox vb;
    
	MenuBarFX(){
		MenuBar mb = new MenuBar();
		fileMenu = new Menu("Menu"); 
		controlMenu = new Menu("Control");
	    analyzeMenu = new Menu("Analyze");
		
		//m1 = new MenuItem("menu item 1"); 
	    //m2 = new MenuItem("menu item 2"); 
	    //m3 = new MenuItem("menu item 3"); 
	    
	    f1=new MenuItem("Load Sample");  
	     f2=new MenuItem("Quit");  
	     c1=new MenuItem("Play");  
	     c2=new MenuItem("Pause");  
	     c3=new MenuItem("Stop");  
	     a1=new MenuItem("Sample – Average Focus");  
	     a2=new MenuItem("Sample – Activity ");  
	     a3=new MenuItem("Subjects – Focus");  
	     a4=new MenuItem("Subjects – Activity");
	    
	     fileMenu.getItems().add(f1); 
	     fileMenu.getItems().add(f2); 
	     controlMenu.getItems().add(c1); 
	     controlMenu.getItems().add(c2);
	     controlMenu.getItems().add(c3);
	     analyzeMenu.getItems().add(a1);
	     analyzeMenu.getItems().add(a2);
	     analyzeMenu.getItems().add(a3);
	     analyzeMenu.getItems().add(a4);
	    mb.getMenus().add(fileMenu); 
	    mb.getMenus().add(controlMenu); 
	    mb.getMenus().add(analyzeMenu); 
	    vb = new VBox(mb);
	}
	
	public VBox getMainMenu() {
		return vb;
	}
}
