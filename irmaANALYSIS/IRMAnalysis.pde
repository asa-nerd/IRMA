//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

import controlP5.*;
import processing.pdf.*;
import processing.video.*;
import java.util.Timer;
import java.util.TimerTask;

PGraphicsPDF pdf;
PImage colorTriangle;

String movieFileName = "KilgoreOC.mp4";                                         // Name of the Video 

ControlP5 GUI;
JSONArray values;
color finnCardboard;
boolean analyze;
int triangleSize = 400;

// Variables for triangle
float triHeight = triangleSize/2*sqrt(3);                                       // h of triangle
PVector center;
PVector triOne, triTwo, triThree;
PVector middleOneTwo, middleTwoThree, middleThreeOne;

ArrayList<Subject> subjects = new ArrayList<Subject>();
//ArrayList<Color()> centroidsColor= new ArrayList<Color()>();
ArrayList<Integer> centroidsColor = new ArrayList<Integer>();

// Variables for interface 
Textarea textfield;
String displayText = "";
boolean fileLoaded = false;

PFont uiFont, pdfFont, pdfFontSmall, pdfFontDeviation, triangleFont;

String[] args = {"--location=0,0", "OutputWin"};
OutputWin ow;

boolean redo = false;
boolean clearOutput = false;
boolean playing = false;
boolean videoFullscreen = true;
boolean timerRun = false;
boolean timerSet = false;

// configuration of PDF-export
int pdfPageHeight = 840;
int pdfPageWidth = 600;
int pdfYglobal = 50;
int pdfTextLineHeight = 20;
int pdfGraphicLineHeight = 100;

int pieceTime;
int pieceSlider = 100;
int sv = 0;
int sliderDraggedTo = -1;
Slider abc;

Movie pieceMovie;
float pieceMovieLength;
Timer timer = new Timer();

public void settings() {
  size(1400,900, P3D);
}

void setup(){
   //ow = new OutputWin(1200,800);
   //PApplet.runSketch(args, ow);
   //pieceMovie = new Movie(this, "../../videos/Coretet.mp4");
   pieceMovie = new Movie(this, movieFileName);                                     // setup Video
   pieceMovie.frameRate(30);
   pieceMovie.play();
   pieceMovieLength = pieceMovie.duration();
   pieceMovie.stop();
   //pieceMovie.resize(1400,800);

   colorTriangle = loadImage("colorTriangle.png");                                  // setup GUI
   colorMode(HSB, 360,100,100);
   ellipseMode(CENTER);
   finnCardboard = color(50,0,100);
   background(finnCardboard);
   GUI = new ControlP5(this);
   uiFont = createFont("UniversLTStd-Light", 10);
   pdfFont = createFont("UniversLTStd-Light", 8);
   pdfFontSmall = createFont("UniversLTStd-Light", 5);
   pdfFontDeviation = createFont("UniversLTStd-Light", 2);
   triangleFont = createFont("UniversLTStd-Light", 9);
   textFont(uiFont);
   makeGUI();  

   center = new PVector(0,0);                                                      // basic vertices for the trianlge
   triOne = new PVector(-triangleSize/2, triHeight/3);  
   triTwo = new PVector(triangleSize/2, triHeight/3); 
   triThree = new PVector(0, -(triHeight/3*2)); 
   middleOneTwo = new PVector(lerp(triOne.x, triTwo.x, 0.5), triOne.y);
   middleTwoThree = new PVector(lerp(triTwo.x, triThree.x, 0.5), lerp(triTwo.y, triThree.y, 0.5));
   middleThreeOne = new PVector(lerp(triThree.x, triOne.x, 0.5), lerp(triThree.y, triOne.y, 0.5));
   
   
   drawTriangle();                                                                // Init the GUI
   drawDimensions();
   drawSections();
}

void draw(){
  /*background(0,0,360);
  if(videoFullscreen == true){
    image(pieceMovie, 0, 0, 1400, 787); 
  }else{
    image(pieceMovie, 960, 100, 400, 220);   
  }*/
  //println(pieceTime);
  if (fileLoaded == true){
     //background(360);
     //displayData();
     if (subjects.size() == 1){
       GUI.addSlider("sv").setPosition(20,550).setRange(0,subjects.get(0).getLength()).setSize(width-40,20);
     }
     displayOverview();
     fileLoaded = false;
   }
   if (playing == true){
     //image(pieceMovie, 0, 0, 1400, 787); 
      //drawCentroid(pieceTime); 
      displayOverview();
      GUI.getController("sv").setValue(pieceTime);
      
   }
   if (sliderDraggedTo != -1){
     if(sliderDraggedTo != -1){
       //image(pieceMovie, 0, 0, 1400, 787); 
       background(0,0,360);
       imageMode(CORNER);
        if(videoFullscreen == true){
          image(pieceMovie, 0, 0, 1400, 787); 
        }else{
          image(pieceMovie, 960, 100, 400, 220);   
        }
       //println("yes");
        // Triangle
        drawCentroid(pieceTime);          
        float pieceMoviePos = pieceMovie.time();
        float dataLength = GUI.getController("sv").getMax();
        
        fill(0,0,360);
        //rect(0, 580,width, height);
        // Deviation
          drawDeviation(20, 700);
        
        // Line + Timer
          float lineX = map(pieceTime, 0, dataLength, 20, width-20);
          drawPosLine(lineX);
          fill(0);
        
        // Calculate Time from Timecode to display it in the Interface
          fill(0,0,360);
          noStroke();
          rect(lineX+5,580, 100, 15);
          /*int tc = subjects.get(0).lastTimecode;
          int ts = floor(tc/1000/60);
          int tms = (tc/1000)%60;
          int currentTC = subjects.get(0).PointsList.get(pieceTime).timestamp;
          int cts = floor(currentTC/1000/60);
          int ctms = (currentTC/1000)%60;*/
          fill(0);
          //text(cts+"m "+ctms+"s / "+ts+"m "+tms+"s", lineX+10, 590);
          text(calcTime(pieceTime), lineX+10, 590);
        // Video
        if (playing == false){
          float t = map(pieceTime, 0, dataLength, 0, pieceMovieLength);
          pieceMovie.play();
          pieceMovie.jump(t);
          pieceMovie.pause();
          //println(subjects.get(0).PointsList.get(_position).timestamp+", position:",+sliderDraggedTo+", dataLength:",+dataLength+", pieceMovieLength:",+pieceMovieLength+", t:",+t);
          //println(_position);
        }
      }
      sliderDraggedTo = -1; 
   }
   
}

public String calcTime(int _pos){
  String currentTime; 
  int tc = subjects.get(0).lastTimecode;
  int ts = floor(tc/1000/60);
  int tms = (tc/1000)%60;
  int currentTC = subjects.get(0).PointsList.get(_pos).timestamp;
  int cts = floor(currentTC/1000/60);
  int ctms = (currentTC/1000)%60;
  //currentTime = str(cts)+"m "+str(ctms)+"s / "+str(ts)+"m "+str(tms)+"s";
  currentTime = str(cts)+"m "+str(ctms)+"s";
  return currentTime;
}

public void drawPosLine(float _x){                                          // Mark the current position of playback 
   stroke(0,360,360);                                                       // in the timeline
   line(_x, 580, _x, height-20);
}

// ---------------------------------------------------------------------------------------------
// UI Functions
// ---------------------------------------------------------------------------------------------

public void loader(int theValue){
  selectInput("Select a file to process:", "fileSelected");
}

public void display(int theValue){
  //drawDeviation(20, 700);
}  

public void calculateAnalysis(int theValue){
  redo = true;
  colorMode(RGB);
  PVector centroid = new PVector();
  color c;
  for (int i = 0; i < subjects.get(0).getLength(); i++) {
    centroid = getCentroid(i);
    c = get(int(centroid.x)+(width/2), int(centroid.y)+350);  
    centroidsColor.add(c);
  }
  colorMode(HSB);
}  

public void clearOutput(int theValue){
  clearOutput = true;
}  

public void printReport(int theValue){
  selectOutput("Select a file to process:", "fileOutput");
  /*
  float  pieceActivity = 0;
  for(int t = 0; t < subjects.get(0).getLength(); t ++){ 
    float currentActivity = getAllActivity(t);  
    pieceActivity += currentActivity;
    float xm = map(t, 0, subjects.get(0).getLength(), 0, width-40);
    int baseY = height-100;
    stroke(0);
    line(20+xm, baseY, 20+xm, baseY-currentActivity);
  }
  
  println(pieceActivity);*/
}

    

/*
public void pieceSlider(int _position){
    if(_position != 0){
      drawCentroid(_position);
      float pieceMoviePos = pieceMovie.time();
      
      float dataLength = GUI.getController("pieceSlider").getMax();
      float t = map(_position, 0, dataLength, 0, pieceMovieLength);
      /*pieceMovie.play();
      pieceMovie.jump(t);
      pieceMovie.pause();*/
      /*println(subjects.get(0).PointsList.get(_position).timestamp+", position:",+_position+", dataLength:",+dataLength+", pieceMovieLength:",+pieceMovieLength+", t:",+t);
      //println(_position);
    }
}
*/

public synchronized void sv(int _position){
  //println("sliding…");
  //drawCentroid(_position);
  if (playing != true && _position != 0){
    
  }
  
  if (playing != true){
    pieceTime = _position;
    sliderDraggedTo = _position;
  }else{
    sliderDraggedTo = _position;
    
  }
 
}

public int getLongestSubject(){
  int minSize = 0;
  int currentSize;
  for (int i = 0; i < subjects.size(); i++) {
     currentSize = subjects.get(i).getLength();
     //println(i+", "+currentSize);
     if (currentSize < minSize){
       minSize = currentSize;
     }
  }
  //println(maxSize);
  return minSize;
}

void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    values = loadJSONArray(selection.getAbsolutePath());
    subjects.add(new Subject(values, selection.getName(), ow));
    //subjects.get(subjects.size()).getPointbyIndex(1)
    fileLoaded = true;
  }
}

public void switchDisplay(int theValue){
   videoFullscreen = !videoFullscreen;
}

public void playPiece(int theValue){
  if(subjects.size() != 0){
    //pieceTime = 10;
    long a = 0;
    long b = 500;
    timerRun = true;
    if (timerSet == false){
      timer.scheduleAtFixedRate(new Task(),a,b);
      timerSet = true;
    }
    pieceMovie.play();
    playing = true;
    
  }
}

public void pausePiece(int theValue){
  //timer.cancel();
  //timerRun = false;
  //timer = null;
  pieceMovie.pause();
  playing = false;
}

public void stopPiece(int theValue){
  //timerRun = false;
  //timer = null;
  pieceMovie.stop();
  playing = false;
}


class Task extends TimerTask
{
  @Override public void run()
  {
    if (playing == true){
      pieceTime ++;
    }
    /*if (timerRun){
      pieceTime ++;
    }else{
      timer.cancel();
      timer.purge();
    }*/    
  }
}


void movieEvent(Movie m) {
  m.read();
}
