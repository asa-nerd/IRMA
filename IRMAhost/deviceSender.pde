//  ------------------------------------------------------------
//  Class storing the connected IRMA clients
//  ------------------------------------------------------------


class DeviceSender {
  String ipAdress;
  int id;
  int x, y;
  JSONArray data;

  DeviceSender(String _ipAdress, int _id, int _x, int _y) {
    ipAdress = _ipAdress;
    id = _id;
    x = _x;
    y = _y;
    data = new JSONArray();
  }

  void killMe() {
  }

  void eraseMyData() {
    data = new JSONArray();
  }

  String getIp() {
    return ipAdress;
  }

  int getId() {
    return id;
  }

  JSONArray getData() {
    return data;
  }

  void addItem(String _currentRecordTitle, int id, int _timeStamp, float x, float y) {
    JSONObject coordinateObject = new JSONObject();
    coordinateObject.setString("Recording", _currentRecordTitle);
    coordinateObject.setInt("senderId", id);
    coordinateObject.setInt("timeStamp", _timeStamp);
    coordinateObject.setFloat("x", x);
    coordinateObject.setFloat("y", y);
    data.setJSONObject(data.size(), coordinateObject);
  }
}
