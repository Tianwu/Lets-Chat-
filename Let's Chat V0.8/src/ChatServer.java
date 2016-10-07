import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ChatServer {
	private ServerSocket ss = null;
	private Socket s = null;
	List<Client> clients = new ArrayList<Client>();
	
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
				clients.add(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class Client implements Runnable {
		private Socket s = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean coned = false;
		
		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
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
					for(int i=0 ; i<clients.size() ; ++i) {
						Client c = clients.get(i);
						if(c.equals(this)==false) 
							c.send(str);
					}
				} catch(SocketException se) {
System.out.println("A Client quit...");
System.exit(0);		
				}catch(IOException e) {
					e.printStackTrace();
				}
				
			}
		}

		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
