import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Dice implements Variable{
	int num=0;
	int x, y;
	Image texture;
	Font traditionalArabicFont;

	public Dice(){
		try{
			texture = ImageIO.read(this.getClass().getResource("dice/Dice.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
		try {
			traditionalArabicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("fonts/tradbdo.ttf"));
			traditionalArabicFont = traditionalArabicFont.deriveFont(35F);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g, int n){
		g.setFont(traditionalArabicFont);
		x = n==1?900:1010;
		y = 50;
		g.drawImage(texture, x, y, 100, 96, null);
		g.drawString(""+num, x+28 , y+73);
		g.setFont(Variable.standartFont);
	}

	public void update(){
		num=(int)(Math.random()*6)+1;
	}

	public int get(){
		return num;
	}
}