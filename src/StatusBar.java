import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class StatusBar implements Variable{
	String status;
	Font traditionalArabicFont;

	public StatusBar(){
		status="";
		try {
			traditionalArabicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("fonts/tradbdo.ttf"));
			traditionalArabicFont = traditionalArabicFont.deriveFont(18F);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	public void setStatus(String s){
		this.status=s;
	}
	public void draw(Graphics g){
		g.setFont(traditionalArabicFont);
		g.drawRoundRect(100, 40, 500, 50, 12, 12);
		Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(status, g2d);
        int x=350-(int)(r.getWidth()/2);
        int y=84-(int)(r.getHeight()/2);
        g.drawString(status, x, y);
        g.setFont(Variable.standartFont);
	}
}