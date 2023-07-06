package tcpGPT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;

public class MySQL {
	protected Connection conn=null;
	protected PreparedStatement pstmt = null;
	protected Statement stmt = null;
	protected ResultSet rs = null;
	
	public MySQL() throws Exception {
		connect();
	}
	
	public void connect() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatdb?useUnicode=true&characterEncoding=GBK&user=root&password=5477"); 
		stmt = conn.createStatement();
	}
	
	// 关闭数据库连接
    public void close() throws Exception {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
    
    public void add(Msg msg) throws Exception {
    	PreparedStatement pstmt = conn.prepareStatement("INSERT INTO msg (UID, time, content, UIDs) VALUES (?, ?, ?, ?)");
        pstmt.setInt(1, msg.usr.uid);
        pstmt.setTimestamp(2, msg.time);
        pstmt.setString(3, msg.content);
        pstmt.setString(4, msg.whoSee);
        pstmt.executeUpdate();
    }
    
    public ResultSet query() throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM msg");
        return pstmt.executeQuery();
    }
    
    // 不查询自己
    public ResultSet query(int uid) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM msg where UID <> "+uid);
        return pstmt.executeQuery();
    }
    
    // 查询用户名
    public String getName(int uid) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user where UID = "+uid);
        rs = pstmt.executeQuery();
        rs.next();
        return rs.getString("neckName");
    }
    
    // 更新消息查看人数
    public void update(int count, String uids) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("UPDATE msg SET UIDs = ? where count = ?");
        pstmt.setString(1, uids);
        pstmt.setInt(2, count);
        pstmt.executeUpdate();
    }
}
