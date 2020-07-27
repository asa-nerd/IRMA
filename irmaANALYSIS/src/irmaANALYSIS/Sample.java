
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
	ArrayList<Subject> SubjectsList = new ArrayList<Subject>();
	int datasetLength;
	int sampleSize;
	
	Sample(){          
		sampleSize = 0;
	}

	public void clearSample() {
		SubjectsList.clear();
	}
	
	public void addSubject(Subject _s){
       SubjectsList.add(_s);
       datasetLength = getShortestDataset();						// amount of measuring points (temporal) of the sample  
       sampleSize ++;
    }
	
	public ArrayList<Subject> getSubjectList(){
		return SubjectsList;
	}
	
	public int getShortestDataset() {						// find the shortes Dataset in the Sample to avoid breaking the iterator while looping through non-existent data
		int minimalLength = 1000000000;
		for (int i = 0; i < SubjectsList.size(); i ++) {
			int testLength = SubjectsList.get(i).getDatasetLength();
			if (testLength < minimalLength) {
				minimalLength = testLength;
			}
		}
		return minimalLength;
	}
   
    public Subject getSubject(int i){
       return(SubjectsList.get(i)); 
    }
   
    public Point2D getSectionAFA(int _start, int _end) {
    	int len = _end-_start;
    	double AFAx = 0;
    	double AFAy = 0;
    	
    	for (int i = _start; i < _end; i++) {
    		Point2D cp = getAFA(i);
    		AFAx += cp.x();
    		AFAy += cp.y();
    	}
    	AFAx = AFAx / len;
    	AFAy = AFAy / len;
    	Point2D SectionAFA = new Point2D(AFAx, AFAy);
    	return SectionAFA;
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
    
    public double getActivity(int _t) {
    	double sampleAct = 0;
    	for (int i = 0; i < SubjectsList.size(); i ++) {
    		double a = SubjectsList.get(i).getActivity(_t);
    		sampleAct += a; 
    	}
    	return sampleAct;
    }
}
