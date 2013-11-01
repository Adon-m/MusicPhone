package app;

import commons.DeviceManager;

import player.Player;
import player.PlayerUI;
import recommender.RecommenderUI;
import gps.GpsAdapter;
import gps.GpsAdapterObserver;
import gps.GpsUI;

public class App {
	static GpsAdapter gps;
	private static GpsAdapterObserver gpsOberver;
	
	
	public static void main(String[] args) {
		
    DeviceManager.getInstance().setPlayer(new Player());
	PlayerUI.createAndShowGUI();
	
		
	new RecommenderUI();
	RecommenderUI.createAndShowGUI();
	
	gps =new GpsAdapter();
	gpsOberver = new GpsAdapterObserver();
	gps.addObserver(gpsOberver);
	
	new GpsUI();
	


	}

}
