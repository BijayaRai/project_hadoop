package hadoopApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HadoopClass {

	ArrayList<AirlinesDetails> airlineArrayHadoop = new ArrayList<AirlinesDetails>();
	ArrayList<PassangerDetails> passangerArrayHadoop = new ArrayList<PassangerDetails>();
	private Iterator<String> itrator;

	/**
	 * divides the array into smaller chunks to implement Hadoop 
	 */
	public void splitter() {
		int start = (int) Math.floor((passangerArrayHadoop.size() / 3));
		int middle = (int) Math.floor((passangerArrayHadoop.size() / 2));
		int end = passangerArrayHadoop.size();

		ArrayList<PassangerDetails> pdetailChunk1 = new ArrayList<PassangerDetails>(
				passangerArrayHadoop.subList(0, start));
		ArrayList<PassangerDetails> pdetailChunk2 = new ArrayList<PassangerDetails>(
				passangerArrayHadoop.subList(start, middle));
		ArrayList<PassangerDetails> pdetailChunk3 = new ArrayList<PassangerDetails>(
				passangerArrayHadoop.subList(middle, end));
		
		
		HashMap<String, Integer> flightID = new HashMap<String, Integer>();
		flightID = combineFunction(reduceFunction(pdetailChunk1,1),
				reduceFunction(pdetailChunk2,1), reduceFunction(pdetailChunk3,1));
		
		
		for (Map.Entry<String, Integer> entry : flightID.entrySet()) {
			 System.out.println("Flight: "+entry.getKey()+" , "+" Passangers:"+entry.getValue());
		}
		// System.out.println("Flights:"+flightID.size()+" , "+"Total Passanger:" +flightID);

		Map<String, List<PassangerDetails>> passangerByFlightID = mapFlightIDtoPassanger(
				passangerArrayHadoop, flightID);

		for (Entry<String, List<PassangerDetails>> entry : passangerByFlightID
				.entrySet()) {
			List<String> stng = new ArrayList<String>();
			List<PassangerDetails> sd = entry.getValue();
			for (int i = 0; entry.getValue().size() > i; i++) {
				stng.add(sd.get(i).passInfo());
			}
	//		System.out.println("Flight: " + entry.getKey() + " , "	+ " Passangers:" + stng);
		}

	}
	
	/**
	 * 3 blocks are parameter for the function which is then combined into a final hashMap
	 * @param reduceFunction
	 * @param reduceFunction2
	 * @param reduceFunction3
	 * @return Hashmap of combined values of all the HashMaps: contains key and arralist of list of values
	 */
	private HashMap<String, Integer> combineFunction(
			HashMap<String, Integer> reduceFunction,
			HashMap<String, Integer> reduceFunction2,
			HashMap<String, Integer> reduceFunction3) {

		HashMap<String, Integer> blockReduce = new HashMap<String, Integer>();

		//adding the keyset to the set to perform union set function
		Set<String> keys2 = new HashSet<String>();
		keys2.addAll(reduceFunction.keySet());
		keys2.addAll(reduceFunction2.keySet());
		keys2.addAll(reduceFunction3.keySet());

		// System.out.println(reduceFunction3.values());

		itrator = keys2.iterator();

		while (itrator.hasNext()) {
			String k = (String) itrator.next();

			//adding keys and values on the map
			if (!reduceFunction.containsKey(k)) {
				blockReduce.put(k, 0);
			} else
				blockReduce.put(k, reduceFunction.get(k));

			if (!reduceFunction2.containsKey(k)) {
				blockReduce.put(k, blockReduce.get(k));
			} else
				blockReduce.put(k,
						reduceFunction.get(k) + reduceFunction2.get(k));

			if (!reduceFunction3.containsKey(k)) {
				blockReduce.put(k, blockReduce.get(k));
			} else
				blockReduce.put(k, blockReduce.get(k) + reduceFunction3.get(k));
		}

		/*
		 * for (Map.Entry<String, Integer> entry : blockReduce.entrySet()) { //
		 * System.out.println(entry); }
		 */

		return blockReduce;
	}

	/**
	 * @param passangerArrayHadoop- original array with all the {@link PassangerDetails}
	 * @param flightID - hashmap of Flight id and number of flights
	 * @return result of all the passangers on each flight
	 */
	private Map<String, List<PassangerDetails>> mapFlightIDtoPassanger(
			ArrayList<PassangerDetails> passangerArrayHadoop,
			HashMap<String, Integer> flightID) {

		Set<String> set = new HashSet<String>();
		set.addAll(flightID.keySet());

		itrator = set.iterator();

		Map<String, List<PassangerDetails>> mapFlightDetailandId = new HashMap<String, List<PassangerDetails>>();
		List<PassangerDetails> listOfPassangerDetails = new ArrayList<PassangerDetails>();
		List<List<PassangerDetails>> arrayOfDetail = new ArrayList<List<PassangerDetails>>();

		while (itrator.hasNext()) {
			String id = (String) itrator.next();
			int j = 0;
			//value to the hashmap
			for (int i = 0; passangerArrayHadoop.size() > i; i++) {

				if (passangerArrayHadoop.get(i).getFlighId()
						.equalsIgnoreCase(id)) {
					listOfPassangerDetails.add(passangerArrayHadoop.get(i));
				}
			}
			//clone the list to be added to arraylist
			List<PassangerDetails> clone = new ArrayList<PassangerDetails>(
					listOfPassangerDetails.size());
			for (PassangerDetails item : listOfPassangerDetails) {
				clone.add(item);
			}
			
			//add the arraylist to specific location on the arraylist
			arrayOfDetail.add(j, clone);

			//remove the values from the list to collect fresh values
			listOfPassangerDetails.clear();
			mapFlightDetailandId.put(id, arrayOfDetail.get(j));
			j++;
		}

		/*
		 * for(Entry<String, List<String>> entry:
		 * mapFlightDetailandId.entrySet()) {
		 * System.out.println(entry.getKey()+" Value: "+entry.getValue()); }
		 */
		return mapFlightDetailandId;

	}

	
	/**
	 * @param pdetailChunks - the dataset to be mapped
	 * 
	 * @param hashValueSet the branching value to determine the return answer from the function
	 * @return
	 */
	private HashMap<String, Integer> reduceFunction(
			ArrayList<PassangerDetails> pdetailChunks, int hashValueSet) {
		HashMap<String, Integer> newHash3 = new HashMap<String, Integer>();

		if(hashValueSet==1){
		for (PassangerDetails pd : pdetailChunks) {

			if (!newHash3.containsKey(pd.getFlighId())) {
				newHash3.put(pd.getFlighId(), 1);
			} else {
				newHash3.put(pd.getFlighId(), newHash3.get(pd.getFlighId()) + 1);
			}

		}
		return newHash3;
		}
		
		else
		{
			for (PassangerDetails pd : pdetailChunks) {

				if (!newHash3.containsKey(pd.getDepartAirportCode())) {
					newHash3.put(pd.getDepartAirportCode(), 1);
				} else {
					newHash3.put(pd.getDepartAirportCode(), newHash3.get(pd.getDepartAirportCode()) + 1);
				}

			}
		//	System.out.println(newHash3);
			return newHash3;
		}

		/*
		 * int sum = 0; for (Map.Entry<String, Integer> entry :
		 * newHash3.entrySet()) { // System.out.println(entry.getKey() + " " +
		 * entry.getValue()); sum += entry.getValue(); } //
		 * System.out.println("Fuc"+sum);
		 */
		
	}

}
