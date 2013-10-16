package commons;


import java.util.ArrayList;
import java.util.List;

import commons.dataClasses.ConcertInfo;
import commons.dataClasses.Destination;
import commons.dataClasses.GeoPoint;
import commons.dataClasses.Recommendation;
import commons.interfaces.IConnector;
import commons.interfaces.IGps;
import commons.interfaces.IPlayer;
import commons.interfaces.IRecommender;
import dataConnectors.LastFmConnectionException;
import dataConnectors.LastFmXmlConnector;




public class Recommender implements IRecommender {
	public Recommender(IConnector connector) {
	
		this.connector = connector;
	}
	
	public Recommender(){
		this.connector = null;
	}

	private IConnector connector;
	@Override
	public IConnector getConnector() {

		return this.connector;
	}

	@Override
	public void setConnector(IConnector connector) {
		this.connector = connector;

	}

	@Override
	public List<Recommendation> getRecommendations() throws Exception {
		throw new SQATException("You need ");
	}

	@Override
	public List<Destination> getDestinationsForArtists(String artist) {
		LastFmXmlConnector last = new LastFmXmlConnector();
		List<Destination> destination= new ArrayList<Destination>();
		try {
			List<ConcertInfo> c= last.getConcertsForArtist(artist);
			
			for (int i=0;i<c.size();i++)
			{
			Destination dest = new Destination(c.get(i)); 
			
			destination.add(dest);
			}
		} catch (LastFmConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destination;
	}

	@Override
	public List<Destination> buildItineraryForArtists(List<String> artists) throws SQATException  {
		throw new SQATException("You");
	}
	
	public  IPlayer getPlayer(){
		return DeviceManager.getInstance().getPlayer();
	}
	
	public  IGps getGps(){
		return DeviceManager.getInstance().getGps();
	}
	public double computeDistance (GeoPoint orig, GeoPoint dest, String  metric){
		
		double radius = 0.0;
		if(metric=="mi")
		{
			radius=3958.76;
		}
		if(metric=="km")
		{
			radius=6371.01;
		}
		
		//convert the values to radians
		double latR1=(Double.parseDouble(orig.getLatitude())*Math.PI)/180;
		double lonR1=(Double.parseDouble(orig.getLongitude())*Math.PI)/180;
		double latR2=(Double.parseDouble(dest.getLatitude())*Math.PI)/180;
		double lonR2=(Double.parseDouble(dest.getLongitude())*Math.PI)/180;
		
		double latDelta=(latR2-latR1);
		double lonDelta=(lonR2-lonR1);
		
		double a=Math.sqrt(Math.pow((Math.sin(latDelta/2)),2)+Math.cos(latR1)*Math.cos(latR2)*Math.pow((Math.sin(lonDelta/2)),2));
		
		
		double GCircleD=2*Math.asin(Math.min(1.0, a))*radius;
		return GCircleD;
	}
	
}
