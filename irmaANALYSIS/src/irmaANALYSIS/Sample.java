
//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Class to store data of the whole sample of a measurement (comprising of subjects)
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

//  Functions

//  Calculate:
//  getAFA()
//  getDOA()
//  entireAFA --> probably covered by sectionAverage
//  sectionAverage()
//  getColor()


//  Visualize:
//  drawTimeAFA
//  drawTimeDOA
//  drawTimeActivity
//  
//  ------------------------------------------------------------

package irmaANALYSIS;

import java.util.ArrayList;
import math.geom2d.Point2D;

public class Sample {
	static ArrayList<Subject> SubjectsList = new ArrayList<Subject>();
	int datasetLength;
	static int sampleSize = 0;
	
	Sample(){
	       datasetLength = 100;                              // amount of measuring points (temporal) of the sample  
	}

	public static void addSubject(Subject _s){
       SubjectsList.add(_s);
       sampleSize ++;
    }
   
    public Subject getSubject(int i){
       return(SubjectsList.get(i)); 
    }
   
    public Point2D getAFA(int _t) {												// Average Focus of Attention at _t
    	double AFAx = 0;
    	double AFAy = 0;
    	for (int i = 0; i < SubjectsList.size(); i ++) {
    		Point2D cp = SubjectsList.get(i).getPointByIndex(_t);
    		AFAx += cp.x();
    		AFAy += cp.y();
    	}
    	AFAx = AFAx / SubjectsList.size();
    	AFAy = AFAy / SubjectsList.size();
    	
    	Point2D AverageFocusOfAttention = new Point2D(AFAx, AFAy);
    	return AverageFocusOfAttention;
    }
    
    public double getDOA(int _t) {												// Deviation of Attention at _t
    	
    	double distance = 0;
    	for (int i = 0; i < SubjectsList.size(); i ++) {
    		Point2D currentFocus = SubjectsList.get(i).getPointByIndex(_t);
    		Point2D AFA = this.getAFA(_t);
    		double d = AFA.distance(currentFocus);
    		distance += d;
    	}
    	double deviationOfAttention = distance/SubjectsList.size();
    	return deviationOfAttention;
    	
    }
}
