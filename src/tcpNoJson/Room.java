package tcpNoJson;


public class Room {
    private int roomID;
    private int Type;
    private int[] UIDs;
    private Msg[] msgArr;

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int[] getUIDs() {
        return UIDs;
    }

    public void setUIDs(int[] UIDs) {
        this.UIDs = UIDs;
    }

    public Msg[] getMsgArr() {
        return msgArr;
    }

    public void setMsgArr(Msg[] msgArr) {
        this.msgArr = msgArr;
    }
}