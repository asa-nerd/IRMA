//  ------------------------------------------------------------
//  irmaANALYSIS V 0.2
//  Class to store data of measuring points
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

package irmaANALYSIS;

import math.geom2d.Point2D;

public class Point{
	Point2D thisPoint;
	Point2D center;
	Point2D triOne, triTwo, triThree;
	int id;
	long timestamp;
	double x,y;
	double distanceCenter, distanceOne, distanceTwo, distanceThree;
	double angle;
	double activity;
	
	
	int triangleSize = 400;
	double triHeight = triangleSize/2 * Math.sqrt(3);   
	  
  Point(int _id, long _timestamp, double _x, double _y){
    id = _id;
    center = new Point2D(0,0);
    thisPoint = new Point2D(_x, _y);
    triOne = new Point2D(-triangleSize/2, triHeight/3);  
    triTwo = new Point2D(triangleSize/2, triHeight/3); 
    triThree = new Point2D(0, -(triHeight/3*2)); 
    timestamp = _timestamp;
    x = _x;
    y = _y;
    distanceCenter = thisPoint.distance(center);
    distanceOne = thisPoint.distance(triOne);                // Distance of Point to Dimension 1
    distanceTwo = thisPoint.distance(triTwo);                // Distance of Point to Dimension 2
    distanceThree = thisPoint.distance(triThree);            // Distance of Point to Dimension 3
    //angle = thisPoint.heading()+HALF_PI;                 // Angle of Point related to Center
  }
  
  public Point2D getPoint(){
     return thisPoint;
  }
  
  public double getActivityData() {
	  return activity;
  }
}


