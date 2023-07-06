package tcpNoJson;

public class User {
    private int UID;
    private String neckName;
    private String password;
    private int[] friendIDs;
    private int[] joinRoomIDs;
    
    public User(){}
    
    public User(int UID, String neckName, String password) {
		this.UID = UID;
		this.neckName = neckName;
		this.password = password;
	}

    public String getNeckName() {
		return neckName;
	}

	public void setNeckName(String neckName) {
		this.neckName = neckName;
	}

	public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getFriendIDs() {
        return friendIDs;
    }

    public void setFriendIDs(int[] friendIDs) {
        this.friendIDs = friendIDs;
    }

    public int[] getJoinRoomIDs() {
        return joinRoomIDs;
    }

    public void setJoinRoomIDs(int[] joinRoomIDs) {
        this.joinRoomIDs = joinRoomIDs;
    }
}