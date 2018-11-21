package interno.poprocket.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {
	private PopRocket parent;
	private Stage     stage;
	
	public MenuScreen (PopRocket parent) {
		System.out.println("Entrei no Menu Screen");
		this.parent = parent;
		this.stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(true);
		stage.addActor(table);		
		
		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		
		TextButton newGame = new TextButton("New Game", skin);
        TextButton load    = new TextButton("Load", skin);
        TextButton exit    = new TextButton("Exit", skin);
        TextButton credits = new TextButton("Credits", skin);
        TextButton options = new TextButton("Options", skin);
        
        table.add(newGame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(load).fillX().uniformX();
		table.row();
		table.add(options).fillX().uniformX();
		table.row();
		table.add(credits).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();
		
		
		// create button listeners
	    exit.addListener(new ChangeListener() {
		    @Override
		    public void changed(ChangeEvent event, Actor actor) {
		        Gdx.app.exit();				
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override 
            public void changed(ChangeEvent event, Actor actor) { 
            	parent.changeScreen(parent.GAME);            	
            } 
        });
        
        load.addListener(new ChangeListener() {
            @Override 
            public void changed(ChangeEvent event, Actor actor) { 
            	parent.changeScreen(parent.LOAD_DATA);
            } 
        });
        
        credits.addListener(new ChangeListener() {
            @Override 
            public void changed(ChangeEvent event, Actor actor) { 
            	parent.changeScreen(parent.CREDITS);
            } 
        });
        
        
        options.addListener(new ChangeListener() {
            @Override 
            public void changed(ChangeEvent event, Actor actor) { 
            	parent.changeScreen(parent.OPTIONS);
            } 
        });
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
