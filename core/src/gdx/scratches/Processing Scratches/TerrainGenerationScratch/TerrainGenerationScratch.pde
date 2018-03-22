/*
* This is a scratch program to test creating realistic
* world environments.  The shaded dark areas represent
* depth (darker = higher), and the blue areas on the
* map represent trees.
*/

int W, H, D;
int val, scale;
boolean outOfBounds;
Map map;
void setup() {
  size(800, 800);
  scale = 20;
  W = height/scale;
  H = width/scale;
  map = new Map(W, H);
  vegetation();
}

void draw() {
  noiseSeed(0);
  randomSeed(0);
  background(255);
  drawMap();
}

void drawMap() {
  for (int h = 0; h < map.getHeight(); h++) {
    for (int w = 0; w < map.getWidth(); w++) {
      val = map.terrain[h][w].depth;
      if(map.terrain[h][w].state==State.Grass){
        fill(0, map(val, 0, 10, 255, 0), 0);
      } else {
        fill(0, 0, 255);
      }
      rect(w*scale, h*scale, scale, scale);
    }
  }
}

class Map {
  private final int W, H;
  Tile terrain[][];
  float wOffset, hOffset;
  Map(int W, int H) {
    this.W = W;
    this.H = H;
    this.wOffset = 0;
    this.hOffset = 0;
    terrain = new Tile[H][W];
    generateTerrain();
  }
  public void generateTerrain() {
   wOffset = 0;
   hOffset = 0;
    for (int h = 0; h < H; h++) {
      wOffset = 0;
      for (int w = 0; w < W; w++) {
        terrain[h][w] = new Tile(parseInt(map(noise(hOffset, wOffset), 0, 1, 0, 10)), w, h, State.Grass);
        wOffset+=0.2;
      }
      hOffset+=0.2;
    }
  }

  public int getWidth() {
    return W;
  }
  public int getHeight() {
    return H;
  }
}

class Tile {
  int x, y, depth;
  State state;
  Tile(int depth, int x, int y, State state) {
    this.depth = depth;
    this.x = x;
    this.y = y;
    this.state = state;
  }
}

enum State {
  Tree, Grass;
}