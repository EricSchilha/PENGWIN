package gdx.pengwin.Release2_0Backup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ScrGame extends GameScreen {
    protected static ScreenType screenType = ScreenType.Game;
    protected static Texture txButtonUp = new Texture(Gdx.files.internal("GameButtonUp.png"));
    protected static Texture txButtonDown = new Texture(Gdx.files.internal("GameButtonDown.png"));
    protected Map map;
    boolean showButtons = false;

    public ScrGame(GamMain gamMain) { //TODO: move the code from here into GameScreen constructor (maybe)
        this.gamMain = gamMain;
        super.screenType = this.screenType;
        super.txButtonUp = this.txButtonUp;
        super.txButtonDown = this.txButtonDown;
        arnKeys = new int[4];
        map = new Map(0);

    }

    @Override
    public void show() {
        map = new Map(0);
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sr.begin(ShapeRenderer.ShapeType.Line);
        batch.setProjectionMatrix(oc.combined);
        sr.setProjectionMatrix(oc.combined);
        map.update();
        map.draw(batch, sr);
        if (showButtons)
            for (SprButton sprButton : alsprButtons)
                if (sprButton.screenType != screenType)
                    sprButton.draw(batch);
        batch.end();
        sr.end();
    }

    @Override
    public boolean keyDown(int keyCode) {   //TODO: add (keys, directions) to a HashMap
        switch (keyCode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                arnKeys[0] = 1;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                arnKeys[1] = 1;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                arnKeys[2] = 1;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                arnKeys[3] = 1;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        switch (keyCode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                arnKeys[0] = 0;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                arnKeys[1] = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                arnKeys[2] = 0;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                arnKeys[3] = 0;
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int mouseButton) {
        if (showButtons) super.touchDown(screenX, screenY, pointer, mouseButton);
        return false;
    }
}
