package irmaANALYSIS;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MenuBarFX extends MenuBar{
	
	MenuBar mb;
	Menu fileMenu, editMenu, controlMenu, analyzeMenu, layoutMenu;
	MenuItem f1, f2, f3, f4, f5, f6, c1, c2, c3, a1, a2, a3, a4, l0, l1; 
	VBox vb;
    
	MenuBarFX(Stage stage){
		MenuBar mb = new MenuBar();
		fileMenu = new Menu("File"); 
		editMenu = new Menu("Edit"); 
		controlMenu = new Menu("Control");
	    analyzeMenu = new Menu("Visualize");
	    layoutMenu = new Menu("Layout");
	    
	    
	     f1=new MenuItem("New Project");
	     f2=new MenuItem("Load Project");
	     f3=new MenuItem("Save Project");
	     f4=new MenuItem("Load Sample Data");
	     f5=new MenuItem("Load Video");
	     f6=new MenuItem("Quit");

	     c1=new MenuItem("Play");  
	     c2=new MenuItem("Pause");  
	     c3=new MenuItem("Stop");  
	     a1=new MenuItem("Sample – Average Focus");  
	     a2=new MenuItem("Sample – Activity ");  
	     a3=new MenuItem("Subjects – Focus");  
	     a4=new MenuItem("Subjects – Activity");
	     l0=new MenuItem("Standard Layout");  
	     l1=new MenuItem("Timeline Layout");
	     
	     f2.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
	     f6.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
	     l0.setAccelerator(KeyCombination.keyCombination("Ctrl+1"));
	     l1.setAccelerator(KeyCombination.keyCombination("Ctrl+2"));
	     
	     
	     
	     f1.setOnAction(new EventHandler<ActionEvent>() {							// New Project Function
	         public void handle(ActionEvent event) {
	        	 GUI.s.clearSample();
	        	 GUI.visTemp.clearTimelines();
	        	 GUI.visSpat.clearSpatial();
	        	 GUI.updateSampleTable();    	 
	         }
	     });
	     
	     f2.setOnAction(new EventHandler<ActionEvent>() {							// Load Project Function
	         public void handle(ActionEvent event) {
	        	 GUI.s.clearSample();
	        	 GUI.visTemp.clearTimelines();
	        	 GUI.visSpat.clearSpatial();
	        	 GUI.updateSampleTable();
	        	 
	        	 FileChooser fileChooser = new FileChooser();							// Open File Chooser
		           fileChooser.setTitle("Load Subject's Data");
		           File file = fileChooser.showOpenDialog(stage);
		           JSONParser parser = new JSONParser();
		           JSONArray jsonData = new JSONArray();
		           
		           if (file != null) {
		        	   try (Reader reader = new FileReader(file)) {	        		   // Read selected file
		        		   try {
							 jsonData = (JSONArray) parser.parse(reader);				// Parse JSON
							// Subjects
							 // Timelines
							 // Markers
							 // Sections
							 
							 // Subject s = new Subject(jsonData);							// Make new Subject	
							// GUI.s.addSubject(s);										// add Subject to Sample							 
							// GUI.updateSampleTable();									// update the Sample Table in GUI
		        		   } catch (ParseException e) {
								e.printStackTrace();
		        		   }
		        		   
		        	   }catch (IOException e) {
		        	   }		        	   
		           }
	        	 
	         }
	     });
	     
	     f3.setOnAction(new EventHandler<ActionEvent>() {							// Save Project Function
	         public void handle(ActionEvent event) {
	        	 FileChooser fileChooser = new FileChooser();
	             fileChooser.setTitle("Save");
	             fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
	             File file = fileChooser.showSaveDialog(stage);
	             JSONObject projecttData = new JSONObject();
	             final JSONArray subjectArray = new JSONArray();
	             final JSONArray timelineArray = new JSONArray();
	             final JSONArray markerArray = new JSONArray();
	             
	             GUI.s.SubjectsList.forEach((s) ->{
	            	 subjectArray.add(s.JSONData);
	            	 
	             });
	             
	           /*  GUI.visTemp.timelines.forEach((tl) ->{
	            	 tl.getMarkerList();
	             });*/
	               
	             projecttData.put("subjects", subjectArray);
	             //projecttData.put("timelines", timelineArray);
	             //projecttData.put("marker", markerArray);
	             
	             String subjectDataString = projecttData.toString();
	             if (file != null) {
	                 saveTextToFile(subjectDataString, file);
	             }
	         }
	     });
	     
	     f4.setOnAction(new EventHandler<ActionEvent>() {							// Load Sample Data Menu Function
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
	     f5.setOnAction(new EventHandler<ActionEvent>() {								// Load Video Function
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
	     
	     f6.setOnAction(new EventHandler<ActionEvent>() {								// QUit Function
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
	     fileMenu.getItems().addAll(f1, f2, f3, new SeparatorMenuItem(), f4, f5, new SeparatorMenuItem(), f6);
	     controlMenu.getItems().addAll(c1, c2, c3);
	     analyzeMenu.getItems().addAll(a1, a2, new SeparatorMenuItem(), a3, a4);
	     layoutMenu.getItems().addAll(l0,l1);

	    mb.getMenus().addAll(fileMenu, editMenu, controlMenu, analyzeMenu, layoutMenu);
	    vb = new VBox(mb);
	}
	
	public VBox getMainMenu() {
		return vb;
	}
	
	private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            //Logger.getLogger(SaveFileWithFileChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
