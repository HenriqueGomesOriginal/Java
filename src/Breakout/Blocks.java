package Breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blocks {
	public int x, y;
	private int width=40, height=12;

	public Blocks(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void Render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
