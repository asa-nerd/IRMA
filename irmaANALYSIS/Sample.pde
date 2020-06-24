
//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Class to store data of the whole sample of a measurement (comprising of subjects)
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------


class Sample{
    //PApplet sketch;
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
   
   public PVector getAFA(_t){                            // return Average Focus of Attention of Sample at time t
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
   
  public synchronized PVector entireAFA(int _datasetLength){ // calculates the average focus of attention (AFA) for the whole performace
    PVector entireAverage = new PVector();
    for(int t = 0; t < datasetLength; t ++){
      PVector currentAverage = this.getAFA(t);
      entireAverage.x += currentAverage.x;
      entireAverage.y += currentAverage.y;
    }
    entireAverage.x = entireAverage.x/datasetLength;
    entireAverage.y = entireAverage.y/datasetLength;
    return entireAverage;
  }
   
   public float getDOA(_t){                            // return Deviation of Attention of Sample at time t
       
   }
   
   public static color getColor(PVector _p){          // calculate color for Vector
       
     
   }
}
  
