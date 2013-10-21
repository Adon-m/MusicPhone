package app;

import static org.junit.Assert.*;

import org.junit.Test;

import commons.Recommender;
import commons.dataClasses.ConcertInfo;
import commons.dataClasses.GeoPoint;


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
		Recommender r= new Recommender();
		assertNotNull(r.getDestinationsForArtists("Coldplay"));
		assertEquals(r.getDestinationsForArtists("Coldplay").get(0).getVenue(),"Vector Arena");
		
		
		
	}
	@Test
	public void makeSureTheRecomenderClassFindsRelatedArtists() throws Exception{
		Recommender r =new Recommender();
		assertNotNull(r.getRecommendations());
		//assertEquals(r.getRecommendations();
	}

}
