package irmaANALYSIS;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

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

import irmaANALYSIS.Sample;
import irmaANALYSIS.Subject;
import irmaANALYSIS.GUI;
import irmaANALYSIS.triangleWidget;

public class MenuBarFX extends MenuBar{
	
	MenuBar mb;
	Menu fileMenu, editMenu, controlMenu, analyzeMenu;
	MenuItem f1, f2, f3, f4, f5, c1, c2, c3, a1, a2, a3, a4; 
	VBox vb;
    
	
	MenuBarFX(Stage stage){
		MenuBar mb = new MenuBar();
		fileMenu = new Menu("File"); 
		editMenu = new Menu("Edit"); 
		controlMenu = new Menu("Control");
	    analyzeMenu = new Menu("Analyze");
		
	    
	     f1=new MenuItem("Load Sample Data");  
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
	     
	     f1.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
	     f5.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
	     
	     
	     f1.setOnAction(new EventHandler<ActionEvent>() {							// Load Data Menu Function
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
							 Sample.addSubject(s);										// add Subject to Sample							 
							 GUI.updateSampleTable();									// update the Sample Table in GUI
							// triangleWidget.drawSample(100);
		        		   } catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
		        		   }
		        		   
		        	   }catch (IOException e) {
		        	   }
	        	   }
	           }
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
	           System.out.println("Quit");
	           System.exit(0);
	         }
	       });
	    
	     
	     fileMenu.getItems().addAll(f1, new SeparatorMenuItem(), f2, f3, new SeparatorMenuItem(), f4, new SeparatorMenuItem(), f5);
	     controlMenu.getItems().addAll(c1, c2, c3);
	     analyzeMenu.getItems().addAll(a1, a2, new SeparatorMenuItem(), a3, a4);

	    mb.getMenus().addAll(fileMenu, editMenu, controlMenu, analyzeMenu);
	    vb = new VBox(mb);
	}
	
	public VBox getMainMenu() {
		return vb;
	}
}
