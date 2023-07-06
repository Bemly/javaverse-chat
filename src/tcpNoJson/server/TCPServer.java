package tcpNoJson.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

import tcpNoJson.Msg;
import tcpNoJson.User;

public class TCPServer extends Thread {
    
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
    			                InputStream is = socket.getInputStream();
    							BufferedReader in = new BufferedReader(new InputStreamReader(is));
    							PrintWriter out = new PrintWriter(socket.getOutputStream(), false);
    							System.err.println(is.available());
    							String connectType = in.readLine();
    							System.out.println("[" + getName()+"]连接类型:"+connectType);
    							try {
    								if (connectType != null && connectType.equals("GET")) GET(in, out);
        							if (connectType != null && connectType.equals("POST")) POST(in, out);
        							if (connectType != null && connectType.equals("LOGIN")) LOGIN(in, out);
								} catch (Exception e) {
									// 维持下一次线程正常执行
									e.printStackTrace();
								} finally {
									out.flush();	// =>
								}
    							
    							// 关闭连接
    							socket.close();
    							// 维持仅一个线程在激活状态
    							synchronized (TCPServer.this) {
    			                    TCPServer.this.notify();
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
	
	public void GET(BufferedReader in, PrintWriter out) throws Exception {
		// GET_TYPE: 查询角色
		// Token UID GET_TYPE
		
		// 验证阶段
		String token = in.readLine();
		String UID = in.readLine();
		String getType = in.readLine();
		if (token == null || UID == null || getType == null) Sender(out, "["+getName()+"]GET格式错误");
		int uid = Integer.parseInt(UID);
		if (Token.check(token, uid, new Timestamp(new Date().getTime()))) Sender(out, "["+getName()+"]Token过期");
		
		UserFormOper ufo = new UserFormOper();
		// 1 : User => roomIDs 查询用户对应房间
		switch (Integer.parseInt(getType)) {
		case 1:
//			ufo.
			break;
		case 2:
			
			break;
		default:
			break;
		}
	}
	
	public void POST(BufferedReader in, PrintWriter out) throws Exception {
		// POST_TYPE: 创建房间 发送消息
		// Token UID POST_TYPE
		
		// 验证阶段
		String token = in.readLine();
		String UID = in.readLine();
		String postType = in.readLine();
		if (token == null || UID == null || postType == null) Sender(out, "["+getName()+"]POST格式错误");
		int uid = Integer.parseInt(UID);
		if (Token.check(token, uid, new Timestamp(new Date().getTime()))) Sender(out, "["+getName()+"]Token过期");
		
		switch (Integer.parseInt(postType)) {
		case 1:
			// 1:创建房间
			// 初始化房间 不返回值 客户端知道
			RoomFormOper rfo = new RoomFormOper();
			if(rfo.create()==0) throw new Exception("["+getName()+"]创建房间-初始化房间失败");
			else Sender(out, uid);
			break;
		case 2:
			// 2:发送消息
			MsgOper mo = new MsgOper();
			
			mo.add(new Msg());
			break;
		default:
			break;
		}
	}
	
	public void LOGIN(BufferedReader in, PrintWriter out) throws Exception {
		// neckName pwd sign
		
		// 验证阶段
		String neckName = in.readLine();
		String pwd = in.readLine();
		String sign = in.readLine();
		if (neckName == null || pwd == null || sign == null) Sender(out, "["+getName()+"]LOGIN格式错误");
		int signType = Integer.parseInt(sign);
		
		UserFormOper ufo = new UserFormOper();
		User usr = ufo.search(neckName);
		ufo.close();
		System.out.println("[" + getName()+"]LOGIN连接类型:"+signType);
		switch (signType) {
		case 1:
			// 登录
			if (usr == null) Sender(out, "["+getName()+"]账户不存在,可以尝试注册");
			else if (usr.getPassword().equals(pwd)) Sender(out, usr.getUID());
			else Sender(out, "["+getName()+"]登录密码错误");
			break;
		case 2:
			// 注册
			if (usr != null) Sender(out, "["+getName()+"]账户已存在,可以尝试登录");
			else {
				ufo.connect();
				ufo.add(new User(ufo.getMax("UID")+1, neckName, pwd));
			}
			Sender(out, usr.getUID());
			break;
		default:
			// 找回
			// 修改密码
			break;
		}
	}
	
	public void Sender(PrintWriter out, String e) throws Exception {
		// 处理失败回执
		out.println("404");
		out.println(e);
		out.flush();
		throw new Exception(e);
	}
	
	public void Sender(PrintWriter out, int uid) throws Exception {
		// 若成功 回执
		// 200:success UID Token 404:error ErrorContent
		out.println("200");
		out.println(uid);
		out.println(Token.update(uid, new Timestamp(new Date().getTime())));
		out.flush();
	}
}
