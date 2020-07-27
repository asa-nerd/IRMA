package irmaANALYSIS;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

public class MenuBarFX extends MenuBar{
	
	MenuBar mb;
	Menu fileMenu, editMenu, controlMenu, analyzeMenu, layoutMenu;
	MenuItem f0, f1, f2, f3, f4, f5, c1, c2, c3, a1, a2, a3, a4, l0, l1; 
	VBox vb;
    
	MenuBarFX(Stage stage){
		MenuBar mb = new MenuBar();
		fileMenu = new Menu("File"); 
		editMenu = new Menu("Edit"); 
		controlMenu = new Menu("Control");
	    analyzeMenu = new Menu("Visualize");
	    layoutMenu = new Menu("Layout");
	    
	     f0=new MenuItem("Load Sample Data");
	     f1=new MenuItem("Clear Sample");
	     f2=new MenuItem("Load Project");
	     f3=new MenuItem("Save Project");
	     f4=new MenuItem("Load Video");
	     f5=new MenuItem("Quit");
	     c1=new MenuItem("Play");  
	     c2=new MenuItem("Pause");  
	     c3=new MenuItem("Stop");  
	     a1=new MenuItem("Sample – Average Focus");  
	     a2=new MenuItem("Sample – Activity ");  
	     a3=new MenuItem("Subjects – Focus");  
	     a4=new MenuItem("Subjects – Activity");
	     l0=new MenuItem("Standard Layout");  
	     l1=new MenuItem("Timeline Layout");
	     
	     f0.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
	     f5.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
	     l0.setAccelerator(KeyCombination.keyCombination("Ctrl+1"));
	     l1.setAccelerator(KeyCombination.keyCombination("Ctrl+2"));
	     
	     f0.setOnAction(new EventHandler<ActionEvent>() {							// Load Data Menu Function
	         public void handle(ActionEvent event) {
	           FileChooser fileChooser = new FileChooser();							// Open File Chooser
	           fileChooser.setTitle("Load Subject's Data");
	           List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
	           JSONParser parser = new JSONParser();
	           JSONArray jsonData = new JSONArray();
	           
	           if (fileList != null) {
	        	   for (File fs : fileList) {											 // loop through all selected files
		        	   try (Reader reader = new FileReader(fs)) {	        		   // Read selected file
		        		   try {
							 jsonData = (JSONArray) parser.parse(reader);				// Parse JSON
							 Subject s = new Subject(jsonData);							// Make new Subject	
							 GUI.s.addSubject(s);										// add Subject to Sample							 
							 GUI.updateSampleTable();									// update the Sample Table in GUI
		        		   } catch (ParseException e) {
								e.printStackTrace();
		        		   }
		        		   
		        	   }catch (IOException e) {
		        	   }
	        	   }
	           }
	         }
	       });
	     
	     f1.setOnAction(new EventHandler<ActionEvent>() {							// Load Data Menu Function
	         public void handle(ActionEvent event) {
	        	 GUI.s.clearSample();
	        	 GUI.visTemp.clearTimelines();
	        	 GUI.visSpat.clearSpatial();
	        	 GUI.updateSampleTable();
	        	 
	         }
	     });
	     f4.setOnAction(new EventHandler<ActionEvent>() {
	         public void handle(ActionEvent event) {
	        	 FileChooser fileChooser = new FileChooser();							// Open File Chooser
		         fileChooser.setTitle("Load Video");
		         File f = fileChooser.showOpenDialog(stage);
		         if (f != null) {
		        	 String fileAsString = f.toString();
		        	 GUI.makeVideo(fileAsString);
		         }
	         }
	       });
	     
	     f5.setOnAction(new EventHandler<ActionEvent>() {
	         public void handle(ActionEvent event) {
	           System.exit(0);
	         }
	       });
 
	     c1.setOnAction(e -> {
	    	 System.out.println("play");
	    	 
	     });
	     c2.setOnAction(e -> {
	    	 System.out.println("pause");
	     });
	     c3.setOnAction(e -> {
	    	 System.out.println("stop");
	     });
	     c1.setAccelerator(KeyCombination.keyCombination("Ctrl+Space"));
	     c2.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Space"));
	     c3.setAccelerator(KeyCombination.keyCombination("Alt+Space"));
	     l0.setOnAction(e -> {
	    	 GUI.topContainer.setVisible(true);
	    	 GUI.topContainer.setManaged(true);
	    	 GUI.bottomContainer.setPrefHeight(800);
	    	 
	     });
	     l1.setOnAction(e -> {
	    	 GUI.topContainer.setVisible(false);
	    	 GUI.topContainer.setManaged(false);
	     });
	     fileMenu.getItems().addAll(f0, f1, new SeparatorMenuItem(), f2, f3, new SeparatorMenuItem(), f4, new SeparatorMenuItem(), f5);
	     controlMenu.getItems().addAll(c1, c2, c3);
	     analyzeMenu.getItems().addAll(a1, a2, new SeparatorMenuItem(), a3, a4);
	     layoutMenu.getItems().addAll(l0,l1);

	    mb.getMenus().addAll(fileMenu, editMenu, controlMenu, analyzeMenu, layoutMenu);
	    vb = new VBox(mb);
	}
	
	public VBox getMainMenu() {
		return vb;
	}
}
