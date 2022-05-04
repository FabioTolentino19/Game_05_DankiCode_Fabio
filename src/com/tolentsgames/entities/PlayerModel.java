package com.tolentsgames.entities;

import java.awt.image.BufferedImage;

import com.tolentsgames.main.Game;

public class PlayerModel extends Entity {
	
	public static BufferedImage[] TOWER = { Game.spritesheet.getSprite(16, 16, 16, 16),
											Game.spritesheet.getSprite(00, 32, 16, 16),
											Game.spritesheet.getSprite(16, 32, 16, 16),
											Game.spritesheet.getSprite(00, 48, 16, 16),
											Game.spritesheet.getSprite(16, 48, 16, 16) };

	public PlayerModel(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
//		System.out.println("Colocando no mapa, Modelo Torre: " + (int)this.speed);
	}

}
