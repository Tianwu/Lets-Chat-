import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient extends Frame{
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}

	private void launchFrame() {
		this.setTitle(" Let's Chat");
		this.setBounds(100, 100, 400, 300);
		//这样写，可以在关闭窗口时，做资源回收的当作
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.setVisible(true);
	}
}
