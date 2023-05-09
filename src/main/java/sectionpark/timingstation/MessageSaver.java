package sectionpark.timingstation;

import java.util.ArrayList;
import java.util.List;

public class MessageSaver {
    private List<String> msg = new ArrayList<>();

    public List<String> getMsg() {
        return msg;
    }

    public void setD(List<String> msg) {
        this.msg = msg;
    }
    public void addMsg(String s){
        msg.add(s);
    }
}
