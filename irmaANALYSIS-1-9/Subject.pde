//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Class to store subject data
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

//  Functions

//  General 
//  getLength()
//  getRecName()

//  Calculate:
//  getPointsByIndex
//  getPointbyIndex
//  getPointsDistance
//  getMovementSum
//  getCurrentActivity

/// Visualize:
//  drawActivity
//  drawDistanceOne
//  drawDistanceTwo
//  drawDistanceThree
//  drawSubject
//  ------------------------------------------------------------


class Subject{
   JSONArray subjectPoints;
   String filename;
   PApplet sketch;

   ArrayList<PVector> points = new ArrayList<PVector>(); // delete, probably not used
   ArrayList<Point> PointsList = new ArrayList<Point>(); // this is used
   
   int interval;
   int recLength;
   String recName; 
   int recIntervall;
   int senderID; 
   int lastTimecode;
   float distanceAll;
   int movementSum;
   float distanceAverage;
   float xJSON, yJSON, x, y;
   int ts; 
   
  
   Subject(JSONArray _subjectPoints, String _filename, PApplet _sketch){
      subjectPoints = _subjectPoints;
      filename = _filename; 
      sketch = _sketch;
      
      for (int i = 0; i < subjectPoints.size(); i++) {               // Iterate subject`s measuring points
        JSONObject pointJSON = subjectPoints.getJSONObject(i);       // get current point
        xJSON = pointJSON.getFloat("x");                             // load x,y and timeStamp
        yJSON = pointJSON.getFloat("y");
        ts = pointJSON.getInt("timeStamp");
        x = map(xJSON, -0.5, 0.5, triOne.x, triTwo.x);               // normalize coordinates for output triangle
        y = map(yJSON, -0.577, 0.287, triThree.y, triOne.y);
        //points.add(new PVector(x,y));                                
        PointsList.add(new Point(i, ts, x,y));                       // store coordinates in Point array 
      }
      
      for (int i = 1; i < PointsList.size(); i++) {                  // calculate activity for Points and save them in the Point-Objects
          Point lastPoint = PointsList.get(i-1);
          Point currentPoint = PointsList.get(i);
          float activity = PVector.dist(lastPoint.getPointVector(), currentPoint.getPointVector());
          currentPoint.activity = activity;
      }
      
      // set variables for whole dataset
      recLength = subjectPoints.size();                               // length
      lastTimecode = PointsList.get(PointsList.size()-1).timestamp;   // lastTimecode
      distanceAll = getPointsDistance(0, recLength-1);                // sum of movement distance of subject
      movementSum = getMovementSum(0, recLength-1);                  
      distanceAverage = distanceAll / movementSum;                    // average distance per time interval of subject
      
      JSONObject firstPoint = subjectPoints.getJSONObject(1);         // extract recording meta data from first data point
      recName = firstPoint.getString("Recording");                    // recording name
      senderID = firstPoint.getInt("senderId");                       // sender ID
      recIntervall = 500;//firstPoint.getInt("senderId");             // time interval
   }
   
   public int getLength(){
      return recLength;
   }
   
   public String getRecName(){
      return recName;
   }
   
   public ArrayList getPointsByIndex(int beginIndex, int endIndex){
      ArrayList<PVector> splicedPoints = new ArrayList<PVector>();
      return splicedPoints;
   }
   
   public PVector getPointbyIndex(int index){
      PVector p =  PointsList.get(index).getPointVector();
      return p;
   }
   
   public float getPointsDistance(int beginIndex, int endIndex){
     float distanceSum = 0;
     if (beginIndex >= 0 && endIndex <= PointsList.size()){
       for (int i = beginIndex; i <= endIndex-1; i++) {      
          //PointsList.get(i).
          PVector testOne = PointsList.get(i).getPointVector();
          PVector testTwo = PointsList.get(i+1).getPointVector();
          float dist = testOne.dist(testTwo);
          distanceSum += dist;
       }
       return distanceSum;
     }else{
       return -1; 
     }
   }
   
   public int getMovementSum(int beginIndex, int endIndex){
     int movementSum = 0;
     if (beginIndex >= 0 && endIndex <= PointsList.size()){
       for (int i = beginIndex; i <= endIndex-1; i++) {  
           PVector testOne = PointsList.get(i).getPointVector();
           PVector testTwo = PointsList.get(i+1).getPointVector();
           float dist = testOne.dist(testTwo);
           if (dist > 0){
               movementSum ++;
           }
       }
     }
     return movementSum;
   }
   
   public float getCurrentActivity(int _currentPoint){
       Point currentPoint = PointsList.get(_currentPoint);
       float ac = currentPoint.activity;
       return ac;
   }
   
   public void drawActivity(int _x, int _y, int _width, String _target){
      stroke(0, 100);
      for (int i = 0; i < PointsList.size(); i++) {
        Point currentPoint = PointsList.get(i);
        float ac = currentPoint.activity/2;
        float xm = map(i, 0, this.recLength, 0, _width);
        if (_target == "PDF"){
           pdf.line(_x+(xm), _y, _x+(xm), _y-ac);
        }else{
           sketch.line(_x+(xm), _y, _x+(xm), _y-ac);
        }
      }
   }
   
   
   public void drawDistanceOne(int _x, int _y, int _width, String _target){
      stroke(0, 30);
      for (int i = 0; i < PointsList.size(); i++) {
        Point currentPoint = PointsList.get(i);
        float d = currentPoint.distanceOne/5;
        float xm = map(i, 0, this.recLength, 0, _width);
        //sketch.line(0+(i/4), 500, 0+(i/4), 500-d);
        if (_target == "PDF"){
           pdf.line(_x+(xm), _y, _x+(xm), _y-d);
        }else{
           sketch.line(_x+(xm), _y, _x+(xm), _y-d);
        }
      }
   }
   
   public void drawDistanceTwo(int _x, int _y, int _width, String _target){
      stroke(0, 30);
      for (int i = 0; i < PointsList.size(); i++) {
        Point currentPoint = PointsList.get(i);
        float d = currentPoint.distanceTwo/5;
        float xm = map(i, 0, this.recLength, 0, _width);
        //sketch.line(0+(i/4), 700, 0+(i/4), 700-d);
        if (_target == "PDF"){
           pdf.line(_x+(xm), _y, _x+(xm), _y-d);
        }else{
           sketch.line(_x+(xm), _y, _x+(xm), _y-d);
        }
      }
   }
   
   public void drawDistanceThree(int _x, int _y, int _width, String _target){
      stroke(0, 30);
      for (int i = 0; i < PointsList.size(); i++) {
        Point currentPoint = PointsList.get(i);
        float d = currentPoint.distanceThree/5;
        float xm = map(i, 0, this.recLength, 0, _width);
        //sketch.line(0+(i/4), 900, 0+(i/4), 900-d);
        if (_target == "PDF"){
           pdf.line(_x+(xm), _y, _x+(xm), _y-d);
        }else{
           sketch.line(_x+(xm), _y, _x+(xm), _y-d);
        }
      }
   }
   
   public void drawSubject(){
     noStroke();
     pushMatrix();
     translate(width/2, height/2);
     for (int i = 0; i < PointsList.size(); i++) {
       //PVector currentPoint = points.get(i);
       Point currentPoint = PointsList.get(i);
       float angle = currentPoint.angle;
       //float angle = currentPoint.heading()+HALF_PI;
       if(angle > 0 && angle < TWO_PI/3 ){
          fill(0, 360,360);
          sketch.ellipse(currentPoint.x,currentPoint.y,4,4);
          //count[0] ++;
        }else if(angle > TWO_PI/3 && angle < TWO_PI/3*2){
          fill(100, 360,360); 
          sketch.ellipse(currentPoint.x,currentPoint.y,4,4);
          //count[1] ++;
        }else if(angle > -TWO_PI/3*2 || angle < 0){
          fill(200, 360,360);
          sketch.ellipse(currentPoint.x,currentPoint.y,4,4);
          //count[2] ++;
        }
       
       //sketch.ellipse(currentPoint.x, currentPoint.y, 2,2);
     }
     popMatrix();
   }
   
   
}
