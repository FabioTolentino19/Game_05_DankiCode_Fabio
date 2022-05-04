package com.tolentsgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.tolentsgames.entities.Entity;
import com.tolentsgames.entities.Particle;
import com.tolentsgames.entities.PlayerModel;
import com.tolentsgames.entities.Spawner;
import com.tolentsgames.main.Game;
import com.tolentsgames.main.Sound;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	public static boolean fullMap, premioMap;

	public static int xFINAL = 0;
	public static int yFINAL = 0;
	
	public static int xINITIAL = 0;
	public static int yINITIAL = 0;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					} else if(pixelAtual == 0xFFFFFFFF) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					} else if(pixelAtual == 0xFFFF0000) {
						//criar Spawner
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
						Spawner spawner = new Spawner(xx*16, yy*16, 16, 16, 0, null);
						Game.entities.add(spawner);
						xINITIAL = xx;
						yINITIAL = yy;
					} else if(pixelAtual == 0xFF0026FF) {
						//Target final do inimigo
						tiles[xx + (yy * WIDTH)] = new TargetTile(xx*16, yy*16, Tile.TILE_FLOOR);
						xFINAL = xx;
						yFINAL = yy;
					} else if(pixelAtual == 0xFF4800FF) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, PlayerModel.TOWER[0]);
						PlayerModel playermodel = new PlayerModel(xx*16, yy*16, 16, 16, 0, null);
						Game.entities.add(playermodel);
					} else if(pixelAtual == 0xFF007F0E) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, PlayerModel.TOWER[1]);
						PlayerModel playermodel = new PlayerModel(xx*16, yy*16, 16, 16, 1, null);
						Game.entities.add(playermodel);
					} else if(pixelAtual == 0xFF7F0000) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, PlayerModel.TOWER[2]);
						PlayerModel playermodel = new PlayerModel(xx*16, yy*16, 16, 16, 2, null);
						Game.entities.add(playermodel);
					} else if(pixelAtual == 0xFF00FFFF) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, PlayerModel.TOWER[3]);
						PlayerModel playermodel = new PlayerModel(xx*16, yy*16, 16, 16, 3, null);
						Game.entities.add(playermodel);
					} else if(pixelAtual == 0xFFFF00DC) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, PlayerModel.TOWER[4]);
						PlayerModel playermodel = new PlayerModel(xx*16, yy*16, 16, 16, 4, null);
						Game.entities.add(playermodel);
					}
				}  
			}
		} catch (IOException e) {
			e.printStackTrace();
			}
		}
	
	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+width-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+height-1) / TILE_SIZE;
		
		int x4 = (xnext+width-1) / TILE_SIZE;
		int y4 = (ynext+height-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)
				 );
	}

	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)
				 );
	}
	
	public static void generateParticles(int amount, int x, int y, int pColor) {
		for(int i = 0; i < amount; i++ ) {
			if(pColor == 0)
				Game.entities.add(new Particle(x+(TILE_SIZE/2), y+(TILE_SIZE/2), 1, 1, 0, null, pColor));			
			if(pColor == 1) {
				Sound.explosionWall.play(Sound.effectsVolume);
				Game.entities.add(new Particle(x, y, 1, 1, 0, null, pColor));
			}
		}
	}
		
	public static void restartGame() {
		
		return;	
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4; //Divisão por 16 shift binário :)
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
