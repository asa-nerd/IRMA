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

DeviceSender[] fixedSenderList = new DeviceSender[12];

String myIP;
int x, y, lastx, lasty;
int pointCounter = 0;
int myBroadcastPort = 7000;

float superviseX, superviseY;

color guiColorBackground = color(255,180);
color guiColorText = color(0);
color strokeColor = color(0,100);
color finnCardboard, darkGray, lightGray, middleGray, darkRed;
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

void setup() {
  //size(1920,1080);
  size(900,820);
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
  middleOneThree = PVector.lerp(dimensionOneOrigin, dimensionThreeOrigin, 0.5);
  middleOneTwo = PVector.lerp(dimensionOneOrigin, dimensionTwoOrigin, 0.5);
  middleTwoThree = PVector.lerp(dimensionTwoOrigin, dimensionThreeOrigin, 0.5);
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

void draw() {
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
            float xAll = map(xo, -0.5, 0.5, -triangleWidth/2, triangleWidth/2);
            float yAll = map(yo, -0.433, 0.433, -triangleHeight/2, triangleHeight/2);
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
              float drawX = int(map(x, -0.5, 0.5, -senderTriangleWidth/2, senderTriangleWidth/2));
              float drawY = int(map(y, -0.433, 0.433, -senderTriangleHeight/2, senderTriangleHeight/2));
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
    for(int i = 0; i < fixedSenderList.length; i++){
      if (fixedSenderList[i] != null){
        fixedSenderList[i].eraseMyData(); 
      }
    }
}
