import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import oscP5.*; 
import netP5.*; 
import java.net.InetAddress; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class IRMAhost_0_1_6 extends PApplet {







ControlP5 GUI;
InetAddress inet;
OscP5 oscP5;
NetAddressList myNetAddressList = new NetAddressList();

PFont din;
ArrayList<DeviceSender> senderList = new ArrayList<DeviceSender>();

DeviceSender[] fixedSenderList = new DeviceSender[12];

String myIP;
int x, y, lastx, lasty;
int pointCounter = 0;
int myBroadcastPort = 7000;

float superviseX, superviseY;

int guiColorBackground = color(255,180);
int guiColorText = color(0);
int strokeColor = color(0,100);
int finnCardboard, darkGray, lightGray, middleGray, darkRed;
int senderCounter = 0;
int senderLine = 0;
int timerIntervall = 250;
String currentRecordTitle = "";
boolean record = false;
boolean testMode = false;

PVector originCollecitveTriangle, middleCollectiveTriangle;
PVector originSenderTriangle, middleSenderTriangle;
PVector dimensionOneOrigin, dimensionTwoOrigin, dimensionThreeOrigin;
PVector middleOneThree, middleOneTwo, middleTwoThree;

float triangleWidth = 340;
float triangleHeight = triangleWidth/2 * sqrt(3);

float senderTriangleWidth = 120;
float senderTriangleHeight = senderTriangleWidth/2 * sqrt(3);

int guiPlaceholderRedraw = 0;

public void setup() {
  //size(1920,1080);
  
  frameRate(60);
  colorMode(HSB, 360,100,100);
  finnCardboard = color(50,0,100);
  darkGray = color(0,0,40);
  middleGray = color(0,0,60);
  lightGray = color(0,0,80);
  darkRed = color(0,70,40);


  originCollecitveTriangle = new PVector(30, 30);  
  middleCollectiveTriangle = new PVector(originCollecitveTriangle.x+(triangleWidth/2), originCollecitveTriangle.y + (triangleHeight/3*2));
  dimensionOneOrigin = new PVector(originCollecitveTriangle.x, originCollecitveTriangle.y+triangleHeight);
  dimensionTwoOrigin = new PVector(originCollecitveTriangle.x+triangleWidth, originCollecitveTriangle.y+triangleHeight);
  dimensionThreeOrigin = new PVector(originCollecitveTriangle.x+(triangleWidth/2), originCollecitveTriangle.y);
  middleOneThree = PVector.lerp(dimensionOneOrigin, dimensionThreeOrigin, 0.5f);
  middleOneTwo = PVector.lerp(dimensionOneOrigin, dimensionTwoOrigin, 0.5f);
  middleTwoThree = PVector.lerp(dimensionTwoOrigin, dimensionThreeOrigin, 0.5f);
  originSenderTriangle = new PVector(15, 15);
  middleSenderTriangle = new PVector(originSenderTriangle.x+(senderTriangleWidth/2), originSenderTriangle.y+(senderTriangleHeight/3*2));
  oscP5 = new OscP5(this,32000);
  GUI = new ControlP5(this);
  GUI.setColorForeground(middleGray);
  GUI.setColorBackground(lightGray);
  GUI.setColorActive(darkGray);
  GUI.setColorValueLabel(darkGray);
  GUI.setColorCaptionLabel(darkGray);

  din = createFont("DINPro", 14);
  
  
  
  
  
  background(finnCardboard);
  makeGUI();
  drawGUISections();
  drawPlaceholders();

  try {
     inet = InetAddress.getLocalHost();
     myIP = inet.getHostAddress();
  }
  catch (Exception e) {
     e.printStackTrace();
     myIP = "couldnt get IP"; 
  }
  fill(255);
  text("Server IP: "+myIP,210,460);
  ellipseMode(CENTER);
}

public void draw() {
  fill(finnCardboard);
  noStroke();
  rect(0,0,380,370);
  stroke(255,150);
  //triangle(200-170,390-50, 200+170, 390-50, 200, 390-350);
  triangle(dimensionOneOrigin.x, dimensionOneOrigin.y, dimensionTwoOrigin.x, dimensionTwoOrigin.y, dimensionThreeOrigin.x, dimensionThreeOrigin.y);
  stroke(255,30);
  line(middleOneThree.x, middleOneThree.y, dimensionTwoOrigin.x, dimensionTwoOrigin.y);
  line(middleOneTwo.x, middleOneTwo.y, dimensionThreeOrigin.x, dimensionThreeOrigin.y);
  line(middleTwoThree.x, middleTwoThree.y, dimensionOneOrigin.x, dimensionOneOrigin.y);
  //ellipse(middleOneThree.x, middleOneThree.y, 4,4);
  noStroke();
  fill(0,150);

  // Draw collective Triangle with all Data
     for(int h = 0; h < fixedSenderList.length; h++){
      if (fixedSenderList[h] != null){
        JSONArray allData = fixedSenderList[h].getData();
        for(int i = 0; i < allData.size(); i++){
            JSONObject o = allData.getJSONObject(i);
            float xo = o.getFloat("x");
            float yo = o.getFloat("y");
            float xAll = map(xo, -0.5f, 0.5f, -triangleWidth/2, triangleWidth/2);
            float yAll = map(yo, -0.433f, 0.433f, -triangleHeight/2, triangleHeight/2);
            ellipse(middleCollectiveTriangle.x + xAll,middleCollectiveTriangle.y + yAll, 2,2);
         }
       }
     }
   
   // Draw small individual Traingles with Data from the Devices
     for(int h = 0; h < fixedSenderList.length; h++){
        if (fixedSenderList[h] != null){
          int sX = fixedSenderList[h].x;
          int sY = fixedSenderList[h].y;
          stroke(255);
          fill(0,10);
          rect(sX,sY,149,149);
          //fill(0,30);
          //rect(sX,sY,149,149);
          noFill();
          stroke(255,150);
          //triangle(sX+75-60,sY+150-20, sX+75+60, sY+150-20, sX+75, sY+150-135);
          triangle(sX+75-senderTriangleWidth/2,sY+20+senderTriangleHeight, sX+75+senderTriangleWidth/2, sY+20+senderTriangleHeight, sX+75, sY+20);
          
          if (record == true){
            noStroke();
            fill(255, 80);
            //103,92
            for(int i = 0; i < fixedSenderList[h].data.size(); i++){
              JSONObject o = fixedSenderList[h].data.getJSONObject(i);
              float x = o.getFloat("x");
              float y = o.getFloat("y");
              float drawX = PApplet.parseInt(map(x, -0.5f, 0.5f, -senderTriangleWidth/2, senderTriangleWidth/2));
              float drawY = PApplet.parseInt(map(y, -0.433f, 0.433f, -senderTriangleHeight/2, senderTriangleHeight/2));
              ellipse(sX + middleSenderTriangle.x + drawX,sY + middleSenderTriangle.y + drawY, 2,2);
            }
          }
        }
       }

   if (guiPlaceholderRedraw !=0){
       int listX = 0;
       int listY = 0;
       switch (guiPlaceholderRedraw){
          case 1: listX = 410; listY = 30; break;
          case 2: listX = 570; listY = 30; break;
          case 3: listX = 730; listY = 30; break;
          case 4: listX = 410; listY = 200; break;
          case 5: listX = 570; listY = 200; break;
          case 6: listX = 730; listY = 200; break;
          case 7: listX = 410; listY = 370; break;
          case 8: listX = 570; listY = 370; break;
          case 9: listX = 730; listY = 370; break;
          case 10: listX = 410; listY = 540; break;
        }
       drawOnePlaceholder(listX,listY, guiPlaceholderRedraw);
       guiPlaceholderRedraw = 0;
       
   }
   
   //println("x: "+superviseX);
   //println("y: "+superviseY);
}

public void connect(String theIPaddress) {
     if (!myNetAddressList.contains(theIPaddress, myBroadcastPort)) {
       myNetAddressList.add(new NetAddress(theIPaddress, myBroadcastPort));
       println("### adding "+theIPaddress+" to the list.");
     } else {
       println("### "+theIPaddress+" is already connected.");
     }
     println("### currently there are "+myNetAddressList.list().size()+" remote locations connected.");
}

public void disconnect(String theIPaddress) {
  if (myNetAddressList.contains(theIPaddress, myBroadcastPort)) {
      myNetAddressList.remove(theIPaddress, myBroadcastPort);
         println("### removing "+theIPaddress+" from the list.");
  } else {
         println("### "+theIPaddress+" is not connected.");
  }
  println("### currently there are "+myNetAddressList.list().size());
}


public void clearData() {
    for(int i = 0; i < fixedSenderList.length; i++){
      if (fixedSenderList[i] != null){
        fixedSenderList[i].eraseMyData(); 
      }
    }
}
public void oscEvent(OscMessage theOscMessage) {
  if (theOscMessage.addrPattern().equals("/coordinates")) { 
    String senderIP = theOscMessage.netAddress().address();
    int timeStamp = theOscMessage.get(0).intValue();
    int currentDeviceId = theOscMessage.get(1).intValue();
    //int currentDeviceId = theOscMessage.get(1).intValue();
    float xSender = theOscMessage.get(2).floatValue();
    float ySender = theOscMessage.get(3).floatValue();
    //println(xSender+","+ySender);
    //println("got new data: "+xSender);
    lastx = x;
    lasty = y;
    superviseX = xSender;
    superviseY = ySender;
    float collectiveX = originCollecitveTriangle.x+PApplet.parseInt(map(xSender, 0, 1, 0, 340));
    float collectiveY = originCollecitveTriangle.y+PApplet.parseInt(map(ySender, 0, 1, 0, 340));
    x = PApplet.parseInt(map(xSender, 0, 1, 0, 120));
    y = PApplet.parseInt(map(ySender, 0, 1, 0, 120));
    fixedSenderList[currentDeviceId].addItem(currentRecordTitle, currentDeviceId, timeStamp, xSender, ySender);
  }
  
  if (theOscMessage.addrPattern().equals("/connect")) {
    connect(theOscMessage.netAddress().address());
    int deviceID = PApplet.parseInt(theOscMessage.get(0).stringValue());  
    float listX = 0;
    float listY = 0;

    switch (deviceID){
      case 1: listX = 410; listY = 30; break;
      case 2: listX = 570; listY = 30; break;
      case 3: listX = 730; listY = 30; break;
      case 4: listX = 410; listY = 200; break;
      case 5: listX = 570; listY = 200; break;
      case 6: listX = 730; listY = 200; break;
      case 7: listX = 410; listY = 370; break;
      case 8: listX = 570; listY = 370; break;
      case 9: listX = 730; listY = 370; break;
      case 10: listX = 410; listY = 540; break;
    }
    if (record != true){
      fixedSenderList[deviceID] = new DeviceSender(theOscMessage.netAddress().address(), deviceID, PApplet.parseInt(listX), PApplet.parseInt(listY));
    }
  }
  
  if (theOscMessage.addrPattern().equals("/disconnect")) { 
   int deviceID = theOscMessage.get(0).intValue(); 
    String senderIP = theOscMessage.netAddress().address();
    int deleteNumber = deviceID;
      try{
        fixedSenderList[deleteNumber].killMe();
        fixedSenderList[deleteNumber] = null;
        //println(deleteNumber);
        //fixedSenderList[deleteNumber].remove();
      }
      catch (Exception e) {
        println("error");
      }
    // disconnect from NetAdressList
    disconnect(theOscMessage.netAddress().address());
    guiPlaceholderRedraw = deviceID;
  }
}
class DeviceSender {
  String ipAdress;
  int id;
  int x,y;
  JSONArray data;
  
  DeviceSender(String _ipAdress, int _id, int _x, int _y){
      ipAdress = _ipAdress;
      id = _id;
      x = _x;
      y = _y;
      data = new JSONArray();
  }
  
  public void killMe(){

  }
  
  public void eraseMyData(){
    data = new JSONArray();
  }
  
  public String getIp(){
     return ipAdress;
  }
  
  public int getId(){
     return id;
  }
  
  public JSONArray getData(){
    return data;
  }
  
  public void addItem(String _currentRecordTitle, int id, int _timeStamp, float x, float y){
     JSONObject coordinateObject = new JSONObject();
        coordinateObject.setString("Recording", _currentRecordTitle);
        coordinateObject.setInt("senderId", id);
        coordinateObject.setInt("timeStamp", _timeStamp);
        coordinateObject.setFloat("x", x);
        coordinateObject.setFloat("y", y);
        data.setJSONObject(data.size(), coordinateObject);
  }
}
public void makeGUI(){
  int elementWidth = 160;
  GUI.addSlider("timerInput").setLabel("Timer Intervall").setPosition(20,410).setSize(elementWidth,20).setRange(250,2000).setNumberOfTickMarks(8).setValue(500);
  GUI.addTextfield("recNameInput").setPosition(20,450).setLabel("Name Recording").setSize(elementWidth,20).setFocus(true).setColor(color(255,255,255));
  GUI.addToggle("testMode").setMode(ControlP5.SWITCH).setLabel("Test Mode").setValue(false).setSize(40, 10).setPosition(20,490);
  
  GUI.addButton("setLabels").setLabel("Set Labels").setPosition(20,520).setSize(elementWidth,20);
  GUI.addButton("startRecord").setLabel("Record").setPosition(20,550).setSize(elementWidth,20);
  GUI.addButton("stopRecord").setLabel("Stop").setPosition(20,580).setSize(elementWidth,20);
  //GUI.addButton("newPiece").setLabel("New").setPosition(20,580).setSize(elementWidth,20);
  GUI.addButton("disconnectButton").setLabel("Disconnect All Devices").setPosition(20,610).setSize(elementWidth,20);

  GUI.addTextfield("dimensionLabelOne").setColorValueLabel(color(0,0,0)).setColorCursor(color(0,0,0)).setColorValue(color(0,0,0)).setValue("Leinwand").setPosition(210,520).setLabel("Dimension 1").setSize(elementWidth,20).setColor(color(255,255,255));
  GUI.addTextfield("dimensionLabelTwo").setValue("Performer").setPosition(210,560).setLabel("Dimension 2").setSize(elementWidth,20).setColor(color(255,255,255));
  GUI.addTextfield("dimensionLabelThree").setValue("Sound").setPosition(210,600).setLabel("Dimension 3").setSize(elementWidth,20).setColor(color(255,255,255));
  GUI.getController("dimensionLabelOne").getValueLabel().setColor(darkGray);
  GUI.getController("dimensionLabelTwo").getValueLabel().setColor(darkGray);
  GUI.getController("dimensionLabelThree").getValueLabel().setColor(darkGray);
}

public void drawGUISections(){
  //fill(255,20);
  stroke(200,50);
  //rect(10,10,380, 380);
  //rect(10,400,380, 250);
  //rect(10,660,380, 120);
  //rect(400,10,490,700);
  line(390,30, 390, 690);
  line(20,375,370,375);
  line(20,710,370,710);
  line(410,710,880,710);
  fill(255,15);
  stroke(255, 150);
  triangle(200-170,390-50, 200+170, 390-50, 200, 390-350);
  ellipse(originCollecitveTriangle.x, originCollecitveTriangle.y, 5,5);
  fill(180);
  textSize(30);
  text("IRMA", 20, 750);
  textSize(12);
  text("Interactive Realtime Measurement of Attention", 20, 770);
  text("V0.9", 20, 790);
}

public void drawOnePlaceholder(int _x, int _y, int _id){
  noStroke();
  //fill(255,15);
  fill(finnCardboard);
  rect(_x,_y,149,149);
  fill(0,0,0,20);
  stroke(255,150);
  rect(_x,_y,149,149);
  fill(0,180);
  text(_id, _x + 70, _y + 80);
}

public void drawPlaceholders(){
  fill(255,20);
  stroke(255, 150);
  int w = 149;
  int h = 149;
  int line = 0;
  int count = 1;
  int xcount = 1; 
  while(count <=10){
     if (xcount >= 4) {
        xcount = 1;
        line ++; 
      }
      float x = 410 + ((xcount-1) * 160);
      float y = 30 + (line *170);
      fill(0,0,0,20);
      rect(x, y, h, w);
      fill(0,180);
      text(count, x + 70, y + 80);
      xcount ++;
      count ++;
  } 
}

public void timerInput(int value) {
  //println(value);
  timerIntervall = value;
}

public void setLabels(int theValue) {
    OscMessage serverMessage = new OscMessage("/setLabels"); 
    oscP5.send(serverMessage, myNetAddressList);
    currentRecordTitle = GUI.get(Textfield.class,"recNameInput").getText();
    //boolean testModeActive = GUI.get(CheckBox.class, "testMode").getState(0);
    String dimOne = GUI.get(Textfield.class,"dimensionLabelOne").getText();
    String dimTwo = GUI.get(Textfield.class,"dimensionLabelTwo").getText();
    String dimThree = GUI.get(Textfield.class,"dimensionLabelThree").getText();

    serverMessage.add(currentRecordTitle);
    serverMessage.add(timerIntervall);
    serverMessage.add(str(testMode));
    serverMessage.add(dimOne);
    serverMessage.add(dimTwo);
    serverMessage.add(dimThree);
    oscP5.send(serverMessage, myNetAddressList);
    println("set labels");
}

public void startRecord(int theValue) {
   if (record != true){
    OscMessage serverMessage = new OscMessage("/startRecord");
    //serverMessage.add(timerIntervall);
    currentRecordTitle = GUI.get(Textfield.class,"recNameInput").getText();
    //boolean testModeActive = GUI.get(CheckBox.class, "testMode").getState(0);
    String dimOne = GUI.get(Textfield.class,"dimensionLabelOne").getText();
    String dimTwo = GUI.get(Textfield.class,"dimensionLabelTwo").getText();
    String dimThree = GUI.get(Textfield.class,"dimensionLabelThree").getText();

    serverMessage.add(currentRecordTitle);
    serverMessage.add(timerIntervall);
    serverMessage.add(str(testMode));
    serverMessage.add(dimOne);
    serverMessage.add(dimTwo);
    serverMessage.add(dimThree);
    
    oscP5.send(serverMessage, myNetAddressList);
    record = true;
    fill(360,100,100);
    stroke(255);
    rect(190,520,10,20);
    println("start recording");
   }
}

public void stopRecord(int theValue) {
  if (record == true){
    
    OscMessage serverMessage = new OscMessage("/stopRecord"); 
    oscP5.send(serverMessage, myNetAddressList);
    GUI.get(Textfield.class,"recNameInput").setValue("");
    //currentRecordTitle = "";
    for(int h = 0; h < fixedSenderList.length; h++){
      if (fixedSenderList[h] != null){
        saveJSONArray(fixedSenderList[h].data, "data/"+currentRecordTitle+"_"+fixedSenderList[h].id+".json");
      }
    }
    record = false;
    fill(0,0,0);
    stroke(255);
    rect(190,520,10,20);
    clearData();
    println("stop recording");
  }
  
  
  
  
}

public void newPiece(int theValue) {
  OscMessage serverMessage = new OscMessage("/newRecording"); 
  oscP5.send(serverMessage, myNetAddressList);
  println("setting up new piece");
}

public void disconnectButton(int theValue){
  if (record != true){
    OscMessage serverMessage = new OscMessage("/disconnect"); 
    oscP5.send(serverMessage, myNetAddressList);
    currentRecordTitle = "";
    record = false;
    fill(360,100,100);
    noStroke();
    rect(240,520,20,20);
    drawPlaceholders();
    //println("stop recording");
  }
  //myNetAddressList.
  for(int h = 0; h < fixedSenderList.length; h++){
    if (fixedSenderList[h] != null){
       fixedSenderList[h].killMe();
       fixedSenderList[h] = null;
    }
  }
  for(int i = 0; i < senderList.size(); i++){
    senderList.get(i).killMe();
  }
  
  senderList.clear();
  
  // Redraw part of interface
  fill(360);
  noStroke();
  rect(400,10, 790, 770);
  //fill(255,20);
  //stroke(255, 150);
  //rect(400,10,790,770);
  drawPlaceholders();
  
  //disconnect();
  myNetAddressList = new NetAddressList(); // Reset List to disconnect all Devices

  println(senderList.size());
}

public void controlEvent(ControlEvent theEvent) {
  if(theEvent.isAssignableFrom(Textfield.class)) {
    println("controlEvent: accessing a string from controller '"
            +theEvent.getName()+"': "
            +theEvent.getStringValue()
            );
  }
}


public void input(String theText) {
  // automatically receives results from controller input
  println("a textfield event for controller 'input' : "+theText);
}
  public void settings() {  size(900,820); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#FFFFFF", "--hide-stop", "IRMAhost_0_1_6" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
