import static javax.imageio.ImageIO.read;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

public class UnBreakableWall extends Wall{
	
	public UnBreakableWall() {
		init();
	}
	
	public UnBreakableWall(Point p) {
		super(p);
		init();
	}

	private void init() {
		this.setType(1);
		try {
            this.setImage(read(new File("un_breakable.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}

	@Override
	public void draw(Graphics g) {
		 Graphics2D g2d = (Graphics2D) g;
		 g2d.drawImage(this.getImage(), this.getLocation().x, this.getLocation().y, null);
	}
	
	
}