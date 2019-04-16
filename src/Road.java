import java.awt.Color;
import java.awt.Graphics;

public class Road implements Variable{
	private int start_x, start_y, end_x, end_y;
	private int player;
	///
	public Road(int x, int y, int p){
		this.start_x=x;
		this.start_y=y;
		this.end_x=x;
		this.end_y=y;
		this.player=p;
	}
	///
	public boolean end(int x, int y){
		if(Math.abs(this.start_x - x)+Math.abs(this.start_y - y)==1){
			this.end_x=x;
			this.end_y=y;
			return true;
		} 
		return false;
	}
	///
	public void draw(Graphics g){
		g.translate(100, 100);
		g.setColor(colors[player]);
		// System.out.println("player="+player);
		if(Math.abs(start_x - end_x)==1){
			g.fillRect(f_width*Math.min(start_x, end_x), f_width*Math.min(start_y, end_y)-5, f_width, 10);
		} else if(Math.abs(start_y - end_y)==1){
			g.fillRect(f_width*Math.min(start_x, end_x)-5, f_width*Math.min(start_y, end_y), 10, f_width);
		}
		g.setColor(Color.BLACK);
		g.translate(-100, -100);
	}
	public int getSX(){
		return start_x;
	}
	public int getSY(){
		return start_y;
	}
	public int getEX(){
		return end_x;
	}
	public int getEY(){
		return end_y;
	}
	public int getPlayer(){
		return player;
	}
}