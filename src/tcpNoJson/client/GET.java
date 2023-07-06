package tcpNoJson.client;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GET extends TCPSender {
	
	private ChatWindow frm;
	
	public GET(int select, ChatWindow frm) {
		this.frm = frm;
		this.select = select;
	}

	@Override
	protected void sender(PrintWriter out, BufferedReader in) throws Exception {
//		out.println("LOGIN\n"+sendContent);
//		out.flush();
//		
//		resultCode = Integer.parseInt(in.readLine());
//		String line; resultContent = "";
//		while ((line = in.readLine())!=null) resultContent += line;
//		if (resultCode == 404) JOptionPane.showMessageDialog(null, resultContent);
//		if (resultCode == 200) {
//			System.out.println("[" + getName() + "]获得Token" + resultContent);
//			RunClient.login2Chat(resultContent);
//		}
		
		// Token UID GET_TYPE
		out.println("GET");
		out.println(frm.usr.getPassword());
		out.println(frm.usr.getUID());
		out.println(select);
		// 1 : User => roomIDs 查询用户对应房间
		switch (select) {
		case 1:
			out.println(frm);
			break;
		case 2:
			
			break;
		default:
			break;
		}
		out.flush();
	}

}
