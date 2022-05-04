package com.tolentsgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tolentsgames.main.Game;
import com.tolentsgames.world.World;

public class TowerController extends Entity {
	
	public boolean isPressed = false;
	public int xTarget, yTarget;
	private int towerSelection = 0;
	private boolean towerSelected = false;
	private boolean previous = true;
	public int nextTspeed=4; //debug

	public TowerController(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		if(isPressed) {
			isPressed = false;
			boolean liberado = true;
			towerSelected = false;
			//criar torre no mapa
			int xx = (xTarget/16)*16;
			int yy = (yTarget/16)*16;
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				Player testplayer = new Player(xx, yy, 16, 16, 0, PlayerModel.TOWER[0],0,0,0);
				if(e instanceof PlayerModel) {
					if(Entity.isColidding(e, testplayer)) {
						//captura torre selecionada
						towerSelection = (int)e.speed;
						towerSelected = true;
						previous = false;
				//		System.out.println("Selecionei Torre: " + towerSelection + " towerSelected: " + towerSelected + " previous: " + previous);
						break;
					}
				}
			}
			
			if(!towerSelected && previous) {
			//	System.out.println("Selecionei torre no click anterior: " + towerSelection + " towerSelected: " + towerSelected + " previous: " + previous);
				Player player = new Player(xx, yy, 16, 16, 4, PlayerModel.TOWER[towerSelection],5,towerSelection*10+10,towerSelection*20+40);
				for(int i = 0; i < Game.entities.size(); i++) {
					Entity e = Game.entities.get(i);
					if( e instanceof Player) {
						if(Entity.isColidding(e, player)) {
							liberado = false;
							Game.entities.remove(i);
							// já tem torre na posição
						}
					} 
				}
				
				if(World.isFree(xx, yy)) {
					liberado = false;
				}
				
				if(liberado) {
					if(Game.dinheiro >= 2) {
						Game.entities.add(player);
						Game.dinheiro -= 2;
						} else {
							//sem dinheiro para torres
					}
				}
				towerSelected = false;
			} else {
				previous = true;
			//	System.out.println("Selecionei a torre: " + towerSelection + " towerSelected: " + towerSelected + " previous: " + previous);
			}
		}
			if(Game.vida <= 0) {		
				//Game Over
				System.exit(1);
			}
		}
	
	public void render(Graphics g) {
		
		g.setColor(Color.red);
		
		g.drawRect(( 8 + towerSelection) * 16 *      3,  0,   48,   48);
		g.drawRect(((8 + towerSelection) * 16 * 3) + 1,  1, 48-2, 48-2);
		g.drawRect(((8 + towerSelection) * 16 * 3) + 2,  2, 48-4, 48-4);
	}
}
