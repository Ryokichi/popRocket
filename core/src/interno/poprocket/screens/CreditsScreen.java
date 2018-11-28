package interno.poprocket.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CreditsScreen implements Screen {
	
	private PopRocket parent;
	private Stage     stage;
	private Sprite    bkg1;
	private SpriteBatch batch;

    public CreditsScreen(PopRocket popRocket) {
    	System.out.println("Entrei no Credit Screen");
		this.parent = popRocket;
		this.stage = new Stage(new ScreenViewport());
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		this.bkg1 = new Sprite(new Texture(Gdx.files.internal("img/menu_bkg.png")));		
		this.bkg1.setSize(w, h*1.78f);
	    batch = new SpriteBatch();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		
		
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(parent.debugMode);
		stage.addActor(table);		
		
		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		TextButton backBtn = new TextButton("Back", skin);		
		Label txtDesign = new Label("Design:", skin, "big");
		Label txtCoding = new Label("Coding:", skin, "big");
		Label bianca    = new Label("Bianca Soliman Bariani", skin,"big");
		Label carol     = new Label("Caroline Goncalves de Felipe", skin,"big");
		Label heitor    = new Label("Heitor Ryokichi Nakamura", skin,"big");
		Label will      = new Label("William Marques Santos", skin,"big");
		
		table.row().pad(5,0,5,0);
		table.add(txtDesign);		
		table.row().pad(1,0,1,0);
		table.add(bianca);
		table.row().pad(1,0,1,0);
		table.add(carol);
		
		table.row().pad(30,0,5,0);		
		table.add(txtCoding);		
		table.row().pad(1,0,1,0);
		table.add(heitor);
		table.row().pad(1,0,1,0);
		table.add(will);
		
		table.row().pad(30,0,0,0);
        table.add(backBtn);
		
		
		
        backBtn.addListener(new ChangeListener() {			
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
		bkg1.draw(batch);
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
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
