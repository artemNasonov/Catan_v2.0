import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class World extends JPanel{
	Field[][] fields = new Field[5][5];
	StatusBar statusBar = new StatusBar();
	Dice dice1 = new Dice();
	Dice dice2 = new Dice();
	ArrayList<Player> players;
	ArrayList<Road> road = new ArrayList<>();
	ArrayList<Village> villa = new ArrayList<>();
	ArrayList<Town> town = new ArrayList<>();
	private int turn;
	private Image backgroundIMG;
	InitializationWindow inWin;
    // int robbersX, robbersY;

	public World(ArrayList<Player> arrP, InitializationWindow i){
		statusBar.setStatus("for this turn you have to build 2 villages and roads");
		players = arrP;
		this.inWin = i;
		inWin.setValueOnProgressBar(20);

		turn=(int)(Math.random()*players.size());
		players.get(turn).setTurn(true);
		inWin.setValueOnProgressBar(30);

        resetScores();
		generateWorld();
		inWin.setValueOnProgressBar(50);
        try {
            backgroundIMG = ImageIO.read(getClass().getResource("background/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        inWin.setValueOnProgressBar(60);
    }

    @Override
    protected void paintComponent(Graphics g){
        g.drawImage(backgroundIMG, 0, 0, 1200, 700, null);
        g.translate(0, 25);
		g.drawRect(96, 96, 508, 508);
		for(int y=0; y<5; y++){
        	for(int x=0; x<5; x++){
        		if(!fields[x][y].getHighlight()) fields[x][y].draw(g);
        	}
        }
        for(int y=0; y<5; y++){
            for(int x=0; x<5; x++){
                if(fields[x][y].getHighlight()) fields[x][y].draw(g);
                fields[x][y].unHighlight();
            }
        }
        for(int i=0; i<road.size(); i++){
        	road.get(i).draw(g);
        }
        for(int i=0; i<villa.size(); i++){
        	villa.get(i).draw(g);
        }
        for(int i=0; i<town.size(); i++){
        	town.get(i).draw(g);
        }
		statusBar.draw(g);
        g.translate(0, -25);
        for(int i=0; i<players.size(); i++){
        	players.get(i).setTurn(i==turn);
        	players.get(i).draw(g);
        }
        dice1.draw(g, 0);
        dice2.draw(g, 1);
    }
    
    private void generateWorld(){
    	ArrayList<Integer> nums = new ArrayList<>(), resources = new ArrayList<>();
		for(int i=0; i<25; i++){
			nums.add(i<9?(i/3+2):((i-1)/2+1));
			resources.add(i<20?(i/2)%5+1:25-i);
		}
		for(int y=0; y<5; y++){	
			for(int x=0; x<5; x++){
				Field f = new Field(x, y);
				int k = (int)(Math.random()*nums.size());
				f.setNumber(nums.get(k));
				nums.remove(k);
				k = (int)(Math.random()*resources.size());
				f.setResource(resources.get(k));
				resources.remove(k);
				fields[x][y]=f;
			}
		}
    }
    
    public int getTurn(){
    	return turn;
    }

    public void updTurn(int n){
    	turn=turn+1>=n?0:turn+1;
    }

    public void allocateRes(int n){
    	for(int y=0; y<5; y++){
    		for(int x=0; x<5; x++){
    			Field f = fields[x][y];
    			if(f.getNum()==n){
                    f.highlight();
    				addRes(f);
    			}
    		}
    	}
    }

    private void addResToPlayer(int res, int player, int num){
		switch (res) {
			case 1: players.get(player).addWood(num);
				break;
			case 2: players.get(player).addWool(num);
				break;
			case 3: players.get(player).addBricks(num);
				break;
			case 4: players.get(player).addCorn(num);
				break;
			case 5: players.get(player).addOre(num);
				break;
		}
	}

    private void addRes(Field f){
    	int addX=f.getX(), addY=f.getY();
    	for(int i=0; i<villa.size(); i++){
    		Village v = villa.get(i);
    		if((v.getX()==addX && v.getY()==addY) || (v.getX()==addX+1 && v.getY()==addY) ||
    			(v.getX()==addX && v.getY()==addY+1) || (v.getX()==addX+1 && v.getY()==addY+1)){
    			addResToPlayer(f.getRes(), v.getPlayer(), 1);
    		}
    	}
    	for(int i=0; i<town.size(); i++){
    		Town t = town.get(i);
    		if((t.getX()==addX && t.getY()==addY) || (t.getX()==addX+1 && t.getY()==addY) ||
    			(t.getX()==addX && t.getY()==addY+1) || (t.getX()==addX+1 && t.getY()==addY+1)){
				addResToPlayer(f.getRes(), t.getPlayer(), 2);
    		}
    	}
    }

    private void resetScores(){
        for(int i=0; i<players.size(); i++){
            players.get(i).setScore(0);
        }
    }

    public void updScore(){
        resetScores();
        for (int i=0; i<villa.size(); i++) {
            players.get(villa.get(i).getPlayer()).setScore(players.get(villa.get(i).getPlayer()).getScore()+1);
        }
        for (int i=0; i<town.size(); i++) {
            players.get(town.get(i).getPlayer()).setScore(players.get(town.get(i).getPlayer()).getScore()+2);
        }
    }

    public int checkEnd(int checkSum){
        for(int i=0; i<players.size(); i++){
            if(players.get(i).getScore()>=checkSum){
                return i;
            }
        }
        return -1;
    }
}