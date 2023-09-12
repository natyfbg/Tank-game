import java.awt.Point;

public abstract class Wall extends GameObject{
	private int type;
	private boolean destroyed;
	
	public Wall() {
		super();
		destroyed = false;
	}
	
	public Wall(Point p) {
		super(p);
		destroyed = false;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public int setDestroyed(boolean destroyed) {
		if (type == 1) {
			return 0;
		}
		
		this.destroyed = destroyed;
		
		if (type == 2) {
			return 10;
		}
		
		if (type == 3) {
			return 30;
		}
		
		else{
			return 50;
		}
	}

	public static Wall getWall(int type) {
		Wall wall = null;
        if (type == 1) {
        	wall = new UnBreakableWall();
        } else if (type == 2) {
        	wall = new BreakableWall();
        } else if (type == 3) {
        	wall = new BonusWall();
        } else if (type == 4) {
        	wall = new TargetWall();
        }
        return wall;
	}
	
}
