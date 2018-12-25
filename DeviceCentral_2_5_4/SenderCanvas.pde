class SenderCanvas extends Canvas {
  float n;
  float a;
  JSONArray data;
  PVector originTriangle;
  
  SenderCanvas(JSONArray _data){
    data = _data;
    originTriangle = new PVector(15, 15);
  }
  
  public void setup(PGraphics pg) {
    size(150,150);
    data = new JSONArray();
    //println("starting a test canvas.");
    n = 1;
  }
  
  public void draw(PGraphics pg) {
    pg.fill(0);
    pg.rect(0,0,150,150);
    n += 0.01;
    pg.ellipseMode(CENTER);
    //pg.fill(lerpColor(color(0,100,200),color(0,200,100),map(sin(n),-1,1,0,1)),60);
    pg.fill(255,50);
    pg.rect(0,0,150,150);
    pg.noFill();
    pg.stroke(255,150);
    pg.triangle(75-60,150-20, 75+60, 150-20, 75, 150-135);
    pg.noStroke();
    pg.fill(255, 100);
    for(int i = 0; i < data.size(); i++){
      JSONObject o = data.getJSONObject(i);
      float x = o.getFloat("x");
      float y = o.getFloat("y");
      float drawX = int(map(x, 0, 1, 0, 120));
      float drawY = int(map(y, 0, 1, 0, 120));
      
      pg.ellipse(originTriangle.x + drawX,originTriangle.y + drawY, 4,4);
    }
    //ellipse(originTriangle.x, originTriangle.y, 5,5);
  }
  
}