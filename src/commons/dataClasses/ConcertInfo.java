package commons.dataClasses;

import java.util.Date;

public class ConcertInfo {
	private String artist;
	private String city;
	private String venue;
	private Date startDate;
	private GeoPoint position;
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getVanue() {
		return venue;
	}
	public void setVanue(String vanue) {
		this.venue = vanue;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public GeoPoint getPosition() {
		return position;
	}
	public void setPosition(GeoPoint position) {
		this.position = position;
	}
	public ConcertInfo(String artist, String city, String venue,
			Date startDate, GeoPoint position) {
		super();
		this.artist = artist;
		this.city = city;
		this.venue = venue;
		this.startDate = startDate;
		this.position = position;
	}
}
