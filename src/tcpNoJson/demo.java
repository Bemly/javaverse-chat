package tcpNoJson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Base64;

public class demo {
	
	public static void main(String[] args) throws Exception {
		String[] names = {"王麻子", "李四", "张三", "张博"};

        for (String name : names) {
            String encoded = Base64.getEncoder().encodeToString(name.getBytes());
            System.out.println(name + " 的Base64编码为：" + encoded);
        }
//		demo d = new demo();
//		d.connect();
//		d.s();
//		d.close();
        String t = "再测试下 啊哈\n\n换行";
        System.out.println(t);
        System.out.println(t.replaceAll("\n", "<br>").replaceAll("<br>", "\n"));
	}
	
	public void s() throws Exception {
		String sql = "UPDATE user SET neckName=? WHERE UID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置参数
        pstmt.setString(1, "你好");
        pstmt.setInt(2, 1); // 假设要更新的UID为1

        int rows = pstmt.executeUpdate();
        System.out.println(rows + " 行记录被更新。");
	}
	
	public void f() throws Exception {
		String sql = "SELECT friendIDs FROM user WHERE UID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置参数
        pstmt.setInt(1, 3);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String friendIDs = rs.getString("friendIDs");
            System.out.println("王麻子的friendIDs为：" + friendIDs);
        }
	}
	
	public void e() throws Exception {
            String sql = "CREATE TABLE `" + 1 + "` (count INT NOT NULL AUTO_INCREMENT, UID INT NOT NULL, time TIMESTAMP NOT NULL, content TEXT NOT NULL, roomID INT NOT NULL, UIDs TEXT NOT NULL, PRIMARY KEY (count)) ENGINE=InnoDB DEFAULT CHARSET=gbk COLLATE=gbk_chinese_ci;";
            stmt.executeUpdate(sql);
            System.out.println("表1创建成功！");
	}
	
	public void a() throws SQLException {
		String sql = "SELECT MAX(roomID) AS max_roomID FROM room";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int maxRoomID = rs.getInt("max_roomID");
            System.out.println("room表现在自动编号的roomID最高是：" + maxRoomID);
        }
	}
	
	
	protected Connection conn=null;
	protected PreparedStatement pstmt = null;
	protected Statement stmt = null;
	protected ResultSet rs = null;
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
