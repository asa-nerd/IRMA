//  ------------------------------------------------------------
//  irmaANALYSIS V 0.2
//  Class to store subject data
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------


// Constants
// totalActivity, averageActivity

//  Functions

//  1. General 
//  getLength()
//  getRecName()

//  2. Calculate:
//  getPointsByIndex
//  getPointbyIndex
//  getPointsDistance
//  getMovementSum
//  getCurrentActivity

/// 3. Visualize:
//  drawActivity
//  drawDistanceOne
//  drawDistanceTwo
//  drawDistanceThree
//  drawSubject
//  ------------------------------------------------------------

package irmaANALYSIS;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.*;
import math.geom2d.Point2D;

public class Subject {
	
	JSONArray subjectPoints;

	ArrayList<Point> PointsList = new ArrayList<Point>(); 						// Store measuring points of subject
//	PVector triOne, triTwo, triThree;
	//int triangleSize = 400;
//	float triHeight = triangleSize/2 * (float) Math.sqrt(3);   
	
	double totalActivity = 0;
	double averageActivitySample;
	
	Subject(JSONArray _subjectPoints){
		subjectPoints = _subjectPoints;
	    
//	    triOne = new PVector(-triangleSize/2, triHeight/3);  
//	    triTwo = new PVector(triangleSize/2, triHeight/3); 
//	    triThree = new PVector(0, -(triHeight/3*2)); 
	    
	   // for (int i = 0; i < subjectPoints.size(); i++) {               			// Iterate subject`s measuring points
	    Iterator<JSONObject> iterator = subjectPoints.iterator();
            while (iterator.hasNext()) {
               
                JSONObject pointJSON = iterator.next();								// get current point     		
		        double xJSON = (double) pointJSON.get("x");                       		// load x,y and timeStamp
		        double yJSON = (double) pointJSON.get("y");
		        long ts = (long) pointJSON.get("timeStamp");                          
		        PointsList.add(new Point(1, ts, xJSON, yJSON));                       		// store coordinates in Point array 
	    	}
	    
	   for (int i = 1; i < PointsList.size(); i++) {                  			// calculate activity for Points and save them in the Point-Objects
	          Point lastPoint = PointsList.get(i-1);
	          Point currentPoint = PointsList.get(i);
	          double activity = Point2D.distance(lastPoint.getPoint(), currentPoint.getPoint());
	          currentPoint.activity = activity;
	          totalActivity += activity;
	    }
	    
	    averageActivitySample = totalActivity/PointsList.size();
		
	}
	
	// 1. General functions
	
	
	// 2. Calculations
	public Point2D getPointByIndex(int _t) {		
		return PointsList.get(_t).getPoint();
	}
	
	// 3. Visualize
}
