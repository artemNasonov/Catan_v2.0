import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

public class Town implements Variable{
	int local_x, local_y;
	int player;
	URL textureWay;
    Image texture;

	public Town(int x, int y, int p){
		this.local_x=x;
		this.local_y=y;
		this.player=p;
        textureWay = this.getClass().getResource("buildings/Town"+colorNames[player]+".png");
        try {
            texture = ImageIO.read(textureWay);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void draw(Graphics g){
		g.translate(100, 100);
        g.drawImage(texture, local_x*f_width-22, local_y*f_width-22, 44, 44, null);
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