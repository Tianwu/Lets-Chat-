import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient extends Frame{
	private TextField tf = new TextField();
	private TextArea  ta = new TextArea();
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}

	private void launchFrame() {
		this.setTitle("Let's Chat");
		this.setBounds(100, 100, 400, 300);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//����д�������ڹرմ���ʱ������Դ���յĵ���
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.add(ta, BorderLayout.NORTH);
		this.add(tf, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}
}