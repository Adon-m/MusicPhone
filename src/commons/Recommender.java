package commons;


import java.util.List;

import commons.dataClasses.Destination;
import commons.dataClasses.GeoPoint;
import commons.dataClasses.Recommendation;
import commons.interfaces.IConnector;
import commons.interfaces.IGps;
import commons.interfaces.IPlayer;
import commons.interfaces.IRecommender;




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
		try {
			throw new SQATException("You ");
		} catch (SQATException e) {
			e.printStackTrace();
		}
		return null;
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
	public double Calculatedistance (GeoPoint origlon, GeoPoint origlat, GeoPoint destlon, GeoPoint destlat, boolean km){
		double latR1= (Double.parseDouble(orig.getLatitude())*Math.PI)/2;
		double latR2=(Double.parseDouble(dest.getLatitude())*Math.PI)/2;
		double lonR1=(Double.parseDouble(orig.getLongitude())*Math.PI)/2;
		double lonR2=(Double.parseDouble(dest.getLongitude())*Math.PI)/2;
		double latChange=Math.abs(latR2-latR1);
		double lonChange=Math.abs(lonR2-lonR1);
		
		double radius= 6371.01;
		if(km==false)
		{
			radius=3958.76;
		}
		double a=Math.sqrt(Math.pow((latChange/2.0),2.0)+Math.cos(latR1)*Math.cos(latR2)*Math.pow((lonChange/2), 2));
		double GCircleD=2*Math.asin(Math.min(1, a))*radius;
		return GCircleD;
	}
	
}
