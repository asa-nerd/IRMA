class DeviceSender {
  String ipAdress;
  int id;
  int x,y;
  JSONArray data;
  SenderCanvas thisCanvas;
  
  DeviceSender(String _ipAdress, int _id, int _x, int _y){
      ipAdress = _ipAdress;
      id = _id;
      x = _x;
      y = _y;
      data = new JSONArray();
      thisCanvas = new SenderCanvas(data);
      GUI.addGroup("myGroup"+id).setLabel(id+" |   "+ ipAdress).setPosition(x,y).setWidth(150).addCanvas(thisCanvas);  
      //println("Device Object added "+this.getIp()); 
  }
  
  void killMe(){
    GUI.getGroup("myGroup"+id).hide();
    GUI.getGroup("myGroup"+id).remove();
  }
  
  void eraseMyData(){
    data = new JSONArray();
    //println("Device Object cleared "+this.getId()); 
  }
  
  String getIp(){
     return ipAdress;
  }
  
  int getId(){
     return id;
  }
  
  JSONArray getData(){
    return data;
  }
  
  void addItem(String _currentRecordTitle, int id, int _timeStamp, float x, float y){
     JSONObject coordinateObject = new JSONObject();
        coordinateObject.setString("Recording", _currentRecordTitle);
        coordinateObject.setInt("senderId", id);
        coordinateObject.setInt("timeStamp", _timeStamp);
        coordinateObject.setFloat("x", x);
        coordinateObject.setFloat("y", y);
        data.setJSONObject(data.size(), coordinateObject);
        saveJSONArray(data, "data/"+currentRecordTitle+"_"+id+".json");
        thisCanvas.data = data;
  }
}