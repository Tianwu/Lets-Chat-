import java.io.DataInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class ChatServer {
	private ServerSocket ss = null;
	private Socket s = null;
	
	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(9600);	
System.out.println("A Server openning...");
//JOptionPane.showMessageDialog(null, "服务器打开", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (BindException be) {
System.out.println("A Server opened, please not open it.");
//JOptionPane.showMessageDialog(null, "请不要重复打开服务器", "警告", JOptionPane.WARNING_MESSAGE);
System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
System.exit(0);
		}
		
		try {
			while(true) {
				s = ss.accept();
				Client c = new Client(s);
System.out.println("A Client Connected...");
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class Client implements Runnable {
		private Socket s = null;
		private DataInputStream dis = null;
		private boolean coned = false;
		
		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				coned = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while(coned) {	
				try {
					String str = dis.readUTF();
					System.out.println(str);
				} catch(SocketException se) {
System.out.println("A Client quit...");
System.exit(0);		
				}catch(IOException e) {
					e.printStackTrace();
				}
				
			}
		}

	}
}
