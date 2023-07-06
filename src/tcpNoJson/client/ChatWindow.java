package tcpNoJson.client;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;

import javax.swing.JTabbedPane;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JEditorPane;
import javax.swing.JMenu;



import tcpNoJson.User;

import java.awt.Panel;
import java.util.Base64;

public class ChatWindow extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private JMenu setting;
	private JMenuItem logOut;
	private Panel room1;
	private Panel room2;
	private Panel room3;
	private Panel room4;
	
	public User usr;


	/**
	 * Create the frame.
	 */
	public ChatWindow(User usr) {
		this.usr = usr;
		setVisible(true);
		setTitle("\u804A\u5929\u8F6F\u4EF6 \u57FA\u7840\u6846\u67B6");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(300, 400));
		setMinimumSize(new Dimension(300, 400));
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		setting = new JMenu("\u8BBE\u7F6E");
		menuBar.add(setting);
		
		logOut = new JMenuItem("\u9000\u51FA\u767B\u5F55");
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 改变frame 退出主页面 进入账户登录页面
				RunClient.chat2login();
			}
		});
		setting.add(logOut);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		contentPane.add(tabbedPane);
		
		initRoomPane();
		
		pack();
	}
	
	private void initRoomPane() {
		new GET(1, this).start();
	}
}
