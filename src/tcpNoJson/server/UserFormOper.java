package tcpNoJson.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import tcpNoJson.User;
class UserFormOper extends AccessMySQL {
	
	public UserFormOper() throws Exception {
		connect();
	}
	
	// 查询房间最高数字+1
	public int getMax(String rowName) throws SQLException {
		String sql = "SELECT MAX("+ rowName +") AS max_value FROM user";
        ResultSet rs = stmt.executeQuery(sql);
        int max = 0;
        while (rs.next()) max = rs.getInt("max_value");
        return max;
	}

    // 添加用户
    public void add(User user) throws Exception {
    	// TODO []=>LIST
    	if (user.getFriendIDs() == null || user.getJoinRoomIDs() == null) {
    		String sql = "INSERT INTO User(neckName, password) VALUES (? , ?)";
    		pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getNeckName());
            pstmt.setString(2, user.getPassword());
    	} else {
    		String sql = "INSERT INTO User(neckName, password, friendIDs, joinRoomIDs) VALUES (?, ?, ?, ?)";
    		pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getNeckName());
            pstmt.setString(2, user.getPassword());
    		pstmt.setString(3, int2DtoString(user.getFriendIDs()));
            pstmt.setString(4, int2DtoString(user.getJoinRoomIDs()));
		}
        pstmt.executeUpdate();
    }

    // 删除用户
    public void deleteUser(String UID) throws Exception {
        String sql = "DELETE FROM User WHERE UID=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, UID);
        pstmt.executeUpdate();
    }

    // 修改用户信息
    public void updateUser(User user) throws Exception {
        String sql = "UPDATE User SET password=?, friendIDs=?, joinRoomIDs=? WHERE UID=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(4, user.getUID());
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, int2DtoString(user.getFriendIDs()));
        pstmt.setString(3, int2DtoString(user.getJoinRoomIDs()));
        pstmt.executeUpdate();
    }

    // 查询用户信息
    public User search(int UID) throws Exception {
        User user = null;
        String sql = "SELECT * FROM User WHERE UID=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, UID);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            user = new User();
            user.setUID(rs.getInt("UID"));
            user.setNeckName(rs.getString("neckName"));
            user.setPassword(rs.getString("password"));
            user.setFriendIDs(stringtoInt2D(rs.getString("friendIDs")));
            user.setJoinRoomIDs(stringtoInt2D(rs.getString("joinRoomIDs")));
        }
        return user;
    }
    
    public User search(String name) throws Exception {
        User user = null;
        String sql = "SELECT * FROM User WHERE neckName=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            user = new User();
            int uid = rs.getInt("UID");
            user.setUID(uid);
            user.setNeckName(rs.getString("neckName"));
            user.setPassword(rs.getString("password"));
            user.setFriendIDs(stringtoInt2D(rs.getString("friendIDs")));
            user.setJoinRoomIDs(stringtoInt2D(rs.getString("joinRoomIDs")));
        }
        return user;
    }
}