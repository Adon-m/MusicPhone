package commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import commons.dataClasses.ConcertInfo;
import commons.dataClasses.Destination;
import commons.dataClasses.GeoPoint;
import commons.dataClasses.Recommendation;
import commons.interfaces.IConnector;
import commons.interfaces.IRecommender;

import commons.utils.CustomComparator;


public class Recommender implements IRecommender {
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
		ArrayList<Recommendation> rec = new ArrayList<Recommendation>();
		String currentArtist = DeviceManager.getInstance().getPlayer().getCurrentArtist();
		HashMap<String, Integer> artists = new HashMap<String, Integer>();
		try {
			List<String> topFans = this.connector.getTopFansForArtist(currentArtist);
			for (String fan : topFans) {
				List<String> artist = this.connector.getTopArtistsByFan(fan);
				for (String art : artist) {
					if(artists.containsKey(art)) artists.put(art, artists.get(art) + 1);
					else artists.put(art, 1);
				}
			}
		} catch (Exception e) {
			System.out.print(e);
		}

		/*The entries of artists should be desc sorted by value. See commons.utils.ValueCompartor 
		ValueComparator bvc = new ValueComparator(artists);
		TreeMap<String, Integer> sortedDict = new TreeMap<String, Integer>(bvc);
		 */

		for (Entry<String, Integer> entry : artists.entrySet()) {			
			rec.add(new Recommendation(entry.getKey(), entry.getValue()));

		}

		/* Do the comparison over the ArrayList rather than HashMap. See commons.utils */
		Collections.sort(rec, new CustomComparator());

		// returns only the top 20 artists
		return rec.subList(0, 19);
	}

	@Override
	public List<Destination> getDestinationsForArtists(String artist) {
		ArrayList<Destination> result = new ArrayList<>();
		List<ConcertInfo> concerts;
		Destination dest=null;
		GeoPoint currentPosition = DeviceManager.getInstance().getGps().getCurrentPosition();
		String gpsUnits = DeviceManager.getInstance().getGps().getDistanceUnits();

		try {
			concerts = this.connector.getConcertsForArtist(artist);
		} catch (Exception e) {
			return result;
		}

		for (ConcertInfo concert : concerts) {
			dest = new Destination(concert);

			try {
				dest.setDistance(Distance.ComputeDistance(dest.getPosition(), currentPosition, gpsUnits));
			}

			catch (Exception e) {
				// TODO: handle exception

			} result.add(dest);
		}
		return result;
	}

	@Override
	public List<Destination> buildItineraryForArtists(List<String> artists) throws SQATException  {
		throw new SQATException("You have to implement this");
	}

}