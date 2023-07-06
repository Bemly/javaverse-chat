package tcpGPT;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Server extends Thread {
	
	@Override
	public void run() {
		try {
			setName("服务端");
            // 创建ServerSocket对象，监听指定端口
            ServerSocket serverSocket = new ServerSocket(23333);
            System.out.println("[" + getName() + "]创建成功,执行子线程操作");
            
            while (true) {
            	synchronized (this) {
            		new Thread() {
    					@Override
    					public void run() {
    						try {
    							setName("服务端-"+getId());
    							System.out.println("[" + getName()+"]开始等待客户端回应");
    							
    							// 等待客户端连接
    			                Socket socket = serverSocket.accept();
    			                System.out.println("[" + getName()+"]连接成功,客户端:" + socket.getInetAddress().getHostAddress());
    							
    							// 处理客户端请求
    			                // ...
    			                InputStream in = socket.getInputStream();
    							// 从TCP连接中读取JSON字符串
    							ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    							
    							
								byte[] data = new byte[1024];
    					        int length;
    					        while ((length = in.read(data)) != -1) {
    					            buffer.write(data, 0, length);
    					        }
    							String json = buffer.toString();
    					        buffer.close();
    					        // 将JSON字符串反序列化为数据
    					        ObjectMapper mapper = new ObjectMapper();
    					        Package pack = mapper.readValue(json, Package.class);
    					        
    					        OutputStream out = socket.getOutputStream();
    					        // 1 发送 2请求接收 3请求成功 4请求失败
    					        if (pack.packageType == 1) POST(pack.msg, out);
    					        if (pack.packageType == 2) GET(pack.usr, out);
								out.flush();
    							
    							// 关闭连接
								in.close();
								out.close();
						        socket.close();
    							// 维持仅一个线程在激活状态
    							synchronized (Server.this) {
    			                    Server.this.notify();
    			                }
    						} catch (Exception e) {
    							e.printStackTrace();
    						}
    					}
    				}.start();
    				wait();
				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void GET(User usr, OutputStream out) throws Exception {
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
		PrintWriter pw = new PrintWriter(out, false);
		pw.println(msgList.size());
		for (Msg msg : msgList) {
			String json = new ObjectMapper().writeValueAsString(msg);
			pw.println(json);
		}
		pw.flush();
		pw.close();
	}
	
	public void POST(Msg msg, OutputStream out) throws Exception {
		System.out.println("INSERT=>"+msg.content);
		MySQL sql = new MySQL();
		sql.add(msg);
		sql.close();
		System.out.println("OK=>Client");
		// 创建JSON对象
        ObjectMapper mapper = new ObjectMapper();

        // 将数据序列化为JSON字符串
        // 1 发送 2请求接收 3请求成功 4请求失败
        String json = mapper.writeValueAsString(new Package(3));
        out.write(json.getBytes());
	}
	
    public static void main(String[] args) throws Exception {
    	new Server().start();
    }
}