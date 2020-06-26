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

import processing.core.*;
import processing.data.*;
import java.util.ArrayList;


public class Subject {
	
	JSONArray subjectPoints;
	String filename;
	PApplet p;

	ArrayList<Point> PointsList = new ArrayList<Point>(); 						// Store measuring points of subject
	PVector triOne, triTwo, triThree;
	int triangleSize = 400;
	float triHeight = triangleSize/2 * (float) Math.sqrt(3);   
	
	float totalActivity = 0;
	float averageActivity;
	
	Subject(JSONArray _subjectPoints, String _filename, PApplet _sketch){
		subjectPoints = _subjectPoints;
	    filename = _filename; 
	    p = _sketch;
	    
	    triOne = new PVector(-triangleSize/2, triHeight/3);  
	    triTwo = new PVector(triangleSize/2, triHeight/3); 
	    triThree = new PVector(0, -(triHeight/3*2)); 
	    
	    for (int i = 0; i < subjectPoints.size(); i++) {               			// Iterate subject`s measuring points
	        JSONObject pointJSON = subjectPoints.getJSONObject(i);       		// get current point
	        float xJSON = pointJSON.getFloat("x");                       		// load x,y and timeStamp
	        float yJSON = pointJSON.getFloat("y");
	        int ts = pointJSON.getInt("timeStamp");
	        float x = p.map(xJSON, -0.5f, 0.5f, triOne.x, triTwo.x);         		// normalize coordinates for output triangle
	        float y = p.map(yJSON, -0.577f, 0.287f, triThree.y, triOne.y);                            
	        PointsList.add(new Point(i, ts, x,y));                       		// store coordinates in Point array 
	    }
	    
	    for (int i = 1; i < PointsList.size(); i++) {                  			// calculate activity for Points and save them in the Point-Objects
	          Point lastPoint = PointsList.get(i-1);
	          Point currentPoint = PointsList.get(i);
	          float activity = PVector.dist(lastPoint.getPointVector(), currentPoint.getPointVector());
	          currentPoint.activity = activity;
	          totalActivity += activity;
	    }
	    
	    averageActivity = totalActivity/PointsList.size();
		
	}
	
	// 1. General functions
	
	
	// 2. Calculations
	public PVector getPointByIndex(int _t) {		
		return PointsList.get(_t).getPointVector();
	}
	
	// 3. Visualize
}
