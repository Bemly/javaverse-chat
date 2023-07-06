package tcpNoJson.server;

import tcpNoJson.Msg;


public class MsgOper extends AccessMySQL {


	// 添加消息
	public void add(Msg msg) throws Exception {
	    String sql = "INSERT INTO Msg(count, UID, time, content, roomID, UIDs) VALUES (?, ?, ?, ?, ?, ?)";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, msg.getCount());
	    pstmt.setInt(2, msg.getUID());
	    pstmt.setDouble(3, msg.getTime());
	    pstmt.setString(4, msg.getContent());
	    pstmt.setInt(5, msg.getRoomID());
	    pstmt.setString(6, int2DtoString(msg.getUIDs()));
	    pstmt.executeUpdate();
	}

	// 删除消息
	public void deleteMsg(int count) throws Exception {
	    String sql = "DELETE FROM Msg WHERE count=?";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, count);
	    pstmt.executeUpdate();
	}

	// 修改消息内容
	public void updateMsgContent(int count, String content) throws Exception {
	    String sql = "UPDATE Msg SET content=? WHERE count=?";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, content);
	    pstmt.setInt(2, count);
	    pstmt.executeUpdate();
	}

	// 查询消息
	public Msg getMsg(int count) throws Exception {
	    Msg msg = null;
	    String sql = "SELECT * FROM Msg WHERE count=?";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, count);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
	        msg = new Msg();
	        msg.setCount(rs.getInt("count"));
	        msg.setUID(rs.getInt("UID"));
	        msg.setTime(rs.getDouble("time"));
	        msg.setContent(rs.getString("content"));
	        msg.setRoomID(rs.getInt("roomID"));
	        msg.setUIDs(stringtoInt2D(rs.getString("UIDs")));
	    }
	    return msg;
	}
}