//  ------------------------------------------------------------
//  irmaANALYSIS V 0.1.9
//  Class to Visualize sample data
//  Andreas Pirchner, 2018-2020
//  ------------------------------------------------------------

import processing.core.*;
import processing.data.*;
import java.util.ArrayList;

public class Visualizer {
	Sample s;
	PApplet p;
	
	Visualizer (Sample _s, PApplet _p){
		s = _s;
		p = _p;
	}
	
	public void drawTimeline(int _begin, int _end) {
		float currentDOA;
		float currentAFA;
		float currentColor;
		int rangeStart = _begin;
		int rangeEnd = _end;
		
		for (int i = rangeStart; i < rangeEnd; i = i + 3) {
			p.color(255,0,0);
			float lHeight = s.getDOA(i) * 10;
			p.line(i, 200, i, 200-lHeight);
		}
		
	}

}
