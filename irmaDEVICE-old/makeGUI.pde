void makeGUI(){
  int elementWidth = 160;
  GUI.addSlider("timerInput").setPosition(20,410).setSize(elementWidth,20).setRange(250,2000).setNumberOfTickMarks(8);
  GUI.addTextfield("recNameInput").setPosition(20,450).setLabel("Name Recording").setSize(elementWidth,20).setFocus(true).setColor(color(255,255,255));
  GUI.addToggle("testMode").setMode(ControlP5.SWITCH).setLabel("Test Mode").setValue(false).setSize(40, 10).setPosition(20,490);
  
  GUI.addButton("startRecord").setLabel("Record").setPosition(20,520).setSize(elementWidth,20);
  GUI.addButton("stopRecord").setLabel("Stop").setPosition(20,550).setSize(elementWidth,20);
  //GUI.addButton("newPiece").setLabel("New").setPosition(20,580).setSize(elementWidth,20);
  GUI.addButton("disconnectButton").setLabel("Disconnect All Devices").setPosition(20,580).setSize(elementWidth,20);

  GUI.addTextfield("dimensionLabelOne").setValue("Sound").setPosition(210,520).setLabel("Dimension 1").setSize(elementWidth,20).setColor(color(255,255,255));
  GUI.addTextfield("dimensionLabelTwo").setValue("Performer").setPosition(210,560).setLabel("Dimension 2").setSize(elementWidth,20).setColor(color(255,255,255));
  GUI.addTextfield("dimensionLabelThree").setValue("Visual").setPosition(210,600).setLabel("Dimension 3").setSize(elementWidth,20).setColor(color(255,255,255));
}

void drawGUISections(){
  fill(255,20);
  stroke(255, 150);
  rect(10,10,380, 380);
  rect(10,400,380, 380);
  rect(400,10,790,770);
  fill(255,15);
  stroke(255, 150);
  triangle(200-170,390-50, 200+170, 390-50, 200, 390-350);
  ellipse(originCollecitveTriangle.x, originCollecitveTriangle.y, 5,5);
}

void drawOnePlaceholder(int _x, int _y, int _id){
  fill(0);
  noStroke();
  rect(_x,_y-10,150,159);
  fill(255,35);
  stroke(255,150);
  rect(_x,_y,149,149);
  fill(255, 190);
  text(_id, _x + 70, _y + 80);
}

void drawPlaceholders(){
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
      fill(255,15);
      rect(x, y, h, w);
      fill(255,190);
      text(count, x + 70, y + 80);
      xcount ++;
      count ++;
  } 
}

void timerInput(int value) {
  //println(value);
  timerIntervall = value;
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
    fill(255,0,0);
    stroke(255);
    rect(190,520,10,20);
    
    println(testMode);
   }
}

public void stopRecord(int theValue) {
  if (record == true){
    OscMessage serverMessage = new OscMessage("/stopRecord"); 
    oscP5.send(serverMessage, myNetAddressList);
    GUI.get(Textfield.class,"recNameInput").setValue("");
    currentRecordTitle = "";
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
  if (record == true){
    OscMessage serverMessage = new OscMessage("/disconnect"); 
    oscP5.send(serverMessage, myNetAddressList);
    currentRecordTitle = "";
    record = false;
    fill(0,0,0);
    stroke(255);
    rect(240,520,20,20);
    drawPlaceholders();
    //println("stop recording");
  }
  //myNetAddressList.
  for(int i = 0; i < senderList.size(); i++){
    senderList.get(i).killMe();
  }
  
  senderList.clear();
  
  // Redraw part of interface
  fill(0);
  noStroke();
  rect(400,10, 790, 770);
  fill(255,20);
  stroke(255, 150);
  rect(400,10,790,770);
  drawPlaceholders();
  
  //disconnect();
  myNetAddressList = new NetAddressList(); // Reset List to disconnect all Devices

  println(senderList.size());
}

void controlEvent(ControlEvent theEvent) {
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