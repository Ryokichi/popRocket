package interno.poprocket.screens;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;

import interno.db.SqliteConn;

public class LoadDataScreen implements Screen {
	final SqliteConn db = new SqliteConn();	
	private PopRocket parent;
	private Stage     stage;
	private SpriteBatch batch;
	private Sprite bkg;

    public LoadDataScreen (PopRocket popRocket) {
    	System.out.println("Entrei load data Screen");
		parent = popRocket;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();		
		this.bkg = new Sprite(new Texture(Gdx.files.internal("img/menu_bkg.png")));		
		this.bkg.setSize(w, h*1.78f);
		batch = new SpriteBatch();
		
		stage = new Stage(new ScreenViewport());
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(this.parent.debugMode);
		stage.addActor(table);		
		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));	
		TextButton slot1   = new TextButton("Slot 1", skin);
		TextButton slot2   = new TextButton("Slot 2", skin);
		TextButton slot3   = new TextButton("Slot 3", skin);
		TextButton backBtn = new TextButton("Voltar", skin);
        
        table.add(slot1);
		table.row().pad(10, 0, 10, 0);
		table.add(slot2);
		table.row().pad(10, 0, 10, 0);
		table.add(slot3);
		table.row().pad(10, 0, 10, 0);
		table.add(backBtn).fillX().uniformX();
		
		slot1.addListener(new ChangeListener() {
            @Override 
            public void changed(ChangeEvent event, Actor actor) { 
            	parent.cosultaDB(1);
            	parent.changeScreen(parent.MENU);
            	stage.clear();
            } 
        });
		
		slot2.addListener(new ChangeListener() {
            @Override 
            public void changed(ChangeEvent event, Actor actor) { 
            	parent.cosultaDB(2);
            	parent.changeScreen(parent.MENU);
            	stage.clear();
            } 
        });
		
		slot3.addListener(new ChangeListener() {
            @Override 
            public void changed(ChangeEvent event, Actor actor) {
            	parent.cosultaDB(3);
            	parent.changeScreen(parent.MENU);
            	stage.clear();
            } 
        });
		
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
