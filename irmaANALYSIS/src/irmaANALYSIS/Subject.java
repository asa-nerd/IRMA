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
	
	JSONArray JSONData;
	JSONArray subjectPoints;

	ArrayList<Point> PointsList = new ArrayList<Point>(); 						// Store measuring points of subject
	Point2D triOne, triTwo, triThree;
	long triangleSize = 400;
	double triHeight = triangleSize/2 * Math.sqrt(3);   
	long id;
	String recordName;
	double age; 
	String education;
	double totalActivity = 0;
	double averageActivitySample;
	int dataSetLength;
	
	
	Subject(JSONArray _JSONData){
		JSONData = _JSONData;

		JSONObject u = (JSONObject) JSONData.get(0);
		id = (long) u.get("id");
		
		recordName = (String) u.get("recording");
		JSONObject persBackground = (JSONObject) u.get("personalBackground");
		//System.out.println(persBackground);
		age = (double) persBackground.get("Age");
		education = (String) persBackground.get("Education");
		
		JSONObject j = (JSONObject) JSONData.get(1);
		JSONArray subjectPoints = (JSONArray) j.get("timesequence");
		dataSetLength = subjectPoints.size();
		//JSONObject firstDataset = (JSONObject) subjectPoints.get(0);
		
	    triOne = new Point2D(-triangleSize/2, triHeight/3);  
	    triTwo = new Point2D(triangleSize/2, triHeight/3); 
	    triThree = new Point2D(0, -(triHeight/3*2)); 
	                  			
	    Iterator<JSONObject> iterator = subjectPoints.iterator();					// Iterate subject`s measuring points
            while (iterator.hasNext()) {
               
                JSONObject pointJSON = iterator.next();								// get current point     		               
                Object tx = pointJSON.get("x");										// load x,y and timeStamp
		        Object ty = pointJSON.get("y");
		        double xJSON = Double.parseDouble(tx.toString());
		        double yJSON = Double.parseDouble(ty.toString());
		        long ts = (long) pointJSON.get("timeStamp");                          
		        PointsList.add(new Point(1, ts, xJSON, yJSON));                     // store coordinates in Point array 
	    	}
	    
	   for (int i = 1; i < PointsList.size(); i++) {                  				// calculate activity for Points and save them in the Point-Objects
	          Point lastPoint = PointsList.get(i-1);
	          Point currentPoint = PointsList.get(i);
	          double activity = Point2D.distance(lastPoint.getPoint(), currentPoint.getPoint());
	          currentPoint.activity = activity;
	          totalActivity += activity;
	    }
	    
	    averageActivitySample = totalActivity/PointsList.size();
		
	}
	
	// 1. General functions
	public int getDatasetLength() {
		return dataSetLength;
	}
	
	public long getId() {
		return id;
	}
	
	public String getRecordName() {
		return recordName;
	}
	
	public double getAge() {
		return age;
	}
	
	public String getEducation() {
		return education;
	}
	
	// 2. Calculations
	public Point2D getPointByIndex(int _t) {		
		return PointsList.get(_t).getPoint();
	}
	
	public double getActivity(int _t) {
		double currentActivity;
		if (_t == 0) {
			currentActivity = 0;	
		}else {
			currentActivity = PointsList.get(_t).getActivityData();
		}
		return currentActivity;
	}
	
	// 3. Visualize
}
