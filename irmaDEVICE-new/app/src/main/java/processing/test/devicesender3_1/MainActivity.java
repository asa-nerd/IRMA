package processing.test.devicesender3_1;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import processing.android.PFragment;
import processing.core.PApplet;


//-----------------------------------------------------
/*import android.app.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import processing.android.PFragment;
import processing.android.CompatUtils;
import processing.core.PApplet;
import processing.core.PFont;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.Writer;*/

//-----------------------------------------------------

public class MainActivity extends AppCompatActivity{

  /*private PApplet sketch;
  private Button prefButton;

  public SharedPreferences prefs;
  private static String deviceId, ipAdress, timerInterval, resX, resY, sendPort, receivePort, triangleSize, triangleOffset;

  JSONArray recDataJSON = new JSONArray();

  File sd;
  File outputDirectory;
  File outputFile;*/

  private static String deviceId;
  private static String ipAdress;
  private static boolean logging;
  private static String receivePort;
  private static String resX;
  private static String resY;
  private static String sendPort;
  private static String timerInterval;
  private static String triangleOffset;
  private static String triangleSize;
  private AudioManager myAudioManager;
  File outputDirectory;
  File outputFile;
  private Button prefButton;
  public SharedPreferences prefs;
  JSONArray recDataJSON = new JSONArray();
  File sd;
  private PApplet sketch;


  class C03331 implements OnClickListener {
    C03331() {
    }

    public void onClick(View v) {
      MainActivity.this.startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    prefButton = (Button) findViewById(R.id.prefB);
    transferPrefs();

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    if (findViewById(R.id.fragment_container) != null) {

      /* Make Fragment from Processing Class*/
      sketch = new deviceSender3_1();
      /*PFragment profragment = new PFragment(sketch);*/
      Fragment profragment = new PFragment(this.sketch);
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

      /* Add Processing-Fragment to Layout and pass Data*/
      /*FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction ft = fm.beginTransaction();*/
      ft.add(R.id.fragment_container, profragment);
      ft.commit();
      this.prefButton.setOnClickListener(new C03331());
      this.myAudioManager = (AudioManager) getSystemService("audio");
      this.myAudioManager.setRingerMode(0);
    }

    prefButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    });

  }

/*
  @Override
  protected void onResume() {
    super.onResume();
    setContentView(R.layout.main);
    prefButton = (Button) findViewById(R.id.prefB);
    transferPrefs();

    if (findViewById(R.id.fragment_container) != null) {


      sketch = new deviceSender3_1();
      PFragment profragment = new PFragment(sketch);



      FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction ft = fm.beginTransaction();
      ft.add(R.id.fragment_container, profragment);
      ft.commit();
    }

    prefButton.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
      }
    });

  }
  */


  public void saveJSON(File _outputFile){

    try {
      FileWriter file = new FileWriter(_outputFile);
      //file.write(recDataJSON.toJSONString());
      file.write(String.valueOf(recDataJSON));
      file.flush();
      file.close();
    }
    catch(IOException n) {
      System.err.println("Error writing JSON data");
    }

  }

  public void updateJSON(File _outputFile, String _currentRecordName, int _timeStamp, String _deviceID, float _xTriNorm, float _yTriNorm){
    JSONObject coordinateObject = new JSONObject();
    coordinateObject.put("Recording", _currentRecordName);
    coordinateObject.put("senderID", Integer.parseInt(_deviceID));
    coordinateObject.put("timeStamp", _timeStamp);
    coordinateObject.put("x", _xTriNorm);
    coordinateObject.put("y", _yTriNorm);
    recDataJSON.add(coordinateObject);
  }

  public void clearJSON(){
    recDataJSON = new JSONArray();
  }


  public File openOutputFile(String _filename){
    // Setup File for Output
    sd = Environment.getExternalStorageDirectory();
    outputDirectory = new File(this.sd.getAbsolutePath(), "IRMAjson");
    //String path = sd.getAbsolutePath();
    //outputDirectory = new File(path, "recordingBackup");
    // File myFolder = new File(directory);
    if (!outputDirectory.exists()) {
      outputDirectory.mkdirs();
    }

    outputFile = new File(outputDirectory, _filename+".json");
    return outputFile;
  }

  private void transferPrefs(){
    /* Load Preferences and fill them into the Variables */
    prefs = PreferenceManager.getDefaultSharedPreferences(this);
    deviceId = prefs.getString("ID","");
    ipAdress = prefs.getString("IP","");
    sendPort = prefs.getString("sendPort","");
    receivePort = prefs.getString("receivePort","");
    timerInterval = prefs.getString("Timer","");
    resX = prefs.getString("resoX","");
    resY = prefs.getString("resoY","");
    triangleSize = prefs.getString("triangleSize","");
    triangleOffset = prefs.getString("triangleOffsetTop","");
    logging = this.prefs.getBoolean("logging", true);
  }

  public String getDeviceId(){
      return deviceId;
  }

  public String getIpAdress(){
        return ipAdress;
  }

  public String getTimerInterval(){
        return timerInterval;
  }

  public String getResX(){
        return resX;
  }

  public String getResY(){
        return resY;
  }

  public String getSendPort(){
    return sendPort;
  }

  public String getReceivePort(){
    return receivePort;
  }

  public String getTriangleSize(){
    return triangleSize;
  }

  public String getTriangleOffset(){
    return triangleOffset;
  }


  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[],
                                         int[] grantResults) {
    if (sketch != null) {
      sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override
  public void onNewIntent(Intent intent) {
    if (sketch != null) {
      sketch.onNewIntent(intent);
    }
  }




}
