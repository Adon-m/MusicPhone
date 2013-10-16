package app;

import static org.junit.Assert.*;

import org.junit.Test;

import commons.Recommender;
import commons.dataClasses.GeoPoint;


public class MyTests {
	@Test
	public void CalculateDistanceInMilesAndKM(){
		GeoPoint dest = new GeoPoint("0","0");
		GeoPoint orig = new GeoPoint("60","0");
		
		Recommender r= new Recommender();
		
		assertEquals(r.computeDistance(orig, dest, "km"), 6671.70, 2);
	}

}
