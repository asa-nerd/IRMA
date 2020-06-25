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
	
	Sample(){
	       //sketch = _sketch;
	       datasetLength = 100;                              // amount of measuring points (temporal) of the sample  
	}

	public void addSubject(Subject _s){
       SubjectsList.add(_s);
    }
   
    public Subject getSubject(int i){
       return(SubjectsList.get(i)); 
    }
}
