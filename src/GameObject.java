import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	private Point location;
	private Rectangle boundingBox;
	private BufferedImage image;
	
	public GameObject() {
		this.boundingBox =  new Rectangle();
	}
	
	public GameObject(Point location) {
		this.location = location;
		this.boundingBox =  new Rectangle();
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Rectangle getBoundingBox() {
		return new Rectangle(this.getLocation().x, this.getLocation().y, image.getWidth(null), image.getHeight(null));
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setBoundingBox(int x, int y, int w, int h){
        this.boundingBox.x = this.location.x + x;
        this.boundingBox.y = this.location.y + y;
        this.boundingBox.width = w;
        this.boundingBox.height = h;
    }

	public abstract void draw(Graphics g);
}
