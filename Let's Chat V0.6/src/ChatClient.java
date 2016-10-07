import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JOptionPane;

public class ChatClient extends Frame{
	private TextField tf = new TextField();
	private TextArea  ta = new TextArea();
	private Socket s = null;
	private DataOutputStream dos = null;
	
	public ChatClient() {
		super();
		this.setTitle("Let's Chat");
		this.setBounds(100, 100, 400, 300);
		//����д�������ڹرմ���ʱ������Դ���յĵ���
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.add(ta, BorderLayout.NORTH);
		this.add(tf, BorderLayout.SOUTH);

//		tf.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//					ta.setText(tf.getText().trim());
//					tf.setText("");
//				}
//		});
		//Lambda ��д������������࣬ȴ��Щ��ֱ��
		tf.addActionListener((e)->{
			String str = tf.getText().trim();
			ta.setText(str);
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
		//����Server
		connect();
		this.setVisible(true);
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1",9600);
			dos = new DataOutputStream(s.getOutputStream());
		} catch (ConnectException ce) {
			JOptionPane.showMessageDialog(null, "�����������...", "����", JOptionPane.WARNING_MESSAGE);
System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}