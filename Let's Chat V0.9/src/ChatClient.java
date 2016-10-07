import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;

public class ChatClient extends Frame{
	private TextField tf = new TextField();
	private TextArea  ta = new TextArea();
	private Socket s = null;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	//这个线程的作用是不断读取或者说接收数据。主线程main不断发送数据
	private Thread rt = new Thread(new RecvThread());
	private boolean conned  = false;
	
	public ChatClient() {
		this.setTitle("Let's Chat");
		this.setBounds(100, 100, 400, 300);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//这样写，可以在关闭窗口时，做资源回收的当作
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}			
		});
		this.setResizable(false);
		this.add(ta, BorderLayout.NORTH);
		this.add(tf, BorderLayout.SOUTH);
		ta.setEditable(false);
//Lambda 的写法，看起来简洁，却有些不直观
		tf.addActionListener((e)->{
			String str = tf.getText().trim();
			ta.setText(ta.getText()+getTime()+" : "+str+"\n");
			tf.setText("");
			try {
				if(str.equals("")!=true) {
					dos.writeUTF(str);
					dos.flush();
				}
			} catch (SocketException se) {
				System.out.println("A Server quit...");
				System.exit(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		this.pack();
	}

	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}

	private void launchFrame() {
		//连接Server
		connect();
		this.setVisible(true);
		//Run Thread
		rt.start();
	}
	//获取系统时间，必须调用一次就NEW一个Date对象
	public String getTime() {
		String str = null;//yyyy-MM-dd 
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		str = f.format(date);
		return str;
	}
	//做资源的关闭，回收处理
	public void disconnect() {
			try {
				if(  s!=null)  s.close();
				if(dos!=null)dos.close();
				if(dis!=null)dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1",9600);
			conned = true;
			dos = new DataOutputStream(s.getOutputStream());	
		} catch (ConnectException ce) {
System.out.println("服务器抽风了...");
//JOptionPane.showMessageDialog(null, "服务器抽风了...", "警告", JOptionPane.WARNING_MESSAGE);
System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class RecvThread implements Runnable {

		public void run() {
			try {
				while(conned) {
					dis = new DataInputStream(s.getInputStream());
					String str = dis.readUTF();
//System.out.println("i got it");
					ta.setText(ta.getText()+getTime()+" : "+str+"\n");					
				}
			} catch (SocketException se) {
System.out.println("Server is not line...");			
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}