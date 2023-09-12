import static javax.imageio.ImageIO.read;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

public class BonusWall extends Wall{
	
	public BonusWall(Point p) {
		super(p);
		init();
	}

	public BonusWall() {
		init();
	}
	
	private void init() {
		this.setType(3);
		try {
            this.setImage(read(new File("bonus_wall.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}

	@Override
	public void draw(Graphics g) {
		 Graphics2D g2d = (Graphics2D) g;
		 if(!this.isDestroyed()) {
			 g2d.drawImage(this.getImage(), this.getLocation().x, this.getLocation().y, null);
		 } 
	}
	
	
}
