package Breakout;

/**
 * Autor: Henrique Gomes
 * This isn't a clone of the original Breakout, I'm using the same idea,
 * it's a reconstruction in java of that game.
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Breakout extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	public static final int width = 420, height = 420;
	private boolean Running;
	public int life = 3;
	private int MAX_BLOCKS = 44, score; 
	public boolean side, GameOver=false;
	
	public static Breakout breakout;
	private static JFrame frame;
	private Paddle paddle;
	private Ball ball;
	private ArrayList<Blocks> blocks;
	
	// Buttons
	public boolean left, right;
	
	public Breakout() {
		
		addKeyListener(this);
	}
	
	// Start is a preparing for the game loop
	private void Start() {
	
		if(Running) return;
		Running = true;
		new Thread(this, "Main thread").start();
		
		paddle = new Paddle();
		ball = new Ball();
		blocks = new ArrayList<Blocks>();
	}
	
	private void Create_Blocks() {
		
		int x = 10, y = 10;
		int count = 0;
		
		// Position of blocks
		for(int i = 0; i <= MAX_BLOCKS; i++) {
			if(i%9 == 0) {
				count = 0;
				y += 22;
			}
			blocks.add(i, new Blocks(x + (45 * count), y));
			count++;
		}
	}
	
	private static void Frame() {
		
		frame = new JFrame("Breakout");
		
		frame.setSize(width + 6, height + 25);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	// Takes care about the Graphics...
	public void Render() {
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null) {
			
			// Create a triple buffer
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		// Background
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		// Score
		g.setColor(Color.yellow);
		g.drawString("" + score, 20, 20);
		// Life count
		for(int i=0; i <= life; i++) {
			if(i == 1) g.fillRect(50, 15, 5, 5);
			if(i == 2) g.fillRect(60, 15, 5, 5);
			if(i == 3) g.fillRect(70, 15, 5, 5);
		}
		paddle.Render(g);
		ball.Render(g);
		
		for(int i=0; i < blocks.size(); i++) {
			blocks.get(i).Render(g);
		}
		
		if(GameOver) {
			g.setColor(Color.yellow);
			g.setFont(new Font("Serif", 50, 50));
			g.drawString("Game Over", width / 2 - 110, height / 2);
		}
		
		g.dispose();
		bs.show();
	}
	
	// Tick is the responsible of update the game
	public void Tick() {
		
		if(GameOver) return;
		paddle.tick();
		ball.tick(paddle);
		
		if(life == 0) GameOver = true;
	}

	// Game loop
	public void run() {
		int fps = 0, tps = 60;
		long wait = 1000/tps;
		long clock = System.currentTimeMillis(), tickStart = 0, lastTick = 0, lastTime = 0;
		
		while(Running) {
			
			tickStart = System.currentTimeMillis();

			Render();
			Tick();
			
			// This counts the frames per second 
			fps++;
			if(lastTime >= clock + 1000) {
				System.out.println("FPS: "+fps);
				
				fps = 0;
				clock = lastTime;
			}
			lastTime = System.currentTimeMillis();
			
			lastTick = System.currentTimeMillis();
			
			// Lock the fps
			if (wait > lastTick - tickStart) {
				try {
					Thread.sleep(wait - (lastTick - tickStart));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	// Detect collision intersecting arrays of position.
	public boolean Collision() {
		
		//See if exist blocks, else reset the game
		if(blocks.size() > 0) {
			for(int i=0; i < blocks.size(); i++) {
				//See if intersect the array bounds of blocks with the ball
				if(blocks.get(i).getBounds().intersects(ball.getBounds())) {
					//See if the collision is on the side of the block
					if(blocks.get(i).y > ball.y - 5) {
						side = true;
					} else {
						side = false;
					}
					blocks.remove(i);
					score += 50;
					
					return true;
				}
			}
		} else {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ball.Reset();
			Create_Blocks();
		}
		return false;
	}

	// Simply keyListenner methods...
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) {
			left = true;
		} else if(key == KeyEvent.VK_RIGHT) {
			right = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) {
			left = false;
		} else if(key == KeyEvent.VK_RIGHT) {
			right = false;
		}		
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public static void main(String[] args) {

		breakout = new Breakout();		
		Frame();
		frame.add(breakout);
		breakout.Start();
	}
}