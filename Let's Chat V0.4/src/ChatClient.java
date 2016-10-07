import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ChatClient extends Frame{
	private TextField tf = new TextField();
	private TextArea  ta = new TextArea();
	private Socket s = null;
	public ChatClient() {
		super();
		this.setTitle("Let's Chat");
		this.setBounds(100, 100, 400, 300);
		//这样写，可以在关闭窗口时，做资源回收的当作
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
		//Lambda 的写法，看起来简洁，却有些不直观
		tf.addActionListener((e)->{
			ta.setText(tf.getText().trim());
			tf.setText("");
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
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1",9600);
		} catch (ConnectException ce) {
			JOptionPane.showMessageDialog(null, "服务器抽风了...", "警告", JOptionPane.WARNING_MESSAGE);
System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}