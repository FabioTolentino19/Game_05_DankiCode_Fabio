package com.tolentsgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tolentsgames.main.Game;
import com.tolentsgames.world.World;

public class BulletShoot extends Entity {
	
	private double dx;
	private double dy;
	
	private int life = 20, curLife = 0;
	
	
	public BulletShoot(double x, double y, int width, int height, double speed, BufferedImage sprite, double dx, double dy, int alcance) {
			super(x, y, width, height, speed, sprite);
			this.dx = dx;
			this.dy = dy;
			this.life = alcance;
	}
	
	public void tick() {
		if(curLife == life) {
			Game.bullets.remove(this);
			return;
		}
				
			x += dx*speed;
			y += dy*speed;
			curLife++;
		//	System.out.println("x:" + x + " dx:" + dx + " y:" + y + " dy:" + dy);
		
	//		Game.bullets.remove(this);
	//		World.generateParticles(100, (int)x, (int)y, 1);
	//		return;
		
	
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX(), this.getY(), width, height);
	}

}
