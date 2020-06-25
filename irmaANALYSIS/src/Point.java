//  ------------------------------------------------------------
//  irmaANALYSIS V 0.2
//  Class to store data of measuring points
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

import processing.core.*;
import processing.data.*;
import processing.core.PConstants;
import processing.core.PVector;
import java.util.ArrayList;


public class Point implements PConstants{
	PVector thisPoint;
	PVector center;
	PVector triOne, triTwo, triThree;
	int id;
	int timestamp;
	float x,y;
	float distanceCenter, distanceOne, distanceTwo, distanceThree;
	float angle;
	float activity;
	
	
	int triangleSize = 400;
	float triHeight = triangleSize/2 * (float) Math.sqrt(3);   
	  
  Point(int _id, int _timestamp, float _x, float _y){
    id = _id;
    center = new PVector(0,0);
    thisPoint = new PVector(_x, _y);
    triOne = new PVector(-triangleSize/2, triHeight/3);  
    triTwo = new PVector(triangleSize/2, triHeight/3); 
    triThree = new PVector(0, -(triHeight/3*2)); 
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


