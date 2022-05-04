package com.tolentsgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tolentsgames.main.Game;
import com.tolentsgames.main.Sound;
import com.tolentsgames.world.AStar;
import com.tolentsgames.world.Vector2i;
import com.tolentsgames.world.World;

public class Enemy extends Entity {
	
	public boolean right = true, left = false;
	public double vida = 20;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		path = AStar.findPath(Game.world, new Vector2i(World.xINITIAL, World.yINITIAL), new Vector2i(World.xFINAL, World.yFINAL));
	}
	
	public void tick() {
		followPath(path);
		if(x >= Game.WIDTH) {
			//Perdemos vida aqui
			Game.vida -= Entity.rand.nextDouble();
			Game.entities.remove(this);
			return;
		}
		
		coliddingBullet();
		if(vida <= 0) {
			World.generateParticles(100, this.getX(), this.getY(), 0);
			Game.entities.remove(this);
			Game.dinheiro++;
			return;
		}
	}
	
	public void coliddingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if( e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
				//	if(Entity.rand.nextInt(100) < 30)
					vida--; // -= Entity.rand.nextDouble();
				//	World.generateParticles(50, this.getX(), this.getY(), 0);
				
				//	Sound.hurtEffectEnemy.play(Sound.effectsVolume);
					Game.bullets.remove(i);
				//	World.generateParticles(50, this.getX(), this.getY(), 0);
					return;
				}
			}
		}
	}
	
	
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.red);
		g.fillRect((int)x, (int)(y-5), 20, 6);
		
		g.setColor(Color.green);
		g.fillRect((int)x, (int)(y-5),(int)((vida/20) * 20), 6);
	}
}
