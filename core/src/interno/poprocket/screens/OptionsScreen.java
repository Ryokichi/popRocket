package interno.poprocket.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class OptionsScreen implements Screen {
	
	private PopRocket parent;
	private Stage     stage;
	private SpriteBatch batch;
	private Sprite bkg;

    public OptionsScreen(PopRocket popRocket) {
    	System.out.println("Entrei option Screen");
		this.parent = popRocket;
		this.stage = new Stage(new ScreenViewport());
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		this.bkg = new Sprite(new Texture(Gdx.files.internal("img/menu_bkg.png")));		
		this.bkg.setSize(w, h*1.78f);
		batch = new SpriteBatch();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(true);
		stage.addActor(table);		
		
		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));		
		TextButton back_btn = new TextButton("Back", skin, "small");        
        table.add(back_btn).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		
		
		back_btn.addListener(new ChangeListener() {
            @Override 
            public void changed(ChangeEvent event, Actor actor) { 
            	parent.changeScreen(parent.MENU);
            } 
        });
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		this.bkg.draw(batch);
		batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);	
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
