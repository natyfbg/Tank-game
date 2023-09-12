import static javax.imageio.ImageIO.read;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Bullet extends GameObject implements MoveableObject{

	private double INITIAL_SPEED = 2.0;
	Random r= new Random();
	
	private double speed;
	private int heading;
    private Player player;

    private int xdir;
    private int ydir;
	
	public Bullet() {
		speed = INITIAL_SPEED;
		heading = 60;
		
		xdir = 1;
        ydir = -1;
		
        this.setLocation(new Point(r.nextInt(600) + 200, Game.GAME_HEIGHT - 400));
		try {
            this.setImage(read(new File("bullet.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}
	
	
	
	public void setPlayer(Player player) {
		this.player = player;
	}


	public int getXdir() {
		return xdir;
	}


	public void setXdir(int xdir) {
		this.xdir = xdir;
	}


	public int getYdir() {
		return ydir;
	}


	public void setYdir(int ydir) {
		this.ydir = ydir;
	}


	@Override
	public void draw(Graphics g) {        
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.getImage(), this.getLocation().x, this.getLocation().y, null);
	}

	
	@Override
	public void move() {
		int x = this.getLocation().x + (int)(xdir * speed);
		int y = this.getLocation().y + (int)(ydir * speed);
		
		this.getLocation().setLocation(x, y);
		

        if (x <= 0) {
            setXdir(1);
        }

        if (x >= Game.GAME_WIDTH - 25) {
            setXdir(-1);
        }

        if (y <= 90) {
            setYdir(1);
        }
        
        if (y >= Game.GAME_HEIGHT) {
        	player.addLives(-1);
        	resetBullet();
        	player.resetPlayer();
		}
	}
	


	public void resetBullet() {
		if(r.nextInt(2) == 1)
			xdir = 1;
		else 
			xdir = -1;

        ydir = -1;
		speed = INITIAL_SPEED;
		heading = 70;
		
		
		this.setLocation(new Point(r.nextInt(600) + 200, Game.GAME_HEIGHT - 400));
	}



	public void bouncePlayer() {
		int paddleLPos = (int) player.getBoundingBox().getMinX();
        int ballLPos = (int) this.getBoundingBox().getMinX();

        //System.out.println(player.getBoundingBox());
        
        int first = paddleLPos + 5;
        int second = paddleLPos + 45;
        int third = paddleLPos + 85;
        int fourth = paddleLPos + 125;

        
        this.setYdir(-1);
        
        
        speed += 0.4;
	}




	
}
