//import of necessary libraries
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

//The main class of the game
public class Catan {
	private int type=0;	//variable which shows us type of editing object
	private int state =0;	//shows us state of editing obj
	private int numberOfPlayers;
	private int hod=0;  //it should be named twist, but i dono English
	private int sx=0, sy=0;//start x and y for road
	private int checkSum=7;//points player need to win

	private boolean wereRolled = false;

	JFrame frame = new JFrame();//our main frame

	private JButton completeTheMove = new JButton("complete_the_move");
	private JButton selectTown = new JButton("town_button");
	private JButton selectVillage = new JButton("village_button");
	private JButton selectRoad = new JButton("select_road");
	private JButton rollTheDice = new JButton("roll_the_dice");
	private JButton exchange = new JButton("exchange_frame");

    private World myWorld;//my world

    private ArrayList<Player> addP = new ArrayList<>();

	public void go(){//main void
		initFrame(); //create frame

		InitializationWindow inWin = new InitializationWindow(this);
		inWin.initProgressBarWindow();

        completeTheMove.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            	myWorld.updScore();
	        	if(myWorld.checkEnd(checkSum)==-1){
	        		if(hod/numberOfPlayers<=1){
		        		if(checkFirstSteps(myWorld.road, myWorld.villa, hod, 0)){ //if player can complete the move on the first two steps
			        		type = 0;
				        	myWorld.updTurn(numberOfPlayers);
				            hod++;
				            myWorld.statusBar.setStatus("No items selected");
			            } else {
			            	myWorld.statusBar.setStatus("You can't complete the move!!!");
			            }
		            } else if(wereRolled) { //if player can complete the move on the next steps
		            	type = 0;
				        myWorld.updTurn(numberOfPlayers);
				        wereRolled=false;
				        hod++;
				        myWorld.statusBar.setStatus("No items selected");
		            } else {
		            	myWorld.statusBar.setStatus("Roll the dice to complete the move!!!");
		            }
	        	} else {
	        		openFinalWindow();
	        	}
            	frame.repaint();
            }
        });
        inWin.setValueOnProgressBar(11);
        selectRoad.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            	if(myWorld.checkEnd(checkSum)==-1){
	            	if(hod/numberOfPlayers<=1) { //if it's first two steps
	            		if(!checkFirstSteps(myWorld.road, myWorld.villa, hod, 1)){ //if road was not bild yet
	                		type=1;
	                		myWorld.statusBar.setStatus("Road was chosen");
	                		frame.repaint();
	                	} else {
	                		myWorld.statusBar.setStatus("You can't build road now!!!");
	                		frame.repaint();
	                	}
	            	} else {
	            		type=1;
	                	myWorld.statusBar.setStatus("Road was chosen, it'll cost 1 brick + 1 wood");
	                	frame.repaint();
	            	}
	            }
            }
        });
		inWin.setValueOnProgressBar(12);
        selectVillage.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            	if(myWorld.checkEnd(checkSum)==-1){
		            if(hod/numberOfPlayers<=1){
		            	if(!checkFirstSteps(myWorld.road, myWorld.villa, hod, 2)){ //if village wasn't build yet
		                	type=2;
		                	myWorld.statusBar.setStatus("Village was chosen");
		                	frame.repaint();
		                } else {
		                	myWorld.statusBar.setStatus("You can't build village now!!!");
		                	frame.repaint();
		                }
		            } else {
		            	type=2;
		                myWorld.statusBar.setStatus("Village was chosen, it'll cost 1 brick + 1 wood + 1 wool + 1 corn");
		                frame.repaint();
		            }
		        }
            }
        });
		inWin.setValueOnProgressBar(13);
        selectTown.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            	if(myWorld.checkEnd(checkSum)==-1){
		            if(hod/numberOfPlayers>1){ //if it isn't first two steps
		            	type=3;
		                myWorld.statusBar.setStatus("Select the village you want to improve, it'll cost 2 corn + 3 Ore");
		            } else {
		            	myWorld.statusBar.setStatus("You can't upgrade village now!!!");
		            }
		        }
		        frame.repaint();
            }
        });
		inWin.setValueOnProgressBar(14);
        rollTheDice.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            	if(myWorld.checkEnd(checkSum)==-1){
	            	if(!wereRolled && hod/numberOfPlayers>1){ //if they were not rolled yet and it isn't first two steps, roll the dice
	            		myWorld.dice1.update();
				    	myWorld.dice2.update();
				    	int sum = myWorld.dice1.get()+myWorld.dice2.get();
				    	myWorld.allocateRes(sum);
				    	myWorld.statusBar.setStatus("You rolled the dice and the sum is "+sum+"!!!");
				    	wereRolled=true;
	            	} else if(wereRolled){ //if they were rolled yet
	            		myWorld.statusBar.setStatus("You have already rolled the dice!!!");
	            	} else if(hod/numberOfPlayers<=1){ //if it is first two steps
	            		myWorld.statusBar.setStatus("You can't roll the dice during the first two moves!!!");
	            	}
	            }
			    frame.repaint();
            }
        });
		inWin.setValueOnProgressBar(15);
        exchange.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e) {
            	if(myWorld.checkEnd(checkSum)==-1){
		            if(hod/numberOfPlayers>1){ //if it isn't first two steps
		            	ExchangeFrame exchangeFrame = new ExchangeFrame(Catan.this);
		        	} else {
		            	myWorld.statusBar.setStatus("You can't exchange resources now!!!");
		            }
		        }
            	frame.repaint();
            }
        });
		inWin.setValueOnProgressBar(16);


        myWorld = new World(addP, inWin);
		inWin.setValueOnProgressBar(65);

		addIconsToButtons();
		inWin.setValueOnProgressBar(70);
		addTooltipToButtons();
		inWin.setValueOnProgressBar(75);
		initButtons(); //add buttons to panel
		inWin.setValueOnProgressBar(80);

        myWorld.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	int  mouseX=nearest(e.getX()), mouseY=nearest(e.getY()), t=myWorld.getTurn();
            	if(myWorld.checkEnd(checkSum)==-1){
            		// System.out.println("Eche ne konez");
	            	if(hod/numberOfPlayers>1){//if it isn't first two rounds
	            		if(type==1){//if road is chosen

		            		if(state ==0){//if the beginning of the road is not selected
		            			if(haveRes(t, "Road")){//if player has needed resourses
		            				if(checkRoadStart(mouseX, mouseY, t, myWorld.road, myWorld.villa, myWorld.town)){//if we can start here
			            				Road r = new Road(mouseX, mouseY, t);
			            				sx=mouseX; sy=mouseY;

			            				myWorld.statusBar.setStatus("The beginning of the road is marked");
			            				
			            				myWorld.road.add(r);
			            				state =1;//shows that start was selected
			            			} else {
			            				type=0;//reset the current mode
			            				
			            				myWorld.statusBar.setStatus("You can't start road here!!!");
			            			}
		            			} else {
		            				myWorld.statusBar.setStatus("You have no resources(((");
		            				type=0;
		            			}
		            			frame.repaint();
		            		} else if(state ==1){//if the start of the road was chosen

		            			if(checkRoadEnd(sx, sy, mouseX, mouseY, myWorld.road)){//if the road does not overlap with another road

			            			if(myWorld.road.get(myWorld.road.size()-1).end(mouseX, mouseY)){//if lenght of the road is equal to one
			            				myWorld.statusBar.setStatus("The road was successfully built");
			            				rmRes(t, "Road");
			            				frame.repaint();
			            			} else {
			            				myWorld.statusBar.setStatus("You can't build this road!!!");
			            				myWorld.road.remove(myWorld.road.size()-1);//delete this road, we can't build it
			            				frame.repaint();
			            			}
			            		} else {
			            			myWorld.statusBar.setStatus("You can't build this road!!!");
			            			myWorld.road.remove(myWorld.road.size()-1);
			            			frame.repaint();
			            		}
			            		state = 0;//
				            	type = 0;//reset all params
			            	}
		            	} else if(type==2){//if village was chosen
		            		if(haveRes(t, "Village")){
		            			if(checkVillaR(mouseX, mouseY, t, myWorld.road, myWorld.villa, myWorld.town)){
			            			//if the village is built along the road
				            		Village v = new Village(mouseX, mouseY, t);
				            		myWorld.villa.add(v);
				            		myWorld.statusBar.setStatus("The Village was successfully built");
				            		myWorld.updScore();
				            		rmRes(t, "Village");
				            	} else {
				            		myWorld.statusBar.setStatus("You can't build village here!!!");
				            	}	
		            		} else {
		            			myWorld.statusBar.setStatus("You have no resources(((");
		            		}
		            		type=0;//reset type
		            		frame.repaint();
		            	} else if(type==3){
		            		if(haveRes(t, "Town")){
		            			int rmbl = checkTown(mouseX, mouseY, t, myWorld.villa);
		            			//number of village, which we'll upgrade and remove from world
			            		if(rmbl!=-1){//if it exists
			            			Town city = new Town(mouseX, mouseY, t);	//\
			            			myWorld.town.add(city);						// >|upgrade village
			            			myWorld.villa.remove(rmbl);					///
			            			myWorld.statusBar.setStatus("The Village was successfully upgraded");
			            			rmRes(t, "Town");
			            			myWorld.updScore();
			            		} else {
			            			myWorld.statusBar.setStatus("There is no Village to upgrade!!!");
			            		}	
		            		} else {
		            			myWorld.statusBar.setStatus("You have no resources(((");
		            		}
		            		type=0;
		            		frame.repaint();
		            	}
	            	} else {//if it's first two rounds:
	            			//						-the village can be built anywhere in the field;
	            			//						-the village could not be improved until the city
	            			//						-resources should not be written off
	            			//otherwise the same as above
	            		if(type==1){
			           		if(state ==0){
			           			if(checkRoadStart(mouseX, mouseY, t, myWorld.road, myWorld.villa, myWorld.town)){
			           				Road r = new Road(mouseX, mouseY, t);
			           				sx=mouseX;
			           				sy=mouseY;
			           				myWorld.statusBar.setStatus("The beginning of the road is marked");
			           				frame.repaint();
			           				myWorld.road.add(r);
			           				state =1;
			           			} else {
			           				type=0;
			           				myWorld.statusBar.setStatus("You can't start road here!!!");
			           				frame.repaint();
			           			}
			           		} else if(state ==1){
			           			if(checkRoadEnd(sx, sy, mouseX, mouseY, myWorld.road)){
				           			if(myWorld.road.get(myWorld.road.size()-1).end(mouseX, mouseY)){
				           				myWorld.statusBar.setStatus("The road was successfully built");
				           				frame.repaint();
				           			} else {
				           				myWorld.statusBar.setStatus("You can't build this road!!!");
				           				myWorld.road.remove(myWorld.road.size()-1);
				           				frame.repaint();
				           			}
				           		} else {
				           			myWorld.statusBar.setStatus("You can't build this road!!!");
				           			myWorld.road.remove(myWorld.road.size()-1);
				           			frame.repaint();
				           		}
				           		state = 0;
					           	type = 0;
				           	}
			           	} else if(type==2){
			            		if(checkVilla(mouseX, mouseY, myWorld.villa, myWorld.town)){
				            		Village v = new Village(mouseX, mouseY, t);
				            		myWorld.villa.add(v);
				            		myWorld.statusBar.setStatus("The Village was successfully built");
				            		myWorld.updScore();
				            	} else {
				            		myWorld.statusBar.setStatus("You can't build village here!!!");
				            	}	
			            		type=0;
			            		frame.repaint();
			           	} else if(type==3){
			            		myWorld.statusBar.setStatus("You can't improve the village now!!!");
			            		type=0;
			            		frame.repaint();
			           	}
	            	}
	            }
	            if(myWorld.checkEnd(checkSum)!=-1){
	            	openFinalWindow();
            	}
            	frame.repaint();
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
		inWin.setValueOnProgressBar(90);

        frame.add(myWorld);	//add panel to frame
		inWin.close();
        frame.setVisible(true);
	}

	private int nearest(int mouse){//find the grid node closest to clicking the mouse
		if(mouse>=600) return 5;
        else if(mouse<=100) return 0;
        else return (mouse/100)+(int)Math.round((double)(mouse%100)/100)-1;
	}

	private boolean checkRoadStart(int dx, int dy, int dp, ArrayList<Road> ro, ArrayList<Village> vi, ArrayList<Town> tow){
		//if there is where to start the road
		if (checkRoad(dx, dy, dp, ro)) return true;
		for(int i=0; i<vi.size(); i++){
			Village v = vi.get(i);
			if(v.getX()==dx && v.getY()==dy && v.getPlayer()==dp){
				return true;
			}
		}
		for(int i=0; i<tow.size(); i++){
			Town t = tow.get(i);
			if(t.getX()==dx && t.getY()==dy && t.getPlayer()==dp){
				return true;
			}
		}
		return false;
	}

	private boolean checkRoadEnd(int sx, int sy, int dx, int dy, ArrayList<Road> ro){
		//if roads do not overlap
		for(int i=0; i<ro.size(); i++){
			Road r = ro.get(i);
			if((r.getEX()==sx && r.getEY()==sy && r.getSX()==dx && r.getSY()==dy) || 
			   (r.getEX()==dx && r.getEY()==dy && r.getSX()==sx && r.getSY()==sy)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkVilla(int dx, int dy, ArrayList<Village> vi, ArrayList<Town> tow){
		//check whether you can build a village on the first two moves
		return !checkViOrTown(dx, dy, vi, tow);
	}

	private boolean checkVillaR(int dx, int dy, int dp, ArrayList<Road> ro, ArrayList<Village> vi, ArrayList<Town> tow){
		//check whether you can build a village on the next moves
		if (checkViOrTown(dx, dy, vi, tow)) return false;
		return checkRoad(dx, dy, dp, ro);
	}

	private boolean checkRoad(int dx, int dy, int dp, ArrayList<Road> ro) {
		for(int i=0; i<ro.size(); i++){
			Road r = ro.get(i);
			if(((r.getEX()==dx && r.getEY()==dy) || (r.getSY()==dy && r.getSX()==dx)) && (r.getPlayer()==dp)){
				return true;
			}
		}
		return false;
	}

	private boolean checkViOrTown(int dx, int dy, ArrayList<Village> vi, ArrayList<Town> tow) {
		for(int i=0; i<vi.size(); i++){
			Village v = vi.get(i);
			if(Math.abs(v.getX()-dx)+Math.abs(v.getY()-dy)<=1){
				return true;
			}
		}
		for(int i=0; i<tow.size(); i++){
			Town t = tow.get(i);
			if(Math.abs(t.getX()-dx)+Math.abs(t.getY()-dy)<=1){
				return true;
			}
		}
		return false;
	}

	private int checkTown(int dx, int dy, int dp, ArrayList<Village> vi){
		//check whether there is something to improve
		for(int i=0; i<vi.size(); i++){
			Village v = vi.get(i);
			if(v.getX()==dx && v.getY()==dy && v.getPlayer()==dp){
				return i;
			}
		}
		return -1;
	}

	private boolean checkFirstSteps(ArrayList<Road> ro, ArrayList<Village> vi, int h, int flag){
		//check whether what you need is built
		switch (flag) {
			case 0: return vi.size()==h+1 && ro.size()==h+1;
			case 1: return ro.size()==h+1;
			case 2: return vi.size()==h+1;
		}
		return false;//if always false chek flag parametr
	}

	private void initFrame(){
		//set start configurations for the frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Catan");
        frame.setSize(1200, 700);
        frame.setResizable(false);
        frame.setLocation(50, 2);
	}

	private void initButtons(){
		ExchangeFrame.addTorFButtons(myWorld, completeTheMove, selectRoad, selectVillage, selectTown, rollTheDice);
		myWorld.add(exchange);
	}

	private void addIcoToButton(JButton button){
		button.setIcon(new ImageIcon(getClass().getResource("buttons/"+button.getText()+".jpg")));
		button.setPressedIcon(new ImageIcon(getClass().getResource("buttons/"+button.getText()+"_pressed.jpg")));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setText("");
	}

	private void addIconsToButtons(){
		addIcoToButton(selectRoad);
		addIcoToButton(selectTown);
		addIcoToButton(selectVillage);
		addIcoToButton(completeTheMove);
		addIcoToButton(exchange);
		addIcoToButton(rollTheDice);
	}

	private void addTooltipToButtons(){
		completeTheMove.setToolTipText("Complete the move");
		selectRoad.setToolTipText("Select road");
		selectVillage.setToolTipText("Select village");
		selectTown.setToolTipText("Select town");
		rollTheDice.setToolTipText("Roll the dice");
		exchange.setToolTipText("Exchange resource");
	}

	private boolean haveRes(int n, String type){
		//check if player has enough resources
		Player p = myWorld.players.get(n);
		return p.checkRes(type);
	}

	private void rmRes(int n, String type){
		//remove res from player
		switch (type) {
			case "Road": myWorld.players.get(n).addBricks(-1);
						 myWorld.players.get(n).addWood(-1);
						 break;
			case "Village": myWorld.players.get(n).addBricks(-1);
							myWorld.players.get(n).addWood(-1);
							myWorld.players.get(n).addWool(-1);
							myWorld.players.get(n).addCorn(-1);
							break;
			case "Town": myWorld.players.get(n).addCorn(-2);
						 myWorld.players.get(n).addOre(-3);
						 break;
		}
	}

	public World getMyWorld(){
		return this.myWorld;
	}
	public JFrame getJFrame(){
		return this.frame;
	}
	public void setNumberOfPlayers(int n){
		numberOfPlayers=n;
	}
	public void initPlayers(int n, String[] names){
		for(int i=0; i<n; i++){
			addP.add(new Player(i, names[i]));
		}
	}

	private void openFinalWindow(){
		String winnerName=myWorld.players.get(myWorld.checkEnd(checkSum)).getName();
		myWorld.statusBar.setStatus("We have winner!!! It's player "+winnerName);
		WinnerWindow winWin = new WinnerWindow(winnerName, Catan.this);
	}

	public static void main(String[] args) {
		Catan c = new Catan();
		c.go();
	}
}
