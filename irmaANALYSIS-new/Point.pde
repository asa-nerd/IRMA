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
    distanceOne = thisPoint.dist(triOne);
    distanceTwo = thisPoint.dist(triTwo);
    distanceThree = thisPoint.dist(triThree);
    angle = thisPoint.heading()+HALF_PI;
  }
  
  public PVector getPointVector(){
     return thisPoint;
  }
}