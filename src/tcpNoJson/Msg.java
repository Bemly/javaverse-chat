package tcpNoJson;

public class Msg {
    private int count;
    private int UID;
    private double time;
    private String content;
    private int roomID;
    private int[] UIDs;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int[] getUIDs() {
        return UIDs;
    }

    public void setUIDs(int[] UIDs) {
        this.UIDs = UIDs;
    }
}