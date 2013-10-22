package commons;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import player.Player;

import commons.dataClasses.Comparisons;
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

		
		String artist = getPlayer().getCurrentArtist();
		List<Recommendation> rec = new ArrayList<Recommendation>();
		
		// get top fans of currently playing artist
		List<String> topFans= connector.getTopFansForArtist(artist);
		
		HashMap<String, Integer> hash = new HashMap<String , Integer>();
		List<String> artists = new ArrayList<String>();
		for(int i=0; i<topFans.size();++i)
		{
			artists= connector.getTopArtistsByFan(topFans.get(i));
			for(int j = 0; j < artists.size(); ++j) {
				if(hash.containsKey(artists.get(j))) {
					hash.put(artists.get(j), hash.get(artists.get(j)) + 1);
				} else {
					hash.put(artists.get(j), 1);
				}
			}
		}
		for(String key: hash.keySet())
		{
			Recommendation r = new Recommendation(key, hash.get(key));
			rec.add(r);
		}
		
		
		
		
		return rec;
	}

	@Override
	public List<Destination> getDestinationsForArtists(String artist) {
		
		List<Destination> destination= new ArrayList<Destination>();
		try {
			List<ConcertInfo> c= connector.getConcertsForArtist(artist);
			
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
		//the first thing was to first get the list of artists added by the user then sort them chronologically
		List<Comparisons> concerts = new ArrayList<Comparisons>();
		Comparisons comp = null;
		for(int i=0; i<artists.size(); ++i){
			try {
				List<ConcertInfo> c =  (List<ConcertInfo>) (connector.getConcertsForArtist(artists.get(i)));
				
				for(int j=0; j<c.size();++j){
					ConcertInfo con = c.get(j);
				 comp =new Comparisons (con.getArtist(),con.getCity(),con.getVenue(),con.getStartDate(), con.getPosition());
				
				concerts.add(comp);
				comp=null;
				}
		
				
				
				
				
				
				
				
				
				
			} catch (LastFmConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// sort the concerts by date
		for(int i=0; i<concerts.size();++i){
		System.out.print(concerts.get(i).getStartDate()+" "+concerts.get(i).getArtist() +" "+concerts.get(i).getVenue()+"\r");
		}
		System.out.println(" ////////////////////////////////////////////////////////");
		Collections.sort(concerts);
		for(int i=0; i<concerts.size();++i){
			System.out.print(concerts.get(i).getStartDate()+" "+concerts.get(i).getArtist() +" "+concerts.get(i).getVenue()+"\r");
			}
		
		
		
		return null;
		
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
