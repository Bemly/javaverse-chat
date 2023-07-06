package tcpNoJson.client;

import javax.swing.JFrame;

import tcpNoJson.User;

public class RunClient {
	private static JFrame frame;
	
	public static void main(String[] args) {
		// TODO token¾ÃµÇÂ¼
		frame = new ChatLogin();
	}
	
	public static void login2Chat(User usr) {
		frame.dispose();
		frame = new ChatWindow(usr);
	}
	
	public static void chat2login() {
		frame.dispose();
		frame = new ChatLogin();
	}
}
