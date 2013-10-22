package commons.dataClasses;

import java.util.Comparator;
import java.util.Date;

public class Comparisons extends ConcertInfo implements Comparator<ConcertInfo>{
	public Comparisons (String artist, String city, String venue, Date startDate, GeoPoint position){
		super (artist,city, venue, startDate, position);
		
	}
	public int CompareTo(ConcertInfo c){
		Date compare = ((ConcertInfo) c).getStartDate();
		return this.getStartDate().compareTo(c.getStartDate());
	}
	
	@Override
	public int compare(ConcertInfo first, ConcertInfo second) {
		Date o1 = first.getStartDate();
		Date o2= second.getStartDate();
		
		return o1.compareTo(o2);
	}

}
