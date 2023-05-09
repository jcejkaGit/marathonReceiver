package sectionpark.timingstation;

import java.util.ArrayList;

public class CompetitionData {
    private String unitTiming;
    private ArrayList<Party> party;
    
    public CompetitionData() {
    	this.unitTiming = "hh:mm:ss";
    	this.party = new ArrayList<Party>();
    	
    }
    public void setUnitTiming(String unitTiming){
        this.unitTiming = unitTiming;
    }
    public String getUnitTiming(){
        return this.unitTiming;
    }
    public void setParty(ArrayList<Party> party){
        this.party =  party;
    }
    
    public ArrayList<Party> getParty(){
        return this.party;
    }
    @Override
    public String toString(){
        String ausgabe ="";

        return null;
    }
}
