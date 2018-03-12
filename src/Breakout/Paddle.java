package Breakout;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle extends Breakout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	public float x, y;
	private double xVel;
	private double gravity = 0.85;
	public int width = 70, height = 10;
	
	public Paddle() {
		
		this.y = (breakout.getHeight() - height) - 10;
		this.x = (breakout.getWidth() / 2) - 16;
	}
	
	public void tick() {
		
		// Move paddle
		if(breakout.left) {
			xVel--;
		} else if(breakout.right) {
			xVel++;
		} else {
			xVel*=gravity;
		}
		x += xVel;
		
		// Limit bounds for paddle
		if(x <= 0) {
			x = 0;
			xVel = 0;
		} else if(x >= breakout.getWidth() - width) {
			x = breakout.getWidth() - width;
			xVel = 0;
		}
	}
	
	public void Render(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, width, height);
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
}
