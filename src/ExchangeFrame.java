//import of necessary libraries
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;

class ExchangeFrame{
	private JFrame frame;
	private JDialog exchangeFrame;
	private String from="", to="";

	private JButton woodF = new JButton("Wood");
	private JButton woolF = new JButton("Wool");
	private JButton brickF = new JButton("Brick");
	private JButton cornF = new JButton("Corn");
	private JButton oreF = new JButton("Ore");

	private JButton woodT = new JButton("Wood");
	private JButton woolT = new JButton("Wool");
	private JButton brickT = new JButton("Brick");
	private JButton cornT = new JButton("Corn");
	private JButton oreT = new JButton("Ore");

	private JButton confirm = new JButton("Confirm");
	private JButton cancel = new JButton("Cancel");

	ExchangeFrame(Catan c){
		World myWorld = c.getMyWorld();
		frame = c.getJFrame();
		initExchangeFrame(myWorld.players.get(myWorld.getTurn()));
	}

	private void setIconToButton(JButton button, Player player){
		String res = button.getText();
		ImageIcon icon = new ImageIcon(getClass().getResource("resource/"+res+".png"));
		ImageIcon icon_pressed = new ImageIcon(getClass().getResource("resource/"+res+"_pressed.png"));
		button.setIcon(icon);
		button.setPressedIcon(icon_pressed);
		button.setToolTipText(button.getText());
		button.setText(""+player.getResource(res));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
	}

	private void setIconToNButtons(Player p, JButton b0, JButton b1, JButton b2, JButton b3, JButton b4){
		setIconToButton(b0, p);
		setIconToButton(b1, p);
		setIconToButton(b2, p);
		setIconToButton(b3, p);
		setIconToButton(b4, p);
	}

	private void setIconsToAllButtons(Player player){
		setIconToNButtons(player, woodF, woolF, brickF, cornF, oreF);
		setIconToNButtons(player, woodT, woolT, brickT, cornT, oreT);
	}

	private void setAllExchangeToButtonsEnabled(){
		woodT.setEnabled(true);
		woolT.setEnabled(true);
		brickT.setEnabled(true);
		cornT.setEnabled(true);
		oreT.setEnabled(true);
	}

	private void changeLabel(int n, String mes){
		JPanel p = (JPanel)(exchangeFrame.getContentPane().getComponent(n));
		JLabel l = (JLabel)(p.getComponent(0));
		String text = (n==0?"From: ":"To: ") + mes;
		l.setText(text);
	}

	private void addActionListenerToExchangeButtons(Player player){
		woodF.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				from = "Wood";
				setAllExchangeToButtonsEnabled();
				woodT.setEnabled(false);
				changeLabel(0, "Wood");
				exchangeFrame.repaint();
			}
		});
		woolF.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				from = "Wool";
				setAllExchangeToButtonsEnabled();
				woolT.setEnabled(false);
				changeLabel(0, "Wool");
				exchangeFrame.repaint();
			}
		});
		brickF.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				from = "Brick";
				setAllExchangeToButtonsEnabled();
				brickT.setEnabled(false);
				changeLabel(0, "Brick");
				exchangeFrame.repaint();
			}
		});
		cornF.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				from = "Corn";
				setAllExchangeToButtonsEnabled();
				cornT.setEnabled(false);
				changeLabel(0, "Corn");
				exchangeFrame.repaint();
			}
		});
		oreF.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				from = "Ore";
				setAllExchangeToButtonsEnabled();
				oreT.setEnabled(false);
				changeLabel(0, "Ore");
				exchangeFrame.repaint();
			}
		});

		woodT.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				to = "Wood";
				changeLabel(2, "Wood");
				setAllExchangeFromButtonsEnabled(player);
				woodF.setEnabled(false);
				exchangeFrame.repaint();
			}
		});
		woolT.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				to = "Wool";
				changeLabel(2, "Wool");
				setAllExchangeFromButtonsEnabled(player);
				woolF.setEnabled(false);
				exchangeFrame.repaint();
			}
		});
		brickT.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				to = "Brick";
				changeLabel(2, "Brick");
				setAllExchangeFromButtonsEnabled(player);
				brickF.setEnabled(false);
				exchangeFrame.repaint();
			}
		});
		cornT.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				to = "Corn";
				changeLabel(2, "Corn");
				setAllExchangeFromButtonsEnabled(player);
				cornF.setEnabled(false);
				exchangeFrame.repaint();
			}
		});
		oreT.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				to = "Ore";
				changeLabel(2, "Ore");
				setAllExchangeFromButtonsEnabled(player);
				oreF.setEnabled(false);
				exchangeFrame.repaint();
			}
		});

		confirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doExchange(from, to, player);
				frame.repaint();
				exchangeFrame.dispose();
			}
		});
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doExchange("", "", player);
				frame.repaint();
				exchangeFrame.dispose();
			}
		});
	}

	private void setAllExchangeFromButtonsEnabled(Player player){
		if(player.getWood()<4)   woodF.setEnabled(false);
		else woodF.setEnabled(true);

		if(player.getWool()<4)   woolF.setEnabled(false);
		else woolF.setEnabled(true);

		if(player.getBricks()<4) brickF.setEnabled(false);
		else brickF.setEnabled(true);

		if(player.getCorn()<4)   cornF.setEnabled(false);
		else cornF.setEnabled(true);

		if(player.getOre()<4)    oreF.setEnabled(false);
		else oreF.setEnabled(true);
	}

	private void addString(String message, Container container){
    	JPanel p = new JPanel();
    	JLabel s = new JLabel(message);
    	p.setAlignmentX(Component.CENTER_ALIGNMENT);
    	p.add(s);
    	container.add(p);
    }

    private void addButtons(int n, Container container){
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		if(n==0){
			addTorFButtons(p, woodF, woolF, brickF, cornF, oreF);
		} else if(n==1){
			addTorFButtons(p, woodT, woolT, brickT, cornT, oreT);
		} else if(n==2){
			p.setLayout(new FlowLayout(FlowLayout.RIGHT));
			JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
	        grid.add(confirm);
	        grid.add(cancel);
	        p.add(grid);
		}
		container.add(p);
    }

	static void addTorFButtons(JPanel p, JButton woodT, JButton woolT, JButton brickT, JButton cornT, JButton oreT) {
		p.add(woodT);
		p.add(woolT);
		p.add(brickT);
		p.add(cornT);
		p.add(oreT);
	}

	private void createUI(Container container) {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        addString("From:"+from, container);
        addButtons(0,      container);
        addString("To:"+to,   container);
        addButtons(1, 	   container);
        addButtons(2,      container);
    }

    private void doExchange(String f, String t, Player player){
    	if(!f.equals("") && !t.equals("")){
			switch (f) {
				case "Wood": player.addWood(-4);
							 break;
				case "Wool": player.addWool(-4);
							 break;
				case "Brick":player.addBricks(-4);
							 break;
				case "Corn": player.addCorn(-4);
							 break;
				case "Ore":  player.addOre(-4);
							 break;
			}
			switch (t) {
				case "Wood": player.addWood(1);
							 break;
				case "Wool": player.addWool(1);
							 break;
				case "Brick":player.addBricks(1);
							 break;
				case "Corn": player.addCorn(1);
							 break;
				case "Ore":  player.addOre(1);
							 break;
			}
		}
    }

    private void initExchangeFrame(Player player){
		setIconsToAllButtons(player);

		exchangeFrame = new JDialog(frame, "Exchange bar", true);
		exchangeFrame.setSize(500, 300);
		exchangeFrame.setResizable(false);

		Rectangle rect = frame.getBounds();

		exchangeFrame.setLocation((int)(rect.getX()+(rect.getWidth()/2)-250), (int)(rect.getY()+(rect.getHeight()/2)-150));

		exchangeFrame.addWindowListener(new WindowListener() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            doExchange("", "", player);
	            exchangeFrame.dispose();
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

		setAllExchangeFromButtonsEnabled(player);

		setAllExchangeToButtonsEnabled();

		addActionListenerToExchangeButtons(player);

		createUI(exchangeFrame.getContentPane());

		exchangeFrame.setVisible(true);
	}
}