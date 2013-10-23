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
	
	public void AddToIten(List<Destination> destList, List<Comparisons> concerts, GeoPoint current){
		//make sure the iten list is not empty
		Destination dest =null;
		int index=0;
		int size= concerts.size();
		while(size>0){
			size= concerts.size();
			if(concerts.get(0).getStartDate()!=null){
				 
			// because the RemoveEntries fuction deletes all entries before the date added to the itenerary
			// the next available date is always at index 0 because it is ordered chronologically
		
			index = ClosestDistance (concerts.get(0).getStartDate(), current,concerts);
			dest= new Destination(concerts.get(index));
			
			destList.add(dest);
			
			
			}
			RemoveEntries(concerts, destList.get(destList.size()-1).getStartDate(), destList.get(destList.size()-1).getArtist());
			current = destList.get(destList.size()-1).getPosition();	
			}
			--size;
		}
		
		
		
	
	

	public int ClosestDistance (Date date, GeoPoint current, List<Comparisons> concertList){
		//gets the closest distance of concerts on the same day
		double distance = computeDistance(concertList.get(0).getPosition(), current, "km");
		int indexOnList=0;
		for (int i=0; i<concertList.size(); ++i){
			if(concertList.get(i).getStartDate().compareTo(date)==0){
				
				double temp = computeDistance(concertList.get(i).getPosition(), current, "km");
				if(temp<distance){
					distance=temp;
					indexOnList=i;
				}
				
			}
			
			
		}
		
		
		
		
		
		return (indexOnList);
		
	}
	public void RemoveEntries(List<Comparisons> list, Date date, String artist){
		// this assumes the artists concert has been added to the itenerary list so all entries are removed from the concert list 
		//for rule E2
		
	
		for(int i=0;i<list.size(); ++i)
		{			
		
		if (list.get(i).getArtist().compareTo(artist)==0){
				list.remove(i);
				
			
			}
		}
			for(int i=0;i<list.size(); ++i)
			{
				
				if(list.get(i).getStartDate().compareTo(date)>0 &&i<list.size()){
					list.remove(i);
					
				}
				
			}
			for(int i=0;i<list.size(); ++i)
			{
				
				if(list.get(i).getStartDate().compareTo(date)==0&&i<list.size()){
						list.remove(i);
				}
				
			}
				
				
				
				
		
				
				
				
			}
			
		
		//now that all the artists concerts have been removed  we remove any concert before this date
		//because this has already been added to the itenerary and we keep to rule E0
		
		
		
		
	
	
	
	@Override
	public List<Destination> buildItineraryForArtists(List<String> artists) throws SQATException  {
		//the first thing was to first get the list of artists added by the user then sort them chronologically
		List<Comparisons> concerts = new ArrayList<Comparisons>();
		
		for(int i=0; i<artists.size(); ++i){
			try {
				List<ConcertInfo> concertsForArtist =  (List<ConcertInfo>) (connector.getConcertsForArtist(artists.get(i)));
				
				for(int j=0; j<concertsForArtist.size();++j){
					ConcertInfo con = concertsForArtist.get(j);
				Comparisons comp =new Comparisons (con.getArtist(),con.getCity(),con.getVenue(),con.getStartDate(), con.getPosition());
				
				concerts.add(comp);
				
				}
	
				
			} catch (LastFmConnectionException e) {
				
				e.printStackTrace();
			}
		}
		// sort the concerts by date
		
		Collections.sort(concerts,Comparisons.compare);
		
		/*find the closest concert with the earliest date.
		 * the list is already sorted in chronological order so its just computing the distances between each concert in the
		 * list
		*/
		GeoPoint current =new GeoPoint("70","125");
		
		List <Destination> iten= new ArrayList<Destination>();
//		int index = ClosestDistance(concerts.get(0).getStartDate(), current, concerts);
//		Destination dest = new Destination(concerts.get(index));
//		
//		iten.add(dest);
//		RemoveEntries(concerts, dest.getStartDate(), dest.getArtist());
		
		AddToIten(iten, concerts, current);
		
		
		if(iten.size()>0){
			for(int i=0; i<iten.size();++i){
				System.out.print(iten.get(i).getStartDate()+" "+iten.get(i).getArtist()+" "+iten.get(i).getCity()+" "+"\r" );
			}
		}
		return iten;
		
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
