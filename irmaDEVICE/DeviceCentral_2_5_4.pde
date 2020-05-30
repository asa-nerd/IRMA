//  ------------------------------------------------------------
//  irmaDEVICE V 0.2.5.4
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

import controlP5.*;
import oscP5.*;
import netP5.*;
import java.net.InetAddress;
import java.util.*;

ControlP5 GUI;
InetAddress inet;
OscP5 oscP5;
NetAddressList myNetAddressList = new NetAddressList();

PFont din;
ArrayList<DeviceSender> senderList = new ArrayList<DeviceSender>();
String myIP;
int x, y, lastx, lasty;
int pointCounter = 0;
int myBroadcastPort = 7000;

color guiColorBackground = color(255,180);
color guiColorText = color(0);
int senderCounter = 0;
int senderLine = 0;
int timerIntervall = 250;
String currentRecordTitle = "";
boolean record = false;
boolean testMode = false;

PVector originCollecitveTriangle;
int guiPlaceholderRedraw = 0;

void setup() {
  //size(1920,1080);
  size(1200,860);
  frameRate(60);
  //fullScreen();
  originCollecitveTriangle = new PVector(30, 30);
  oscP5 = new OscP5(this,32000);
  GUI = new ControlP5(this);
  din = createFont("DINPro", 14);
  //textFont(din);
  
  background(0);
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

}

void draw() {
  fill(0);
  noStroke();
  rect(0,0,390,390);
  stroke(255,150);
  triangle(200-170,390-50, 200+170, 390-50, 200, 390-350);
  noStroke();
  fill(255,150);
  for(int h = 0; h < senderList.size(); h++){
    JSONArray allData = senderList.get(h).getData();
    for(int i = 0; i < allData.size(); i++){
        JSONObject o = allData.getJSONObject(i);
        float xo = o.getFloat("x");
        float yo = o.getFloat("y");
        float xAll = map(xo, 0, 1, 0, 340);
        float yAll = map(yo, 0, 1, 0, 310);
        ellipse(originCollecitveTriangle.x + xAll,originCollecitveTriangle.y + yAll, 4,4);
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
}

void oscEvent(OscMessage theOscMessage) {
  if (theOscMessage.addrPattern().equals("/coordinates")) {
    String senderIP = theOscMessage.netAddress().address();
    int timeStamp = theOscMessage.get(0).intValue();
    int currentDeviceId = theOscMessage.get(1).intValue();
    //int currentDeviceId = theOscMessage.get(1).intValue();
    float xSender = theOscMessage.get(2).floatValue();
    float ySender = theOscMessage.get(3).floatValue();

    lastx = x;
    lasty = y;
    float collectiveX = originCollecitveTriangle.x+int(map(xSender, 0, 1, 0, 340));
    float collectiveY = originCollecitveTriangle.y+int(map(ySender, 0, 1, 0, 340));
    x = int(map(xSender, 0, 1, 0, 120));
    y = int(map(ySender, 0, 1, 0, 120));

    for(int i = 0; i < senderList.size(); i++){
      DeviceSender currentSender = senderList.get(i);
      if ((currentSender.getIp()).equals(senderIP)){        // compare Stringt with IP-Adresses
        currentSender.addItem(currentRecordTitle, currentDeviceId, timeStamp, xSender, ySender);
      }
    } 
  }
  
  if (theOscMessage.addrPattern().equals("/connect")) {
    connect(theOscMessage.netAddress().address());
    int deviceID = int(theOscMessage.get(0).stringValue());  
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
    senderList.add(new DeviceSender(theOscMessage.netAddress().address(), deviceID, int(listX), int(listY)));
      senderCounter ++;
      if (senderCounter%4 == 0) {
        senderLine ++; 
        senderCounter = 0;
      }
  }
  
  if (theOscMessage.addrPattern().equals("/disconnect")) {
   int deviceID = theOscMessage.get(0).intValue(); 
    String senderIP = theOscMessage.netAddress().address();
    int deleteNumber = 0;
      for(int i = 0; i < senderList.size(); i ++){
        if (senderList.get(i).getIp().equals(senderIP)){
            deleteNumber = i;
           break;  
        }
      }
      try{
        senderList.get(deleteNumber).killMe();
        senderList.remove(deleteNumber);
      }
      catch (Exception e) {
        println("error");
      }

    // disconnect from NetAdressList
    disconnect(theOscMessage.netAddress().address());
    guiPlaceholderRedraw = deviceID;
   
  }
}



void connect(String theIPaddress) {
     if (!myNetAddressList.contains(theIPaddress, myBroadcastPort)) {
       myNetAddressList.add(new NetAddress(theIPaddress, myBroadcastPort));
       println("### adding "+theIPaddress+" to the list.");
     } else {
       println("### "+theIPaddress+" is already connected.");
     }
     println("### currently there are "+myNetAddressList.list().size()+" remote locations connected.");
}

void disconnect(String theIPaddress) {
  if (myNetAddressList.contains(theIPaddress, myBroadcastPort)) {
      myNetAddressList.remove(theIPaddress, myBroadcastPort);
         println("### removing "+theIPaddress+" from the list.");
  } else {
         println("### "+theIPaddress+" is not connected.");
  }
  println("### currently there are "+myNetAddressList.list().size());
}


public void clearData() {
    for(int i = 0; i < senderList.size(); i++){
      DeviceSender currentSender = senderList.get(i);
      currentSender.eraseMyData(); 
      currentSender.thisCanvas.data = new JSONArray();
    } 
}