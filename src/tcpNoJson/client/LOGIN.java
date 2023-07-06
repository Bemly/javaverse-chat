package tcpNoJson.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Base64;

import javax.swing.JOptionPane;

import tcpNoJson.User;

public class LOGIN extends TCPSender {
	
	private String neckName, pwd;
	
	public LOGIN(String neckName, String pwd, int select) {
		this.pwd = pwd;
		this.neckName = neckName;
		this.select = select;
	}

	@Override
	protected void sender(PrintWriter out, BufferedReader in) throws Exception {
		// neckName pwd sign in1 up2
		out.printf("LOGIN\n%s\n%s\n%s\n", neckName, pwd, select);
		out.flush();
		
		// 200:success UID Token 404:error ErrorContent
		switch (Integer.parseInt(in.readLine())) {
		case 404:
			String line, err = "";
			while ((line = in.readLine())!=null) err += line;
			JOptionPane.showMessageDialog(null, err);
			break;
		case 200:
			int UID = Integer.parseInt(in.readLine());
			String token = in.readLine();
			System.out.println("[" + getName() + "]token:" + token + " UID:" + UID);
			// 改变frame，进入主页面
			RunClient.login2Chat(new User(UID, neckName, token));
			break;
		};
	}

}
