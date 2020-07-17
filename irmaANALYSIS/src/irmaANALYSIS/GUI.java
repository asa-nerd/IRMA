package irmaANALYSIS;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import irmaANALYSIS.Sample;
import irmaANALYSIS.Subject;
import irmaANALYSIS.VisualizerSpatial;

// left: Subjects
// center: Video
// right: triangle
// bottom: timeline

public class GUI {
	
	Stage primaryStage;
	
	VBox mainStageContainer;
	HBox topContainer;
	HBox bottomContainer;
	
	GridPane topLeftContainer;
	VBox topMiddleContainer;
	GridPane topRightContainer;
	
	static TableView table;
	//static TableColumn tableColumnId, tableColumnRecording, tableColumnLength;
	static TableColumn <Long, Subject> tableColumnId;
	static TableColumn <String, Subject> tableColumnRecordName;
	static TableColumn <Double, Subject> tableColumnAge;
	static TableColumn <String, Subject> tableColumnEducation;
	
	Timer globalTimer;
	long timerCounter;
	boolean timerPaused = false;
	boolean globalIsPlaying = false;
	static Media media;
	static MediaPlayer mediaPlayer;
	static MediaView mediaView;
	
	static VisualizerSpatial tri;
	
	Sample sample;
	
	GUI(Stage _primaryStage, Sample _sample){
		sample = _sample;
		primaryStage = _primaryStage;
			
		mainStageContainer = new VBox();
		topContainer = new HBox();
		bottomContainer = new HBox();
		topLeftContainer = new GridPane();
		topMiddleContainer = new VBox();
		topRightContainer = new GridPane();;
		
		topContainer.getChildren().addAll(topLeftContainer, topMiddleContainer, topRightContainer);		
		
		HBox.setMargin(topLeftContainer, new Insets( 0, 16, 0, 0 ) );
	    HBox.setMargin(topMiddleContainer, new Insets( 0, 16, 0, 0 ) );
	    topRightContainer.setStyle("-fx-border-color: white");
	    
		// Bottom: Temporal Visualizer
	 	// --------------------------------------
	    VisualizerTemporal v = new VisualizerTemporal(sample);										// initialize visualizer 
	    ScrollPane t = v.getTemporalContainer();
	    bottomContainer.getChildren().add(t);
			    
			    
		// Top-Left: Main Navigation
		// --------------------------------------
		
	    Label labelVideo = new Label("Video");
	    Label labelVisualization = new Label("Visualize");
	    Label labelStatistic = new Label("Statistical Methods");
	    
	    Line lineVideo = new Line(0,0,212,0);
	    lineVideo.setStroke(Color.rgb(112,112,112));
	    Line lineVis= new Line(0,0,212,0);
	    lineVis.setStroke(Color.rgb(112,112,112));
	    Line lineStat = new Line(0,0,212,0);
	    lineStat.setStroke(Color.rgb(112,112,112));
	    
	    Button playButton = new Button("P");
	    Button pauseButton = new Button("Pa");
	    Button stopButton = new Button("St");
	    Button drawButton = new Button("FA");
	    Button attentionButton = new Button("AT");
	    Button printButton = new Button("Pr");
	    
	    labelVideo.getStyleClass().add("main-navi-label");
	    labelVisualization.getStyleClass().add("main-navi-label");
	    labelStatistic.getStyleClass().add("main-navi-label");
	    
	    playButton.getStyleClass().add("main-navi-button");
	    pauseButton.getStyleClass().add("main-navi-button");
	    stopButton.getStyleClass().add("main-navi-button");
	    drawButton.getStyleClass().add("main-navi-button");
	    attentionButton.getStyleClass().add("main-navi-button");
	    printButton.getStyleClass().add("main-navi-button");
	    
	    playButton.setPrefSize(26, 26);
	    pauseButton.setPrefSize(26, 26);
	    stopButton.setPrefSize(26, 26);
	    drawButton.setPrefSize(26, 26);
	    attentionButton.setPrefSize(26, 26);
	    printButton.setPrefSize(26, 26);

	    
	    playButton.setOnAction(new EventHandler<ActionEvent>() {
	   	    @Override public void handle(ActionEvent e) {   
	   	    	if (globalIsPlaying != true) {
		   	    	globalTimer = new Timer();
		   	    	if (timerPaused == true) {
		   	    		timerPaused = false;
		   	    	}else {
		   	    		timerCounter = 0;
		   	    	}
		   	    	
		   		    TimerTask task = new TimerTask(){
	
		   	   	        public void run(){ 
			   	   	      Platform.runLater(() -> {					// runLater() is necessary for threading, eventually replace with JavaFX timeline
					   	   	  			
			   	   	            v.drawPlaybackPosition(timerCounter);
			   	   	            v.movePlaybackLines((int) timerCounter);
			   	   	            tri.drawSampleVector((int) timerCounter);
			   	   	            timerCounter ++;
			              });
		   	   	        }		   	   	        
		   	   	    };
			   	    globalTimer.scheduleAtFixedRate(task, 0, 100l);
			   	    globalIsPlaying = true;
			   	   // mediaPlayer.play();
			   	 
	   	    	}else {
	   	    		
	   	    	}
	   	    }
	   	});
	    pauseButton.setOnAction(new EventHandler<ActionEvent>() {
	   	    @Override public void handle(ActionEvent e) {   	    
		   	    //task.pause();
	   	    	if (globalIsPlaying == true) {
		   	    	timerPaused = true;
		   	    	globalIsPlaying= false;
		   	    	globalTimer.cancel();
		   	    	globalTimer.purge();
		   	    	mediaPlayer.pause();
		   	    	
	   	    	}
	   	    }
	   	});
	    stopButton.setOnAction(new EventHandler<ActionEvent>() {
	   	    @Override public void handle(ActionEvent e) {   
	   	    	if (globalIsPlaying == true) {
		   	    	timerCounter = 0;
		   	    	timerPaused = false;
		   	    	globalTimer.cancel();
		   	    	globalTimer.purge();
		   	    	globalIsPlaying= false;
		   	    	mediaPlayer.stop();
	   	    	}
	   	    }
	   	});
	    printButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	System.out.println("print.");
            	ScrollPane p = v.getTemporalContainer();
            	PrinterJob job = PrinterJob.createPrinterJob();
                if(job != null){
	                job.showPrintDialog(primaryStage); 
	                job.printPage(p);
	                job.endJob();
                }
            }
        });    
	    drawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	//v.drawTimeline(0, sample.getShortestDataset());
            	v.makeTimelineElement(sample);
            }
        });
	   
	    
	    
	    topLeftContainer.setHgap(6);
	    topLeftContainer.setVgap(6);
	    topLeftContainer.add(labelVideo, 0, 0, 6, 1);
	    topLeftContainer.add(lineVideo, 0, 1, 6, 1);
	    //topLeftContainer.setGridLinesVisible(true);
	    
	    topLeftContainer.add(playButton, 0, 2);
	    topLeftContainer.add(pauseButton, 1, 2);
	    topLeftContainer.add(stopButton, 2, 2);
	   
	    topLeftContainer.add(labelVisualization, 0, 4, 6, 1);
	    topLeftContainer.add(lineVis, 0, 5, 6, 1);
	    topLeftContainer.add(drawButton, 0, 6);
	    topLeftContainer.add(attentionButton, 1, 6);
	    topLeftContainer.add(printButton, 2, 6);
	    topLeftContainer.add(labelStatistic, 0,8, 6, 1);
	    topLeftContainer.add(lineStat, 0, 9, 6, 1);
	    
	    
			 
	    // Top-Middle: Data Table
		// --------------------------------------
		table = makeSampleTable();
	    topMiddleContainer.getChildren().add(table);
	    
			    
		// Top-Right: Spatial Visualizer
		// --------------------------------------
	    tri = new VisualizerSpatial(sample);
	    HBox spatialConatiner = tri.getSpatialContainer();
	    topRightContainer.getChildren().add(spatialConatiner);

	    // Menu Bar
	    // --------------------------------------
	    MenuBarFX mbfx = new MenuBarFX(primaryStage);
	    VBox mbfxContainer = mbfx.getMainMenu();
	    
	    // Set whole GUI Scene
	    // --------------------------------------
	    mainStageContainer.getChildren().addAll(mbfxContainer, topContainer, bottomContainer);
	    
	    
	    
	    
	    topLeftContainer.setPrefSize(212, 400);
	    topLeftContainer.setMinSize(212, 400);
	    topLeftContainer.setMaxSize(212, 400);
	    
	    topMiddleContainer.setPrefSize(554, 400);
	    topMiddleContainer.setMinSize(554, 400);
	    topMiddleContainer.setMaxSize(554, 400);
	    
	    topRightContainer.setPrefSize(554, 400);
	    topRightContainer.setMinSize(554, 400);
	    topRightContainer.setMaxSize(554, 400);
	    topContainer.setPadding(new Insets(20, 20, 10, 20));
	    bottomContainer.setPadding(new Insets(10, 20, 20, 20));
	    
	    topContainer.setMaxHeight(400);
	    topContainer.setPrefHeight(400);
	    bottomContainer.setMinHeight(400);
	    bottomContainer.setPrefHeight(400);
	}
	

	public VBox getLayout() {
		return mainStageContainer;
	}
	
	static public void makeVideo(String _f) {
		String n = "file:"+_f;
		media = new Media(n);
	    mediaPlayer = new MediaPlayer(media);  
	    mediaView = new MediaView (mediaPlayer);
	    mediaView.setFitWidth(500);
	   // rootLayout.setCenter(mediaView);
		
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
		tableColumnAge = new TableColumn<>("Age");
		tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		tableColumnEducation =  new TableColumn<>("Education");
		tableColumnEducation.setCellValueFactory(new PropertyValueFactory<>("education"));
		// tableColumnLength = new TableColumn<>("Length");
		tableColumnId.setSortable(false);
		tableColumnRecordName.setSortable(false);
		
		tableColumnId.setPrefWidth(40);
		tableColumnRecordName.setPrefWidth(140);
		tableColumnAge.setPrefWidth(40);
		tableColumnEducation.setPrefWidth(180);
		//tableColumnLength.setSortable(false);
		thisTable.getColumns().addAll(tableColumnId, tableColumnRecordName, tableColumnAge, tableColumnEducation);
		return thisTable;
	}
}
