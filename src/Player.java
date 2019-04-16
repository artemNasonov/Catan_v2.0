import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;

public class Player implements Variable{
	private int wood=4, bricks=2, wool=4, corn=4, ore=2;
	private int number;
	private int score=0;
	boolean turn=false;
	private String name;
	Font traditionalArabicFont;

	public String getName(){
		return name;
	}

	public Player(int n, String s){
		number=n;
		name=s;
		try {
			traditionalArabicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("fonts/tradbdo.ttf"));
			traditionalArabicFont = traditionalArabicFont.deriveFont(16F);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void addWood(int n) {
		wood += n;
	}
	public void addWool(int n){
		wool+=n;
	}
	public void addBricks(int n){
		bricks+=n;
	}
	public void addCorn(int n){
		corn+=n;
	}
	public void addOre(int n){
		ore+=n;
	}
	public void setScore(int n){
		score=n;
	}
	public void setTurn(boolean n){
		turn = n;
	}

	public int getWood(){
		return wood;
	}
	public int getWool(){
		return wool;
	}
	public int getBricks(){
		return bricks;
	}
	public int getOre(){
		return ore;
	}
	public int getCorn(){
		return corn;
	}
	public int getScore(){
		return score;
	}
	public int getResource(String res){
	    switch (res){
            case "Wool": return wool;
            case "Wood": return wood;
            case "Ore": return ore;
            case "Brick": return bricks;
            case "Corn": return corn;
        }
        return -1;
    }

	public void draw(Graphics g0){
		String mes;
		Graphics2D g = (Graphics2D)g0;
		int x=625, y=200+number*f_width;
		g.setColor(Variable.colors[number]);
		if(!turn){
			g.drawRoundRect(x, y, 525, f_width-15, 10, 10);
		} else {
			g.setStroke(new BasicStroke(5));
			g.drawRoundRect(x, y, 535, f_width-10, 12, 12);
			g.setStroke(new BasicStroke(1));
		}
		g.setColor(Color.BLACK);
		if(turn){
			mes = "Player "+name+": Your turn!";
		} else {
			mes = "Player "+name+":";
		}
		g.setFont(traditionalArabicFont);
		Graphics2D g2d = g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(mes, g2d);
        x+=2;
        y+=10;
		g.drawString(mes, x, y+(int)(r.getHeight()/2));
		y+=(int)(r.getHeight());
		mes = "Your resources: "+"Wood - "+wood+"; Wool - "+wool+"; Bricks - "+bricks+"; Corn - "+corn+"; Ore - "+ore+";";
		r = fm.getStringBounds(mes, g2d);
		g.drawString(mes, x, y+(int)(r.getHeight()/2));
		mes = "Your score is "+score;
		y+=(int)(r.getHeight());
		r = fm.getStringBounds(mes, g2d);
		g.drawString(mes, x, y+(int)(r.getHeight()/2));
	}

	public boolean checkRes(String type){
		switch (type) {
			case "Road": return wood>=1 && bricks>=1;
			case "Village": return wood>=1 && bricks>=1 && wool>=1 && corn>=1;
			case "Town": return ore>=3 && corn>=2;
		}
		return false;
	}
}