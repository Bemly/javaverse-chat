package tcpGPT;

import java.sql.Timestamp;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Msg {
    public User usr;
    public Timestamp time;
    public String content = "hello";
    public String whoSee;
    
    public Msg() {}

    public Msg(User usr, Timestamp time, String content) {
    	this.usr = usr;
        this.time = time;
        this.content = content;
        whoSee = Arrays.toString(new int[]{1,2,3,4,5,6,7,8,9,10})
    			.replaceAll("\\bnull\\b,?|,\\bnull\\b", "")
    			.replaceAll("[\\[\\]\\s]", "");;
    }
}