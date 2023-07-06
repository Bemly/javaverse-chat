package tcpNoJson.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;


// DAO => JDBC
public abstract class AccessMySQL {
	
	protected Connection conn;
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	// 用prepare准备状态连接数据库
	public void connect() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ChatPZH?useUnicode=true&characterEncoding=GBK&user=root&password=5477"); 
		stmt = conn.createStatement();
	}
	// 关闭数据库连接
    public void close() throws Exception {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
    
    // 在数据库里面存取一维数组
    public static String int2DtoString(int[] arr) {
    	return String.join(",", Arrays.stream(arr).mapToObj(String::valueOf).toArray(String[]::new));
	}
    
    public static int[] stringtoInt2D(String str) {
    	return Arrays.stream(str.split(",")).mapToInt(Integer::parseInt).toArray();
    }
}



