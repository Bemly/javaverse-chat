package tcpNoJson.client;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Base64;

public class ChatLogin extends JFrame {

	private JPanel contentPane;
	private JPanel usrPane;
	private JPanel pwdPane;
	private JPanel submitPane;
	private JLabel pwdLabel;
	private JLabel usrLabel;
	private JTextField userName;
	private Component verticalGlue;
	private Component verticalGlue1;
	private Component verticalGlue2;
	private JButton btnSignIn;
	private JButton btnSignUp;
	private JPasswordField pwd;
	

	/**
	 * Create the frame.
	 */
	public ChatLogin() {
		setVisible(true);
		setTitle("\u767B\u5F55\u804A\u5929");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 374, 296);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		verticalGlue = Box.createVerticalGlue();
		contentPane.add(verticalGlue);
		
		usrPane = new JPanel();
		contentPane.add(usrPane);
		
		usrLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		usrPane.add(usrLabel);
		
		userName = new JTextField();
		userName.setColumns(18);
		usrPane.add(userName);
		
		verticalGlue1 = Box.createVerticalGlue();
		contentPane.add(verticalGlue1);
		
		pwdPane = new JPanel();
		contentPane.add(pwdPane);
		
		pwdLabel = new JLabel("\u5BC6  \u7801\uFF1A");
		pwdPane.add(pwdLabel);
		
		pwd = new JPasswordField();
		pwd.setColumns(18);
		pwdPane.add(pwd);
		
		verticalGlue2 = Box.createVerticalGlue();
		contentPane.add(verticalGlue2);
		
		submitPane = new JPanel();
		contentPane.add(submitPane);
		
		btnSignIn = new JButton("\u767B\u5F55");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// neckName pwd sign in1 登录
				String neckName = userName.getText(), password = new String(pwd.getPassword());
				if (neckName == null || password.equals("")) JOptionPane.showMessageDialog(null, "请输入合法用户名或密码");
				else new LOGIN(neckName, Base64.getEncoder().encodeToString(password.getBytes()), 1).start();
			}
		});
		submitPane.add(btnSignIn);
		
		btnSignUp = new JButton("\u6CE8\u518C");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// neckName pwd sign up2 注册
				String neckName = userName.getText(), password = new String(pwd.getPassword());
				if (neckName == null || password.equals("")) JOptionPane.showMessageDialog(null, "请输入合法用户名或密码");
				else new LOGIN(neckName, Base64.getEncoder().encodeToString(password.getBytes()), 2).start();
			}
		});
		submitPane.add(btnSignUp);
		
		pack();
	}
}
