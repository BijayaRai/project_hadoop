package hadoopApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainFunction {
	private static ArrayList<AirlinesDetails> airlineArray = new ArrayList<AirlinesDetails>();
	private static ArrayList<PassangerDetails> passangerArray = new ArrayList<PassangerDetails>();

	
	
	
	/**
	 * main point of entry for the application 
	 * call the other function from different class
	 * @param args
	 */
	public static void main(String[] args) {

		HadoopClass hclass = new HadoopClass();
		String fileURI = "airlines.csv";
		try {
			fileReader(fileURI, "airport");

			fileURI = "passangers.csv";
			fileReader(fileURI, "passanger");

		} catch (IOException e) {

			e.printStackTrace();
		}

		saveToFile();
		removeEmptyData();
		timeCalculator();
		spellingErrorHandler();

		FlightsFromaAirport ffa = new FlightsFromaAirport();

		ffa.passangerArrayHadoop = passangerArray;
		ffa.airlineArrayHadoop = airlineArray;
		ffa.airportFlights();

		hclass.airlineArrayHadoop = airlineArray;
		hclass.passangerArrayHadoop = passangerArray;
		hclass.splitter();


	}

	/**
	 * validation 2:
	 * 
	 * analyses the text and replaces any anomalies with the data that best matches the anomaly
	 */
	private static void spellingErrorHandler() {
		String[] airportCodeList = new String[airlineArray.size()];
		for(int i=0; airlineArray.size()>i;i++)
		{	
			airportCodeList[i]= airlineArray.get(i).getAirportCode();
		}
		for (PassangerDetails pd: passangerArray) {

			String a= pd.getDepartAirportCode();
			if(a.matches("[^[A-Z][a-z]]"+"[^[A-Z][a-z]]"+"[^[A-Z][a-z]]")==false)
				
				for(int i=0;a.length()>i;i++)
				{
					String b= a.substring(i,i+1);
					if(b.matches("[[A-Z]]")==false)
					{	
						int errorCharIndex=a.indexOf(b);;
						String firstHalf= a.substring(0,errorCharIndex);
						String secondHalf=a.substring(errorCharIndex+1, a.length());
						//	System.out.println("----->"+a);
						//	System.out.println(firstHalf+" "+ secondHalf);
						for(String airCodeCompare: airportCodeList)
						{
							if(airCodeCompare.contains(firstHalf)&&airCodeCompare.contains(secondHalf))
							{
								pd.setDepartAirportCode(airCodeCompare);
								//		System.out.println(airCodeCompare);
							}
						
						}
					}
				}
				//System.out.println("Errr"+a+"----->"+a.substring(0,1));
			//	System.out.print();
					a.substring(0);
				
			}
		}
	
	/**
	 * change the linux epoch time to standard format
	 */
	private static void timeCalculator() {
		for (int i = 0; passangerArray.size() > i; i++) {
			String inditem = passangerArray.get(i).getLocalArrivalTime();

			long l = Long.parseLong(inditem);
			Date date = new Date(l * 1000L);

			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss ");
			format.setTimeZone(TimeZone.getTimeZone("london/Europe"));
			String formattedDate = format.format(date);

			passangerArray.get(i).setLocalArrivalTime(formattedDate);
		}

	}

	/**
	 * validation 2:
	 * empty row remover
	 */
	private static void removeEmptyData() {
		//remove empty cell from passenger Array and airlineArray
		
		for (int i = 0; passangerArray.size() > i; i++) {
		// System.out.println(passangerArray.size());
			String inditem = passangerArray.get(i).getPassangerId();

			if (inditem.matches("") == true) {
				passangerArray.remove(i);
			}
		}
		for (int i = 0; airlineArray.size() > i; i++) {
		// System.out.println(airlineArray.size());
			String airlineItem = airlineArray.get(i).getAirportName();

			if (airlineItem.matches("") == true) {
				airlineArray.remove(i);
			}
		}

	}

	/**
	 * saves the output to a txt file named "passangerTest.txt"
	 */
	private static void saveToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"passangerTest.txt"));
			for (PassangerDetails pd : passangerArray) {
				// System.out.println(pd.passInfo());
				writer.write(pd.passInfo());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @param location ->location of the file
	 * @param valueChanger ->to read different type of file, used to breach the code
	 * @throws IOException ->incase the file is non existant
	 */
	private static void fileReader(String location, String valueChanger)
			throws IOException {
		File file = new File(location);

		if (file.exists()) {

			String line = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));

			while ((line = reader.readLine()) != null) {
				String[] valueHolder = line.split(",");

				//read and assings the value from file to the object in the arraylist
				if (line.trim().length() > 0) {
					if (valueChanger == "airport") {
						AirlinesDetails airlinesDetailsList = new AirlinesDetails(
								valueHolder[0], valueHolder[1],
								Float.parseFloat(valueHolder[2]),
								Float.parseFloat(valueHolder[3]));
						airlineArray.add(airlinesDetailsList);
					} else if (valueChanger == "passanger") {
						PassangerDetails pdet = new PassangerDetails(
								valueHolder[0], valueHolder[1], valueHolder[2],
								valueHolder[3], valueHolder[4],
								Float.parseFloat(valueHolder[5]));
						passangerArray.add(pdet);

					}
				}

			}
			reader.close();

		}

	}

}