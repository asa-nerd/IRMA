package irmaANALYSIS;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import irmaANALYSIS.Sample;
import irmaANALYSIS.Subject;
import irmaANALYSIS.VisualizerSpatial;

public class GUI {
	Stage primaryStage;
	VBox mainStageContainer;
	HBox topContainer;
	HBox bottomContainer;
	GridPane topLeftContainer;
	VBox topMiddleContainer;
	GridPane topRightContainer;
	
	Timer globalTimer;
	static long timerCounter;
	boolean timerPaused = false;
	boolean globalIsPlaying = false;
	
	/*static Media media;
	static MediaPlayer mediaPlayer;
	static MediaView mediaView;*/
	
	static VisualizerList visList;
	static VisualizerSpatial visSpat;
	static VisualizerTemporal visTemp;
	static VisualizerVideo visVid;
	
	static Sample s;
	
	GUI(Stage _primaryStage, Sample _s){
		s = _s;
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
	    
	    // External Window: Video Visualizer
	 	// --------------------------------------
	    visVid = new VisualizerVideo();
	    
		// Bottom: Temporal Visualizer
	 	// --------------------------------------
	    visTemp = new VisualizerTemporal(s);										// initialize visualizer 
	    ScrollPane t = visTemp.getTemporalContainer();
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
	    Button VisAFAButton = new Button("FA");
	    Button VisActivityButton = new Button("AC");
	    Button VisSubActivityButton = new Button("SAC");
	    Button VisSubAttentionButton = new Button("SAT");
	    Button printButton = new Button("Pr");
	    
	    labelVideo.getStyleClass().add("main-navi-label");
	    labelVisualization.getStyleClass().add("main-navi-label");
	    labelStatistic.getStyleClass().add("main-navi-label");
	    
	    playButton.getStyleClass().add("main-navi-button");
	    pauseButton.getStyleClass().add("main-navi-button");
	    stopButton.getStyleClass().add("main-navi-button");
	    VisAFAButton.getStyleClass().add("main-navi-button");
	    VisActivityButton.getStyleClass().add("main-navi-button");
	    VisSubActivityButton.getStyleClass().add("main-navi-button");
	    VisSubAttentionButton.getStyleClass().add("main-navi-button");
	    printButton.getStyleClass().add("main-navi-button");
	    
	    playButton.setPrefSize(26, 26);
	    pauseButton.setPrefSize(26, 26);
	    stopButton.setPrefSize(26, 26);
	    VisAFAButton.setPrefSize(26, 26);
	    VisActivityButton.setPrefSize(26, 26);
	    VisSubActivityButton.setPrefSize(26, 26);
	    VisSubAttentionButton.setPrefSize(26, 26);
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
			   	   	    		//visTemp.setMainTimer((int) timerCounter);
			   	   	    	    visTemp.movePlaybackLines((int) timerCounter);
			   	   	            visSpat.updateTimerDisplay((int) timerCounter);
			   	   	    	    visSpat.drawSampleVector((int) timerCounter);
			   	   	            timerCounter ++;
			              });
		   	   	        }		   	   	        
		   	   	    };
			   	    globalTimer.scheduleAtFixedRate(task, 0, 500l);
			   	    globalIsPlaying = true;
			   	    if (visVid.mediaView != null) {
			   	    	visVid.playVideo();
			   	    }
	   	    	}else {
	   	    		
	   	    	}
	   	    }
	   	});
	    pauseButton.setOnAction(new EventHandler<ActionEvent>() {
	   	    @Override public void handle(ActionEvent e) {   	    
		   	    //task.pause();
	   	    	if (globalIsPlaying == true) {
	   	    		System.out.print("pause");
		   	    	timerPaused = true;
		   	    	globalIsPlaying= false;
		   	    	globalTimer.cancel();
		   	    	globalTimer.purge();
		   	    	if (visVid.mediaView != null) {
			   	    	visVid.pauseVideo();
			   	    }	   	    	
	   	    	}
	   	    }
	   	});
	    stopButton.setOnAction(new EventHandler<ActionEvent>() {
	   	    @Override public void handle(ActionEvent e) {   
	   	    	if (globalIsPlaying == true) {
	   	    		System.out.print("stoop");
		   	    	timerCounter = 0;
		   	    	timerPaused = false;
		   	    	globalTimer.cancel();
		   	    	globalTimer.purge();
		   	    	globalIsPlaying= false;
		   	    	if (visVid.mediaView != null) {
			   	    	visVid.stopVideo();
			   	    }	
	   	    	}
	   	    }
	   	});
	    printButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	System.out.println("print.");
            	ScrollPane p = visTemp.getTemporalContainer();
            	PrinterJob job = PrinterJob.createPrinterJob();
                if(job != null){
	                job.showPrintDialog(primaryStage); 
	                job.printPage(p);
	                job.endJob();
                }
            }
        });    
	    VisAFAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	visTemp.makeTimelineElement(s, "AFA");
            }
        });
	    
	    VisActivityButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	visTemp.makeTimelineElement(s, "ATTENTION");
            }
        });
	    
	    VisSubActivityButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	visTemp.makeTimelineElement(s, "SUBJECTACTIVITY");
            }
        });
	    
	    VisSubAttentionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	visTemp.makeTimelineElement(s, "SUBJECTATTENTION");
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
	    topLeftContainer.add(VisAFAButton, 0, 6);
	    topLeftContainer.add(VisActivityButton, 1, 6);
	    topLeftContainer.add(VisSubActivityButton, 2, 6);
	    topLeftContainer.add(VisSubAttentionButton, 3, 6);
	    topLeftContainer.add(printButton, 4, 6);
	    topLeftContainer.add(labelStatistic, 0,8, 6, 1);
	    topLeftContainer.add(lineStat, 0, 9, 6, 1);
			 
	    // Top-Middle: Data Table
		// --------------------------------------
		//table = makeSampleTable();
	    visList = new VisualizerList();
	    topMiddleContainer.getChildren().add(visList.getVisualizerListContainer());
	    
			    
		// Top-Right: Spatial Visualizer
		// --------------------------------------
	    visSpat = new VisualizerSpatial(s);
	    HBox spatialConatiner = visSpat.getSpatialContainer();
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
	
	public static void setTimerCounter(int _t) {
		timerCounter = _t;
		visTemp.movePlaybackLines((int) timerCounter);
	    visSpat.updateTimerDisplay((int) timerCounter);
	    visSpat.drawSampleVector((int) timerCounter);	
	    if (visVid.mediaView != null) {
   	    	visVid.jumpTo(_t);
   	    }	
	}
	
	public static int getTimerCounter() {
		return (int) timerCounter;
	}
	
	public static void makeVideo(String _f) {
		visVid.initVideo(_f);
		
		Group g = visVid.getVisualizerListContainer();
		//VBox videoContainer = new VBox();
		Scene videoScene = new Scene(g, 640, 360);
		Stage videoStage = new Stage();
		videoStage.setScene(videoScene);
		videoStage.show();
	}
	
	public static void updateSampleTable() {
		visList.clearTable();						// first erase all entries from table
		ArrayList<Subject> subl = s.getSubjectList();
		s.getSubjectList().forEach((s) -> {		// loop through subjects list
			visList.addSubject(s);					// and add each subject
		});
	}
}
