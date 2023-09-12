import static javax.imageio.ImageIO.read;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener{
	
	private static Timer timer;
	private static final int TIMER_DELAY = 1;

	
	public static final int GAME_WIDTH = 1016;
	public static final int GAME_HEIGHT = 810;
	
	BufferedImage background;
	BufferedImage gameOverBackground;
	private BufferedImage world;
	private Level level;
	private ArrayList<Wall> wallTiles = new ArrayList<Wall>();
	private Player player;  
	private Bullet bullet;
	private boolean gameOver = false;
	private int currLevel = 1;
	
	//Music
	Clip clip;
	

	public Game() {
		init();
	}

	private void init() {
		timer = new javax.swing.Timer(TIMER_DELAY, new ActionListener() {
		     public void actionPerformed(ActionEvent e) {
		    	 tick();
		     }
		 });
		
		world = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		try {
			background = read(new File("bg.png"));
			gameOverBackground = read(new File("over-bg.png"));
			
			File bgMusic = new File("back.wav");
			clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(bgMusic));
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} 
		
		player = new Player();
		bullet = new Bullet();
		bullet.setPlayer(player);
		
		JFrame jFrame = new JFrame("Rainbow Reef");
		jFrame.setSize(GAME_WIDTH, GAME_HEIGHT + 29);
		jFrame.setResizable(false);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLayout(new BorderLayout());
		
		jFrame.add(this);
		jFrame.setVisible(true);
		jFrame.addKeyListener(this);
		this.changeLevel(currLevel);
	}

	private void changeLevel(int i) {
		wallTiles = new ArrayList<Wall>();
		level = new Level("map"+i+".txt");
		initWalls(level.getGrid());
	}

	

	private void initWalls(int[][] grid) {
		for (int i = 1; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Wall newWall = Wall.getWall(grid[i][j]);
                if (newWall != null) {
                    newWall.setLocation(new Point((16 + j*51), (i*26)));
                    newWall.setBoundingBox(-10, -10, 62, 38);
                    wallTiles.add(newWall);
                }
            }
        }
	}

	public void start() {
		timer.start();
		clip.start();
	}
	
	
	
	public void tick() {
		
		checkifOver();
		checkLevel();
		
		if (!gameOver) {
			checkCollisions();
			bullet.move();
			player.move();
		}

		this.repaint();
    }
	
	
	private void checkLevel() {
		int totalWalls = 0;
		int destroyed = 0;
		for (int i = 0; i < wallTiles.size(); i++) {
			Wall wall = wallTiles.get(i);
			
			if (wall instanceof UnBreakableWall) {
				
			}
			else {
				totalWalls++;
				if (wall.isDestroyed()) {
					destroyed++;
				}
			}
		}
		
		if (totalWalls <= destroyed) {
			
			if (currLevel == 3) {
				gameOver = true;
				clip.stop();
				return;
			}
			
			currLevel++;
			bullet.resetBullet();
			player.resetPlayer();
			this.changeLevel(currLevel);
		}
	}
	

	private void checkifOver() {
		if (player.getLives() == 0) {
			gameOver = true;
			clip.stop();
			clip.setMicrosecondPosition(0);
		}
	}

	private void checkCollisions() {
		if (player.getBoundingBox().intersects(bullet.getBoundingBox())) {
			bullet.bouncePlayer();
		}
		
		for (int i = 0; i < wallTiles.size(); i++) {
			Wall wall = wallTiles.get(i);
			if (bullet.getBoundingBox().intersects(wall.getBoundingBox())) {

				int ballLeft = (int) bullet.getBoundingBox().getMinX();
                int ballHeight = (int) bullet.getBoundingBox().getHeight();
                int ballWidth = (int) bullet.getBoundingBox().getWidth();
                int ballTop = (int) bullet.getBoundingBox().getMinY();

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point(ballLeft - 1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!wall.isDestroyed()) {

                	if (wall.getBoundingBox().contains(pointRight)) {

                        bullet.setXdir(-1);
                    } else if (wall.getBoundingBox().contains(pointLeft)) {

                        bullet.setXdir(1);
                    }

                    if (wall.getBoundingBox().contains(pointTop)) {

                        bullet.setYdir(1);
                    } else if (wall.getBoundingBox().contains(pointBottom)) {

                        bullet.setYdir(-1);
                    }

                    wall.setDestroyed(true);
                    
                    player.addScores(wall.setDestroyed(true));
                    
                }
			}
		}
	}

	@Override
    public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Graphics2D buffer = world.createGraphics();
		super.paintComponent(g2);
	
		if (gameOver) {
			g2.drawImage(this.gameOverBackground, 0,0, null);
			g2.setColor(Color.cyan);
			g2.setFont(new Font("Cambria",Font.BOLD,80));
			g2.drawString("Scores: " + player.getScore(), (GAME_WIDTH/2) -200, (GAME_HEIGHT/2)+100);
			return;
		}
		
		buffer.drawImage(this.background, 0,0, null);
		
		for (int i = 0; i < wallTiles.size(); i++) {
			wallTiles.get(i).draw(buffer);
		}
		player.draw(buffer);
		bullet.draw(buffer);
		
		g2.setColor(Color.YELLOW);
		g2.drawImage(world, 0,0, null);
		
		
		g2.setFont(new Font("Helvetica",Font.BOLD,24));
        g2.drawString("Scores: " + player.getScore(), GAME_WIDTH - 635, 40);
        g2.drawString("Lives: " + player.getLives(), GAME_WIDTH - 200, 40);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (gameOver) {
			player.newGame();
			bullet.resetBullet();
			gameOver = false;
			currLevel = 1;
			changeLevel(1);
			clip.start();
		}

		
		int keyPressed = e.getKeyCode();
		if (keyPressed == KeyEvent.VK_LEFT) {
			player.setMovingLeft(true);
		}
		
		if (keyPressed == KeyEvent.VK_RIGHT) {
			player.setMovingRight(true);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyPressed = e.getKeyCode();
		if (keyPressed == KeyEvent.VK_LEFT) {
			player.setMovingLeft(false);
		}
		
		if (keyPressed == KeyEvent.VK_RIGHT) {
			player.setMovingRight(false);
		}
	}
}
