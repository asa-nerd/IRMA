import processing.core.*;
 
public class OutputWin extends PApplet {
 
  private int w, h;
 
  public OutputWin() {
    //w = 200;
    //h = 200;
  }
 
  public OutputWin(int w, int h) {
    this.w = w;
    this.h = h;
  }
 
  public void settings() {
    size(w, h);

  }
  
  public void setup(){
     background(255);   
  }
 
  public void draw() {
    //super.draw();
    if (redo == true){
        fill(0, 30);
        for (int i = 0; i < subjects.size(); i++) {
          subjects.get(i).drawActivity(20, (i+1)*100, width, "SCREEN");
        }
        redo = false;
    }
    
    if (clearOutput == true){
       background(255); 
       clearOutput = false;
    }

  }
  
  
  synchronized public void testDraw(){
    println(frameCount);
    this.background(0);
    background(0);
    //PApplet.background(0);
    this.fill(0);
     this.ellipse(random(width), random(height), 10,10); 
  }
}