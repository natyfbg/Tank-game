import static javax.imageio.ImageIO.read;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

public class Player extends GameObject{

	private static final int STARTX = 435;
    private static final int STARTY = Game.GAME_HEIGHT - 50;
    
    private int lives;
    private int score; 
    
    private boolean movingLeft;
    private boolean movingRight;
	
	public Player() {
		super();
		this.setLocation(new Point(STARTX, STARTY));
		lives = 3;
		score = 0;
		movingLeft = false;
		movingRight = false;
		
		try {
            this.setImage(read(new File("player.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.getImage(), this.getLocation().x, this.getLocation().y, null);
	}


	public void addLives(int i) {
		lives += i;
	}

	public int getLives() {
		return lives;
	}

	public int getScore() {
		return score;
	}
	
	public void addScores(int scor) {
		this.score += scor;
	}

	public void move() {
		if (movingLeft) {
			moveLeft();
		}
		
		if(movingRight) {
			movingRight();
		}
		this.setBoundingBox(-100, 0, 328, 8);
	}

	private void movingRight() {
		if(this.getLocation().getX() < Game.GAME_WIDTH -160) {
			this.getLocation().setLocation(getLocation().getX()+15, getLocation().getY());
		}
	}

	private void moveLeft() {
		if(this.getLocation().getX() > 10) {
			this.getLocation().setLocation(getLocation().getX()-15, getLocation().getY());
		}
	}
	
	public void resetPlayer() {
		this.setLocation(new Point(STARTX, STARTY));
		movingLeft = false;
		movingRight = false;
	}

	public void newGame() {
		this.lives = 3;
		this.score = 0;
	}



}
