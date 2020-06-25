
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

class Sample{
   //PApplet sketch;
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
   
   public PVector getAFA(int _t){                            // return Average Focus of Attention of Sample at time t
       PVector centroid = new PVector();
        for (int i = 0; i < SubjectsList.size(); i++) {
                    Subject s = SubjectsList.get(i);
                    PVector p = s.getPointbyIndex(_t);
                    Point po = s.PointsList.get(_t);
                    centroid.x += p.x;
                    centroid.y += p.y;
        }
        centroid.x = centroid.x/SubjectsList.size();
        centroid.y = centroid.y/SubjectsList.size();
        return centroid; 
   }
   
   public float getDOA(PVector _centroid, int _t){                  // return Deviation of Attention of Sample at time t
     float dist = 0;
     for (int i = 0; i < SubjectsList.size(); i++) {
        //PVector currentPoint = SubjectsList.get(i).getPointbyIndex(_t);
        float d = PVector.dist(_centroid, SubjectsList.get(i).getPointbyIndex(_t));
        dist += d; 
     }
     float distAvg = dist/SubjectsList.size();
     return distAvg;
  }

  public PVector entireAFA(int _datasetLength){               // calculates the average focus of attention (AFA) for the whole performace
    PVector entireAverage = new PVector();
    for(int t = 0; t < _datasetLength; t ++){
      PVector currentAverage = this.getAFA(t);
      entireAverage.x += currentAverage.x;
      entireAverage.y += currentAverage.y;
    }
    entireAverage.x = entireAverage.x/datasetLength;
    entireAverage.y = entireAverage.y/datasetLength;
    return entireAverage;
  }
  
  public PVector sectionAverage(int _datasetStart, int _datasetEnd){  // sectionAverage() calculates the average focus of attention (AFA) for a section of the performance
    int _datasetLength = _datasetEnd - _datasetStart;
    PVector sectionAverage = new PVector();
    for(int t = _datasetStart; t < _datasetEnd; t ++){
      PVector currentAverage = getCentroid(t);
      sectionAverage.x += currentAverage.x;
      sectionAverage.y += currentAverage.y;
    }
    sectionAverage.x = sectionAverage.x/_datasetLength;
    sectionAverage.y = sectionAverage.y/_datasetLength;
    return sectionAverage;
  }
   
   
   
   public static color getColor(PVector _p){          // calculate color for Vector
       
     
   }
}
  
