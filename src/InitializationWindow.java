//import of necessary libraries
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;

public class InitializationWindow{
	Catan mainCatan;

	JDialog initFrame;
	JDialog progressFrame;
	JProgressBar progressBar;

	JButton confirm = new JButton("OK");
	JButton cancel = new JButton("Cancel");

	int numberOfPlayers;
	String[] names;

	public InitializationWindow(Catan c){
		mainCatan=c;
		initInitWindow();
	}

	private void addStringAndTextField(String message1, String message2, Container container){
    	JPanel p = new JPanel();
    	JLabel s = new JLabel(message1);
    	JTextField tf = new JTextField();
    	tf.setText(message2);
    	tf.setPreferredSize(new Dimension(100, 20));
    	// p.setAlignmentX(Component.ComponentTER_ALIGNMENT);
    	p.add(s);
    	p.add(tf);
    	container.add(p);
    }
    private void addButtons(Container container){
    	JPanel p = new JPanel();
    	p.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JPanel grid = new JPanel(new GridLayout(1, 2, 5, 10));
	    grid.add(confirm);
	    grid.add(cancel);
	    p.add(grid);
	    container.add(p);
    }

	private void createUIN(Container container){//create User Interface for Number
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		addStringAndTextField("Number of players (from 2 to 4):", "2", container);
		addButtons(container);
	}
	private void createUIS(Container container){
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		for(int i=1; i<=numberOfPlayers; i++){
			addStringAndTextField("Player "+i+": ", "Player"+i, container);
		}
		addButtons(container);
	}

	private void addActionListenerToButtonsN(){
		confirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				JPanel p = (JPanel)(initFrame.getContentPane().getComponent(0));
				JTextField tf = (JTextField)(p.getComponent(1));
				String num = tf.getText();
				try{
					numberOfPlayers = Integer.parseInt(num);
				} catch (NumberFormatException en){
					numberOfPlayers = -1;
				}
				if(numberOfPlayers>=2 && numberOfPlayers<=4){
					mainCatan.setNumberOfPlayers(numberOfPlayers);
					names = new String[numberOfPlayers];
					initFrame.getContentPane().removeAll();
					initNameWindow();
				}
			}
		});
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
	}
	private void addActionListenerToButtonsS(){
		confirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				for(int i=0; i<numberOfPlayers; i++){
					JPanel p = (JPanel)(initFrame.getContentPane().getComponent(i));
					JTextField tf = (JTextField)p.getComponent(1);
					names[i]=tf.getText();
				}
				mainCatan.initPlayers(numberOfPlayers, names);
				initFrame.dispose();
			}
		});
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
	}


	private void initInitWindow(){
		initFrame = new JDialog(mainCatan.frame, "New game", true);
		initFrame.setSize(500, 120);
		initFrame.setResizable(false);

		Rectangle rect = (Rectangle)(mainCatan.frame.getBounds());

		initFrame.setLocation((int)(rect.getX()+(rect.getWidth()/2)-250), (int)(rect.getY()+(rect.getHeight()/2)-50));

		createUIN(initFrame.getContentPane());

		addActionListenerToButtonsN();
		initFrame.addWindowListener(new WindowListener() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            System.exit(0);
	        }

	        @Override 
	        public void windowOpened(WindowEvent e) {}

	        @Override 
	        public void windowClosed(WindowEvent e) {}

	        @Override 
	        public void windowIconified(WindowEvent e) {}

	        @Override 
	        public void windowDeiconified(WindowEvent e) {}

	        @Override 
	        public void windowActivated(WindowEvent e) {}

	        @Override 
	        public void windowDeactivated(WindowEvent e) {}
	    });

		initFrame.setVisible(true);
	}

	private void initNameWindow(){
		initFrame.setSize(500, numberOfPlayers*80);
		Rectangle rect = (Rectangle)(mainCatan.frame.getBounds());
		initFrame.setLocation((int)(rect.getX()+(rect.getWidth()/2)-250), (int)(rect.getY()+(rect.getHeight()/2)-numberOfPlayers*40));

		createUIS(initFrame.getContentPane());

		cancel.removeActionListener((cancel.getActionListeners())[0]);
		confirm.removeActionListener((confirm.getActionListeners())[0]);
		addActionListenerToButtonsS();

		initFrame.addWindowListener(new WindowListener() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            System.exit(0);
	        }

	        @Override 
	        public void windowOpened(WindowEvent e) {}

	        @Override 
	        public void windowClosed(WindowEvent e) {}

	        @Override 
	        public void windowIconified(WindowEvent e) {}

	        @Override 
	        public void windowDeiconified(WindowEvent e) {}

	        @Override 
	        public void windowActivated(WindowEvent e) {}

	        @Override 
	        public void windowDeactivated(WindowEvent e) {}
	    });
	    initFrame.setVisible(true);
	}

	public void initProgressBarWindow(){
		progressFrame = new JDialog(mainCatan.frame, "Loading");
		progressFrame.setSize(400, 60);
		Rectangle rect = mainCatan.frame.getBounds();
		progressFrame.setLocation((int)(rect.getX()+(rect.getWidth()/2)-250), (int)(rect.getY()+(rect.getHeight()/2)-50));
		progressFrame.setLayout(null);

		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		progressBar.setPreferredSize(new Dimension(350, 20));
		progressBar.setValue(10);

		progressFrame.setContentPane(progressBar);

		progressFrame.setVisible(true);
	}

	public void setValueOnProgressBar(int n){
		progressBar.setValue(n);
		progressFrame.repaint();
	}

	public void close(){
		progressFrame.dispose();
	}
}