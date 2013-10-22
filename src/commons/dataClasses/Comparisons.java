package commons.dataClasses;

import java.util.Comparator;
import java.util.Date;

public class Comparisons extends ConcertInfo implements Comparable<Comparisons>{
	public Comparisons (String artist, String city, String venue, Date startDate, GeoPoint position){
		super (artist,city, venue, startDate, position);
		
	}
	
	public int compareTo(Comparisons c){
		Date d = ((ConcertInfo) c).getStartDate();
		return this.getStartDate().compareTo(d);
	}
	
	public static Comparator<Comparisons> compare = new Comparator<Comparisons>(){
	
	

	@Override
	public int compare(Comparisons first, Comparisons second) {
		Date o1 = first.getStartDate();
		Date o2= second.getStartDate();
		return o1.compareTo(o2);
	}
	};
	
	
	
}
