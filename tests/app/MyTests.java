package app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import player.Player;

import commons.DeviceManager;
import commons.Recommender;
import commons.SQATException;
import commons.dataClasses.ConcertInfo;
import commons.dataClasses.GeoPoint;
import dataConnectors.LastFmXmlConnector;


public class MyTests {
	@Test
	public void CalculateDistanceInKM(){
		GeoPoint dest = new GeoPoint("0","60");
		GeoPoint orig = new GeoPoint("0","0");
		
		Recommender r= new Recommender();
		
		assertEquals(r.computeDistance(orig, dest, "km"), 6671.70, 2);
	}
	@Test
	public void CalculateDistanceInMI(){
		GeoPoint dest = new GeoPoint("33.94","-118.40");
		GeoPoint orig = new GeoPoint("36.12","-86.67");
		
		Recommender r= new Recommender();
		
		assertEquals(r.computeDistance(orig, dest, "mi"), 1793.55, 2);
	}
	@Test
	public void makeSureGetDestinationsReturnsCorrectInfo(){
		Recommender r= new Recommender(new LastFmXmlConnector());
		
		assertNotNull(r.getDestinationsForArtists("Coldplay"));
		assertEquals(r.getDestinationsForArtists("Coldplay").get(0).getVenue(),"Vector Arena");
		
		
		
	}
	@Test
	public void makeSureTheRecomenderClassFindsRelatedArtists() throws Exception{
		(new Player()).setCurrentArtist("Cher");
		
		Recommender r =new Recommender(new LastFmXmlConnector());
		assertNotNull(r.getRecommendations());
		//assertEquals(r.getRecommendations();
	}
	@Test
	public void makeSureTheDatesAreSortedInTHeITerniary(){
		// Lists some Artists
		List <String> artists = new ArrayList<String>();
		
	
		
	
		artists.add("Britney Spears");
	artists.add("Coldplay");
	
artists.add("Kylie Minogue");
//	artists.add("Radiohead");
//	artists.add("Snow Patrol");
	artists.add("The Who");
	Recommender r = new Recommender(new LastFmXmlConnector());
	
	try {
		r.buildItineraryForArtists(artists);
	} catch (SQATException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
