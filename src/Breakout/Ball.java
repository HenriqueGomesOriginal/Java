package Breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball extends Breakout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private int width = 7, height = 7;
	private Random rand;
	public float x, y, xVel = 2, yVel = 2;
	
	public Ball(){	
		
		rand = new Random();
		this.x = breakout.getWidth() / 2;
		this.y = breakout.getHeight() - 35;
	}
	
	public void tick(Paddle paddle) {
		
		// Paddle collision
		if(paddle.getY() <= y + height && paddle.getY() >= y - height) {
			if((x >= paddle.getX()) && (x <= paddle.getX() + paddle.width)) {
				if(yVel > 0) {
					xVel += rand.nextFloat() - 0.5;
				}
				yVel *= -1;
			}
		} else {
			
			// Walls collision
			if(x <= 0) {
				xVel *= -1;
			} else if(x >= breakout.getWidth() - width) {
				xVel *= -1;
			}
			if(y <= 0) {
				yVel *= -1;
			}  else if(y >= 420) {
				breakout.life--;
				Reset();
			}
		
		}
		
		// Blocks collision
		if(breakout.Collision()) {
			if(breakout.side == true) {
				xVel *= -1;
			} else {
				yVel *= -1;
			}
		} 
		
		y += yVel;
		x += xVel;
	}
	
	public void Reset() {
		
		this.x = breakout.getWidth() / 2;
		this.y = breakout.getHeight() - 35;
		this.xVel = 2;
		this.yVel = 2;
		this.xVel *= -1;
		this.yVel *= -1;
	}
	
	public void Render(Graphics g) {
		
		g.setColor(Color.magenta);
		g.fillRect((int)x, (int)y, width, height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
