//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Class to store data of measuring points
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

class Point{
  PVector thisPoint;
  int id;
  int timestamp;
  float x,y;
  float distanceCenter, distanceOne, distanceTwo, distanceThree;
  float angle;
  float activity;
  
  Point(int _id, int _timestamp, float _x, float _y){
    id = _id;
    thisPoint = new PVector(_x, _y);
    timestamp = _timestamp;
    x = _x;
    y = _y;
    distanceCenter = thisPoint.dist(center);
    distanceOne = thisPoint.dist(triOne);                // Distance of Point to Dimension 1
    distanceTwo = thisPoint.dist(triTwo);                // Distance of Point to Dimension 2
    distanceThree = thisPoint.dist(triThree);            // Distance of Point to Dimension 3
    angle = thisPoint.heading()+HALF_PI;                 // Angle of Point related to Center
  }
  
  public PVector getPointVector(){
     return thisPoint;
  }
}
