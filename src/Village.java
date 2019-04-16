import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

public class Village implements Variable{
	int local_x, local_y;
	int player;
	URL textureWay;
	Image village;

	public Village(int x, int y, int p){
		this.local_x=x;
		this.local_y=y;
		this.player=p;
		textureWay = this.getClass().getResource("buildings/Village"+colorNames[player]+".png");
		try{
			village = ImageIO.read(textureWay);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public void draw(Graphics g){
		g.translate(100, 100);
		g.drawImage(village, local_x*f_width-15, local_y*f_width-15, 30, 30, null);
		g.setColor(Color.BLACK);
		g.translate(-100, -100);
	}
	public int getX(){
		return local_x;
	}
	public int getY(){
		return local_y;
	}
	public int getPlayer(){
		return player;
	}
}