package sectionpark.timingstation;

import sectionpark.model.TimingstationData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimingstationSimulation {
	
	private double getRandomDouble( int inMinimum, int inMaximum ) {

		double number = ( Math.random() * ( (inMaximum-inMinimum) + 1 )) + inMinimum; 
		double rounded = Math.round(number * 100.0) / 100.0; 
		return rounded;
		
	}

	private int getRandomInt( int inMinimum, int inMaximum ) {

		double number = ( Math.random() * ( (inMaximum-inMinimum) + 1 )) + inMinimum; 
		Long rounded = Math.round(number); 
		return rounded.intValue();

	}
	private TimingstationData genTimingStation(String inTimingstationID, int partyAnzahl){
		TimingstationData data = new TimingstationData();
		data.setTimingstationID( inTimingstationID );
		data.setDistance( 1 );
		data.setAltitude( 200 );
		CompetitionData compData = new CompetitionData();
		data.setCompetitionData(compData);

		ArrayList<Party> compParty = new ArrayList<Party>();
		for(int i =0;i<partyAnzahl ;i++){
			Party a1 = new Party();
			a1.setTiming(new SimpleDateFormat("hh:mm:ss").format(new Date()));
			a1.setPartyID(getRandomInt(1000,2000));
			compParty.add(a1);
		}

		compData.setParty(compParty);
		data.setCompetitionData(compData);
		return data;
	}
	
	public TimingstationData getData( String inTimingstationID ) {
		
		TimingstationData data = genTimingStation(inTimingstationID, 3);
		
		return data;
		
	}

}
