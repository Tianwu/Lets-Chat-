import java.io.DataInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ChatServer {
	private ServerSocket ss = null;
	private Socket s = null;
	private DataInputStream dis = null;
	
	public static void main(String[] args) {
		new ChatServer().connect();

	}

	public void connect() {
		try {//BindException
			ss = new ServerSocket(9600);
			
System.out.println("A Server openning...");
			JOptionPane.showMessageDialog(null, "服务器已打开", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (BindException be) {
			JOptionPane.showMessageDialog(null, "请不要重复打开服务器", "警告", JOptionPane.WARNING_MESSAGE);
System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			s = ss.accept();
System.out.println("A Client Connected...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			dis = new DataInputStream(s.getInputStream());
			String str = dis.readUTF();
System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
