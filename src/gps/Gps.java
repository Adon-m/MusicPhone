package gps;

import commons.dataClasses.GeoPoint;
import commons.interfaces.IGps;

public class Gps implements IGps{
	private GeoPoint position;
	private String distanceUnits;
	public Gps(){
		DeviceManager.Instance.Gps = this;
	}
	@Override
	public GeoPoint getCurrentPosition() {
		return this.position;
	}

	@Override
	public String getDistanceUnits() {
		// TODO Auto-generated method stub
		return this.distanceUnits;
	}

	@Override
	public void setCurrentPosition(GeoPoint value) {
		this.position=value;
		
	}

	@Override
	public void setDistanceUnits(String value) {
		this.distanceUnits=value;
		
	}
	
}
