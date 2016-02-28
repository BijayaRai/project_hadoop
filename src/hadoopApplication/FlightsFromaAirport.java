package hadoopApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FlightsFromaAirport {

	ArrayList<AirlinesDetails> airlineArrayHadoop = new ArrayList<AirlinesDetails>();
	ArrayList<PassangerDetails> passangerArrayHadoop = new ArrayList<PassangerDetails>();
	private Iterator<String> itrator;
	
	public void airportFlights()
	{
		
		int start = (int) Math.floor((passangerArrayHadoop.size() / 3));
		int middle = (int) Math.floor((passangerArrayHadoop.size() / 2));
		int end = passangerArrayHadoop.size();

		ArrayList<PassangerDetails> pdetailChunk1 = new ArrayList<PassangerDetails>(
				passangerArrayHadoop.subList(0, start));
		ArrayList<PassangerDetails> pdetailChunk2 = new ArrayList<PassangerDetails>(
				passangerArrayHadoop.subList(start, middle));
		ArrayList<PassangerDetails> pdetailChunk3 = new ArrayList<PassangerDetails>(
				passangerArrayHadoop.subList(middle, end));
		
		HashMap<String, Integer> airportMap = new HashMap<String, Integer>();
		airportMap= hashAirpotID(airlineArrayHadoop);
		mapPassanger(pdetailChunk1);
	//	System.out.println(mapPassanger(pdetailChunk1));
		
		airportMap=airportCombine(mapPassanger(pdetailChunk1), mapPassanger(pdetailChunk2), mapPassanger(pdetailChunk3),airportMap);
	//	System.out.print(pdetailChunk1);
		//	System.out.println(pdetailChunk1.size()+pdetailChunk2.size()+pdetailChunk3.size());

		
	}
	
	private  HashMap<String, Integer> mapPassanger(ArrayList<PassangerDetails> p)
	{
		HashMap<String, Integer> mapPtoF = new HashMap<String, Integer>();
		for(PassangerDetails pinfo: p)
		{	String pString= pinfo.getDepartAirportCode();
			if(!mapPtoF.containsKey(pString))
			{
				mapPtoF.put(pString, 1);
			}else
			{
				mapPtoF.put(pString, mapPtoF.get(pString) + 1);
				
			}
		}
		
		return mapPtoF;
		
	}
	
	private HashMap<String, Integer> hashAirpotID(
			ArrayList<AirlinesDetails> pdetailChunks) {
		HashMap<String, Integer> newHash3 = new HashMap<String, Integer>();
		
		for(AirlinesDetails a: pdetailChunks)
		{
			String id= a.getAirportCode();
			if(!newHash3.containsKey(id))
			{
				newHash3.put(id, 0);
			}
		}
		
		return newHash3;

	
		
	}

	private HashMap<String, Integer> airportCombine(
			HashMap<String, Integer> reduceFunction,
			HashMap<String, Integer> reduceFunction2,
			HashMap<String, Integer> reduceFunction3, 
			HashMap<String, Integer> airportMap) {

		HashMap<String, Integer> blockReduce = new HashMap<String, Integer>();

		Set<String> keys2 = new HashSet<String>();
		keys2.addAll(airportMap.keySet());
		keys2.addAll(reduceFunction.keySet());
		keys2.addAll(reduceFunction3.keySet());
		keys2.addAll(reduceFunction2.keySet());
		
		//System.out.println(keys2.size());

		itrator = keys2.iterator();

		while (itrator.hasNext()) {
			String k = (String) itrator.next();

			if (!reduceFunction.containsKey(k)) {
				blockReduce.put(k, 0);
			} else{
				blockReduce.put(k, reduceFunction.get(k));
			}
			
			if (!reduceFunction2.containsKey(k)) {
				blockReduce.put(k, blockReduce.get(k));
			} else{
				blockReduce.put(k,	blockReduce.get(k) + reduceFunction2.get(k));}

			
			
			if (!reduceFunction3.containsKey(k)) {
				blockReduce.put(k, blockReduce.get(k));
			} else{
				blockReduce.put(k, blockReduce.get(k) + reduceFunction3.get(k));}
		}

		
//System.out.println(blockReduce);
		return blockReduce;
	}
	

}
