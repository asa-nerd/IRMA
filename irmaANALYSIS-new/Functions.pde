void makeGUI(){
  int buttonWidth = 100;
  
     
  //GUI.addSlider("pieceSlider").setPosition(20,500).setSize(width-40,20).setRange(0,2850);
  GUI.addButton("loader").setLabel("Load File").setPosition(20,20).setSize(buttonWidth,20);
  GUI.addButton("display").setLabel("Display Deviation").setPosition(130,20).setSize(buttonWidth,20);
  GUI.addButton("calculateAnalysis").setLabel("Calc Analysis").setPosition(240,20).setSize(buttonWidth,20);
  GUI.addButton("clearOutput").setLabel("Clear Output").setPosition(350,20).setSize(buttonWidth,20);
  GUI.addButton("printReport").setLabel("Print Report").setPosition(460,20).setSize(buttonWidth,20);
  GUI.addButton("playPiece").setLabel("Play Piece").setPosition(570,20).setSize(buttonWidth,20);
  GUI.addButton("pausePiece").setLabel("Pause Piece").setPosition(680,20).setSize(buttonWidth,20);
  GUI.addButton("stopPiece").setLabel("Stop Piece").setPosition(790,20).setSize(buttonWidth,20);
  GUI.addButton("switchDisplay").setLabel("Switch Display").setPosition(1280,20).setSize(buttonWidth,20);
}


// ---------------------------------------------------------------------------------------------
// Display Functions
// ---------------------------------------------------------------------------------------------

//void printReport(){
  //println("print");
  /*float pieceActivity = 0;
  for(int t = 0; t < 100; t ++){ 
    float currentActivity = getAllActivity(t);  
    pieceActivity += currentActivity;
  }*/
//}



void drawTriangle(){
  stroke(360);
  //noFill();
  fill(0, 0, 360, 50);
  pushMatrix();
    translate(width/2, 350);
    imageMode(CENTER);
    image(colorTriangle, 0,-60);
    triangle(triOne.x, triOne.y, triTwo.x, triTwo.y, triThree.x, triThree.y);
  popMatrix();
}

void drawDimensions(){
  stroke(360);
  pushMatrix();
    translate(width/2, 350);
    line(middleOneTwo.x, middleOneTwo.y, 0,0);
    line(middleTwoThree.x, middleTwoThree.y, 0,0);
    line(middleThreeOne.x, middleThreeOne.y, 0,0);
  popMatrix();
}

void drawSections(){
    stroke(360,50);
    pushMatrix();
    translate(width/2, 350);
      line(triOne.x, triOne.y, 0,0);
      line(triTwo.x, triTwo.y, 0,0);
      line(triThree.x, triThree.y, 0,0);
    popMatrix();
}

// 
// ----------------------------------------------------------------------------------------
public synchronized void drawCentroid(int _timeIndex){
    fill(0, 0, 360);
    noStroke();
    ellipseMode(CENTER);
    //rect((width/2)+triOne.x-30, (350)+triThree.y-30, triangleSize+60, triHeight+60);        //erase old drawings
    drawTriangle();
    drawDimensions();
    drawSections();
    displayOverview();
    PVector centroid = new PVector();
    color c;
    pushMatrix();
    translate(width/2, 350);
      centroid = getCentroid(_timeIndex);
      /*colorMode(RGB);
      c = get(int(centroid.x)+(width/2), int(centroid.y)+350);
      fill(c);
      colorMode(HSB);*/
      /*if(centroidsColor.size() != 0){
        colorMode(RGB);
        c = centroidsColor.get(_timeIndex);
        fill(c);
        colorMode(HSB);
      }*/
      fill(0,0,360);
      ellipse(centroid.x, centroid.y, 10,10);
      float distAvg = getAvgDistanceFromCentroid(centroid, _timeIndex);
      
      // Draw Lines & Dots
      for (int i = 0; i < subjects.size(); i++) {
          PVector currentPoint = subjects.get(i).getPointbyIndex(_timeIndex);
          stroke(0,360, 360, 50);
          line(centroid.x, centroid.y, currentPoint.x, currentPoint.y);
          noStroke();
          fill(0);
          ellipse(currentPoint.x, currentPoint.y, 4, 4);
          textFont(triangleFont);
          text(subjects.get(i).senderID, currentPoint.x+8, currentPoint.y+10);
          text(subjects.get(i).PointsList.get(_timeIndex).activity, currentPoint.x+8, currentPoint.y+20);
      }
      //println(distAvg);
      
      PVector Average = entireAverage(subjects.get(0).getLength());
      color ca = get(int(Average.x)+(width/2), int(Average.y)+350); 
      stroke(0);
      strokeWeight(1);
      fill(ca);
     // ellipse(Average.x, Average.y, 10,10);
      textFont(triangleFont);
      fill(0);
      //text("AVG", Average.x+8, Average.y+10);
      
      // Special Analysis for Kilgore | Sections etc.
      // ------------------------------------------------------------------
      /*PVector firstSectionAverage = sectionAverage(135,282);
      color ca1 = get(int(firstSectionAverage.x)+(width/2), int(firstSectionAverage.y)+350); 
      fill(ca1);
      ellipse(firstSectionAverage.x, firstSectionAverage.y, 10,10);
      fill(0);
      text("A1", firstSectionAverage.x+8, firstSectionAverage.y+10);
      
      PVector secondSectionAverage = sectionAverage(283,1036);
      color ca2 = get(int(secondSectionAverage.x)+(width/2), int(secondSectionAverage.y)+350); 
      fill(ca2);
      ellipse(secondSectionAverage.x, secondSectionAverage.y, 10,10);
      fill(0);
      text("A2", secondSectionAverage.x+8, secondSectionAverage.y+10);
      
      PVector thirdSectionAverage = sectionAverage(1037,1394);
      color ca3 = get(int(thirdSectionAverage.x)+(width/2), int(thirdSectionAverage.y)+350); 
      fill(ca3);
      ellipse(thirdSectionAverage.x, thirdSectionAverage.y, 10,10);
      fill(0);
      text("A3", thirdSectionAverage.x+8, thirdSectionAverage.y+10);
      
      PVector fourthSectionAverage = sectionAverage(1395,2545);
      color ca4 = get(int(fourthSectionAverage.x)+(width/2), int(fourthSectionAverage.y)+350); 
      fill(ca4);
      ellipse(fourthSectionAverage.x, fourthSectionAverage.y, 10,10);
      fill(0);
      text("A4", fourthSectionAverage.x+8, fourthSectionAverage.y+10);
      
      PVector fifthSectionAverage = sectionAverage(2546,2877);
      color ca5 = get(int(fifthSectionAverage.x)+(width/2), int(fifthSectionAverage.y)+350); 
      fill(ca5);
      ellipse(fifthSectionAverage.x, fifthSectionAverage.y, 10,10);
      fill(0);
      text("A5", fifthSectionAverage.x+8, fifthSectionAverage.y+10);*/
      // ------------------------------------------------------------------
      
      
    popMatrix();
}

// getCentroid() returns the average focus of attention (AFA) at a given time
// ----------------------------------------------------------------------------------------
public synchronized PVector getCentroid(int _timeIndex){
  PVector centroid = new PVector();
  for (int i = 0; i < subjects.size(); i++) {
              Subject s = subjects.get(i);
              PVector p = s.getPointbyIndex(_timeIndex);
              Point po = s.PointsList.get(_timeIndex);
              centroid.x += p.x;
              centroid.y += p.y;
  }
  centroid.x = centroid.x/subjects.size();
  centroid.y = centroid.y/subjects.size();
  return centroid; 
}

// entireAverage() calculates the average focus of attention (AFA) for the whole performace
// ----------------------------------------------------------------------------------------
public synchronized PVector entireAverage(int _datasetLength){
  PVector entireAverage = new PVector();
  for(int t = 0; t < _datasetLength; t ++){
    PVector currentAverage = getCentroid(t);
    entireAverage.x += currentAverage.x;
    entireAverage.y += currentAverage.y;
  }
  entireAverage.x = entireAverage.x/_datasetLength;
  entireAverage.y = entireAverage.y/_datasetLength;
  return entireAverage;
}

// sectionAverage() calculates the average focus of attention (AFA) for a section of the performance
// ----------------------------------------------------------------------------------------
public synchronized PVector sectionAverage(int _datasetStart, int _datasetEnd){
  int _datasetLength = _datasetEnd - _datasetStart;
  PVector sectionAverage = new PVector();
  for(int t = _datasetStart; t < _datasetEnd; t ++){
    PVector currentAverage = getCentroid(t);
    sectionAverage.x += currentAverage.x;
    sectionAverage.y += currentAverage.y;
  }
  sectionAverage.x = sectionAverage.x/_datasetLength;
  sectionAverage.y = sectionAverage.y/_datasetLength;
  return sectionAverage;
}

// 
// ----------------------------------------------------------------------------------------
public float getAvgDistanceFromCentroid(PVector _centroid, int _timeIndex){
   float dist = 0;
   for (int i = 0; i < subjects.size(); i++) {
      PVector currentPoint = subjects.get(i).getPointbyIndex(_timeIndex);
      float d = PVector.dist(_centroid, subjects.get(i).getPointbyIndex(_timeIndex));
      dist += d; 
   }
   float distAvg = dist/subjects.size();
   return distAvg;
}

// 
// ----------------------------------------------------------------------------------------
public void drawDeviation(int _x, int _y){
  stroke(0);
  fill(360, 200);
  beginShape();
  //PVector lastVectorOne = new PVector(_x,_y);
  //PVector lastVectorTwo = new PVector(_x,_y);
  for (int i = 0; i < subjects.get(0).getLength(); i++) {
    PVector centroid = getCentroid(i);
    float distArvg = getAvgDistanceFromCentroid(centroid, i);
    
    float xm = map(i, 0, subjects.get(0).getLength(), 0, width-40);
    
    //line(lastVectorOne.x, lastVectorOne.y, _x+xm,_y+distArvg);
    //line(lastVectorTwo.x, lastVectorTwo.y, _x+xm,_y-distArvg);
    //point(_x+xm,_y-distArvg);
    //point(_x+xm,_y+distArvg);
    //lastVectorOne.set(_x+xm,_y+distArvg);
    //lastVectorTwo.set(_x+xm,_y-distArvg);
    //line(_x, _y, width, _y);
    vertex(_x+xm, _y+abs(distArvg));
  }
  color c;
  beginShape();
  vertex(_x, _y);
  for (int i = 0; i < subjects.get(0).getLength(); i++) {
    PVector centroid = getCentroid(i);
    float distArvg = getAvgDistanceFromCentroid(centroid, i);
    if(centroidsColor.size() != 0){
      colorMode(RGB);
      c = centroidsColor.get(i);
      stroke(c);
      colorMode(HSB);
    }
    float xm = map(i, 0, subjects.get(0).getLength(), 0, width-40);
    line(_x+xm, _y, _x+xm, _y+abs(distArvg));
    stroke(0);
    vertex(_x+xm, _y+abs(distArvg));
  }
  vertex(width-20, _y);
  endShape();
  beginShape();
  vertex(_x, _y);
  for (int i = 0; i < subjects.get(0).getLength(); i++) {
    PVector centroid = getCentroid(i);
    float distArvg = getAvgDistanceFromCentroid(centroid, i);
    if(centroidsColor.size() != 0){
      colorMode(RGB);
      c = centroidsColor.get(i);
      stroke(c);
      colorMode(HSB);
    }
    float xm = map(i, 0, subjects.get(0).getLength(), 0, width-40);
    line(_x+xm, _y, _x+xm, _y-abs(distArvg));
    stroke(0);
    vertex(_x+xm, _y-abs(distArvg));
  }
  vertex(width-20, _y);
  endShape();
}

// 
// ----------------------------------------------------------------------------------------
public synchronized void displayOverview(){
  int row = 1;
  int offsetY = 100;
  fill(0,0,360,100);
  noStroke();
  rect(9,offsetY-10, 400,350);
  fill(0,0,360);
  stroke(0,0,360);
  fill(0);
  stroke(0);
  for (int i = 0; i < subjects.size(); i++) {
    Subject c = subjects.get(i);  
    //text(c.filename, 10, (i*20)+offsetY);
    text(c.recName, 10, (i*20)+offsetY);
    text(c.recLength, 100, (i*20)+offsetY);
    text(c.senderID, 140, (i*20)+offsetY);
    text("Dist.: "+nf(c.distanceAll,1,1), 160, (i*20)+offsetY);
    text("Mov.: "+c.movementSum, 230, (i*20)+offsetY);
    text("distance Avg: "+nf(c.distanceAverage,1,2), 300, (i*20)+offsetY);
    line(10, (i*20)+offsetY+8, 400, (i*20)+offsetY+8);
    row ++;
  }
  
}

// 
// ----------------------------------------------------------------------------------------
public synchronized void displayData(){
  int count[] = new int[3];
  count[0] = 0;
  count[1] = 0;
  count[2] = 0;
  
  drawTriangle();
  noStroke();
  pushMatrix();
  translate(width/2, 350);
  for (int i = 0; i < values.size(); i++) {
        JSONObject singleSubjectJSON = values.getJSONObject(i); 
        float xJSON = singleSubjectJSON.getFloat("x");
        float yJSON = singleSubjectJSON.getFloat("y");
        float x = map(xJSON, -0.5, 0.5, triOne.x, triTwo.x);
        float y = map(yJSON, -0.577, 0.287, triThree.y, triOne.y);
        PVector currentPoint = new PVector(x,y);
        
        float angle = currentPoint.heading()+HALF_PI;
        fill(0);
        ellipse(x,y,4,4);
        /*if(angle > -TWO_PI/6 && angle < TWO_PI/6 ){
          fill(0, 360,360);
          ellipse(x,y,4,4);
          count[0] ++;
        }else if(angle > TWO_PI/6 && angle < TWO_PI/2){
          fill(100, 360,360); 
          ellipse(x,y,4,4);
          count[1] ++;
        }else if(angle > TWO_PI/2 || angle < -TWO_PI/6){
          fill(200, 360,360);
          ellipse(x,y,4,4);
          count[2] ++;
        }*/
        if(angle > 0 && angle < TWO_PI/3 ){
          fill(0, 360,360);
          ellipse(x,y,4,4);
          count[0] ++;
        }else if(angle > TWO_PI/3 && angle < TWO_PI/3*2){
          fill(100, 360,360); 
          ellipse(x,y,4,4);
          count[1] ++;
        }else if(angle > -TWO_PI/3*2 || angle < 0){
          fill(200, 360,360);
          ellipse(x,y,4,4);
          count[2] ++;
        }
        stroke(0,10);
        //line(x,y,0,0);
        
          //println(angle);
        noStroke();
        
        
  } 
  //translate(0,0);
  popMatrix();
 // println(count[0]+","+count[1]+","+count[2]+",");
  drawSections();
  drawDimensions();
}
