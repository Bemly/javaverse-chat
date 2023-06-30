package bemlyGPT;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Server extends WebSocketServer {
	
	// 发Msg给客户端
	public String GET(User usr) throws Exception {
		MySQL sql = new MySQL();
		// 查询全部数据
		ResultSet rs = sql.query();
		List<Msg> msgList = new ArrayList<>();
		while (rs.next()) {
			int count = rs.getInt("count");
            int uid = rs.getInt("UID");
            Timestamp time = rs.getTimestamp("time");
            String content = rs.getString("content");
            String whoSeeDB = rs.getString("UIDs");
            if (whoSeeDB != null && whoSeeDB != "") {
            	String[] whoSeeSA = whoSeeDB.split(",");
            	String[] newWhoSeeSA = new String[whoSeeSA.length];
            	// 遍历数组里面是否有自己
                for (int i = 0, n = 0; i < whoSeeSA.length; i++) {
                	// 有自己则发送消息 同时删除数组列表
					if (whoSeeSA[i] != null && !whoSeeSA[i].equals("null")) 
						if (usr.uid == Integer.parseInt(whoSeeSA[i])) {
						MySQL subSql = new MySQL();
						// 加入消息发送列表
		                msgList.add(new Msg(new User(uid, subSql.getName(uid)), time, content));
		                subSql.close();
		             // 只保留不是自己的值
					} else newWhoSeeSA[n++] = whoSeeSA[i];
				}
                // 查看自己是否在数组中
                if (newWhoSeeSA[newWhoSeeSA.length - 1] == null) {
                	// 不在就更新表中的列表
                	MySQL subSql = new MySQL();
                	subSql.update(count, Arrays.toString(newWhoSeeSA)
                			.replaceAll("\\bnull\\b,?|,\\bnull\\b", "")
                			.replaceAll("[\\[\\]\\s]", ""));
                	subSql.close();
                }
            }
        }
		// 发送给客户端 msgList msgJson
		StringJoiner json = new StringJoiner("\n");
		for (Msg msg : msgList) json.add(new ObjectMapper().writeValueAsString(msg));
		return json.toString();
	}
	
	// 接收客户端的消息
	public String POST(Msg msg) throws Exception {
		System.out.println("INSERT=>"+msg.content);
		new MySQL().add(msg).close();
        // 将数据序列化为JSON字符串
        // 1 发送 2请求接收 3请求成功 4请求失败
        return new ObjectMapper().writeValueAsString(new Package(3));
	}
	
    public static void main(String[] args) throws Exception {
        Server s = new Server(23333);
        s.start();
        
    }
    
    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onStart() {
        System.out.println("服务器启动完毕");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
    	System.out.printf("[%s]客户端连接 地址=%s%n",Thread.currentThread().getName(), conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.printf("[%s]客户端掉线 地址=%s%n",Thread.currentThread().getName(), conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
    	try {
    		System.out.printf("[%s]收到包=%s%n",Thread.currentThread().getName(), message);
    		// 反序列化JSON为对象 拆包
            Package pack = new ObjectMapper().readValue(message, Package.class);
            // 1 发送 2请求接收 3请求成功 4请求失败
            if (pack.packageType == 1) conn.send(POST(pack.msg));
            if (pack.packageType == 2) conn.send(GET(pack.usr));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }
}