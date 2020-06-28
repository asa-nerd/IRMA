package irmaANALYSIS;
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

import processing.core.*;
import processing.data.*;
import java.util.ArrayList;

public class Sample {
	PApplet p;
	ArrayList<Subject> SubjectsList = new ArrayList<Subject>();
	int datasetLength;
	int sampleSize = 0;
	
	Sample(){
	       //sketch = _sketch;
	       datasetLength = 100;                              // amount of measuring points (temporal) of the sample  
	}

	public void addSubject(Subject _s){
       SubjectsList.add(_s);
       sampleSize ++;
    }
   
    public Subject getSubject(int i){
       return(SubjectsList.get(i)); 
    }
   /*
    public PVector getAFA(int _t) {
    	PVector AverageFocusOfAttention = new PVector();
    	for (int i = 0; i < SubjectsList.size(); i ++) {
    		//PVector cp = SubjectsList.get(i).getPointByIndex(_t);
    		AverageFocusOfAttention.x += cp.x;
    		AverageFocusOfAttention.y += cp.y;
    	}
    	AverageFocusOfAttention.x = AverageFocusOfAttention.x / SubjectsList.size();
    	AverageFocusOfAttention.y = AverageFocusOfAttention.y / SubjectsList.size();
    	
    	return AverageFocusOfAttention;
    }
    
    public float getDOA(int _t) {
    	
    	float distance = 0;
    	for (int i = 0; i < SubjectsList.size(); i ++) {
    		PVector currentFocus = SubjectsList.get(i).getPointByIndex(_t);
    		PVector AFA = this.getAFA(_t);
    		float d = PVector.dist(AFA, SubjectsList.get(i).getPointByIndex(_t));
    		distance += d;
    	}
    	float deviationOfAttention = distance/SubjectsList.size();
    	return deviationOfAttention;
    	
    }*/
}
