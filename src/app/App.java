package app;

import player.PlayerUI;
import recommender.RecommenderUI;
import gps.GpsAdapter;
import gps.GpsAdapterObserver;
import gps.GpsUI;

public class App {
	static GpsAdapter gps;
	private static GpsAdapterObserver gpsOberver;
	
	
	public static void main(String[] args) {
		
    PlayerUI playObject = new PlayerUI();	
	PlayerUI.createAndShowGUI();
	
		
	RecommenderUI RecObject = new RecommenderUI();
	RecObject.createAndShowGUI();
	
	gps =new GpsAdapter();
	gpsOberver = new GpsAdapterObserver();
	gps.addObserver(gpsOberver);
	
	GpsUI gpsObject = new GpsUI();
	GpsUI.createAndShowGUI();

	
	}

}
