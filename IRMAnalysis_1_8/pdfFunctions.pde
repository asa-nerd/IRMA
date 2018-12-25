// ---------------------------------------------------------------------------------------------
// PDF Functions
// ---------------------------------------------------------------------------------------------

import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;


void fileOutput(File selection) {
  int pdfPageHeight = (int)com.lowagie.text.PageSize.A1.getWidth();
  int pdfPageWidth = (int)com.lowagie.text.PageSize.A1.getHeight();
  
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    //println("User selected " + selection.getAbsolutePath());
    pdf = (PGraphicsPDF) createGraphics(pdfPageWidth, pdfPageHeight, PDF, selection.getName());
    
    pdf.beginDraw();
    pdf.textMode(MODEL);
    //pdf.textFont(pdfFont);
    pdf.textFont(pdfFontDeviation);
    pdf.fill(0);
    pdf.stroke(100);
    pdf.strokeWeight(0.4);
  
    /*pdfDisplayDeviationColor(0, subjects.size(), pdf);*/
    pdfDisplayAverageActivity(0, subjects.size(), pdf);
    /*pdfDisplayDeviation(0, subjects.size(), pdf);
    pdfDisplayActivity(0, subjects.size(), pdf);
    /*pdfDisplayDistance(1,0,subjects.size(), pdf);
    pdfDisplayDistance(2,0,subjects.size(), pdf);
    pdfDisplayDistance(3,0,subjects.size(), pdf);*/
    //pdf.nextPage();
    pdf.dispose();
    pdf.endDraw();
  }
}

public void pdfDisplayDeviationColor(int beginIndex, int endIndex, PGraphicsPDF _pdf){
   /* for (int k = beginIndex; k < endIndex; k++) {
      
      
    }*/
  int _x = 100;
  int _y = 250;
  int xScaling = 3;
  color c;
  //_pdf.beginShape();
  //_pdf.vertex(_x, _y);
  for (int i = 0; i < subjects.get(0).getLength(); i++) {
    PVector centroid = getCentroid(i);
    float distArvg = getAvgDistanceFromCentroid(centroid, i);
    if(centroidsColor.size() != 0){
      _pdf.colorMode(RGB);
      c = centroidsColor.get(i);
      _pdf.stroke(c);
      pdf.colorMode(HSB);
    }
    float xm = map(i, 0, subjects.get(0).getLength(), 0, width-40);
    float currentX = i/xScaling;
    _pdf.line(_x+currentX, _y, _x+currentX, _y+abs(distArvg));
    //_pdf.line(_x+xm, _y, _x+xm, _y+abs(distArvg));
    _pdf.stroke(0);
    //_pdf.vertex(_x+xm, _y+abs(distArvg));
  }
  //_pdf.vertex(width-20, _y);
  //_pdf.endShape();
  //_pdf.beginShape();
  //_pdf.vertex(_x, _y);
  for (int i = 0; i < subjects.get(0).getLength(); i++) {
    PVector centroid = getCentroid(i);
    float distArvg = getAvgDistanceFromCentroid(centroid, i);
    if(centroidsColor.size() != 0){
      _pdf.colorMode(RGB);
      c = centroidsColor.get(i);
      _pdf.stroke(c);
      _pdf.colorMode(HSB);
    }
    float xm = map(i, 0, subjects.get(0).getLength(), 0, width-40);
    float currentX = i/xScaling;
    _pdf.line(_x+currentX, _y, _x+currentX, _y-abs(distArvg));
    //_pdf.line(_x+xm, _y, _x+xm, _y-abs(distArvg));
    _pdf.stroke(0);
    //_pdf.vertex(_x+xm, _y-abs(distArvg));
  }
  //_pdf.vertex(width-20, _y);
  //_pdf.endShape();
  _pdf.fill(0,0,360);
  _pdf.noStroke();
  _pdf.rect(_x, _y-4, width-40, 8);
  _pdf.fill(0,0,0);
  _pdf.stroke(0);
  for (int i = 0; i < subjects.get(0).getLength(); i += 30) {
    float xm = map(i, 0, subjects.get(0).getLength(), 0, width-40);
    /*_pdf.fill(0,0,360);
    _pdf.noStroke();
    _pdf.rect(_x+xm, _y-1, 10, 2.5);
    _pdf.fill(0,0,0);*/
    float currentX = i/xScaling;
    _pdf.line(_x+currentX, _y+4, _x+currentX, _y-12);
    _pdf.text(calcTime((int)floor(xm)), _x+currentX+1, _y);
  }
  
}


public void pdfDisplayDeviation(int beginIndex, int endIndex, PGraphicsPDF _pdf){
    if(subjects.get(0) != null){
      _pdf.text("Piece: "+subjects.get(0).recName,10,pdfYglobal);
    }
    tb();
    _pdf.text("Deviation | Time",10,pdfYglobal);
    
    
    for (int k = beginIndex; k < endIndex; k++) {
      
      // calculate centroid of subjects at given time
      PVector centroid = new PVector();
      for (int i = 0; i < subjects.size(); i++) {
            Subject s = subjects.get(i);
            PVector p = s.getPointbyIndex(k);
            centroid.x += p.x;
            centroid.y += p.y;
      }
      centroid.x = centroid.x/(endIndex-beginIndex);
      centroid.y = centroid.y/(endIndex-beginIndex);
      
      //now calculate the deviation of the Points from 
    }
    
    
    
}


public void pdfDisplayDistance(int _dimension, int beginIndex, int endIndex, PGraphicsPDF _pdf) {
    if(subjects.get(0) != null){
      _pdf.text("Piece: "+subjects.get(0).recName,10,pdfYglobal);
    }
    tb();
    _pdf.text("Distance Dimension "+_dimension +" | Time",10,pdfYglobal);
    for (int i = beginIndex; i < endIndex; i++) {
          Subject s = subjects.get(i);
          tb();
          _pdf.text("Subject "+s.senderID,10,pdfYglobal);
          lb(); 
          if (_dimension == 1){
            s.drawDistanceOne(10, pdfYglobal, pdfPageWidth-100, "PDF");
          }else if (_dimension == 2){
            s.drawDistanceTwo(10, pdfYglobal, pdfPageWidth-100, "PDF");
          }else if (_dimension == 3){
            s.drawDistanceThree(10, pdfYglobal, pdfPageWidth-100, "PDF");
          }
          tb();
          _pdf.textFont(pdfFontSmall);
          _pdf.text("Filename: "+s.filename+",  Measuring Points: "+s.recLength+",  Overall Distance: "+s.distanceAll+",  Movements: "+s.movementSum+",  Avg Distance: "+s.distanceAverage,10,pdfYglobal);
          _pdf.textFont(pdfFont);  
    }
}

public void pdfDisplayAverageActivity(int beginIndex, int endIndex, PGraphicsPDF _pdf){
    int recordLength = subjects.get(0).getLength();
    float[] allActivity = new float[recordLength];
    for (int t = 0; t < recordLength; t++){
      float currentActivity = 0;
      for (int i = beginIndex; i < endIndex; i++) {
          Subject s = subjects.get(i);
          float ac = s.getCurrentActivity(t);
          currentActivity += ac;
      }
      allActivity[t] = currentActivity;
      float xm = map(t, 0, recordLength, 0, width);
      float _y = 500;
      pdf.line(+(xm), _y, +(xm), _y-(currentActivity/subjects.size()));
    } 
     
}

public void pdfDisplayActivity(int beginIndex, int endIndex, PGraphicsPDF _pdf) {
    if(subjects.get(0) != null){
      _pdf.text("Piece: "+subjects.get(0).recName,10,pdfYglobal);
    }
    tb();
    _pdf.text("Activity | Time",10,pdfYglobal);
    for (int i = beginIndex; i < endIndex; i++) {
          Subject s = subjects.get(i);
          tb();
          _pdf.text("Subject "+s.senderID,10,pdfYglobal);
          lb(); 
          s.drawActivity(10, pdfYglobal, pdfPageWidth-100, "PDF");
          tb();
          _pdf.textFont(pdfFontSmall);
          _pdf.text("Filename: "+s.filename+",  Measuring Points: "+s.recLength+",  Overall Distance: "+s.distanceAll+",  Movements: "+s.movementSum+",  Avg Distance: "+s.distanceAverage,10,pdfYglobal);
          _pdf.textFont(pdfFont);          
    }
}

public void lb(){
  if (pdfYglobal < pdfPageHeight - pdfGraphicLineHeight){
    pdfYglobal += pdfGraphicLineHeight;
  }else{
    pdf.nextPage();
    pdfYglobal = 50;
    pdfYglobal += pdfGraphicLineHeight;
  }
}

public void tb(){
  if (pdfYglobal < pdfPageHeight - pdfTextLineHeight){
    pdfYglobal += pdfTextLineHeight;
  }else{
    pdf.nextPage();
    pdfYglobal = 50;
    pdfYglobal += pdfTextLineHeight;
  }
}
