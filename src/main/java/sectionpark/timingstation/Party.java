package sectionpark.timingstation;

import java.text.SimpleDateFormat;

public class Party {
    private int partyID;
    private String timing;

    public void setPartyID(int partyID){
        this.partyID = partyID;
    }
    public int getPartyID(){
        return this.partyID;
    }
    
    public void setTiming(String timing){
        this.timing = timing;
    }
    public String getTiming(){
        return this.timing;
    }
}
