package processing.test.devicesender3_1;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import controlP5.ControlP5;
import java.io.File;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscPacket;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.data.JSONArray;
//-----------------------------------------------------

/*
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import controlP5.*;
import oscP5.*;
import netP5.*;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;*/

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//-----------------------------------------------------

/*----------------------------------------------------------------------------------------------------------------------------------------*/
public class deviceSender3_1 extends PApplet{

    boolean connected = false;
    ControlP5 cp5;
    private String currentRecordName = "";
    int currentTime = 0;
    private String deviceID;
    private String dimensionOneLabel = "Leinwand";
    private String dimensionThreeLabel = "Sounds";
    private String dimensionTwoLabel = "Performer";
    PFont din;
    int interfaceColor = color(255, 50);
    private String ipAdress;
    int lastMeasuredTime = 0;
    int marginWidth = 100;
    NetAddress myRemoteLocation;
    private String myfn = "atextFilet.txt";
    PVector originTriangle;
    OscP5 oscP5;
    PrintWriter output;
    File outputFile;
    String[] recData = new String[0];
    JSONArray recDataJSON;
    private String receivePort;
    boolean record = false;
    int recordStartTime = 0;
    private String resX;
    private String resY;
    PVector resolution;
    private String sendPort;
    boolean testMode = false;
    //MainActivity testget = new MainActivity();
    int timeStamp = 0;
    Timer timer;
    private String timerInterval;
    PVector triOne;
    PVector triThree;
    PVector triTwo;
    PVector triangleCenter;
    PVector currentPosEllipse;
    private float triangleHeight;
    private int triangleOffset;
    private int triangleSize;
    float xTriNorm;
    float yTriNorm;


  /*
ControlP5 cp5;
OscP5 oscP5;
NetAddress myRemoteLocation;

//SharedPreferences prefs;
//String deviceId, ipAdress, timerInterval, resX, resY;

private String myfn="atextFilet.txt";
PrintWriter output;
PFont din;

boolean record = false;

int timeStamp = 0;
int currentTime = 0;
int recordStartTime = 0;

int interfaceColor = color(255,50);

PVector resolution;
PVector originTriangle;
int marginWidth = 100;
PVector centerTriangle;

PVector triOne, triTwo, triThree;
    float heightTri;


MainActivity testget = new MainActivity();
private String deviceID, ipAdress, sendPort, receivePort, timerInterval, resX, resY;
private int triangleSize, triangleOffset;
private String currentRecordName = "";

//File outputDirectory;
File outputFile;
String[] recData = {};

JSONArray recDataJSON;
*/



    class PerformanceTimer extends TimerTask {
        PerformanceTimer() {
            recordStartTime = millis();
        }

        @Override
        public void run() {
            timeStamp = millis() - recordStartTime;
            OscMessage myMessage = new OscMessage("/coordinates");
            myMessage.add(timeStamp);
            myMessage.add(Integer.parseInt(deviceID));
            myMessage.add(xTriNorm);
            myMessage.add(yTriNorm);
            myMessage.add(yTriNorm);
            oscP5.send(myMessage, myRemoteLocation);
            ((MainActivity)getActivity()).updateJSON(outputFile, currentRecordName, timeStamp, deviceID, xTriNorm, yTriNorm);
            //testget.updateJSON(outputFile, currentRecordName, timeStamp, deviceID, xTriNorm, yTriNorm);
            //testget.saveJSON(outputFile);
        }
    }
/*----------------------------------------------------------------------------------------------------------------------------------------*/
public void settings() {
    fullScreen();
}


/*----------------------------------------------------------------------------------------------------------------------------------------*/
public void setup() {

    frameRate(60);
    requestPermission("android.permission.WRITE_EXTERNAL_STORAGE");

  // Load Preferences
    deviceID = ((MainActivity)getActivity()).getDeviceId();
    ipAdress = ((MainActivity)getActivity()).getIpAdress();
    sendPort = ((MainActivity)getActivity()).getSendPort();
    receivePort = ((MainActivity)getActivity()).getReceivePort();
    timerInterval = ((MainActivity)getActivity()).getTimerInterval();
    resX = ((MainActivity)getActivity()).getResX();
    resY = ((MainActivity)getActivity()).getResY();
    triangleSize = parseInt(((MainActivity)getActivity()).getTriangleSize());
    triangleOffset = parseInt(((MainActivity)getActivity()).getTriangleOffset());

    resolution = new PVector((parseInt(resX)), parseInt(resY));
    triangleCenter = new PVector((resolution.x / 2.0f) - (marginWidth), (resolution.y / 2.0f) + (triangleOffset));
    xTriNorm = triangleCenter.x;
    yTriNorm = triangleCenter.y;
    triangleHeight = (triangleSize) * PApplet.sqrt(3.0f);
    triOne = new PVector(triangleCenter.x - (triangleSize), triangleCenter.y + (triangleHeight / 3.0f));
    triTwo = new PVector(triangleCenter.x + (triangleSize), triangleCenter.y + (triangleHeight / 3.0f));
    triThree = new PVector(triangleCenter.x, triangleCenter.y - ((triangleHeight / 3.0f) * 2.0f));

    currentPosEllipse = new PVector(triangleCenter.x, triangleCenter.y);

    din = createFont("DINPro", 18*displayDensity);
    textFont(din);
  // set Vecotrs and calculate size of Triangle
  //resolution = new PVector(parseInt(resX), parseInt(resY));
  //centerTriangle = new PVector(resolution.x/2 - marginWidth, resolution.y/2+triangleOffset);

  //float heightTriSquare = sq(2*triangleSize) - sq(triangleSize); //calculate height of triangle with pythagoras
  //heightTri = sqrt(heightTriSquare);
  /*triOne = new PVector(centerTriangle.x - triangleSize, centerTriangle.y+heightTri/3);
  triTwo = new PVector(centerTriangle.x + triangleSize, centerTriangle.y+heightTri/3);
  triThree = new PVector(centerTriangle.x,centerTriangle.y-heightTri/3*2);
  originTriangle = new PVector(triOne.x, triThree.y);*/



  //setup OSC and connect to server
    oscP5 = new OscP5(this, parseInt(receivePort));    // start oscP5, listening for incoming messages at port 12000
    myRemoteLocation = new NetAddress(ipAdress,parseInt(sendPort));
    OscMessage connectMessage = new OscMessage("/connect");
    connectMessage.add(deviceID);
    oscP5.send(connectMessage, myRemoteLocation);


}

/*----------------------------------------------------------------------------------------------------------------------------------------*/
public void draw() {

    //drawBackground and Trianlge;
    colorMode(3, 360.0f, 100.0f, 100.0f);
    background(50.0f, 20.0f, 80.0f);


    stroke(0.0f);
    fill(50.0f, 20.0f, 80.0f);

    triangle(this.triOne.x, this.triOne.y, this.triTwo.x, this.triTwo.y, this.triThree.x, this.triThree.y);
    //triangle(10f,10,300f,100f,30f,500f);



    if (mousePressed) {
        //noFill();
        //strokeWeight(4.0f);
        currentPosEllipse.set(mouseX, mouseY);

        strokeWeight(1.0f);
        float yTri = ((float) this.mouseY) - this.triangleCenter.y;
        this.xTriNorm = PApplet.map(((float) this.mouseX) - this.triangleCenter.x, (float) (-this.triangleSize), (float) this.triangleSize, -0.5f, 0.5f);
        this.yTriNorm = PApplet.map(yTri, ((-this.triangleHeight / 3.0f) * 2.0f), this.triangleHeight / 3.0f, -0.577f, 0.289f);
        if (mouseX > width - 200 && mouseY < 200 && connected) {
            connected = false;
            OscMessage myMessage = new OscMessage("/disconnect");
            myMessage.add(PApplet.parseInt(deviceID));

            this.oscP5.send(myMessage, myRemoteLocation);
        }
        if (mouseX > width - 200 && mouseY > height - 200 && !connected) {
            connected = true;

            OscMessage connectMessage = new OscMessage("/connect");
            connectMessage.add(deviceID);
            this.oscP5.send(connectMessage, myRemoteLocation);
        }
    }

    if (connected) {
        stroke(255);
        fill(50.0f, 20.0f, 80.0f);
        background(0.0f, 0.0f, 20.0f);
        triangle(triOne.x, triOne.y, triTwo.x, triTwo.y, triThree.x, triThree.y);
        fill(255);
        noStroke();
        fill(0,0,20);
        ellipse( currentPosEllipse.x, currentPosEllipse.y, (float) 20, 20);
    }

    if (record) {
        stroke(255);
        fill(0);
        background(0.0f, 0.0f, 0.0f);
        triangle(this.triOne.x, this.triOne.y, this.triTwo.x, this.triTwo.y, this.triThree.x, this.triThree.y);
        fill(255);
        noStroke();
        ellipse( currentPosEllipse.x, currentPosEllipse.y, (float) 20, 20);
    }


    if (testMode) {
        xTriNorm = random(-0.5f, 0.5f);
        yTriNorm = random(-0.577f, 0.289f);
    }

    if (record == true && timeStamp < 5000){
        fill(180);
        pushMatrix();
        translate(triangleCenter.x, triangleCenter.x );
        textAlign(CENTER,CENTER);
        text("Performance begins.\nPlease start your input.", 0.0f, 0.0f);
        popMatrix();
    }


    // Draw Texts
    fill(360);
    stroke(360);
    text(deviceID, 100.0f, 50.0f);
    text(currentRecordName, 100.0f, 150.0f);
    //float fr = frameRate; // Print actual frameRate
    //text(fr,100, 200);

    drawDimensionLabels();



    /*
    background(0);

    stroke(255);
    fill(255);
    text(deviceID, 100,50);
    text(currentRecordName, 100,150);
    //ellipse(originTriangle.x, originTriangle.y, 10, 10);
    //ellipse(centerTriangle.x, centerTriangle.y, 30,30);
    fill(0);
    triangle(triOne.x, triOne.y, triTwo.x, triTwo.y, triThree.x, triThree.y);

    currentTime = millis();
    timeStamp = currentTime - recordStartTime;

    if(mousePressed){
      fill(255, 40);                          // always draw
      stroke(255,60);
      //line(pmouseX, pmouseY, mouseX, mouseY);
      ellipse(mouseX, mouseY, 100,100);
      ellipse(mouseX, mouseY, 150,150);
      ellipse(mouseX, mouseY, 200,200);
      ellipse(mouseX, mouseY, 250,250);
      // Normalize Coordinates
      float xTri = mouseX - originTriangle.x;
      float yTri = mouseY - originTriangle.y;
      float xTriNorm = map(xTri, 0, triangleSize*2, 0, 1);
      float yTriNorm = map(yTri, 0, heightTri, 0, 1);

      if (record == true){                // send data only when recording is activated by server
        OscMessage myMessage = new OscMessage("/coordinates");
        myMessage.add(timeStamp);
        myMessage.add(Integer.parseInt(deviceID));
        myMessage.add(xTriNorm);
        myMessage.add(yTriNorm);
        oscP5.send(myMessage, myRemoteLocation);

        testget.updateJSON(outputFile, currentRecordName, timeStamp, deviceID, xTriNorm, yTriNorm);

      }

      if (mouseX > width-200 && mouseY < 100){
          OscMessage myMessage = new OscMessage("/disconnect");
          oscP5.send(myMessage, myRemoteLocation);
      }
    }

    drawRecodSign();

    */
}

/*----------------------------------------------------------------------------------------------------------------------------------------*/
    public void oscEvent(OscMessage theOscMessage) {
        if (theOscMessage.checkAddrPattern("/setLabels")) {
            currentRecordName = theOscMessage.get(0).stringValue();
            timerInterval = String.valueOf(theOscMessage.get(1).intValue());
            testMode = Boolean.parseBoolean(theOscMessage.get(2).stringValue());
            dimensionOneLabel = String.valueOf(theOscMessage.get(3).stringValue());
            dimensionTwoLabel = String.valueOf(theOscMessage.get(4).stringValue());
            dimensionThreeLabel = String.valueOf(theOscMessage.get(5).stringValue());
        }
        if (theOscMessage.checkAddrPattern("/startRecord")) {
            currentRecordName = theOscMessage.get(0).stringValue();
            timerInterval = String.valueOf(theOscMessage.get(1).intValue());
            testMode = Boolean.parseBoolean(theOscMessage.get(2).stringValue());
            dimensionOneLabel = String.valueOf(theOscMessage.get(3).stringValue());
            dimensionTwoLabel = String.valueOf(theOscMessage.get(4).stringValue());
            dimensionThreeLabel = String.valueOf(theOscMessage.get(5).stringValue());
            record = true;
            recordStartTime = millis();
            outputFile = ((MainActivity)getActivity()).openOutputFile(this.currentRecordName + "_" + this.deviceID);
            if(timer == null) {
                timer = new Timer();
                timer.scheduleAtFixedRate(new PerformanceTimer(), 0, Long.parseLong(this.timerInterval));
            }
        }
        if (theOscMessage.checkAddrPattern("/stopRecord")) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            record = false;
            testMode = false;
            currentRecordName = "";
            ((MainActivity)getActivity()).saveJSON(outputFile);
            //deviceSender3_1.this.testget.saveJSON(deviceSender3_1.this.outputFile);
            ((MainActivity)getActivity()).clearJSON();

        }
        if (theOscMessage.checkAddrPattern("/disconnect")) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            record = false;
            connected = false;
            currentRecordName = "";
            ((MainActivity)getActivity()).saveJSON(this.outputFile);
            ((MainActivity)getActivity()).clearJSON();

        }
    }

    public void drawBackground() {
        colorMode(3, 360.0f, 100.0f, 100.0f);
        background(50.0f, 20.0f, 80.0f);
        if (connected) {
            background(115.0f, 0.0f, 20.0f);
        }
        if (record) {
            background(0.0f, 60.0f, 30.0f);
        }
        colorMode(1, 255.0f);
    }

    /*
    public void oscEvent(OscMessage theOscMessage) {

        if(theOscMessage.checkAddrPattern("/startRecord")){
            record = true;
            recordStartTime = millis();
            currentRecordName = theOscMessage.get(0).stringValue();
            outputFile = testget.openOutputFile(currentRecordName);
        }
        if(theOscMessage.checkAddrPattern("/stopRecord")){
            record = false;
            currentRecordName = "";
            testget.saveJSON(outputFile);
            testget.clearJSON();
        }

    }
*/
    public void drawDimensionLabels() {
        fill(60);
        if (this.record) {
            fill(180);
        }
        textAlign(LEFT,BASELINE);
        pushMatrix();
        translate(this.triOne.x + 34.0f, this.triOne.y - 15.0f);
        rotate(-0.5235988f);
        text(this.dimensionOneLabel, 0.0f, 0.0f);
        popMatrix();
        pushMatrix();
        translate(this.triTwo.x - 126.0f, this.triTwo.y - 64.0f);
        rotate(0.5235988f);
        text(this.dimensionTwoLabel, 0.0f, 0.0f);
        popMatrix();
        pushMatrix();
        translate(this.triThree.x - 8.0f, this.triThree.y + 36.0f);
        rotate(1.5707964f);
        text(this.dimensionThreeLabel, 0.0f, 0.0f);
        popMatrix();
    }


    public void drawRecodSign(){
        if (record == true){
          fill(255,0,0);
        }else{
          fill(0,0,0);
        }
        ellipse(10, height-40, 20, 20);
    }

    public void disco(){
        OscMessage myMessage = new OscMessage("/disconnect");
        myMessage.add(ipAdress);
        myMessage.add(Integer.parseInt(deviceID));
        oscP5.send(myMessage, myRemoteLocation);
    }

}

