//import of necessary libraries
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinnerWindow{
	Catan mainCatan;

	String name;

	JDialog winFrame;

	JButton confirm = new JButton("OK");

	public WinnerWindow(String s, Catan c){
		name=s;
		mainCatan=c;
		initWinWin();
	}

	private void addString(String message, Container container){
		JPanel p = new JPanel();
		JLabel l = new JLabel(message);
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.add(l);
		container.add(p);
	}

	private void addButton(Container container){
		container.add(confirm, "South");
	}

	private void createUI(Container container){
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		addString("Congratulations!!! Player "+name+" won this game!!!", container);
		addButton(container);
	}

	private void initWinWin(){
		winFrame = new JDialog(mainCatan.frame, "Congratulations!!!", true);
		winFrame.setSize(500, 100);
		winFrame.setResizable(false);
		Rectangle rect = mainCatan.frame.getBounds();
		winFrame.setLocation((int)(rect.getX()+(rect.getWidth()/2)-250), (int)(rect.getY()+(rect.getHeight()/2)-50));

		confirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		createUI(winFrame.getContentPane());
		winFrame.setVisible(true);
	}
}