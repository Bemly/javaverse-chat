package tcpNoJson.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JOptionPane;

public abstract class TCPSender extends Thread {
	
	protected int select;
	
	
    @Override
    public void run() {
    	try {
            // 创建Socket对象，连接服务器
            Socket socket = new Socket("localhost", 23333);

            // 获取输出流，发送数据
            // GET/POST/LOGIN
			PrintWriter out = new PrintWriter(socket.getOutputStream(), false);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sender(out, in);

            // 关闭连接
            socket.close();
        } catch (ConnectException e) {
        	JOptionPane.showMessageDialog(null, "服务器连接失败,可以等待一段时间重试:"+e);
            e.printStackTrace();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, e);
        	e.printStackTrace();
		}
    }

	protected abstract void sender(PrintWriter out, BufferedReader in) throws Exception;
}