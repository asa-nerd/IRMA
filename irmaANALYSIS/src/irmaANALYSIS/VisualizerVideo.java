package irmaANALYSIS;

import javafx.scene.Group;
import javafx.scene.media.*;
import javafx.util.Duration;

public class VisualizerVideo {
	Group g;
	Media media;
	MediaPlayer mediaPlayer;
	MediaView mediaView;
	
	VisualizerVideo(){
		g = new Group();
	}
	
	
	public Group getVisualizerListContainer() {
		return g;
	}
	
	public void playVideo() {
		mediaPlayer.play();
	}
	
	public void pauseVideo() {
		mediaPlayer.pause();
	}
	public void stopVideo() {
		mediaPlayer.stop();
	}
	
	public void jumpTo(int _t) {
		//mediaPlayer.pause();
       // mediaPlayer.seek(new Duration(mediaPlayer.getCurrentTime().toMillis() +10000));
		if (_t > 1) {
			mediaPlayer.seek(new Duration(_t*500));
		}
	}
	
	public void initVideo(String _f) {
		String n = "file:"+_f;
		media = new Media(n);
	    mediaPlayer = new MediaPlayer(media);  
	    mediaView = new MediaView (mediaPlayer);
	    mediaView.setFitWidth(640);
	    g.getChildren().add(mediaView);
	    
	}
}
