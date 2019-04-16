import javax.imageio.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class Field implements Variable{
	private int number;
	private int resource;
	private int local_x, local_y;
	private String src = "";
	private Image texture;
	private boolean highlighted=false;
	Font traditionalArabicFont;
	///
	public Field(int x, int y){
		this.local_y=y;
		this.local_x=x;
		try {
			traditionalArabicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("fonts/tradbdo.ttf"));
			traditionalArabicFont = traditionalArabicFont.deriveFont(26F);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g){
		//draw rectangle of field
		Color highlight = highlighted?Color.ORANGE:Color.BLACK;
		g.translate(100, 100);
        g.setColor(highlight);
		g.drawRect(f_width*local_x, f_width*local_y, f_width, f_width);
		g.setColor(Color.BLACK);

		g.setFont(traditionalArabicFont);
		//draw texture
		g.drawImage(texture, Variable.f_width*local_x+3, Variable.f_width*local_y+3, 94, 94, null);
		//draw number
		highlight = highlighted?Color.ORANGE:Color.LIGHT_GRAY;
		g.setColor(highlight);
		g.fillOval(Variable.f_width*local_x+33, Variable.f_width*local_y+29, 32, 32);
		g.setColor(Color.BLACK);
		g.drawOval(Variable.f_width*local_x+33, Variable.f_width*local_y+29, 32, 32);
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(""+number, g);
		g.drawString(""+number, f_width*local_x+50-(int)(r.getWidth()/2), f_width*local_y+73-(int)(r.getHeight()/2));

		g.setFont(Variable.standartFont);
		g.translate(-100, -100);
	}

	public int getNum(){
		return number;
	}
	public int getX(){
		return local_x;
	}
	public int getY(){
		return local_y;
	}
	public int getRes() {
		return resource;
	}

	public void highlight(){
		highlighted=true;
	}
	public void unHighlight(){
		highlighted=false;
	}
	public boolean getHighlight(){
		return highlighted;
	}

	public void setNumber(int n){
		number=n;
	}
	public void setResource(int n){
		resource=n;
		switch(resource){
			case 1: src="Wood";
				break;
			case 2: src="Wool";
				break;
			case 3: src="Bricks";
				break;
			case 4:	src="Corn";
				break;
			case 5:	src="Ore";
				break;
		}
		try {
			texture = ImageIO.read(getClass().getResource("textures/" + src + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}