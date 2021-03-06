package gdx.pengwin.Release3_1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Chunk {
    public static /*final*/ int CHUNK_SIZE = 32;

    private Map map;
    Random randNPO;
    public SprNPO[][] arsprNPO; //Stores the other things on the map (trees, rocks, etc.)
    public SprTile[][] arsprTiles;
    Vector2 vTopLeft;

    public Chunk(Vector2 vTopLeft, Map map) {
        this.map = map;
        this.arsprTiles = new SprTile[CHUNK_SIZE][CHUNK_SIZE];
        this.arsprNPO = new SprNPO[CHUNK_SIZE][CHUNK_SIZE];
        this.vTopLeft = vTopLeft;
        randNPO = new Random((int) map.noise.noise((int) this.vTopLeft.x, (int) this.vTopLeft.y) * 10000);
        generateMap();
    }

    public void generateMap() {
        float fWidthOffset = 0;
        float fHeightOffset = 0;
        float fPersistence = (float) 0.05;
        for (int y = 0; y < arsprTiles.length; y++) {
            for (int x = 0; x < arsprTiles[y].length; x++) {
                arsprTiles[y][x] = new SprTile(TileType.Grass);
                double dNoiseVal = map.noise.noise(vTopLeft.x * fPersistence + fWidthOffset, vTopLeft.y * fPersistence + fHeightOffset);
                fWidthOffset += fPersistence;
                arsprTiles[y][x] = (dNoiseVal < 0.35) ? new SprTile(TileType.Water) : (dNoiseVal < 0.65 ? new SprTile(TileType.Grass) : new SprTile(TileType.Mountain));
                if (x % 2 == 0 && y % 2 == 0) {
                    if (arsprTiles[y][x].tileType == TileType.Grass) {
                        try {
                            int nX = randNPO.nextInt(3) - 1;
                            int nY = randNPO.nextInt(3) - 1;
                            if (arsprTiles[y + nY][x + nX].tileType == TileType.Grass) {
                                arsprNPO[y + nY][x + nX] = new SprNPO(NPOType.Tree);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                if(x % 3 == 0 && y % 3 == 0) {
                    if (arsprTiles[y][x].tileType == TileType.Grass) {
                        try {
                            int nX = randNPO.nextInt(3) - 1;
                            int nY = randNPO.nextInt(3) - 1;
                            if (arsprTiles[y + nY][x + nX].tileType == TileType.Grass) {
                                arsprNPO[y + nY][x + nX] = new SprNPO(NPOType.Rock);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
            fWidthOffset = 0;
            fHeightOffset += fPersistence;
        }
    }

    public void draw(SpriteBatch batch, SprPlayer player) {
        boolean playerDrawn = false;
        for (int y = 0; y < arsprTiles.length; y++) {
            for (int x = 0; x < arsprTiles[0].length; x++) {
                if (arsprTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getLocation().x + x + 1) + player.nPixelX > 0 && arsprTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getLocation().x + x) + player.nPixelX < Gdx.graphics.getWidth() && arsprTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getLocation().y + y + 1) + player.nPixelY > 0 && arsprTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getLocation().y + y - 1) + player.nPixelY < Gdx.graphics.getHeight()) {
                    arsprTiles[y][x].draw(batch, arsprTiles[y][x].TILE_SIZE * (vTopLeft.x - player.getLocation().x + x) + player.nPixelX, arsprTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getLocation().y + y) + player.nPixelY);
                    if(!playerDrawn && arsprTiles[y][x].TILE_SIZE * (vTopLeft.y - player.getLocation().y + y + player.fVertOffset) + player.nPixelY >= player.nPixelY) {
                        player.draw(batch);
                        playerDrawn = true;
                    }
                    if (arsprNPO[y][x] != null) {
                        arsprNPO[y][x].draw(batch, arsprNPO[y][x].OBJECT_WIDTH * (vTopLeft.x - player.getLocation().x + x) + player.nPixelX, arsprNPO[y][x].OBJECT_WIDTH * (vTopLeft.y - player.getLocation().y + y) + player.nPixelY);
                    }
                }
            }
        }
    }

}