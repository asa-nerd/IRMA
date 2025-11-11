void oscEvent(OscMessage theOscMessage) {
  if (theOscMessage.addrPattern().equals("/coordinates")) {
    String senderIP = theOscMessage.netAddress().address();
    int timeStamp = theOscMessage.get(0).intValue();
    int currentDeviceId = theOscMessage.get(1).intValue();
    float xSender = theOscMessage.get(2).floatValue();
    float ySender = theOscMessage.get(3).floatValue();
    lastx = x;
    lasty = y;
    superviseX = xSender;
    superviseY = ySender;
    float collectiveX = originCollecitveTriangle.x+int(map(xSender, 0, 1, 0, 340));
    float collectiveY = originCollecitveTriangle.y+int(map(ySender, 0, 1, 0, 340));
    x = int(map(xSender, 0, 1, 0, 120));
    y = int(map(ySender, 0, 1, 0, 120));
    fixedSenderList[currentDeviceId].addItem(currentRecordTitle, currentDeviceId, timeStamp, xSender, ySender);
  }

  if (theOscMessage.addrPattern().equals("/connect")) {
    connect(theOscMessage.netAddress().address());
    int deviceID = int(theOscMessage.get(0).stringValue());
    float listX = 0;
    float listY = 0;

    switch (deviceID) {
    case 1:
      listX = 410;
      listY = 30;
      break;
    case 2:
      listX = 570;
      listY = 30;
      break;
    case 3:
      listX = 730;
      listY = 30;
      break;
    case 4:
      listX = 410;
      listY = 200;
      break;
    case 5:
      listX = 570;
      listY = 200;
      break;
    case 6:
      listX = 730;
      listY = 200;
      break;
    case 7:
      listX = 410;
      listY = 370;
      break;
    case 8:
      listX = 570;
      listY = 370;
      break;
    case 9:
      listX = 730;
      listY = 370;
      break;
    case 10:
      listX = 410;
      listY = 540;
      break;
    }
    if (record != true) {
      fixedSenderList[deviceID] = new DeviceSender(theOscMessage.netAddress().address(), deviceID, int(listX), int(listY));
    }
  }

  if (theOscMessage.addrPattern().equals("/disconnect")) {
    int deviceID = theOscMessage.get(0).intValue();
    String senderIP = theOscMessage.netAddress().address();
    int deleteNumber = deviceID;
    try {
      fixedSenderList[deleteNumber].killMe();
      fixedSenderList[deleteNumber] = null;
    }
    catch (Exception e) {
      println("error");
    }
    // disconnect from NetAdressList
    disconnect(theOscMessage.netAddress().address());
    guiPlaceholderRedraw = deviceID;
  }
}
