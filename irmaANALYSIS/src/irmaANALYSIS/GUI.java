package irmaANALYSIS;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import irmaANALYSIS.Sample;
import irmaANALYSIS.Subject;
import irmaANALYSIS.triangleWidget;

// left: Subjects
// center: Video
// right: triangle
// bottom: timeline

public class GUI {
	static BorderPane rootLayout;
	Stage primaryStage;
	VBox mainStageContainer;
	VBox top, left, right, center, bottom;
	Button b1, b2;
	static TableView table;
	//static TableColumn tableColumnId, tableColumnRecording, tableColumnLength;
	static TableColumn <Long, Subject> tableColumnId;
	static TableColumn <String, Subject> tableColumnRecordName;
	
	Timer globalTimer;
	long timerCounter;
	boolean timerPaused = false;
	
	static Media media;
	static MediaPlayer mediaPlayer;
	static MediaView mediaView;
	
	
	Sample sample;
	
	GUI(Stage _primaryStage, Sample _sample){
		sample = _sample;
		primaryStage = _primaryStage;
		
		mainStageContainer = new VBox();
		rootLayout = new BorderPane();
		top = new VBox();
		left = new VBox();
		right = new VBox();
		center = new VBox();
		center.setMinWidth(300.0);
		top.setMinHeight(100.0);
		top.setStyle("-fx-background-color: #888");
		right.setStyle("-fx-background-color: #888");
		right.setMinWidth(300.0);
		right.setPrefHeight(400.0);
		right.setMaxHeight(400.0);
		left.setMinWidth(300.0);
		left.setPrefWidth(300.0);
		left.setMaxWidth(300.0);
		left.setMinHeight(200.0);
		left.setPrefHeight(300.0);
		left.setMaxHeight(400.0);
		b1 = new Button("Play");
		b2 = new Button("Pause");
		bottom = new VBox(b1, b2);
		//rootLayout.setTop(top);
		rootLayout.setLeft(left);
		rootLayout.setRight(right);
		rootLayout.setCenter(center);
		rootLayout.setBottom(bottom);
		//rootLayout.getLef
		
		
		
		// Right: Triangle Widget
		// --------------------------------------
	    triangleWidget tri = new triangleWidget(sample);
	    
        
	    // Bottom: Visualizer
	 	// --------------------------------------
	    Visualizer v = new Visualizer (sample);										// initialize visualizer 
	    
	    
   	    
		// Left: Table
		// --------------------------------------
		table = makeSampleTable();
		final Label label = new Label("Sample Data");
		final VBox vbox = new VBox();
	    Button sampleLoadButton = new Button("Load Data");
	    vbox.setSpacing(5);
	    vbox.setPadding(new Insets(10, 0, 0, 10));
	    vbox.getChildren().addAll(label, table, sampleLoadButton);
	       
	    rootLayout.setLeft(vbox);
		
	    // Center: Video
	 	// --------------------------------------
	    
	    
	   // Media media = new Media("file:/Users/andreas/Documents/GitHub/IRMA/irmaANALYSIS/data/test.mp4");
	    //MediaPlayer mediaPlayer = new MediaPlayer(media);  
	    //MediaView mediaView = new MediaView (mediaPlayer);
	    //mediaView.setFitWidth(500);
	    
	    //rootLayout.setCenter(mediaView);
	    
	    // Top: Buttons 
	    // --------------------------------------
       
	    Button playButton = new Button("Play");
	    Button pauseButton = new Button("Pause");
	    Button stopButton = new Button("Stop");
	    Button drawButton = new Button("draw");
	    drawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //label.setText("Accepted");
            	v.drawTimeline(0, 1500);
            }
        });
	   
	    playButton.setOnAction(new EventHandler<ActionEvent>() {
	   	    @Override public void handle(ActionEvent e) {   
	   	    	globalTimer = new Timer();
	   	    	if (timerPaused == true) {
	   	    		timerPaused = false;
	   	    	}else {
	   	    		timerCounter = 0;
	   	    	}
	   	    	
	   		    TimerTask task = new TimerTask(){

	   	   	        public void run(){ 
	   	   	              v.clearCanvas();
	   	   	              tri.clearCanvas();
	   	   	              //v.drawTimeline(0, 1500);
	   	   	              v.drawPlaybackPosition(timerCounter);
	   	   	              tri.drawSample((int) timerCounter);
	   	   	              timerCounter ++;
	   	   	        }
	   	   	        
	   	   	    };
		   	    globalTimer.scheduleAtFixedRate(task, 0, 100l);
		   	    mediaPlayer.play();
	   	    }
	   	});
	    pauseButton.setOnAction(new EventHandler<ActionEvent>() {
	   	    @Override public void handle(ActionEvent e) {   	    
		   	    //task.pause();
	   	    	timerPaused = true;
	   	    	globalTimer.cancel();
	   	    	globalTimer.purge();
	   	    	mediaPlayer.pause();
	   	    }
	   	});
	    stopButton.setOnAction(new EventHandler<ActionEvent>() {
	   	    @Override public void handle(ActionEvent e) {   
	   	    	timerCounter = 0;
	   	    	timerPaused = false;
	   	    	v.clearCanvas();
	   	    	tri.clearCanvas();
	   	    	globalTimer.cancel();
	   	    	globalTimer.purge();
	   	    	mediaPlayer.stop();
	   	    }
	   	});
	    
	    HBox playButtonBox = new HBox();
	    playButtonBox.getChildren().addAll(playButton, pauseButton, stopButton, drawButton);
	    top.getChildren().add(playButtonBox);
	    rootLayout.setTop(top);
	    
	    // Menu Bar
	    // --------------------------------------
	    MenuBarFX mbfx = new MenuBarFX(primaryStage);
	    VBox mbfxContainer = mbfx.getMainMenu();
	    
	    // Set whole Scene
	    // --------------------------------------
	    
	    mainStageContainer.getChildren().addAll(mbfxContainer, rootLayout);
	 	
	}
	
	
	static public void makeVideo(String _f) {
		String n = "file:"+_f;
		media = new Media(n);
	    mediaPlayer = new MediaPlayer(media);  
	    mediaView = new MediaView (mediaPlayer);
	    mediaView.setFitWidth(500);
	    
	    rootLayout.setCenter(mediaView);
		
	}
	public void setSize(long w, long h) {
		
	}
	
	public VBox getLayout() {
		return mainStageContainer;
	}
	
	public static void updateSampleTable() {
		table.getItems().clear();					// first erase all entries from table
		for (Subject s: Sample.SubjectsList) {		// loop through subjects list		
			table.getItems().add(s);				// and add each subject
		}
	}
	
	private TableView makeSampleTable() {
		TableView thisTable = new TableView();
		thisTable.setPlaceholder(new Label("Please load sample data."));
		thisTable.setEditable(true);
		tableColumnId = new TableColumn<>("ID");
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnRecordName = new TableColumn<>("Record Name");
		tableColumnRecordName.setCellValueFactory(new PropertyValueFactory<>("recordName"));
		// tableColumnLength = new TableColumn<>("Length");
		tableColumnId.setSortable(false);
		tableColumnRecordName.setSortable(false);
		//tableColumnLength.setSortable(false);
		thisTable.getColumns().addAll(tableColumnId, tableColumnRecordName);
		return thisTable;
	}
}
