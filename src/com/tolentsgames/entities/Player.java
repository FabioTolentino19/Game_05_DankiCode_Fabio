package com.tolentsgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tolentsgames.main.Game;
import com.tolentsgames.main.Sound;
import com.tolentsgames.world.Camera;


public class Player extends Entity {
	
	private int xTarget, yTarget;
	public boolean atacando = false;
	private int tiroFrame = 0, maxTiroFrame;
	private int alcance;
	private int mira;

	public Player(double x, double y, int width, int height, double speed, BufferedImage sprite, int maxTiroFrame, int alcance, int mira) {
		super(x, y, width, height, speed, sprite);
		this.maxTiroFrame = maxTiroFrame;
		this.alcance = alcance;
		this.mira = mira;
	}
	
		
	public void tick() {
		tiroFrame++;
		Enemy enemy = null;
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.calculateDistance(this.getX(), this.getY(), e.getX(), e.getY()) < mira) {
					enemy = (Enemy) e;
				}
			}
		}
		
		if(enemy != null) {
			atacando = true;			
			xTarget = enemy.getX();
			yTarget = enemy.getY();
		//	if(Entity.rand.nextInt(100) < 30)
			//	enemy.vida -= Entity.rand.nextDouble();
		} else {
			atacando = false;
		}
		
		if(atacando && (tiroFrame >= maxTiroFrame)) {
			//Criar bala e atirar
				
			int px = 8, py = 8, mBullet = 0;
			double angle = 0;
			angle = Math.atan2(yTarget - this.getY()+py, xTarget - this.getX()+px);
							
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);
			
			if(mira == 120)
				mBullet = -2;
				
			for(int i = mBullet; i <= 2; i += 4) {
				BulletShoot bullet = new BulletShoot(this.getX()+px+i, this.getY()+py+i, 3, 3, speed, null, dx, dy, alcance);
				Game.bullets.add(bullet);				
			}
			tiroFrame = 0;
		}	
	}
	
	public void render(Graphics g) {
		super.render(g);
		// Mostrar ataque as torres atraves do BulletShot
	//	if(atacando) {
	//		g.setColor(Color.green);
	//		g.drawLine((int)x+8, (int)y+8, xTarget+8, yTarget+8);
	//	}
	}
}
